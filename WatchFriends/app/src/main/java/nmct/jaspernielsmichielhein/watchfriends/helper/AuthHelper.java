package nmct.jaspernielsmichielhein.watchfriends.helper;

import android.accounts.Account;
import android.accounts.AccountAuthenticatorResponse;
import android.accounts.AccountManager;
import android.content.Context;

public class AuthHelper {

    private static AccountManager mAccountManager;
    private static AccountAuthenticatorResponse mAccountAuthenticatorResponse;

    public static String getUsername(Context context) {
        mAccountManager = AccountManager.get(context);

        Account[] accounts = mAccountManager.getAccountsByType(nmct.jaspernielsmichielhein.watchfriends.helper.Contract.ACCOUNT_TYPE);

        if (accounts.length > 0) {
            return accounts[0].name;
        } else {
            return null;
        }
    }

    public static Boolean isUserLoggedIn(Context context) {
        mAccountManager = AccountManager.get(context);

        Account[] accounts = mAccountManager.getAccountsByType(nmct.jaspernielsmichielhein.watchfriends.helper.Contract.ACCOUNT_TYPE);

        if (accounts.length > 0) {
            return true;
        } else {
            return false;
        }
    }

    public static void logUserOff(Context context) {
        mAccountManager = AccountManager.get(context);

        Account[] accounts = mAccountManager.getAccountsByType(nmct.jaspernielsmichielhein.watchfriends.helper.Contract.ACCOUNT_TYPE);

        for(int i = 0, len = accounts.length; i < len; i++) {
            mAccountManager.removeAccount(accounts[i], null, null, null);
        }
    }

}