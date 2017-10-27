
package com.mobilesoftphone4.ui.more;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Calendar;

import org.json.JSONException;
import org.json.JSONObject;

import com.actionbarsherlock.app.SherlockFragment;
import com.mobilesoftphone4.R;
import com.mobilesoftphone4.api.SipManager;
import com.mobilesoftphone4.api.SipProfile;
import com.mobilesoftphone4.api.SipUri;
import com.mobilesoftphone4.db.DBProvider;
import com.mobilesoftphone4.ui.ScreenSplash;
import com.mobilesoftphone4.ui.SipHome;
import com.mobilesoftphone4.ui.account.AccountWizard;
import com.mobilesoftphone4.ui.dialpad.DialerFragment;
import com.mobilesoftphone4.ui.more.More;
import com.mobilesoftphone4.ui.more.MoreListAdapter;
import com.mobilesoftphone4.utils.Log;
import com.mobilesoftphone4.utils.PreferencesProviderWrapper;
import com.mobilesoftphone4.utils.PreferencesWrapper;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.AlertDialog.Builder;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

public class NewAccount extends SherlockFragment {

	TextView balance,paypal,MOBILE_TOP,PAY_PAL,ABOUT_US,log_in,refl_vchr,LOCAL_ACCESS;
	Button SIGN_OUT,ADD_CREDIT;
	ToggleButton TOGGLE_BUTTON;
	ImageButton skip;
	
	
	ConnectivityManager connMgr;
	NetworkInfo networkInfo;
	private Context c;
	private DatePicker dpResult;
	private int PICK_YEAR;
	private int PICK_MONTH;
	private int PICK_DAY;
	
