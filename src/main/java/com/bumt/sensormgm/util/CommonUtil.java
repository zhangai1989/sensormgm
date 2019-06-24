package com.bumt.sensormgm.util;

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
 * @author : zhangai
 * @desaciption :
 * @date : Create in 11:34$ 2018/5/4$
 */
public class CommonUtil {


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

	public static void main(String[] args) throws IOException {

		Map<String,Object> entity = new HashMap<>();
		System.out.println(entity.hashCode());

		entity.put("123","2343");
		System.out.println(entity.hashCode());

		Map<String,Object> entity2 = new HashMap<>();
		System.out.println(entity2.hashCode());
		entity2.put("123","234");
		System.out.println(entity2.hashCode());

		System.out.println(entity==entity2);

		//BBB0411C4E2D85602E412A082EEA9EF1 BBB0411C4E2D85602E412A082EEA9EF1  A83296E8FC888BC6597F3872C1566D16
		//BEEBA1BD768E055B0415AE2D943AB1F9


		//System.out.println("MD5:"+DigestUtils.md5Hex("WANGQIUYUN"));
	}
}
