package com.epfd.dolto.models;

import android.os.Parcel;
import android.os.Parcelable;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

public class ModalUserTimeTable implements Parcelable {

    private List<DateTime> mListReservationStart;
    private List<DateTime> mListReservationEnd;
    private List<String> mTablesId;

    public ModalUserTimeTable() {
        mListReservationStart = new ArrayList<>();
        mListReservationEnd = new ArrayList<>();
        mTablesId = new ArrayList<>();
    }

    public List<DateTime> getListReservationStart() {
        return mListReservationStart;
    }

    public void setListReservationStart(List<DateTime> listReservationStart) {
        mListReservationStart = listReservationStart;
    }

    public List<DateTime> getListReservationEnd() {
        return mListReservationEnd;
    }

    public void setListReservationEnd(List<DateTime> listReservationEnd) {
        mListReservationEnd = listReservationEnd;
    }

    public List<String> getTablesId() {
        return mTablesId;
    }

    public void setTablesId(List<String> tablesId) {
        mTablesId = tablesId;
    }

    protected ModalUserTimeTable(Parcel in) {
        if (in.readByte() == 0x01) {
            mListReservationStart = new ArrayList<DateTime>();
            in.readList(mListReservationStart, DateTime.class.getClassLoader());
        } else {
            mListReservationStart = null;
        }
        if (in.readByte() == 0x01) {
            mListReservationEnd = new ArrayList<DateTime>();
            in.readList(mListReservationEnd, DateTime.class.getClassLoader());
        } else {
            mListReservationEnd = null;
        }
        if (in.readByte() == 0x01) {
            mTablesId = new ArrayList<String>();
            in.readList(mTablesId, String.class.getClassLoader());
        } else {
            mTablesId = null;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (mListReservationStart == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(mListReservationStart);
        }
        if (mListReservationEnd == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(mListReservationEnd);
        }
        if (mTablesId == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(mTablesId);
        }
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<ModalUserTimeTable> CREATOR = new Parcelable.Creator<ModalUserTimeTable>() {
        @Override
        public ModalUserTimeTable createFromParcel(Parcel in) {
            return new ModalUserTimeTable(in);
        }

        @Override
        public ModalUserTimeTable[] newArray(int size) {
            return new ModalUserTimeTable[size];
        }
    };
}