	public static String CURRENT_SELECTED_MONTH;
	public static StringBuilder CURRENT_SELECTED_YEAR;
	
	
	public static final int DATE_DIALOG_ID=999;
	
	
	/*private DatePickerDialog.OnDateSetListener PICK_DATE_SET_LISTENER = new DatePickerDialog.OnDateSetListener() {
		
		@Override
		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth)
		{
			
			PICK_YEAR=year;
			PICK_MONTH=monthOfYear;
			PICK_DAY=dayOfMonth;
			
		//	updateDisplay();
		}
	};*/
		
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.new_account);
		/*balance=(TextView)findViewById(R.id.account_balance);
		PHONE_NUMBER=(TextView)findViewById(R.id.PHONE_NUMBER);
		EMAIL_ID=(TextView)findViewById(R.id.PERSONAL_INFO);
		CHECK_RATE=(TextView)findViewById(R.id.CHECK_RATE);
		CHANGE_PIN=(TextView)findViewById(R.id.CHANGE_PIN);
		ENABLE_3G=(TextView)findViewById(R.id.ENABLE_3G);
		ACCOUNT_HISTORY=(TextView)findViewById(R.id.ACCOUNT_HISTORY);
		
		SIGN_OUT=(Button)findViewById(R.id.SIGN_OUT);
		ADD_CREDIT=(Button)findViewById(R.id.ADD_CRIDET);
		
		prefProviderWrapper=new PreferencesProviderWrapper(NewAccount.this);*/
	}
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
       //super.onCreateView(inflater, container, savedInstanceState);
		View v = inflater.inflate(R.layout.new_account, container, false);
		c = getActivity().getBaseContext();
		
		//balance=(TextView)v.findViewById(R.id.account_balance);
		/*PHONE_NUMBER=(TextView)v.findViewById(R.id.PHONE_NUMBER);
		EMAIL_ID=(TextView)v.findViewById(R.id.PERSONAL_INFO);
		ENABLE_3G=(TextView)v.findViewById(R.id.ENABLE_3G);
		CHECK_RATE=(TextView)v.findViewById(R.id.CHECK_RATE);
		CHANGE_PIN=(TextView)v.findViewById(R.id.CHANGE_PIN);
		TOGGLE_BUTTON = (ToggleButton)v.findViewById(R.id.TOGGLE_BUTTON);
		
		ACCOUNT_HISTORY=(TextView)v.findViewById(R.id.ACCOUNT_HISTORY);
		MOBILE_TOP=(TextView)v.findViewById(R.id.MOB_TOP);*/
		//log_in=(TextView)v.findViewById(R.id.log_in);
		//SIGN_OUT=(Button)v.findViewById(R.id.SIGN_OUT);
		
		
		 connMgr = (ConnectivityManager)getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);

	        networkInfo = connMgr.getActiveNetworkInfo();
		
	    refl_vchr=(TextView)v.findViewById(R.id.refl_vchr);
		refl_vchr.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
			
				if (networkInfo != null && networkInfo.isConnected()) 
		        {
				
		 System.out.println("reffffiiiiiiiiiiiiiilllllllllllllllllllllllll");
         startActivity(new Intent(getActivity(),RefillVoucher.class));
		        }else
		        {
		        	 Toast.makeText(getActivity(), "Please check your network first", Toast.LENGTH_LONG).show();	
		        }
					
		 
		}
		}); 	
		paypal=(TextView)v.findViewById(R.id.paypal);
		paypal.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				
				 SipProfile account;
	    			long accountId = 1;
	    			account = SipProfile.getProfileFromDbId(getActivity(), accountId,
	    					DBProvider.ACCOUNT_FULL_PROJECTION);
	    			String user1, pass1;
	    			
	    			user1 = AccountWizard.userName1;
	      			pass1 = AccountWizard.password1;
				if (networkInfo != null && networkInfo.isConnected()) 
		        {
		       
					//Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://billing.yepingo.it/customer/mobile_payment.php?pr_login="+user1+"&pr_password="+pass1+"&mobiledone=submit_log"));
					Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://billing.yepingo.co.uk/customer/mobile_payment.php?pr_login="+user1+"&pr_password="+pass1+"&mobiledone=submit_log"));
					
					//https://billing.yepingo.nl/customer/mobile_payment.php?pr_login=74693&pr_password=74693&mobiledone=submit_log
					startActivity(browserIntent);
					
		    	  }
		      else
		      {
		    
		    	  Toast.makeText(getActivity(), "Please check your network first", Toast.LENGTH_LONG).show();
		      }	
		}
		}); 	
        
		skip=(ImageButton)v.findViewById(R.id.skip);
		skip.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				
				 Intent it = new Intent(getActivity(),SipHome.class);
		        	//  it.putExtra("did",str);
		        	  it.setAction(SipManager.ACTION_SIP_DIALER);
		        	  it.setData(SipUri.forgeSipUri(SipManager.PROTOCOL_SIP, ""));
		        	  startActivity(it);
				
		}
		}); 	
		
		/*ADD_CREDIT=(Button)v.findViewById(R.id.ADD_CRIDET);
		PAY_PAL=(TextView)v.findViewById(R.id.PAY_PAL);
		
		LOCAL_ACCESS=(TextView)v.findViewById(R.id.LOCAL_ACCESS);
		
		ABOUT_US=(TextView)v.findViewById(R.id.ABOUT_US);*/
		//balance.setText("$"+DialerFragment.NEWBALANCE);
		//PHONE_NUMBER.setText("Phone Number"+"\n"+DialerFragment.NEW_PHONE_NUMBER);
		//EMAIL_ID.setText("Profile"+"\n"+DialerFragment.NEW_EMAILID);
		
		return v;
	}
	
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
	//	balance.setText(DialerFragment.NEWBALANCE+" EUR");
	//	PHONE_NUMBER.setText("Phone Number"+"\n"+DialerFragment.NEW_PHONE_NUMBER);
	//	EMAIL_ID.setText("Profile"+"\n"+DialerFragment.NEW_EMAILID);
		//balance.setText(DialerFragment.NEWBALANCE);
	//	balance.setText(DialerFragment.NEWBALANCE);
		
		   
		
	
		/*ABOUT_US.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				
				if (networkInfo != null && networkInfo.isConnected()) 
		        {
		        // fetch data
					startActivity(new Intent(getActivity(),AboutDialog.class));
		    	  }
		      else
		      {
		    	  //showNetworkAlert(); 
		    	  Toast.makeText(getActivity(), "Please check your network first", Toast.LENGTH_LONG).show();
		      }	
			}
		}); 
		*/
		
		
	
		
		
		/*
		ADD_CREDIT.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				
				if (networkInfo != null && networkInfo.isConnected()) 
		        {
		        // fetch data
					
					
					

					Intent intent11 = new Intent(Intent.ACTION_VIEW,Uri.parse("https://www.ucalltel.com/account/customer"));
					startActivity(intent11);
		    	  }
		      else
		      {
		    	  //showNetworkAlert();  
		    	  Toast.makeText(getActivity(), "Please check your network first", Toast.LENGTH_LONG).show();
		      }	
		}
		}); */	
		
		/*log_in.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				
				if (networkInfo != null && networkInfo.isConnected()) 
		        {
		        // fetch data
					startActivity(new Intent(getActivity(),AccountWizard.class));
		    	  }
		      else
		      {
		    	  //showNetworkAlert(); 
		    	  Toast.makeText(getActivity(), "Please check your network first", Toast.LENGTH_LONG).show();
		      }	
			}
		}); */
		
		/*SIGN_OUT.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				
				new AlertDialog.Builder(getActivity())
				.setTitle(R.string.warning)
				.setMessage(
						"Exit will limit your availability and you will not receive Free Calls and Messages on yepingo")
				.setNeutralButton("OK", new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int which)
					{
						 prefProviderWrapper.setPreferenceBooleanValue(PreferencesWrapper.HAS_BEEN_QUIT, true);
			                disconnect(true);
							
			                SipProfile account;
			    			long accountId = 1;
			    			account = SipProfile.getProfileFromDbId(getActivity(), accountId,
			    					DBProvider.ACCOUNT_FULL_PROJECTION);
			    			String user1, pass1,inputLine;
			    			String Balance,doller;
			    			user1 = account.getSipUserName();
			    			pass1 = account.getPassword();
			    			String accountUpdateURL="https://account.yepingo.com/webui/server.php?user="+user1+"&pass="+pass1+"&lineswitch=2";
			    			System.out.println("xxxxx ROHIT ROHIT ROHIT xxxxx"+accountUpdateURL);
			    			new UpdateAccount().execute(accountUpdateURL);
			    			
			                
			                
					}
				})
				.setNegativeButton("Cancel",
						new DialogInterface.OnClickListener() {

							public void onClick(DialogInterface dialog,
									int which) {
								// TODO Auto-generated method stub
								// anurag.
							}
				}).show();
		}
		}); 	
		*/
		
	/*	
		TOGGLE_BUTTON.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				
				 boolean on = TOGGLE_BUTTON.isChecked();
					    
					    if (on) 
					    {
					        // Enable vibrate
					    	 System.out.println("inside onToggleClicked in if block New Account class : "+on);
					    	 try {
								setMobileDataEnabled(getActivity(),on);
								
							} catch (IllegalArgumentException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (ClassNotFoundException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (NoSuchFieldException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (IllegalAccessException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (NoSuchMethodException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (InvocationTargetException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
					    } 
					    else
					    {
					    	System.out.println("inside onToggleClicked in else block New Account class : "+on);
					        // Disable vibrate
					    	
					    	try {
								setMobileDataEnabled(getActivity(),on);
								
							} catch (IllegalArgumentException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (ClassNotFoundException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (NoSuchFieldException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (IllegalAccessException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (NoSuchMethodException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (InvocationTargetException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
					    }
		}
		}); 	
		
		*/
		}
	/*private void setMobileDataEnabled(Context context, boolean enabled) throws ClassNotFoundException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
	    final ConnectivityManager conman = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
	    final Class conmanClass = Class.forName(conman.getClass().getName());
	    final Field iConnectivityManagerField = conmanClass.getDeclaredField("mService");
	    iConnectivityManagerField.setAccessible(true);
	    final Object iConnectivityManager = iConnectivityManagerField.get(conman);
	    final Class iConnectivityManagerClass = Class.forName(iConnectivityManager.getClass().getName());
	    final Method setMobileDataEnabledMethod = iConnectivityManagerClass.getDeclaredMethod("setMobileDataEnabled", Boolean.TYPE);
	    setMobileDataEnabledMethod.setAccessible(true);

	    setMobileDataEnabledMethod.invoke(iConnectivityManager, enabled);
	}
	void openAboutDialog1(){
		startActivity(new Intent(getActivity(),AccountWizard.class));
	}*/
	/*private void updateDisplay()
	{
		StringBuilder str= new StringBuilder().append(PICK_MONTH + 1).append("/").append(PICK_DAY).append("/").append(PICK_YEAR).append(" ");
	    System.out.println("updated date is : "+str);
	    
	    String str1= String.valueOf(PICK_MONTH+1);
	    
	    if(str1.length()==1)
	    {
	    CURRENT_SELECTED_MONTH="0"+str1;
	    }
	    else
	    {
	    	CURRENT_SELECTED_MONTH=String.valueOf(PICK_MONTH+1);
	    }
	    
	    CURRENT_SELECTED_YEAR=new StringBuilder().append(PICK_YEAR);
		
		System.out.println("selected month : "+CURRENT_SELECTED_MONTH);
		System.out.println("selected year : "+CURRENT_SELECTED_YEAR);
		
	    startActivity(new Intent(getActivity(),CdrDialog.class));
	}
	*/
	
	/*private void disconnect(boolean quit) {
        
        Intent intent = new Intent(SipManager.ACTION_OUTGOING_UNREGISTER);
        intent.putExtra(SipManager.EXTRA_OUTGOING_ACTIVITY, new ComponentName(getActivity(), NewAccount.class));
        getActivity().sendBroadcast(intent);
        Intent intent1 = new Intent(Intent.ACTION_MAIN);
		intent1.addCategory(Intent.CATEGORY_HOME);
		intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(intent1);
		//finish();
		
    }
   */
	
	/*private class UpdateAccount extends AsyncTask<String, Void, String>
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
			System.out.println("result value in UpdateAccount<<<<<<<<<Rohit : "+result);
			
			try {
				JSONObject json=new JSONObject(result);
				
				 status=json.getString("status");
				System.out.println("json : "+json);
				
				
				System.out.println("$$++ROHIT++$$$+++ROHIT+++++$$$$$++++++++ROHIT+++++++$$$$: "+status);			
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		//	dialog.dismiss();
		}

		@Override
		protected void onPreExecute() 
		{
			//dialog=new ProgressDialog(AccountWizard.this);
			//dialog.setTitle("Please wait.....");
			//dialog.setMessage("Please wait...");
			//dialog.setCancelable(false);

			//dialog.show();
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

	}*/


	
	
}
