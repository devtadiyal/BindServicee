package com.mobilesoftphone4.ui.more;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.mobilesoftphone4.R;

public class AboutDialog extends Activity{
public void onCreate(Bundle savedInstanceState){
	super.onCreate(savedInstanceState);
	setContentView(R.layout.about_dialog);
}
public void openWeb(View v){
	startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("http://www.adoresoftphone.com")));
}
}
