
package com.mobilesoftphone4.ui.calllog;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.mobilesoftphone4.R;


public final class PhoneCallDetailsViews {
    public final TextView nameView;
    public final View callTypeView;
    //   public final CallTypeIconsView callTypeIcons;
    public final TextView callTypeAndDate;
    public final TextView numberView;

    private PhoneCallDetailsViews(TextView nameView, View callTypeView,
                                  TextView callTypeAndDate, TextView numberView) {
        this.nameView = nameView;
        this.callTypeView = callTypeView;
        //    this.callTypeIcons = callTypeIcons;
        this.callTypeAndDate = callTypeAndDate;
        this.numberView = numberView;
    }


    public static PhoneCallDetailsViews fromView(View view) {
        return new PhoneCallDetailsViews((TextView) view.findViewById(R.id.name),
                view.findViewById(R.id.call_type),
           /*     (CallTypeIconsView) view.findViewById(R.id.call_type_icons),*/
                (TextView) view.findViewById(R.id.call_count_and_date),
                (TextView) view.findViewById(R.id.number));
    }

    public static PhoneCallDetailsViews createForTest(Context context) {
        return new PhoneCallDetailsViews(
                new TextView(context),
                new View(context),
              /*  new CallTypeIconsView(context),*/
                new TextView(context),
                new TextView(context));
    }
}
