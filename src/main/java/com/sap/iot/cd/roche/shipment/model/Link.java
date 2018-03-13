package com.sap.iot.cd.roche.shipment.model;

public class Link {
	private String containerId;
	private String loggerId;
	public String getContainerId() {
		return containerId;
	}
	public void setContainerId(String containerId) {
		this.containerId = containerId;
	}
	public String getLoggerId() {
		return loggerId;
	}
	public void setLoggerId(String loggerId) {
		this.loggerId = loggerId;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((containerId == null) ? 0 : containerId.hashCode());
		result = prime * result + ((loggerId == null) ? 0 : loggerId.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Link other = (Link) obj;
		if (containerId == null) {
			if (other.containerId != null)
				return false;
		} else if (!containerId.equals(other.containerId))
			return false;
		if (loggerId == null) {
			if (other.loggerId != null)
				return false;
		} else if (!loggerId.equals(other.loggerId))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Link [containerId=" + containerId + ", loggerId=" + loggerId + "]";
	}
}
