package com.winsel.controller;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class LocalTimeAdapter implements JsonSerializer<LocalTime> {
    @Override
    public JsonElement serialize(LocalTime localTime, Type type, JsonSerializationContext jsonSerializationContext) {
        return new JsonPrimitive(localTime.format(DateTimeFormatter.ofPattern("HH:mm:ss")));
    }
}
