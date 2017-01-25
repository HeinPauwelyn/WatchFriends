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
import com.google.gson.JsonObject;

import nmct.jaspernielsmichielhein.watchfriends.R;
import nmct.jaspernielsmichielhein.watchfriends.helper.ApiWatchFriendsHelper;
import nmct.jaspernielsmichielhein.watchfriends.helper.Interfaces;
import nmct.jaspernielsmichielhein.watchfriends.helper.Utils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener,
        GoogleApiClient.OnConnectionFailedListener {
    private static final String KEY_IS_RESOLVING = "is_resolving";
    private static final String KEY_SHOULD_RESOLVE = "should_resolve";
    private static final int RC_SIGN_IN = 9001;

    private GoogleApiClient mGoogleApiClient;

    CallbackManager callbackManager;
    ProgressDialog progressDialog;

    private boolean mIsResolving = false;
    private boolean mShouldResolve = false;

    private AppCompatEditText mTxbEmail;
    private AppCompatEditText mTxbLastname;
    private AppCompatEditText mTxbFirstname;
    private AppCompatEditText mTxbPassword;
    private AppCompatEditText mTxbConfirmPassword;

    private String mEmail;
    private String mLastname;
    private String mFirstname;
    private String mPassword;
    private String mConfirmPassword;
    private String mValidation;

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
                registerAccount("fb", loginResult.getAccessToken().getUserId(), null, null, null, loginResult.getAccessToken().getToken());
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

        mTxbEmail = (AppCompatEditText) findViewById(R.id.txbEmail);
        mTxbLastname = (AppCompatEditText) findViewById(R.id.txbLastname);
        mTxbFirstname = (AppCompatEditText) findViewById(R.id.txbFirstname);
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
        mEmail = mTxbEmail.getText().toString();
        mLastname = mTxbLastname.getText().toString();
        mFirstname = mTxbFirstname.getText().toString();
        mPassword = mTxbPassword.getText().toString();
        mConfirmPassword = mTxbConfirmPassword.getText().toString();
        if (checkCredentials(mEmail, mLastname, mFirstname, mPassword, mConfirmPassword)) {
            registerAccount("default", mEmail, mLastname, mFirstname, mPassword, null);
        } else {
            showRegisterError();
        }
    }

    private boolean checkCredentials(String email, String lastname, String firstname, String password, String confirmPassword) {
        boolean isValid = true;

        if (email.equals("") || lastname.equals("") || firstname.equals("") || password.equals("") || confirmPassword.equals("")) {
            isValid = false;
            mValidation = "please fill in all fields";
        } else if (!Utils.isEmailValid(email)) {
            isValid = false;
            mValidation = "not a valid email address";
        } else if (!Utils.isPasswordValid(password)) {
            isValid = false;
            mValidation = "password should be at least 8 characters";
        } else if (!password.equals(confirmPassword)) {
            isValid = false;
            mValidation = "passwords don't match";
        }

        return isValid;
    }

    private void showRegisterError() {
        progressDialog.dismiss();
        Snackbar.make(mTxbEmail, "Error when registering: " + mValidation, Snackbar.LENGTH_SHORT).show();
    }

    private void registerAccount(String tag, String email, String lastname, String firstname, String password, String token) {
        switch (tag) {
            case "fb":
                progressDialog.dismiss();
                mListener.onAccountRegistered(email, token);
                finish();
                break;
            case "google":
                progressDialog.dismiss();
                mListener.onAccountRegistered(email, token);
                finish();
                break;
            default:
                Call<JsonObject> call = ApiWatchFriendsHelper.getWatchFriendsServiceInstance().register(email, lastname, firstname, password);
                call.enqueue(registerCallback);
                break;
        }
    }

    Callback<JsonObject> registerCallback = new Callback<JsonObject>() {
        @Override
        public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
            if (response.isSuccessful()) {
                JsonObject body = response.body();
                String token = body.getAsJsonPrimitive("token").getAsString();
                String email = body.getAsJsonObject("user").getAsJsonPrimitive("email").getAsString();
                progressDialog.dismiss();
                mListener.onAccountRegistered(email, token);
                finish();
            } else {
                mValidation = "unknown error";
                showRegisterError();
            }
        }

        @Override
        public void onFailure(Call<JsonObject> call, Throwable t) {
            Log.d("Error", t.getMessage());
            mValidation = "unknown error";
            showRegisterError();
        }
    };

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
            registerAccount("google", account.getEmail(), account.getGivenName(), account.getFamilyName(), null, account.getIdToken());
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.d(RegisterActivity.class.getName(), "Connection failed: " + connectionResult);
    }

}