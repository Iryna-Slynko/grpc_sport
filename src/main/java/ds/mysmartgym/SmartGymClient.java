package ds.mysmartgym;

import com.google.protobuf.Empty;
import ds.shared.DNSLookup;
import ds.shared.TimestampHelper;
import io.grpc.Channel;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * SmartGymClient is a test client to test SmartGym service
 */
public class SmartGymClient {
  private static final Logger logger =
      Logger.getLogger(SmartGymClient.class.getName());

  private final MySmartGymGrpc.MySmartGymBlockingStub blockingStub;
  private final MySmartGymGrpc.MySmartGymStub asyncStub;

  /**
   * Construct client for accessing RouteGuide server using the existing
   * channel.
   */
  public SmartGymClient(final Channel channel) {
    blockingStub = MySmartGymGrpc.newBlockingStub(channel);
    asyncStub = MySmartGymGrpc.newStub(channel);
  }

  public void addWeight(final float weight) {
    info("*** Send weight: {0}", weight);

    final Weight request = Weight.newBuilder().setWeight(weight).build();
    blockingStub.weightUpdate(request);
  }

  public void getWeights() {
    final Empty request = Empty.getDefaultInstance();
    info("*** Getting weights");
    blockingStub.getSavedWeights(request).forEachRemaining(savedWeight -> {
      info("Got {0} on {1}", savedWeight.getWeight(),
           TimestampHelper.fromTimestamp(savedWeight.getTime()));
    });
  }

  public void checkFood() {
    final StreamObserver<NutrientsReply> responseObserver =
        new StreamObserver<NutrientsReply>() {
          int count = 0;

          @Override
          public void onNext(final NutrientsReply value) {
            System.out.println(value.getMessage());
            ;
            count++;
          }

          @Override
          public void onError(final Throwable t) {
            t.printStackTrace();
          }

          @Override
          public void onCompleted() {
            System.out.println("stream is completed ... got " + count +
                               " messages");
          }
        };

    // send several randomly generated items
    final StreamObserver<ConsumedFoodRequest> food =
        asyncStub.consumedFoodStreaming(responseObserver);
    final Random r = new Random();
    food.onNext(ConsumedFoodRequest.newBuilder()
                    .setFood("Chicken")
                    .setQuantity(r.nextInt(300) + 50)
                    .build());
    food.onNext(ConsumedFoodRequest.newBuilder()
                    .setFood("Apple")
                    .setQuantity(r.nextInt(100) + 50)
                    .build());
    food.onNext(ConsumedFoodRequest.newBuilder()
                    .setFood("Yoghurt")
                    .setQuantity(r.nextInt(400) + 100)
                    .build());
    food.onCompleted();
  }

  public void exercise() {
    // this observer calculate each time observer reports the new intensity and
    // then picks up the intensity that was received the most. if several
    // intensities were reported the same number of times, it chooses the
    // highest one
    final StreamObserver<WorkoutIntensity> responseObserver =
        new StreamObserver<WorkoutIntensity>() {
          int[] intensities = new int[6];

          @Override
          public void onNext(final WorkoutIntensity value) {
            System.out.println("receiving messages " + value);
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

    final StreamObserver<HeartBeat> beats =
        asyncStub.heartTracking(responseObserver);
    // send random data 20 times. Increase the maximum possible intensity with
    // each request
    final Random r = new Random();
    for (int i = 0; i < 20; i++) {

      beats.onNext(
          HeartBeat.newBuilder().setPulse(r.nextInt(100 + i) + 50).build());
    }
    beats.onCompleted();
  }

  /** Issues several different requests and then exits. */
  public static void main(final String[] args) throws InterruptedException {
    final String target = DNSLookup.getEndpoint("smartgym");
    if (target == "") {
      System.err.println("Service not found");
      System.exit(1);
    }
    final ManagedChannel channel =
        ManagedChannelBuilder.forTarget(target).usePlaintext().build();
    try {
      final SmartGymClient client = new SmartGymClient(channel);
      client.addWeight(58);
      client.getWeights();
      client.exercise();
      client.checkFood();
    } catch (final Exception exception) {
      System.err.println(exception.toString());
    } finally {
      Thread.sleep(new Random().nextInt(1000) + 500);
      channel.shutdownNow().awaitTermination(600, TimeUnit.SECONDS);
    }
  }

  private void info(final String msg, final Object... params) {
    logger.log(Level.INFO, msg, params);
  }
}
