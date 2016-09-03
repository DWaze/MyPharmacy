package com.srdeveloppement.atelier.mypharmacy;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.srdeveloppement.atelier.mypharmacy.Data.GetData;

public class LanguageSettings extends AppCompatActivity {
    Button ok ;
    Button x ;
    RadioButton en,fr,ar;
    RadioGroup radiolng ;
    public static String lng; SharedPreferences sharedPref;
    public String checked_language="en";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LocaleHelper.onCreate(this, GetData.appLNG);

        setContentView(R.layout.dialog_language_settings);

        x=(Button)findViewById(R.id.x);
        ok=(Button)findViewById(R.id.ok);
        en=(RadioButton)findViewById(R.id.en);en.setChecked(true);
        fr=(RadioButton)findViewById(R.id.fr);
        ar=(RadioButton)findViewById(R.id.ar);
        x.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
       sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        String language=sharedPref.getString("lng","en");
        switch (language){
            case "en":
                en.setChecked(true);
                fr.setChecked(false);
                ar.setChecked(false);
                break;
            case "fr":
                en.setChecked(false);
                fr.setChecked(true);
                ar.setChecked(false);
                break;
            case "ar":
                en.setChecked(false);
                fr.setChecked(false);
                ar.setChecked(true);
                break;
        }

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (checked_language){
                    case "en":
                        sharedPref.edit().putString("lng", "en").apply();
                        break;
                    case "fr":
                        sharedPref.edit().putString("lng", "fr").apply();
                        break;
                    case "ar":
                        sharedPref.edit().putString("lng", "ar").apply();
                        break;
                }
                LocaleHelper.setLocale(v.getContext(), lng);
                finish();

                Intent i = getBaseContext().getPackageManager()
                        .getLaunchIntentForPackage( getBaseContext().getPackageName() );
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });



        radiolng = (RadioGroup) findViewById(R.id.radiolng);

        radiolng.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // checkedId is the RadioButton selected

                switch(checkedId) {
                    case R.id.en:
                        lng="en-us";
                        checked_language="en";
                        break;
                    case R.id.fr:
                        lng="fr";
                        checked_language="fr";
                        break;
                    case R.id.ar:
                        lng="ar";
                        checked_language="ar";
                        break;
                }
            }
        });
















    }


}
