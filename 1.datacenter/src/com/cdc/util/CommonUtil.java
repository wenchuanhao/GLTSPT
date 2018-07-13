package com.cdc.util;

import java.io.IOException;
import java.io.InputStream;

/**
 * 
 * @author ZENGKAI
 * @date 2016-04-07 17:04:29
 */
public class CommonUtil {

	private static String bytesToHexString(byte[] src) {
		StringBuilder stringBuilder = new StringBuilder();
		if (src == null || src.length <= 0) {
			return null;
		}
		for (int i = 0; i < src.length; i++) {
			int v = src[i] & 0xFF;
			String hv = Integer.toHexString(v);
			if (hv.length() < 2) {
				stringBuilder.append(0);
			}
			stringBuilder.append(hv);
		}
		return stringBuilder.toString();
	}

	public static boolean checkFileType(String fileName, InputStream fis) {
		boolean result = false;
		byte[] b = new byte[4];

		try {
			if (fileName != null && fileName.toLowerCase().contains(".doc")) {
				result = true;
			} else if (fileName != null
					&& fileName.toLowerCase().contains(".docx")) {
				result = true;
			} else if (fileName != null
					&& fileName.toLowerCase().contains(".xls")) {
				result = true;
			} else if (fileName != null
					&& fileName.toLowerCase().contains(".xlsx")) {
				result = true;
			} else if (fileName != null
					&& fileName.toLowerCase().contains(".ppt")) {
				result = true;
			} else if (fileName != null
					&& fileName.toLowerCase().contains(".pptx")) {
				result = true;
			} else if (fileName != null
					&& fileName.toLowerCase().contains(".rar")) {
				result = true;
			} else if (fileName != null
					&& fileName.toLowerCase().contains(".zip")) {
				result = true;
			} else if (fileName != null
					&& fileName.toLowerCase().contains(".7z")) {
				result = true;
			} else if (fileName != null
					&& fileName.toLowerCase().contains(".jpg")) {
				fis.read(b, 0, b.length);
				String type = bytesToHexString(b).toUpperCase();
				System.out.println(type);
				if (type.contains("FFD8FF")) {
					result = true;
				}
			} else if (fileName != null
					&& fileName.toLowerCase().contains(".jpeg")) {
				result = true;
			} else if (fileName != null
					&& fileName.toLowerCase().contains(".bmp")) {
				fis.read(b, 0, b.length);
				String type = bytesToHexString(b).toUpperCase();
				if (type.contains("424D38")) {
					result = true;
				}
			} else if (fileName != null
					&& fileName.toLowerCase().contains(".png")) {
				fis.read(b, 0, b.length);
				String type = bytesToHexString(b).toUpperCase();
				if (type.contains("89504E")) {
					result = true;
				}
			} else if (fileName != null
					&& fileName.toLowerCase().contains(".gif")) {
				fis.read(b, 0, b.length);
				String type = bytesToHexString(b).toUpperCase();
				if (type.contains("474946")) {
					result = true;
				}
			} else if (fileName != null
					&& fileName.toLowerCase().contains(".txt")) {
				result = true;
			} else if (fileName != null
					&& fileName.toLowerCase().contains(".html")) {
				fis.read(b, 0, b.length);
				String type = bytesToHexString(b).toUpperCase();
				if (type.contains("3C212D2D") || type.contains("3C21444F")) {
				}
				result = true;
			} else if (fileName != null
					&& fileName.toLowerCase().contains(".xhtml")) {
				fis.read(b, 0, b.length);
				String type = bytesToHexString(b).toUpperCase();
				if (type.contains("3C3F786D")) {
					result = true;
				}
			} else if (fileName != null
					&& fileName.toLowerCase().contains(".xml")) {
				fis.read(b, 0, b.length);
				String type = bytesToHexString(b).toUpperCase();
				if (type.contains("3C3F786D")) {
					result = true;
				}
			} else if (fileName != null
					&& fileName.toLowerCase().contains(".pdf")) {
				fis.read(b, 0, b.length);
				String type = bytesToHexString(b).toUpperCase();
				System.out.println(type);
				if (type.contains("25504446")) {
					result = true;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return result;
	}
	
	/**
	 * 首字母大写
	 * 
	 * @param srcStr
	 * @return
	 */
	public static String firstCharacterToUpper(String srcStr) {
		return srcStr.substring(0, 1).toUpperCase() + srcStr.substring(1);
	}

	/**
	 * 替换字符串并让它的下一个字母为大写
	 * 
	 * @param srcStr
	 * @param org
	 * @param ob
	 * @return
	 */
	public static String replaceUnderlineAndfirstToUpper(String srcStr,
			String org, String ob) {
		srcStr = srcStr.toLowerCase();
		String newString = "";
		int first = 0;
		while (srcStr.indexOf(org) != -1) {
			first = srcStr.indexOf(org);
			if (first != srcStr.length()) {
				newString = newString + srcStr.substring(0, first) + ob;
				srcStr = srcStr
						.substring(first + org.length(), srcStr.length());
				srcStr = firstCharacterToUpper(srcStr);
			}
		}
		newString = newString + srcStr;
		return newString;
	}
}
