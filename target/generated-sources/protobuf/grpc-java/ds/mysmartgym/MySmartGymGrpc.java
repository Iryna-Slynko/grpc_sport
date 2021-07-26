package ds.mysmartgym;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.39.0)",
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
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "WeightUpdate"))
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

  private static volatile io.grpc.MethodDescriptor<ds.mysmartgym.ConsumedFoodRequest,
      ds.mysmartgym.NutrientsReply> getConsumedFoodStreamingMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "ConsumedFoodStreaming",
      requestType = ds.mysmartgym.ConsumedFoodRequest.class,
      responseType = ds.mysmartgym.NutrientsReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.BIDI_STREAMING)
  public static io.grpc.MethodDescriptor<ds.mysmartgym.ConsumedFoodRequest,
      ds.mysmartgym.NutrientsReply> getConsumedFoodStreamingMethod() {
    io.grpc.MethodDescriptor<ds.mysmartgym.ConsumedFoodRequest, ds.mysmartgym.NutrientsReply> getConsumedFoodStreamingMethod;
    if ((getConsumedFoodStreamingMethod = MySmartGymGrpc.getConsumedFoodStreamingMethod) == null) {
      synchronized (MySmartGymGrpc.class) {
        if ((getConsumedFoodStreamingMethod = MySmartGymGrpc.getConsumedFoodStreamingMethod) == null) {
          MySmartGymGrpc.getConsumedFoodStreamingMethod = getConsumedFoodStreamingMethod =
              io.grpc.MethodDescriptor.<ds.mysmartgym.ConsumedFoodRequest, ds.mysmartgym.NutrientsReply>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.BIDI_STREAMING)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "ConsumedFoodStreaming"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  ds.mysmartgym.ConsumedFoodRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  ds.mysmartgym.NutrientsReply.getDefaultInstance()))
              .setSchemaDescriptor(new MySmartGymMethodDescriptorSupplier("ConsumedFoodStreaming"))
              .build();
        }
      }
    }
    return getConsumedFoodStreamingMethod;
  }

  private static volatile io.grpc.MethodDescriptor<ds.mysmartgym.HeartBeat,
      ds.mysmartgym.WorkoutIntensity> getHeartTrackingMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "HeartTracking",
      requestType = ds.mysmartgym.HeartBeat.class,
      responseType = ds.mysmartgym.WorkoutIntensity.class,
      methodType = io.grpc.MethodDescriptor.MethodType.BIDI_STREAMING)
  public static io.grpc.MethodDescriptor<ds.mysmartgym.HeartBeat,
      ds.mysmartgym.WorkoutIntensity> getHeartTrackingMethod() {
    io.grpc.MethodDescriptor<ds.mysmartgym.HeartBeat, ds.mysmartgym.WorkoutIntensity> getHeartTrackingMethod;
    if ((getHeartTrackingMethod = MySmartGymGrpc.getHeartTrackingMethod) == null) {
      synchronized (MySmartGymGrpc.class) {
        if ((getHeartTrackingMethod = MySmartGymGrpc.getHeartTrackingMethod) == null) {
          MySmartGymGrpc.getHeartTrackingMethod = getHeartTrackingMethod =
              io.grpc.MethodDescriptor.<ds.mysmartgym.HeartBeat, ds.mysmartgym.WorkoutIntensity>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.BIDI_STREAMING)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "HeartTracking"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  ds.mysmartgym.HeartBeat.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  ds.mysmartgym.WorkoutIntensity.getDefaultInstance()))
              .setSchemaDescriptor(new MySmartGymMethodDescriptorSupplier("HeartTracking"))
              .build();
        }
      }
    }
    return getHeartTrackingMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static MySmartGymStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<MySmartGymStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<MySmartGymStub>() {
        @java.lang.Override
        public MySmartGymStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new MySmartGymStub(channel, callOptions);
        }
      };
    return MySmartGymStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static MySmartGymBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<MySmartGymBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<MySmartGymBlockingStub>() {
        @java.lang.Override
        public MySmartGymBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new MySmartGymBlockingStub(channel, callOptions);
        }
      };
    return MySmartGymBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static MySmartGymFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<MySmartGymFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<MySmartGymFutureStub>() {
        @java.lang.Override
        public MySmartGymFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new MySmartGymFutureStub(channel, callOptions);
        }
      };
    return MySmartGymFutureStub.newStub(factory, channel);
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
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getWeightUpdateMethod(), responseObserver);
    }

    /**
     */
    public io.grpc.stub.StreamObserver<ds.mysmartgym.ConsumedFoodRequest> consumedFoodStreaming(
        io.grpc.stub.StreamObserver<ds.mysmartgym.NutrientsReply> responseObserver) {
      return io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall(getConsumedFoodStreamingMethod(), responseObserver);
    }

    /**
     */
    public io.grpc.stub.StreamObserver<ds.mysmartgym.HeartBeat> heartTracking(
        io.grpc.stub.StreamObserver<ds.mysmartgym.WorkoutIntensity> responseObserver) {
      return io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall(getHeartTrackingMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getWeightUpdateMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                ds.mysmartgym.Weight,
                ds.mysmartgym.Empty>(
                  this, METHODID_WEIGHT_UPDATE)))
          .addMethod(
            getConsumedFoodStreamingMethod(),
            io.grpc.stub.ServerCalls.asyncBidiStreamingCall(
              new MethodHandlers<
                ds.mysmartgym.ConsumedFoodRequest,
                ds.mysmartgym.NutrientsReply>(
                  this, METHODID_CONSUMED_FOOD_STREAMING)))
          .addMethod(
            getHeartTrackingMethod(),
            io.grpc.stub.ServerCalls.asyncBidiStreamingCall(
              new MethodHandlers<
                ds.mysmartgym.HeartBeat,
                ds.mysmartgym.WorkoutIntensity>(
                  this, METHODID_HEART_TRACKING)))
          .build();
    }
  }

  /**
   */
  public static final class MySmartGymStub extends io.grpc.stub.AbstractAsyncStub<MySmartGymStub> {
    private MySmartGymStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected MySmartGymStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new MySmartGymStub(channel, callOptions);
    }

    /**
     * <pre>
     * Sends weight
     * </pre>
     */
    public void weightUpdate(ds.mysmartgym.Weight request,
        io.grpc.stub.StreamObserver<ds.mysmartgym.Empty> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getWeightUpdateMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public io.grpc.stub.StreamObserver<ds.mysmartgym.ConsumedFoodRequest> consumedFoodStreaming(
        io.grpc.stub.StreamObserver<ds.mysmartgym.NutrientsReply> responseObserver) {
      return io.grpc.stub.ClientCalls.asyncBidiStreamingCall(
          getChannel().newCall(getConsumedFoodStreamingMethod(), getCallOptions()), responseObserver);
    }

    /**
     */
    public io.grpc.stub.StreamObserver<ds.mysmartgym.HeartBeat> heartTracking(
        io.grpc.stub.StreamObserver<ds.mysmartgym.WorkoutIntensity> responseObserver) {
      return io.grpc.stub.ClientCalls.asyncBidiStreamingCall(
          getChannel().newCall(getHeartTrackingMethod(), getCallOptions()), responseObserver);
    }
  }

  /**
   */
  public static final class MySmartGymBlockingStub extends io.grpc.stub.AbstractBlockingStub<MySmartGymBlockingStub> {
    private MySmartGymBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected MySmartGymBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new MySmartGymBlockingStub(channel, callOptions);
    }

    /**
     * <pre>
     * Sends weight
     * </pre>
     */
    public ds.mysmartgym.Empty weightUpdate(ds.mysmartgym.Weight request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getWeightUpdateMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class MySmartGymFutureStub extends io.grpc.stub.AbstractFutureStub<MySmartGymFutureStub> {
    private MySmartGymFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected MySmartGymFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new MySmartGymFutureStub(channel, callOptions);
    }

    /**
     * <pre>
     * Sends weight
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<ds.mysmartgym.Empty> weightUpdate(
        ds.mysmartgym.Weight request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getWeightUpdateMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_WEIGHT_UPDATE = 0;
  private static final int METHODID_CONSUMED_FOOD_STREAMING = 1;
  private static final int METHODID_HEART_TRACKING = 2;

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
        case METHODID_CONSUMED_FOOD_STREAMING:
          return (io.grpc.stub.StreamObserver<Req>) serviceImpl.consumedFoodStreaming(
              (io.grpc.stub.StreamObserver<ds.mysmartgym.NutrientsReply>) responseObserver);
        case METHODID_HEART_TRACKING:
          return (io.grpc.stub.StreamObserver<Req>) serviceImpl.heartTracking(
              (io.grpc.stub.StreamObserver<ds.mysmartgym.WorkoutIntensity>) responseObserver);
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
              .addMethod(getConsumedFoodStreamingMethod())
              .addMethod(getHeartTrackingMethod())
              .build();
        }
      }
    }
    return result;
  }
}
