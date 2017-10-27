package com.mobilesoftphone4.ui;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.mobilesoftphone4.R;
import com.mobilesoftphone4.api.SipProfile;
import com.mobilesoftphone4.db.DBProvider;
import com.mobilesoftphone4.ui.account.AccountWizard;
import com.mobilesoftphone4.utils.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;


public class VerifyOTPActivity extends Activity {
private TextView otpMsg,otpTimmer;
private static EditText otpCode;
private static Button buttonCheckOtp;
static SipProfile account;
private Button reSendOtp;
String phoneNumber;
static String otp;
 private static   String uname, password;
private RequestQueue requestQueue;
Map<String,String> params;
    private SharedPreferences app_preferences;
    private SharedPreferences.Editor editor;
    private Boolean isFirstTime2;

    final IncomingSms broadcast_receiver = new IncomingSms();

    @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_verify_otp);
		requestQueue = Volley.newRequestQueue(this);
		Intent intent = getIntent();
	     phoneNumber = intent.getStringExtra("phoneNumber"); 
		    otpMsg = (TextView) findViewById(R.id.otp_info);
	        otpTimmer = (TextView) findViewById(R.id.otp_timmer);
	        otpCode = (EditText) findViewById(R.id.otp_code);
	        buttonCheckOtp = (Button) findViewById(R.id.btnCheckOtp);
	        reSendOtp = (Button) findViewById(R.id.reSendOtp);
	       // buttonCheckOtp.setBackgroundColor(Color.BLUE);
	       // reSendOtp.setBackgroundColor(Color.BLUE);
	        otpMsg.setText("Please enter the One Time PIN which you have received on  "+phoneNumber+" or Re-generate the One Time PIN");
	        MyCount counter = new MyCount(100000, 1000);
	        counter.start();
        app_preferences = PreferenceManager
                .getDefaultSharedPreferences(VerifyOTPActivity.this);

          editor = app_preferences.edit();
         isFirstTime2 = app_preferences.getBoolean("isFirstTime2", true);
        System.out.println("Verify  OTP is  firsttime status 1"+isFirstTime2);
        if (isFirstTime2) //isFirstTime
        {
            // displayScreen();


            buttonCheckOtp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Hiding the alert dialog
                    //alertDialog.dismiss();


                    //Displaying a progressbar
                    final ProgressDialog loading = ProgressDialog.show(VerifyOTPActivity.this, "Authenticating", "Please wait while we check the entered code", false, false);
                    loading.setMessage(Html.fromHtml("<font color='#026D95'>Authenticating Please wait while we check the OTP</font>"));
                    loading.getWindow().setBackgroundDrawableResource(android.R.color.white);
                    // .setMessage( Html.fromHtml("<font color='#026D95'>We will be Verifying Your Phone Number"+"\n"+ phoneNumber +"\n Is this OK ,or you would like to edit the number ?</font>"))
                    //Getting the user entered otp from edittext
                    if (otp == null) {
                        otp = otpCode.getText().toString().trim();
                    }

                    System.out.println(otp + "<-----rekkksponse");


                    //Creating an string request
                    StringRequest stringRequest = new StringRequest(Request.Method.GET, Config.CONFIRM_URL.concat("0") + phoneNumber.trim() + "&otp=" + otp.trim(),
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    loading.dismiss();
                                    //if the server response is success

                                    System.out.println(response + "<-----response");
                                    try {
                                        JSONObject jsonResponse = new JSONObject(response);
                                        System.out.println("jsonResponse" + jsonResponse.getString("result"));
                                        //If it is success
                                        otp = null;
                                        if (jsonResponse.getString("result").equalsIgnoreCase("success")) {
                                            loading.dismiss();
                                            new LongOperation().execute(phoneNumber);
                                            JSONObject uinfo = jsonResponse.getJSONObject("user credentials");
                                             uname = uinfo.getString("uname");
                                             password = uinfo.getString("password");
                                            editor.putString("userName",uname);
                                            editor.putString("userPassword",password);
                                            editor.commit();

                                            System.out.println("uname passwrd---->" + uname + password);
                                            AccountWizard.userName1 = uname;
                                            AccountWizard.password1 = password;
                                            Intent myIntent = new Intent(VerifyOTPActivity.this, SipHome.class);
                                            myIntent.putExtra("uname", uname);
                                            myIntent.putExtra("password", password);
                                            VerifyOTPActivity.this.startActivity(myIntent);
                                            finish();
                                            //Starting a new activity
                                            //startActivity(new Intent(VerifyOTPActivity.this, SipHome.class));
                                        } else if (jsonResponse.getString("failure reason").equalsIgnoreCase("Your One Time Passowrd is expired")) {
                                            loading.dismiss();

                                            //Starting a new activity
                                            Toast.makeText(VerifyOTPActivity.this, "Your One Time Password is expired", Toast.LENGTH_LONG).show();

                                        } else {
                                            loading.dismiss();
                                            //Displaying a toast if the otp entered is wrong
                                            Toast.makeText(VerifyOTPActivity.this, "Wrong OTP Please Try Again", Toast.LENGTH_LONG).show();

                                            //Asking user to enter otp again
                                            //confirmOtp();
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    // alertDialog.dismiss();
                                    Toast.makeText(VerifyOTPActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
                                }
                            }) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            params = new HashMap<String, String>();
                            //Adding the parameters otp and username
                            params.put(Config.KEY_OTP, otp);
                            params.put(Config.KEY_PHONE, phoneNumber);
                            return params;
                        }
                    };

                    //Adding the request to the queue
                    System.out.println("hello" + stringRequest + "<-----stringRequest");
                    requestQueue.add(stringRequest);
                }
            });
            System.out.println("before relogin1--->"+uname+"  >>>>"+password);

            editor.putBoolean("isFirstTime2", false);
            //editor.putString("userName",uname);
            //editor.putString("userPassword",password);
            editor.commit();
            String sharad=app_preferences.getString("userName","");
            String maurya=app_preferences.getString("userPassword","");
            System.out.println("before relogin2--->"+sharad+"  >>>>"+maurya);

            System.out.println("Verify  OTP is  firsttime status 1"+isFirstTime2);
        }
        else
        {

            String sharad=app_preferences.getString("userName","");
            String maurya=app_preferences.getString("userPassword","");

            AccountWizard.userName1 = sharad;
            AccountWizard.password1 = maurya;
            Intent myIntent = new Intent(VerifyOTPActivity.this, SipHome.class);
            System.out.println("After relogin--->  "+sharad+"  >>>>"+maurya);
            myIntent.putExtra("uname", sharad);
            myIntent.putExtra("password", maurya);
            startActivity(myIntent);
            finish();
        }

        reSendOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            	register1() ;
            }
        });
            
        
