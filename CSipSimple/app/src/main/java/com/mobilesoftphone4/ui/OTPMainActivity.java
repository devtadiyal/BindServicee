package com.mobilesoftphone4.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.telephony.TelephonyManager;
import android.text.Html;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;

public class OTPMainActivity extends Activity {
String scountry ;
Map countryLookupMap = null;
String countryCodeValue=null;
ArrayList<String> countries ;
private EditText editPhoneNumber,editCountryCode;
private Button buttonRegister;
private String phoneNumber="phone_number";
public static CheckBox checkBox;
public static TextView terms;
private RequestQueue requestQueue;
String mPhoneNumber;
private  SharedPreferences app_preferences;
private SharedPreferences.Editor editor;
private Boolean isFirstTime;
long accountId = 1;
private SipProfile account = null;
private TextView status;
	final IncomingSms broadcast_receiver = new IncomingSms();

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.otp_activity_main);
        terms = (TextView) findViewById(R.id.textView1);
        checkBox = (CheckBox) findViewById(R.id.checkBox1);
         app_preferences = PreferenceManager
                .getDefaultSharedPreferences(OTPMainActivity.this);

     editor = app_preferences.edit();

    isFirstTime = app_preferences.getBoolean("isFirstTime", true);
    account = SipProfile.getProfileFromDbId(getApplication(), accountId,
    		DBProvider.ACCOUNT_FULL_PROJECTION);
    String user1, pass1;
    System.out.println("user1 account------>"+account);
    if(account !=null)
    {
	AccountWizard.userName1 = account.getSipUserName();
	AccountWizard.password1 = account.getPassword();
	System.out.println("user1 user1------>"+ AccountWizard.userName1+"  "+ AccountWizard.password1);
    }
    if (isFirstTime) //isFirstTime  
    {
    	displayScreen();
    	
    }
    else if(AccountWizard.userName1!=null && AccountWizard.password1!=null && isFirstTime==false)
    {
    	 System.out.println("The value in the OTP home 2"+isFirstTime);
    	   
        //app open directly
//    	Intent myIntent = new Intent(OTPMainActivity.this, SipHome.class);
//		//myIntent.putExtra("uname", uname);
//		//myIntent.putExtra("password", password);
//		startActivity(myIntent);
//		finish();
        }
 else
 {
	 displayScreen();
	 
 }
    }
    public void displayScreen()
    {
    	 System.out.println("The value in the OTP home"+isFirstTime);
    	    //implement your first time logic
    	    editor.putBoolean("isFirstTime", false);
    	    editor.commit();

    	   
    	        requestQueue = Volley.newRequestQueue(this);

    	        countryLookupMap = new HashMap<String, String>();

    	        countryLookupMap.put("AD","Andorra");
    	        countryLookupMap.put("AE","United Arab Emirates");
    	        countryLookupMap.put("AF","Afghanistan");
    	        countryLookupMap.put("AG","Antigua and Barbuda");
    	        countryLookupMap.put("AI","Anguilla");
    	        countryLookupMap.put("AL","Albania");
    	        countryLookupMap.put("AM","Armenia");
    	        countryLookupMap.put("AN","Netherlands Antilles");
    	        countryLookupMap.put("AO","Angola");
    	        countryLookupMap.put("AQ","Antarctica");
    	        countryLookupMap.put("AR","Argentina");
    	        countryLookupMap.put("AS","American Samoa");
    	        countryLookupMap.put("AT","Austria");
    	        countryLookupMap.put("AU","Australia");
    	        countryLookupMap.put("AW","Aruba");
    	        countryLookupMap.put("AZ","Azerbaijan");
    	        countryLookupMap.put("BA","Bosnia and Herzegovina");
    	        countryLookupMap.put("BB","Barbados");
    	        countryLookupMap.put("BD","Bangladesh");
    	        countryLookupMap.put("BE","Belgium");
    	        countryLookupMap.put("BF","Burkina Faso");
    	        countryLookupMap.put("BG","Bulgaria");
    	        countryLookupMap.put("BH","Bahrain");
    	        countryLookupMap.put("BI","Burundi");
    	        countryLookupMap.put("BJ","Benin");
    	        countryLookupMap.put("BM","Bermuda");
    	        countryLookupMap.put("BN","Brunei");
    	        countryLookupMap.put("BO","Bolivia");
    	        countryLookupMap.put("BR","Brazil");
    	        countryLookupMap.put("BS","Bahamas");
    	        countryLookupMap.put("BT","Bhutan");
    	        countryLookupMap.put("BV","Bouvet Island");
    	        countryLookupMap.put("BW","Botswana");
    	        countryLookupMap.put("BY","Belarus");
    	        countryLookupMap.put("BZ","Belize");
    	        countryLookupMap.put("CA","Canada");
    	        countryLookupMap.put("CC","Cocos (Keeling) Islands");
    	        countryLookupMap.put("CD","Congo, The Democratic Republic of the");
    	        countryLookupMap.put("CF","Central African Republic");
    	        countryLookupMap.put("CG","Congo");
    	        countryLookupMap.put("CH","Switzerland");
    	        countryLookupMap.put("CI","C�te d?Ivoire");
    	        countryLookupMap.put("CK","Cook Islands");
    	        countryLookupMap.put("CL","Chile");
    	        countryLookupMap.put("CM","Cameroon");
    	        countryLookupMap.put("CN","China");
    	        countryLookupMap.put("CO","Colombia");
    	        countryLookupMap.put("CR","Costa Rica");
    	        countryLookupMap.put("CU","Cuba");
    	        countryLookupMap.put("CV","Cape Verde");
    	        countryLookupMap.put("CX","Christmas Island");
    	        countryLookupMap.put("CY","Cyprus");
    	        countryLookupMap.put("CZ","Czech Republic");
    	        countryLookupMap.put("DE","Germany");
    	        countryLookupMap.put("DJ","Djibouti");
    	        countryLookupMap.put("DK","Denmark");
    	        countryLookupMap.put("DM","Dominica");
    	        countryLookupMap.put("DO","Dominican Republic");
    	        countryLookupMap.put("DZ","Algeria");
    	        countryLookupMap.put("EC","Ecuador");
    	        countryLookupMap.put("EE","Estonia");
    	        countryLookupMap.put("EG","Egypt");
    	        countryLookupMap.put("EH","Western Sahara");
    	        countryLookupMap.put("ER","Eritrea");
    	        countryLookupMap.put("ES","Spain");
    	        countryLookupMap.put("ET","Ethiopia");
    	        countryLookupMap.put("FI","Finland");
    	        countryLookupMap.put("FJ","Fiji Islands");
    	        countryLookupMap.put("FK","Falkland Islands");
    	        countryLookupMap.put("FM","Micronesia, Federated States of");
    	        countryLookupMap.put("FO","Faroe Islands");
    	        countryLookupMap.put("FR","France");
    	        countryLookupMap.put("GA","Gabon");
    	        countryLookupMap.put("GB","United Kingdom");
    	        countryLookupMap.put("GD","Grenada");
    	        countryLookupMap.put("GE","Georgia");
    	        countryLookupMap.put("GF","French Guiana");
    	        countryLookupMap.put("GH","Ghana");
    	        countryLookupMap.put("GI","Gibraltar");
    	        countryLookupMap.put("GL","Greenland");
    	        countryLookupMap.put("GM","Gambia");
    	        countryLookupMap.put("GN","Guinea");
    	        countryLookupMap.put("GP","Guadeloupe");
    	        countryLookupMap.put("GQ","Equatorial Guinea");
    	        countryLookupMap.put("GR","Greece");
    	        countryLookupMap.put("GS","South Georgia and the South Sandwich Islands");
    	        countryLookupMap.put("GT","Guatemala");
    	        countryLookupMap.put("GU","Guam");
    	        countryLookupMap.put("GW","Guinea-Bissau");
    	        countryLookupMap.put("GY","Guyana");
    	        countryLookupMap.put("HK","Hong Kong");
    	        countryLookupMap.put("HM","Heard Island and McDonald Islands");
    	        countryLookupMap.put("HN","Honduras");
    	        countryLookupMap.put("HR","Croatia");
    	        countryLookupMap.put("HT","Haiti");
    	        countryLookupMap.put("HU","Hungary");
    	        countryLookupMap.put("ID","Indonesia");
    	        countryLookupMap.put("IE","Ireland");
    	        countryLookupMap.put("IL","Israel");
    	        countryLookupMap.put("IN","India");
    	        countryLookupMap.put("IO","British Indian Ocean Territory");
    	        countryLookupMap.put("IQ","Iraq");
    	        countryLookupMap.put("IR","Iran");
    	        countryLookupMap.put("IS","Iceland");
    	        countryLookupMap.put("IT","Italy");
    	        countryLookupMap.put("JM","Jamaica");
    	        countryLookupMap.put("JO","Jordan");
    	        countryLookupMap.put("JP","Japan");
    	        countryLookupMap.put("KE","Kenya");
    	        countryLookupMap.put("KG","Kyrgyzstan");
    	        countryLookupMap.put("KH","Cambodia");
    	        countryLookupMap.put("KI","Kiribati");
    	        countryLookupMap.put("KM","Comoros");
    	        countryLookupMap.put("KN","Saint Kitts and Nevis");
    	        countryLookupMap.put("KP","North Korea");
    	        countryLookupMap.put("KR","South Korea");
    	        countryLookupMap.put("KW","Kuwait");
    	        countryLookupMap.put("KY","Cayman Islands");
    	        countryLookupMap.put("KZ","Kazakstan");
    	        countryLookupMap.put("LA","Laos");
    	        countryLookupMap.put("LB","Lebanon");
    	        countryLookupMap.put("LC","Saint Lucia");
    	        countryLookupMap.put("LI","Liechtenstein");
    	        countryLookupMap.put("LK","Sri Lanka");
    	        countryLookupMap.put("LR","Liberia");
    	        countryLookupMap.put("LS","Lesotho");
    	        countryLookupMap.put("LT","Lithuania");
    	        countryLookupMap.put("LU","Luxembourg");
    	        countryLookupMap.put("LV","Latvia");
    	        countryLookupMap.put("LY","Libyan Arab Jamahiriya");
    	        countryLookupMap.put("MA","Morocco");
    	        countryLookupMap.put("MC","Monaco");
    	        countryLookupMap.put("MD","Moldova");
    	        countryLookupMap.put("MG","Madagascar");
    	        countryLookupMap.put("MH","Marshall Islands");
    	        countryLookupMap.put("MK","Macedonia");
    	        countryLookupMap.put("ML","Mali");
    	        countryLookupMap.put("MM","Myanmar");
    	        countryLookupMap.put("MN","Mongolia");
    	        countryLookupMap.put("MO","Macao");
    	        countryLookupMap.put("MP","Northern Mariana Islands");
    	        countryLookupMap.put("MQ","Martinique");
    	        countryLookupMap.put("MR","Mauritania");
    	        countryLookupMap.put("MS","Montserrat");
    	        countryLookupMap.put("MT","Malta");
    	        countryLookupMap.put("MU","Mauritius");
    	        countryLookupMap.put("MV","Maldives");
    	        countryLookupMap.put("MW","Malawi");
    	        countryLookupMap.put("MX","Mexico");
    	        countryLookupMap.put("MY","Malaysia");
    	        countryLookupMap.put("MZ","Mozambique");
    	        countryLookupMap.put("NA","Namibia");
    	        countryLookupMap.put("NC","New Caledonia");
    	        countryLookupMap.put("NE","Niger");
    	        countryLookupMap.put("NF","Norfolk Island");
    	        countryLookupMap.put("NG","Nigeria");
    	        countryLookupMap.put("NI","Nicaragua");
    	        countryLookupMap.put("NL","Netherlands");
    	        countryLookupMap.put("NO","Norway");
    	        countryLookupMap.put("NP","Nepal");
    	        countryLookupMap.put("NR","Nauru");
    	        countryLookupMap.put("NU","Niue");
    	        countryLookupMap.put("NZ","New Zealand");
    	        countryLookupMap.put("OM","Oman");
    	        countryLookupMap.put("PA","Panama");
    	        countryLookupMap.put("PE","Peru");
    	        countryLookupMap.put("PF","French Polynesia");
    	        countryLookupMap.put("PG","Papua New Guinea");
    	        countryLookupMap.put("PH","Philippines");
    	        countryLookupMap.put("PK","Pakistan");
    	        countryLookupMap.put("PL","Poland");
    	        countryLookupMap.put("PM","Saint Pierre and Miquelon");
    	        countryLookupMap.put("PN","Pitcairn");
    	        countryLookupMap.put("PR","Puerto Rico");
    	        countryLookupMap.put("PS","Palestine");
    	        countryLookupMap.put("PT","Portugal");
    	        countryLookupMap.put("PW","Palau");
    	        countryLookupMap.put("PY","Paraguay");
    	        countryLookupMap.put("QA","Qatar");
    	        countryLookupMap.put("RE","R�union");
    	        countryLookupMap.put("RO","Romania");
    	        countryLookupMap.put("RU","Russian Federation");
    	        countryLookupMap.put("RW","Rwanda");
    	        countryLookupMap.put("SA","Saudi Arabia");
    	        countryLookupMap.put("SB","Solomon Islands");
    	        countryLookupMap.put("SC","Seychelles");
    	        countryLookupMap.put("SD","Sudan");
    	        countryLookupMap.put("SE","Sweden");
    	        countryLookupMap.put("SG","Singapore");
    	        countryLookupMap.put("SH","Saint Helena");
    	        countryLookupMap.put("SI","Slovenia");
    	        countryLookupMap.put("SJ","Svalbard and Jan Mayen");
    	        countryLookupMap.put("SK","Slovakia");
    	        countryLookupMap.put("SL","Sierra Leone");
    	        countryLookupMap.put("SM","San Marino");
    	        countryLookupMap.put("SN","Senegal");
    	        countryLookupMap.put("SO","Somalia");
    	        countryLookupMap.put("SR","Suriname");
    	        countryLookupMap.put("ST","Sao Tome and Principe");
    	        countryLookupMap.put("SV","El Salvador");
    	        countryLookupMap.put("SY","Syria");
    	        countryLookupMap.put("SZ","Swaziland");
    	        countryLookupMap.put("TC","Turks and Caicos Islands");
    	        countryLookupMap.put("TD","Chad");
    	        countryLookupMap.put("TF","French Southern territories");
    	        countryLookupMap.put("TG","Togo");
    	        countryLookupMap.put("TH","Thailand");
    	        countryLookupMap.put("TJ","Tajikistan");
    	        countryLookupMap.put("TK","Tokelau");
    	        countryLookupMap.put("TM","Turkmenistan");
    	        countryLookupMap.put("TN","Tunisia");
    	        countryLookupMap.put("TO","Tonga");
    	        countryLookupMap.put("TP","East Timor");
    	        countryLookupMap.put("TR","Turkey");
    	        countryLookupMap.put("TT","Trinidad and Tobago");
    	        countryLookupMap.put("TV","Tuvalu");
    	        countryLookupMap.put("TW","Taiwan");
    	        countryLookupMap.put("TZ","Tanzania");
    	        countryLookupMap.put("UA","Ukraine");
    	        countryLookupMap.put("UG","Uganda");
    	        countryLookupMap.put("UM","United States Minor Outlying Islands");
    	        countryLookupMap.put("US","United States");
    	        countryLookupMap.put("UY","Uruguay");
    	        countryLookupMap.put("UZ","Uzbekistan");
    	        countryLookupMap.put("VA","Holy See (Vatican City State)");
    	        countryLookupMap.put("VC","Saint Vincent and the Grenadines");
    	        countryLookupMap.put("VE","Venezuela");
    	        countryLookupMap.put("VG","Virgin Islands, British");
    	        countryLookupMap.put("VI","Virgin Islands, U.S.");
    	        countryLookupMap.put("VN","Vietnam");
    	        countryLookupMap.put("VU","Vanuatu");
    	        countryLookupMap.put("WF","Wallis and Futuna");
    	        countryLookupMap.put("WS","Samoa");
    	        countryLookupMap.put("YE","Yemen");
    	        countryLookupMap.put("YT","Mayotte");
    	        countryLookupMap.put("YU","Yugoslavia");
    	        countryLookupMap.put("ZA","South Africa");
    	        countryLookupMap.put("ZM","Zambia");
    	        countryLookupMap.put("ZW","Zimbabwe");
    	        
    	        editCountryCode = (EditText) findViewById(R.id.edtCountryCode);
    	        editPhoneNumber = (EditText) findViewById(R.id.edtPhoneNumber);
    	        buttonRegister = (Button) findViewById(R.id.btnValidate);
    	        
    	      //  buttonRegister.setBackgroundColor(Color.BLUE);
    	        
    	        TelephonyManager tm = (TelephonyManager)this.getSystemService(this.TELEPHONY_SERVICE);
    	         countryCodeValue = tm.getNetworkCountryIso().toUpperCase();
    	        System.out.println("Country Code "+countryCodeValue.toUpperCase());
    	        
    	        //TelephonyManager tMgr = (TelephonyManager)mAppContext.getSystemService(Context.TELEPHONY_SERVICE);
    	        if(tm.getLine1Number()!=null && tm.getLine1Number()!="" && tm.getLine1Number().length()>=10)
    	        {
    	        	System.out.println("Country Code "+tm.getLine1Number().substring(3).trim());
    	        	mPhoneNumber = tm.getLine1Number().substring(tm.getLine1Number().length() - 10).trim();
    	        } 
    	        
    	        if(mPhoneNumber !=null && mPhoneNumber!="")
    	        {
    	        	  editPhoneNumber.setText(mPhoneNumber);
    	        }
    	        
    	        Locale[] locale = Locale.getAvailableLocales();
    	        countries = new ArrayList<String>();
    	        String country;
    	        for( Locale loc : locale ){
    	            country = loc.getDisplayCountry();
    	            if( country.length() > 0 && !countries.contains(country) ){
    	                countries.add( country );
    	            }
    	        }
    	        Collections.sort(countries, String.CASE_INSENSITIVE_ORDER);

    	        Spinner citizenship = (Spinner)findViewById(R.id.spinner);
    	        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_item, countries);
    	        citizenship.setAdapter(adapter);
    	        
    	        Iterator<Map.Entry<String, String>> iterator = countryLookupMap.entrySet().iterator() ;
    	        while(iterator.hasNext()){
    	        	 Map.Entry<String, String> studentEntry = iterator.next();
    	        	 //System.out.println("values are 1"+ countryCodeValue);
    	             if(studentEntry.getKey().equals(countryCodeValue))
    	             {   int an= countries.indexOf(studentEntry.getValue());
    	             System.out.println("values arefff "+ an);
    	            	 System.out.println("values arehhh "+ studentEntry.getValue());
    	            	 citizenship.setSelection(an,false );
    	            	 
    	             }
    	             //You can remove elements while iterating.
    	             
    	        }
    	        editCountryCode.setText(GetCountryZipCode());
    	        
    	       // listview.setAdapter(new CountriesListAdapter(this, recourseList));
    	        citizenship.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
    	            @Override
    	            public void onItemSelected(AdapterView<?> adapter, View v,
    	                                       int position, long id) {
    	                // On selecting a spinner item
    	                 scountry = adapter.getItemAtPosition(position).toString();
    	                // Showing selected spinner item
    	              /*  Toast.makeText(getApplicationContext(),
    	                        "Selected Country : " + scountry, Toast.LENGTH_SHORT).show();*/
    	            }
    	            @Override
    	            public void onNothingSelected(AdapterView<?> arg0) {
    	                // TODO Auto-generated method stub
    	            }
    	        });
    	        buttonRegister.setOnClickListener(new OnClickListener() {

    	    		@Override
    	    		public void onClick(View arg0) {
    	    			
    	    			   if(checkBox.isChecked())
    	    			   {
    	    			
    	    	phoneNumber = editPhoneNumber.getText().toString().trim();
    	    	 if(phoneNumber.length()<10 || phoneNumber.length()>10)
		          {
    	    		 AlertDialog.Builder builder1 = new  AlertDialog.Builder(OTPMainActivity.this);
                     
     	            builder1.setTitle("Alert Dialog");
                     builder1.setMessage("Please enter 10 digit number.");
     	            
 				     				    				 
	    		    	Toast.makeText(OTPMainActivity.this,
	    		                "Please enter 10 digit number", Toast.LENGTH_LONG).show();
	    		    	
 			 }
    	    	 else{
    			//System.out.println("values phoneNumber "+ phoneNumber);
    			AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(OTPMainActivity.this);

    			// set title
    			//alertDialogBuilder.setTitle("Your Title");

    			// set dialog message
    			alertDialogBuilder
    			
    				//.setMessage("We will be Verifying Your Phone Number"+"\n"+ phoneNumber +"\n Is this OK ,or you would like to edit the number ?")
    				
    				.setMessage( Html.fromHtml("<font color='#026D95'>We will be Verifying Your Phone Number"+"\n"+ phoneNumber +"\n Is this OK ,or you would like to edit the number ?</font>"))
    				//.setMessage("Is this OK ,or you would like to edit the number ?")
    				.setCancelable(false)
    				.setPositiveButton("OK",new DialogInterface.OnClickListener() {
    					public void onClick(DialogInterface dialog,int id) {
    						// if this button is clicked, close
    						// current activity
    						register();
    						
    					}
    				  })
    				.setNegativeButton("Edit",new DialogInterface.OnClickListener() {
    					public void onClick(DialogInterface dialog,int id) {
    						// if this button is clicked, just close
    						// the dialog box and do nothing
    						dialog.cancel();
    					}
    				});

    				// create alert dialog
    				AlertDialog alertDialog = alertDialogBuilder.create();
    				
    				
    				// show it
    				alertDialog.show();
    				alertDialog.getButton(alertDialog.BUTTON_NEGATIVE).setTextColor(Color.parseColor("#000000")); 
    				alertDialog.getButton(alertDialog.BUTTON_POSITIVE).setTextColor(Color.parseColor("#000000")); 
					alertDialog.getWindow().setBackgroundDrawableResource(android.R.color.white);}
    	    	 }
    	    			   else{
    	    				   Toast.makeText(OTPMainActivity.this, "Please agree to the Terms and Conditions.", Toast.LENGTH_LONG).show();
    	    			   }

    	    		}});
    	        
    	        
    	        
    	        
    	        terms.setOnClickListener(new OnClickListener() {
    	            public void onClick(View v) {
    	                Intent intent = new Intent();
    	                intent.setAction(Intent.ACTION_VIEW);
    	                intent.addCategory(Intent.CATEGORY_BROWSABLE);
    	                intent.setData(Uri.parse("https://yepingo.co.uk/terms-conditions.php"));
    	                startActivity(intent);
    	            }});
    	        
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


	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    public String GetCountryZipCode(){
        String CountryID="";
        String CountryZipCode="";

        TelephonyManager manager = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
        //getNetworkCountryIso
        CountryID= manager.getSimCountryIso().toUpperCase();
        String[] rl=this.getResources().getStringArray(R.array.CountryCodes);
        for(int i=0;i<rl.length;i++){
            String[] g=rl[i].split(",");
            if(g[1].trim().equals(CountryID.trim())){
                CountryZipCode=g[0];
                break;  
            }
        }
        return CountryZipCode;
    }
    
    
    

   
    
    //this method will register the user
    public void register() {//Displaying a progress dialog
        final ProgressDialog loading = ProgressDialog.show(this, "Registering", "Please wait...", false, false);
        loading.setMessage( Html.fromHtml("<font color='#026D95'>Connecting Please wait...</font>"));
        	    
        loading .getWindow().setBackgroundDrawableResource(android.R.color.white);
        //final IncomingSms broadcast_receiver = new IncomingSms();
        IntentFilter filter1 = new IntentFilter();
        filter1.addAction("android.provider.Telephony.SMS_RECEIVED");
        registerReceiver(broadcast_receiver, filter1);
        //Getting user data
        //username = editTextUsername.getText().toString().trim();
        //password = editTextPassword.getText().toString().trim();
//        phone = editTextPhone.getText().toString().trim();

        //Again creating the string request
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Config.REGISTER_URL.concat("0")+phoneNumber,
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
                                Intent myIntent = new Intent(OTPMainActivity.this, VerifyOTPActivity.class);
        						myIntent.putExtra("phoneNumber", phoneNumber); //Optional parameters
        						OTPMainActivity.this.startActivity(myIntent);
        						finish();
        						//OTPMainActivity.this.finish();
                                //confirmOtp();
                            }else{
                                //If not successful user may already have registered
                                Toast.makeText(OTPMainActivity.this, "Username or Phone number already registered", Toast.LENGTH_LONG).show();
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
                        Toast.makeText(OTPMainActivity.this, error.getMessage(),Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                //Adding the parameters to the request
//                params.put(Config.KEY_USERNAME, username);
  //             params.put(Config.KEY_PASSWORD, password);
                params.put(Config.KEY_PHONE, phoneNumber);
                return params;
            }
        };

        //Adding request the the queue
        System.out.println("hello"+stringRequest+"<-----stringRequest");

        requestQueue.add(stringRequest);}

    
}

	

