package ds.mysmartgym;

import ds.mysmartgym.MySmartGymGrpc.MySmartGymImplBase;
import ds.mysmartgym.WorkoutIntensity.Builder;
import io.grpc.stub.StreamObserver;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;

class MySmartGymImpl extends MySmartGymImplBase {
  private final class HeartBeatToIntensityObserver
      implements StreamObserver<HeartBeat> {
    private final StreamObserver<WorkoutIntensity> responseObserver;

    private HeartBeatToIntensityObserver(
        StreamObserver<WorkoutIntensity> responseObserver) {
      this.responseObserver = responseObserver;
    }

    @Override
    public void onNext(HeartBeat request) {
      Calendar cal = Calendar.getInstance();
      cal.setTime(Date.from(Instant.now()));
      Builder intensity = WorkoutIntensity.newBuilder()
                              .setHour(cal.get(Calendar.HOUR))
                              .setMinutes(cal.get(Calendar.MINUTE));
      int pulse = request.getPulse();
      SmartGymServer.logger.log(Level.INFO, "Pulse is {0}", pulse);
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
                                  .setTime(Helper.currentTime())
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
        Integer proteins = getProtein(request.getFood(), request.getQuantity());
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