//        reSendOtp.setOnClickListener(new View.OnClickListener(){
//			
//			@Override
//			public void onClick(View v) {
//				OTPMainActivity otpMainActivity=new OTPMainActivity();
//				otpMainActivity.register();
//				// TODO Auto-generated method stub
//				
//			}
//		});
    }
    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub

        try{
            if(broadcast_receiver!=null)
                unregisterReceiver(broadcast_receiver);
        }catch(Exception e)
        {

        }
        super.onDestroy();

    }
	public static void recivedSms(String message)
    {    
      final String Otp1= extractNumber(message);
        try
        {  otp=Otp1;
        	System.out.println("now the value"+Otp1);
           //otpCode.setText(Otp);
           
           final Handler myHandler = new Handler() {
        	    public void handleMessage(Message msg) {
        	    	System.out.println("SKM"+Otp1);
        	    	otpCode.setText(Otp1);
        	    }
        	};
           
        	myHandler.sendEmptyMessage(1);
        }
        catch (Exception e)
        {
        	e.printStackTrace();
        }
        Thread thread=new Thread();
        thread.start();
        try {
        	//for(int i = 1000; i > 0; i--) {
        		thread.sleep(1000);
			if(Otp1!=null)
			{
				buttonCheckOtp.performClick();	
			}
        	//}
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
       // buttonCheckOtp.performClick();
    }
    public static  String extractNumber(final String str) {

        if(str == null || str.isEmpty()) return "";

        StringBuilder sb = new StringBuilder();
        boolean found = false;
        for(char c : str.toCharArray()){
            if(Character.isDigit(c)){
                sb.append(c);
                found = true;
            } else if(found){
                // If we already found a digit before and this char is not a digit, stop looping
                break;
            }
        }

        return sb.toString();
    }

    @Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.verify_ot, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		//if (id == R.id.action_settings) {
			//return true;
		//}
		return super.onOptionsItemSelected(item);
	}
	public class MyCount extends CountDownTimer {

        public MyCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onFinish() {
        	reSendOtp.setEnabled(true);
        	otpTimmer.setText("Time Out Re-Try!");
        }

        @Override
        public void onTick(long millisUntilFinished) {
        	reSendOtp.setEnabled(false);
        	otpTimmer.setText("One Time PIN will expire in " + millisUntilFinished / 1000+" Second");
        }
    }
	
	 public void register1() {

	        //Displaying a progress dialog
	        final ProgressDialog loading = ProgressDialog.show(this, "Registering", "Please wait...", false, false);

	        IntentFilter filter1 = new IntentFilter();
	        filter1.addAction("android.provider.Telephony.SMS_RECEIVED");
	        registerReceiver(broadcast_receiver, filter1);
	        //Getting user data
	        //username = editTextUsername.getText().toString().trim();
	        //password = editTextPassword.getText().toString().trim();
//	        phone = editTextPhone.getText().toString().trim();

	        //Again creating the string request
	        StringRequest stringRequest = new StringRequest(Request.Method.GET, Config.REGISTER_URL+phoneNumber,
	                new Response.Listener<String>() {
	                    @Override
	                    public void onResponse(String response) {
	                        loading.dismiss();
	                        try { unregisterReceiver(broadcast_receiver);
	                            //Creating the json object from the response
	                            JSONObject jsonResponse = new JSONObject(response);
	                            System.out.println("jsonResponse"+jsonResponse.getString("result"));
	                            //If it is success
	                            if(jsonResponse.getString("result").equalsIgnoreCase("success"))
	                            {
	                                //Asking user to confirm otp
	                                System.out.println("jsonResponse--->"+jsonResponse.getString("result"));
	                                Intent myIntent = new Intent(VerifyOTPActivity.this, VerifyOTPActivity.class);
	        						myIntent.putExtra("phoneNumber", phoneNumber); //Optional parameters
	        						VerifyOTPActivity.this.startActivity(myIntent);
	                                //confirmOtp();
	                            }else{
	                                //If not successful user may already have registered
	                                Toast.makeText(VerifyOTPActivity.this, "Username or Phone number already registered", Toast.LENGTH_LONG).show();
	                            }
	                        } catch (JSONException e) {
	                            e.printStackTrace();
	                        }
	                    }
	                },
	                new Response.ErrorListener() {
	                    @Override
	                    public void onErrorResponse(VolleyError error) {
	                        loading.dismiss();
	                        Toast.makeText(VerifyOTPActivity.this, error.getMessage(),Toast.LENGTH_LONG).show();
	                    }
	                }) {
	            @Override
	            protected Map<String, String> getParams() throws AuthFailureError {
	                Map<String, String> params = new HashMap<String, String>();
	               
	                params.put(Config.KEY_PHONE, phoneNumber);
	                return params;
	            }
	        };

	        //Adding request the the queue
	        System.out.println("hello"+stringRequest+"<-----stringRequest");
	        
	        requestQueue.add(stringRequest);

	    }

