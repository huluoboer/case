package com.snmp;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.snmp4j.CommunityTarget;
import org.snmp4j.PDU;
import org.snmp4j.Snmp;
import org.snmp4j.Target;
import org.snmp4j.TransportMapping;
import org.snmp4j.mp.MessageProcessingModel;
import org.snmp4j.mp.SnmpConstants;
import org.snmp4j.smi.GenericAddress;
import org.snmp4j.smi.OID;
import org.snmp4j.smi.OctetString;
import org.snmp4j.smi.VariableBinding;
import org.snmp4j.transport.DefaultUdpTransportMapping;
import org.snmp4j.util.PDUFactory;
import org.snmp4j.util.TableEvent;
import org.snmp4j.util.TableUtils;

public class SnmpUtils {
		         
		      
		          
		      
		              //获取CPU的使用率
		          static    String[] oidsCpu = {"1.3.6.1.2.1.25.3.3.1.2"};  
					//获取内存的使用率
		          static  	String[] oids= {"1.3.6.1.2.1.25.2.3.1.2",  //type 存储单元类型  
			   		     "1.3.6.1.2.1.25.2.3.1.3",  //descr  
			   		     "1.3.6.1.2.1.25.2.3.1.4",  //unit 存储单元大小  
			   		     "1.3.6.1.2.1.25.2.3.1.5",  //size 总存储单元数  
			   		     "1.3.6.1.2.1.25.2.3.1.6"}; //used 使用存储单元数;  
		          static	String PHYSICAL_MEMORY_OID = "1.3.6.1.2.1.25.2.1.2";//物理存储  
		          static		String VIRTUAL_MEMORY_OID = "1.3.6.1.2.1.25.2.1.3"; //虚拟存储  
		          static String DISK_OID = "1.3.6.1.2.1.25.2.1.4";//磁盘大小
			          //获取硬盘的使用率
			          //获取swap的使用率
		        
			     
		       
					
		
