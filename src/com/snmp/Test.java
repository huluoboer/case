package com.snmp;

import java.util.ArrayList;
import java.util.List;
public class Test {
   public static void main(String[] args) {
	  List<String> ip = new ArrayList<>();
	  ip.add("192.168.0.25");
	  ip.add("127.0.0.1");
	  for (int i = 0; i < ip.size(); i++) {
		  Dao dao = new Dao(ip.get(i));
		   dao.timer();
	}
	  
}
}