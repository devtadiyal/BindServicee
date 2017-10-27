package com.mobilesoftphone4.ui.account;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.mobilesoftphone4.R;
import com.mobilesoftphone4.api.SipManager;
import com.mobilesoftphone4.api.SipProfile;
import com.mobilesoftphone4.db.DBProvider;
import com.mobilesoftphone4.models.Filter;
import com.mobilesoftphone4.ui.OTPMainActivity;
import com.mobilesoftphone4.ui.SipHome;
import com.mobilesoftphone4.ui.dialpad.DialerFragment;
import com.mobilesoftphone4.utils.AccountListUtils;
import com.mobilesoftphone4.utils.AccountListUtils.AccountStatusDisplay;
import com.mobilesoftphone4.utils.PreferencesWrapper;
import com.mobilesoftphone4.utils.animation.ActivitySwitcher;
import com.mobilesoftphone4.wizards.WizardIface;
import com.mobilesoftphone4.wizards.impl.Basic;

import org.apache.commons.codec.binary.Base64;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

//import com.androidquery.service.MarketService;

public class AccountWizard extends SherlockActivity {
	private static final int PARAMS_MENU = 0;
	private final static int CHANGE_PREFS = 1;
	boolean shouldExecuteOnResume;
	TextView status;
	// CheckBox cb;
	boolean toggle=false;
	public static EditText pass, user, sip, proxy;
	protected SipProfile account = null;
	private WizardIface wizard = null;
	public static String userName1,password1;
	private String wizardId = "";
	private BroadcastReceiver mReceiver,mReceiver1;
	IntentFilter intentFilter,intentFilter1;
	public  SharedPreferences app_preferences1;
	public SharedPreferences.Editor editor1;
	Boolean isFirstTime1;
	protected void onCreate(Bundle savedInstanceState) {
		setWizardId();

		super.onCreate(savedInstanceState);


		setContentView(R.layout.account_wizard);


		// cb = (CheckBox) findViewById(R.id.acc_checkbox);
		pass = (EditText) findViewById(R.id.acc_pass);
		user = (EditText) findViewById(R.id.acc_user);
		sip = (EditText) findViewById(R.id.acc_sip);
		proxy = (EditText) findViewById(R.id.acc_proxy);
		status = (TextView) findViewById(R.id.status);
		long accountId = 1;
		account = SipProfile.getProfileFromDbId(this, accountId,
				DBProvider.ACCOUNT_FULL_PROJECTION);

		shouldExecuteOnResume = true;


		saveAndFinish();

		//Intent myIntent = new Intent(AccountWizard.this, SipHome.class);
//myIntent.putExtra("uname", uname);
//myIntent.putExtra("password", password);
		//startActivity(myIntent);
//finish();
		app_preferences1 = PreferenceManager
				.getDefaultSharedPreferences(AccountWizard.this);

		editor1 = app_preferences1.edit();

		isFirstTime1 = app_preferences1.getBoolean("isFirstTime", true);
	}

	@Override
	protected void onStart() {
		super.onStart();
		Log.i("OnStartinAccount", "On Start .....");
	}
	@Override
	protected void onRestart() {
		super.onRestart();
		Log.i("onRestart", "On Restart .....");
	}


	public void onResume() {
		super.onResume();
		//ActivitySwitcher.animationIn(findViewById(R.id.account_layout),getWindowManager());
		//sip.setText(account.getSipDomain());
//		if (isFirstTime1) //isFirstTime
//		{

		System.out.println("FIRSTTIME--Call+"+isFirstTime1+userName1+"  Password  "+password1+"  mReceiver1 "+mReceiver1);
		// Your onResume Code Here
		System.out.println("HI 1");
		user.setText(userName1);
		pass.setText(password1);
		System.out.println("HI 2"+intentFilter1);
		/*if (account.getProxyAddress().length()!=0)
		{
		String prox = account.getProxyAddress().substring(4);
			proxy.setText(prox);
		}*/

		this.resolveStatus();

		// Register Broadcast Reciever
		intentFilter = new IntentFilter(
				SipManager.ACTION_SIP_REGISTRATION_CHANGED);
		mReceiver = new BroadcastReceiver() {
			@Override
			public void onReceive(Context context, Intent intent) {
				Log.e("Broadcast", "registration changed");
				IntentFilter intentfilter = new IntentFilter();
				intentfilter.addAction(SipManager.ACTION_GET_EXTRA_CODECS);
				System.out.println("Intent call!!!!!!!!!!!!");
				AccountStatusDisplay accountStatusDisplay = AccountListUtils
						.getAccountDisplay(getApplicationContext(), 1);
				status.setTextColor(accountStatusDisplay.statusColor);
				status.setText(accountStatusDisplay.statusLabel);
				if (accountStatusDisplay.statusLabel == getString(R.string.acct_registered));
				animate();
			}
		};
		this.registerReceiver(mReceiver1, intentFilter1);
		System.out.println("HI 3");
//		} else{
//			editor1.putBoolean("isFirstTimeAccountW", false);
//			editor1.commit();}
//		editor1.putBoolean("isFirstTime", false);
//		editor1.commit();
	}

