package com.dorset.gossipers.cores;

import com.dorset.gossipers.Board;

public class ClientCore implements ICore {

    private static ClientCore instance;
    private Board client;
    private Board clientBlankBoard;

    @Override
    public void run() {

    }

    public void setClient(Board client) {
        this.client = client;
    }

    public void setClientBlankBoard(Board clientBlankBoard) {
        this.clientBlankBoard = clientBlankBoard;
    }

    public static synchronized ClientCore getInstance() {
        if (instance == null)
            instance = new ClientCore();
        return instance;
    }
}
