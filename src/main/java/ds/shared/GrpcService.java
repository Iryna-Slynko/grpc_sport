package ds.shared;

import io.grpc.BindableService;

/**
 * GrpcService is the interface that can be used by grpc to start grpc server
 * and jmdns to register service
 */
public interface GrpcService extends BindableService {
  public String getServiceName();
  public String getDescription();
}
