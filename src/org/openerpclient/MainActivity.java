package org.openerpclient;

import org.openerpclient.lib.Connection;
import org.openerpclient.lib.ConnectorType;
import org.openerpclient.lib.Model;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        Button connectButton = (Button) findViewById(R.id.button1);
        connectButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				System.out.println("Connect");
				Connection con = new Connection("192.168.0.100", 8069, "android", "admin", "a", ConnectorType.JSONRPC);
				if (con.login()) {
					System.out.println("Connected");
				}
				else {
					System.out.println("Connection Failed");
				}
				Model partner = con.getModel("res.partner");
				int[] result = partner.search("['|', ['name', 'in', ['Thibault', 'test']], ['name', 'like', 'th']]");
				if (result != null) {
					System.out.println("Search Result");
					for(int id : result) 
						System.out.println(id);
				}
			}
		});
        
        Button xmlRPCButton = (Button) findViewById(R.id.button2);
        xmlRPCButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				System.out.println("Connect");
				Connection con = new Connection("192.168.0.100", 8069, "android", "admin", "a", ConnectorType.XMLRPC);
				if (con.login()) {
					System.out.println("Connected");
				}
				else {
					System.out.println("Connection Failed");
				}
				Model partner = con.getModel("res.partner");
				int[] result = partner.search("['|', ['name', 'in', ['Thibault', 'test']], ['name', 'like', 'th']]");
				if (result != null) {
					System.out.println("Search Result");
					for(int id : result) 
						System.out.println(id);
				}
				
			}
		});
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}
