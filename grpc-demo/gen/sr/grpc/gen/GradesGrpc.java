package sr.grpc.gen;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.71.0)",
    comments = "Source: student.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class GradesGrpc {

  private GradesGrpc() {}

  public static final java.lang.String SERVICE_NAME = "student.Grades";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<sr.grpc.gen.StudentNameOpArguments,
      sr.grpc.gen.GradesOpResult> getGetAllGradesMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetAllGrades",
      requestType = sr.grpc.gen.StudentNameOpArguments.class,
      responseType = sr.grpc.gen.GradesOpResult.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<sr.grpc.gen.StudentNameOpArguments,
      sr.grpc.gen.GradesOpResult> getGetAllGradesMethod() {
    io.grpc.MethodDescriptor<sr.grpc.gen.StudentNameOpArguments, sr.grpc.gen.GradesOpResult> getGetAllGradesMethod;
    if ((getGetAllGradesMethod = GradesGrpc.getGetAllGradesMethod) == null) {
      synchronized (GradesGrpc.class) {
        if ((getGetAllGradesMethod = GradesGrpc.getGetAllGradesMethod) == null) {
          GradesGrpc.getGetAllGradesMethod = getGetAllGradesMethod =
              io.grpc.MethodDescriptor.<sr.grpc.gen.StudentNameOpArguments, sr.grpc.gen.GradesOpResult>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetAllGrades"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  sr.grpc.gen.StudentNameOpArguments.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  sr.grpc.gen.GradesOpResult.getDefaultInstance()))
              .setSchemaDescriptor(new GradesMethodDescriptorSupplier("GetAllGrades"))
              .build();
        }
      }
    }
    return getGetAllGradesMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static GradesStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<GradesStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<GradesStub>() {
        @java.lang.Override
        public GradesStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new GradesStub(channel, callOptions);
        }
      };
    return GradesStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports all types of calls on the service
   */
  public static GradesBlockingV2Stub newBlockingV2Stub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<GradesBlockingV2Stub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<GradesBlockingV2Stub>() {
        @java.lang.Override
        public GradesBlockingV2Stub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new GradesBlockingV2Stub(channel, callOptions);
        }
      };
    return GradesBlockingV2Stub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static GradesBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<GradesBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<GradesBlockingStub>() {
        @java.lang.Override
        public GradesBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new GradesBlockingStub(channel, callOptions);
        }
      };
    return GradesBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static GradesFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<GradesFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<GradesFutureStub>() {
        @java.lang.Override
        public GradesFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new GradesFutureStub(channel, callOptions);
        }
      };
    return GradesFutureStub.newStub(factory, channel);
  }

  /**
   */
  public interface AsyncService {

    /**
     */
    default void getAllGrades(sr.grpc.gen.StudentNameOpArguments request,
        io.grpc.stub.StreamObserver<sr.grpc.gen.GradesOpResult> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetAllGradesMethod(), responseObserver);
    }
  }

  /**
   * Base class for the server implementation of the service Grades.
   */
  public static abstract class GradesImplBase
      implements io.grpc.BindableService, AsyncService {

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return GradesGrpc.bindService(this);
    }
  }

  /**
   * A stub to allow clients to do asynchronous rpc calls to service Grades.
   */
  public static final class GradesStub
      extends io.grpc.stub.AbstractAsyncStub<GradesStub> {
    private GradesStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected GradesStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new GradesStub(channel, callOptions);
    }

    /**
     */
    public void getAllGrades(sr.grpc.gen.StudentNameOpArguments request,
        io.grpc.stub.StreamObserver<sr.grpc.gen.GradesOpResult> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetAllGradesMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * A stub to allow clients to do synchronous rpc calls to service Grades.
   */
  public static final class GradesBlockingV2Stub
      extends io.grpc.stub.AbstractBlockingStub<GradesBlockingV2Stub> {
    private GradesBlockingV2Stub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected GradesBlockingV2Stub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new GradesBlockingV2Stub(channel, callOptions);
    }

    /**
     */
    public sr.grpc.gen.GradesOpResult getAllGrades(sr.grpc.gen.StudentNameOpArguments request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetAllGradesMethod(), getCallOptions(), request);
    }
  }

  /**
   * A stub to allow clients to do limited synchronous rpc calls to service Grades.
   */
  public static final class GradesBlockingStub
      extends io.grpc.stub.AbstractBlockingStub<GradesBlockingStub> {
    private GradesBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected GradesBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new GradesBlockingStub(channel, callOptions);
    }

    /**
     */
    public sr.grpc.gen.GradesOpResult getAllGrades(sr.grpc.gen.StudentNameOpArguments request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetAllGradesMethod(), getCallOptions(), request);
    }
  }

  /**
   * A stub to allow clients to do ListenableFuture-style rpc calls to service Grades.
   */
  public static final class GradesFutureStub
      extends io.grpc.stub.AbstractFutureStub<GradesFutureStub> {
    private GradesFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected GradesFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new GradesFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<sr.grpc.gen.GradesOpResult> getAllGrades(
        sr.grpc.gen.StudentNameOpArguments request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetAllGradesMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_GET_ALL_GRADES = 0;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final AsyncService serviceImpl;
    private final int methodId;

    MethodHandlers(AsyncService serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_GET_ALL_GRADES:
          serviceImpl.getAllGrades((sr.grpc.gen.StudentNameOpArguments) request,
              (io.grpc.stub.StreamObserver<sr.grpc.gen.GradesOpResult>) responseObserver);
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

  public static final io.grpc.ServerServiceDefinition bindService(AsyncService service) {
    return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
        .addMethod(
          getGetAllGradesMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              sr.grpc.gen.StudentNameOpArguments,
              sr.grpc.gen.GradesOpResult>(
                service, METHODID_GET_ALL_GRADES)))
        .build();
  }

  private static abstract class GradesBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    GradesBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return sr.grpc.gen.StudentProto.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("Grades");
    }
  }

  private static final class GradesFileDescriptorSupplier
      extends GradesBaseDescriptorSupplier {
    GradesFileDescriptorSupplier() {}
  }

  private static final class GradesMethodDescriptorSupplier
      extends GradesBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final java.lang.String methodName;

    GradesMethodDescriptorSupplier(java.lang.String methodName) {
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
      synchronized (GradesGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new GradesFileDescriptorSupplier())
              .addMethod(getGetAllGradesMethod())
              .build();
        }
      }
    }
    return result;
  }
}
