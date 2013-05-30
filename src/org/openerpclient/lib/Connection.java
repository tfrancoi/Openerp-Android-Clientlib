package org.openerpclient.lib;

import org.openerpclient.lib.jsonrpc.JSONRPCConnector;
import org.openerpclient.lib.xmlrpc.XmlRpcConnector;

public class Connection {
	private String host;
	private int port;
	private String username;
	private String password;
	private String database;
	private int uid = 0;
	
	private Connector connector;
	

	
	public Connection(String host, int port, String database, String username, String password, ConnectorType connectorType) {
		this.host = host;
		this.port = port;
		this.database = database;
		this.username = username;
		this.database = database;
		this.password = password;
		
		switch (connectorType) {
		case JSONRPC:
			this.connector = new JSONRPCConnector(this.host, this.port);
			break;
		case XMLRPC:
			this.connector = new XmlRpcConnector(this.host, this.port);
			break;
		default:
			throw new UnsupportedOperationException("Not yet implemented");
		}
	}
	
	public boolean login()  {
		try {
			Service common = this.getService(Service.COMMON);
			this.uid = (Integer) common.call("login", this.database, this.username, this.password);
			System.out.println("Connection successfull " + uid);
			return true;
		}
		catch(ConnectorException e) {
			e.printStackTrace();
			System.out.println("Connection Failed");
			return false;
		}
	}
	
	public String getDatabase() {
		return database;
	}
	
	public String getPassword() {
		return password;
	}
	
	public int getUid() {
		return uid;
	}
	
	public Connector getConnector() {
		return connector;
	}
	
	
	
	public Model getModel(String model) {
		return new Model(model, this);
	}
	
	public Service getService(String serviceName) {
		return new Service(serviceName, this.connector);
	}
}
	
	
	