		     //返回CPU利用率
		     public   static double collectCPU(CommunityTarget target, TableUtils tableUtils) {
		    	  int percentage = 0;  
		    		OID[] columns = new OID[oidsCpu.length];  
		    		for (int i = 0; i < oidsCpu.length; i++)  
		    		columns[i] = new OID(oidsCpu[i]);  
		    		List<TableEvent> list = tableUtils.getTable(target, columns, null, null);  
		    		if(list.size()==1 && list.get(0).getColumns()==null){  
		    	
		    		}else{  	
		    		for(TableEvent event : list){  
		    		VariableBinding[] values = event.getColumns();  
		    		if(values != null)   
		    		percentage += Integer.parseInt(values[0].getVariable().toString());  
		    		}  
		    		//System.out.println("CPU利用率为："+percentage/list.size()+"%");  
		    		}
		    	
					return	percentage;
				
		    	
		     }
		     
		   
		     //硬盘
		     public 	  static List<DriverUsageBean> collectDisk(CommunityTarget target, TableUtils tableUtils) {
		    		List<DriverUsageBean>	driverInfo=null;
		    	 OID[] columns = new OID[oids.length];  
		 		for (int i = 0; i < oids.length; i++)  
		 		columns[i] = new OID(oids[i]);  
		 		@SuppressWarnings("unchecked")  
		 		List<TableEvent> list = tableUtils.getTable(target, columns, null, null);  
		 		if(list.size()==1 && list.get(0).getColumns()==null){  
		 	
		 		}else{  
		 			driverInfo=new ArrayList<>();
		 		for(TableEvent event : list){  
		 		VariableBinding[] values = event.getColumns();  
		 		if(values == null ||!DISK_OID.equals(values[0].getVariable().toString()))   
		 		continue;  
		 		int unit = Integer.parseInt(values[2].getVariable().toString());//unit 存储单元大小  
		 		int totalSize = Integer.parseInt(values[3].getVariable().toString());//size 总存储单元数  
		 		int usedSize = Integer.parseInt(values[4].getVariable().toString());//used  使用存储单元数  
		 	//	System.out.println((values[1].getVariable().toString())+"   磁盘大小："+(long)totalSize*unit/(1024*1024*1024)+"G   磁盘使用率为："+(long)usedSize*100/totalSize+"%");  
		 		DriverUsageBean  dub = new DriverUsageBean();
		 	
		 		dub.setDirverName(values[1].getVariable().toString());
		 		dub.setTotal((double)totalSize*unit/(1024*1024*1024));
		 		dub.setUsePercent((double)usedSize*100/totalSize);
		 	    driverInfo.add(dub);
		 		}  
		 		}
				return driverInfo;  
		 		
		     }
		     
		     
		     
		     
//内存
public     static DriverUsageBean collectMemory(CommunityTarget target, TableUtils tableUtils) {
	 OID[] columns = new OID[oids.length];  
	 DriverUsageBean  dub = new DriverUsageBean();
	for (int i = 0; i < oids.length; i++)  
	columns[i] = new OID(oids[i]);  
	@SuppressWarnings("unchecked")  
	List<TableEvent> list = tableUtils.getTable(target, columns, null, null);  
	if(list.size()==1 && list.get(0).getColumns()==null){  

	}else{  
	
	for(TableEvent event : list){  
	VariableBinding[] values = event.getColumns();  
	if(values == null) continue;  
	int unit = Integer.parseInt(values[2].getVariable().toString());//unit 存储单元大小  
	int totalSize = Integer.parseInt(values[3].getVariable().toString());//size 总存储单元数  
	int usedSize = Integer.parseInt(values[4].getVariable().toString());//used  使用存储单元数  
	String oid = values[0].getVariable().toString();  
	if (PHYSICAL_MEMORY_OID.equals(oid)){  
		dub.setTotal((double)totalSize * unit/(1024*1024*1024));
		dub.setUsePercent((double)usedSize*100/totalSize);
	//System.out.println("PHYSICAL_MEMORY----->物理内存大小："+(long)totalSize * unit/(1024*1024*1024)+"G   内存使用率为："+(long)usedSize*100/totalSize+"%");  
	}

      }
	}
	return dub;
	

}

//虚拟内存
public   static List<DriverUsageBean> collectVMemory(CommunityTarget target, TableUtils tableUtils) {
	List<DriverUsageBean>	driver=null;
	 OID[] columns = new OID[oids.length];  
	for (int i = 0; i < oids.length; i++)  
	columns[i] = new OID(oids[i]);  
	@SuppressWarnings("unchecked")  
	List<TableEvent> list = tableUtils.getTable(target, columns, null, null);  
	if(list.size()==1 && list.get(0).getColumns()==null){  
	}else{  
		driver=new ArrayList<>();
	for(TableEvent event : list){  
	VariableBinding[] values = event.getColumns();  
	if(values == null) continue;  
	int unit = Integer.parseInt(values[2].getVariable().toString());//unit 存储单元大小  
	int totalSize = Integer.parseInt(values[3].getVariable().toString());//size 总存储单元数  
	int usedSize = Integer.parseInt(values[4].getVariable().toString());//used  使用存储单元数  
	String oid = values[0].getVariable().toString();  
 if (VIRTUAL_MEMORY_OID.equals(oid)) {  
	//System.out.println("VIRTUAL_MEMORY----->虚拟内存大小："+(long)totalSize * unit/(1024*1024*1024)+"G   内存使用率为："+(long)usedSize*100/totalSize+"%");  
	    DriverUsageBean  dub = new DriverUsageBean();
		dub.setDirverName(values[1].getVariable().toString());
		dub.setTotal((double)totalSize*unit/(1024*1024*1024));
		dub.setUsePercent((double)usedSize*100/totalSize);
	    driver.add(dub);
	     }  
      }
	}
	return  driver;
}
		     
 }


