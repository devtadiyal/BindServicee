package com.mobilesoftphone4.ui.more;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.mobilesoftphone4.R;
import com.mobilesoftphone4.api.SipProfile;
import com.mobilesoftphone4.db.DBProvider;
import com.mobilesoftphone4.ui.account.AccountWizard;
import com.mobilesoftphone4.ui.dialpad.DigitsEditText;
import com.mobilesoftphone4.utils.Log;
//import com.yepingo.ui.dialpad.MobileMessage;


    public class MobileMessage extends Activity{ 
    	
	private ProgressDialog dialog;
	EditText edittext1,mobile,textView11;
	public static EditText job;
	TextView refill,balance,country;
    public static String str,user,pass,msg,a;
	SipProfile account;
    Button send,cancel,contact;
    private static final int PICK_CONTACT = 0;
    public static Button button11;
    private DigitsEditText digits;
    
	public void onCreate(Bundle savedInstanceState){
	super.onCreate(savedInstanceState);
	
	setContentView(R.layout.mobile_message);
	
	mobile = (EditText) findViewById(R.id.cw_mob);
	
	job = (EditText) findViewById(R.id.cw_job);
	/*if(More.count==1)
	{
	job.setText("Please download the yepingo app! Calls starting to International destination from 1/2p. Click the link https://www.yepingo.co.uk/download.php. yepingo Call More Pay Less");
	More.count=0;
	}else
	{
		job.setText("");
	}*/
	
	
    send=(Button)findViewById(R.id.save_bt);
	cancel=(Button)findViewById(R.id.cancel_bt);
	button11=(Button)findViewById(R.id.button1);
	contact=(Button)findViewById(R.id.contacts);
	country = (TextView)findViewById(R.id.country);
	textView11 = (EditText)findViewById(R.id.textView1);
	 /*if(More.count==1)
		{
		job.setText("hello");
		}else
		if(DialerCallBar.count==2)
		{
			job.setText("");
			
		}*/
			
	
	/* country.setOnClickListener(new OnClickListener() 
		{
			@Override
			public void onClick(View v)
			{
				//Toast.makeText(getApplication(),"Hello I am your OPCODE", Toast.LENGTH_LONG).show();
				
				Intent moveToCountryDialog=new Intent(MobileMessage.this, CountryDialog.class);
				startActivity(moveToCountryDialog);
				
				
	              System.out.println("saved value of country name in AccountWizard : "+CountryDialog.savedCountryName);
				//  System.out.println("saved value of country flag in AccountWizard : "+CountryDialog.savedCountryFlag);
	              System.out.println("saved value of country code in AccountWizard : "+CountryDialog.savedCountryCode);
				
			}
		});
	*/
	
	}
	public void call()
	{
		 send.setEnabled(false);
		send.setVisibility(View.GONE); 
		
		 mobile.setText("");
		 job.setText("");
		 button11.setText("Please select country");
		//	textView11.setText("");
		 Timer buttonTimer = new Timer();
			buttonTimer.schedule(new TimerTask() {
				
			    @Override
			    public void run() {
			        runOnUiThread(new Runnable() {

			            @Override
			            public void run() {
			                send.setEnabled(true);		               
			                send.setVisibility(View.VISIBLE); 
			              
			            }
			        });
			    }
			}, 
		
			30000);
	}
	public void Aa()
	{
				
		 Timer buttonTimer = new Timer();
			buttonTimer.schedule(new TimerTask() {

			    @Override
			    public void run() {
			        runOnUiThread(new Runnable() {

			            @Override
			            public void run() {
			            
			               MyMessage.setMessageStart(true); 
			       Intent i = new Intent(getApplicationContext(),MobileMessage.class);
			      startActivity(i);
			             // A();
			            }
			        });
			    }
			}, 
		
			30000);
	}
	
  public void onResume()
  {
	super.onResume();
	
	/* country.setText(CountryDialog.savedCountryName);
	 String code=CountryDialog.savedCountryCode;
	 String name=CountryDialog.savedCountryName;
	System.out.println(code+"$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$rk"+name);*/
		
	button11.setText(CountryDialog.savedCountryName);
	textView11.setText(CountryDialog.countryCode);
	

	if(!MyMessage.startmessage)
	{
		send.setVisibility(View.GONE);
	}
		
	 send.setOnClickListener(new OnClickListener() 
		{
			@Override
			public void onClick(View v)
			{
				
				 
				 try {
						ConnectivityManager connMgr = (ConnectivityManager)getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);

						    NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
							 if(networkInfo != null && networkInfo.isConnected())
							 {
								 if(CountryDialog.countryCode == null)
								 {
									 Toast.makeText(getApplication(),"Please select country", Toast.LENGTH_LONG).show();
								 }
								 else
								 {
									 String str=CountryDialog.countryCode;
										System.out.println("COUNTRYCODE "+str);
									
				            try {
								if(!(str.equals(null)))
								   {
									 
									 String str1=mobile.getText().toString();					
										int dest_Length1=str1.length();
										System.out.println("LENGTH OF MOBILENUMBER"+dest_Length1);
										
										if(!(dest_Length1<=6))
										{
											
											String str2=job.getText().toString();					
											int dest_Length2=str2.length();
											
											if(dest_Length2!=0)
											{
												
												 a = mobile.getText().toString();
								    			 System.out.println("STRING NUMBER &&&&&&&&&"+a);
								    			 if(a.contains("+"))
								    			 {
								    				
								    					
								    					 new LongOperation1().execute(user);
								        				 MyMessage.setMessageStart(false);
								        				 Aa();
								    				
								    			 }
								    			 else    
								    			 {
								    				
								    				 new LongOperation().execute(user);
								    				
							        				 MyMessage.setMessageStart(false);
							        				 Aa();
								    			 }
												
											}
											else
											{
												 Toast.makeText(getApplication(),"Enter your message.", Toast.LENGTH_LONG).show();
											}
											
										}
										else
										{
											 Toast.makeText(getApplication(),"Enter a valid number.", Toast.LENGTH_LONG).show();
										}
									 
								   }
								 else
								 {
									 Toast.makeText(getApplication(),"Enter a valid number.", Toast.LENGTH_LONG).show();	
		         }
							} catch (Exception e) {
								// TODO Auto-generated catch block
								 Toast.makeText(getApplication(),"Please select country", Toast.LENGTH_LONG).show();
							}
								 }			 }
				 }
				 
				 catch(Exception e)
				 {
					// Toast.makeText(getApplication(),"Please select country", Toast.LENGTH_LONG).show();
				 }
			}
		}
		);
	 
	 button11.setOnClickListener(new OnClickListener() 
		{
			@Override
			public void onClick(View v)
			{
				Intent i = new Intent(getApplication(),CountryDialog.class);
				startActivity(i);
				
			}
		});
	
	 
	 cancel.setOnClickListener(new OnClickListener() 
		{
			@Override
			public void onClick(View v)
			{
				
				String text = "";
				mobile.setText(text);
				button11.setText("Please select country");
				textView11.setText("");
			    job.setText(text);
			    finish();
			    
		    
			}
		});
	 contact.setOnClickListener(new OnClickListener() 
		{
			@Override
			public void onClick(View v)
			{
				CountryDialog.countryCode="";
				CountryDialog.savedCountryName="Please select country";
			
				//Toast.makeText(getApplication(),"Hello I am your Contact button", Toast.LENGTH_LONG).show();
				contact();
			
			}
		});
}

 
  
  
  void showNetworkAlert() 
	{
		new AlertDialog.Builder(this)
				.setTitle("Alert")
				.setMessage(
						"Please make sure you have Network Enabled")
				.setNeutralButton("OK", new DialogInterface.OnClickListener()
				{

					public void onClick(DialogInterface dialog, int which)
					{
						/*Intent siphome = new Intent(getApplicationContext(),SipHome.class);
						startActivity(siphome);*/
						
						
							}
				}).show();
				
	}

  
  
 	public void onActivityResult(int requestCode, int resultCode, Intent data) {
 	    // TODO Auto-generated method stub
 	    super.onActivityResult(requestCode, resultCode, data);
 	    
 	    try{

 	   if(requestCode == PICK_CONTACT){
 	   if(resultCode == resultCode){
 	    Uri contactData = data.getData();
 	    Cursor cursor =  getContentResolver().query(contactData, null, null, null, null);
 	                    
 	      cursor.moveToFirst();

 	      String number = cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.NUMBER));
 	      mobile.setText(number);
 	      mobile.requestFocus();
 	      
 	      System.out.println(cursor+"xxxxxxxxxxxxxxxxxxxxxxxnumber="+number);

 	      //contactName.setText(name);
 	      //contactNumber.setText(number);
 	      //contactEmail.setText(email);
 	     }
 	     }
 	   
 	    }catch(NullPointerException e){
 	    	System.out.println(e);
 	    }
 	     }
  @SuppressLint("NewApi") public void contact() {
		// TODO Auto-generated method stub

		
	   // Toast.makeText(getApplication(),"Hello ", Toast.LENGTH_LONG).show();
	    
		Intent pickContactIntent = new Intent(Intent.ACTION_PICK,ContactsContract.Contacts.CONTENT_URI);
	    pickContactIntent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE);
	    startActivityForResult(pickContactIntent, PICK_CONTACT);
        
	    System.out.println("hello you had click the contact method <*><*><*><*><*><*>");
	
	}

  
  
  
  
  
