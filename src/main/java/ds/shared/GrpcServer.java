package ds.shared;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;
import javax.jmdns.JmDNS;
import javax.jmdns.ServiceInfo;

/**
 * GrpcServer is a helper class that starts grpc server
 * and registers it using jmDNS
 */
public class GrpcServer {
  static final Logger logger = Logger.getLogger(GrpcServer.class.getName());
  private Server server;
  private final int port;
  private final GrpcService service;
  private ServiceInfo serviceInfo;

  /**
   * start creates a new server and starts it.
   * @param service GrpcService
   * @param port port to serve the service
   * @return running server
   * @throws UnknownHostException
   * @throws IOException
   */
  public static GrpcServer start(final GrpcService service, final int port)
      throws UnknownHostException, IOException {
    final GrpcServer server = new GrpcServer(service, port);
    server.start();
    return server;
  }

  private GrpcServer(final GrpcService service, final int port) {
    this.service = service;
    this.port = port;
  }

  private void registerService() throws UnknownHostException, IOException {
    // Create a JmDNS instance
    final JmDNS jmdns = JmDNS.create(InetAddress.getLocalHost());

    // Register a service
    serviceInfo =
        ServiceInfo.create("_http._tcp.local.", service.getServiceName(),
                           "grpc", port, service.getDescription());
    jmdns.registerService(serviceInfo);
  }

  private void unregisterService() throws UnknownHostException, IOException {
    final JmDNS jmdns = JmDNS.create(InetAddress.getLocalHost());
    // Use stderr here since the logger may have been reset by its JVM
    // shutdown hook.
    System.err.println("*** removing service registration");
    jmdns.unregisterService(serviceInfo);
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
        } catch (final InterruptedException e) {
          e.printStackTrace(System.err);
        }

        try {
          unregisterService();
        } catch (final UnknownHostException e) {
          e.printStackTrace(System.err);
        } catch (final IOException e) {
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
