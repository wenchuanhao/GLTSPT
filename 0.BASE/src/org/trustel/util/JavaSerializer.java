package org.trustel.util;

import java.io.*;

/**
 * Created by nike on 2014/7/10.
 * 序列化对象
 */
public class JavaSerializer implements Serializable{

    /**
     * 序列化对象
     * @param value
     * @return
     * @throws IOException
     */
    public static byte[] serializeFrom(Object value) throws IOException {

        if (value == null) {
            throw new NullPointerException("Can't serialize null");
        }

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream(bos));
        oos.writeObject(value);

        oos.close();

        return bos.toByteArray();
    }

    /**
     * 反序列化生成对应的对象
     * @param serData
     * @return
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static Object deserializeInto(byte[] serData) throws IOException, ClassNotFoundException {

        BufferedInputStream bis = new BufferedInputStream(new ByteArrayInputStream(serData));
        ObjectInputStream ois = new ObjectInputStream(bis);
        Object rv = ois.readObject();

        return rv;
    }

}
