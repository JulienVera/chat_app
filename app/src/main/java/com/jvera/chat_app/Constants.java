package com.jvera.chat_app;


public class Constants {
    /** */
    static final int PERMISSIONS_REQUEST_LOCATION = 666;
    /**
    * Text strings for toasts pop-ups mainly
    */
    public static String TXT_REGISTRATION_SUCCESSFUL = "Registration successful";
    public static String TXT_REGISTRATION_SUCCESSFUL_WELCOME = "Registration successful. Welcome ";
    public static String TXT_ERROR_USER_EXISTS = "username already exists";
    static String TXT_ERROR_FIELD_REQUIRED = "This field is required";
    static String TXT_ERROR_ALPHA_OR_NUMBER_ONLY = "only alphabet or number allowed";
    static String TXT_ERROR_SHORT_PASSWORD = "This password is too short";
    static String TXT_ERROR_SHORT_USERNAME = "This username is too short";
    public static String TXT_ERROR_USER_NOT_FOUND = "user not found";
    public static String TXT_ERROR_INCORRECT_PASSWORD = "This password is incorrect";

    /**
     * api urls string constants
     */
    public static String API_URL_USERS_USERNAMES = "https://chat-app-f7685.firebaseio.com/users/usernames";
    public static String API_URL_USERS_USERNAMES_JSON = "https://chat-app-f7685.firebaseio.com/users/usernames.json";
    public static String API_URL_GUESTS_USERNAMES = "https://chat-app-f7685.firebaseio.com/guests/usernames";
    public static String API_URL_GUESTS_MESSAGES = "https://chat-app-f7685.firebaseio.com/guests/messages";
    public static String API_BASE_URL = "https://chat-app-f7685.firebaseio.com/";

    /**
    * Message type Constants
    */
    public static int MESSAGE_FROM_SELF = 1;
    public static int MESSAGE_FROM_OTHER = 2;
    public static String MESSAGE_TYPE_TEXT = "text";
    public static String MESSAGE_TYPE_IMAGE = "image";

    /**
     * Messages field names
     */
    public static String MESSAGES_CATEGORY_MESSAGE = "message";
    public static String MESSAGES_CATEGORY_USER = "user";
    public static String MESSAGES_CATEGORY_TYPE = "type";

    /**
    * Semi-Constant methods for String manipulations
    */
    public static String TXT_WELCOME_USER (final String user) {
        return "Welcome " + user + "!";
    }
}
