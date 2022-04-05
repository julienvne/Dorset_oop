package com.dorset.gossipers.server.packets;

import com.google.gson.*;

import java.lang.reflect.Type;

public class PacketAdapter implements JsonSerializer<Packet>, JsonDeserializer<Packet> {

    @Override
    public JsonElement serialize(Packet src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject result = new JsonObject();
        result.add("type", new JsonPrimitive(src.getClass().getSimpleName()));
        result.add("properties", context.serialize(src, src.getClass()));

        return result;
    }

    @Override
    public Packet deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        String type = jsonObject.get("type").getAsString();
        JsonElement element = jsonObject.get("properties");

        try {
            return context.deserialize(element, Class.forName("com.dorset.gossipers.server.packets." + type));
        } catch (ClassNotFoundException exception) {
            throw new JsonParseException("Unknown element type: " + type, exception);
        }
    }
}
