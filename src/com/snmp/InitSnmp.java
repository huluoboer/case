package com.snmp;

import java.io.IOException;

import org.snmp4j.CommunityTarget;
import org.snmp4j.PDU;
import org.snmp4j.Snmp;
import org.snmp4j.Target;
import org.snmp4j.TransportMapping;
import org.snmp4j.mp.MessageProcessingModel;
import org.snmp4j.mp.SnmpConstants;
import org.snmp4j.smi.GenericAddress;
import org.snmp4j.smi.OctetString;
import org.snmp4j.transport.DefaultUdpTransportMapping;
import org.snmp4j.util.PDUFactory;
import org.snmp4j.util.TableUtils;

public class InitSnmp {
	      TransportMapping transport=null;
	      Snmp snmp=null;
	     static  int timeout=8000;
	     static  String community="public";
        CommunityTarget target=null;
   	 TableUtils tableUtils=null;
	//初始化目标对象
	public 	  CommunityTarget  initComm(String ip) {	
 		try {
			transport = new DefaultUdpTransportMapping();
			snmp = new Snmp(transport);//创建snmp  
	 		snmp.listen();//监听消息  
	 	    target = new CommunityTarget();  
	 		target.setCommunity(new OctetString(community));  
	 		target.setRetries(2);  
	 		target.setAddress(GenericAddress.parse("udp:"+ip+"//161"));  
	 		target.setTimeout( timeout);  
	 		target.setVersion(SnmpConstants.version2c);  
	 	
	 	  
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return target;
		
 	
    }
	
	
	public TableUtils  get() {
	     return tableUtils = new TableUtils(snmp, new PDUFactory() {  
		 		@Override  
		 		public PDU createPDU(Target arg0) {  
		 		PDU request = new PDU();  
		 		request.setType(PDU.GET);  
		 		return request;  
		 		}

		 		@Override
		 		public PDU createPDU(MessageProcessingModel arg0) {
		 			// TODO Auto-generated method stub
		 			return null;
		 		}  
		 		});  
	}
}
