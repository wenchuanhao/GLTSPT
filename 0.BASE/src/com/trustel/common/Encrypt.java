package com.trustel.common;

import java.security.*;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;
/**
 * 
 * 加密解密
 *
 */
public class Encrypt {
	private final static String DES = "DES";
	 
    public static void main(String[] args) throws Exception {
        String data = "trustel123";
        String key = "chinaMobile";
        System.out.println(encrypt(data, key));
        System.out.println(decrypt(encrypt(data, key), key));
 
    }
     
    /**
     * Description 根据键值进行加密
     * @param data 
     * @param key  加密键byte数组
     * @return
     * @throws Exception
     */
    public static String encrypt(String data, String key) throws Exception {
        byte[] bt = encrypt(data.getBytes(), key.getBytes());
        String strs = new BASE64Encoder().encode(bt);
        return strs;
    }
 
    /**
     * Description 根据键值进行解密
     * @param data
     * @param key  加密键byte数组
     * @return
     * @throws IOException
     * @throws Exception
     */
    public static String decrypt(String data, String key) throws Exception {
        if (data == null)
            return null;
        BASE64Decoder decoder = new BASE64Decoder();
        byte[] buf = decoder.decodeBuffer(data);
        byte[] bt = decrypt(buf,key.getBytes());
        return new String(bt);
    }
 
    /**
     * Description 根据键值进行加密
     * @param data
     * @param key  加密键byte数组
     * @return
     * @throws Exception
     */
    private static byte[] encrypt(byte[] data, byte[] key) throws Exception {
        // 生成一个可信任的随机数源
        SecureRandom sr = new SecureRandom();
 
        // 从原始密钥数据创建DESKeySpec对象
        DESKeySpec dks = new DESKeySpec(key);
 
        // 创建一个密钥工厂，然后用它把DESKeySpec转换成SecretKey对象
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
        SecretKey securekey = keyFactory.generateSecret(dks);
 
        // Cipher对象实际完成加密操作
        Cipher cipher = Cipher.getInstance(DES);
 
        // 用密钥初始化Cipher对象
        cipher.init(Cipher.ENCRYPT_MODE, securekey, sr);
 
        return cipher.doFinal(data);
    }
     
     
    /**
     * Description 根据键值进行解密
     * @param data
     * @param key  加密键byte数组
     * @return
     * @throws Exception
     */
    private static byte[] decrypt(byte[] data, byte[] key) throws Exception {
        // 生成一个可信任的随机数源
        SecureRandom sr = new SecureRandom();
 
        // 从原始密钥数据创建DESKeySpec对象
        DESKeySpec dks = new DESKeySpec(key);
 
        // 创建一个密钥工厂，然后用它把DESKeySpec转换成SecretKey对象
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
        SecretKey securekey = keyFactory.generateSecret(dks);
 
        // Cipher对象实际完成解密操作
        Cipher cipher = Cipher.getInstance(DES);
 
        // 用密钥初始化Cipher对象
        cipher.init(Cipher.DECRYPT_MODE, securekey, sr);
 
        return cipher.doFinal(data);
    }
	
//	public static void main(String[] args) {
//		String key = "chinaMobile"; // 初始化密钥。
//		Encrypt encrypt = new Encrypt();
//		encrypt.setKey(key); // 调用set函数设置密钥。
////		encrypt.setEncString("trustel123");// 将要加密的明文传送给Encrypt.java进行加密计算。
////		String strMi = encrypt.getStrMi(); // 调用get函数获取加密后密文。
////		System.out.println("strMi = " + strMi);
//		String strMi = "afsuKIKobTl487GzZuRVOw==";
//		// 密文解密：
//		encrypt.setDesString(strMi); // 将要解密的密文传送给Encrypt.java进行解密计算。
//		String strM = encrypt.getStrM(); // 调用get函数获取解密后明文。
//		System.out.println("strM = " + strM);
//	}
//	
	//生成加密字符串
	public String ecrypt(String pwd) {
		String key = "chinaMobile"; // 初始化密钥。
		try {
			return encrypt(pwd, key);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
	
	//生成解密字符串
	public String dencrypt(String strMi) {
		String key = "chinaMobile"; // 初始化密钥。
		try {
			return decrypt(strMi, key);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
}
