package com.mobilesoftphone4.ui.contacts;

import java.util.ArrayList;

import android.app.Activity;
import android.content.ContentProviderOperation;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.mobilesoftphone4.R;
import com.mobilesoftphone4.api.SipUri;

public class AddContactDialog extends Activity implements TextWatcher {

	Context c;
	EditText name, mobile, home, work, mail, companey, job;
	Button save, cancel;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_contact_dialog);
		name = (EditText) findViewById(R.id.cw_name);
		mobile = (EditText) findViewById(R.id.cw_mob);
		home = (EditText) findViewById(R.id.cw_home);
		work = (EditText) findViewById(R.id.cw_work);
		mail = (EditText) findViewById(R.id.cw_email);
		companey = (EditText) findViewById(R.id.cw_companey);
		job = (EditText) findViewById(R.id.cw_job);
		save = (Button) findViewById(R.id.save_bt);
		name.addTextChangedListener(this);
		mobile.addTextChangedListener(this);
		Intent intent = getIntent();
		String initNumber = intent.getStringExtra("number");
		initNumber=SipUri.getDisplayedSimpleContact(initNumber);
		if (initNumber != null && initNumber.length() != 0)
			mobile.setText(initNumber);
	}

	public void addContact(View v) {
		String DisplayName = name.getText().toString();
		String MobileNumber = mobile.getText().toString();
		String HomeNumber = home.getText().toString();
		String WorkNumber = work.getText().toString();
		String emailID = mail.getText().toString();
		String company = companey.getText().toString();
		String jobTitle = job.getText().toString();

		ArrayList<ContentProviderOperation> ops = new ArrayList<ContentProviderOperation>();

		ops.add(ContentProviderOperation
				.newInsert(ContactsContract.RawContacts.CONTENT_URI)
				.withValue(ContactsContract.RawContacts.ACCOUNT_TYPE, null)
				.withValue(ContactsContract.RawContacts.ACCOUNT_NAME, null)
				.build());

		// ------------------------------------------------------ Names
		if (!DisplayName.equals("")) {
			ops.add(ContentProviderOperation
					.newInsert(ContactsContract.Data.CONTENT_URI)
					.withValueBackReference(
							ContactsContract.Data.RAW_CONTACT_ID, 0)
					.withValue(
							ContactsContract.Data.MIMETYPE,
							ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE)
					.withValue(
							ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME,
							DisplayName).build());
		}

		// ------------------------------------------------------ Mobile Number
		if (!MobileNumber.equals("")) {
			ops.add(ContentProviderOperation
					.newInsert(ContactsContract.Data.CONTENT_URI)
					.withValueBackReference(
							ContactsContract.Data.RAW_CONTACT_ID, 0)
					.withValue(
							ContactsContract.Data.MIMETYPE,
							ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
					.withValue(ContactsContract.CommonDataKinds.Phone.NUMBER,
							MobileNumber)
					.withValue(ContactsContract.CommonDataKinds.Phone.TYPE,
							ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE)
					.build());
		}

		// ------------------------------------------------------ Home Numbers
		if (!HomeNumber.equals("")) {
			ops.add(ContentProviderOperation
					.newInsert(ContactsContract.Data.CONTENT_URI)
					.withValueBackReference(
							ContactsContract.Data.RAW_CONTACT_ID, 0)
					.withValue(
							ContactsContract.Data.MIMETYPE,
							ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
					.withValue(ContactsContract.CommonDataKinds.Phone.NUMBER,
							HomeNumber)
					.withValue(ContactsContract.CommonDataKinds.Phone.TYPE,
							ContactsContract.CommonDataKinds.Phone.TYPE_HOME)
					.build());
		}

		// ------------------------------------------------------ Work Numbers
		if (!WorkNumber.equals("")) {
			ops.add(ContentProviderOperation
					.newInsert(ContactsContract.Data.CONTENT_URI)
					.withValueBackReference(
							ContactsContract.Data.RAW_CONTACT_ID, 0)
					.withValue(
							ContactsContract.Data.MIMETYPE,
							ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
					.withValue(ContactsContract.CommonDataKinds.Phone.NUMBER,
							WorkNumber)
					.withValue(ContactsContract.CommonDataKinds.Phone.TYPE,
							ContactsContract.CommonDataKinds.Phone.TYPE_WORK)
					.build());
		}

		// ------------------------------------------------------ Email
		if (!emailID.equals("")) {
			ops.add(ContentProviderOperation
					.newInsert(ContactsContract.Data.CONTENT_URI)
					.withValueBackReference(
							ContactsContract.Data.RAW_CONTACT_ID, 0)
					.withValue(
							ContactsContract.Data.MIMETYPE,
							ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE)
					.withValue(ContactsContract.CommonDataKinds.Email.DATA,
							emailID)
					.withValue(ContactsContract.CommonDataKinds.Email.TYPE,
							ContactsContract.CommonDataKinds.Email.TYPE_WORK)
					.build());
		}

		// ------------------------------------------------------ Organization
		if (!company.equals("") && !jobTitle.equals("")) {
			ops.add(ContentProviderOperation
					.newInsert(ContactsContract.Data.CONTENT_URI)
					.withValueBackReference(
							ContactsContract.Data.RAW_CONTACT_ID, 0)
					.withValue(
							ContactsContract.Data.MIMETYPE,
							ContactsContract.CommonDataKinds.Organization.CONTENT_ITEM_TYPE)
					.withValue(
							ContactsContract.CommonDataKinds.Organization.COMPANY,
							company)
					.withValue(
							ContactsContract.CommonDataKinds.Organization.TYPE,
							ContactsContract.CommonDataKinds.Organization.TYPE_WORK)
					.withValue(
							ContactsContract.CommonDataKinds.Organization.TITLE,
							jobTitle)
					.withValue(
							ContactsContract.CommonDataKinds.Organization.TYPE,
							ContactsContract.CommonDataKinds.Organization.TYPE_WORK)
					.build());
		}
		Intent returnIntent = new Intent();
		setResult(RESULT_OK, returnIntent);
		finish();
		// Asking the Contact provider to create a new contact
		try {
			getContentResolver().applyBatch(ContactsContract.AUTHORITY, ops);
		} catch (Exception e) {
			e.printStackTrace();
			Toast.makeText(this, "Exception: " + e.getMessage(),
					Toast.LENGTH_SHORT).show();
			finish();
		}
	}

	/*void sendLocalBroadcast() {
		Intent intent = new Intent(ContactManager.ACTION_CONTACTS);
		LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
	}*/

	public void cancel(View v) {
		String text = "";
		name.setText(text);
		mobile.setText(text);
		home.setText(text);
		work.setText(text);
		job.setText(text);
		companey.setText(text);
		mail.setText(text);
		finish();

	}

	@Override
	public void afterTextChanged(Editable arg0) {
		// TODO Auto-generated method stub
		if (name.getText().length() > 0 && mobile.getText().length() > 0) {
			save.setEnabled(true);
		} else {
			save.setEnabled(false);
		}

	}

	@Override
	public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
			int arg3) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub

	}
}