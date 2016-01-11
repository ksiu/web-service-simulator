package com.kensoft.test.ws.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.core.io.ClassPathResource;

public final class ClassPathResourceUtils {
    private ClassPathResourceUtils() {}

    public static File getResourceAsFile(String path) throws IOException {
        ClassPathResource resource = new ClassPathResource(path);
        return resource.getFile();
    }

    public static InputStream getResourceAsStream(String path) throws IOException {
        ClassPathResource resource = new ClassPathResource(path);
        return resource.getInputStream();
    }
}
