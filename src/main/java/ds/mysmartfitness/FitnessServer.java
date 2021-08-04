package ds.mysmartfitness;

import ds.shared.GrpcServer;
import java.io.IOException;

public class FitnessServer {
  public static void main(String[] args)
      throws IOException, InterruptedException {
    final GrpcServer server = GrpcServer.start(new MySmartFitnessImpl(), 50052);
    server.blockUntilShutdown();
  }
}
