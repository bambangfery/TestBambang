package com.dans.bambang.presenter;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

public class LoginPresenter {
    private View view;
    private GoogleSignInClient mGoogleSignInClient;
    private GoogleSignInOptions gso;
    private GoogleSignInAccount account;
    private CallbackManager callbackManager;

    public LoginPresenter(View view) {
        this.view = view;
    }

    public void googleSignIn(Activity activity){
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(activity, gso);
        view.googleSignIn(mGoogleSignInClient);
    }

    public void handleGoogleSignInResult(@NotNull Task<GoogleSignInAccount> completedTask){
        try {
            account = completedTask.getResult(ApiException.class);
            if (account!=null){
                System.out.println("Nama : "+account.getDisplayName());
                view.successLogin("google",account.getDisplayName(),account.getPhotoUrl().toString());
            }
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w("TAGGOOGLE1111", "signInResult:failed msg=" + e.getStatusMessage());
            Log.w("TAGGOOGLE1111", "signInResult:failed code=" + e.getStatusCode());
            view.failureLogin();
        }
    }

    public void fbSignIn(@NotNull LoginButton loginButton){
        callbackManager = CallbackManager.Factory.create();
        loginButton.setReadPermissions(Arrays.asList("email","public_profile"));
        // Callback registration
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                // App code
                GraphRequest request = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response)
                    {
                        try {
                            String first_name = object.getString("first_name");
                            String last_name = object.getString("last_name");
                            String email = object.getString("email");
                            String id = object.getString("id");
                            String image_url = "https://graph.facebook.com/"+id+ "/picture?type=normal";

                            System.out.println("login fb : "+first_name+", urlImg : "+image_url);
                            view.successLogin("fb", "","");

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });

                Bundle parameters = new Bundle();
                parameters.putString("fields","first_name,last_name,email,id");
                request.setParameters(parameters);
                request.executeAsync();
            }

            @Override
            public void onCancel() {
                // App code
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
                view.failureLogin();
            }
        });
    }

    public interface View{
        void googleSignIn(GoogleSignInClient mGoogleSignInClient);
        void successLogin(String loginWith, String username, String url);
        void failureLogin();

    }
}
