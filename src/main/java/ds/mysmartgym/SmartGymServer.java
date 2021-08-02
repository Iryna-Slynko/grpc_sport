package ds.mysmartgym;

import com.google.protobuf.Timestamp;
import com.google.type.DateTime;
import ds.mysmartgym.MySmartGymGrpc.MySmartGymImplBase;
import ds.mysmartgym.WorkoutIntensity.Builder;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.netty.shaded.io.netty.handler.codec.ReplayingDecoder;
import io.grpc.stub.StreamObserver;
import java.io.IOException;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SmartGymServer {
  private static final Logger logger =
      Logger.getLogger(SmartGymServer.class.getName());

  private Server server;

  private void start() throws IOException {
    /* The port on which the server should run */
    int port = 50051;
    server = ServerBuilder.forPort(port)
                 .addService(new MySmartGymImpl())
                 .build()
                 .start();
    logger.info("Server started, listening on " + port);
    Runtime.getRuntime().addShutdownHook(new Thread() {
      @Override
      public void run() {
        // Use stderr here since the logger may have been reset by its JVM
        // shutdown hook.
        System.err.println(
            "*** shutting down gRPC server since JVM is shutting down");
        try {
          SmartGymServer.this.stop();
        } catch (InterruptedException e) {
          e.printStackTrace(System.err);
        }
        System.err.println("*** server shut down");
      }
    });
  }

  private void stop() throws InterruptedException {
    if (server != null) {
      server.shutdown().awaitTermination(30, TimeUnit.SECONDS);
    }
  }

  /**
   * Await termination on the main thread since the grpc library uses daemon
   * threads.
   */
  private void blockUntilShutdown() throws InterruptedException {
    if (server != null) {
      server.awaitTermination();
    }
  }
  public static void main(String[] args)
      throws IOException, InterruptedException {
    final SmartGymServer server = new SmartGymServer();
    server.start();
    server.blockUntilShutdown();
  }

  static class MySmartGymImpl extends MySmartGymImplBase {
    private final class HeartBeatToIntensityObserver
        implements StreamObserver<HeartBeat> {
      private final StreamObserver<WorkoutIntensity> responseObserver;

      private HeartBeatToIntensityObserver(
          StreamObserver<WorkoutIntensity> responseObserver) {
        this.responseObserver = responseObserver;
      }

      @Override
      public void onNext(HeartBeat request) {
        Date i = Date.from(Instant.now());
        Builder intensity = WorkoutIntensity.newBuilder()
                                .setHour(i.getHours())
                                .setMinutes(i.getMinutes());
        int pulse = request.getPulse();
        logger.log(Level.INFO, "Pulse is {0}", pulse);
        if (pulse < 75) {
          intensity.setZone(1);
        } else if (pulse < 100) {
          intensity.setZone(2);
        } else if (pulse < 120) {
          intensity.setZone(3);
        } else if (pulse < 140) {
          intensity.setZone(4);
        } else {
          intensity.setZone(5);
        }
        responseObserver.onNext(intensity.build());
      }

      @Override
      public void onError(Throwable t) {
        responseObserver.onError(t);
      }

      @Override
      public void onCompleted() {
        responseObserver.onCompleted();
      }
    }

    List<SavedWeight> savedWeights = new ArrayList<SavedWeight>();

    @Override
    public void weightUpdate(Weight request,
                             StreamObserver<Empty> responseObserver) {
      SavedWeight savedWeight = SavedWeight.newBuilder()
                                    .setWeight(request)
                                    .setTime(currentTime())
                                    .build();
      savedWeights.add(savedWeight);
      Empty reply = Empty.newBuilder().build();
      responseObserver.onNext(reply);
      responseObserver.onCompleted();
    }

    @Override
    public void getSavedWeights(Empty request,
                                StreamObserver<SavedWeight> responseObserver) {
      System.err.println("Saved weights");
      savedWeights.forEach(responseObserver::onNext);
      responseObserver.onCompleted();
    }

    @Override
    public StreamObserver<ConsumedFoodRequest>
    consumedFoodStreaming(StreamObserver<NutrientsReply> responseObserver) {
      return new StreamObserver<ConsumedFoodRequest>() {
        @Override
        public void onNext(ConsumedFoodRequest request) {
          Integer proteins =
              getProtein(request.getFood(), request.getQuantity());
          NutrientsReply proteinReply =
              NutrientsReply.newBuilder()
                  .setMessage("Protein: " + proteins.toString() + "g")
                  .build();

          responseObserver.onNext(proteinReply);

          Integer carbs = getCarbs(request.getFood(), request.getQuantity());
          NutrientsReply carbsReply =
              NutrientsReply.newBuilder()
                  .setMessage("Carbohydrate: " + carbs.toString() + "g")
                  .build();

          responseObserver.onNext(carbsReply);
        }

        private Integer getCarbs(String food, int quantity) {
          if (food == "Sugar") {
            return quantity;
          } else if (food == "Chicken") {
            return quantity / 6;
          } else {
            return quantity / 4;
          }
        }

        private Integer getProtein(String food, int quantity) {
          if (food == "Sugar") {
            return 0;
          } else if (food == "Chicken") {
            return quantity / 3;
          } else {
            return quantity / 7;
          }
        }

        @Override
        public void onError(Throwable t) {
          responseObserver.onError(t);
        }

        @Override
        public void onCompleted() {
          responseObserver.onCompleted();
        }
      };
    }

    @Override
    public StreamObserver<HeartBeat>
    heartTracking(StreamObserver<WorkoutIntensity> responseObserver) {
      return new HeartBeatToIntensityObserver(responseObserver);
    }
  }

  private static Timestamp currentTime() {
    Instant time = Instant.now();
    return Timestamp.newBuilder().setSeconds(time.getEpochSecond()).build();
  }
}
