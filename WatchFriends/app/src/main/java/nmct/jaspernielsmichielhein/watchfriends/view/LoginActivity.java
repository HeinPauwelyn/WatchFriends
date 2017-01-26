package nmct.jaspernielsmichielhein.watchfriends.view;

import android.Manifest;
import android.accounts.Account;
import android.accounts.AccountAuthenticatorResponse;
import android.accounts.AccountManager;
import android.accounts.OnAccountsUpdateListener;
import android.app.ProgressDialog;
import android.content.Context;
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
import android.widget.Toast;

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
import nmct.jaspernielsmichielhein.watchfriends.helper.AuthHelper;
import nmct.jaspernielsmichielhein.watchfriends.helper.Contract;
import nmct.jaspernielsmichielhein.watchfriends.helper.Interfaces;
import nmct.jaspernielsmichielhein.watchfriends.helper.Utils;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@RuntimePermissions
public class LoginActivity extends AppCompatActivity implements View.OnClickListener,
        GoogleApiClient.OnConnectionFailedListener,
        Interfaces.onAccountRegisteredListener {
    private static final String KEY_IS_RESOLVING = "is_resolving";
    private static final String KEY_SHOULD_RESOLVE = "should_resolve";
    private static final int RC_SIGN_IN = 9000;

    public static Context mContext;

    private GoogleApiClient mGoogleApiClient;

    CallbackManager callbackManager;

    private boolean mIsResolving = false;
    private boolean mShouldResolve = false;

    private AppCompatEditText mTxbEmail;
    private AppCompatEditText mTxbPassword;

    private String mEmail;
    private String mPassword;
    private String mValidation;

    ProgressDialog progressDialog;

    private OnAccountsUpdateListener mOnAccountsUpdateListener;

    private AccountManager mAccountManager;
    private AccountAuthenticatorResponse mAccountAuthenticatorResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;

        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestIdToken("955047814444-3e0u7iin8gka9r5htlcj38op04b3ga9c.apps.googleusercontent.com").requestEmail().build();
        mGoogleApiClient = new GoogleApiClient.Builder(this).enableAutoManage(this, this).addApi(Auth.GOOGLE_SIGN_IN_API, googleSignInOptions).build();

        callbackManager = CallbackManager.Factory.create();

        setContentView(R.layout.activity_login);
        progressDialog = new ProgressDialog(LoginActivity.this, R.style.customDialog);

        SignInButton signInButton = (SignInButton) findViewById(R.id.btnLoginGoogle);
        Utils.setGooglePlusButtonText(signInButton, "Login");

        LoginButton loginButton = (LoginButton) findViewById(R.id.btnLoginFacebook);
        loginButton.setReadPermissions("email");
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                LoginActivityPermissionsDispatcher.addAccountWithCheck(LoginActivity.this, loginResult.getAccessToken().getUserId(), loginResult.getAccessToken().getToken());
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
        mTxbPassword = (AppCompatEditText) findViewById(R.id.txbPassword);

        findViewById(R.id.btnLogin).setOnClickListener(this);
        findViewById(R.id.btnLoginGoogle).setOnClickListener(this);
        findViewById(R.id.btnLoginFacebook).setOnClickListener(this);
        findViewById(R.id.txtRegisterLink).setOnClickListener(this);

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
            case R.id.btnLogin:
                showDialog();
                onSignInClicked();
                break;
            case R.id.btnLoginGoogle:
                showDialog();
                onSignInClickedGoogle();
                break;
            case R.id.btnLoginFacebook:
                showDialog();
                break;
            case R.id.txtRegisterLink:
                showRegisterActivity();
                break;
        }
    }

    private void showDialog() {
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating ...");
        progressDialog.show();
    }

    private void onSignInClicked() {
        mEmail = mTxbEmail.getText().toString();
        mPassword = mTxbPassword.getText().toString();
        if (checkCredentials(mEmail, mPassword)) {
            LoginActivityPermissionsDispatcher.loginWithCheck(this, "default", mEmail, mPassword);
        } else {
            showLoginError();
        }
    }

    private boolean checkCredentials(String email, String password) {
        boolean isValid = true;

        if (email.equals("") || password.equals("")) {
            isValid = false;
            mValidation = "please fill in all fields";
        } else if (!Utils.isEmailValid(email)) {
            isValid = false;
            mValidation = "not a valid email address";
        }

        return isValid;
    }

    private void showLoginError() {
        progressDialog.dismiss();
        Snackbar.make(mTxbEmail, "Error when logging in: " + mValidation, Snackbar.LENGTH_SHORT).show();
    }

    @NeedsPermission(Manifest.permission.GET_ACCOUNTS)
    public void login(String tag, String email, String password) {
        switch (tag) {
            case "fb":
                break;
            case "google":
                break;
            default:
                Call<JsonObject> call = ApiWatchFriendsHelper.getWatchFriendsServiceInstance().login(email, password);
                call.enqueue(loginCallback);
                break;
        }
    }

    Callback<JsonObject> loginCallback = new Callback<JsonObject>() {
        @Override
        public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
            if (response.isSuccessful()) {
                JsonObject body = response.body();
                String token = body.getAsJsonPrimitive("token").getAsString();
                String email = body.getAsJsonObject("user").getAsJsonPrimitive("email").getAsString();
                LoginActivityPermissionsDispatcher.addAccountWithCheck((LoginActivity) mContext, email, token);
            } else {
                mValidation = "wrong credentials";
                showLoginError();
            }
        }

        @Override
        public void onFailure(Call<JsonObject> call, Throwable t) {
            Log.d("Error", t.getMessage());
            mValidation = "unknown error";
            showLoginError();
        }
    };

    @NeedsPermission(Manifest.permission.GET_ACCOUNTS)
    public void addAccount(String email, String token) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.GET_ACCOUNTS) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        Account[] accountsByType = mAccountManager.getAccountsByType(Contract.ACCOUNT_TYPE);
        Account account;

        if (accountsByType.length == 0) {
            account = new Account(email, Contract.ACCOUNT_TYPE);
            mAccountManager.addAccountExplicitly(account, null, null);
            mAccountManager.setAuthToken(account, "access_token", token);
        } else if (!email.equals(accountsByType[0].name)) {
            AuthHelper.logUserOff(this, accountsByType[0]);
            account = new Account(email, Contract.ACCOUNT_TYPE);
            mAccountManager.addAccountExplicitly(account, null, null);
            mAccountManager.setAuthToken(account, "access_token", token);
        } else {
            account = accountsByType[0];
            mAccountManager.setAuthToken(account, "access_token", token);
        }

        Intent intent = new Intent();
        intent.putExtra(AccountManager.KEY_AUTHTOKEN, token);
        intent.putExtra(AccountManager.KEY_ACCOUNT_NAME, email);
        intent.putExtra(AccountManager.KEY_ACCOUNT_TYPE, Contract.ACCOUNT_TYPE);

        if (mAccountAuthenticatorResponse != null) {
            Bundle bundle = intent.getExtras();
            intent.putExtra(AccountManager.KEY_AUTHTOKEN, token);
            intent.putExtra(AccountManager.KEY_ACCOUNT_NAME, email);
            intent.putExtra(AccountManager.KEY_ACCOUNT_TYPE, Contract.ACCOUNT_TYPE);
            mAccountAuthenticatorResponse.onResult(bundle);
        }

        setResult(RESULT_OK, intent);
        mContext = null;
        progressDialog.dismiss();
        finish();
    }

    private void showRegisterActivity() {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (!AuthHelper.isUserLoggedIn(this)) {
            ActivityCompat.finishAffinity(LoginActivity.this);
        }
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

    private void onSignInClickedGoogle() {
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
            LoginActivityPermissionsDispatcher.addAccountWithCheck(this, account.getEmail(), account.getIdToken());
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.d(LoginActivity.class.getName(), "Connection failed: " + connectionResult);
    }

    // REGISTER

    @Override
    public void onAccountRegistered(String email, String token) {
        LoginActivityPermissionsDispatcher.addAccountWithCheck(this, email, token);
    }

    // PERMISSIONS

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        LoginActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    @OnShowRationale(Manifest.permission.GET_ACCOUNTS)
    public void showRationaleForAccounts(PermissionRequest request) {
        Utils.showRationaleDialog(this, "Accounts permission needed to log in", request);
    }

    @OnPermissionDenied(Manifest.permission.GET_ACCOUNTS)
    public void onAccountsDenied() {
        progressDialog.dismiss();
        AuthHelper.logUserOff(this);
        Toast.makeText(this, "Accounts permission denied, consider accepting to use this app", Toast.LENGTH_SHORT).show();
    }

    @OnNeverAskAgain(Manifest.permission.GET_ACCOUNTS)
    public void onAccountsNeverAskAgain() {
        progressDialog.dismiss();
        AuthHelper.logUserOff(this);
        Toast.makeText(this, "Accounts permission denied with never ask again", Toast.LENGTH_SHORT).show();
    }

}