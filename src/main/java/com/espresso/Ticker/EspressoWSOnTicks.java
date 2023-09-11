package com.espresso.Ticker;


import org.json.JSONObject;

public interface EspressoWSOnTicks {
	void onTicks(JSONObject ticks);
}
