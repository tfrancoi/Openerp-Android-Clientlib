package org.openerpclient.lib;

public class Service {
	private String name;
	private Connector connector;
	
	public Service(String name, Connector connector) {
		this.name = name;
		this.connector = connector;
	}
	
	Object call(String method, Object... args) throws ConnectorException {
		return connector.call(this.name, method, args);
	}
	
	
	public static final String DATABASE = "database";
	public static final String COMMON = "common";
	public static final String OBJECT = "object";
	public static final String REPORT = "report";
}