public boolean validate()
{
	String str=mobile.getText().toString();
	 msg=job.getText().toString();
	int dest_Length=str.length();
	int dest_Length1=msg.length();
	if(dest_Length==0 && dest_Length1==0)
	{
		Toast.makeText(this, "Please first Enter Mobile Number and write Your message first.", Toast.LENGTH_LONG).show();
		
	return false;
	}
	
	if (job.getText().length() == 0) {
		
	    Toast.makeText(this, "Please Write Your Message first.", Toast.LENGTH_LONG).show();
		
		return false;
	}
	if (mobile.getText().length() == 0) {
		
		Toast.makeText(this, "Please first Enter Mobile Number.", Toast.LENGTH_LONG).show();
		return false;
	}
	
	
	else
	
	{
		return true;
		
	}
	
    }

void showPinAlert() 
{
	new AlertDialog.Builder(this)
			.setTitle("Alert")
			.setMessage(
					"Invalid Mobile number or Please check your accounts")
			.setNeutralButton("OK", new DialogInterface.OnClickListener()
			{

				public void onClick(DialogInterface dialog, int which)
				{
					
				}
			}).show();
}
private class LongOperation1 extends AsyncTask<String, Void, String>{

    
	
	 JSONObject json;
	 String message;
   @Override
   protected String doInBackground(String... params) {
   	
								
         return  getBalance(params[0]);
   }      

