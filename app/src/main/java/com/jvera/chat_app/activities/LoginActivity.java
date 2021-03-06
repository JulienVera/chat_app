package com.jvera.chat_app.activities;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.firebase.client.Firebase;
import com.jvera.chat_app.Helper;
import com.jvera.chat_app.R;
import com.jvera.chat_app.database_access.CredsValidationInterface;
import com.jvera.chat_app.database_access.Database;
import com.jvera.chat_app.database_access.DbHelper;
import com.jvera.chat_app.models.UserDetails;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class LoginActivity extends AppCompatActivity implements CredsValidationInterface {

    @BindView(R.id.login) EditText login;
    @BindView(R.id.password) TextInputLayout password;
    @BindView(R.id.Error_pop) TextView errorPop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        Firebase.setAndroidContext(this);

        if (!UserDetails.username.equals("")) {
            login.setText(UserDetails.username);
        }
    }

    @OnClick(R.id.login_btn)
    public void setOnClickLoginEvents(View v) {
        verifyLoginValidity();
    }
    @OnClick(R.id.guest_btn)
    public void setOnClickGuestEvents(View v) {
        startActivity(GuestRegisterActivity.class);
    }
    @OnClick(R.id.register_btn)
    public void setOnClickRegisterEvents(View v) {
        startActivity(UserRegisterActivity.class);
    }

    //slight overkill but .. just because we can
    private void startActivity(Class newActivityClass) {
        Helper.activityStarter(this, newActivityClass);
    }

    /** Checks basic user and pass validity then calls DB to see the user exists*/
    private void verifyLoginValidity() {
        String user = login.getText().toString();
        String pass = password.getEditText().getText().toString(); //IDE whining for no damn reason

        if(user.isEmpty() || pass.isEmpty()){
            errorPop.setVisibility(View.VISIBLE);
        }
        else {
            errorPop.setVisibility(View.INVISIBLE); // For appearance on 2nd attempt with usr & pass
            Database.verifyUserCredentials(
                this,
                DbHelper.generateCallback(this), //damned trick
                user,
                pass
            );
        }
    }

    /** To do list of things if credentials are validated*/
    @Override public void actionOnValidCredentials() {
        startActivity(UserHomePageActivity.class);
    }
}