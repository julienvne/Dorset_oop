package com.dorset.gossipers.server.packets;

public interface Packet<T> {

    void read(T t);
    T write();

}
