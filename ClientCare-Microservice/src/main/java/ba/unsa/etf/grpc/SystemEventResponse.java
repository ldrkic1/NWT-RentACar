// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: SystemEvent.proto

package ba.unsa.etf.grpc;

/**
 * Protobuf type {@code SystemEventResponse}
 */
public  final class SystemEventResponse extends
    com.google.protobuf.GeneratedMessageV3 implements
    // @@protoc_insertion_point(message_implements:SystemEventResponse)
    SystemEventResponseOrBuilder {
private static final long serialVersionUID = 0L;
  // Use SystemEventResponse.newBuilder() to construct.
  private SystemEventResponse(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
    super(builder);
  }
  private SystemEventResponse() {
    responseContent_ = "";
    responseType_ = 0;
  }

  @java.lang.Override
  @SuppressWarnings({"unused"})
  protected java.lang.Object newInstance(
      UnusedPrivateParameter unused) {
    return new SystemEventResponse();
  }

  @java.lang.Override
  public final com.google.protobuf.UnknownFieldSet
  getUnknownFields() {
    return this.unknownFields;
  }
  private SystemEventResponse(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    this();
    if (extensionRegistry == null) {
      throw new java.lang.NullPointerException();
    }
    com.google.protobuf.UnknownFieldSet.Builder unknownFields =
        com.google.protobuf.UnknownFieldSet.newBuilder();
    try {
      boolean done = false;
      while (!done) {
        int tag = input.readTag();
        switch (tag) {
          case 0:
            done = true;
            break;
          case 10: {
            java.lang.String s = input.readStringRequireUtf8();

            responseContent_ = s;
            break;
          }
          case 16: {
            int rawValue = input.readEnum();

            responseType_ = rawValue;
            break;
          }
          default: {
            if (!parseUnknownField(
                input, unknownFields, extensionRegistry, tag)) {
              done = true;
            }
            break;
          }
        }
      }
    } catch (com.google.protobuf.InvalidProtocolBufferException e) {
      throw e.setUnfinishedMessage(this);
    } catch (java.io.IOException e) {
      throw new com.google.protobuf.InvalidProtocolBufferException(
          e).setUnfinishedMessage(this);
    } finally {
      this.unknownFields = unknownFields.build();
      makeExtensionsImmutable();
    }
  }
  public static final com.google.protobuf.Descriptors.Descriptor
      getDescriptor() {
    return ba.unsa.etf.grpc.SystemEvent.internal_static_SystemEventResponse_descriptor;
  }

  @java.lang.Override
  protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internalGetFieldAccessorTable() {
    return ba.unsa.etf.grpc.SystemEvent.internal_static_SystemEventResponse_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            ba.unsa.etf.grpc.SystemEventResponse.class, ba.unsa.etf.grpc.SystemEventResponse.Builder.class);
  }

  public static final int RESPONSECONTENT_FIELD_NUMBER = 1;
  private volatile java.lang.Object responseContent_;
  /**
   * <code>string responseContent = 1;</code>
   * @return The responseContent.
   */
  public java.lang.String getResponseContent() {
    java.lang.Object ref = responseContent_;
    if (ref instanceof java.lang.String) {
      return (java.lang.String) ref;
    } else {
      com.google.protobuf.ByteString bs = 
          (com.google.protobuf.ByteString) ref;
      java.lang.String s = bs.toStringUtf8();
      responseContent_ = s;
      return s;
    }
  }
  /**
   * <code>string responseContent = 1;</code>
   * @return The bytes for responseContent.
   */
  public com.google.protobuf.ByteString
      getResponseContentBytes() {
    java.lang.Object ref = responseContent_;
    if (ref instanceof java.lang.String) {
      com.google.protobuf.ByteString b = 
          com.google.protobuf.ByteString.copyFromUtf8(
              (java.lang.String) ref);
      responseContent_ = b;
      return b;
    } else {
      return (com.google.protobuf.ByteString) ref;
    }
  }

  public static final int RESPONSETYPE_FIELD_NUMBER = 2;
  private int responseType_;
  /**
   * <code>.ResponseType responseType = 2;</code>
   * @return The enum numeric value on the wire for responseType.
   */
  public int getResponseTypeValue() {
    return responseType_;
  }
  /**
   * <code>.ResponseType responseType = 2;</code>
   * @return The responseType.
   */
  public ba.unsa.etf.grpc.ResponseType getResponseType() {
    @SuppressWarnings("deprecation")
    ba.unsa.etf.grpc.ResponseType result = ba.unsa.etf.grpc.ResponseType.valueOf(responseType_);
    return result == null ? ba.unsa.etf.grpc.ResponseType.UNRECOGNIZED : result;
  }

  private byte memoizedIsInitialized = -1;
  @java.lang.Override
  public final boolean isInitialized() {
    byte isInitialized = memoizedIsInitialized;
    if (isInitialized == 1) return true;
    if (isInitialized == 0) return false;

    memoizedIsInitialized = 1;
    return true;
  }

  @java.lang.Override
  public void writeTo(com.google.protobuf.CodedOutputStream output)
                      throws java.io.IOException {
    if (!getResponseContentBytes().isEmpty()) {
      com.google.protobuf.GeneratedMessageV3.writeString(output, 1, responseContent_);
    }
    if (responseType_ != ba.unsa.etf.grpc.ResponseType.SUCCESS.getNumber()) {
      output.writeEnum(2, responseType_);
    }
    unknownFields.writeTo(output);
  }

  @java.lang.Override
  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    if (!getResponseContentBytes().isEmpty()) {
      size += com.google.protobuf.GeneratedMessageV3.computeStringSize(1, responseContent_);
    }
    if (responseType_ != ba.unsa.etf.grpc.ResponseType.SUCCESS.getNumber()) {
      size += com.google.protobuf.CodedOutputStream
        .computeEnumSize(2, responseType_);
    }
    size += unknownFields.getSerializedSize();
    memoizedSize = size;
    return size;
  }

  @java.lang.Override
  public boolean equals(final java.lang.Object obj) {
    if (obj == this) {
     return true;
    }
    if (!(obj instanceof ba.unsa.etf.grpc.SystemEventResponse)) {
      return super.equals(obj);
    }
    ba.unsa.etf.grpc.SystemEventResponse other = (ba.unsa.etf.grpc.SystemEventResponse) obj;

    if (!getResponseContent()
        .equals(other.getResponseContent())) return false;
    if (responseType_ != other.responseType_) return false;
    if (!unknownFields.equals(other.unknownFields)) return false;
    return true;
  }

  @java.lang.Override
  public int hashCode() {
    if (memoizedHashCode != 0) {
      return memoizedHashCode;
    }
    int hash = 41;
    hash = (19 * hash) + getDescriptor().hashCode();
    hash = (37 * hash) + RESPONSECONTENT_FIELD_NUMBER;
    hash = (53 * hash) + getResponseContent().hashCode();
    hash = (37 * hash) + RESPONSETYPE_FIELD_NUMBER;
    hash = (53 * hash) + responseType_;
    hash = (29 * hash) + unknownFields.hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static ba.unsa.etf.grpc.SystemEventResponse parseFrom(
      java.nio.ByteBuffer data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static ba.unsa.etf.grpc.SystemEventResponse parseFrom(
      java.nio.ByteBuffer data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static ba.unsa.etf.grpc.SystemEventResponse parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static ba.unsa.etf.grpc.SystemEventResponse parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static ba.unsa.etf.grpc.SystemEventResponse parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static ba.unsa.etf.grpc.SystemEventResponse parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static ba.unsa.etf.grpc.SystemEventResponse parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static ba.unsa.etf.grpc.SystemEventResponse parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }
  public static ba.unsa.etf.grpc.SystemEventResponse parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input);
  }
  public static ba.unsa.etf.grpc.SystemEventResponse parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
  }
  public static ba.unsa.etf.grpc.SystemEventResponse parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static ba.unsa.etf.grpc.SystemEventResponse parseFrom(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }

  @java.lang.Override
  public Builder newBuilderForType() { return newBuilder(); }
  public static Builder newBuilder() {
    return DEFAULT_INSTANCE.toBuilder();
  }
  public static Builder newBuilder(ba.unsa.etf.grpc.SystemEventResponse prototype) {
    return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
  }
  @java.lang.Override
  public Builder toBuilder() {
    return this == DEFAULT_INSTANCE
        ? new Builder() : new Builder().mergeFrom(this);
  }

  @java.lang.Override
  protected Builder newBuilderForType(
      com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
    Builder builder = new Builder(parent);
    return builder;
  }
  /**
   * Protobuf type {@code SystemEventResponse}
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
      // @@protoc_insertion_point(builder_implements:SystemEventResponse)
      ba.unsa.etf.grpc.SystemEventResponseOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return ba.unsa.etf.grpc.SystemEvent.internal_static_SystemEventResponse_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return ba.unsa.etf.grpc.SystemEvent.internal_static_SystemEventResponse_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              ba.unsa.etf.grpc.SystemEventResponse.class, ba.unsa.etf.grpc.SystemEventResponse.Builder.class);
    }

    // Construct using ba.unsa.etf.grpc.SystemEventResponse.newBuilder()
    private Builder() {
      maybeForceBuilderInitialization();
    }

    private Builder(
        com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
      super(parent);
      maybeForceBuilderInitialization();
    }
    private void maybeForceBuilderInitialization() {
      if (com.google.protobuf.GeneratedMessageV3
              .alwaysUseFieldBuilders) {
      }
    }
    @java.lang.Override
    public Builder clear() {
      super.clear();
      responseContent_ = "";

      responseType_ = 0;

      return this;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.Descriptor
        getDescriptorForType() {
      return ba.unsa.etf.grpc.SystemEvent.internal_static_SystemEventResponse_descriptor;
    }

    @java.lang.Override
    public ba.unsa.etf.grpc.SystemEventResponse getDefaultInstanceForType() {
      return ba.unsa.etf.grpc.SystemEventResponse.getDefaultInstance();
    }

    @java.lang.Override
    public ba.unsa.etf.grpc.SystemEventResponse build() {
      ba.unsa.etf.grpc.SystemEventResponse result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    @java.lang.Override
    public ba.unsa.etf.grpc.SystemEventResponse buildPartial() {
      ba.unsa.etf.grpc.SystemEventResponse result = new ba.unsa.etf.grpc.SystemEventResponse(this);
      result.responseContent_ = responseContent_;
      result.responseType_ = responseType_;
      onBuilt();
      return result;
    }

    @java.lang.Override
    public Builder clone() {
      return super.clone();
    }
    @java.lang.Override
    public Builder setField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        java.lang.Object value) {
      return super.setField(field, value);
    }
    @java.lang.Override
    public Builder clearField(
        com.google.protobuf.Descriptors.FieldDescriptor field) {
      return super.clearField(field);
    }
    @java.lang.Override
    public Builder clearOneof(
        com.google.protobuf.Descriptors.OneofDescriptor oneof) {
      return super.clearOneof(oneof);
    }
    @java.lang.Override
    public Builder setRepeatedField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        int index, java.lang.Object value) {
      return super.setRepeatedField(field, index, value);
    }
    @java.lang.Override
    public Builder addRepeatedField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        java.lang.Object value) {
      return super.addRepeatedField(field, value);
    }
    @java.lang.Override
    public Builder mergeFrom(com.google.protobuf.Message other) {
      if (other instanceof ba.unsa.etf.grpc.SystemEventResponse) {
        return mergeFrom((ba.unsa.etf.grpc.SystemEventResponse)other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(ba.unsa.etf.grpc.SystemEventResponse other) {
      if (other == ba.unsa.etf.grpc.SystemEventResponse.getDefaultInstance()) return this;
      if (!other.getResponseContent().isEmpty()) {
        responseContent_ = other.responseContent_;
        onChanged();
      }
      if (other.responseType_ != 0) {
        setResponseTypeValue(other.getResponseTypeValue());
      }
      this.mergeUnknownFields(other.unknownFields);
      onChanged();
      return this;
    }

    @java.lang.Override
    public final boolean isInitialized() {
      return true;
    }

    @java.lang.Override
    public Builder mergeFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      ba.unsa.etf.grpc.SystemEventResponse parsedMessage = null;
      try {
        parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        parsedMessage = (ba.unsa.etf.grpc.SystemEventResponse) e.getUnfinishedMessage();
        throw e.unwrapIOException();
      } finally {
        if (parsedMessage != null) {
          mergeFrom(parsedMessage);
        }
      }
      return this;
    }

    private java.lang.Object responseContent_ = "";
    /**
     * <code>string responseContent = 1;</code>
     * @return The responseContent.
     */
    public java.lang.String getResponseContent() {
      java.lang.Object ref = responseContent_;
      if (!(ref instanceof java.lang.String)) {
        com.google.protobuf.ByteString bs =
            (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        responseContent_ = s;
        return s;
      } else {
        return (java.lang.String) ref;
      }
    }
    /**
     * <code>string responseContent = 1;</code>
     * @return The bytes for responseContent.
     */
    public com.google.protobuf.ByteString
        getResponseContentBytes() {
      java.lang.Object ref = responseContent_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (java.lang.String) ref);
        responseContent_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }
    /**
     * <code>string responseContent = 1;</code>
     * @param value The responseContent to set.
     * @return This builder for chaining.
     */
    public Builder setResponseContent(
        java.lang.String value) {
      if (value == null) {
    throw new NullPointerException();
  }
  
      responseContent_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>string responseContent = 1;</code>
     * @return This builder for chaining.
     */
    public Builder clearResponseContent() {
      
      responseContent_ = getDefaultInstance().getResponseContent();
      onChanged();
      return this;
    }
    /**
     * <code>string responseContent = 1;</code>
     * @param value The bytes for responseContent to set.
     * @return This builder for chaining.
     */
    public Builder setResponseContentBytes(
        com.google.protobuf.ByteString value) {
      if (value == null) {
    throw new NullPointerException();
  }
  checkByteStringIsUtf8(value);
      
      responseContent_ = value;
      onChanged();
      return this;
    }

    private int responseType_ = 0;
    /**
     * <code>.ResponseType responseType = 2;</code>
     * @return The enum numeric value on the wire for responseType.
     */
    public int getResponseTypeValue() {
      return responseType_;
    }
    /**
     * <code>.ResponseType responseType = 2;</code>
     * @param value The enum numeric value on the wire for responseType to set.
     * @return This builder for chaining.
     */
    public Builder setResponseTypeValue(int value) {
      responseType_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>.ResponseType responseType = 2;</code>
     * @return The responseType.
     */
    public ba.unsa.etf.grpc.ResponseType getResponseType() {
      @SuppressWarnings("deprecation")
      ba.unsa.etf.grpc.ResponseType result = ba.unsa.etf.grpc.ResponseType.valueOf(responseType_);
      return result == null ? ba.unsa.etf.grpc.ResponseType.UNRECOGNIZED : result;
    }
    /**
     * <code>.ResponseType responseType = 2;</code>
     * @param value The responseType to set.
     * @return This builder for chaining.
     */
    public Builder setResponseType(ba.unsa.etf.grpc.ResponseType value) {
      if (value == null) {
        throw new NullPointerException();
      }
      
      responseType_ = value.getNumber();
      onChanged();
      return this;
    }
    /**
     * <code>.ResponseType responseType = 2;</code>
     * @return This builder for chaining.
     */
    public Builder clearResponseType() {
      
      responseType_ = 0;
      onChanged();
      return this;
    }
    @java.lang.Override
    public final Builder setUnknownFields(
        final com.google.protobuf.UnknownFieldSet unknownFields) {
      return super.setUnknownFields(unknownFields);
    }

    @java.lang.Override
    public final Builder mergeUnknownFields(
        final com.google.protobuf.UnknownFieldSet unknownFields) {
      return super.mergeUnknownFields(unknownFields);
    }


    // @@protoc_insertion_point(builder_scope:SystemEventResponse)
  }

  // @@protoc_insertion_point(class_scope:SystemEventResponse)
  private static final ba.unsa.etf.grpc.SystemEventResponse DEFAULT_INSTANCE;
  static {
    DEFAULT_INSTANCE = new ba.unsa.etf.grpc.SystemEventResponse();
  }

  public static ba.unsa.etf.grpc.SystemEventResponse getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<SystemEventResponse>
      PARSER = new com.google.protobuf.AbstractParser<SystemEventResponse>() {
    @java.lang.Override
    public SystemEventResponse parsePartialFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return new SystemEventResponse(input, extensionRegistry);
    }
  };

  public static com.google.protobuf.Parser<SystemEventResponse> parser() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.protobuf.Parser<SystemEventResponse> getParserForType() {
    return PARSER;
  }

  @java.lang.Override
  public ba.unsa.etf.grpc.SystemEventResponse getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }

}
