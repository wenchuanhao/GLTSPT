package com.cdc.util;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

/**
 * DES加解密
 * @author ZENGKAI
 * @date 2016-05-03 11:34:29
 */
public class DesUtil {
	 	public final static String DES_KEY_STRING = "Awe89gzS";
     
	 	/**
	 	 * 加密
	 	 * @param message	明文
	 	 * @param key	秘钥
	 	 * @return
	 	 * @throws Exception
	 	 */
	    public static String encrypt(String message, String key) throws Exception {
	        Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
	 
	        DESKeySpec desKeySpec = new DESKeySpec(key.getBytes("UTF-8"));
	 
	        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
	        SecretKey secretKey = keyFactory.generateSecret(desKeySpec);
	        IvParameterSpec iv = new IvParameterSpec(key.getBytes("UTF-8"));
	        cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv);
	 
	        return encodeBase64(cipher.doFinal(message.getBytes("UTF-8")));
	    }
	 
	    /**
	     * 解密
	     * @param message	密文
	     * @param key	秘钥
	     * @return
	     * @throws Exception
	     */
	    public static String decrypt(String message, String key) throws Exception {
	 
	        byte[] bytesrc = decodeBase64(message);//convertHexString(message);
	        Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
	        DESKeySpec desKeySpec = new DESKeySpec(key.getBytes("UTF-8"));
	        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
	        SecretKey secretKey = keyFactory.generateSecret(desKeySpec);
	        IvParameterSpec iv = new IvParameterSpec(key.getBytes("UTF-8"));
	 
	        cipher.init(Cipher.DECRYPT_MODE, secretKey, iv);
	 
	        byte[] retByte = cipher.doFinal(bytesrc);
	        return new String(retByte);
	    }
	 
	    public static byte[] convertHexString(String ss) {
	        byte digest[] = new byte[ss.length() / 2];
	        for (int i = 0; i < digest.length; i++) {
	            String byteString = ss.substring(2 * i, 2 * i + 2);
	            int byteValue = Integer.parseInt(byteString, 16);
	            digest[i] = (byte) byteValue;
	        }
	 
	        return digest;
	    }
	 
	    public static String toHexString(byte b[]) {
	        StringBuffer hexString = new StringBuffer();
	        for (int i = 0; i < b.length; i++) {
	            String plainText = Integer.toHexString(0xff & b[i]);
	            if (plainText.length() < 2)
	                plainText = "0" + plainText;
	            hexString.append(plainText);
	        }
	 
	        return hexString.toString();
	    }
	 
	     
	    public static String encodeBase64(byte[] b) {
	        return Base64Util.encode(b);
	    }
	     
	    public static byte[] decodeBase64(String base64String) {
	        return Base64Util.decode(base64String);
	    }
	    
	    public static void main(String[] args){
	    	DesUtil des = new DesUtil();
        	try {
        		System.out.println(des.encrypt("13822221860","Awe89gzS"));
	        	System.out.println(des.decrypt("IIuQR63dlQip1EoA1RMhMA==","Awe89gzS"));
				/*System.out.println(des.decrypt("Lw/VMM/j1L9ddQsGyigHJ9XRIEVen1PY6otYOl7po8EeUa9b5u/mMQ==","Awe89gzS"));
	        	System.out.println(des.decrypt("sBhAq9QA5PQ=","Awe89gzS"));
	        	System.out.println(des.decrypt("fAiWmMNXPh8=","Awe89gzS"));*/
//	        	System.out.println(des.decrypt("abcdefgh", des.encrypt("abcdefgh","test")));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

	    }
}
