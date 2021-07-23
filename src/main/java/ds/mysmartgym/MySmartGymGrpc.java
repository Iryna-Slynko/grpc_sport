package ds.mysmartgym;

import static io.grpc.MethodDescriptor.generateFullMethodName;
import static io.grpc.stub.ClientCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ClientCalls.asyncClientStreamingCall;
import static io.grpc.stub.ClientCalls.asyncServerStreamingCall;
import static io.grpc.stub.ClientCalls.asyncUnaryCall;
import static io.grpc.stub.ClientCalls.blockingServerStreamingCall;
import static io.grpc.stub.ClientCalls.blockingUnaryCall;
import static io.grpc.stub.ClientCalls.futureUnaryCall;
import static io.grpc.stub.ServerCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ServerCalls.asyncClientStreamingCall;
import static io.grpc.stub.ServerCalls.asyncServerStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnaryCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.15.0)",
    comments = "Source: mysmartgym.proto")
public final class MySmartGymGrpc {

  private MySmartGymGrpc() {}

  public static final String SERVICE_NAME = "mysmartgym.MySmartGym";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<ds.mysmartgym.Weight,
      ds.mysmartgym.Empty> getWeightUpdateMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "WeightUpdate",
      requestType = ds.mysmartgym.Weight.class,
      responseType = ds.mysmartgym.Empty.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<ds.mysmartgym.Weight,
      ds.mysmartgym.Empty> getWeightUpdateMethod() {
    io.grpc.MethodDescriptor<ds.mysmartgym.Weight, ds.mysmartgym.Empty> getWeightUpdateMethod;
    if ((getWeightUpdateMethod = MySmartGymGrpc.getWeightUpdateMethod) == null) {
      synchronized (MySmartGymGrpc.class) {
        if ((getWeightUpdateMethod = MySmartGymGrpc.getWeightUpdateMethod) == null) {
          MySmartGymGrpc.getWeightUpdateMethod = getWeightUpdateMethod = 
              io.grpc.MethodDescriptor.<ds.mysmartgym.Weight, ds.mysmartgym.Empty>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "mysmartgym.MySmartGym", "WeightUpdate"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  ds.mysmartgym.Weight.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  ds.mysmartgym.Empty.getDefaultInstance()))
                  .setSchemaDescriptor(new MySmartGymMethodDescriptorSupplier("WeightUpdate"))
                  .build();
          }
        }
     }
     return getWeightUpdateMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static MySmartGymStub newStub(io.grpc.Channel channel) {
    return new MySmartGymStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static MySmartGymBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new MySmartGymBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static MySmartGymFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new MySmartGymFutureStub(channel);
  }

  /**
   */
  public static abstract class MySmartGymImplBase implements io.grpc.BindableService {

    /**
     * <pre>
     * Sends weight
     * </pre>
     */
    public void weightUpdate(ds.mysmartgym.Weight request,
        io.grpc.stub.StreamObserver<ds.mysmartgym.Empty> responseObserver) {
      asyncUnimplementedUnaryCall(getWeightUpdateMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getWeightUpdateMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                ds.mysmartgym.Weight,
                ds.mysmartgym.Empty>(
                  this, METHODID_WEIGHT_UPDATE)))
          .build();
    }
  }

  /**
   */
  public static final class MySmartGymStub extends io.grpc.stub.AbstractStub<MySmartGymStub> {
    private MySmartGymStub(io.grpc.Channel channel) {
      super(channel);
    }

    private MySmartGymStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected MySmartGymStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new MySmartGymStub(channel, callOptions);
    }

    /**
     * <pre>
     * Sends weight
     * </pre>
     */
    public void weightUpdate(ds.mysmartgym.Weight request,
        io.grpc.stub.StreamObserver<ds.mysmartgym.Empty> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getWeightUpdateMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class MySmartGymBlockingStub extends io.grpc.stub.AbstractStub<MySmartGymBlockingStub> {
    private MySmartGymBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private MySmartGymBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected MySmartGymBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new MySmartGymBlockingStub(channel, callOptions);
    }

    /**
     * <pre>
     * Sends weight
     * </pre>
     */
    public ds.mysmartgym.Empty weightUpdate(ds.mysmartgym.Weight request) {
      return blockingUnaryCall(
          getChannel(), getWeightUpdateMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class MySmartGymFutureStub extends io.grpc.stub.AbstractStub<MySmartGymFutureStub> {
    private MySmartGymFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private MySmartGymFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected MySmartGymFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new MySmartGymFutureStub(channel, callOptions);
    }

    /**
     * <pre>
     * Sends weight
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<ds.mysmartgym.Empty> weightUpdate(
        ds.mysmartgym.Weight request) {
      return futureUnaryCall(
          getChannel().newCall(getWeightUpdateMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_WEIGHT_UPDATE = 0;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final MySmartGymImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(MySmartGymImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_WEIGHT_UPDATE:
          serviceImpl.weightUpdate((ds.mysmartgym.Weight) request,
              (io.grpc.stub.StreamObserver<ds.mysmartgym.Empty>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  private static abstract class MySmartGymBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    MySmartGymBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return ds.mysmartgym.SmartGymService.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("MySmartGym");
    }
  }

  private static final class MySmartGymFileDescriptorSupplier
      extends MySmartGymBaseDescriptorSupplier {
    MySmartGymFileDescriptorSupplier() {}
  }

  private static final class MySmartGymMethodDescriptorSupplier
      extends MySmartGymBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    MySmartGymMethodDescriptorSupplier(String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (MySmartGymGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new MySmartGymFileDescriptorSupplier())
              .addMethod(getWeightUpdateMethod())
              .build();
        }
      }
    }
    return result;
  }
}
