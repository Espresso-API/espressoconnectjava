package com.espresso.Ticker;

import com.espresso.http.exceptions.EspressoAPIException;

public interface EspressoWSOnError {
	public void onError(Exception exception);

	public void onError(EspressoAPIException espressoAPIException);

	void onError(String error);
}
