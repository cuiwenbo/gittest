package com.manong.mall.utils;

import java.io.*;

/**
 * Created by boleli on 16/7/21.
 */
public class SerializationUtils implements Serializable {

    public static byte[] serialize(Object object) {
        ObjectOutputStream oos = null;
        ByteArrayOutputStream baos = null;
        try {
            baos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(baos);
            oos.writeObject(object);
            return baos.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeStream(oos);
            closeStream(baos);
        }
        return null;
    }

    public static <T> T deserialize(byte[] bytes) {
        ByteArrayInputStream bais = null;
        ObjectInputStream ois = null;
        try {
            bais = new ByteArrayInputStream(bytes);
            ois = new ObjectInputStream(bais);
            return (T)ois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeStream(bais);
            closeStream(ois);
        }
        return null;
    }

    public static void closeStream(Closeable stream) {
        if (stream != null) {
            try {
                stream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
