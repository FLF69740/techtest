package com.epfd.dolto.models;

public class VersionCode {

   private String mVersion;

    public VersionCode() {}

    public VersionCode(String version) {
        mVersion = version;
    }

    public String getVersionNumber() {
        return mVersion;
    }

    public void setVersionNumber(String version) {
        mVersion = version;
    }

}
