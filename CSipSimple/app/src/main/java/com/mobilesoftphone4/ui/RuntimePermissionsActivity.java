package com.mobilesoftphone4.ui;
import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
//import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.mobilesoftphone4.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RuntimePermissionsActivity extends Activity {

    public static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 1;
    private static int SPLASH_TIME_OUT = 11000;
    private String TAG = "tag";
    public SharedPreferences app_preferences;
    public SharedPreferences.Editor editor;
    Boolean isFirstTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_runtime_permissions);

        app_preferences = PreferenceManager.getDefaultSharedPreferences(RuntimePermissionsActivity.this);
        editor = app_preferences.edit();
        isFirstTime = app_preferences.getBoolean("isFirstTime", true);

        if (isFirstTime) {

            if (checkForPermission()) {
                askForPermissions();
                // carry on the normal flow, as the case of  permissions  granted.
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // This method will be executed once the timer is over
                        // Start your app main activity
                        //askForPermissions();
                        editor.putBoolean("isFirstTime", false);
                        editor.commit();
                        Intent i = new Intent(RuntimePermissionsActivity.this, OTPMainActivity.class);
                        startActivity(i);

                        // close this activity
                        finish();

                    }
                }, SPLASH_TIME_OUT);
            }
        }
        else
        {
            Intent i = new Intent(RuntimePermissionsActivity.this, VerifyOTPActivity.class);
            startActivity(i);

            // close this activity
            finish();
        }
    }
    void askForPermissions(){

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if(checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.READ_CONTACTS)!= PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED){
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE  ,Manifest.permission.READ_CONTACTS, Manifest.permission.CAMERA,Manifest.permission.RECORD_AUDIO,Manifest.permission.READ_PHONE_STATE}, REQUEST_ID_MULTIPLE_PERMISSIONS);


               // requestPermissions(new String[]{Manifest.permission.READ_CONTACTS, Manifest.permission.READ_CONTACTS}, REQUEST_ID_MULTIPLE_PERMISSIONS);
               // requestPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.CAMERA}, REQUEST_ID_MULTIPLE_PERMISSIONS);
            }
//            if(checkSelfPermission(Manifest.permission.READ_CONTACTS)!= PackageManager.PERMISSION_GRANTED){
//                requestPermissions(new String[]{Manifest.permission.READ_CONTACTS, Manifest.permission.READ_CONTACTS}, REQUEST_ID_MULTIPLE_PERMISSIONS);
//            }
//            if(checkSelfPermission(Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED){
//                requestPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.CAMERA}, REQUEST_ID_MULTIPLE_PERMISSIONS);
//            }
////            if(checkSelfPermission(Manifest.permission.RECORD_AUDIO)!= PackageManager.PERMISSION_GRANTED){
//                requestPermissions(new String[]{Manifest.permission.RECORD_AUDIO, Manifest.permission.RECORD_AUDIO}, REQUEST_ID_MULTIPLE_PERMISSIONS);
//            }
//            if(checkSelfPermission(Manifest.permission.READ_PHONE_STATE)!= PackageManager.PERMISSION_GRANTED){
//                requestPermissions(new String[]{Manifest.permission.READ_PHONE_STATE, Manifest.permission.READ_PHONE_STATE}, REQUEST_ID_MULTIPLE_PERMISSIONS);
//            }
//
//            showDialogOK("CAMERA CONTACT MICROPHONE STORAGE TELEPHONE Permissions Needed",
//                    new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            switch (which) {
//                                case DialogInterface.BUTTON_POSITIVE:
//                                  //  checkAndRequestPermissions();
//                                    Intent i = new Intent(RuntimePermissionsActivity.this, SipHome.class);
//                                    startActivity(i);
//
//                                    // close this activity
//                                    finish();
//                                    break;
//                                case DialogInterface.BUTTON_NEGATIVE:
//                                    // proceed with logic by disabling the related features or quit the app.
//                                    finish();
//                                    break;
//                            }
//                        }
//                    });
        }
    }

    boolean checkForPermission(){
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
    }
