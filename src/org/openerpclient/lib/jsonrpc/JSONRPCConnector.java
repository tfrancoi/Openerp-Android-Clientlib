package org.openerpclient.lib.jsonrpc;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.alexd.jsonrpc.JSONRPCClient;
import org.alexd.jsonrpc.JSONRPCException;
import org.alexd.jsonrpc.JSONRPCHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.openerpclient.lib.Connector;
import org.openerpclient.lib.ConnectorException;

public class JSONRPCConnector implements Connector {
	public final String JSONRPC_URL = "/jsonrpc";

	String baseURL;
	String url;
	JSONRPCClient client;


	public JSONRPCConnector(String host, int port) {
		this.baseURL = "http://" + host + ":" + port;
		this.url = this.baseURL + JSONRPC_URL;
		this.client = new JSONRPCHttpClient(this.url);
	}

	/**
	 * @param 0: Model
	 * @param 1: Method
	 * @param 2..n : Method parameter
	 */
	public Object call(String service, String method, Object... args) throws ConnectorException {
		try {
			System.out.println("Call: " + service + " - " + method);
			for (Object o : args) {
				System.out.println(o);
			}
			JSONObject params = new JSONObject();
			params.put("service", service);
			params.put("method", method);
			params.put("args", toJSON(args));
			Object result = this.client.call("call", params);
			System.out.println("JSONRPC result " + result + " " + result.getClass().getName());
			
			if(result instanceof JSONObject || result instanceof JSONArray) {
				System.out.println("JSON result");
				return fromJson(result);
			}
			return result;
		}
		catch(JSONException e) {
			throw new ConnectorException(e.getMessage(), e);
		}
		catch(JSONRPCException e) {
			throw new ConnectorException(e.getMessage(), e);
		}
	}

	/**
	 * The following code is inspire from 
	 * https://gist.github.com/codebutler/2339666
	 * @author Eric Butler
	 * 
	 */
	public static Object toJSON(Object object) throws JSONException {
		if (object instanceof Map) {
			JSONObject json = new JSONObject();
			Map map = (Map) object;
			for (Object key : map.keySet()) {
				json.put(key.toString(), toJSON(map.get(key)));
			}
			return json;
		} else if (object instanceof Iterable) {
			JSONArray json = new JSONArray();
			for (Object value : ((Iterable)object)) {
				json.put(toJSON(value));
			}
			return json;
		} else if (object.getClass().isArray()) {
			JSONArray json = new JSONArray();
			Object[] array = (Object[]) object;
			for(Object value : (Object[]) object) {
				json.put(toJSON(value));
			}
			return json;
		} else {
			return object;
		}
	}

	public static boolean isEmptyObject(JSONObject object) {
		return object.names() == null;
	}

	public static Map<String, Object> getMap(JSONObject object, String key) throws JSONException {
		return toMap(object.getJSONObject(key));
	}

	public static Map<String, Object> toMap(JSONObject object) throws JSONException {
		Map<String, Object> map = new HashMap<String, Object>();
		Iterator keys = object.keys();
		while (keys.hasNext()) {
			String key = (String) keys.next();
			map.put(key, fromJson(object.get(key)));
		}
		return map;
	}

	public static Object[] toArray(JSONArray array) throws JSONException {
		Object[] objectArray = new Object[array.length()];
		for (int i = 0; i < array.length(); i++) {
			objectArray[i] = fromJson(array.get(i));
		}
		return objectArray;
	}

	public static Object fromJson(Object json) throws JSONException {
		if (json == JSONObject.NULL) {
			return null;
		} else if (json instanceof JSONArray) {
			return toArray((JSONArray) json);
		} else if (json instanceof JSONObject) {
			return toMap((JSONObject) json);
		} else {
			return json;
		}
	}
	
	@Override
	public Object parseDomain(String domain) {
		try {
			return new JSONArray(domain);
		}
		catch(JSONException e) {
			e.printStackTrace();
			return null;
		}
	}
}