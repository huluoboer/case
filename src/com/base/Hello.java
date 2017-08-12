package com.base;

import java.util.ArrayList;
import java.util.List;

public class Hello {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		  List<String> list = new ArrayList<>();
		  list.add("123");
		  list.add("456");
		  list.add("789");
		  list.add("000");
	
	  for (String string : list) {
		  char[] res=string.toCharArray();
		System.out.println(res[0]);
	}

	}

}