	public void onPause() {
		super.onPause();
		//this.unregisterReceiver(this.mReceiver);
		this.unregisterReceiver(this.mReceiver1);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(Menu.NONE, PARAMS_MENU, Menu.NONE, R.string.prefs)
				.setIcon(android.R.drawable.ic_menu_preferences)
				.setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case PARAMS_MENU:
				startActivityForResult(
						new Intent(SipManager.ACTION_UI_PREFS_GLOBAL), CHANGE_PREFS);
				return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == CHANGE_PREFS) {
			sendBroadcast(new Intent(SipManager.ACTION_SIP_REQUEST_RESTART));
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	public void onClick(View v) {
		int id = v.getId();
		switch (id) {
		/*
		 * case R.id.acc_checkbox: // checkbox script to save account
		 * saveAccounts(); break;203.123.147.41
		 */
			case R.id.acc_login:
				// login script

				new LongOperation1().execute("");
				saveAndFinish();


				//animate();
				break;
			case R.id.acc_clear:
				// checkbox script
				clearAlll();
				break;
		}
	}

	void saveAccounts() {

	}

	private boolean setWizardId() {

		try {
			//			wizard = Advanced.class.newInstance();
			wizard = Basic.class.newInstance();
		} catch (IllegalAccessException e) {

			return false;
		} catch (InstantiationException e) {

			return false;
		}
		//		wizardId = "Advanced";
		wizardId = "Basic";
		// wizard.setParent(this);
		return true;
	}

	public void saveAndFinish() {
		System.out.println(userName1+"<-----response save finish");
		//if (validate()) {

		intentFilter1 = new IntentFilter(
				SipManager.ACTION_SIP_REGISTRATION_CHANGED);
		mReceiver1 = new BroadcastReceiver() {
			@Override
			public void onReceive(Context context, Intent intent) {
				System.out.println(password1+" "+mReceiver1+" "+intentFilter1+" "+wizardId+"<-----Inside of broadcast ");
				Log.e("Broadcast", "registration changed");
				IntentFilter intentfilter = new IntentFilter();
				intentfilter.addAction(SipManager.ACTION_GET_EXTRA_CODECS);
				System.out.println("Intent call!!!!!!!!!!!!");
				AccountStatusDisplay accountStatusDisplay = AccountListUtils
						.getAccountDisplay(getApplicationContext(), 1);
				status.setTextColor(accountStatusDisplay.statusColor);
				status.setText(accountStatusDisplay.statusLabel);
				if (accountStatusDisplay.statusLabel == getString(R.string.acct_registered))
					animate();

			}
		};
		this.registerReceiver(mReceiver1, intentFilter1);

		System.out.println(password1+" "+mReceiver1+" "+intentFilter1+" "+wizardId+"<-----response save pwd finish out side broadcase");

		//this.registerReceiver(mReceiver, intentFilter);
		saveAccount(wizardId);
		updateStatus();
		// IntentFilter intentfilter = new IntentFilter();
		//	intentfilter.addAction(SipManager.ACTION_GET_EXTRA_CODECS);
		//presenceMgr = new PresenceManager();


		Intent intent = getIntent();
		setResult(RESULT_OK, intent);
		//	}

	}

	public boolean isOnline() {
		ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

		return cm.getActiveNetworkInfo() != null
				&& cm.getActiveNetworkInfo().isConnectedOrConnecting();
	}

	boolean validate() {
		if (!isOnline()) {
			status.setTextColor(Color.RED);
			showNetworkAlert();
			// status.setText("There is no active network..");
			return false;
		}
		/*if (sip.getText().length() == 0) {
			status.setTextColor(Color.RED);
			status.setText("SIP IP field can not be blank..");
			sip.requestFocus();
			return false;
		}*/
		if (user.getText().length() == 0) {
			status.setTextColor(Color.RED);
			status.setText("username field can not be blank..");
			user.requestFocus();
			return false;
		}
		if (pass.getText().length() == 0) {
			status.setTextColor(Color.RED);
			status.setText("password field can not be blank..");
			pass.requestFocus();
			return false;
		}
		return true;
	}

	void showNetworkAlert() {
		new AlertDialog.Builder(this)
				.setTitle("Alert")
				.setMessage(
						"No active network detected...press ok to check network settings")
				.setNeutralButton("OK", new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {
						// anurag
						Intent intent = new Intent(Intent.ACTION_MAIN);
						intent.setAction(android.provider.Settings.ACTION_WIRELESS_SETTINGS);
						startActivity(intent);
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

	void updateStatus() {
		AccountStatusDisplay accountStatusDisplay = AccountListUtils
				.getAccountDisplay(this, account.id);
		System.out.println(accountStatusDisplay+"<-----accountStatusDisplay in AccountWizard");

		status.setTextColor(getResources().getColor(R.color.account_inactive));
		status.setText(getString(R.string.acct_registering));
	}

	private void saveAccount(String wizardId) {
		boolean needRestart = false;

		PreferencesWrapper prefs = new PreferencesWrapper(
				getApplicationContext());
		account = wizard.buildAccount(account);
		account.wizard = wizardId;
		if (account.id == SipProfile.INVALID_ID) {
			// This account does not exists yet
			prefs.startEditing();
			wizard.setDefaultParams(prefs);
			prefs.endEditing();
			Uri uri = getContentResolver().insert(SipProfile.ACCOUNT_URI,
					account.getDbContentValues());

			// After insert, add filters for this wizard
			account.id = ContentUris.parseId(uri);
			List<Filter> filters = wizard.getDefaultFilters(account);
			if (filters != null) {
				for (Filter filter : filters) {
					// Ensure the correct id if not done by the wizard
					filter.account = (int) account.id;
					getContentResolver().insert(SipManager.FILTER_URI,
							filter.getDbContentValues());
				}
			}
			// Check if we have to restart
			needRestart = wizard.needRestart();

		} else {
			// TODO : should not be done there but if not we should add an
			// option to re-apply default params
			prefs.startEditing();
			wizard.setDefaultParams(prefs);
			prefs.endEditing();
			getContentResolver().update(
					ContentUris.withAppendedId(SipProfile.ACCOUNT_ID_URI_BASE,
							account.id), account.getDbContentValues(), null,
					null);
		}

		// Mainly if global preferences were changed, we have to restart sip
		// stack
		if (needRestart) {
			Intent intent = new Intent(SipManager.ACTION_SIP_REQUEST_RESTART);
			sendBroadcast(intent);
		}
	}

	private void resolveStatus() {

		AccountStatusDisplay accountStatusDisplay = AccountListUtils
				.getAccountDisplay(this, 1);
		status.setTextColor(accountStatusDisplay.statusColor);
		status.setText(accountStatusDisplay.statusLabel);

	}

	private void clearAlll() {
		String clear = "";
		pass.setText(clear);
		user.setText(clear);
		//sip.setText(clear);
		//proxy.setText(clear);
	}

	private static final String WIZARD_PREF_NAME = "Wizard";

	@Override
	public SharedPreferences getSharedPreferences(String name, int mode) {
		return super.getSharedPreferences(WIZARD_PREF_NAME, mode);
	}

	void animate() {

		ActivitySwitcher.animationOut(findViewById(R.id.account_layout),
				getWindowManager(),
				new ActivitySwitcher.AnimationFinishedListener() {
					@Override
					public void onAnimationFinished() {
						DialerFragment.wasAccount = true;
						finish();
					}
				});

	}

	@Override
	public void onBackPressed() {
		AccountStatusDisplay accountStatusDisplay = AccountListUtils
				.getAccountDisplay(this, 1);
		if (accountStatusDisplay.statusLabel == getString(R.string.acct_registered)) {
			animate();
		} else {
			Intent intent = new Intent();
			intent.setAction(Intent.ACTION_MAIN);
			intent.addCategory(Intent.CATEGORY_HOME);
			startActivity(intent);
		}
	}

	private class LongOperation1 extends AsyncTask<String, Void, String> {

		JSONObject json;
		String message,str3;

		@Override
		protected String doInBackground(String... params) {


			return  getBalance(params[0]);
		}

		@Override
		public void onPostExecute(String result) {




		}

		protected void onPreExecute() {

		}

		protected void onProgressUpdate(Void... values) {
		}
		public String getBalance(String user) {

			try {
				System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@ : "+user);

				SipProfile account;
				long accountId = 1;
				account = SipProfile.getProfileFromDbId(getApplicationContext(), accountId,
						DBProvider.ACCOUNT_FULL_PROJECTION);
				String inputLine;

				String url = "http://billing.adoreinfotech.co.in/vibgyor_4700/billing_firebase/fcm_notification.php";
				URL obj = new URL(url);
				HttpURLConnection con = (HttpURLConnection) obj.openConnection();

				//add reuqest header
				con.setRequestMethod("POST");
				//con.setRequestProperty("User-Agent", USER_AGENT);
				con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
				String key = "1hg45&^$%#$34567";
				String data1 = account.getSipUserName();
				System.out.println("USERNAME "+data1);

				File f111 = new File(getFilesDir(), "devika.txt");
				try {
					BufferedReader bf = new BufferedReader(new FileReader(f111));
					String sss;


					while((sss = bf.readLine())!=null)
					{
						str3+=sss;
					}
					System.out.println(str3+"TIMEOUT HAI VALUE"+str3.length());
					bf.close();

					finish();

				} catch (IOException e) {
					// TODO Auto-generated catch block

					e.printStackTrace();
				}





				String data2= str3.replace("null","");
				System.out.println("FIREBASE ID HAI"+data2);


				String output1 =encrypt(data1, key);
				String output2 =encrypt(data2, key);



				String a=(asHex(output1.getBytes()));
				System.out.println("ENCRYPTED USERNAME " +a);
				String b=(asHex(output2.getBytes()));
				System.out.println("ENCRYPTED PASSWORD "+b);
				/*String c=(asHex(output3.getBytes()));
				System.out.println("ENCRYPTED MOBILENUMBER "+c);
				String d=(asHex(output4.getBytes()));
				System.out.println("ENCRYPTED MESSAGE "+d);*/

				String e  = b.replaceAll(" ", "%20");

				String urlParameters = "username="+a+"&regid="+b;

				// Send post request
				con.setDoOutput(true);
				DataOutputStream wr = new DataOutputStream(con.getOutputStream());
				wr.writeBytes(urlParameters);
				wr.flush();
				wr.close();

				System.out.println("\nSending 'POST' request to URL : " + url);
				System.out.println("Post parameters : " + urlParameters);
				//System.out.println("Response Code : " + responseCode);

				BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));

				StringBuilder response = new StringBuilder();
				while ((inputLine = in.readLine()) != null) {
					response.append(inputLine);
				}
				in.close();
				System.out.println(response.toString());
				String jsonvalue=response.toString();
				System.out.println("TADIYALSINGHDEV DDDDDDDDDDDDDDDDDDDDDDDDDDDDDDD"+jsonvalue);
				JSONObject json = null;

				return inputLine;

			} catch (Exception e) {
				Log.e("Balance", "XML Pasing Excpetion ");
				e.printStackTrace();
				return null;
			}

		}

		private String encrypt(String input, String key) {
			// TODO Auto-generated method stub
			byte[] crypted = null;
			try{
				SecretKeySpec skey = new SecretKeySpec(key.getBytes(), "AES");
				Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
				cipher.init(Cipher.ENCRYPT_MODE, skey);
				crypted = cipher.doFinal(input.getBytes());
			}catch(Exception e){
				System.out.println(e.toString());
			}
			return new String(Base64.encodeBase64(crypted));
		}

		public final char[] HEX_CHARS = "0123456789abcdef".toCharArray();

		public  String asHex(byte[] buf)
		{
			char[] chars = new char[2 * buf.length];
			for (int i = 0; i < buf.length; ++i)
			{
				chars[2 * i] = HEX_CHARS[(buf[i] & 0xF0) >>> 4];
				chars[2 * i + 1] = HEX_CHARS[buf[i] & 0x0F];
			}
			return new String(chars);
		}
	}



}