   @Override
   protected void onPostExecute(String result) {  
       try{
       	json = new JSONObject(result);
       	message=json.getString("status");
       	System.out.println("QQQQQQQQQQQQQQQQQQQQQQQQQQ"+message);
       	
          
       	if(message.contains("You have enter wrong Number.")||message.contains("Error loading your account information!"))
       	{  	
			mobile.setText("");
  			job.setText("");
  	    button11.setText("Please select country");
		textView11.setText("");
       		showPinAlert();	
       		
       	}
       	else
       	{    
       		
       		new AlertDialog.Builder(MobileMessage.this)
   			.setTitle("Alert")
   			.setMessage(message)
   			.setNeutralButton("OK", new DialogInterface.OnClickListener()
   			{

   				public void onClick(DialogInterface dialog, int which)
   				{
   					
   					if(message.startsWith("You Enter wrong Mobile Number."))
   					{
   						mobile.setText("");
   		    			job.setText("");
   		    	    button11.setText("Please select country");
   				textView11.setText("");
   						System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
   						
   					}else
   					{	call();
  						mobile.setText("");
  	  	    			job.setText("");
  	  	    	    button11.setText("Please select country");
  	  			textView11.setText("");
  	  		
   						
   						finish();
   					}
   								
   				}
   			}).show();
       	}
       	
           }
       catch(Exception e)
       {
    	   mobile.setText("");
			job.setText("");
	    button11.setText("Please select country");
	textView11.setText("");
          System.out.println(e);	
       }    	
   	}
   
    

  protected void onPreExecute() {
	   
	   dialog=new ProgressDialog(MobileMessage.this);
		dialog.setTitle("Please wait.");
		dialog.setMessage("sending.");
		dialog.setCancelable(false);

		dialog.show();
	   
   }

