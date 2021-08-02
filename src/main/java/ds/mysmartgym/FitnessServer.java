package ds.mysmartgym;

import java.io.IOException;

public class FitnessServer {
  public static void main(String[] args)
      throws IOException, InterruptedException {
    final SmartGymServer server =
        SmartGymServer.start(new MySmartFitnessImpl(), 50052);
    server.blockUntilShutdown();
  }
}
