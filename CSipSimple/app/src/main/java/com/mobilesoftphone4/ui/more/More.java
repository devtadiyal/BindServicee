package com.mobilesoftphone4.ui.more;

import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragment;
import com.mobilesoftphone4.R;
import com.mobilesoftphone4.api.SipManager;
import com.mobilesoftphone4.api.SipProfile;
import com.mobilesoftphone4.db.DBProvider;
import com.mobilesoftphone4.ui.SipHome.ViewPagerVisibilityListener;
import com.mobilesoftphone4.ui.contacts.AddContactDialog;
import com.mobilesoftphone4.utils.Log;
import com.mobilesoftphone4.utils.PreferencesProviderWrapper;
import com.mobilesoftphone4.utils.PreferencesWrapper;

import java.util.ArrayList;
import java.util.List;
public class More extends SherlockFragment implements ViewPagerVisibilityListener{

	private Context c;
	private List<MoreItem> items;
	private ListView listView;
	private MoreListAdapter adapter;
	private PreferencesProviderWrapper prefProviderWrapper;
	private final static int CHANGE_PREFS = 1;

	//private final static int RECORDING = 1;
	//private final static int ABOUT = 1;
	//private final static int Test= 1;
//	private final static int LOCAL=2;
	private final static int MSG=3;
	private final static int RADIO= 5;
	private final static int TOPUP=1;
	private final static int WORLDPAY=2;
	private final static int SETTINGS = 6;
	private final static int PROFILE= 0;
	private final static int INVITE= 4;

	private final static int TERM= 7;
	//private final static int TRAVEL= 5;
	private final static int EXIT= 8;
	//private final static int EXIT1= 7;



