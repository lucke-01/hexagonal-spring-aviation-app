package com.ricardocreates.utiltest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

public class HttpUtilTest {
    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public static <T> T jsonToObject(String json, Class<T> jsonClass) {
        return new Gson().fromJson(json, jsonClass);
    }
}
