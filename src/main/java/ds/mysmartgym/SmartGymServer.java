package ds.mysmartgym;

import ds.shared.GrpcServer;
import java.io.IOException;

public class SmartGymServer {
  public static void main(String[] args)
      throws IOException, InterruptedException {
    final GrpcServer server = GrpcServer.start(new MySmartGymImpl(), 50051);
    server.blockUntilShutdown();
  }
}
