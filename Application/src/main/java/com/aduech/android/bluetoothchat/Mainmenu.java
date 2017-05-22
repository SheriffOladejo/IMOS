package com.aduech.android.bluetoothchat;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Html;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Mainmenu extends Activity {
    BluetoothAdapter mBluetoothAdapter = null;
    private static final int REQUEST_ENABLE_BT = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainmenu);

        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        // If the adapter is null, then Bluetooth is not supported
        if (mBluetoothAdapter == null) {
            Toast.makeText(getApplication(), "Bluetooth is not supported in your device", Toast.LENGTH_LONG).show();




        }

        ActionBar actionBar;

        actionBar = getActionBar();
        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#d6e0f5"));
        actionBar.setBackgroundDrawable(colorDrawable);
        actionBar.setTitle(Html.fromHtml("<font color='#000000'>Bluetooth Chat</font>"));
        /*int titleId = getResources().getIdentifier("action_bar_title", "id", "android");
        TextView abTitle = (TextView) findViewById(titleId);
        abTitle.setTextColor(Color.WHITE);*/


        TextView t1 = (TextView) findViewById(R.id.i1);
        TextView t2 = (TextView) findViewById(R.id.i2);
        TextView t3 = (TextView) findViewById(R.id.i3);
        TextView t4 = (TextView) findViewById(R.id.i4);
        TextView t5 = (TextView) findViewById(R.id.i5);

    }

    @Override
    public void onStart() {
        super.onStart();
        // If BT is not on, request that it be enabled.
        // setupChat() will then be called during onActivityResult
        if (!mBluetoothAdapter.isEnabled()) {
            Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
            // Otherwise, setup the chat session
        }
    }

    public void onClick(View v) {
        if (v.getId() == R.id.i1) {
            Intent intent = new Intent(Mainmenu.this,
                    MainActivity.class);
            startActivity(intent);

        } else if (v.getId() == R.id.i2) {
            if (mBluetoothAdapter.getScanMode() !=
                    BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE) {
                Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
                discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
                startActivity(discoverableIntent);
            }

        } else if (v.getId() == R.id.i3) {
            Intent intent = new Intent(Mainmenu.this,
                    Help.class);
            startActivity(intent);
        }
        else if (v.getId() == R.id.i4) {

            String current_name = mBluetoothAdapter.getName();

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Change Device Name");

            //Current Name
            final  TextView textView = new TextView(this);
            textView.setText("Current Name : "+current_name);
            builder.setView(textView);

            // Set up the input
            final EditText input = new EditText(this);
            // Specify the type of input expected; this, for aduech, sets the input as a password, and will mask the text
            input.setInputType(InputType.TYPE_CLASS_TEXT );
            builder.setView(input);

            // Set up the buttons
            builder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String m_Text = "";
                    m_Text = input.getText().toString();
                    mBluetoothAdapter.setName(m_Text);
                    Toast.makeText(getApplication(), "Device Name Updated", Toast.LENGTH_LONG).show();

                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });

            builder.show();

        }else if (v.getId() == R.id.i5) {
            Intent intent = new Intent(Mainmenu.this,
                    About.class);
            startActivity(intent);
        }

    }



    @Override
    public  void onBackPressed(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Do you want to Exit? ");
        builder.setCancelable(true);

        // Set up the buttons
        builder.setPositiveButton("Exit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
               finish();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }


}
