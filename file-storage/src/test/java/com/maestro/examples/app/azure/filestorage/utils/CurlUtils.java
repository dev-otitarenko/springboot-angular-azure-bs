package com.maestro.examples.app.azure.filestorage.utils;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

public class CurlUtils {
    public static String inputStreamToString(InputStream inputStream) {
        final int bufferSize = 8 * 1024;
        byte[] buffer = new byte[bufferSize];
        final StringBuilder builder = new StringBuilder();
        try (BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream, bufferSize)) {
            while (bufferedInputStream.read(buffer) != -1) {
                builder.append(new String(buffer));
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return builder.toString();
    }

    public static void consumeInputStream(InputStream inputStream) {
        inputStreamToString(inputStream);
    }
}
