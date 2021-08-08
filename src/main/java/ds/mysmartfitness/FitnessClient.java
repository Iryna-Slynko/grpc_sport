package ds.mysmartfitness;

import com.google.protobuf.Timestamp;
import ds.shared.DNSLookup;
import io.grpc.Channel;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import java.util.Calendar;
import java.util.Iterator;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Client for MyFitness service
 */
public class FitnessClient {
  private static final Logger logger =
      Logger.getLogger(FitnessClient.class.getName());

  private final MySmartFitnessGrpc.MySmartFitnessBlockingStub blockingStub;
  private final MySmartFitnessGrpc.MySmartFitnessStub asyncStub;

  private ManagedChannel channel;

  public ManagedChannel getChannel() { return channel; }

  /**
   * FitnessClient looks up for fitness service using jmdns and then connects to
   * it
   */
  public FitnessClient() {
    String target = DNSLookup.getEndpoint("fitness");

    channel = ManagedChannelBuilder.forTarget(target).usePlaintext().build();
    blockingStub = MySmartFitnessGrpc.newBlockingStub(channel);
    asyncStub = MySmartFitnessGrpc.newStub(channel);
  }

  private void getExercises() {
    Calendar c = Calendar.getInstance();
    Random r = new Random();
    c.set(2021, r.nextInt(5) + 3, r.nextInt(20) + 5);
    logger.log(Level.INFO, "Exercises for {0}", c.getTime());
    getExercises(c).forEachRemaining(activity -> {
      logger.log(Level.INFO, "- {0}", activity.getActivity());
    });
  }

  /**
   * getExercises requests exercises from MyFitness Service for a given date
   * @param c date in Java format
   * @return list of activities
   */
  public Iterator<Activity> getExercises(Calendar c) {
    Timestamp request = Timestamp.newBuilder()
                            .setSeconds(c.toInstant().getEpochSecond())
                            .build();
    return blockingStub.activityTracking(request);
  }

  public static void main(String[] args) throws InterruptedException {
    FitnessClient client = null;
    try {
      client = new FitnessClient();
      client.getExercises();
    } catch (Exception exception) {
      System.err.println(exception.toString());
    } finally {
      if (client != null) {
        client.getChannel().shutdownNow().awaitTermination(600,
                                                           TimeUnit.SECONDS);
      }
    }
  }
}
