package ds.mysmartfitness;

import com.google.protobuf.Timestamp;
import ds.mysmartfitness.MySmartFitnessGrpc.MySmartFitnessImplBase;
import ds.shared.Helper;
import io.grpc.stub.StreamObserver;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;


class MySmartFitnessImpl extends MySmartFitnessImplBase {
  @Override
  public void activityTracking(Timestamp request,
                               StreamObserver<Activity> responseObserver) {
    System.err.println("Getting tracking activities");
    Calendar cal = Calendar.getInstance();
    cal.setTime(Date.from(Helper.fromCustomTime(request)));
    int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
    Random r = new Random();
    switch (dayOfWeek) {
    case Calendar.MONDAY:
    case Calendar.WEDNESDAY:
    case Calendar.FRIDAY: {
      Activity a = Activity.newBuilder()
                       .setActivity("Strength")
                       .setDuration(r.nextInt(20) + 20)
                       .build();
      responseObserver.onNext(a);
      break;
    }
    case Calendar.TUESDAY:
    case Calendar.SATURDAY: {
      Activity a = Activity.newBuilder()
                       .setActivity("Cardio")
                       .setDuration(r.nextInt(30) + 30)
                       .build();
      responseObserver.onNext(a);
      break;
    }
    case Calendar.SUNDAY: {
      Activity a = Activity.newBuilder()
                       .setActivity("Running")
                       .setSteps(r.nextInt(15000) + 5000)
                       .build();
      responseObserver.onNext(a);
      break;
    }
    }
    if (r.nextBoolean()) {
      responseObserver.onNext(Activity.newBuilder()
                                  .setActivity("Yoga")
                                  .setDuration(r.nextInt(60) + 10)
                                  .build());
    }

    responseObserver.onCompleted();
  }
}