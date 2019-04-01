package com.proxy.utils;
import java.io.*;

/**
 * @Description: TODO
 * @ClassName FileUtils
 * @Auther: administer
 * @Date: 2019/4/1 13:16
 * @Version 1.0
 */
public class FileUtils {

    public static byte[] copyToByteArray(File in) throws IOException {
        if (in == null) {
            throw new IllegalArgumentException("No input File specified");
        }
        return copyToByteArray((InputStream)(new BufferedInputStream(new FileInputStream(in))));
    }

    public static int copy(InputStream in, OutputStream out) throws IOException {
        if (in == null) {
            throw new IllegalArgumentException("No InputStream specified");
        }
        if (out == null) {
            throw new IllegalArgumentException("No OutputStream specified");
        }
        int byteCount = 0;
        byte[] buffer = new byte[4096];

        int bytesRead;
        for(boolean var4 = true; (bytesRead = in.read(buffer)) != -1; byteCount += bytesRead) {
            out.write(buffer, 0, bytesRead);
        }

        out.flush();
        return byteCount;
    }
    public static byte[] copyToByteArray(InputStream in) throws IOException {
        if (in == null) {
            return new byte[0];
        } else {
            ByteArrayOutputStream out = new ByteArrayOutputStream(4096);
            copy((InputStream)in, (OutputStream)out);
            return out.toByteArray();
        }
    }
    public static void copy(byte[] in, File out) throws IOException {
        if (in == null) {
            throw new IllegalArgumentException("No input byte array specified");
        }
        if (out == null) {
            throw new IllegalArgumentException("No output File specified");
        }
        ByteArrayInputStream inStream = new ByteArrayInputStream(in);
        OutputStream outStream = new BufferedOutputStream(new FileOutputStream(out));
        copy((InputStream)inStream, (OutputStream)outStream);
    }
}
