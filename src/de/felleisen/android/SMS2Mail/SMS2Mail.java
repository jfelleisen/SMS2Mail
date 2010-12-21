package de.felleisen.android.SMS2Mail;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class SMS2Mail extends Activity implements OnClickListener
{
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_main);
        TextView emailAddress = (TextView)findViewById(R.id.id_email_address_main);
        emailAddress.setText(getEmailAddress());
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
    	MenuInflater inflater = getMenuInflater();
    	inflater.inflate(R.menu.menu_main, menu);
    	return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
    	switch (item.getItemId())
    	{
    		case R.id.id_menu_config:
    			setContentView(R.layout.layout_config);
    			TextView emailAddress = (TextView)findViewById(R.id.id_config_email_address);
    			emailAddress.setText(getEmailAddress());
    	        try
    	        {
    	        	Button btnConfigOk = (Button)findViewById(R.id.id_config_ok);
    	        	btnConfigOk.setOnClickListener(this);
    	        	Button btnConfigCancel = (Button)findViewById(R.id.id_config_cancel);
    	        	btnConfigCancel.setOnClickListener(this);
    	        }
    	        catch (Exception e)
    	        {
    	        	Toast toast = Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG);
    	        	toast.show();
    	        }
    			return true;
    		default:
    			return super.onOptionsItemSelected(item);
    	}
    }
    
    public void onClick (View v)
    {
    	TextView emailAddress;
    	
    	if (findViewById(R.id.id_config_ok) == v)
    	{
        	emailAddress = (TextView)findViewById(R.id.id_config_email_address);
        	setEmailAddress(emailAddress.getText().toString());
    	}
    	
        setContentView(R.layout.layout_main);
        emailAddress = (TextView)findViewById(R.id.id_email_address_main);
        emailAddress.setText(getEmailAddress());
    }
    
    private void showException (Exception e)
    {
    	AlertDialog.Builder builder = new AlertDialog.Builder(this);
    	builder.setMessage("Exception:" + e.toString());
    	builder.setPositiveButton("OK", null);
    	builder.create().show();
    }
    
    private static final String EMAIL_ADDRESS_DFLT = "no address specified";
    private static final String EMAIL_ADDRESS_CFG = "email_address.cfg";
    
    private void setEmailAddress(String emailAddress)
    {
    	try
    	{
    		FileOutputStream fos = openFileOutput (EMAIL_ADDRESS_CFG, Context.MODE_PRIVATE);
    		DataOutputStream dos = new DataOutputStream(fos);
    		dos.write((emailAddress + "\n").getBytes());
			fos.close();
    	}
    	catch (Exception e)
    	{
    		showException(e);
    	}
    }
    
    private String getEmailAddress()
    {
    	String emailAddress = new String (EMAIL_ADDRESS_DFLT);
    	
    	try
    	{
			FileInputStream fis = openFileInput(EMAIL_ADDRESS_CFG);
			DataInputStream dis = new DataInputStream(fis);
			emailAddress = dis.readLine();
			fis.close();
    	}
    	catch (Exception e)
    	{
    		setEmailAddress(emailAddress);
    	}
    	
    	return emailAddress;
    }
}
