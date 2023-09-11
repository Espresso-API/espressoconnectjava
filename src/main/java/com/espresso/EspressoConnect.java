package com.espresso;


import java.nio.charset.Charset;
import java.util.Base64;


import javax.crypto.Cipher;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;


import org.json.JSONObject;

import com.espresso.http.EspressoAPIRequestHandler;
import com.espresso.http.exceptions.EspressoAPIException;
import com.espresso.model.OrderParams;


public class EspressoConnect {


    public static boolean ENABLE_LOGGING = false;

    private String apiKey;
    private String accessToken;
    private String vendorKey;
    public String secretKey;


    private EspressoAPIRequestHandler espressoAPIRequestHandler = new EspressoAPIRequestHandler(null);


    private Routes routes = new Routes();

    public EspressoConnect() {
    }


    public EspressoConnect(String apiKey) {
        this.apiKey = apiKey;
    }


    public EspressoConnect(String apiKey, String accessToken, String vendorKey) {
        this.apiKey = apiKey;
        this.accessToken = accessToken;
        this.vendorKey = vendorKey;


    }


    @SuppressWarnings("unused")
    public String getLoginURL(String apiKey, String vendorKey, String state) throws NullPointerException {

        String baseUrl = routes.getLoginUrl() + "?api_key=" + apiKey;

        if (vendorKey != null) {
            baseUrl += "&vendor_key=" + vendorKey;
        } else {
            System.out.println("no vendor key");
        }
        if (state != null) {
            baseUrl += "&state=" + state;
        } else {
            System.out.println("no state");
        }
        return baseUrl;
    }


    public String decryptionMethod(String key, String encryptedData) {
        byte[] raw = key.getBytes(Charset.forName("UTF-8"));
        byte[] original = null;
        if (raw.length != 32) {
            throw new IllegalArgumentException("Invalid key size.");
        }
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        try {
            Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, new GCMParameterSpec(128, new byte[16]));
            original = cipher.doFinal(Base64.getUrlDecoder().decode(encryptedData));
        } catch (Exception e) {
            System.out.println(e);
        }

