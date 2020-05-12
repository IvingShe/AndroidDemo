package com.example.ipcaidlserver;

import android.os.RemoteException;

/**
 * author: li.she
 * date: 2020/5/12:18:42
 * description:
 */
public class ZJLabBean extends IZJLab.Stub{
    @Override
    public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {

    }

    @Override
    public String getString() throws RemoteException {
        return null;
    }

    @Override
    public int getInt() throws RemoteException {
        return 0;
    }
}
