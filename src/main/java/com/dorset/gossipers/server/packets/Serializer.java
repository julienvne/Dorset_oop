package com.dorset.gossipers.server.packets;

public interface Serializer<T> {

    String serialize(T t);

    T deserialize(String input);

}
