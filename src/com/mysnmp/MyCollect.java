package com.mysnmp;

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

import com.snmp.DriverUsageBean;


/*
 * 封装CPU,内存，硬盘，虚拟内存的使用情况
 */
public class MyCollect {
	
	private List<Double> cpuRate = new ArrayList<Double>(70);//cpu
	private List<Double> memRate = new ArrayList<Double>(70);//内存
	private List<Double> memVruRate = new ArrayList<Double>(70);//虚拟内存
	private List<DriverUsageBean> driverInfo = null;//磁盘
	

	public List<Double> getMemVruRate() {
		return memVruRate;
	}
	public void setMemVruRate(List<Double> memVruRate) {
		this.memVruRate = memVruRate;
	}
	private Date   date;
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public List<DriverUsageBean> getDriverInfo() {
		return driverInfo;
	}
	public void setDriverInfo(List<DriverUsageBean> driverInfo) {
		this.driverInfo = driverInfo;
	}
	public List<Double> getCpuRate() {
		return cpuRate;
	}
	public void setCpuRate(List<Double> cpuRate) {
		this.cpuRate = cpuRate;
	}
	public List<Double> getMemRate() {
		return memRate;
	}
	public void setMemRate(List<Double> memRate) {
		this.memRate = memRate;
	}
	             static Snmp snmp=null;
	             static TransportMapping transport=null;
	            
	             static long timeout=8000;
	              static String community="public";
	          	static TableUtils tableUtils=null;
	              //获取CPU的使用率
		          String[] oidsCpu = {"1.3.6.1.2.1.25.3.3.1.2"};  
				//获取内存的使用率
		      	String[] oids= {"1.3.6.1.2.1.25.2.3.1.2",  //type 存储单元类型  
		   		     "1.3.6.1.2.1.25.2.3.1.3",  //descr  
		   		     "1.3.6.1.2.1.25.2.3.1.4",  //unit 存储单元大小  
		   		     "1.3.6.1.2.1.25.2.3.1.5",  //size 总存储单元数  
		   		     "1.3.6.1.2.1.25.2.3.1.6"}; //used 使用存储单元数;  
		   		String PHYSICAL_MEMORY_OID = "1.3.6.1.2.1.25.2.1.2";//物理存储  
		   		String VIRTUAL_MEMORY_OID = "1.3.6.1.2.1.25.2.1.3"; //虚拟存储  
		   		String DISK_OID = "1.3.6.1.2.1.25.2.1.4";//磁盘大小
		          //获取硬盘的使用率
		          //获取swap的使用率
		      	CommunityTarget target=null;
		     
	        public CommunityTarget getTarget() {
					return target;
				}

				public void setTarget(CommunityTarget target) {
					this.target = target;
				}

				
		//初始化目标对象
		public 	CommunityTarget  initComm(String ip) {	
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
		 	     tableUtils = new TableUtils(snmp, new PDUFactory() {  
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
		 	  
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return target;
			
	 	
    }
	     //返回CPU利用率
	     public List<Double> collectCPU(CommunityTarget target) {
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
	    		if(this.getCpuRate().size()<60) {
	    			this.getCpuRate().add((double) percentage);
	    		}
	    		this.getCpuRate().remove(0);
	    		this.getCpuRate().add((double) percentage);
				return cpuRate;
			
	    	
	     }
	     
	     public List<Double> collectMemory(CommunityTarget target) {
	    	 OID[] columns = new OID[oids.length];  
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
	 			if(this.getMemRate().size()<60) {
	 			this.getMemRate().add((double)usedSize*100/totalSize);
	 			}else {
	 				this.memRate.remove(0);
	 				this.getMemRate().add((double)usedSize*100/totalSize);
	 			}
	 		//System.out.println("PHYSICAL_MEMORY----->物理内存大小："+(long)totalSize * unit/(1024*1024*1024)+"G   内存使用率为："+(long)usedSize*100/totalSize+"%");  
	 		}else if (VIRTUAL_MEMORY_OID.equals(oid)) {  
	 		//System.out.println("VIRTUAL_MEMORY----->虚拟内存大小："+(long)totalSize * unit/(1024*1024*1024)+"G   内存使用率为："+(long)usedSize*100/totalSize+"%");  
	 			if(this.getMemVruRate().size()<60) {
		 			this.getMemVruRate().add((double)usedSize*100/totalSize);
		 			}else {
		 				this.memRate.remove(0);
		 				this.getMemVruRate().add((double)usedSize*100/totalSize);
		 			}
	 		
	 		     }  
	 	
	           }
	 		}
			return this.getMemRate() ;	
	    }
	     public 	List<DriverUsageBean> collectDisk(CommunityTarget target) {
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
}

