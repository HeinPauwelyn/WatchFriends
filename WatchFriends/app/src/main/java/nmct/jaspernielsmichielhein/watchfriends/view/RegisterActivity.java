package nmct.jaspernielsmichielhein.watchfriends.view;

import android.Manifest;
import android.accounts.Account;
import android.accounts.AccountAuthenticatorResponse;
import android.accounts.AccountManager;
import android.accounts.OnAccountsUpdateListener;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.util.Log;
import android.view.View;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;

import nmct.jaspernielsmichielhein.watchfriends.R;
import nmct.jaspernielsmichielhein.watchfriends.helper.Interfaces;
import nmct.jaspernielsmichielhein.watchfriends.helper.Utils;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener,
        GoogleApiClient.OnConnectionFailedListener {
    private static final String KEY_IS_RESOLVING = "is_resolving";
    private static final String KEY_SHOULD_RESOLVE = "should_resolve";
    private static final int RC_SIGN_IN = 9001;

    private GoogleApiClient mGoogleApiClient;

    CallbackManager callbackManager;

    private boolean mIsResolving = false;
    private boolean mShouldResolve = false;

    private AppCompatEditText mTxbUsername;
    private AppCompatEditText mTxbPassword;
    private AppCompatEditText mTxbConfirmPassword;

    private String mUsername;
    private String mPassword;
    private String mConfirmPassword;

    ProgressDialog progressDialog;

    private OnAccountsUpdateListener mOnAccountsUpdateListener;

    private AccountManager mAccountManager;
    private AccountAuthenticatorResponse mAccountAuthenticatorResponse;

    private Interfaces.onAccountRegisteredListener mListener;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mListener = (Interfaces.onAccountRegisteredListener) LoginActivity.mContext;

        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        mGoogleApiClient = new GoogleApiClient.Builder(this).enableAutoManage(this, this).addApi(Auth.GOOGLE_SIGN_IN_API, googleSignInOptions).build();

        callbackManager = CallbackManager.Factory.create();

        setContentView(R.layout.activity_register);
        progressDialog = new ProgressDialog(RegisterActivity.this, R.style.customDialog);

        SignInButton signInButton = (SignInButton) findViewById(R.id.btnRegisterGoogle);
        Utils.setGooglePlusButtonText(signInButton, "Register");

        LoginButton loginButton = (LoginButton) findViewById(R.id.btnRegisterFacebook);
        loginButton.setReadPermissions("email");
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                registerAccount("fb", loginResult.getAccessToken().getUserId());
            }

            @Override
            public void onCancel() {
                progressDialog.dismiss();
            }

            @Override
            public void onError(FacebookException error) {
                progressDialog.dismiss();
            }
        });

        mTxbUsername = (AppCompatEditText) findViewById(R.id.txbUsername);
        mTxbPassword = (AppCompatEditText) findViewById(R.id.txbPassword);
        mTxbConfirmPassword = (AppCompatEditText) findViewById(R.id.txbConfirmPassword);

        findViewById(R.id.btnRegister).setOnClickListener(this);
        findViewById(R.id.btnRegisterGoogle).setOnClickListener(this);
        findViewById(R.id.btnRegisterFacebook).setOnClickListener(this);
        findViewById(R.id.txtLoginLink).setOnClickListener(this);

        mAccountManager = AccountManager.get(this);
        mAccountAuthenticatorResponse = this.getIntent().getParcelableExtra(AccountManager.KEY_ACCOUNT_AUTHENTICATOR_RESPONSE);

        if (mAccountAuthenticatorResponse != null) {
            mAccountAuthenticatorResponse.onRequestContinued();
        }

        if (savedInstanceState != null) {
            mIsResolving = savedInstanceState.getBoolean(KEY_IS_RESOLVING);
            mShouldResolve = savedInstanceState.getBoolean(KEY_SHOULD_RESOLVE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnRegister:
                showDialog();
                onRegisterClicked();
                break;
            case R.id.btnRegisterGoogle:
                showDialog();
                onRegisterClickedGoogle();
                break;
            case R.id.btnRegisterFacebook:
                showDialog();
                break;
            case R.id.txtLoginLink:
                finish();
                break;
        }
    }

    private void showDialog() {
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Registering ...");
        progressDialog.show();
    }

    private void onRegisterClicked() {
        mUsername = mTxbUsername.getText().toString();
        mPassword = mTxbPassword.getText().toString();
        mConfirmPassword = mTxbConfirmPassword.getText().toString();
        if (checkCredentials(mUsername, mPassword, mConfirmPassword)) {
            registerAccount("default", mUsername);
        } else {
            showRegisterError();
        }
    }

    private boolean checkCredentials(String mUsername, String mPassword, String mConfirmPassword) {
        return !mUsername.equals("") && !mPassword.equals("") && !mConfirmPassword.equals("") && mPassword.equals(mConfirmPassword);
    }

    private void showRegisterError() {
        progressDialog.dismiss();
        Snackbar.make(mTxbUsername, "Error when registering", Snackbar.LENGTH_SHORT).show();
    }

    private void registerAccount(String tag, String userName) {
        Snackbar.make(mTxbUsername, "Account magically made", Snackbar.LENGTH_SHORT).show();
        mListener.onAccountRegistered(userName);
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mOnAccountsUpdateListener = new OnAccountsUpdateListener() {
            @Override
            public void onAccountsUpdated(Account[] accounts) {

            }
        };

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.GET_ACCOUNTS) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mAccountManager.addOnAccountsUpdatedListener(mOnAccountsUpdateListener, null, true);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mOnAccountsUpdateListener != null) {
            mAccountManager.removeOnAccountsUpdatedListener(mOnAccountsUpdateListener);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        outState.putBoolean(KEY_IS_RESOLVING, mIsResolving);
        outState.putBoolean(KEY_SHOULD_RESOLVE, mShouldResolve);
    }

    // GOOGLE

    private void onRegisterClickedGoogle() {
        Intent intent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(intent, RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        } else {
            callbackManager.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void handleSignInResult(GoogleSignInResult result) {
        Log.d(LoginActivity.class.getName(), "Sign in result: " + result.isSuccess());
        if (result.isSuccess()) {
            GoogleSignInAccount account = result.getSignInAccount();
            registerAccount("google", account.getEmail());
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.d(LoginActivity.class.getName(), "Connection failed: " + connectionResult);
    }

}