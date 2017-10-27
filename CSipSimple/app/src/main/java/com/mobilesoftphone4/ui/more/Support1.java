package com.mobilesoftphone4.ui.more;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.mobilesoftphone4.R;
import com.mobilesoftphone4.api.SipProfile;
import com.mobilesoftphone4.db.DBProvider;
import com.mobilesoftphone4.ui.account.AccountWizard;

@SuppressLint("NewApi") public class Support1 extends Activity {

	//private Button button;
	private WebView webView;
	SipProfile account;
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.webview);
		long accountId = 1;
		account = SipProfile.getProfileFromDbId(this, accountId,
				DBProvider.ACCOUNT_FULL_PROJECTION);
		String user1, pass1;
		user1 = AccountWizard.userName1;
			pass1 = AccountWizard.password1;
		//Get webview 
		webView = (WebView) findViewById(R.id.webView1);
		//https://yepingo.co.uk/terms-conditions.html
		startWebView("https://www.paypoint.com/en-gb/consumers/store-locator");
		
	}
	
	private void startWebView(String url) {
	    
		//Create new webview Client to show progress dialog
		//When opening a url or click on link
		
		webView.setWebViewClient(new WebViewClient() {      
	        ProgressDialog progressDialog;
	     
	        //If you will not use this method url links are opeen in new brower not in webview
	        public boolean shouldOverrideUrlLoading(WebView view, String url) {              
	            view.loadUrl(url);
	            return true;
	        }
	   
	        //Show loader on url load
	        public void onLoadResource (WebView view, String url) {
	            if (progressDialog == null) {
	                // in standard case YourActivity.this
	                progressDialog = new ProgressDialog(Support1.this);
	                progressDialog.setMessage("Caricamento in corso...");
	                progressDialog.setCancelable(true);
	                //progressDialog.show();
	            }
	            
	        }
	        public void onPageFinished(WebView view, String url) 
	        {
	           
	            if (progressDialog.isShowing()) 
	            {
	                progressDialog.dismiss();
	                //progressDialog = null;
	            }
	            
	        }
	        
	    }); 
	     
	     // Javascript inabled on webview  
	    webView.getSettings().setJavaScriptEnabled(true); 
	    
	    // Other webview options
	    /*
	    webView.getSettings().setLoadWithOverviewMode(true);
	    webView.getSettings().setUseWideViewPort(true);
	    webView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
	    webView.setScrollbarFadingEnabled(false);
	    webView.getSettings().setBuiltInZoomControls(true);
	    */
	    
	    /*
	     String summary = "<html><body>You scored <b>192</b> points.</body></html>";
         webview.loadData(summary, "text/html", null); 
	     */
	    
	    //Load url in webview
	    webView.loadUrl(url);
	     
	     
	}
	
	// Open previous opened link from history on webview when back button pressed
	
	
	@Override
    public void onBackPressed() {
            super.onBackPressed();
            this.finish();
    }
	
	

	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	};
}
