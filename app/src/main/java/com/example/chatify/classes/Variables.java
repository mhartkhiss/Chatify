package com.example.chatify.classes;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Variables {

    public static final String djangoServer = "appdev.redirectme.net:25565";
    public static final String guestUser = "a@gmail.com";
    public static final String guestUserPassword = "asdasd";
    //------------------------------------------------------------------------
    public static final String request = "http://";
    public static  String contactImage = "";
    public static final String translateURL = request + djangoServer + "/api/translate/";
    public static final DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
}