   protected void onProgressUpdate(Void... values) {
   }
   public String getBalance(String user) {
		
		String Currency = "";
		int mode = Context.MODE_PRIVATE;
		try {
			System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@ : "+user);
			
			SipProfile account;
			long accountId = 1;
			account = SipProfile.getProfileFromDbId(MobileMessage.this, accountId,
					DBProvider.ACCOUNT_FULL_PROJECTION);
			String user1, pass1,inputLine;
			String Balance,doller;
			user1 = AccountWizard.userName1;
 			pass1 = AccountWizard.password1;
			String str=mobile.getText().toString();
		   String   str1=str.replaceAll("[()\\s-]+", "");
			System.out.println(str+"<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<"+str1);
			 msg=job.getText().toString();
			 String msg1  = msg.replaceAll(" ", "%20");
		
			 
				URL oracle = new URL("https://billing.yepingo.co.uk/web/smsapp.php?cno="+user1+"&pno="+pass1+"&phn="+str1+"&msg="+msg1);
           BufferedReader in = new BufferedReader(new InputStreamReader(oracle.openStream()));
           inputLine =in.readLine();
          
           System.out.println("???????????????????????????????????????????"+oracle);
	        System.out.println("#########################################################: "+inputLine);
         
           in.close();
			
			
			
			
			
			return inputLine;

		} catch (Exception e) {
			Log.e("Balance", "XML Pasing Excpetion ");
			e.printStackTrace();
			return null;
		}

	}

}

  private class LongOperation extends AsyncTask<String, Void, String> {
    
	
	 JSONObject json;
	 String message;
    @Override
    protected String doInBackground(String... params) {
    	
								
          return  getBalance(params[0]);
    }      

    @Override
    protected void onPostExecute(String result) {  
        try{
        	json = new JSONObject(result);
        	message=json.getString("status");
        	System.out.println("QQQQQQQQQQQQQQQQQQQQQQQQQQ"+message);
        	
           
        	if(message.contains("You have enter wrong Number.")||message.contains("Error loading your account information!"))
        	{  
        		mobile.setText("");
    			job.setText("");
    	    button11.setText("Please select country");
		textView11.setText("");
        		showPinAlert();	
        		
        	}
        	else
        	{    
        		
        		new AlertDialog.Builder(MobileMessage.this)
    			.setTitle("Alert")
    			.setMessage(message)
    			.setNeutralButton("OK", new DialogInterface.OnClickListener()
    			{

    				public void onClick(DialogInterface dialog, int which)
    				{
    					
    					if(message.startsWith("You Enter wrong Mobile Number."))
    					{
    						mobile.setText("");
    		    			job.setText("");
    		    	    button11.setText("Please select country");
    				textView11.setText("");
    						
    						System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
    						
    					}else
    					{
    						call();
    						
      	  	    			mobile.setText("");
      	  	    			job.setText("");
      	  	    	    button11.setText("Select a country");
      	  			textView11.setText("");
    						finish();
    					}
    								
    				}
    			}).show();
        	}
        	
            }
        catch(Exception e)
        {
           System.out.println(e);	
        }    	
    	}
    
     
 
   protected void onPreExecute() {
	   
	   dialog=new ProgressDialog(MobileMessage.this);
		dialog.setTitle("Please wait.");
		dialog.setMessage("sending.");
		dialog.setCancelable(false);

		dialog.show();
	   
    }

    protected void onProgressUpdate(Void... values) {
    }
    public String getBalance(String user) {
		
		String Currency = "";
		int mode = Context.MODE_PRIVATE;
		try {
			System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@ : "+user);
			
			SipProfile account;
			long accountId = 1;
			account = SipProfile.getProfileFromDbId(MobileMessage.this, accountId,
					DBProvider.ACCOUNT_FULL_PROJECTION);
			String user1, pass1,inputLine;
			String Balance,doller;
			user1 = AccountWizard.userName1;
  			pass1 = AccountWizard.password1;
			String str=mobile.getText().toString();
		   String   str1=str.replaceAll("[()\\s-]+", "");
			System.out.println(str+"<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<"+str1);
			 msg=job.getText().toString();
			 String msg1  = msg.replaceAll(" ", "%20");
		
			 
				URL oracle = new URL("https://billing.yepingo.co.uk/web/smsapp.php?cno="+user1+"&pno="+pass1+"&phn="+str1+"&msg="+msg1);
            BufferedReader in = new BufferedReader(new InputStreamReader(oracle.openStream()));
            inputLine =in.readLine();
           
            System.out.println("???????????????????????????????????????????"+oracle);
	        System.out.println("#########################################################: "+inputLine);
          
            in.close();
			
			
			
			
			
			return inputLine;

		} catch (Exception e) {
			Log.e("Balance", "XML Pasing Excpetion ");
			e.printStackTrace();
			return null;
		}

	}
}  
    }






