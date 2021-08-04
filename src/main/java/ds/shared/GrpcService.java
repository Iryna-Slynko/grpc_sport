package ds.shared;

import io.grpc.BindableService;

public interface GrpcService extends BindableService {
  public String getServiceName();
  public String getDescription();
}