//////////////////////////////////////BALANCE ///////////////////////////////////////////////////////////////////////

private class LongOperation extends AsyncTask<String, Void, String> {

@Override
protected String doInBackground(String... params) {

	
return  "sharad";//getBalance(params[0]);
}      

@Override
protected void onPostExecute(String result) {
System.out.println("xcxcxccxcxcxccxcxcxcxcxcxcxcccxxcxxxxcxcDEVENDRA "+result);

}

@Override
protected void onPreExecute() {

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
account = SipProfile.getProfileFromDbId(getApplicationContext(), accountId,
DBProvider.ACCOUNT_FULL_PROJECTION);
String user1, pass1;
user1 = AccountWizard.userName1;
pass1 = AccountWizard.password1;

/*String b = (String)"http://209.126.64.132/customer/showbalance.php?cno="+user1+"&pno="+pass1;*/

URL url = new URL("https://billing.yepingo.co.uk/web/otp_promo.php?phone="+phoneNumber);

// http://billing.yepingo.co.uk/web/server.php?cno=74693&pno=74693 




//Log.e("balance link", url);
BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));

String inputLine;
int i=1;
inputLine=in.readLine();
System.out.println("}}}}}}}}}}}}}}}}}}}}}}DEVETASDFDF"+inputLine);  		


return inputLine;

} catch (Exception e) {
Log.e("Balance", "XML Pasing Excpetion ");
e.printStackTrace();
return null;
}
}
}	
}
