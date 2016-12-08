package com.tom.msg;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public class test {

	public static void main(String[] args) {
		String s = "%3F%3F%3F%3F%3F%3F%3F%3F%3F%3F";
		try {
			System.out.println(URLDecoder.decode(s,"utf-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
