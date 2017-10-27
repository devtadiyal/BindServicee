

package com.mobilesoftphone4.ui.calllog;

import android.view.View;
import android.widget.ImageView;

import com.mobilesoftphone4.R;
import com.mobilesoftphone4.widgets.contactbadge.QuickContactBadge;


public final class CallLogListItemViews {
    /** The quick contact badge for the contact. */
    public final QuickContactBadge quickContactView;
    /** The primary action view of the entry. */
    public final View primaryActionView;
    /** The details of the phone call. */
    public final PhoneCallDetailsViews phoneCallDetailsViews;
    /** The divider to be shown below items. */
    public final View bottomDivider;

    private CallLogListItemViews(QuickContactBadge quickContactView, View primaryActionView,
                                 ImageView secondaryActionView, View dividerView,
                                 PhoneCallDetailsViews phoneCallDetailsViews,
                                 View bottomDivider) {
        this.quickContactView = quickContactView;
        this.primaryActionView = primaryActionView;
        this.phoneCallDetailsViews = phoneCallDetailsViews;
        this.bottomDivider = bottomDivider;
    }

    public static CallLogListItemViews fromView(View view) {
        return new CallLogListItemViews(
                (QuickContactBadge) view.findViewById(R.id.quick_contact_photo),
                view.findViewById(R.id.primary_action_view),
                (ImageView) view.findViewById(R.id.secondary_action_icon),
                view.findViewById(R.id.divider),
                PhoneCallDetailsViews.fromView(view),
                view.findViewById(R.id.call_log_divider));
    }
}
