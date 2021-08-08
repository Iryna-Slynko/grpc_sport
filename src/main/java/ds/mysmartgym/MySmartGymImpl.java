package ds.mysmartgym;

import com.google.protobuf.Empty;
import ds.mysmartgym.MySmartGymGrpc.MySmartGymImplBase;
import ds.mysmartgym.WorkoutIntensity.Builder;
import ds.shared.GrpcService;
import ds.shared.TimestampHelper;
import io.grpc.stub.StreamObserver;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * MySmartGymImpl is the implementation of MySmartGym service
 */
class MySmartGymImpl extends MySmartGymImplBase implements GrpcService {
  private final class ConsumerFoodObserver
      implements StreamObserver<ConsumedFoodRequest> {
    private final StreamObserver<NutrientsReply> responseObserver;

    private ConsumerFoodObserver(
        StreamObserver<NutrientsReply> responseObserver) {
      this.responseObserver = responseObserver;
    }

    @Override
    public void onNext(final ConsumedFoodRequest request) {
      final Integer proteins =
          getProtein(request.getFood(), request.getQuantity());
      final NutrientsReply proteinReply =
          NutrientsReply.newBuilder()
              .setMessage("Protein: " + proteins.toString() + "g")
              .build();

      responseObserver.onNext(proteinReply);

      final Integer carbs = getCarbs(request.getFood(), request.getQuantity());
      final NutrientsReply carbsReply =
          NutrientsReply.newBuilder()
              .setMessage("Carbohydrate: " + carbs.toString() + "g")
              .build();

      responseObserver.onNext(carbsReply);
    }

    private Integer getCarbs(final String food, final int quantity) {
      if (food == "Sugar") {
        return quantity;
      } else if (food == "Chicken") {
        return quantity / 6;
      } else {
        return quantity / 4;
      }
    }

    private Integer getProtein(final String food, final int quantity) {
      if (food == "Sugar") {
        return 0;
      } else if (food == "Chicken") {
        return quantity / 3;
      } else {
        return quantity / 7;
      }
    }

    @Override
    public void onError(final Throwable t) {
      responseObserver.onError(t);
    }

    @Override
    public void onCompleted() {
      responseObserver.onCompleted();
    }
  }

  private final class HeartBeatToIntensityObserver
      implements StreamObserver<HeartBeat> {
    private final StreamObserver<WorkoutIntensity> responseObserver;

    private HeartBeatToIntensityObserver(
        final StreamObserver<WorkoutIntensity> responseObserver) {
      this.responseObserver = responseObserver;
    }

    @Override
    public void onNext(final HeartBeat request) {
      final Calendar cal = Calendar.getInstance();
      cal.setTime(Date.from(Instant.now()));
      final Builder intensity = WorkoutIntensity.newBuilder()
                                    .setHour(cal.get(Calendar.HOUR))
                                    .setMinutes(cal.get(Calendar.MINUTE));
      final int pulse = request.getPulse();

      // Assuming the 100% intensity pulse is 150. use formula to calculate
      // zones: 1 - up to 50% 2 - 50-65% 3 - 65-75% 4 - 75-85% 5 - 85 and higher
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
    public void onError(final Throwable t) {
      responseObserver.onError(t);
    }

    @Override
    public void onCompleted() {
      responseObserver.onCompleted();
    }
  }

  private final List<SavedWeight> savedWeights = new ArrayList<SavedWeight>();

  @Override
  public void weightUpdate(final Weight request,
                           final StreamObserver<Empty> responseObserver) {
    System.err.println("Saving weight");

    final SavedWeight savedWeight = SavedWeight.newBuilder()
                                        .setWeight(request)
                                        .setTime(TimestampHelper.currentTime())
                                        .build();
    savedWeights.add(savedWeight);

    final Empty reply = Empty.newBuilder().build();
    responseObserver.onNext(reply);
    responseObserver.onCompleted();
  }

  @Override
  public void
  getSavedWeights(final Empty request,
                  final StreamObserver<SavedWeight> responseObserver) {
    System.err.println("Getting saved weights");
    savedWeights.forEach(responseObserver::onNext);
    responseObserver.onCompleted();
  }

  @Override
  public StreamObserver<ConsumedFoodRequest>
  consumedFoodStreaming(final StreamObserver<NutrientsReply> responseObserver) {
    System.err.println("Processing consumed food");
    return new ConsumerFoodObserver(responseObserver);
  }

  @Override
  public StreamObserver<HeartBeat>
  heartTracking(final StreamObserver<WorkoutIntensity> responseObserver) {
    System.err.println("Processing heartbeats");
    return new HeartBeatToIntensityObserver(responseObserver);
  }

  @Override
  public String getServiceName() {
    return "smartgym";
  }

  @Override
  public String getDescription() {

    return "service to share the fitness information with trainer";
  }
}