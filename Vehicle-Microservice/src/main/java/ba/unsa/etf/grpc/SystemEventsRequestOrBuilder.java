// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: SystemEvent.proto

package ba.unsa.etf.grpc;

public interface SystemEventsRequestOrBuilder extends
    // @@protoc_insertion_point(interface_extends:SystemEventsRequest)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <code>string timeStamp = 1;</code>
   * @return The timeStamp.
   */
  java.lang.String getTimeStamp();
  /**
   * <code>string timeStamp = 1;</code>
   * @return The bytes for timeStamp.
   */
  com.google.protobuf.ByteString
      getTimeStampBytes();

  /**
   * <code>string microservice = 2;</code>
   * @return The microservice.
   */
  java.lang.String getMicroservice();
  /**
   * <code>string microservice = 2;</code>
   * @return The bytes for microservice.
   */
  com.google.protobuf.ByteString
      getMicroserviceBytes();

  /**
   * <code>string action = 3;</code>
   * @return The action.
   */
  java.lang.String getAction();
  /**
   * <code>string action = 3;</code>
   * @return The bytes for action.
   */
  com.google.protobuf.ByteString
      getActionBytes();

  /**
   * <code>string resource = 4;</code>
   * @return The resource.
   */
  java.lang.String getResource();
  /**
   * <code>string resource = 4;</code>
   * @return The bytes for resource.
   */
  com.google.protobuf.ByteString
      getResourceBytes();

  /**
   * <code>string response = 5;</code>
   * @return The response.
   */
  java.lang.String getResponse();
  /**
   * <code>string response = 5;</code>
   * @return The bytes for response.
   */
  com.google.protobuf.ByteString
      getResponseBytes();

  /**
   * <code>string message = 6;</code>
   * @return The message.
   */
  java.lang.String getMessage();
  /**
   * <code>string message = 6;</code>
   * @return The bytes for message.
   */
  com.google.protobuf.ByteString
      getMessageBytes();

  /**
   * <code>int64 idKorisnik = 7;</code>
   * @return The idKorisnik.
   */
  long getIdKorisnik();
}
