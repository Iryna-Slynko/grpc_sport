package ds.shared;

import io.grpc.BindableService;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;
import javax.jmdns.JmDNS;
import javax.jmdns.ServiceInfo;

public class GrpcServer {
  static final Logger logger = Logger.getLogger(GrpcServer.class.getName());
  private Server server;
  private int port;
  private GrpcService service;

  public static GrpcServer start(GrpcService service, int port)
      throws UnknownHostException, IOException {
    GrpcServer server = new GrpcServer(service, port);
    server.start();
    return server;
  }

  private GrpcServer(GrpcService service, int port) {
    this.service = service;
    this.port = port;
  }

  private void registerService() throws UnknownHostException, IOException {
    // Create a JmDNS instance
    JmDNS jmdns = JmDNS.create(InetAddress.getLocalHost());

    // Register a service
    ServiceInfo serviceInfo =
        ServiceInfo.create("_http._tcp.local.", service.getServiceName(), port,
                           service.getDescription());
    jmdns.registerService(serviceInfo);
  }

  private void start() throws UnknownHostException, IOException {
    server = ServerBuilder.forPort(port).addService(service).build().start();
    logger.info("Server started, listening on " + port);
    registerService();
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
