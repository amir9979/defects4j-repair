// Generated by the protocol buffer compiler.  DO NOT EDIT!

package com.google.javascript.jscomp;

public interface InstrumentationOrBuilder
    extends com.google.protobuf.MessageOrBuilder {
  
  // optional string report_defined = 1;
  boolean hasReportDefined();
  String getReportDefined();
  
  // optional string report_call = 2;
  boolean hasReportCall();
  String getReportCall();
  
  // optional string report_exit = 6;
  boolean hasReportExit();
  String getReportExit();
  
  // repeated string declaration_to_remove = 3;
  java.util.List<String> getDeclarationToRemoveList();
  int getDeclarationToRemoveCount();
  String getDeclarationToRemove(int index);
  
  // repeated string init = 4;
  java.util.List<String> getInitList();
  int getInitCount();
  String getInit(int index);
  
  // optional string app_name_setter = 5;
  boolean hasAppNameSetter();
  String getAppNameSetter();
}
