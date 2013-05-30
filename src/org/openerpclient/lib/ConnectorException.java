package org.openerpclient.lib;

public class ConnectorException extends Exception {
	private static final long serialVersionUID = 2337826578493662500L;
	public ConnectorException() {}
	public ConnectorException(String message) { super(message); }
	public ConnectorException(Throwable exception) { super(exception); }
	public ConnectorException(String message, Throwable exception) { super(message, exception); }

}
