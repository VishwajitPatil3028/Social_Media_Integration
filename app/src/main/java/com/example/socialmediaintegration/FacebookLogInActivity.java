package com.example.socialmediaintegration;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONException;

import java.net.URL;
import java.util.Arrays;

public class FacebookLogInActivity extends MainActivity {

    CallbackManager callbackManager;
    Button logOutFacebook;
    TextView nameFacebook,emailFacebook;
    ImageView picFacebook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facebook_log_in);

        nameFacebook = findViewById(R.id.nameFacebook);
        emailFacebook = findViewById(R.id.emailFacebook);
        logOutFacebook = findViewById(R.id.logOutFacebook);
        picFacebook = findViewById(R.id.picFacebook);

        callbackManager = CallbackManager.Factory.create();
        //loginButton.setReadPermissions("email", "public_profile");

        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile"));

        logOutFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FacebookLogInActivity.this,MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
                finish();
                mAuth.signOut();
            }
        });

        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        // App code
                        handleFacebookAccessToken(loginResult.getAccessToken());
                    }

                    @Override
                    public void onCancel() {
                        // App code
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        // App code
                    }
                });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Pass the activity result back to the Facebook SDK
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private void handleFacebookAccessToken(AccessToken token) {

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            if(user != null){

                                Glide.with(FacebookLogInActivity.this).load(R.drawable.bg2).into(picFacebook);
                                nameFacebook.setText(user.getDisplayName());
                                emailFacebook.setText("vishvajeet301101@gmail.com");
                            }
                        } else {

                            Toast.makeText(FacebookLogInActivity.this,""+task.getException(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}

/*

//    AccessTokenTracker t = new AccessTokenTracker() {
//        @Override
//        protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
//            if(currentAccessToken == null){
//                emailFacebook.setText("");
//                Toast.makeText(FacebookLogInActivity.this,"Sign Out",Toast.LENGTH_SHORT).show();
//            }
//            else{
//                loaduserProfile(currentAccessToken);
//            }
//        }
//    };

//    private void loaduserProfile(AccessToken newAccessToken){
//        GraphRequest request = GraphRequest.newMeRequest(newAccessToken,(object, response) -> {
//            if(object != null){
//                try{
//                    String email = object.getString("email");
//                    String name = object.getString("name");
//                    emailFacebook.setText(email);
//                    nameFacebook.setText(name);
//
//                }
//                catch (JSONException ex){
//                    ex.printStackTrace();
//                }
//            }
//        });
//    }

//    @Override
//    protected void onStart() {
//        super.onStart();
//        FirebaseUser user = mAuth.getCurrentUser();
//        if(user != null){
//
//            Uri personPhoto = user.getPhotoUrl();
//
//            nameFacebook.setText(user.getDisplayName());
//            emailFacebook.setText(user.getEmail());
//            Glide.with(this).load(String.valueOf(personPhoto)).into(picFacebook);
//
//
////            Intent intent = new Intent(FacebookLogInActivity.this,MainActivity.class);
////            intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
////            startActivity(intent);
////            finish();
//        }
//    }



//                                URL personPhoto = user.getPhotoUrl();

//                                int image = R.drawable.vp;
//                                picFacebook.setImageResource();

//            Intent intent = new Intent(FacebookLogInActivity.this,MainActivity.class);
//            intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
//            startActivity(intent);
//            finish();


//    private void updateUI(FirebaseUser user) {
//        Intent intent = new Intent(FacebookLogInActivity.this,HomeActivity.class);
////        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
//        startActivity(intent);
//    }

//                            Intent intent = new Intent(FacebookLogInActivity.this,MainActivity.class);
//                            intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
//                            startActivity(intent);
                            //finish();
//                            updateUI(user);

 */