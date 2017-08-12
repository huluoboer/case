package com.snmp;

public class DriverUsageBean{

	private String dirverName;
	private Double total;
	private Double usePercent;
	public Double getTotal() {
		return total;
	}
	public void setTotal(Double total) {
		this.total = total;
	}
	public Double getUsePercent() {
		return usePercent;
	}
	public void setUsePercent(Double usePercent) {
		this.usePercent = usePercent;
	}
	public String getDirverName() {
		return dirverName;
	}
	public void setDirverName(String dirverName) {
		this.dirverName = dirverName;
	}
	@Override
	public String toString() {
		return "DriverUsageBean [dirverName=" + dirverName + ", total=" + total + ", usePercent=" + usePercent + "]";
	}
	
}
