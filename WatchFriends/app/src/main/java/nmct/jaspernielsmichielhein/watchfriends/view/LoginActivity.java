package nmct.jaspernielsmichielhein.watchfriends.view;

import android.accounts.Account;
import android.accounts.AccountAuthenticatorResponse;
import android.accounts.AccountManager;
import android.accounts.OnAccountsUpdateListener;
import android.content.Intent;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.util.Log;
import android.view.View;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;

import nmct.jaspernielsmichielhein.watchfriends.R;
import nmct.jaspernielsmichielhein.watchfriends.helper.AuthHelper;
import nmct.jaspernielsmichielhein.watchfriends.helper.Contract;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, GoogleApiClient.OnConnectionFailedListener {
    private static final String KEY_IS_RESOLVING = "is_resolving";
    private static final String KEY_SHOULD_RESOLVE = "should_resolve";
    private static final int RC_SIGN_IN = 9000;

    private GoogleApiClient mGoogleApiClient;

    private boolean mIsResolving = false;
    private boolean mShouldResolve = false;

    private AppCompatEditText mTxbUsername;
    private AppCompatEditText mTxbPassword;

    private String mUsername;
    private String mPassword;

    private OnAccountsUpdateListener mOnAccountsUpdateListener;

    private AccountManager mAccountManager;
    private AccountAuthenticatorResponse mAccountAuthenticatorResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        mGoogleApiClient = new GoogleApiClient.Builder(this).enableAutoManage(this, this).addApi(Auth.GOOGLE_SIGN_IN_API, googleSignInOptions).build();

        mTxbUsername = (AppCompatEditText) findViewById(R.id.txbUsername);
        mTxbPassword = (AppCompatEditText) findViewById(R.id.txbPassword);

        findViewById(R.id.btnLogin).setOnClickListener(this);
        findViewById(R.id.btnLoginGoogle).setOnClickListener(this);

        mAccountManager = AccountManager.get(this);
        mAccountAuthenticatorResponse = this.getIntent().getParcelableExtra(AccountManager.KEY_ACCOUNT_AUTHENTICATOR_RESPONSE);

        if(mAccountAuthenticatorResponse != null) {
            mAccountAuthenticatorResponse.onRequestContinued();
        }

        if(savedInstanceState != null) {
            mIsResolving = savedInstanceState.getBoolean(KEY_IS_RESOLVING);
            mShouldResolve = savedInstanceState.getBoolean(KEY_SHOULD_RESOLVE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnLogin:
                onSignInClicked();
                break;
            case R.id.btnLoginGoogle:
                onSignInClickedGoogle();
                break;
        }
    }

    private void onSignInClicked() {
        mUsername = mTxbUsername.getText().toString();
        mPassword = mTxbPassword.getText().toString();
        addAccount(mUsername);
    }

    private void addAccount(String userName) {
        Account[] accountsByType = mAccountManager.getAccountsByType(Contract.ACCOUNT_TYPE);
        Account account;

        if (accountsByType.length == 0) {
            account = new Account(userName, Contract.ACCOUNT_TYPE);
            mAccountManager.addAccountExplicitly(account, null, null);
        } else if (!userName.equals(accountsByType[0].name)) {
            mAccountManager.removeAccount(accountsByType[0], this, null, null);
            account = new Account(userName, Contract.ACCOUNT_TYPE);
            mAccountManager.addAccountExplicitly(account, null, null);
        } else {
            account = accountsByType[0];
        }

        Intent intent = new Intent();
        intent.putExtra(AccountManager.KEY_ACCOUNT_NAME, userName);
        intent.putExtra(AccountManager.KEY_ACCOUNT_TYPE, Contract.ACCOUNT_TYPE);

        if (mAccountAuthenticatorResponse != null) {
            Bundle bundle = intent.getExtras();
            intent.putExtra(AccountManager.KEY_ACCOUNT_NAME, userName);
            intent.putExtra(AccountManager.KEY_ACCOUNT_TYPE, Contract.ACCOUNT_TYPE);
            mAccountAuthenticatorResponse.onResult(bundle);
        }

        setResult(RESULT_OK, intent);
        finish();
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
        }
    }

    private void handleSignInResult(GoogleSignInResult result) {
        Log.d(LoginActivity.class.getName(), "Sign in result: " + result.isSuccess());
        if (result.isSuccess()) {
            GoogleSignInAccount account = result.getSignInAccount();
            addAccount(account.getEmail());
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.d(LoginActivity.class.getName(), "Connection failed: " + connectionResult);
    }

}