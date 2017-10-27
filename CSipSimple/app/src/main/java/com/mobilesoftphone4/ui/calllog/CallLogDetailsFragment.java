


package com.mobilesoftphone4.ui.calllog;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CallLog;
import android.provider.CallLog.Calls;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragment;
import com.mobilesoftphone4.R;
import com.mobilesoftphone4.api.SipManager;
import com.mobilesoftphone4.api.SipProfile;
import com.mobilesoftphone4.api.SipUri;
import com.mobilesoftphone4.models.CallerInfo;
import com.mobilesoftphone4.ui.contacts.AddContactDialog;
import com.mobilesoftphone4.utils.Log;
import com.mobilesoftphone4.widgets.AccountChooserButton;

public class CallLogDetailsFragment extends SherlockFragment {

    private static final String THIS_FILE = "CallLogDetailsFragment";
    /** A long array extra containing ids of call log entries to display. */
    public static final String EXTRA_CALL_LOG_IDS = "EXTRA_CALL_LOG_IDS";

    private PhoneCallDetailsHelper mPhoneCallDetailsHelper;
    private TextView mHeaderTextView;
    private View mHeaderOverlayView;
    private AccountChooserButton mAccountChooserButton;

    Intent mainActionIntent;
    int mainActionIcon;
    String mainActionDescription;

    /* package */Resources mResources;
    private LayoutInflater mInflater;

    public interface OnQuitListener {
        public void onQuit();

        public void onShowCallLog(long[] callIds);
    }

    private OnQuitListener quitListener;

    public void setOnQuitListener(OnQuitListener l) {
        quitListener = l;
    }

    private static final String[] CALL_LOG_PROJECTION = new String[] {
            CallLog.Calls.DATE,
            CallLog.Calls.DURATION,
            CallLog.Calls.NUMBER,
            CallLog.Calls.TYPE,
            SipManager.CALLLOG_PROFILE_ID_FIELD,
            SipManager.CALLLOG_STATUS_CODE_FIELD,
            SipManager.CALLLOG_STATUS_TEXT_FIELD
    };

    private static final int DATE_COLUMN_INDEX = 0;
    private static final int DURATION_COLUMN_INDEX = 1;
    private static final int NUMBER_COLUMN_INDEX = 2;
    private static final int CALL_TYPE_COLUMN_INDEX = 3;
    private static final int PROFILE_ID_COLUMN_INDEX = 4;
    private static final int STATUS_CODE_COLUMN_INDEX = 5;
    private static final int STATUS_TEXT_COLUMN_INDEX = 6;


