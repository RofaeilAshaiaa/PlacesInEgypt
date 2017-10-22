package rofaeil.ashaiaa.idea.placesinegypt.recivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;

import rofaeil.ashaiaa.idea.placesinegypt.sights.SightsPresenter;

/**
 * broadcast for network state changes
 *
 * @author Rofaeil Ashaiaa
 *         Created on 21/10/17.
 */

public class NetworkStateChecker extends BroadcastReceiver {
    private boolean firstTime = true;

    @Override
    public void onReceive(Context context, Intent intent) {

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            if (!firstTime) {
                handleNetworkStateChange(context);
            } else
                firstTime = false;

        } else {
            handleNetworkStateChange(context);
        }

    }

    private void handleNetworkStateChange(Context context) {

        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        // if there is a network and is connected
        if (activeNetwork != null && activeNetwork.isConnected()) {
            // if connected to wifi or mobile data plan
            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI
                    || activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
                SightsPresenter.setIsConnected(true);
            }

        } else {
            SightsPresenter.setIsConnected(false);
        }
    }
}