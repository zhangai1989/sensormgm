package com.bumt.sensormgm.util;

import com.alibaba.druid.filter.config.ConfigTools;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Description:
 * @Author:     zhangai
 * @CreateDate: 2019/6/26 11:56
 * @Version: 1.0
 */
public class CommonUtil {


	private static final String ALGORITHM = "MD5";

	private static final char[] HEX_DIGITS = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

	/**
	 * encode string
	 *
	 * @param algorithm
	 * @param str
	 * @return String
	 */
	public static String encode(String algorithm, String str) {

		if (str == null) {
			return null;
		}
		try {
			MessageDigest messageDigest = MessageDigest.getInstance(algorithm);
			messageDigest.update(str.getBytes());
			return getFormattedText(messageDigest.digest());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

	/**
	 * encode By MD5
	 *
	 * @param str
	 * @return String
	 */
	public static String encodeByMD5(String str) {

		if (str == null) {
			return null;
		}
		try {
			MessageDigest messageDigest = MessageDigest.getInstance(ALGORITHM);
			messageDigest.update(str.getBytes());
			return getFormattedText(messageDigest.digest());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

	/**
	 * Takes the raw bytes from the digest and formats them correct.
	 *
	 * @param bytes
	 *            the raw bytes from the digest.
	 * @return the formatted bytes.
	 */
	private static String getFormattedText(byte[] bytes) {

		int len = bytes.length;
		StringBuilder buf = new StringBuilder(len * 2);
		// 把密文转换成十六进制的字符串形式
		for (int j = 0; j < len; j++) {
			buf.append(HEX_DIGITS[(bytes[j] << 4) & 0x0f]);
			buf.append(HEX_DIGITS[bytes[j] & 0x0f]);
		}
		return buf.toString();
	}


	
	
	/**
	 * 解密
	 * @param word
	 * @return
	 * @throws Exception
	 */
	public static String getDecrypt(String word) throws Exception {
		//解密
		String decryptword = ConfigTools.decrypt(word);
		return decryptword;
	}
	/**
	 *  加密
	 * @param password
	 * @return
	 * @throws Exception
	 */
	public static String getEncrypt(String password) throws Exception
	{
		//加密
		String encryptword = ConfigTools.encrypt(password);
		return encryptword;
	}


	public static String getThisMonth(){
		LocalDate today = LocalDate.now();
		LocalDate localDate = today.minusMonths(0);
		return localDate.toString().substring(0,7);
	}

	public static String getPreMonth(){
		LocalDate today = LocalDate.now();
		LocalDate localDate = today.minusMonths(1);
		return localDate.toString().substring(0,7);
	}

	public static String getPreTwoMonth(){
		LocalDate today = LocalDate.now();
		LocalDate localDate = today.minusMonths(2);
		return localDate.toString().substring(0,7);
	}

	public static String getPreYearMonth(){
		LocalDate today = LocalDate.now();
		LocalDate localDate = today.minusMonths(13);
		return localDate.toString().substring(0,7);
	}

	public static String getOnePreMonth(int pre){
		LocalDate today = LocalDate.now();
		LocalDate localDate = today.minusMonths(pre);
		return localDate.toString().substring(0,7);
	}

	/**
	 * 解密
	 * @param
	 * @return
	 * @throws Exception
	 */
	public static String getUUID(){
		UUID uuid=UUID.randomUUID();
		String str = uuid.toString();
		String uuidStr=str.replace("-", "");
		return uuidStr;
	}

	public static Date getNowDateTime(){
		Date day=new Date();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return day;
	}

	public static String getCodeByNowDateTime(){
		Date day=new Date();
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
		return df.format(day);
	}

	public static void main(String[] args) {
		System.out.println(getCodeByNowDateTime());
	}


	public static String repHc(String content){
		return content.replaceAll("\n","<br/>").replaceAll("\r","<br/>");
	}


	public static String getMd5ByFile(File file) throws FileNotFoundException {
		String value = null;
		FileInputStream in = new FileInputStream(file);
		try {
			MappedByteBuffer byteBuffer = in.getChannel().map(FileChannel.MapMode.READ_ONLY, 0, file.length());
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			md5.update(byteBuffer);
			BigInteger bi = new BigInteger(1, md5.digest());
			value = bi.toString(16);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(null != in) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return value.toUpperCase();
	}

	public static boolean checkMobileNumber(String mobileNumber){
		boolean flag = false;
		try{
			Pattern regex = Pattern.compile("^1([38]\\d|5[0-35-9]|7[3678])\\d{8}$");
			Matcher matcher = regex.matcher(mobileNumber);
			flag = matcher.matches();
		}catch(Exception e){
			flag = false;
		}
		return flag;
	}
	public static boolean checkEmail(String email){
		boolean flag = false;
		try{
			String check = "^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
			Pattern regex = Pattern.compile(check);
			Matcher matcher = regex.matcher(email);
			flag = matcher.matches();
		}catch(Exception e){
			flag = false;
		}
		return flag;
	}


	public static String getSpecifiedDayAfter(String specifiedDay){
		Calendar c = Calendar.getInstance();
		Date date=null;
		try {
			date = new SimpleDateFormat("yy-MM-dd").parse(specifiedDay);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		c.setTime(date);
		int day=c.get(Calendar.DATE);
		c.set(Calendar.DATE,day+1);

		String dayAfter=new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
		return dayAfter;
	}


	public static Date getDayByString(String dayStr){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = null; //初始化date
		try {
			date = sdf.parse(dayStr); //Mon Jan 14 00:00:00 CST 2013
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

}
