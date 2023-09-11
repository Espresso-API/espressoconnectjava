package com.espresso.sample;

import java.io.IOException;


import org.json.JSONArray;
import org.json.JSONObject;

import com.espresso.EspressoConnect;
import com.espresso.Ticker.EspressoWSOnConnect;
import com.espresso.Ticker.EspressoWSOnDisconnect;
import com.espresso.Ticker.EspressoWSOnError;
import com.espresso.Ticker.EspressoWSOnTicks;
import com.espresso.Ticker.EspressoWebsocket;
import com.espresso.http.exceptions.EspressoAPIException;
import com.espresso.model.OrderParams;


public class Example {

    public void getLoginURL(EspressoConnect espressoConnect) throws EspressoAPIException, IOException {
        String apiKey = "Enter-your-apiKey";
        String vendorKey = "Enter-your-vendorKey"; //optional
        String state = "12345";
        String response = espressoConnect.getLoginURL(apiKey, vendorKey, state);
        System.out.print(response);
    }


    public void generateSession(EspressoConnect espressoConnect) throws EspressoAPIException, IOException {
        String apiKey = "Enter-your-apiKey";
        String requestToken = "Enter-your-requestToken";
        String vendorKey = "Enter-your-vendorKey";
        String state =  "12345";
        String secretKey = "Enter-your-secretKey";

        JSONObject response = espressoConnect.generateSession(apiKey, requestToken, vendorKey, state, secretKey);
        System.out.print(response.toString(4));

    }


    public void placeOrder(EspressoConnect espressoConnect) throws EspressoAPIException, IOException {

        OrderParams orderParams = new OrderParams();
        orderParams.customerId = (long) 11111111;
        orderParams.scripCode = 2475;
        orderParams.disclosedQty = (long) 0;
        orderParams.validity = "GFD";
        orderParams.quantity = (long) 1;
        orderParams.exchange = "NC";
        orderParams.orderType = "NORMAL";
        orderParams.tradingSymbol = "ONGC";
        orderParams.productType = "CNC";
        orderParams.transactionType = "B";
        orderParams.price = "150";
        orderParams.triggerPrice = "0";
        orderParams.rmsCode = "ANY";
        orderParams.afterHour = "N";
        orderParams.channelUser = "11111111";
        orderParams.requestType = "NEW";
        orderParams.instrumentType = "FUTCUR";
        orderParams.strikePrice = "-1";
        orderParams.optionType = "XX";
        orderParams.expiry = "28/03/2023";

        JSONObject order = espressoConnect.placeOrder(orderParams);
        System.out.println(order.toString(4));

    }

    public void modifyOrder(EspressoConnect espressoConnect) throws EspressoAPIException, IOException {

        OrderParams orderParams = new OrderParams();
        orderParams.orderId = "11111111";
        orderParams.customerId = (long) 11111111;
        orderParams.scripCode = 1314;
        orderParams.disclosedQty = (long) 0;
        orderParams.validity = "GFD";
        orderParams.quantity = (long) 3;
        orderParams.exchange = "RN";
        orderParams.orderType = "NORMAL";
        orderParams.tradingSymbol = "USDINR";
        orderParams.productType = "CNF";
        orderParams.transactionType = "B";
        orderParams.price = "81.1075";
        orderParams.triggerPrice = "0";
        orderParams.rmsCode = "ANY";
        orderParams.afterHour = "N";
        orderParams.channelUser = "11111111";
        orderParams.requestType = "MODIFY";
        orderParams.instrumentType = "FUTCUR";
        orderParams.strikePrice = "-1";
        orderParams.optionType = "XX";
        orderParams.expiry = "28/03/2023";


        JSONObject order = espressoConnect.modifyorder(orderParams);
        System.out.println(order.toString(4));

    }

    public void cancelOrder(EspressoConnect espressoConnect) throws EspressoAPIException, IOException {

        OrderParams orderParams = new OrderParams();
        orderParams.orderId = "11111111";
        orderParams.customerId = (long) 11111111;
        orderParams.scripCode = 1314;
        orderParams.disclosedQty = (long) 0;
        orderParams.validity = "GFD";
        orderParams.quantity = (long) 3;
        orderParams.exchange = "RN";
        orderParams.orderType = "NORMAL";
        orderParams.tradingSymbol = "USDINR";
        orderParams.productType = "CNF";
        orderParams.transactionType = "B";
        orderParams.price = "81.1075";
        orderParams.triggerPrice = "0";
        orderParams.rmsCode = "ANY";
        orderParams.afterHour = "N";
        orderParams.channelUser = "11111111";
        orderParams.requestType = "CANCEL";
        orderParams.instrumentType = "FUTCUR";
        orderParams.strikePrice = "-1";
        orderParams.optionType = "XX";
        orderParams.expiry = "28/03/2023";

        JSONObject order = espressoConnect.cancelOrder(orderParams);
        System.out.println(order.toString(4));

    }

    public void cancelOrderById(EspressoConnect espressoConnect) throws EspressoAPIException, IOException {
        Long orderId = (long) 11111111;
        JSONObject response = espressoConnect.cancelOrderById(orderId);
    }

    public void getFunds(EspressoConnect espressoConnect) throws EspressoAPIException, IOException {
        String exchange = "NC";
        Long customerId = (long) 1111111 ;
        JSONObject response = espressoConnect.getFunds(exchange, customerId);
    }

