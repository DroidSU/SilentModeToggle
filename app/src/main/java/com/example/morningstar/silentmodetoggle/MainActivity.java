package com.example.morningstar.silentmodetoggle;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.media.AudioManager;
import android.view.ContextMenu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import static android.os.Build.VERSION.SDK;
import static android.os.Build.VERSION.SDK_INT;

public class MainActivity extends AppCompatActivity {

    private  AudioManager audio;
    private boolean phoneSilent;
    int bV;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //bV = Build.VERSION>SDK_INT;
        audio = (AudioManager)getSystemService(AUDIO_SERVICE);
        /*try {
            if (bV >= 23) {
                Intent intent = new Intent(android.provider.Settings.ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS);
                startActivity(intent);
            }
        }
        catch (SecurityException e) {
        }*/
        isMyPhoneSilent();
        setButtonOnClickListener();
    }

    private void setButtonOnClickListener()
    {
        Button btnToggle = (Button) findViewById(R.id.btnToggle);
        btnToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(phoneSilent){
                    //change back to normal mode;
                    audio.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
                    phoneSilent = false;
                }
                else
                {
                    //change to silent mode
                    audio.setRingerMode(AudioManager.RINGER_MODE_SILENT);
                    phoneSilent = true;
                }
                //toggle the UI
                toggleUI();
            }
        });
    }

    //the following snippet tells us if the current state of the phone
    private void isMyPhoneSilent()
    {
        int ringerMode = audio.getRingerMode();
        if(ringerMode == AudioManager.RINGER_MODE_SILENT)
            phoneSilent = true;
        else
            phoneSilent = false;
    }

    private void toggleUI()
    {
        ImageView imgView = (ImageView) findViewById(R.id.imgRingerOn);
        Drawable newPhoneImg;
        if(phoneSilent)
            newPhoneImg = getResources().getDrawable(R.mipmap.ringeroff);
        else
            newPhoneImg = getResources().getDrawable(R.mipmap.ringeron);

        imgView.setImageDrawable(newPhoneImg);
    }

    @Override
    protected void onResume() {
        super.onResume();
        isMyPhoneSilent();
        toggleUI();
    }

}
