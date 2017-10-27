package com.mobilesoftphone4.ui.more;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.mobilesoftphone4.R;
import com.mobilesoftphone4.api.SipManager;
import com.mobilesoftphone4.api.SipProfile;
import com.mobilesoftphone4.api.SipUri;
import com.mobilesoftphone4.db.DBProvider;
import com.mobilesoftphone4.ui.SipHome;
import com.mobilesoftphone4.ui.account.AccountWizard;
import com.mobilesoftphone4.utils.Log;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.io.IOException;
public class RefillVoucher extends Activity{ 
	private ProgressDialog dialog;
	EditText edittext1;
	TextView refill,balance,text;
  public static String str,user,pass;
	SipProfile account;
	ConnectivityManager connMgr;
	NetworkInfo networkInfo;
	int a[]={33,3,4,5};
	int b = 5000;
	int c = 1000;
	public void onCreate(Bundle savedInstanceState){
	super.onCreate(savedInstanceState);
	setContentView(R.layout.voucherrefill);
	
	 edittext1=(EditText)findViewById(R.id.CHECK_REFILL_EDIT);  
	 text=(TextView)findViewById(R.id.textView2); 
	 
	
	}
public void onResume()
{
	super.onResume();
	
}

public void show()
{
	/*new CountDownTimer(b,c) {

	    public void onTick(long millisUntilFinished) {
	        text.setText("seconds remaining: " + millisUntilFinished / 1000);
	       //here you can have your logic to set text to edittext
	    }

	    public void onFinish() {
	        text.setText("done!");
	        System.out.println("LLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLL ");
	        
	       int i;
	       
	        	show();
	        
	        
	    }

	}.start();*/
	
}
private void CallMultipleToast(){
	  Thread t = new Thread(){
	        @Override
	        public void run(){
	            try {
	                for(int i1=0;i1<=6;i1++){
	                  
	                  // Toast.makeText(getApplication(), "Message "+(i1+1), Toast.LENGTH_LONG).show();	
	                   System.out.println(i1+1);
	                	sleep(5000);
	                }

	            } catch (InterruptedException ex) {
	                Log.i("error","thread");
	            }
	        }
	    };
	   t.start();
	 }
public void onClick(View v) {
	int id = v.getId();
	switch (id) {
	
	case R.id.REFILL_BUTTON:
		/*String url = "http://www.google.com";
		Intent i = new Intent(Intent.ACTION_VIEW);
		i.setData(Uri.parse(url)); 
		startActivity(i); */
		
		 connMgr = (ConnectivityManager)getApplication().getSystemService(Context.CONNECTIVITY_SERVICE);

	        networkInfo = connMgr.getActiveNetworkInfo();
		
		if (networkInfo != null && networkInfo.isConnected()) 
        {
		
 
        
		
		 if(validate())
   	  {
   		 str=edittext1.getText().toString();
   		  System.out.println("destination value : "+str);
   		  
   		 
   		  
   			  
   			String str=edittext1.getText().toString();
   				System.out.println("****************************************"+str);
   				new LongOperation().execute(user);
   		
   		  
   		 
   	  }
        } else
	        {
	        	 Toast.makeText(getApplication(), "Please check your network first", Toast.LENGTH_LONG).show();	
	        }
		/* String str=edittext1.getText().toString();
		System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%"+str);
		new LongOperation().execute(user);*/
		
		break;
	case R.id.exit:
		//show();
		finish();
		// CallMultipleToast();
		break;

		
	}
}




public boolean validate()
{
	String str=edittext1.getText().toString();
	int dest_Length=str.length();
	
	if(dest_Length==0)
	{
		Toast.makeText(this, "Please first enter Voucher Number", Toast.LENGTH_LONG).show();
		
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
					"Invalid Voucher number.")
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

    @Override
    protected String doInBackground(String... params) {
    	
								
          return  getBalance(params[0]);
    }      

    @Override
    protected void onPostExecute(String result) {  

    	/*balance.setText("Bal: "+result);*/
    	if(result.contains("Voucher doesn't Exist!"))
    	{  
    		showPinAlert();	
    	}else
    	{    
    		
    		new AlertDialog.Builder(RefillVoucher.this)
			.setTitle("Alert")
			.setMessage(result)
			.setNeutralButton("OK", new DialogInterface.OnClickListener()
			{

				public void onClick(DialogInterface dialog, int which)
				{
					 Intent it = new Intent(RefillVoucher.this,SipHome.class);
			        	//  it.putExtra("did",str);
			        	  it.setAction(SipManager.ACTION_SIP_DIALER);
			        	  it.setData(SipUri.forgeSipUri(SipManager.PROTOCOL_SIP, ""));
			        	  startActivity(it);
					
					// anurag
					/*Intent intent = new Intent(Intent.ACTION_MAIN);
					intent.setAction(android.provider.Settings.ACTION_WIRELESS_SETTINGS);
					startActivity(intent);*/
				}
			}).show();
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
	   
	   dialog=new ProgressDialog(RefillVoucher.this);
		dialog.setTitle("Please wait.");
		dialog.setMessage("Recharge.");
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
			account = SipProfile.getProfileFromDbId(RefillVoucher.this, accountId,
					DBProvider.ACCOUNT_FULL_PROJECTION);
			String user1, pass1,inputLine;
			String Balance,doller;
			user1 = AccountWizard.userName1;
  			pass1 = AccountWizard.password1;
			String str=edittext1.getText().toString();
			System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<"+str);
//String b = (String)"http://sip.call-lite.sg//a2billing/customer/androida2billing/index.php?action=voucher-refill-v2&username= "+user1+"&password="+pass1+"&hash="+user1+"."+pass1+".Ky@w2u&voucher="+str+"&type=sip";
             
			//String b= (String)"https://account.yepingo.com/customer/refill_dialer_voucher.php?cno="+user1+"&pno="+pass1+"&vno="+str;
			
		/*	
			URL oracle = new URL("https://account.yepingo.com/customer/refill_dialer_voucher.php?cno="+user1+"&pno="+pass1+"&vno="+str);
            BufferedReader in = new BufferedReader(new InputStreamReader(oracle.openStream()));
            inputLine =in.readLine();
           System.out.println("???????????????????????????????????????????"+oracle);
            in.close();
			*/
			
			
            JSONObject json = null;
	        String temp = "";
	        HttpResponse response;
	        HttpClient myClient = new DefaultHttpClient();
	        
	        
	       // HttpPost myConnection = new HttpPost("http://54.83.58.149/customer/refill_dialer_voucher.php?cno="+user1+"&pno="+pass1+"&vno="+str);
	        HttpPost myConnection = new HttpPost("https://billing.yepingo.co.uk/customer/refill_dialer_voucher.php?cno="+user1+"&pno="+pass1+"&vno="+str); 

                                                  //https://billing.yepingo.co.uk/customer/refill_dialer_voucher.php?cno=74693&pno=74693&vno=164943895865074

	        try {
	            response = myClient.execute(myConnection);
	            temp = EntityUtils.toString(response.getEntity(), "UTF-8");
	    
	            
	            System.out.println("##########################################################"+temp);
	        } catch (ClientProtocolException e) {
	            e.printStackTrace();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
			
			
			
			/*Log.e("balance link", b);

			URL url = new URL(b);

			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(new InputSource(url.openStream()));
			doc.getDocumentElement().normalize();

			NodeList nodeList = doc.getElementsByTagName("response");
			Node node = nodeList.item(0);
			Element fstElmnt = (Element) node;
			NodeList balList = fstElmnt.getElementsByTagName("bal");
			Element balElement = (Element) balList.item(0);
			balList = balElement.getChildNodes();

			Balance = ((Node) balList.item(0)).getNodeValue();
*/
		/*	NodeList curList = fstElmnt.getElementsByTagName("currency");
			Element curElement = (Element) curList.item(0);
			curList = curElement.getChildNodes();

			Currency = ((Node) curList.item(0)).getNodeValue();
			doller =Balance+" "+Currency;
			 */
			
			//Balance="$"+" ";

			return temp;

		} catch (Exception e) {
			Log.e("Balance", "XML Pasing Excpetion ");
			e.printStackTrace();
			return null;
		}

	}
}  







