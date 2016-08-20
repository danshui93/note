package com.zf.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.tomcat.util.codec.binary.Base64;

import sun.misc.BASE64Encoder;

public class WebUtils {
	public static String encode(String msg){
		try {
			MessageDigest digest = MessageDigest.getInstance("md5");
			return Base64.encodeBase64String(digest.digest(msg.getBytes()));
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	public static boolean isNull(String msg){
		return msg==null||msg.equals("");
	}
	
	public static String getNow(){
		return new SimpleDateFormat("yyyyMMddHHmmssSS").format(new Date());
	}
	
	public static String getDateStr(Date date,String pattern){		
		return  new SimpleDateFormat(pattern).format(date);
	}
}
