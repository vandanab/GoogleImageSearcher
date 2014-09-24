package com.codepath.vandanab.googleimagesearcher.util;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetworkMonitor {
	private static boolean isNetworkAvailable(ConnectivityManager connectivityManager) {
	    NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
	    return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
	}

	public static boolean isOnline() {
	    try {
	        Process p1 = java.lang.Runtime.getRuntime().exec("ping -c 1 www.google.com");
	        int returnVal = p1.waitFor();
	        boolean reachable = (returnVal==0);
	        return reachable;
	    } catch (Exception e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
	    }
	    return false;
	}

	public static Status canSendNetworkRequest(ConnectivityManager connectivityManager) {
		if (!isNetworkAvailable(connectivityManager)) {
			return Status.NOT_AVAILABLE;
		}
		if (!isOnline()) {
			return Status.NOT_CONNECTED;
		}
		return Status.OK;
	}

	public enum Status {
		OK,
		NOT_AVAILABLE,
		NOT_CONNECTED
	}
}
