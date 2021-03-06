package com.jvera.chat_app.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.firebase.client.Firebase;
import com.jvera.chat_app.Constants;
import com.jvera.chat_app.R;
import com.jvera.chat_app.database_access.Database;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class GuestChatActivity extends AppCompatActivity {
    @BindView(R.id.layout1) LinearLayout layout;
    @BindView(R.id.messageArea) EditText messageArea;
    @BindView(R.id.scrollView) ScrollView scrollView;

    Firebase refGuestsMessages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guest_chat);
        ButterKnife.bind(this);

        refGuestsMessages = Database.referenceMessages(
            this.getApplicationContext(),
            Constants.API_URL_GUESTS_MESSAGES,
            layout,
            scrollView,
            false //guest chat is not private
        );
    }

    /** Send messages! null given as ref2 because guest chat does only prompt messages in one place*/
    @OnClick(R.id.sendButton)
    public void sendMessage(View v) {
        Database.sendMessage(messageArea, refGuestsMessages, null); //no second reference
    }
}