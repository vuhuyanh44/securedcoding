package com.lgcns.hrm.cv.common.utils;
import org.springframework.lang.Nullable;
import org.springframework.util.StreamUtils;

import java.io.*;
import java.nio.charset.Charset;

public class IoUtil extends org.springframework.util.StreamUtils {

    public static void closeQuietly(@Nullable Closeable closeable) {
        if (closeable == null) {
            return;
        }
        if (closeable instanceof Flushable flushable) {
            try {
                flushable.flush();
            } catch (IOException ignored) {
                // ignore
            }
        }
        try {
            closeable.close();
        } catch (IOException ignored) {
            // ignore
        }
    }

    public static String readToString(InputStream input) {
        return readToString(input, Charsets.UTF_8);
    }

    public static String readToString(@Nullable InputStream input, Charset charset) {
        try {
            return StreamUtils.copyToString(input, charset);
        } catch (IOException e) {
            throw Exceptions.unchecked(e);
        } finally {
            IoUtil.closeQuietly(input);
        }
    }

    public static byte[] readToByteArray(@Nullable InputStream input) {
        try {
            return StreamUtils.copyToByteArray(input);
        } catch (IOException e) {
            throw Exceptions.unchecked(e);
        } finally {
            IoUtil.closeQuietly(input);
        }
    }

    public static void write(@Nullable final String data, final OutputStream output, final Charset encoding) throws IOException {
        if (data != null) {
            output.write(data.getBytes(encoding));
        }
    }
}