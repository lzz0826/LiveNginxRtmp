package org.example.enums;

public enum UploadType {

  Image("image"),

  Avatar("avatar"),

  Mp4("mp4"),

  File("file");

  public final String code;

  UploadType(String code) {
    this.code = code;
  }


}
