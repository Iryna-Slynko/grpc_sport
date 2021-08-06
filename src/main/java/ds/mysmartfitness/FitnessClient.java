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

public class FitnessClient {
  private static final Logger logger =
      Logger.getLogger(FitnessClient.class.getName());

  private final MySmartFitnessGrpc.MySmartFitnessBlockingStub blockingStub;
  private final MySmartFitnessGrpc.MySmartFitnessStub asyncStub;
  public FitnessClient(Channel channel) {
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

  public Iterator<Activity> getExercises(Calendar c) {
    Timestamp request = Timestamp.newBuilder()
                            .setSeconds(c.toInstant().getEpochSecond())
                            .build();
    return blockingStub.activityTracking(request);
  }

  public static void main(String[] args) throws InterruptedException {
    String target = DNSLookup.getEndpoint("fitness");

    ManagedChannel channel =
        ManagedChannelBuilder.forTarget(target).usePlaintext().build();
    try {
      FitnessClient client = new FitnessClient(channel);
      client.getExercises();
    } catch (Exception exception) {
      System.err.println(exception.toString());
    } finally {
      channel.shutdownNow().awaitTermination(600, TimeUnit.SECONDS);
    }
  }
}
