package com.makuners.android.androidquiz;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

public class IntroActivity extends AppCompatActivity {

    public static final String USER_NAME = "com.makuners.androidquiz.USER_NAME";

    @Override
    protected void onCreate(Bundle savedInstanceState) {



        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
    }

    public void goToMainActivity(View view) {
        Intent i = new Intent(this, MainActivity.class);
        EditText userNameEditText = (EditText) findViewById(R.id.inputUserName);
        String userName = userNameEditText.getText().toString();
        i.putExtra(USER_NAME, userName);
        startActivity(i);
    }
}