    public void getOrder(EspressoConnect espressoConnect) throws EspressoAPIException, IOException {

        Long customerId = (long) 11111111;
        JSONObject response = espressoConnect.getOrder(customerId);

    }

    public void getPosition(EspressoConnect espressoConnect) throws EspressoAPIException, IOException {

        Long customerId = (long) 11111111;
        JSONObject response = espressoConnect.getPosition(customerId);

    }

    public void orderHistory(EspressoConnect espressoConnect) throws EspressoAPIException, IOException {
        String exchange = "NC";
        Long customerId = (long) 11111111;
        String orderId = "11111111";
        JSONObject response = espressoConnect.orderHistory(exchange, customerId, orderId);
    }

    public void getTrades(EspressoConnect espressoConnect) throws EspressoAPIException, IOException {
        String exchange = "NC";
        Long customerId = (long) 11111111;
        String orderId = "11111111";
        JSONObject response = espressoConnect.getTrades(exchange, customerId, orderId);
    }

    public void getHolding(EspressoConnect espressoConnect) throws EspressoAPIException, IOException {

        Long customerId = (long) 11111111;
        JSONObject response = espressoConnect.getHolding(customerId);
    }

    public void getSymbol(EspressoConnect espressoConnect) throws EspressoAPIException, IOException {

        String exchange = "NC";
        JSONObject response = espressoConnect.getSymbol(exchange);
    }

    public void getActiveScript(EspressoConnect espressoConnect) throws EspressoAPIException, IOException {

        String exchange = "NC";
        JSONObject response = espressoConnect.getActiveScript(exchange);
    }

    public void getHistorical(EspressoConnect espressoConnect) throws EspressoAPIException, IOException {

        String exchange = "MX";
        String scripCode = "251800";
        String interval = "daily";
        JSONObject response = espressoConnect.getHistorical(exchange, scripCode, interval);
    }

    public void espressoWebSocketUsage(String accessToken, String apiKey)

            throws EspressoAPIException {

        final EspressoWebsocket espressoWebsocket = new EspressoWebsocket(accessToken, apiKey);

//		Subscribe request
        JSONObject jsonObject = new JSONObject();
        JSONArray keyArray = new JSONArray();
        JSONArray valueArray = new JSONArray();

        jsonObject.put("action", "subscribe");
        keyArray.put("feed");
        valueArray.put("");
        jsonObject.put("key", keyArray);
        jsonObject.put("value", valueArray);
        final JSONObject subscribe = jsonObject;

//		Feed Request
        JSONObject jsonObject1 = new JSONObject();
        JSONArray keyArray1 = new JSONArray();
        JSONArray valueArray1 = new JSONArray();

        jsonObject1.put("action", "feed");
        keyArray1.put("ltp");
        valueArray1.put("RN1314");
        jsonObject1.put("key", keyArray1);
        jsonObject1.put("value", valueArray1);

        final JSONObject feed = jsonObject1;


//		Unsubscribe request
        JSONObject jsonObject2 = new JSONObject();
        JSONArray keyArray2 = new JSONArray();
        JSONArray valueArray2 = new JSONArray();

        jsonObject2.put("action", "unsubscribe");
        keyArray2.put("feed");
        valueArray2.put("NC22,NF37833,NF37834,MX253461,RN7719");
        jsonObject2.put("key", keyArray2);
        jsonObject2.put("value", valueArray2);

        final JSONObject unsubscribe = jsonObject2;

        espressoWebsocket.setOnConnectedListener(new EspressoWSOnConnect() {
            @Override
            public void onConnected() {
                espressoWebsocket.subscribe(subscribe);
                System.out.println("subscribe request sent!");
                espressoWebsocket.subscribe(feed);
//				System.out.println("feed request sent!");
                espressoWebsocket.unsubscribe(unsubscribe);
//				System.out.println("unsubscribe request sent!");


            }
        });

        espressoWebsocket.setOnTickerArrivalListener(new EspressoWSOnTicks() {
            @Override
            public void onTicks(JSONObject ticks) {

                System.out.println("Ticker data received: " + ticks.toString(4));

            }
        });


        espressoWebsocket.setOnDisconnectedListener(new EspressoWSOnDisconnect() {
            @Override
            public void onDisconnected() {
                System.out.println("Disconnected");
            }
        });

        /** Set error listener to listen to errors. */
        espressoWebsocket.setOnErrorListener(new EspressoWSOnError() {
            @Override
            public void onError(Exception exception) {
                System.out.println("onError: " + exception.getMessage());
            }

            @Override
            public void onError(EspressoAPIException espressoAPIException) {
                System.out.println("onError: " + espressoAPIException.getMessage());
            }

            @Override
            public void onError(String error) {
                System.out.println("onError: " + error);
            }
        });


        /**
         * connects to Espresso API ticker server for getting live quotes
         */
        espressoWebsocket.connect();

        /**
         * You can check, if websocket connection is open or not using the following
         * method.
         */
        boolean isConnected = espressoWebsocket.isConnectionOpen();
        System.out.println(isConnected);

        // After using SharekhanAPI ticker, close websocket connection.
//		espressoWebsocket.disconnect();


    }
}