        return new String(original, Charset.forName("UTF-8"));
    }

    public String encryptionMethod(String key, String nonEncryptedData) {
        byte[] raw = key.getBytes(Charset.forName("UTF-8"));
        String original1 = "null";
        if (raw.length != 32) {
            throw new IllegalArgumentException("Invalid key size.");
        }
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        try {
            Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, new GCMParameterSpec(128, new byte[16]));
            original1 = Base64.getUrlEncoder().encodeToString(cipher.doFinal(nonEncryptedData.getBytes(Charset.forName("UTF-8"))));
        } catch (Exception e) {
            System.out.println(e);
        }
        return new String(original1);
    }


    public JSONObject generateSession(String apiKey, String request_Token, String vendorKey, String state, String secretKey) {
        try {

            String url = routes.get("api.token");
            System.out.println(url);

            String token = request_Token;

            String decData = decryptionMethod(secretKey, token);
            String[] result = decData.split("\\|");
//		    for(String s : result){System.out.println(s);} 
            String manipulatedcode = result[1] + "|" + result[0];
            String encData = encryptionMethod(secretKey, manipulatedcode);

            String requestToken = encData;

            JSONObject params = new JSONObject();

            params.put("apiKey", apiKey);

            params.put("requestToken", requestToken);

            params.put("vendorKey", vendorKey);

            params.put("state", state);


            JSONObject response = espressoAPIRequestHandler.postRequest(url, params);
            return response;
        } catch (Exception | EspressoAPIException e) {
            System.out.println(e.getMessage());
            return null;
        }

    }


    public JSONObject placeOrder(OrderParams orderParams) {
        try {

            String url = routes.get("api.order.place");

            JSONObject params = new JSONObject();

            if (orderParams.customerId != null)
                params.put("customerId", orderParams.customerId);

            if (orderParams.scripCode != null)
                params.put("scripCode", orderParams.scripCode);

            if (orderParams.tradingSymbol != null)
                params.put("tradingSymbol", orderParams.tradingSymbol);

            if (orderParams.exchange != null)
                params.put("exchange", orderParams.exchange);

            if (orderParams.transactionType != null)
                params.put("transactionType", orderParams.transactionType);

            if (orderParams.quantity != null)
                params.put("quantity", orderParams.quantity);

            if (orderParams.disclosedQty != null)
                params.put("disclosedQty", orderParams.disclosedQty);

            if (orderParams.price != null)
                params.put("price", orderParams.price);

            if (orderParams.triggerPrice != null)
                params.put("triggerPrice", orderParams.triggerPrice);

            if (orderParams.rmsCode != null)
                params.put("rmsCode", orderParams.rmsCode);

            if (orderParams.afterHour != null)
                params.put("afterHour", orderParams.afterHour);

            if (orderParams.orderType != null)
                params.put("orderType", orderParams.orderType);

            if (orderParams.channelUser != null)
                params.put("channelUser", orderParams.channelUser);

            if (orderParams.validity != null)
                params.put("validity", orderParams.validity);

            if (orderParams.gtdd != null)
                params.put("gtdd", orderParams.gtdd);

            if (orderParams.requestType != null)
                params.put("requestType", orderParams.requestType);

            if (orderParams.productType != null)
                params.put("productType", orderParams.productType);

            if (orderParams.instrumentType != null)
                params.put("instrumentType", orderParams.instrumentType);

            if (orderParams.strikePrice != null)
                params.put("strikePrice", orderParams.strikePrice);

            if (orderParams.optionType != null)
                params.put("optionType", orderParams.optionType);

            if (orderParams.expiry != null)
                params.put("expiry", orderParams.expiry);
            JSONObject jsonObject = null;

            jsonObject = espressoAPIRequestHandler.postRequest(this.vendorKey, this.apiKey, url, params, this.accessToken);
            return jsonObject;

        } catch (Exception | EspressoAPIException e) {
            System.out.println(e.getMessage());
            return null;


        }

    }

    public JSONObject modifyorder(OrderParams orderParams) {
        try {
            String url = routes.get("api.order.modify");

            JSONObject params = new JSONObject();

            if (orderParams.orderId != null)
                params.put("orderId", orderParams.orderId);

            if (orderParams.customerId != null)
                params.put("customerId", orderParams.customerId);

            if (orderParams.scripCode != null)
                params.put("scripCode", orderParams.scripCode);

            if (orderParams.tradingSymbol != null)
                params.put("tradingSymbol", orderParams.tradingSymbol);

            if (orderParams.exchange != null)
                params.put("exchange", orderParams.exchange);

            if (orderParams.transactionType != null)
                params.put("transactionType", orderParams.transactionType);

            if (orderParams.quantity != null)
                params.put("quantity", orderParams.quantity);

            if (orderParams.disclosedQty != null)
                params.put("disclosedQty", orderParams.disclosedQty);

            if (orderParams.executedQty != null)
                params.put("executedQty", orderParams.executedQty);

            if (orderParams.price != null)
                params.put("price", orderParams.price);

            if (orderParams.triggerPrice != null)
                params.put("triggerPrice", orderParams.triggerPrice);

            if (orderParams.rmsCode != null)
                params.put("rmsCode", orderParams.rmsCode);

            if (orderParams.afterHour != null)
                params.put("afterHour", orderParams.afterHour);

            if (orderParams.orderType != null)
                params.put("orderType", orderParams.orderType);

            if (orderParams.channelUser != null)
                params.put("channelUser", orderParams.channelUser);

            if (orderParams.validity != null)
                params.put("validity", orderParams.validity);

            if (orderParams.requestType != null)
                params.put("requestType", orderParams.requestType);

            if (orderParams.productType != null)
                params.put("productType", orderParams.productType);

            if (orderParams.instrumentType != null)
                params.put("instrumentType", orderParams.instrumentType);

            if (orderParams.strikePrice != null)
                params.put("strikePrice", orderParams.strikePrice);

            if (orderParams.optionType != null)
                params.put("optionType", orderParams.optionType);

            if (orderParams.expiry != null)
                params.put("expiry", orderParams.expiry);


            JSONObject jsonObject = espressoAPIRequestHandler.postRequest(this.vendorKey, this.apiKey, url, params, this.accessToken);
            return jsonObject;
        } catch (Exception | EspressoAPIException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public JSONObject cancelOrder(OrderParams orderParams) {
        try {
            String url = routes.get("api.order.cancel");
            JSONObject params = new JSONObject();
            if (orderParams.orderId != null)
                params.put("orderId", orderParams.orderId);

            if (orderParams.customerId != null)
                params.put("customerId", orderParams.customerId);

            if (orderParams.scripCode != null)
                params.put("scripCode", orderParams.scripCode);

            if (orderParams.tradingSymbol != null)
                params.put("tradingSymbol", orderParams.tradingSymbol);

            if (orderParams.exchange != null)
                params.put("exchange", orderParams.exchange);

            if (orderParams.transactionType != null)
                params.put("transactionType", orderParams.transactionType);

            if (orderParams.quantity != null)
                params.put("quantity", orderParams.quantity);

            if (orderParams.disclosedQty != null)
                params.put("disclosedQty", orderParams.disclosedQty);

            if (orderParams.executedQty != null)
                params.put("executedQty", orderParams.executedQty);

            if (orderParams.price != null)
                params.put("price", orderParams.price);

            if (orderParams.triggerPrice != null)
                params.put("triggerPrice", orderParams.triggerPrice);

            if (orderParams.rmsCode != null)
                params.put("rmsCode", orderParams.rmsCode);

            if (orderParams.afterHour != null)
                params.put("afterHour", orderParams.afterHour);

            if (orderParams.orderType != null)
                params.put("orderType", orderParams.orderType);

            if (orderParams.channelUser != null)
                params.put("channelUser", orderParams.channelUser);

            if (orderParams.validity != null)
                params.put("validity", orderParams.validity);

            if (orderParams.requestType != null)
                params.put("requestType", orderParams.requestType);

            if (orderParams.productType != null)
                params.put("productType", orderParams.productType);


            JSONObject jsonObject = espressoAPIRequestHandler.postRequest(this.vendorKey, apiKey, url, params, accessToken);
            return jsonObject;
        } catch (Exception | EspressoAPIException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    //		API retrieves all orders for the day.
    public JSONObject cancelOrderById(Long orderId) {
        try {
            String url = routes.get("api.order.cancelId") + "/" + orderId;
            System.out.println(url);
            JSONObject response = espressoAPIRequestHandler.getRequest(this.vendorKey, this.apiKey, url, this.accessToken);
            System.out.println(response.toString(4));

            return response;
        } catch (Exception | EspressoAPIException e) {
            System.out.println("Exception#: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }


    //		API retrieves all orders for the day.
    public JSONObject getOrder(Long customerId) {
        try {
            String url = routes.get("api.order.report") + "/" + customerId;
            System.out.println(url);
            JSONObject response = espressoAPIRequestHandler.getRequest(this.vendorKey, this.apiKey, url, this.accessToken);
            System.out.println(response.toString(4));

            return response;
        } catch (Exception | EspressoAPIException e) {
            System.out.println("Exception#: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }


    //		This API retrieves all positions open for the day.
    public JSONObject getPosition(Long customerId) {
        try {
            String url = routes.get("api.order.position") + "/" + customerId;
            System.out.println(url);
            JSONObject response = espressoAPIRequestHandler.getRequest(this.vendorKey, this.apiKey, url, this.accessToken);
            System.out.println(response.toString(4));
            return response;
        } catch (Exception | EspressoAPIException e) {
            System.out.println("Exception#: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }


    //		The API can retrieve an order's history.
    public JSONObject orderHistory(String exchange, Long customerId, String orderId) {
        try {
            String url = routes.get("api.order.history") + "/" + exchange + "/" + customerId + "/" + orderId;
            System.out.println(url);
            JSONObject response = espressoAPIRequestHandler.getRequest(this.vendorKey, this.apiKey, url, this.accessToken);
            System.out.println(response.toString(4));
            return response;
        } catch (Exception | EspressoAPIException e) {
            System.out.println("Exception#: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    //		This API retrieve trade of an order
    public JSONObject getTrades(String exchange, Long customerId, String orderId) {
        try {
            String url = routes.get("api.order.trade") + "/" + exchange + "/" + customerId + "/" + orderId + "/trades";
            System.out.println(url);
            JSONObject response = espressoAPIRequestHandler.getRequest(this.vendorKey, this.apiKey, url, this.accessToken);
            System.out.println(response.toString(4));
            return response;
        } catch (Exception | EspressoAPIException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }


    //
    public JSONObject getHolding(Long customerId) {
        try {
            String url = routes.get("api.order.holding") + "/" + customerId;
            System.out.println(url);
            JSONObject response = espressoAPIRequestHandler.getRequest(this.vendorKey, this.apiKey, url, this.accessToken);
            System.out.println(response.toString(4));
            return response;
        } catch (Exception | EspressoAPIException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }


    //		This API provides only active scripts for the day.
    public JSONObject getActiveScript(String exchange) {
        try {
            String url = routes.get("api.order.master") + "/" + exchange;
            System.out.println(url);
            JSONObject response = espressoAPIRequestHandler.getRequest(this.vendorKey, this.apiKey, url, this.accessToken);
            System.out.println(response.toString(4));
            return response;
        } catch (Exception | EspressoAPIException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    //		This API provides symbols of all the exchanges.
    public JSONObject getSymbol(String exchange) {
        try {
            String url = routes.get("api.order.symbol") + "/" + exchange;
            System.out.println(url);
            JSONObject response = espressoAPIRequestHandler.getRequest(this.vendorKey, this.apiKey, url);
            System.out.println(response.toString(4));
            return response;
        } catch (Exception | EspressoAPIException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }


    public JSONObject getFunds(String exchange, Long customerId) {
        try {
            String url = routes.get("api.order.fund") + "/" + exchange + "/" + customerId;
            System.out.println(url);
            JSONObject response = espressoAPIRequestHandler.getRequest(this.vendorKey, this.apiKey, url, this.accessToken);
            System.out.println(response.toString(4));
            return response;
        } catch (Exception | EspressoAPIException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public JSONObject getHistorical(String exchange, String scripcode, String interval) {
        try {
            String url = routes.get("api.order.historical") + "/" + exchange + "/" + scripcode + "/" + interval;
            System.out.println(url);
            JSONObject response = espressoAPIRequestHandler.getRequest(this.vendorKey, this.apiKey, url, this.accessToken);
            System.out.println(response.toString(4));
            return response;
        } catch (Exception | EspressoAPIException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }


}

