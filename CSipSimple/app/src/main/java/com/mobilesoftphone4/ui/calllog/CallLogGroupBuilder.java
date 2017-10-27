

package com.mobilesoftphone4.ui.calllog;


import android.database.Cursor;
import android.provider.CallLog;
import android.provider.CallLog.Calls;
import android.telephony.PhoneNumberUtils;


public class CallLogGroupBuilder {
    public interface GroupCreator {
        void addGroup(int cursorPosition, int size, boolean expanded);
    }


    /** The object on which the groups are created. */
    private final GroupCreator mGroupCreator;

    public CallLogGroupBuilder(GroupCreator groupCreator) {
        mGroupCreator = groupCreator;
    }


    public void addGroups(Cursor cursor) {
        final int count = cursor.getCount();
        if (count == 0) {
            return;
        }
        int numberColIndex = cursor.getColumnIndex(CallLog.Calls.NUMBER);
        int typeColIndex = cursor.getColumnIndex(CallLog.Calls.TYPE);

        int currentGroupSize = 1;
        cursor.moveToFirst();
        // The number of the first entry in the group.
        String firstNumber = cursor.getString(numberColIndex);
        // This is the type of the first call in the group.
        int firstCallType = cursor.getInt(typeColIndex);
        while (cursor.moveToNext()) {
            // The number of the current row in the cursor.
            final String currentNumber = cursor.getString(numberColIndex);
            final int callType = cursor.getInt(typeColIndex);
            final boolean sameNumber = equalNumbers(firstNumber, currentNumber);
            final boolean shouldGroup;

            if (!sameNumber) {
                // Should only group with calls from the same number.
                shouldGroup = false;
            } else if ( firstCallType == Calls.MISSED_TYPE) {
                // Voicemail and missed calls should only be grouped with subsequent missed calls.
                shouldGroup = callType == Calls.MISSED_TYPE;
            } else {
                // Incoming and outgoing calls group together.
                shouldGroup = callType == Calls.INCOMING_TYPE || callType == Calls.OUTGOING_TYPE;
            }

            if (shouldGroup) {
                // Increment the size of the group to include the current call, but do not create
                // the group until we find a call that does not match.
                currentGroupSize++;
            } else {
                // Create a group for the previous set of calls, excluding the current one, but do
                // not create a group for a single call.
                if (currentGroupSize > 1) {
                    addGroup(cursor.getPosition() - currentGroupSize, currentGroupSize);
                }
                // Start a new group; it will include at least the current call.
                currentGroupSize = 1;
                // The current entry is now the first in the group.
                firstNumber = currentNumber;
                firstCallType = callType;
            }
        }
        // If the last set of calls at the end of the call log was itself a group, create it now.
        if (currentGroupSize > 1) {
            addGroup(count - currentGroupSize, currentGroupSize);
        }
    }

    private void addGroup(int cursorPosition, int size) {
        mGroupCreator.addGroup(cursorPosition, size, false);
    }


    private boolean equalNumbers(String number1, String number2) {
//        if (PhoneNumberUtils.isUriNumber(number1) || PhoneNumberUtils.isUriNumber(number2)) {
//            return compareSipAddresses(number1, number2);
//        } else {
        // Optim -- first try to compare very simply
        if(number1 != null && number2 != null && number1.equals(number2)) {
            return true;
        }
        return PhoneNumberUtils.compare(number1, number2);
//        }
    }
    /*
    boolean compareSipAddresses(String number1, String number2) {
        if (number1 == null || number2 == null) return number1 == number2;

        int index1 = number1.indexOf('@');
        final String userinfo1;
        final String rest1;
        if (index1 != -1) {
            userinfo1 = number1.substring(0, index1);
            rest1 = number1.substring(index1);
        } else {
            userinfo1 = number1;
            rest1 = "";
        }

        int index2 = number2.indexOf('@');
        final String userinfo2;
        final String rest2;
        if (index2 != -1) {
            userinfo2 = number2.substring(0, index2);
            rest2 = number2.substring(index2);
        } else {
            userinfo2 = number2;
            rest2 = "";
        }

        return userinfo1.equals(userinfo2) && rest1.equalsIgnoreCase(rest2);
    }
    */
}
