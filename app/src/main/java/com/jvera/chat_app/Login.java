package com.jvera.chat_app;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;


public class Login extends AppCompatActivity implements View.OnClickListener {

    final static private String TAG = Login.class.getSimpleName();
    @BindView(R.id.login) EditText login;
    @BindView(R.id.password) TextInputLayout password;
    @BindView(R.id.login_btn) Button login_btn;
    @BindView(R.id.register_btn) Button register_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Log.i(TAG, "onCreate");
        ButterKnife.bind(this);
        login_btn.setOnClickListener(this);
        register_btn.setOnClickListener(this);
    }

    @Override public void onClick(View v) {
        switch(v.getId()) {
            case R.id.login_btn:
                login_click_action();
                break;

            case R.id.register_btn:
                startActivity(new Intent(Login.this, Register.class));
                break;

            default:
                // Shouldn't get here
                break;
        }
    }

    private void login_click_action() {
        String user = login.getText().toString();
        String pass = password.getEditText().getText().toString(); //IDE whining for no damn reason
        TextView Error_pop = findViewById(R.id.Error_pop);

        if(user.isEmpty() || pass.isEmpty()){
            Error_pop.setVisibility(View.VISIBLE);
        }
        else {
            Error_pop.setVisibility(View.INVISIBLE);
            StringRequest request = db_check_credentials(user, pass);
            RequestQueue rQueue = Volley.newRequestQueue(Login.this);
            rQueue.add(request);
        }
    }

    private StringRequest db_check_credentials (final String user, final String pass) {
        String url = constants.api_url_json; //Pull our URL from Firebase
        final ProgressDialog prog_dial = new ProgressDialog(Login.this);
        prog_dial.setMessage("Loading...");
        prog_dial.show();

        Response.Listener<String> response_listener = new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                if (s.equals("null")) {
                    Toast.makeText(Login.this, "user not found", Toast.LENGTH_LONG).show();
                } else {
                    try {
                        JSONObject obj = new JSONObject(s);

                        if (!obj.has(user)) {
                            Toast.makeText(Login.this, "user not found", Toast.LENGTH_LONG).show();
                        } else if (obj.getJSONObject(user).getString("password").equals(pass)) {
                            UserDetails.username = user;
                            UserDetails.password = pass;
                            startActivity(new Intent(Login.this, Users.class));
                        } else {
                            Toast.makeText(Login.this, "incorrect password", Toast.LENGTH_LONG).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                prog_dial.dismiss();
            }
        };

        Response.ErrorListener error_listener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                System.out.println("" + volleyError);
                prog_dial.dismiss();
            }
        };

        return new StringRequest(
            Request.Method.GET,
            url,
            response_listener,
            error_listener
        );
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG,"onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG,"onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG,"onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG,"onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG,"onDestroy");
    }
}