// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: mysmartgym.proto

package ds.mysmartgym;

public final class SmartGymService {
  private SmartGymService() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_mysmartgym_Weight_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_mysmartgym_Weight_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_mysmartgym_Empty_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_mysmartgym_Empty_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\020mysmartgym.proto\022\nmysmartgym\"\030\n\006Weight" +
      "\022\016\n\006weight\030\001 \001(\002\"\007\n\005Empty2E\n\nMySmartGym\022" +
      "7\n\014WeightUpdate\022\022.mysmartgym.Weight\032\021.my" +
      "smartgym.Empty\"\000B\"\n\rds.mysmartgymB\017Smart" +
      "GymServiceP\001b\006proto3"
    };
    com.google.protobuf.Descriptors.FileDescriptor.InternalDescriptorAssigner assigner =
        new com.google.protobuf.Descriptors.FileDescriptor.    InternalDescriptorAssigner() {
          public com.google.protobuf.ExtensionRegistry assignDescriptors(
              com.google.protobuf.Descriptors.FileDescriptor root) {
            descriptor = root;
            return null;
          }
        };
    com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
        }, assigner);
    internal_static_mysmartgym_Weight_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_mysmartgym_Weight_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_mysmartgym_Weight_descriptor,
        new java.lang.String[] { "Weight", });
    internal_static_mysmartgym_Empty_descriptor =
      getDescriptor().getMessageTypes().get(1);
    internal_static_mysmartgym_Empty_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_mysmartgym_Empty_descriptor,
        new java.lang.String[] { });
  }

  // @@protoc_insertion_point(outer_class_scope)
}
