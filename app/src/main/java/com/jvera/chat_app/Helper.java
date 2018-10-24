package com.jvera.chat_app;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.jvera.chat_app.fragments.ProfilFragment;
import com.jvera.chat_app.fragments.UserListFragment;
import com.jvera.chat_app.models.UserDetails;


public class Helper {

    /** Api url generators*/
    public static String api_url_user_messages_friend () {
        return urlGeneratorMessagesSenderReceiver(UserDetails.username, UserDetails.chat_with);
    }
    public static String api_url_friend_messages_user () {
        return urlGeneratorMessagesSenderReceiver(UserDetails.chat_with, UserDetails.username);
    }
    private static String urlGeneratorMessagesSenderReceiver(String sender, String receiver) {
        return Constants.API_URL_USERS_USERNAMES + "/" + sender + "/messages/" + receiver;
    }

    /**
    * Start activities without calling `new Intent` everywhere
    */
    public static void activityStarter(Context context, Class newActivityClass) {
        context.startActivity(new Intent(context, newActivityClass));
    }

    /** Toast helper*/
    public static void toastAnnounce(Context context, final String error_msg){
        Toast.makeText(context, error_msg, Toast.LENGTH_LONG).show();
    }

    /** Check username constraints*/
    public static String checkUsernameValidity(final String username) {
        String errorMessage = "";
        if (username.equals("")) {
            errorMessage = Constants.TXT_ERROR_FIELD_REQUIRED;
        } else if (!username.matches("[A-Za-z0-9]+")) {
            errorMessage = Constants.TXT_ERROR_ALPHA_OR_NUMBER_ONLY;
        } else if (username.length() < 5) {
            errorMessage = Constants.TXT_ERROR_SHORT_USERNAME;
        }
        return errorMessage;
    }

    /** Check password constraints*/
    public static String checkPasswordValidity(final String password) {
        String errorMessage = "";
        if (password.equals("")) {
            errorMessage = Constants.TXT_ERROR_FIELD_REQUIRED;
        } else if (password.length() < 5) {
            errorMessage = Constants.TXT_ERROR_SHORT_PASSWORD;
        }
        return errorMessage;
    }

    /**
    * Generates the message box for screen prompting of messages
    */
    public static void addMessageBox(Context context, LinearLayout layout,
                                     final ScrollView scrollView, String message, int messageFrom,
                                     String messageType){
        TextView view = new TextView(context);
        ImageView imageView = new ImageView(context);

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        layoutParams.weight = 1.0f;

        boolean isImage = messageType.equals(Constants.MESSAGE_TYPE_IMAGE);

        // Assume default
        layoutParams.gravity = Gravity.END;
//        imageView.setBackgroundResource(R.drawable.bubble_right);
        view.setTextColor(context.getResources().getColor(R.color.colorBackgroundChat));
        view.setBackgroundResource(R.drawable.bubble_right);
        if (isImage) {
            if (messageFrom == Constants.MESSAGE_FROM_OTHER) {
                layoutParams.gravity = Gravity.START;
//                imageView.setBackgroundResource(R.drawable.bubble_left);
            }
            layoutParams.width = 220;
            layoutParams.height = 220;
            Bitmap decodedImage = DecodeImage(message);
            imageView.setImageBitmap(decodedImage);
            imageView.setLayoutParams(layoutParams);
            layout.addView(imageView);
        } else {
            if (messageFrom == Constants.MESSAGE_FROM_OTHER) {
                layoutParams.gravity = Gravity.START;
                view.setTextColor(context.getResources().getColor(R.color.black));
                view.setBackgroundResource(R.drawable.bubble_left);
            }
            view.setText(message);
            view.setLayoutParams(layoutParams);
            layout.addView(view);
        }

        scrollView.post(new Runnable() {
            @Override
            public void run() {
                scrollView.fullScroll(ScrollView.FOCUS_DOWN);
            }
        });
    }

    private static Bitmap DecodeImage(String encodedImage) {
        byte[] decodedString = Base64.decode(encodedImage, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
    }
    /**
     * return new user list fragment
     */
    public static Fragment createUserListFragment() {
        return new UserListFragment();
    }

    /**
     * return new user list fragment
     */
    public static Fragment createProfileFragment() {
        return new ProfilFragment();
    }
}
