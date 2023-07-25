package com.dans.bambang.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.dans.bambang.MainActivity;
import com.dans.bambang.R;
import com.dans.bambang.databinding.ActivityLoginBinding;
import com.dans.bambang.presenter.LoginPresenter;
import com.dans.bambang.utils.PrefManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;


public class LoginActivity extends AppCompatActivity implements LoginPresenter.View,View.OnClickListener {
    private ActivityLoginBinding binding;
    private PrefManager pref;
    private int REQ_SIGN_GOOGLE = 10;
    private LoginPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        initView();
        if (pref.getIsLogin()){
            Intent i = new Intent(LoginActivity.this, MainActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
        }
    }

    private void initView(){
        pref = new PrefManager(this);
        presenter = new LoginPresenter(this);
        binding.googleSignInBtn.setOnClickListener(this);
//        binding.fbSignInBtn.setOnClickListener(this);
    }

    @Override
    public void googleSignIn(GoogleSignInClient mGoogleSignInClient) {
        Intent signIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signIntent, REQ_SIGN_GOOGLE);
    }

    @Override
    public void successLogin(String loginWith, String username, String url) {
        pref.setIsLogin(true);
        pref.setLoginWith(loginWith);
        pref.setTagUsername(username);
        pref.setIsUrlOrBitmap(url);
        Intent i = new Intent(LoginActivity.this, MainActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
    }

    @Override
    public void failureLogin() {
        Toast.makeText(this,"Gagal Login",Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        callbackManager.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQ_SIGN_GOOGLE) {
            presenter.handleGoogleSignInResult(GoogleSignIn.getSignedInAccountFromIntent(data));
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.googleSignInBtn:
                presenter.googleSignIn(this);
                break;
            case R.id.fbSignInBtn:
                presenter.fbSignIn(binding.fbSignInBtn);
                break;
        }
    }
}