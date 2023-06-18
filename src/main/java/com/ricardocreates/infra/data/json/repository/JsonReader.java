package com.ricardocreates.infra.data.json.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class JsonReader {
    public String getResourceFileAsString(String fileName) {
        InputStream is = getResourceFileAsInputStream(fileName);
        if (is != null) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            return (String) reader.lines().collect(Collectors.joining(System.lineSeparator()));
        } else {
            throw new RuntimeException("resource not found");
        }
    }

    public InputStream getResourceFileAsInputStream(String fileName) {
        ClassLoader classLoader = this.getClass().getClassLoader();
        return classLoader.getResourceAsStream(fileName);
    }
}
