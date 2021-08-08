package ds.shared;

import com.google.protobuf.Timestamp;
import java.time.Instant;

/**
 * TimestampHelper class provides utilities to simplify work with protobuf
 * Timestamp class
 */
public class TimestampHelper {

  /**
   * @return rpotobuf Timestamp with current time
   */
  public static Timestamp currentTime() {
    Instant time = Instant.now();
    return Timestamp.newBuilder().setSeconds(time.getEpochSecond()).build();
  }

  /**
   * fromTimestamp converts Timestamp to the Java Instant type
   * @param time in protobuf timestamp format
   * @return the same time in Java instant format
   */
  public static Instant fromTimestamp(Timestamp time) {
    return Instant.ofEpochSecond(time.getSeconds());
  }
}
