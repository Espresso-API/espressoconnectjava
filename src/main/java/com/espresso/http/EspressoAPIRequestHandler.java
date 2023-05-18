package com.espresso.http;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.Proxy;
import java.net.URL;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;

import org.json.JSONException;

import org.json.JSONObject;

import com.espresso.EspressoConnect;
import com.espresso.http.exceptions.EspressoAPIException;

import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

@SuppressWarnings("unused")
public class EspressoAPIRequestHandler {
	
	private OkHttpClient client;
	

	
	
	
	public EspressoAPIRequestHandler(Proxy proxy) {
		OkHttpClient.Builder builder = new OkHttpClient.Builder();
		builder.connectTimeout(10000, TimeUnit.MILLISECONDS);
		if (proxy != null) {
			builder.proxy(proxy);
		}

		HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
		logging.setLevel(HttpLoggingInterceptor.Level.BODY);
		if (EspressoConnect.ENABLE_LOGGING) {
			client = builder.addInterceptor(logging).build();
		} else {
			client = builder.build();
		}
	}


	
	public JSONObject postRequest(String url, JSONObject params)
			throws IOException, JSONException, EspressoAPIException {
		Request request = createPostRequest( url, params);
		Response response = client.newCall(request).execute();
		String body = response.body().string();
		return new EspressoAPIResponseHandler().handle(response, body);

	}
	
//	To generate accessToken
	public Request createPostRequest( String url, JSONObject params) {
		try {
			MediaType JSON = MediaType.parse("application/json; charset=utf-8");
			RequestBody body = RequestBody.create(params.toString(), JSON);
			Request request = new Request.Builder().url(url).post(body).header("Content-Type", "application/json").build();
			return request;
		} catch (Exception e) {
			System.out.println("exception createPostRequest");
			System.out.println(e.getMessage());
			return null;
		}
	}

	public JSONObject postRequest(String vendorKey, String apiKey,String url, JSONObject params, String accessToken)
			throws IOException, EspressoAPIException, JSONException {
		Request request = createPostRequest(vendorKey,apiKey, url, params, accessToken);
		Response response = client.newCall(request).execute();
		String body = response.body().string();
		return new EspressoAPIResponseHandler().handle(response, body);
	}
	
	public Request createPostRequest(String vendorKey, String apiKey,String url, JSONObject params, String accessToken) {

		try {
			MediaType JSON = MediaType.parse("application/json; charset=utf-8");
			RequestBody body = RequestBody.create(params.toString(), JSON);
			Request.Builder requestBuilder = new Request.Builder().url(url).post(body)
					.header("Content-Type", "application/json")
					.header("api-key", apiKey)
					.header("access-token", accessToken);
			if (vendorKey != null) {
				requestBuilder.header("vendor-key", vendorKey);
				}
			Request request = requestBuilder.build();
			return request;
			} catch (Exception e) {
				System.out.println(e.getMessage());
				return null;
	     }
	}


	
	
	public JSONObject getRequest(String vendorKey,String apiKey,  String url)
			throws IOException, EspressoAPIException, JSONException {
		Request request = createGetRequest(vendorKey,apiKey, url);
		Response response = client.newCall(request).execute();
		String body = response.body().string();
		return new EspressoAPIResponseHandler().handle(response, body);
	}
	
	public Request createGetRequest(String vendorKey, String apiKey, String url) throws IOException {
		HttpUrl.Builder httpBuilder = HttpUrl.parse(url).newBuilder();
		Request.Builder requestBuilder = new Request.Builder().url(httpBuilder.build())
				.header("Content-Type", "application/json")
				.header("api-key", apiKey);

		if (vendorKey != null) {
			requestBuilder.header("vendor-key", vendorKey);
			}
		return requestBuilder.build();

	 }

	public JSONObject getRequest(String vendorKey,String apiKey,  String url,String accessToken)
			throws IOException, EspressoAPIException, JSONException {
		Request request = createGetRequest(vendorKey,apiKey, url,accessToken);
		Response response = client.newCall(request).execute();
		String body = response.body().string();
		return new EspressoAPIResponseHandler().handle(response, body);
	}
	
	
	public Request createGetRequest(String vendorKey, String apiKey, String url, String accessToken) throws IOException {
		HttpUrl.Builder httpBuilder = HttpUrl.parse(url).newBuilder();
		Request.Builder requestBuilder = new Request.Builder().url(httpBuilder.build())
				.header("Content-Type", "application/json")
				.header("api-key", apiKey)
				.header("access-token", accessToken);
		if (vendorKey != null) {
			requestBuilder.header("vendor-key", vendorKey);
			}
		return requestBuilder.build();

	 }
	

	

}
