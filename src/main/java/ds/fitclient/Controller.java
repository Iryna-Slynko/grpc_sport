package ds.fitclient;

import com.google.protobuf.Empty;
import ds.mysmartfitness.FitnessClient;
import ds.mysmartgym.ConsumedFoodRequest;
import ds.mysmartgym.HeartBeat;
import ds.mysmartgym.MySmartGymGrpc;
import ds.mysmartgym.NutrientsReply;
import ds.mysmartgym.Weight;
import ds.mysmartgym.WorkoutIntensity;
import ds.shared.DNSLookup;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import org.jdatepicker.JDatePicker;

public class Controller {
  private JTextField weightText, weightReplyText;
  private JTextArea foodText, foodReplyText;
  private JTextField pulseTextField, pulseReplyText;
  private JTextArea fitnessResponseText;
  private JDatePicker fitnessDatePicker;
  private SmartGymActionListener smartGymActionListener;
  private FitnessActionListener fitnessActionListener;
  private JLabel label;

  private class SmartGymActionListener implements ActionListener {

    private ManagedChannel channel;
    private MySmartGymGrpc.MySmartGymStub asyncStub;

    public SmartGymActionListener() {
      final String target = DNSLookup.getEndpoint("smartgym");
      if (target.equals("")) {
        JOptionPane.showMessageDialog(null,
                                      "Can not connect to smartgym service");
        return;
      }
      channel = ManagedChannelBuilder.forTarget(target).usePlaintext().build();
      asyncStub = MySmartGymGrpc.newStub(channel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

      JButton button = (JButton)e.getSource();
      switch (button.getName()) {
      case "add_weight":
        addWeight();
        break;
      case "send_food_info":
        sendFoodInfo();
        break;
      case "send_heart_beat":
        sendHeartBeat();
        break;
      }
    }

    private void sendFoodInfo() {
      foodReplyText.setText("Processing");
      final StreamObserver<NutrientsReply> responseObserver =
          new StreamObserver<NutrientsReply>() {
            StringBuffer sb = new StringBuffer();

            @Override
            public void onNext(final NutrientsReply value) {
              sb.append(value.getMessage());
              sb.append(System.getProperty("line.separator"));
              foodReplyText.setText(sb.toString());
            }

            @Override
            public void onError(final Throwable t) {
              t.printStackTrace();
            }

            @Override
            public void onCompleted() {}
          };
      final StreamObserver<ConsumedFoodRequest> food =
          asyncStub.consumedFoodStreaming(responseObserver);
      foodText.getText().lines().forEach(line -> {
        String[] data = line.split(",");
        if (data.length >= 2) {
          try {
            int value = Integer.parseInt(data[1].trim());
            food.onNext(ConsumedFoodRequest.newBuilder()
                            .setFood(data[0])
                            .setQuantity(value)
                            .build());
          } catch (NumberFormatException exception) {
            JOptionPane.showMessageDialog(null,
                                          "Can not parse amount " + data[1]);
          } catch (Exception exception) {
            System.err.println(exception);
          }
        }
      });
      food.onCompleted();
    }

    private void sendHeartBeat() {
      final StreamObserver<WorkoutIntensity> responseObserver =
          new StreamObserver<WorkoutIntensity>() {
            int[] intensities = new int[6];

            @Override
            public void onNext(final WorkoutIntensity value) {
              intensities[value.getZone()]++;
            }

            @Override
            public void onError(final Throwable t) {
              t.printStackTrace();
            }

            @Override
            public void onCompleted() {
              int max = 0;
              int best = 0;
              for (int i = 1; i < intensities.length; i++) {
                if (intensities[i] >= max) {
                  max = intensities[i];
                  best = i;
                }
              }
              pulseReplyText.setText("spent most of the time "
                                     + "doing exercises with " + best +
                                     " intensity");
            }
          };
      final StreamObserver<HeartBeat> beats =
          asyncStub.heartTracking(responseObserver);
      String[] heartBeats = pulseTextField.getText().split(",");
      for (int i = 0; i < heartBeats.length; i++) {
        try {
          beats.onNext(HeartBeat.newBuilder()
                           .setPulse(Integer.parseInt(heartBeats[i]))
                           .build());
        } catch (NumberFormatException exception) {
          JOptionPane.showMessageDialog(null,
                                        "Can not parse pulse " + heartBeats[i]);
        } catch (Exception exception) {
          System.err.println(exception);
        }
      }
      beats.onCompleted();
    }

    private void addWeight() {
      final Float weight = Float.parseFloat(weightText.getText());
      weightReplyText.setText("Processing");
      final Weight request = Weight.newBuilder().setWeight(weight).build();
      StreamObserver<Empty> noop = new StreamObserver<Empty>() {
        @Override
        public void onNext(Empty value) {}

        @Override
        public void onError(Throwable t) {}

        @Override
        public void onCompleted() {
          weightReplyText.setText("Added weight " + weight.toString());
        }
      };
      asyncStub.weightUpdate(request, noop);
    }
  }

