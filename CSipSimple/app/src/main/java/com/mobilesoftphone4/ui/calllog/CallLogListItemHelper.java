package com.mobilesoftphone4.ui.calllog;

import android.content.Context;
import android.content.res.Resources;
import android.text.TextUtils;

import com.mobilesoftphone4.R;
import com.mobilesoftphone4.utils.Theme;

/**
 * Helper class to fill in the views of a call log entry.
 */
/* package */class CallLogListItemHelper {
    /** Helper for populating the details of a phone call. */
    private final PhoneCallDetailsHelper mPhoneCallDetailsHelper;
    /** Resources to look up strings. */
    private final Resources mResources;
    private final Theme mTheme;


    public CallLogListItemHelper(PhoneCallDetailsHelper phoneCallDetailsHelper, Context ctxt) {
        mPhoneCallDetailsHelper = phoneCallDetailsHelper;
        mResources = ctxt.getResources();
        mTheme = Theme.getCurrentTheme(ctxt);
    }


    public void setPhoneCallDetails(CallLogListItemViews views, PhoneCallDetails details) {
        mPhoneCallDetailsHelper.setPhoneCallDetails(views.phoneCallDetailsViews, details);

    }

    /** Returns the description used by the call action for this phone call. */
    private CharSequence getCallActionDescription(PhoneCallDetails details) {
        CharSequence recipient;
        if (!TextUtils.isEmpty(details.name)) {
            recipient = details.name;
        } else {
            recipient = details.number;
        }
        return mResources.getString(R.string.description_call, recipient);
    }

}
