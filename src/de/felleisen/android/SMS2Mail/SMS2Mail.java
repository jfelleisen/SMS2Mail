package de.felleisen.android.SMS2Mail;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
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
        setContentView(R.layout.layout_main);
    }
    
    private String getEmailAddress()
    {
    	String emailAddress = "juergen@felleisen.de";
    	Toast toast;
    	
    	if (Environment.MEDIA_MOUNTED.equalsIgnoreCase(Environment.getExternalStorageState()))
    	{
    		File file = new File(Environment.getExternalStorageDirectory(), "/Android/data/de.felleisen.android.SMS2Mail/files/EmailAddress.cfg");
    		if (file.exists())
    		{
        		toast = Toast.makeText(getApplicationContext(), "read config file", Toast.LENGTH_LONG);
        		toast.show();
    			try
    			{
    				byte[] buffer = {};
    				FileInputStream fis = openFileInput(file.toString());
    				fis.read(buffer);
    				emailAddress = buffer.toString();
    			}
    			catch (Exception e)
    			{
    	    		toast = Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG);
    	    		toast.show();
    			}
    		}
    		else
    		{
        		toast = Toast.makeText(getApplicationContext(), file.toString(), Toast.LENGTH_LONG);
        		toast.show();
    			try
    			{
    				File dir = new File(Environment.getExternalStorageDirectory(), "/Android/data/de.felleisen.android.SMS2Mail/files/");
    				dir.mkdirs();
    				file.createNewFile();
    				FileOutputStream fos = openFileOutput(file.toString(), Context.MODE_PRIVATE);
    				fos.write(emailAddress.getBytes());
    				fos.close();
    			}
    			catch (Exception e)
    			{
    	    		toast = Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG);
    	    		toast.show();
    			}
    		}
    	}
    	else
    	{
    		toast = Toast.makeText(getApplicationContext(), "Cannot Read/Write to external storage!", Toast.LENGTH_LONG);
    		toast.show();
    	}
    	
    	return emailAddress;
    }
}
