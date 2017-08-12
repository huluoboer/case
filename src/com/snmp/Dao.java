package com.snmp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.xml.crypto.Data;

import org.snmp4j.CommunityTarget;
import org.snmp4j.util.TableUtils;

import com.google.gson.Gson;
public class Dao {
	private String ip;
	private List<DataSnmp> list = new ArrayList<DataSnmp>();
	private  ScheduledExecutorService scheduExec =Executors.newScheduledThreadPool(2);;
	public Dao(String ip) {
		this.ip=ip;
	}
	public void timer(){ 
        scheduExec.scheduleAtFixedRate(new Runnable() { 
            public void run() { 
            	DataSnmp  data = new DataSnmp();
            	InitSnmp init = new InitSnmp();
                CommunityTarget target = init.initComm(ip);
                TableUtils tableUtils = init.get();
            	data.setTime(new Date());
            	data.setCpu(	SnmpUtils.collectCPU(target,tableUtils));
            	data.setVmem(	SnmpUtils.collectVMemory(target,tableUtils));
            	 list.add(data);
            	 Gson gson = new Gson();
            	 System.out.println( gson.toJson(list));
            } 
        },2000,5000,TimeUnit.MILLISECONDS); 

    }  
}
