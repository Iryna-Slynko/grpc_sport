package ds.fitclient;

import ds.mysmartfitness.FitnessClient;
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
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import org.jdatepicker.JDatePicker;

public class Controller {
  private JTextField entry1, reply1;
  private JTextField entry2, reply2;
  private JTextField entry3, reply3;
  private JTextArea fitnessResponseText;
  private JDatePicker fitnessDatePicker;
  private SmartGymActionListener smartGymActionListener;
  private FitnessActionListener fitnessActionListener;
  private JLabel label;

  private class SmartGymActionListener implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {}
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

  private JPanel getService1JPanel() {

    JPanel panel = new JPanel();

    BoxLayout boxlayout = new BoxLayout(panel, BoxLayout.X_AXIS);

    JLabel label = new JLabel("Enter value");
    panel.add(label);
    panel.add(Box.createRigidArea(new Dimension(10, 0)));
    entry1 = new JTextField("", 10);
    panel.add(entry1);
    panel.add(Box.createRigidArea(new Dimension(10, 0)));

    JButton button = new JButton("Invoke Service 1");
    button.addActionListener(smartGymActionListener);
    panel.add(button);
    panel.add(Box.createRigidArea(new Dimension(10, 0)));

    reply1 = new JTextField("", 10);
    reply1.setEditable(false);
    panel.add(reply1);

    panel.setLayout(boxlayout);

    return panel;
  }

  private JPanel getService2JPanel() {

    JPanel panel = new JPanel();

    BoxLayout boxlayout = new BoxLayout(panel, BoxLayout.X_AXIS);

    JLabel label = new JLabel("Enter value");
    panel.add(label);
    panel.add(Box.createRigidArea(new Dimension(10, 0)));
    entry2 = new JTextField("", 10);
    panel.add(entry2);
    panel.add(Box.createRigidArea(new Dimension(10, 0)));

    JButton button = new JButton("Invoke Service 2");
    button.addActionListener(smartGymActionListener);
    panel.add(button);
    panel.add(Box.createRigidArea(new Dimension(10, 0)));

    reply2 = new JTextField("", 10);
    reply2.setEditable(false);
    panel.add(reply2);

    panel.setLayout(boxlayout);

    return panel;
  }

  private JPanel getService3JPanel() {

    JPanel panel = new JPanel();

    BoxLayout boxlayout = new BoxLayout(panel, BoxLayout.X_AXIS);

    JLabel label = new JLabel("Enter value");
    panel.add(label);
    panel.add(Box.createRigidArea(new Dimension(10, 0)));
    entry3 = new JTextField("", 10);
    panel.add(entry3);
    panel.add(Box.createRigidArea(new Dimension(10, 0)));

    JButton button = new JButton("Invoke Service 3");
    button.addActionListener(smartGymActionListener);
    panel.add(button);
    panel.add(Box.createRigidArea(new Dimension(10, 0)));

    reply3 = new JTextField("", 10);
    reply3.setEditable(false);
    panel.add(reply3);

    panel.setLayout(boxlayout);

    return panel;
  }

  private JPanel createFitnessJPanel() {

    JPanel panel = new JPanel();

    BoxLayout boxlayout = new BoxLayout(panel, BoxLayout.X_AXIS);

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

    // Set the BoxLayout to be X_AXIS: from left to right
    BoxLayout boxlayout = new BoxLayout(panel, BoxLayout.Y_AXIS);

    panel.setLayout(boxlayout);

    // Set border for the panel
    panel.setBorder(new EmptyBorder(new Insets(50, 100, 50, 100)));

    panel.add(getService1JPanel());
    panel.add(getService2JPanel());
    panel.add(getService3JPanel());
    panel.add(createFitnessJPanel());

    // Set size for the frame
    frame.setSize(400, 400);

    // Set the window to be visible as the default to be false
    frame.add(panel);
    frame.pack();
    frame.setVisible(true);
  }
}
