package com.jvera.chat_app;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.firebase.client.Firebase;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Guest extends AppCompatActivity {

    private static final String TAG = "Debug" ;
    @BindView(R.id.pseudo_guest) EditText pseudo_guest;
    private static final Random r = new Random();
    private static final int guest_password_nbr = r.nextInt(100); //random number between 0 and 100


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guest);
        Log.i(TAG, "onCreate Register");
        ButterKnife.bind(this);
        Firebase.setAndroidContext(this);
    }

    @OnClick({R.id.login_guest_btn})
    public void setOnClickRegisterEvents(View v) {
        switch(v.getId()) {
            case R.id.login_guest_btn:
                register_click_action();
                startActivity(new Intent(Guest.this, Users.class));
                break;

            default:
                // Shouldn't get here
                break;
        }
    }

    private void register_click_action() {
        String pseudo_user = pseudo_guest.getText().toString();

        if (pseudo_user.equals("")) {
            pseudo_guest.setError(constants.txt_error_field_required);
        } else if (!pseudo_user.matches("[A-Za-z0-9]+")) {
            pseudo_guest.setError(constants.txt_error_alpha_or_number_only);
        } else if (pseudo_user.length() < 5) {
            pseudo_guest.setError(constants.txt_error_short_username);
        }  else {
            StringRequest request = db_add_credentials(pseudo_user);
            RequestQueue rQueue = Volley.newRequestQueue(Guest.this);
            rQueue.add(request);
        }
    }

    private StringRequest db_add_credentials(final String guest){
        final ProgressDialog prog_dial = new ProgressDialog(Guest.this);
        prog_dial.setMessage("Loading...");
        prog_dial.show();


        Response.Listener<String> response_listener = new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                Firebase reference = new Firebase(constants.api_url_guests);

                if (s.equals("null")) {
                    reference.child(guest).child("password").setValue("G_"+guest+"_"+guest_password_nbr);
                    helper.toast_error(Guest.this, constants.txt_registration_successful);
                } else {
                    try {
                        JSONObject obj = new JSONObject(s);

                        if (!obj.has(guest)) {
                            reference.child(guest).child("password").setValue("G_"+guest+"_"+guest_password_nbr);
                            helper.toast_error(Guest.this, constants.txt_registration_successful);
                            startActivity(new Intent(Guest.this, Users.class)); // if registration successful come back to Login Page

                        } else {
                            helper.toast_error(Guest.this, constants.txt_error_user_exists);
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
                constants.api_url_users_json,
                response_listener,
                error_listener
        );
    }
}