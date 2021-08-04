package ds.shared;

import io.grpc.BindableService;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public class GrpcServer {
  static final Logger logger = Logger.getLogger(GrpcServer.class.getName());
  private Server server;
  private int port;
  private BindableService service;

  public static GrpcServer start(BindableService service, int port)
      throws IOException {
    GrpcServer server = new GrpcServer(service, port);
    server.start();
    return server;
  }

  private GrpcServer(BindableService service, int port) {
    this.service = service;
    this.port = port;
  }

  private void start() throws IOException {
    server = ServerBuilder.forPort(port).addService(service).build().start();
    logger.info("Server started, listening on " + port);
    Runtime.getRuntime().addShutdownHook(new Thread() {
      @Override
      public void run() {
        // Use stderr here since the logger may have been reset by its JVM
        // shutdown hook.
        System.err.println(
            "*** shutting down gRPC server since JVM is shutting down");
        try {
          GrpcServer.this.stop();
        } catch (InterruptedException e) {
          e.printStackTrace(System.err);
        }
        System.err.println("*** server shut down");
      }
    });
  }

  private void stop() throws InterruptedException {
    if (server != null) {
      server.shutdown().awaitTermination(600, TimeUnit.SECONDS);
    }
  }

  /**
   * Await termination on the main thread since the grpc library uses daemon
   * threads.
   */
  public void blockUntilShutdown() throws InterruptedException {
    if (server != null) {
      server.awaitTermination();
    }
  }
}
