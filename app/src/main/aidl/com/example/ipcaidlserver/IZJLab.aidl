// IZJLab.aidl
package com.example.ipcaidlserver;

// Declare any non-default types here with import statements

interface IZJLab {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
            double aDouble, String aString);


     String getString();

     //Object getObject();

     int getInt();


}
