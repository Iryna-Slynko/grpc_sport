package ds.mysmartgym;

import com.google.common.annotations.VisibleForTesting;
import com.google.protobuf.Message;
import com.google.type.Date;
import com.google.type.DateTime;
import ds.mysmartgym.*;
import io.grpc.Channel;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import io.grpc.stub.StreamObserver;
import java.io.IOException;
import java.time.DateTimeException;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SmartGymClient {
  private static final Logger logger =
      Logger.getLogger(SmartGymClient.class.getName());

  private final MySmartGymGrpc.MySmartGymBlockingStub blockingStub;
  private final MySmartGymGrpc.MySmartGymStub asyncStub;

  /**
   * Construct client for accessing RouteGuide server using the existing
   * channel.
   */
  public SmartGymClient(Channel channel) {
    blockingStub = MySmartGymGrpc.newBlockingStub(channel);
    asyncStub = MySmartGymGrpc.newStub(channel);
  }

  public void addWeight(float weight) {
    info("*** Send weight: {0}", weight);

    Weight request = Weight.newBuilder().setWeight(weight).build();
    blockingStub.weightUpdate(request);
  }

  public void getWeights() {
    Empty request = Empty.getDefaultInstance();
    info("*** Getting weights");
    blockingStub.getSavedWeights(request).forEachRemaining(savedWeight -> {
      info("Got {0} on {1}", savedWeight.getWeight(),
           Helper.fromCustomTime(savedWeight.getTime()));
    });
  }

  public void checkFood() {
    // asyncStub.heartTracking(responseObserver)
  }

  public void exercise() {
    StreamObserver<WorkoutIntensity> responseObserver =
        new StreamObserver<WorkoutIntensity>() {
          int[] intensities = new int[5];

          @Override
          public void onNext(WorkoutIntensity value) {
            System.out.println("receiving messages " + value);
            intensities[value.getZone()]++;
          }

          @Override
          public void onError(Throwable t) {
            t.printStackTrace();
          }

          @Override
          public void onCompleted() {
            int max = 0;
            int best = 0;
            for (int i = 0; i < intensities.length; i++) {
              if (intensities[i] >= max) {
                max = intensities[i];
                best = i;
              }
            }
            System.out.println("stream is completed ... spent most of the time "
                               + "doing exercises with " + best + " intensity");
          }
        };

    StreamObserver<HeartBeat> beats = asyncStub.heartTracking(responseObserver);
    Random r = new Random();
    for (int i = 0; i < 20; i++) {

      beats.onNext(
          HeartBeat.newBuilder().setPulse(r.nextInt(100 + i) + 50).build());
    }
    beats.onCompleted();
  }

  /** Issues several different requests and then exits. */
  public static void main(String[] args) throws InterruptedException {
    String target = "localhost:50051";
    if (args.length > 0) {
      if ("--help".equals(args[0])) {
        System.err.println("Usage: [target]");
        System.err.println("");
        System.err.println("  target  The server to connect to. Defaults to " +
                           target);
        System.exit(1);
      }
      target = args[0];
    }
    ManagedChannel channel =
        ManagedChannelBuilder.forTarget(target).usePlaintext().build();
    try {
      SmartGymClient client = new SmartGymClient(channel);
      client.addWeight(58);
      client.getWeights();
      client.exercise();
    } catch (Exception exception) {
      System.err.println(exception.toString());
    } finally {
      channel.shutdownNow().awaitTermination(600, TimeUnit.SECONDS);
    }
  }

  private void info(String msg, Object... params) {
    logger.log(Level.INFO, msg, params);
  }
}
