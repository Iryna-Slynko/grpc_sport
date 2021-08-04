package ds.shared;

import com.google.protobuf.Timestamp;
import java.time.Instant;

public class Helper {

  public static Timestamp currentTime() {
    Instant time = Instant.now();
    return Timestamp.newBuilder().setSeconds(time.getEpochSecond()).build();
  }

  public static Instant fromCustomTime(Timestamp time) {
    return Instant.ofEpochSecond(time.getSeconds());
  }
}
