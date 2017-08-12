package com.snmp;

import java.util.Date;
import java.util.List;



public class DataSnmp {
  private Date time;
 private  Double  cpu;
  private  List<DriverUsageBean> vmem;
  private DriverUsageBean mem;
  private  List<DriverUsageBean> disk;
public Double getCpu() {
	return cpu;
}

public void setCpu(Double cpu) {
	this.cpu = cpu;
}
public List<DriverUsageBean> getVmem() {
	return vmem;
}
public void setVmem(List<DriverUsageBean> vmem) {
	this.vmem = vmem;
}
public DriverUsageBean getMem() {
	return mem;
}
public void setMem(DriverUsageBean mem) {
	this.mem = mem;
}
public List<DriverUsageBean> getDisk() {
	return disk;
}
public void setDisk(List<DriverUsageBean> disk) {
	this.disk = disk;
}
public Date getTime() {
	return time;
}
public void setTime(Date time) {
	this.time = time;
}
  
}
