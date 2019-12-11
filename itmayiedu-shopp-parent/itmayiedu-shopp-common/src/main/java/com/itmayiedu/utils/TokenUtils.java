package com.itmayiedu.utils;

import java.util.UUID;

import com.itmayiedu.constants.Constants;

public class TokenUtils {

	 public static String getMemberToken(){
		 return Constants.TOKEN_MEMBER+"-"+UUID.randomUUID();
	 }
	
}
