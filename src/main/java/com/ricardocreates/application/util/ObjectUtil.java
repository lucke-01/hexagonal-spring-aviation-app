package com.ricardocreates.application.util;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ObjectUtil {
    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
