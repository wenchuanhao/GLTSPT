package com.trustel.common;

public class ContentType {
	public static String getContentType(String ext) {
		if (ext.equalsIgnoreCase("doc"))
			return "application/msword";
		else if (ext.equalsIgnoreCase("ppt"))
			return "application/vnd.ms-powerpoint";
		else if (ext.equalsIgnoreCase("xls"))
			return "application/vnd.ms-excel";
		else if (ext.equalsIgnoreCase("pdf"))
			return "application/pdf";
		else if (ext.equalsIgnoreCase("zip"))
			return "application/zip";
		else if (ext.equalsIgnoreCase("rar"))
			return "application/x-rar-compressed";
		else if (ext.equalsIgnoreCase("exe"))
			return "application/octet-stream";
		else if (ext.equalsIgnoreCase("z"))
			return "application/x-compress";
		else if (ext.equalsIgnoreCase("gz"))
			return "application/x-gzip";
		else if (ext.equalsIgnoreCase("gzip"))
			return "application/x-gzip";
		else if (ext.equalsIgnoreCase("tgz"))
			return "application/x-gzip";
		else if (ext.equalsIgnoreCase("tar"))
			return "application/x-tar";
		else if (ext.equalsIgnoreCase("js"))
			return "application/x-javascript";
		else if (ext.equalsIgnoreCase("swf"))
			return "application/x-shockwave-flash";
		else if (ext.equalsIgnoreCase("mid"))
			return "audio/midi";
		else if (ext.equalsIgnoreCase("midi"))
			return "audio/midi";
		else if (ext.equalsIgnoreCase("mp3"))
			return "audio/mpeg";
		else if (ext.equalsIgnoreCase("rm"))
			return "audio/x-pn-realaudio";
		else if (ext.equalsIgnoreCase("wav"))
			return "audio/x-wav";
		else if (ext.equalsIgnoreCase("bmp"))
			return "image/bmp";
		else if (ext.equalsIgnoreCase("gif"))
			return "image/gif";
		else if (ext.equalsIgnoreCase("jpg"))
			return "image/jpeg";
		else if (ext.equalsIgnoreCase("png"))
			return "image/png";
		else if (ext.equalsIgnoreCase("tiff"))
			return "image/tiff";
		else if (ext.equalsIgnoreCase("tif"))
			return "'image/tiff'";
		else if (ext.equalsIgnoreCase("txt"))
			return "text/plain";
		else if (ext.equalsIgnoreCase("htm"))
			return "text/html";
		else if (ext.equalsIgnoreCase("html"))
			return "text/html";
		else if (ext.equalsIgnoreCase("rtf"))
			return "text/rtf";
		else if (ext.equalsIgnoreCase("xml"))
			return "text/xml";
		else if (ext.equalsIgnoreCase("xsl"))
			return "text/xml";
		else if (ext.equalsIgnoreCase("mpeg"))
			return "video/mpeg";
		else if (ext.equalsIgnoreCase("mpg"))
			return "'video/mpeg'";
		else if (ext.equalsIgnoreCase("mov"))
			return "video/quicktime";
		else if (ext.equalsIgnoreCase("avi"))
			return "video/x-msvideo";
		else if (ext.equalsIgnoreCase("movie"))
			return "video/x-sgi-movie";
		else if (ext.equalsIgnoreCase("sh"))
			return "application/x-sh";
		else
			return "application/octet-stream";
	}
}