//    private  boolean checkAndRequestPermissions() {
//        int camerapermission = ContextCompat.checkSelfPermission(this, "");
////        int writepermission = ContextCompat.checkSelfPermission(this, "android.permission.WRITE_EXTERNAL_STORAGE");
////        int permissionLocation = ContextCompat.checkSelfPermission(this,"android.permission.READ_CONTACTS");
////        int permissionRecordAudio = ContextCompat.checkSelfPermission(this, "android.permission.RECORD_AUDIO");
////        int permissionTelephone = ContextCompat.checkSelfPermission(this, "android.permission.READ_PHONE_STATE");
//
//
//        List<String> listPermissionsNeeded = new ArrayList<>();
//
//        if (camerapermission != PackageManager.PERMISSION_GRANTED) {
//            listPermissionsNeeded.add(Manifest.permission.CAMERA);
//        }
////        if (writepermission != PackageManager.PERMISSION_GRANTED) {
////            listPermissionsNeeded.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
////        }
////        if (permissionLocation != PackageManager.PERMISSION_GRANTED) {
////            listPermissionsNeeded.add(Manifest.permission.ACCESS_FINE_LOCATION);
////        }
////        if (permissionRecordAudio != PackageManager.PERMISSION_GRANTED) {
////            listPermissionsNeeded.add(Manifest.permission.RECORD_AUDIO);
////        }
////        if (permissionTelephone != PackageManager.PERMISSION_GRANTED) {
////            listPermissionsNeeded.add(Manifest.permission.READ_PHONE_STATE);
////        }
//        if (!listPermissionsNeeded.isEmpty()) {
//            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), REQUEST_ID_MULTIPLE_PERMISSIONS);
//            return false;
//        }
//        return true;
//    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        Log.d(TAG, "Permission callback called-------");
        switch (requestCode) {
            case REQUEST_ID_MULTIPLE_PERMISSIONS: {

                Map<String, Integer> perms = new HashMap<>();
                // Initialize the map with both permissions
                perms.put(Manifest.permission.CAMERA, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.WRITE_EXTERNAL_STORAGE, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.ACCESS_FINE_LOCATION, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.RECORD_AUDIO, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.READ_PHONE_STATE, PackageManager.PERMISSION_GRANTED);
                // Fill with actual results from user
                if (grantResults.length > 0) {
                    for (int i = 0; i < permissions.length; i++)
                        perms.put(permissions[i], grantResults[i]);
                    // Check for both permissions
                    if (perms.get(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
                            && perms.get(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                            && perms.get(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                            && perms.get(Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED
                            && perms.get(Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED){
                        Log.d(TAG, "sms & location services permission granted");
//                         process the normal flow

//                        Intent i = new Intent(RuntimePermissionsActivity.this, SipHome.class);
//                        startActivity(i);
//                        finish();
                        //else any one or both the permissions are not granted
                    } else {
                        Log.d(TAG, "Some permissions are not granted ask again ");
                        //permission is denied (this is the first time, when "never ask again" is not checked) so ask again explaining the usage of permission
//                        // shouldShowRequestPermissionRationale will return true
                        //show the dialog or snackbar saying its necessary and try again otherwise proceed with setup.
//                        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)
////                                || ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
////                                || ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)
////                                || ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.RECORD_AUDIO)
////                                || ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_PHONE_STATE)
////
//) {
//                            showDialogOK("Service Permissions are required for this app",
//                                    new DialogInterface.OnClickListener() {
//                                        @Override
//                                        public void onClick(DialogInterface dialog, int which) {
//                                            switch (which) {
//                                                case DialogInterface.BUTTON_POSITIVE:
//                                                  //  checkAndRequestPermissions();
//                                                    break;
//                                                case DialogInterface.BUTTON_NEGATIVE:
//                                                    // proceed with logic by disabling the related features or quit the app.
//                                                    finish();
//                                                    break;
//                                            }
//                                        }
//                                    });
//                        }
//                        //permission is denied (and never ask again is  checked)
//                        //shouldShowRequestPermissionRationale will return false
//                        else {
//                            explain("You need to give some mandatory permissions to continue. Do you want to go to app settings?");
//                            //                            //proceed with logic by disabling the related features or quit the app.
//                        }
                    }
                }
            }
        }

    }

    private void showDialogOK(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", okListener)
                .create()
                .show();
    }
    private void explain(String msg){
   //     final android.support.v7.app.AlertDialog.Builder dialog = new android.support.v7.app.AlertDialog.Builder(this);
//        dialog.setMessage(msg)
//                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {
//                        //  permissionsclass.requestPermission(type,code);
//                        startActivity(new Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("package:com.mobilesoftphone4")));
//                    }
//                })
//                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {
//                        finish();
//                    }
//                });
//        dialog.show();
    }
}