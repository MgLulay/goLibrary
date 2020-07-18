package com.mobility.library.utility;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class SystemUtility {

    public static String keygen() {
      SimpleDateFormat l_format = new SimpleDateFormat("yyMMdd");
      String l_formatdate = l_format.format(new Date());

      SimpleDateFormat l_time= new SimpleDateFormat("HHmmssSSSS");
      String l_timeformat = l_time.format(new Date());

      Random number = new Random();
      Integer l_random =  number.nextInt(Integer.MAX_VALUE);
      String l_randomNum = l_random.toString();
      l_randomNum = l_randomNum.substring(l_randomNum.length()-4, l_randomNum.length());

      return  l_formatdate+l_timeformat+l_randomNum;
    }
  

	public static String getCurrentDate() {
		return new SimpleDateFormat("yyyyMMdd").format(new Date());
	}

	public static String getCurrentTime() {
		return new SimpleDateFormat("HHmmss").format(new Date());
	}

}