	//private final static int Account= 6;
	public static int count ;
	;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		prefProviderWrapper = new PreferencesProviderWrapper(getActivity());
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {



//		super.onCreateView(inflater, container, savedInstanceState);
		View v = inflater.inflate(R.layout.more, container, false);
		c = getActivity().getBaseContext();
		items =getItems();
		adapter = new MoreListAdapter(getActivity(), items);
		listView = (ListView) v.findViewById(R.id.list);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(onListClick);
		return v;
	}
	public List<MoreItem> getItems() {
		List<MoreItem> list = new ArrayList<MoreItem>();

		// list.add(new MoreItem("Recordings",R.drawable.record,"Play the recorded files"));
		//    list.add(new MoreItem("CDR",R.drawable.cdr_img,"call history"));
		//   list.add(new MoreItem("Rates",R.drawable.rate,"check international call rates"));
		//    list.add(new MoreItem("Recharge",R.drawable.recharge,"recharge your existing account"));
		//list.add(new MoreItem(getString(R.string.about),R.drawable.about,getString(R.string.about_desc)));


		//  list.add(new MoreItem(getString(R.string.test),R.drawable.lkl,getString(R.string.test_desc)));
		//   list.add(new MoreItem(getString(R.string.local),R.drawable.lkl,getString(R.string.local_desc)));
		String prefs_desc=getString(R.string.prefs_network)+" & "+getString(R.string.prefs);

		list.add(new MoreItem(getString(R.string.profile),R.drawable.profile,getString(R.string.profile_desc)));
		list.add(new MoreItem(getString(R.string.top),R.drawable.topupp,getString(R.string.top_desc)));
		list.add(new MoreItem(getString(R.string.worldpay),R.drawable.topupp,getString(R.string.worldpay_desc)));
		//  list.add(new MoreItem(getString(R.string.travel),R.drawable.transfer,getString(R.string.travel_desc)));
		list.add(new MoreItem(getString(R.string.msg),R.drawable.message,getString(R.string.msg_desc)));
		list.add(new MoreItem(getString(R.string.invite),R.drawable.invite,getString(R.string.invite_desc)));
		// list.add(new MoreItem(getString(R.string.travel),R.drawable.topup12,getString(R.string.travel_desc)));
		list.add(new MoreItem(getString(R.string.radio),R.drawable.radio,getString(R.string.radio_desc)));

		list.add(new MoreItem(getString(R.string.prefs),R.drawable.ic_menu_preferences,prefs_desc));
		//    list.add(new MoreItem(getString(R.string.profile2),R.drawable.profile,getString(R.string.profile2_desc)));
		list.add(new MoreItem(getString(R.string.term),R.drawable.term,getString(R.string.term_desc)));




		list.add(new MoreItem(getString(R.string.exit),R.drawable.exit,getString(R.string.exit_desc)));
		//  list.add(new MoreItem(getString(R.string.accounts),R.drawable.profile,getString(R.string.accounts_desc)));
		return list;
	}
	@Override
	public void onVisibilityChanged(boolean visible) {}
	private ListView.OnItemClickListener onListClick = new ListView.OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> mAdapterView, View v,
								int position, long id) {
			try {


				ConnectivityManager connMgr = (ConnectivityManager)getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
				NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
				switch(position){

					case SETTINGS:
						startActivityForResult(new Intent(SipManager.ACTION_UI_PREFS_GLOBAL), CHANGE_PREFS);
						break;


					case TERM:
						//TODO: CDR
						openAboutDialog8();

						break;


					case TOPUP:

						ConnectivityManager connMgr11111 = (ConnectivityManager)getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);

						NetworkInfo networkInfo11111 = connMgr11111.getActiveNetworkInfo();
						if(networkInfo11111 != null && networkInfo11111.isConnected())
						//|| jsonvalue.contains("you are authorized person")
						{
							SipProfile account;
							long accountId = 1;
							account = SipProfile.getProfileFromDbId(getActivity(), accountId,
									DBProvider.ACCOUNT_FULL_PROJECTION);
							String user1, pass1;

							user1 = account.getSipUserName();
							pass1 = account.getPassword();
							Uri uri = Uri.parse("https://billing.yepingo.co.uk/customer/billing_mobile_app.php?pr_login="+user1+"&pr_password="+pass1); // missing 'http://' will cause crashed
							Intent intent = new Intent(Intent.ACTION_VIEW, uri);
							startActivity(intent);
						}
						else
						{
							Toast.makeText(getActivity(), "Please check your internet connection", Toast.LENGTH_LONG).show();
						}



						// openAboutDialog56();

						break;
					case WORLDPAY:

						ConnectivityManager connMgr111111 = (ConnectivityManager)getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);

						NetworkInfo networkInfo111111 = connMgr111111.getActiveNetworkInfo();
						if(networkInfo111111 != null && networkInfo111111.isConnected())
						//|| jsonvalue.contains("you are authorized person")
						{
							SipProfile account;
							long accountId = 1;
							account = SipProfile.getProfileFromDbId(getActivity(), accountId,
									DBProvider.ACCOUNT_FULL_PROJECTION);
							String user1, pass1;

							user1 = account.getSipUserName();
							pass1 = account.getPassword();
							Uri uri = Uri.parse("https://billing.yepingo.co.uk/customer/worldpay_amount_app.php?pr_login="+user1+"&pr_password="+pass1); // missing 'http://' will cause crashed
							Intent intent = new Intent(Intent.ACTION_VIEW, uri);
							startActivity(intent);
						}
						else
						{
							Toast.makeText(getActivity(), "Please check your internet connection", Toast.LENGTH_LONG).show();
						}



						// openAboutDialog56();

						break;
				/*case RECORDING:
					startActivity(new Intent(getActivity(),RecordingList.class));
					break;*/
				/*case ABOUT:
					//TODO: CDR
					openAboutDialog();

					break;*/

			/*	case Test:

					spinner();
					System.out.println("zzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzz");


					break;
					*/
			/*	case LOCAL:

					localAccess();


					break;*/
				/*case EXIT1:
					//TODO: CDR
					openAboutDialog1();

					break;*/
				/*
				case TRAVEL:


					 if(networkInfo != null && networkInfo.isConnected())
	                    {
						 travel();
	                    }
	                    else
	                    {
	                    	Toast.makeText(getActivity(), "Please make sure you have Network Enabled", Toast.LENGTH_LONG).show();
	                    }

					break;*/

					case RADIO:


						if(networkInfo != null && networkInfo.isConnected())
						{
							radio();
						}
						else
						{
							Toast.makeText(getActivity(), "Please make sure you have Network Enabled", Toast.LENGTH_LONG).show();
						}

						break;

					case PROFILE:


						if(networkInfo != null && networkInfo.isConnected())
						{
							profile();
						}
						else
						{
							Toast.makeText(getActivity(), "Please make sure you have Network Enabled", Toast.LENGTH_LONG).show();
						}
						break;

			/*	case TERM:


                    if(networkInfo != null && networkInfo.isConnected())
                    {
                    	openAboutDialog3();
                    }
                    else
                    {
                    	Toast.makeText(getActivity(), "Please make sure you have Network Enabled", Toast.LENGTH_LONG).show();
                    }

				break;*/
					case MSG:

						message();
						// count=1;
						// InviteFriend.job.setText("hello how are you man");
						System.out.println("message plz check ");

						break;

					case INVITE:

						invite();
						count=1;
						// InviteFriend.job.setText("hello how are you man");
						System.out.println("message plz check ");

						break;

/*	case PROFILE2:


                    if(networkInfo != null && networkInfo.isConnected())
                    {
                    profile();
                    }
                    else
                    {
                    	Toast.makeText(getActivity(), "Please make sure you have Network Enabled", Toast.LENGTH_LONG).show();
                    }
				break;
						*/

					case EXIT:

						//TODO: Rates
						Log.e("exit", "pressed");

						new AlertDialog.Builder(getActivity())
								.setTitle(R.string.warning)
								.setMessage(
										"Do You Want to Exit ?")
								.setNeutralButton("OK", new DialogInterface.OnClickListener() {

									public void onClick(DialogInterface dialog, int which)
									{
										prefProviderWrapper.setPreferenceBooleanValue(PreferencesWrapper.HAS_BEEN_QUIT, true);
										disconnect(true);

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



						break;

				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}



		private void openAboutDialog8() {
			// TODO Auto-generated method stub
			startActivity(new Intent(getActivity(),TermCondition.class));
		}



		void message(){
			startActivity(new Intent(getActivity(),MobileMessage.class));


		}
		void invite(){
			startActivity(new Intent(getActivity(),InviteFriend.class));


		}
		void radio(){
			startActivity(new Intent(getActivity(),Support.class));


		}
		void profile(){
			startActivity(new Intent(getActivity(),AccountUpdate.class));
		}
		/*void travel(){
			startActivity(new Intent(getActivity(),Travel.class));
		}*/

		/*void profile2(){
			startActivity(new Intent(getActivity(),MainActivity.class));
		}*/

		void openAboutDialog(){
			startActivity(new Intent(getActivity(),AboutDialog.class));
		}
		/*void openAboutDialog3(){
			startActivity(new Intent(getActivity(),TermCondition.class));
		}*/
		/*void spinner(){
			startActivity(new Intent(getActivity(),MainActivity.class));
		}*/

		void openAboutDialog1(){
			//startActivity(new Intent(getActivity(),MainActivity.class));
		}

		/*void localAccess(){
			startActivity(new Intent(getActivity(),LocalAccess.class));
		}
		*/

		private void addContactDailog() {
			// TODO Auto-generated method stub
			startActivity(new Intent(getActivity(),AddContactDialog.class));
		}
	};
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(requestCode == CHANGE_PREFS) {
			getActivity().sendBroadcast(new Intent(SipManager.ACTION_SIP_REQUEST_RESTART));
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
	private void disconnect(boolean quit) {
		Intent intent = new Intent(SipManager.ACTION_OUTGOING_UNREGISTER);
		intent.putExtra(SipManager.EXTRA_OUTGOING_ACTIVITY, new ComponentName(getActivity(), More.class));
		getActivity().sendBroadcast(intent);
		if(quit) {
			getActivity().finish();
		}
	}

}
