package com.dorset.gossipers.cores;

public class ServerCore implements ICore{

    private static ServerCore instance;

    @Override
    public void run() {

    }

    public static synchronized ServerCore getInstance() {
        if (instance == null)
            instance = new ServerCore();
        return instance;
    }
}
