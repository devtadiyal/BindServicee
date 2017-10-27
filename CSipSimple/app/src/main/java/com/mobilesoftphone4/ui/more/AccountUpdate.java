package com.mobilesoftphone4.ui.more;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import com.mobilesoftphone4.R;
import com.mobilesoftphone4.api.SipProfile;
import com.mobilesoftphone4.db.DBProvider;
import com.mobilesoftphone4.ui.account.AccountWizard;
import com.mobilesoftphone4.utils.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

public class AccountUpdate extends Activity {

	
   public static EditText phone,fname,pin,email,lname;

	ProgressDialog dialog;
	Button updateAccount,Btn,update;
	
	protected SipProfile account = null;
	
	public static String firstName="";
	public static String lastName="";
	public static String phoneNumber="";
	public static String pinNumber="";
	public static String emailId="";
	public static String userID="";
	public static String first_name1,first_email1,first_phone1,first_pin1;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.account_update);
		
		 fname=(EditText)findViewById(R.id.editFirstName);
		 lname=(EditText)findViewById(R.id.editLastName);
		 phone=(EditText)findViewById(R.id.editPhone);
		 pin=(EditText)findViewById(R.id.editPin);
		 email=(EditText)findViewById(R.id.editEmail);
		
		 AccountUpdate.fname.getText().clear();
			AccountUpdate.phone.getText().clear();
			AccountUpdate.pin.getText().clear();
			AccountUpdate.email.getText().clear();	
		 
		 
			
		 updateAccount=(Button)findViewById(R.id.updateAccountBtn);
		 Btn=(Button)findViewById(R.id.Btn);
		 update=(Button)findViewById(R.id.update);

			
			
			/* 	
			   long accountId = 1;
			   account = SipProfile.getProfileFromDbId(AccountUpdate.this, accountId,DBProvider.ACCOUNT_FULL_PROJECTION);
			   String	user = account.getSipUserName();
			   String pass =account.getPassword();
		       new loginOperation().execute(user);*/
			 	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	
		/*fname.setText(AccountUpdate.fname1);
		lname.setText(AccountUpdate.email1);
		phone.setText(AccountUpdate.phone1);
		pin.setText(AccountUpdate.pin1);
		email.setText(AccountUpdate.email1);
		*/
	 	
		   long accountId = 1;
		  
		   
		   account = SipProfile.getProfileFromDbId(AccountUpdate.this, accountId,DBProvider.ACCOUNT_FULL_PROJECTION);
		   
		   String user, pass;
 			user = AccountWizard.userName1;
 			pass = AccountWizard.password1;
		  // String	user = account.getSipUserName();
		  // String pass =account.getPassword();
	       new loginOperation().execute(user);
	       
		fname.setText(AccountUpdate.first_name1);
		
		lname.setText(AccountUpdate.first_email1);
		phone.setText(AccountUpdate.first_phone1);
		pin.setText(AccountUpdate.first_pin1);
		email.setText(AccountUpdate.first_email1);
	 	
		System.out.println("inside onresuem @@@@@@@@@@@@@@@@@@");
		
	/*	System.out.println("first name : "+firstName);
		System.out.println("lastName : "+lastName);
		System.out.println("phoneNumber : "+phoneNumber);
		System.out.println("pinNumber : "+pinNumber);
		System.out.println("emailId : "+emailId);*/
		

	/*	long accountId = 1;
		account = SipProfile.getProfileFromDbId(AccountUpdate.this, accountId,DBProvider.ACCOUNT_FULL_PROJECTION);
		String	user = account.getSipUserName();
	    String pass =account.getPassword();*/
		
	  
		updateAccount.setOnClickListener(new OnClickListener() 
		{
			@Override
			public void onClick(View v)
			{
               
				finish();
				/*firstName=fname.getText().toString();
				lastName=lname.getText().toString();
				phoneNumber=phone.getText().toString();
				pinNumber=pin.getText().toString();
				emailId=email.getText().toString();*/
				
			/*	System.out.println("inside onClick @@@@@@@@@@@@@@@@@@");
				
				System.out.println("first name : "+firstName);
				System.out.println("lastName : "+lastName);
				System.out.println("phoneNumber : "+phoneNumber);
				System.out.println("pinNumber : "+pinNumber);
				System.out.println("emailId : "+emailId);*/
				
				
				//String accountUpdateURL="http://sip.ucalltel.com/account/ws/server.php?updateaccount="+userID+"&firstname="+firstName+"&lastname="+lastName+"&email="+emailId+"&phne="+phoneNumber+"&pin="+pinNumber;
			 	//System.out.println("Sending accountUpdateURL url in**************** AccountUpdate class  : "+accountUpdateURL);
				
			 /*	
				long accountId = 1;
				account = SipProfile.getProfileFromDbId(AccountUpdate.this, accountId,
						DBProvider.ACCOUNT_FULL_PROJECTION);
				
		
				 
			   String	user = account.getSipUserName();
			   String pass =account.getPassword();
				
			   System.out.println("user name of the application ****************"+user);
			 	String accountUpdateURL="http://81.94.192.114/web/server.php?details="+user;		 	*/
			 	
			 //	new UpdateAccountOperation().execute(accountUpdateURL);
				
			}
		});
		
		
		
		
		
		Btn.setOnClickListener(new OnClickListener() 
		{
			@Override
			public void onClick(View v)
			{
	           
				long accountId = 1;
				account = SipProfile.getProfileFromDbId(AccountUpdate.this, accountId,
						DBProvider.ACCOUNT_FULL_PROJECTION);
				

				 
				String user, pass;
	  			user= AccountWizard.userName1;
	  			pass= AccountWizard.password1;
			   System.out.println("user name of the application ****************"+user);
			   new loginOperation().execute(user);
			 	
			   
				fname.setText(AccountUpdate.first_name1);				
				lname.setText(AccountUpdate.first_email1);
				phone.setText(AccountUpdate.first_phone1);
				pin.setText(AccountUpdate.first_pin1);
				email.setText(AccountUpdate.first_email1);
				
			}
		});
		
	
	
	update.setOnClickListener(new OnClickListener() 
	{
		@Override
		public void onClick(View v)
		{
		
			
			first_name1=fname.getText().toString();
			
			first_phone1=phone.getText().toString();
		
			first_email1=email.getText().toString();
			
			System.out.println("inside onClick @@@@@@@@@@@@@@@@@@");
			
			System.out.println("first name : "+first_name1);
			
			System.out.println("phoneNumber : "+first_phone1);
		
			System.out.println("emailId : "+first_email1);
			
			
			long accountId = 1;
			account = SipProfile.getProfileFromDbId(AccountUpdate.this, accountId,
					DBProvider.ACCOUNT_FULL_PROJECTION);
			
	
			 
		   //String	user = account.getSipUserName();
		   
		   String user, pass;
 			user = AccountWizard.userName1;
 			pass = AccountWizard.password1;
			
			
			
			String	 accountUpdateURL="https://billing.yepingo.co.uk/web/update.php?username="+user+"&name="+first_name1+"&email="+first_email1+"&phone="+first_phone1;
			accountUpdateURL = accountUpdateURL.replaceAll("\\s+", "%20"); 
			
		 	System.out.println("Sending accountUpdateURL url in**************** AccountUpdate class  : "+accountUpdateURL);
			
	
		 	
		 	new UpdateAccountOperation().execute(accountUpdateURL);
			
		}
	});
	
	}
	
	
		
	
	 private class UpdateAccountOperation extends AsyncTask<String, Void, String>
	{
		String status="";
       
		@Override
		protected String doInBackground(String... params) 
		{

			return getBalance(params[0]);
		}

		@Override
		protected void onPostExecute(final String result)  
		{			
			System.out.println("result value in UpdateAccountOperation : "+result);
			
			try {
				JSONObject json=new JSONObject(result);
				           
				 first_name1=json.getString("Record update successfully!fname");
				
				
				//System.out.println(fname+":"+email+":"+phone+":"+pin+"$$++ROHIT++$$$+++ROHIT+++++$$$$$++++++++ROHIT+++++++$$$$: "+fname);			
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			
           // dialog.dismiss();
			
			Builder alert=new Builder(AccountUpdate.this);
			if(first_name1.matches("Record update successfully!"))
			{  
				alert.setMessage("Your account has been updated");
			}
			else
			{
				alert.setMessage("Record update successfully!");
			}
			
			alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					
					//finish();
				}
			});
			alert.show();
			
			
		}

		@Override
		protected void onPreExecute() 
		{
			/*dialog=new ProgressDialog(AccountUpdate.this);
			//dialog.setTitle("Please wait.....");
			dialog.setMessage("Please wait...");
			dialog.setCancelable(false);

			dialog.show();*/
		}

		@Override
		protected void onProgressUpdate(Void... values) {
		}

		public String getBalance(String b) {
			String balance = "";
			String currency = "USD";

			try {
				Log.e("link ", b);
				balance = DownloadText(b).trim();
				
				
				Log.e("balance", balance);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return balance;

		}

		String DownloadText(String URL) {
			int BUFFER_SIZE = 2000;
			InputStream in = null;
			try {
				in = OpenHttpConnection(URL);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				return "";
			}

			InputStreamReader isr = new InputStreamReader(in);
			int charRead;
			String str = "";
			char[] inputBuffer = new char[BUFFER_SIZE];
			try {
				while ((charRead = isr.read(inputBuffer)) > 0)
				{		
					String readString = String.copyValueOf(inputBuffer, 0,
							charRead);
					str += readString;
					inputBuffer = new char[BUFFER_SIZE];
				}
				in.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return "";
			}
			Log.e("bal down return", str);
			return str;
		}

		InputStream OpenHttpConnection(String urlString) throws IOException {
			InputStream in = null;
			int response = -1;

			URL url = new URL(urlString);
			URLConnection conn = url.openConnection();

			if (!(conn instanceof HttpURLConnection))
				throw new IOException("Not an HTTP connection");

			try {
				HttpURLConnection httpConn = (HttpURLConnection) conn;
				httpConn.setAllowUserInteraction(false);
				httpConn.setInstanceFollowRedirects(true);
				httpConn.setRequestMethod("GET");
				httpConn.connect();
				response = httpConn.getResponseCode();
				if (response == HttpURLConnection.HTTP_OK) {
					in = httpConn.getInputStream();
				}
			} catch (Exception ex) {
				throw new IOException("Error connecting");
			}
			return in;
		}

	}
	
	private class loginOperation extends AsyncTask<String, Void, String> {
	       
		
		
		 JSONObject json;
     @Override
     protected String doInBackground(String... params) {
     	
									
           return  getBalance(params[0]);
     }      

     @Override
     protected void onPostExecute(final String result) {
     	
     	
			
     	System.out.println("login username in password number "+result);
     	
     	

     	try {
			JSONObject json=new JSONObject(result);
			           
			
			 first_name1=json.getString("fname");
			 first_email1=json.getString("email");
			 first_phone1=json.getString("phone");
			 first_pin1=json.getString("pin");
			 System.out.println("json : "+json);
			
			
			System.out.println(first_name1+":"+first_email1+":"+first_phone1+":"+first_pin1+"$$++ROHIT++$$$+++ROHIT+++++$$$$$++++++++ROHIT+++++++$$$$: "+first_name1);			
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
				
				
					}

     @Override
     protected void onPreExecute() {
     	
     	/*dialog=new ProgressDialog(AccountWizard.this);
			dialog.setTitle("Please wait.....");
			dialog.setMessage("Creating Your PIN....");
			dialog.setCancelable(false);

			dialog.show();*/
     }

     @Override
     protected void onProgressUpdate(Void... values) {
     }
     public String getBalance(String user) {
 		
 		String Currency = "";
 		int mode = Context.MODE_PRIVATE;
 		try {
 			System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@ : "+user);
 			
 			
 			long accountId = 1;
 			account = SipProfile.getProfileFromDbId(getApplicationContext(), accountId,DBProvider.ACCOUNT_FULL_PROJECTION);
 			 
 			String user1, pass1;
  			user1 = AccountWizard.userName1;
  			pass1 = AccountWizard.password1;
        
	    URL oracle = new URL("https://billing.yepingo.co.uk/web/server.php?details="+user1);
        System.out.println("zzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzz"+user1);
        System.out.println("LLLLLLLLiiiiiiiiiinnnnnnnnnnnnnnkkkkkkkkkk"+oracle);
 					 System.out.println("KONSIIIIIIIIIIIIII VALUEEEEEEEEE"+user1);
            BufferedReader in = new BufferedReader(
 			 new InputStreamReader(oracle.openStream()));

 			 String inputLine;
 			 int i=1;
 		     inputLine=in.readLine();
 		    
 		     System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxx"+inputLine);
 		     
 					     		    		
 		return inputLine;
             
 		} catch (Exception e) {
 			Log.e("Balance", "XML Pasing Excpetion ");
 			e.printStackTrace();
 			return null;
 		}

 	}

	}
		
}
