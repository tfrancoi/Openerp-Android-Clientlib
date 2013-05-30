package org.openerpclient.lib;


public interface Connector {
	public Object call(String service, String method, Object... args) throws ConnectorException;

	public Object parseDomain(String domain);
}

