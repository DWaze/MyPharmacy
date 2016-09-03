package com.srdeveloppement.atelier.mypharmacy;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.rengwuxian.materialedittext.MaterialEditText;
import com.srdeveloppement.atelier.mypharmacy.Data.GetData;

import java.util.Random;

public class Dialog_restor_password extends AppCompatActivity {

    String responce;
    Mail m;
    int GENERATED_CODE=0;
    Button close,Next;
    MaterialEditText email,code,Password_et,conf_Password;
    int page=1;
    String TheEmail="",TheNewPassword="",TheConfirmPassword="";
    LinearLayout SendEmailLayout,ConfirmCodeLayout,ChangePassLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_restor_password);
        close=(Button)findViewById(R.id.close);
        Next=(Button)findViewById(R.id.Next);
        email=(MaterialEditText)findViewById(R.id.email);
        code=(MaterialEditText)findViewById(R.id.code);

        Password_et=(MaterialEditText)findViewById(R.id.Password_et);
        conf_Password=(MaterialEditText)findViewById(R.id.conf_Password);

        SendEmailLayout=(LinearLayout)findViewById(R.id.SendEmailLayout);
        ConfirmCodeLayout=(LinearLayout)findViewById(R.id.ConfirmCodeLayout);
        ChangePassLayout=(LinearLayout)findViewById(R.id.ChangePassLayout);

        Random r = new Random();
        GENERATED_CODE = r.nextInt(999999 - 100000) + 99999;

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(page==1){
                    if (isEmpty(email)) {
                        email.setError(getResources().getString(R.string.Email_is_empty));
                        Toast.makeText(v.getContext(), R.string.Email_is_empty, Toast.LENGTH_LONG).show();
                    }else{
                        TheEmail=email.getText().toString().trim();
                        String Params="&Email="+TheEmail;
                        CheckEmail pro= new CheckEmail(Params);
                        pro.execute();
                    }
            }else if(page==2){

                    if (isEmpty(code)) {
                        code.setError(getResources().getString(R.string.code_is_empty));
                        Toast.makeText(v.getContext(), R.string.code_is_empty, Toast.LENGTH_LONG).show();
                    }else{
                        if(code.getText().toString().trim().equals(""+GENERATED_CODE)){
                            ChangePassLayout.setVisibility(View.VISIBLE);
                            ConfirmCodeLayout.setVisibility(View.GONE);
                            Next.setText(getResources().getString(R.string.confirm_dialog));
                            page=3;
                        }else{
                            code.setError(getString(R.string.conf_no_match));
                        }
                    }
                }else  if(page==3){
                    TheNewPassword=Password_et.getText().toString();
                    TheConfirmPassword=conf_Password.getText().toString();

                    if(TheConfirmPassword.equals(TheNewPassword)&& !TheNewPassword.trim().equals("")){
                        String Params="&Password="+TheNewPassword+"&Email="+TheEmail;
                        ResetPassword pr= new ResetPassword(Params);
                        pr.execute();
                    }else{
                        if(TheNewPassword.trim().equals("")) {
                            Password_et.setError(getString(R.string.Password_is_empty));
                            Toast.makeText(v.getContext(),TheNewPassword.trim(), Toast.LENGTH_LONG).show();
                        }else{
                            conf_Password.setText("");
                            conf_Password.setError(getString(R.string.Confirm_password_error));
                            Toast.makeText(v.getContext(),R.string.Confirm_password_error, Toast.LENGTH_LONG).show();
                        }
                    }
                }
            }
        });
    }
    boolean isEmpty(MaterialEditText etText) {return etText.getText().toString().trim().length() == 0;}
    public class sendMailTask extends AsyncTask<String ,Void ,Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();



        }
        @Override
        protected Void doInBackground(String... params) {

            m = new Mail("mypharmacyserver@gmail.com", "srstudio");
            String[] toArr = {TheEmail};
            m.setTo(toArr);
            m.setFrom("mypharmacyserver@gmail.com");
            m.setSubject("Restor your Password");
            m.setBody("this is your confirmation CODE :  "+GENERATED_CODE);

            try {
                //m.addAttachment("/sdcard/bday.jpg");
                if(m.send()) {
                    runOnUiThread(new Runnable() {
                        public void run() {
                            page=2;
                            ConfirmCodeLayout.setVisibility(View.VISIBLE);
                            SendEmailLayout.setVisibility(View.GONE);
                            Toast.makeText(Dialog_restor_password.this, R.string.email_sent, Toast.LENGTH_LONG).show();
                        }
                    });



                } else {
                    runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(Dialog_restor_password.this, R.string.email_not_sent, Toast.LENGTH_LONG).show();
                        }
                    });
                }
            } catch(Exception e) {
                page=1;
                final String err=e.getMessage()+"";

                Log.e("MailApp", "Could not send email", e);
                if(err.equals("Invalid Addresses")){
                    runOnUiThread(new Runnable() {
                        public void run() {
                            String errr=getString(R.string.inv_email);
                            email.setError(errr);
                            Toast.makeText(Dialog_restor_password.this, errr, Toast.LENGTH_LONG).show();
                        }
                    });
                }else{
                    runOnUiThread(new Runnable() {
                        public void run() {
                            String err2=getString(R.string.err_send_email);
                            email.setError(err2);
                            Toast.makeText(Dialog_restor_password.this,err2 , Toast.LENGTH_LONG).show();
                        }
                    });
                }

            }


            return null;
        }
        protected void onProgressUpdate() {
            //called when the background task makes any progress
        }


        protected void onPostExecute() {

        }
    }

    public class CheckEmail extends GetData {

        public CheckEmail(String Params) {
            super(Params);
        }

        public void execute() {
            CheckEmailData data = new CheckEmailData();
            data.execute(GetData.LOCALHOST+"Pharmacie_Project/Check_email.php");
        }

        public class CheckEmailData extends DownloadData{

            @Override
            protected void onPostExecute(String WebData) {
                super.onPostExecute(WebData);
                responce = WebData;
                if (!responce.equals("Error connecting to server")){
                    if (!responce.equals("Email doesnt exist!\n")){
                        sendMailTask task = new sendMailTask();
                        task.execute("");
                    }else{
                        email.setError(getString(R.string.email_no_account));
                    }
                }else{
                    Toast.makeText(getApplicationContext(), R.string.SERVER_ERROR, Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    public class ResetPassword extends GetData {

        public ResetPassword(String Params) {
            super(Params);
        }

        public void execute() {
            ResetPasswordData data = new ResetPasswordData();
            data.execute(GetData.LOCALHOST+"Pharmacie_Project/Reset_Password.php");
        }

        public class ResetPasswordData extends DownloadData{

            @Override
            protected void onPostExecute(String WebData) {
                super.onPostExecute(WebData);
                responce = WebData;
                if (!responce.equals("Error connecting to server")){
                    if (responce.equals("password updated\n")){
                        finish();
                        Toast.makeText(getApplicationContext(), R.string.pass_succ_updated, Toast.LENGTH_SHORT).show();
                        page=0;
                    }
                }else{
                    Toast.makeText(getApplicationContext(),  R.string.SERVER_ERROR, Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}
