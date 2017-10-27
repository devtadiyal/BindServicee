package com.mobilesoftphone4.ui.more;
/*
SetupActivity.java
Copyright (C) 2012  Belledonne Communications, Grenoble, France

This program is free software; you can redistribute it and/or
modify it under the terms of the GNU General Public License
as published by the Free Software Foundation; either version 2
of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program; if not, write to the Free Software
Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
*/
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;

import com.mobilesoftphone4.R;
import com.mobilesoftphone4.api.SipProfile;
import com.mobilesoftphone4.db.DBProvider;
/**
 * @author Sylvain Berfini
 */
public class TermCondition extends FragmentActivity implements OnClickListener {
	private static TermCondition instance;
	private RelativeLayout back, next, cancel;
	SipProfile account;
	private boolean accountCreated = false;
	private Handler mHandler = new Handler();
	
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.signin);
		
		
		
		
		WebView webView =(WebView) findViewById(R.id.webview);;
		
		 webView.setWebViewClient(new WebViewClient());
		    
		   // webView.getSettings().setPluginState(true);
		    webView.getSettings().setBuiltInZoomControls(false); 
		    webView.getSettings().setSupportZoom(false);
		    webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);   
		    webView.getSettings().setAllowFileAccess(true); 
		  //  webView.getSettings().setDomStorageEnabled(true);
		    
		
		   //String user;
		   
		   //user = AccountWizard.getVariable();
		    long accountId = 1;
			account = SipProfile.getProfileFromDbId(this, accountId,
					DBProvider.ACCOUNT_FULL_PROJECTION);
			String user1, pass1;
			user1 = account.getSipUserName();
			pass1 = account.getPassword();
		   System.out.println("account value");
		  
		   // webView.loadUrl("http://www.google.com");
		    webView.loadUrl("https://yepingo.co.uk/terms-conditions.php");
		
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	};
	
	
}
