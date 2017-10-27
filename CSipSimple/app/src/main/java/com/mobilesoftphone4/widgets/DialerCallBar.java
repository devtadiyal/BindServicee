package com.mobilesoftphone4.widgets;

import android.app.Dialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.mobilesoftphone4.R;

public class DialerCallBar extends LinearLayout implements OnClickListener, OnLongClickListener {

    public interface OnDialActionListener {
        /**
         * The make call button has been pressed
         */
        void placeCall();

        /**
         * The video button has been pressed
         */
        void placeVideoCall();
        /**
         * The delete button has been pressed
         */
        void deleteChar();
        /**
         * The delete button has been long pressed
         */
        void deleteAll();
        /**
         * The account button has been pressed
         */
        void editAccount();

        void phonebook();

        void gsmCall();
    }

    private OnDialActionListener actionListener;

    public DialerCallBar(Context context) {
        this(context, null, 0);
    }

    public DialerCallBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DialerCallBar(Context context, AttributeSet attrs, int style) {
        super(context, attrs);
        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.dialpad_additional_buttons, this, true);
        findViewById(R.id.phonebook).setOnClickListener(this);
        findViewById(R.id.phonebook).setEnabled(true);
        findViewById(R.id.accountButton).setOnClickListener(this);
        findViewById(R.id.accountButton).setEnabled(true);
        findViewById(R.id.dialButton).setOnClickListener(this);
        //  findViewById(R.id.deleteButton1).setOnClickListener(this);
        //  findViewById(R.id.deleteButton1).setOnLongClickListener(this);

        if(getOrientation() == LinearLayout.VERTICAL) {
            LayoutParams lp;
            for(int i=0; i < getChildCount(); i++) {
                lp = (LayoutParams) getChildAt(i).getLayoutParams();
                int w = lp.width;
                lp.width = lp.height;
                lp.height = w;
                lp.gravity = Gravity.CENTER_HORIZONTAL;
                // Added for clarity but not necessary
                getChildAt(i).setLayoutParams(lp);

            }
        }
    }

    /**
     * Set a listener for this widget actions
     * @param l the listener called back when some user action is done on this widget
     */
    public void setOnDialActionListener(OnDialActionListener l) {
        actionListener = l;
    }

    /**
     * Set the action buttons enabled or not
     */
    public void setEnabled(boolean enabled) {
        findViewById(R.id.dialButton).setEnabled(enabled);
        //findViewById(R.id.deleteButton).setEnabled(enabled);
    }

    /**
     * Set the video capabilities
     * @param enabled whether the client is able to make video calls
     */
    public void setVideoEnabled(boolean enabled) {
        //  findViewById(R.id.dialVideoButton).setVisibility(enabled ? View.VISIBLE : View.INVISIBLE);
    }

    @Override
    public void onClick(View v) {
        if (actionListener != null) {
            int viewId = v.getId();
            if (viewId == R.id.accountButton) {
                actionListener.editAccount();
            }if (viewId == R.id.phonebook) {
                actionListener.phonebook();
            }
            else if(viewId == R.id.dialButton) {



//Toast.makeText(getContext(), "pagal hai kya bhncho", Toast.LENGTH_LONG).show();

                try{

                    //    b=	AccountWizard.status.getText().toString().contains("Registered");
                    // 	System.out.println(b+" Account status <><><><><><><><>"+AccountWizard.status.getText().toString().contains("Registered"));
                }
                catch(Exception e)
                {

                }
                //new LongOperation1().execute("");


                final Dialog dialog = new Dialog(getContext());
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.customcall);



                final Button sip=(Button)dialog.findViewById(R.id.sipcall);

                sip.setOnClickListener(new OnClickListener()
                {

                    ConnectivityManager connMgr = (ConnectivityManager)getContext().getSystemService(Context.CONNECTIVITY_SERVICE);

                    NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
                    //  AccountStatusDisplay accountStatusDisplay = AccountListUtils.getAccountDisplay(getContext(), 1);
                    @Override
                    public void onClick(View v)
                    {
                        if(networkInfo != null && networkInfo.isConnected())
                        //|| jsonvalue.contains("you are authorized person")
                        {
                            // if(b==true)
                            //{
                            actionListener.placeCall();
                            //}
                            //else
                            // {

                            //	Toast.makeText(getContext(), "Please make sure you have Registered with the proper Account for calling by Data Mode", Toast.LENGTH_LONG).show();
                            // }

                        }else
                        {
                            Toast.makeText(getContext(), "Please make sure you have Network Enabled ", Toast.LENGTH_LONG).show();
                        }
					/*if(	accountStatusDisplay.statusLabel == getString(R.string.acct_registered))
						{
						actionListener.placeCall();
						}
					else
					{
						Toast.makeText(getContext(), "wat he fuck as whole", Toast.LENGTH_LONG).show();
					}*/

                        dialog .dismiss();



                    }
                });
                Button local=(Button)dialog.findViewById(R.id.localcall);

                local.setOnClickListener(new OnClickListener()
                {

                    @Override
                    public void onClick(View v)
                    {

                        actionListener.gsmCall();

                        dialog .dismiss();

                    }
                });

                Button cancel=(Button)dialog.findViewById(R.id.cancel);

     			/*cancel.setOnClickListener(new OnClickListener()
             	{

 					@Override
 					public void onClick(View v)
 					{




 						 dialog .dismiss();



 					}
 				});*/
                dialog.show();





            }/*else if(viewId == R.id.deleteButton1) {
                actionListener.deleteChar();
            }*/
        }
    }

    @Override
    public boolean onLongClick(View v) {
        if (actionListener != null) {
            int viewId = v.getId();
            if(viewId == R.id.deleteButton1) {
                actionListener.deleteAll();
                v.setPressed(false);
                return true;
            }
        }
        return false;
    }

}
