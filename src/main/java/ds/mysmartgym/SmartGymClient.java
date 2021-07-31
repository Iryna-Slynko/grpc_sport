package ds.mysmartgym;

import com.google.common.annotations.VisibleForTesting;
import com.google.protobuf.Message;
import io.grpc.Channel;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import io.grpc.stub.StreamObserver;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import ds.mysmartgym.*;

public class SmartGymClient {
    private static final Logger logger = Logger.getLogger(SmartGymClient.class.getName());

    private final MySmartGymGrpc.MySmartGymBlockingStub blockingStub;
    private final MySmartGymGrpc.MySmartGymStub asyncStub;

  
    /** Construct client for accessing RouteGuide server using the existing channel. */
    public SmartGymClient(Channel channel) {
      blockingStub = MySmartGymGrpc.newBlockingStub(channel);
      asyncStub = MySmartGymGrpc.newStub(channel);
    }

    public void addWeight(float weight) {
      logger.info(String.format("*** Send weight: {0}", weight));

      Weight request = Weight.newBuilder().setWeight(weight).build();
      blockingStub.weightUpdate(request);
    }

    /** Issues several different requests and then exits. */
    public static void main(String[] args) throws InterruptedException {
      String target = "localhost:50051";
      if (args.length > 0) {
        if ("--help".equals(args[0])) {
          System.err.println("Usage: [target]");
          System.err.println("");
          System.err.println("  target  The server to connect to. Defaults to " + target);
          System.exit(1);
        }
        target = args[0];
      }
      ManagedChannel channel = ManagedChannelBuilder.forTarget(target).usePlaintext().build();
      try {
        SmartGymClient client = new SmartGymClient(channel);
        client.addWeight(58);
        
      } finally {
        channel.shutdownNow().awaitTermination(5, TimeUnit.SECONDS);
      }
    }
 
}
