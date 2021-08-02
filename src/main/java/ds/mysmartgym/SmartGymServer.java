package ds.mysmartgym;

import com.google.protobuf.Timestamp;
import com.google.type.DateTime;
import ds.mysmartgym.MySmartGymGrpc.MySmartGymImplBase;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;
import java.io.IOException;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.TimeUnit;
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
  }

  private static Timestamp currentTime() {
    Instant time = Instant.now();
    return Timestamp.newBuilder().setSeconds(time.getEpochSecond()).build();
  }
}
