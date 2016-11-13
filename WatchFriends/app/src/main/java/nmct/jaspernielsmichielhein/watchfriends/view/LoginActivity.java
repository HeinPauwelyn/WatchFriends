package nmct.jaspernielsmichielhein.watchfriends.view;

import android.accounts.AccountAuthenticatorResponse;
import android.accounts.AccountManager;
import android.accounts.OnAccountsUpdateListener;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.view.View;

import nmct.jaspernielsmichielhein.watchfriends.R;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String KEY_IS_RESOLVING = "is_resolving";
    private static final String KEY_SHOULD_RESOLVE = "should_resolve";

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

        mTxbUsername = (AppCompatEditText) findViewById(R.id.txbUsername);
        mTxbPassword = (AppCompatEditText) findViewById(R.id.txbPassword);

        findViewById(R.id.btnLogin).setOnClickListener(this);

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
        }
    }

    private void onSignInClicked() {
    }
}