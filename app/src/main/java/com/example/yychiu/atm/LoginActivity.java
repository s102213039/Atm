package com.example.yychiu.atm;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class LoginActivity extends AppCompatActivity {

    private EditText userid,passwd;
    private Button login,cancel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        findViews();
        SharedPreferences setting = getSharedPreferences("atm",MODE_PRIVATE);
        userid.setText(setting.getString("PREF_USERID",""));
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uid = userid.getText().toString();
                String pw = passwd.getText().toString();
                String url = new StringBuilder(
                        "http://atm201605.appspot.com/login?uid=")
                        .append(uid)
                        .append("&pw=")
                        .append(pw)
                        .toString();
                new LoginTask().execute(url);
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void findViews() {
        userid = (EditText)findViewById(R.id.userid);
        passwd = (EditText)findViewById(R.id.passwd);
        login = (Button)findViewById(R.id.login);
        cancel = (Button)findViewById(R.id.cancel);
    }

    class LoginTask extends AsyncTask<String,Void,Boolean>{

        @Override
        protected Boolean doInBackground(String... strings) {
            boolean logon = false;
                try {
                    URL url = new URL(strings[0]);
                    InputStream is = url.openStream();
                    int data = is.read();
                    Log.d("Http",String.valueOf(data));
                    if(data==49){
                        String uid = userid.getText().toString();
                        logon=true;
                        SharedPreferences setting = getSharedPreferences("atm",MODE_PRIVATE);
                        setting.edit()
                                .putString("PREF_USERID",uid)
                                .apply();
                    }
                    is.close();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            return logon;
        }
        @Override
        protected void onPostExecute(Boolean logon) {
            super.onPostExecute(logon);
            if(logon){
                Toast.makeText(LoginActivity.this,"登入成功",Toast.LENGTH_LONG).show();
                setResult(RESULT_OK,getIntent());
                finish();
            }else{
                new AlertDialog.Builder(LoginActivity.this)
                        .setTitle("Atm")
                        .setMessage("登入失敗")
                        .setPositiveButton("OK",null)
                        .show();
            }
        }
    }
}
