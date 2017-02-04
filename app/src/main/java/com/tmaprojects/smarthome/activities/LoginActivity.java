package com.tmaprojects.smarthome.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.tmaprojects.smarthome.R;

public class LoginActivity extends AppCompatActivity {

    EditText ipET, passwordEt;
    Button loginBTN;
    CheckBox remmemberMeCB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);//replace the splash screen theme with the activity one
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setupViews();

        loginBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    login(new OnLogin() {
                        @Override
                        public void loggedIn() {
                            startActivity(MainActivity.getNewIntent(LoginActivity.this));
                        }
                    });
            }
        });

    }

    private void setupViews() {
        ipET = (EditText) findViewById(R.id.ipaddress);
        passwordEt = (EditText) findViewById(R.id.password);
        loginBTN = (Button) findViewById(R.id.loginBtn);
        remmemberMeCB = (CheckBox)findViewById(R.id.remmemberMe);
    }

    private boolean validateInput() {
        //TODO Add validation code here
        return true;
    }

    private void login(OnLogin callback) {
        //TODO Add login code here
        if(!validateInput())return;

        callback.loggedIn();
    }

    private interface OnLogin {
        void loggedIn();
    }
}