  private class FitnessActionListener implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
      try {
        FitnessClient client = new FitnessClient();
        var exercises = client.getExercises(
            (Calendar)(fitnessDatePicker.getModel().getValue()));
        final StringBuilder activities = new StringBuilder();
        exercises.forEachRemaining(activity -> {
          activities.append(activity.getActivity());
          int duration = activity.getDuration();
          if (duration > 0) {
            activities.append(" for ");
            activities.append(duration);
            activities.append(" minutes");
          } else {
            activities.append(" - ");
            activities.append(activity.getSteps());
            activities.append(" steps");
          }
          activities.append(System.getProperty("line.separator"));
        });
        if (activities.isEmpty()) {
          fitnessResponseText.setText("No exercises that day");
        } else {
          fitnessResponseText.setText(activities.toString());
        }
      } catch (Exception exception) {
        System.err.println(exception.toString());
      }
    }
  }
  public static void main(String[] args) {

    Controller gui = new Controller();

    gui.build();
  }

  public Controller() {
    smartGymActionListener = new SmartGymActionListener();
    fitnessActionListener = new FitnessActionListener();
  }

  private JPanel createAddWeightPanel() {

    JPanel panel = new JPanel();

    BoxLayout boxlayout = new BoxLayout(panel, BoxLayout.LINE_AXIS);

    JLabel label = new JLabel("Enter value");
    panel.add(label);
    panel.add(Box.createRigidArea(new Dimension(10, 0)));
    weightText = new JTextField("", 10);
    panel.add(weightText);
    panel.add(Box.createRigidArea(new Dimension(10, 0)));

    JButton button = new JButton("Add weight");
    button.setName("add_weight");
    button.addActionListener(smartGymActionListener);
    panel.add(button);
    panel.add(Box.createRigidArea(new Dimension(10, 0)));

    weightReplyText = new JTextField("", 10);
    weightReplyText.setEditable(false);
    panel.add(weightReplyText);

    panel.setLayout(boxlayout);

    return panel;
  }

  private JPanel createSendFoodTextPanel() {

    JPanel panel = new JPanel();

    BoxLayout boxlayout = new BoxLayout(panel, BoxLayout.LINE_AXIS);

    JLabel label = new JLabel("Enter comma separated values for food");
    panel.add(label);
    panel.add(Box.createRigidArea(new Dimension(10, 0)));
    foodText = new JTextArea("Ham, 20", 2, 10);
    panel.add(foodText);
    panel.add(Box.createRigidArea(new Dimension(10, 0)));

    JButton button = new JButton("Send Food Information");
    button.setName("send_food_info");
    button.addActionListener(smartGymActionListener);
    panel.add(button);
    panel.add(Box.createRigidArea(new Dimension(10, 0)));

    foodReplyText = new JTextArea("", 2, 10);
    foodReplyText.setEditable(false);
    panel.add(foodReplyText);

    panel.setLayout(boxlayout);

    return panel;
  }

  private JPanel createSendHeartBeatPanel() {

    JPanel panel = new JPanel();

    BoxLayout boxlayout = new BoxLayout(panel, BoxLayout.LINE_AXIS);

    JLabel label = new JLabel("Enter value");
    panel.add(label);
    panel.add(Box.createRigidArea(new Dimension(10, 0)));
    pulseTextField = new JTextField("", 10);
    panel.add(pulseTextField);
    panel.add(Box.createRigidArea(new Dimension(10, 0)));

    JButton button = new JButton("Send Heart Beat Info");
    button.setName("send_heart_beat");
    button.addActionListener(smartGymActionListener);
    panel.add(button);
    panel.add(Box.createRigidArea(new Dimension(10, 0)));

    pulseReplyText = new JTextField("", 20);
    pulseReplyText.setEditable(false);
    panel.add(pulseReplyText);

    panel.setLayout(boxlayout);

    return panel;
  }

  private JPanel createFitnessJPanel() {

    JPanel panel = new JPanel();

    BoxLayout boxlayout = new BoxLayout(panel, BoxLayout.LINE_AXIS);

    label = new JLabel("Choose a date");
    panel.add(label);
    panel.add(Box.createRigidArea(new Dimension(10, 0)));
    Calendar c = Calendar.getInstance();
    fitnessDatePicker = new JDatePicker(c);
    panel.add(fitnessDatePicker);
    panel.add(Box.createRigidArea(new Dimension(10, 0)));

    JButton button = new JButton("Check exercises");
    button.addActionListener(fitnessActionListener);
    panel.add(button);
    panel.add(Box.createRigidArea(new Dimension(10, 0)));

    fitnessResponseText = new JTextArea(null, null, 2, 10);
    fitnessResponseText.setEditable(false);
    panel.add(fitnessResponseText);

    panel.setLayout(boxlayout);

    return panel;
  }

  private void build() {

    JFrame frame = new JFrame("Smart Fitness");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    // Set the panel to add buttons
    JPanel panel = new JPanel();

    // Set the BoxLayout to be LINE_AXIS: from left to right
    BoxLayout boxlayout = new BoxLayout(panel, BoxLayout.PAGE_AXIS);

    panel.setLayout(boxlayout);

    // Set border for the panel
    panel.setBorder(new EmptyBorder(new Insets(20, 30, 20, 30)));

    panel.add(createAddWeightPanel());
    panel.add(createSendFoodTextPanel());
    panel.add(createSendHeartBeatPanel());
    panel.add(createFitnessJPanel());

    // Set size for the frame
    frame.setSize(400, 400);

    // Set the window to be visible as the default to be false
    frame.add(panel);
    frame.pack();
    frame.setVisible(true);
  }
}
