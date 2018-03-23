package com.meraas.entity;

import java.io.Serializable;


public class Path implements Serializable {
	private static final long serialVersionUID = 1L;

	private String pathId;
	
	private String layerName;

	private String status;
	
	private String info;
	
	public Path(){
		
	}

	public Path(String pathId, String layerName, String status, String info) {
		super();
		this.pathId = pathId;
		this.layerName = layerName;
		this.status = status;
		this.info = info;
	}

	public String getPathId() {
		return pathId;
	}

	public void setPathId(String pathId) {
		this.pathId = pathId;
	}

	public String getLayerName() {
		return layerName;
	}

	public void setLayerName(String layerName) {
		this.layerName = layerName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}
	
}
