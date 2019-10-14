package com.epfd.csandroid.models;

public class VersionCode {

   private String mVersionNumber;
   private boolean mVersionPublished;

    public VersionCode() {}

    public String getVersionNumber() {
        return mVersionNumber;
    }

    public void setVersionNumber(String versionNumber) {
        mVersionNumber = versionNumber;
    }

    public boolean isVersionPublished() {
        return mVersionPublished;
    }

    public void setVersionPublished(boolean versionPublished) {
        mVersionPublished = versionPublished;
    }
}
