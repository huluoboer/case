package com.mysnmp;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.snmp.DriverUsageBean;

import java.util.TimerTask;

public class TestMy extends TimerTask {

	List<MyCollect> list = new ArrayList<>();
	List<String> ips = new ArrayList<>();
	 public TestMy() {
		 ips.add("127.0.0.1");
		 ips.add("192.168.0.25");
	for (String ip : ips) {
		MyCollect me = new MyCollect();
		me.initComm(ip);
		list.add(me);
	  }
	 }
	@Override
	public void run() {
		synchronized (this) {
		    for (MyCollect coll : list) {
			List<Double> collectMemory = coll.collectMemory(coll.getTarget());
		    List<Double> collectCPU = coll.collectCPU(coll.getTarget());
		    List<DriverUsageBean> collectDisk = coll.collectDisk(coll.getTarget());
		List values=new ArrayList<>();
		values.add( collectMemory);
		values.add(collectCPU);
		//System.out.println(values.size());
		values.add(collectDisk);
	
		System.out.println(values.toString());
		//	System.out.println(coll.getMemRate().toString());
		}
        }
		
	}
}