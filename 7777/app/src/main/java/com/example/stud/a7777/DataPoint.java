package com.example.stud.a7777;

/**
 * Created by Steven on 7/6/2016.
 */
public class DataPoint {

    int type;
    double readValue;
    long timeMillis;

    final int TYPE_GENERAL = 101;

    public DataPoint(double _readValue, long _timeMillis) {

        type = TYPE_GENERAL;
        readValue = _readValue;
        timeMillis = _timeMillis;
    }
}
