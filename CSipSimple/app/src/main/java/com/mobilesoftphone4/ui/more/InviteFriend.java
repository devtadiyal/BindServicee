package com.mobilesoftphone4.ui.more;
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

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;


      
    public class InviteFriend extends Activity{ 
    	
	private ProgressDialog dialog;
	EditText edittext1,mobile,textView11;
	public static EditText job;
	TextView refill,balance,country;
    public static String str,user,pass,msg;
	SipProfile account;
    Button send,cancel,contact, button11;
    private static final int PICK_CONTACT = 0;
    private DigitsEditText digits;
    
	public void onCreate(Bundle savedInstanceState){
	super.onCreate(savedInstanceState);
	
	setContentView(R.layout.invite_friend);
	
	mobile = (EditText) findViewById(R.id.cw_mob);
	
	job = (EditText) findViewById(R.id.cw_job);
	/*if(More.count==1)
	{
	job.setText("Please download the YepINGO UK app! Calls starting to International destination from 1/2p. Click the link https://www.yepingo.co.uk/download.php. YepINGO UK Call More Pay Less");
	More.count=0;
	}else
	{
		job.setText("");
	}*/
	
	
    send=(Button)findViewById(R.id.save_bt);
	cancel=(Button)findViewById(R.id.cancel_bt);
	contact=(Button)findViewById(R.id.contacts);
	button11=(Button)findViewById(R.id.button1);
	textView11 = (EditText)findViewById(R.id.textView1);

	}
	public void call()
	{
		 send.setEnabled(false);
		 send.setVisibility(View.GONE); 
		 mobile.setText("");						
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
			            
			               MyInvite.setMessageStart(true); 
			       Intent i = new Intent(getApplicationContext(),InviteFriend.class);
			      startActivity(i);
			             // A();
			            }
			        });
			    }
			}, 
		
			30000);
	}
	
	
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
				
				Intent moveToCountryDialog=new Intent(InviteFriend.this, CountryDialog.class);
				startActivity(moveToCountryDialog);
				
				
	              System.out.println("saved value of country name in AccountWizard : "+CountryDialog.savedCountryName);
				//  System.out.println("saved value of country flag in AccountWizard : "+CountryDialog.savedCountryFlag);
	              System.out.println("saved value of country code in AccountWizard : "+CountryDialog.savedCountryCode);
				
			}
		});
	*/
	
	
	
  public void onResume()
  {
	super.onResume();
	
	/* country.setText(CountryDialog.savedCountryName);
	 String code=CountryDialog.savedCountryCode;
	 String name=CountryDialog.savedCountryName;
	System.out.println(code+"$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$rk"+name);*/
		
	
	
	if(!MyInvite.startmessage)
	{
		send.setVisibility(View.GONE);
	}
		
	 send.setOnClickListener(new OnClickListener() 
		{
			@Override
			public void onClick(View v)
			{
				
				ConnectivityManager connMgr = (ConnectivityManager)getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);

			       NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
				//Toast.makeText(getApplication(),"Hello I am send button", Toast.LENGTH_LONG).show();
				String MobileNumber = mobile.getText().toString();
				String jobTitle = job.getText().toString();
		        
				System.out.println("loooloooooooooooooo: "+jobTitle+"loooooooooooolaaaaaa: "+MobileNumber);
			

				 if( networkInfo != null && networkInfo.isConnected())
		   	     {
					 if(validate())
					 { 
		   		 str=mobile.getText().toString();
		   		 msg=job.getText().toString();
		   		 
		   		  System.out.println("destination number : "+str);
		   		  System.out.println("destination message : "+msg);
		   		  
		   		 new LongOperation().execute(user);
		   		 MyInvite.setMessageStart(false);
		   		 Aa();
					 }
		   		  }
				 
	             else
			      {
			    	  showNetworkAlert(); 
			      }
					
				}
		});
	 
	 cancel.setOnClickListener(new OnClickListener() 
		{
			@Override
			public void onClick(View v)
			{
				
				String text = "";
				mobile.setText(text);
			    job.setText(text);
			    finish();
			
		        // fetch data
				
		    	  //showNetworkAlert();  
		    	
		    
				
			}
		});
	 contact.setOnClickListener(new OnClickListener() 
		{
			@Override
			public void onClick(View v)
			{
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
	
	/*if(dest_Length==0 && dest_Length1==0)
	{
		Toast.makeText(this, "Please first enter Mobile Number and write your message first.", Toast.LENGTH_LONG).show();
		
	return false;}*/
	else
	{
		return true;}
	
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
					
					// anurag
					/*Intent intent = new Intent(Intent.ACTION_MAIN);
					intent.setAction(android.provider.Settings.ACTION_WIRELESS_SETTINGS);
					startActivity(intent);*/
				}
			}).show();
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
    		showPinAlert();	
    	}else
    	{    
    		
    		new AlertDialog.Builder(InviteFriend.this)
			.setTitle("Alert")
			.setMessage(message)
			.setNeutralButton("OK", new DialogInterface.OnClickListener()
			{

				public void onClick(DialogInterface dialog, int which)
				{
					
					if(message.startsWith("You Enter wrong Mobile Number."))
					{
						System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
						
					}else
					{
						/* Intent it = new Intent(InviteFriend.this,SipHome.class);
				        	//  it.putExtra("did",str);
				        	  it.setAction(SipManager.ACTION_SIP_DIALER);
				        	  it.setData(SipUri.forgeSipUri(SipManager.PROTOCOL_SIP, ""));
				        	  startActivity(it);*/
						finish();
					}
									// anurag
					/*Intent intent = new Intent(Intent.ACTION_MAIN);
					intent.setAction(android.provider.Settings.ACTION_WIRELESS_SETTINGS);
					startActivity(intent);*/
				}
			}).show();
    	}
    	
        }
        catch(Exception e)
        {
        	
        }
    	
    	
    	/*if(result.equals("")&&(result.equals("null")))
    	{
    		
    		
    	Toast toast=Toast.makeText(getApplicationContext(),"Voucher : Invalid voucher",Toast.LENGTH_SHORT); 
    	toast.show();
    	}
    	else{
    		
    		
    		Toast toast=Toast.makeText(getApplicationContext(),"Voucher:"+result,Toast.LENGTH_SHORT); 
        	toast.show();
        	}*/
    	}
    	/*toast.setMargin();  */
     }
 
   protected void onPreExecute() {
	   
	   dialog=new ProgressDialog(InviteFriend.this);
		dialog.setTitle("Please wait.....");
		dialog.setMessage("sending........");
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
			account = SipProfile.getProfileFromDbId(InviteFriend.this, accountId,
					DBProvider.ACCOUNT_FULL_PROJECTION);
			
			String Balance,doller;
			String user1, pass1,inputLine;
  			user1 = AccountWizard.userName1;
  			pass1 = AccountWizard.password1;
			String str=mobile.getText().toString();
		   String   str1=str.replaceAll("[()\\s-]+", "");
			System.out.println(str+"<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<"+str1);
			 msg=job.getText().toString();
			 String msg1  = msg.replaceAll(" ", "%20");
		//	 String numberCode=CountryDialog.savedCountryCode.substring(1, CountryDialog.savedCountryCode.length());
			//String b= (String)"https://account.csipsimple.com/customer/refill_dialer_voucher.php?cno="+user1+"&pno="+pass1+"&vno="+str;
			
			 //http://81.94.192.114/web/smsapp.php?cno="+user1+"&pno="+pass1+"&phn="+str+"&msg="+msg
			URL oracle = new URL("https://billing.yepingo.co.uk/web/invite_friends_sms.php?cno="+user1+"&pno="+pass1+"&phn="+str1+"&msg="+msg1);

                                  //https://billing.yepingo.co.uk/web/invite_friends_sms.php?cno=74693&pno=74693&phn=919990646165&msg=hello Friends
            BufferedReader in = new BufferedReader(new InputStreamReader(oracle.openStream()));
            inputLine =in.readLine();
           
            System.out.println("???????????????????????????????????????????"+oracle);
	        System.out.println("#########################################################: "+inputLine);
          
            in.close();
			
			
			/*
            JSONObject json = null;
	        String temp = "";
	        HttpResponse response;
	        HttpClient myClient = new DefaultHttpClient();
	        
	        
	       // HttpPost myConnection = new HttpPost("http://54.83.58.149/customer/refill_dialer_voucher.php?cno="+user1+"&pno="+pass1+"&vno="+str);
	        HttpPost myConnection = new HttpPost("http://81.94.192.114/web/smsapp.php?cno="+user1+"&pno="+pass1+"&action=smsapp&country=91&phn="+str+"&msg="+msg); 
	        try {
	            response = myClient.execute(myConnection);
	            temp = EntityUtils.toString(response.getEntity(), "UTF-8");
	    
	        System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$: "+myConnection);
	        System.out.println("#########################################################: "+temp);
	        } catch (ClientProtocolException e) {
	            e.printStackTrace();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }*/
			
			
			return inputLine;

		} catch (Exception e) {
			Log.e("Balance", "XML Pasing Excpetion ");
			e.printStackTrace();
			return null;
		}

	}
}  