    /**
     * Action when the call icon is pressed
     */
    private final View.OnClickListener mPrimaryActionListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String nbr = (String) view.getTag();
            if (!TextUtils.isEmpty(nbr)) {
                SipProfile acc = mAccountChooserButton.getSelectedAccount();
                Intent it = new Intent(Intent.ACTION_CALL);
                it.setData(SipUri.forgeSipUri(SipManager.PROTOCOL_CSIP, nbr));
                it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                it.putExtra(SipProfile.FIELD_ACC_ID, acc.id);
                startActivity(it);
            }
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.call_detail, container, false);
        mResources = getResources();
        mInflater = inflater;

        mPhoneCallDetailsHelper = new PhoneCallDetailsHelper(mResources);
        mHeaderTextView = (TextView) v.findViewById(R.id.header_text);
        mHeaderOverlayView = v.findViewById(R.id.photo_text_bar);
        mAccountChooserButton = (AccountChooserButton) v.findViewById(R.id.call_choose_account);
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateData(getCallLogEntryUris());
    }

    /**
     * Returns the list of URIs to show.
     * <p>
     * There are two ways the URIs can be provided to the activity: as the data
     * on the intent, or as a list of ids in the call log added as an extra on
     * the URI.
     * <p>
     * If both are available, the data on the intent takes precedence.
     */
    private Uri[] getCallLogEntryUris() {
        long[] ids = getArguments().getLongArray(EXTRA_CALL_LOG_IDS);
        Uri[] uris = new Uri[ids.length];
        for (int index = 0; index < ids.length; ++index) {
            uris[index] = ContentUris.withAppendedId(SipManager.CALLLOG_ID_URI_BASE, ids[index]);
        }
        return uris;
    }

    /**
     * Update user interface with details of given call.
     *
     * @param callUris URIs into {@link CallLog.Calls} of the calls to be
     *            displayed
     */
    private void updateData(final Uri... callUris) {

        final int numCalls = callUris.length;
        if(numCalls == 0) {
            Log.w(THIS_FILE, "No calls logs as parameters");
            return;
        }

        PhoneCallDetails[] details = new PhoneCallDetails[numCalls];
        for (int index = 0; index < numCalls; ++index) {
            details[index] = getPhoneCallDetailsForUri(callUris[index]);
        }

        // We know that all calls are from the same number and the same contact,
        // so pick the
        // first.
        PhoneCallDetails firstDetails = details[0];
        final Uri contactUri = firstDetails.contactUri;
        final Uri photoUri = firstDetails.photoUri;

        // Set the details header, based on the first phone call.
        mPhoneCallDetailsHelper.setCallDetailsHeader(mHeaderTextView, firstDetails);

        // Cache the details about the phone number.
        // final Uri numberCallUri = mPhoneNumberHelper.getCallUri(mNumber);
        // final boolean canPlaceCallsTo =
        // mPhoneNumberHelper.canPlaceCallsTo(mNumber);
        // final boolean isVoicemailNumber =
        // mPhoneNumberHelper.isVoicemailNumber(mNumber);
        // final boolean isSipNumber = mPhoneNumberHelper.isSipNumber(mNumber);

        // Let user view contact details if they exist, otherwise add option to
        // create new
        // contact from this number.


        final CharSequence nameOrNumber;
        if (!TextUtils.isEmpty(firstDetails.name)) {
            nameOrNumber = firstDetails.name;
        } else {
            nameOrNumber = firstDetails.number;
        }

        if (contactUri != null) {
            mainActionIntent = new Intent(Intent.ACTION_VIEW, contactUri);
            mainActionIcon = R.drawable.ic_contacts_holo_dark;
            mainActionDescription = nameOrNumber.toString();
        } else if(!TextUtils.isEmpty(firstDetails.number)){
            if(TextUtils.isEmpty(firstDetails.name)) {
                mHeaderTextView.setText(R.string.menu_add_to_contacts);
                (getActivity().findViewById(R.id.photo_text_bar)).setBackgroundResource(R.drawable.bg_add_contact);
                mainActionIntent =new Intent(getActivity(),AddContactDialog.class);
                mainActionIntent.putExtra("number",firstDetails.number);
                mainActionDescription = getString(R.string.menu_add_to_contacts);
            }else {
                (getActivity().findViewById(R.id.photo_text_bar)).setBackgroundResource(R.drawable.bg_cal_log_name);
                mHeaderTextView.setText(getString(R.string.menu_add_address_to_contacts, firstDetails.name));

            }
        }else {
            // If we cannot call the number, when we probably cannot add it as a
            // contact either.
            // This is usually the case of private, unknown, or payphone
            // numbers.
            mainActionIntent = null;
            mainActionIcon = 0;
            mainActionDescription = null;
        }

        if (mainActionIntent == null) {
            mHeaderTextView.setVisibility(View.INVISIBLE);
            mHeaderOverlayView.setVisibility(View.INVISIBLE);
        } else {

            mHeaderTextView.setVisibility(View.VISIBLE);
            mHeaderOverlayView.setVisibility(View.VISIBLE);
            mHeaderTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(mainActionIntent);
                }
            });
        }

        // This action allows to call the number that places the call.
        // final CharSequence displayNumber = firstDetails.formattedNumber;
        CharSequence displayNumber;
        if (!TextUtils.isEmpty(firstDetails.number)) {
            displayNumber =SipUri.getCanonicalSipContact(firstDetails.number.toString(), false).split("@")[0];
        } else {
            displayNumber = mResources.getString(R.string.unknown);
        }

        String callText = getString(R.string.description_call, displayNumber);
        configureCallButton(callText, firstDetails.number, firstDetails.number);

        ListView historyList = (ListView) getView().findViewById(R.id.history);
        historyList.setAdapter(new CallDetailHistoryAdapter(getActivity(), mInflater, details));

        mAccountChooserButton.setTargetAccount(firstDetails.accountId);
    }

    /** Return the phone call details for a given call log URI. */
    private PhoneCallDetails getPhoneCallDetailsForUri(Uri callUri) {
        ContentResolver resolver = getActivity().getContentResolver();
        Cursor callCursor = resolver.query(callUri, CALL_LOG_PROJECTION, null, null, null);
        try {
            if (callCursor == null || !callCursor.moveToFirst()) {
                throw new IllegalArgumentException("Cannot find content: " + callUri);
            }

            // Read call log specifics.
            String number = callCursor.getString(NUMBER_COLUMN_INDEX);
            long date = callCursor.getLong(DATE_COLUMN_INDEX);
            long duration = callCursor.getLong(DURATION_COLUMN_INDEX);
            int callType = callCursor.getInt(CALL_TYPE_COLUMN_INDEX);
            Long accountId = callCursor.getLong(PROFILE_ID_COLUMN_INDEX);
            int statusCode = callCursor.getInt(STATUS_CODE_COLUMN_INDEX);
            String statusText = callCursor.getString(STATUS_TEXT_COLUMN_INDEX);

            // Formatted phone number.
            final CharSequence formattedNumber;
            // Read contact specifics.
            final CharSequence nameText;
            final int numberType;
            final CharSequence numberLabel;
            final Uri photoUri;
            final Uri lookupUri;
            // If this is not a regular number, there is no point in looking it
            // up in the contacts.
            CallerInfo info = CallerInfo.getCallerInfoFromSipUri(getActivity(), number);
            if (info == null) {
                formattedNumber = number;
                nameText = "";
                numberType = 0;
                numberLabel = "";
                photoUri = null;
                lookupUri = null;
            } else {
                formattedNumber = info.phoneNumber;
                nameText = info.name;
                numberType = info.numberType;
                numberLabel = info.phoneLabel;
                photoUri = info.photoUri;
                lookupUri = info.contactContentUri;
            }
            return new PhoneCallDetails(number, formattedNumber,
                    new int[] {
                            callType
                    }, date, duration,
                    accountId, statusCode, statusText,
                    nameText, numberType, numberLabel, lookupUri, photoUri);
        } finally {
            if (callCursor != null) {
                callCursor.close();
            }
        }
    }




    /** Configures the call button area using the given entry. */
    private void configureCallButton(String callText, CharSequence nbrLabel, CharSequence number) {
        View convertView = getView().findViewById(R.id.call_and_sms);
        convertView.setVisibility(TextUtils.isEmpty(number) ? View.GONE : View.VISIBLE);

        TextView text = (TextView) convertView.findViewById(R.id.call_and_sms_text);

        View mainAction = convertView.findViewById(R.id.call_and_sms_main_action);
        mainAction.setOnClickListener(mPrimaryActionListener);
        mainAction.setContentDescription(callText);
        if(TextUtils.isEmpty(number)) {
            number = "";
        }
        mainAction.setTag(SipUri.getCanonicalSipContact(number.toString(), false));
        text.setText(callText);

    }

    public void onMenuRemoveFromCallLog(MenuItem menuItem) {
        final StringBuilder callIds = new StringBuilder();
        for (Uri callUri : getCallLogEntryUris()) {
            if (callIds.length() != 0) {
                callIds.append(",");
            }
            callIds.append(ContentUris.parseId(callUri));
        }

        getActivity().getContentResolver().delete(SipManager.CALLLOG_URI,
                Calls._ID + " IN (" + callIds + ")", null);
        if (quitListener != null) {
            quitListener.onQuit();
        }
    }

}
