#   Espresso API Java 

    Espresso API is a set of REST-like APIs that expose many capabilities required to build a complete investment and 
    trading platform. Execute orders in real time, stream live market data (WebSockets), and 
    more, with the simple HTTP API collection.
    
#   Usage
[Download Espresso API jar file](https://github.com/Espresso-API/espressoconnectjava/blob/main/target/espresso-0.0.1-SNAPSHOT.jar) and include it in your build path.

    Use Java JDK Version 8
    Include package com.espresspo into build path from maven. Use version 3.8.1
    
#   API usage
//  Instantiate the EspressoConnect class 
	
	EspressoConnect espressoConnect = new EspressoConnect();
	
//	Instantiate the Example class to perform the testing for all the methods.
	
	Example examples = new Example();
	
//	getLoginUrl --> this will provide you with the login url which can be used to login in the espresso account, here vendorKey is for vendorlogin. In-case of customer login you may leave it null.

	public void getLoginURL(EspressoConnect espressoConnect) throws EspressoAPIException, IOException {
		String apiKey = "Enter-your-apiKey";
        	String vendorKey = "Enter-your-vendorKey"; //optional
        	String state = "12345";
        	
       	String response = espressoConnect.getLoginURL(apiKey, vendorKey, state);
        	System.out.print(response);
	}
	
	

	
//	GenerateSession--> provide the requestToken received after successful login with apiKey,vendorKey state and secret key
    This will provide the accessToken after the decrypt and encrypt part using Base64.getUrlDecoder() n Base64.getUrlEncoder().
	
	public void generateSession(EspressoConnect espressoConnect) throws EspressoAPIException, IOException {
		String apiKey = "Enter-your-apiKey";
		String requestToken = "Enter-your-requestToken";
		String vendorKey = "Enter-your-vendorKey"; //optional
		String state =  "12345";
		String secretKey = "Enter-your-secretKey";
		
		JSONObject response = espressoConnect.generateSession(apiKey,requestToken,vendorKey,state,secretKey);
		System.out.print(response.toString(4));
		
	}
	
//	add apiKey ,accessToken n vendorKey(optional-> only for vendor login) in the EspressoConnect constructor
	
	EspressoConnect espressoConnect = new EspressoConnect("<api-key>","<access-token>","<vendor-key>");
	
//	Place Order
	
	public void placeOrder(EspressoConnect espressoConnect) throws EspressoAPIException, IOException {
			OrderParams orderParams = new OrderParams();
		  	orderParams.customerId=(long) XXXXXX;
			orderParams.scripCode=2475;
			orderParams.tradingSymbol = "ONGC";
			orderParams.exchange = "NC";             // Allowed parameters NC/BC/NF/RN/MX
			orderParams.transactionType="B";           // Allowed parameters (B, S, BM, SM, SAM)
			orderParams.quantity = (long) 1;
			orderParams.disclosedQty=(long) 0;
			orderParams.executedQty=(long) 0;
			orderParams.price = "156";	
			orderParams.triggerPrice="0";
			orderParams.rmsCode="SKNSE1";
			orderParams.afterHour="N";
			orderParams.orderType = "NORMAL";
			orderParams.channelUser="XXXXXX";       //	LoginId of the user
			orderParams.validity="GFD";              // Validity of an order (GFD/MyGTD/IOC)
			orderParams.requestType="CANCEL";
			orderParams.productType = "CNC";        // Note: For Equity Exchange – CNC (Normal),For F&O – CNF(Normal), MIS or MIS+)
			//Below parameters are mandatory for F&0
			orderParams.instrumentType="FUTCUR";    //(Future Stocks(FS)/ Future Index(FI)/ Option Index(OI)/ Option Stocks(OS)/ Future Currency(FUTCUR)/ Option Currency(OPTCUR))    
			orderParams.strikePrice="-1";
			orderParams.optionType="XX";         //(XX/PE/CE) 
			orderParams.expiry="31/03/2023";

		JSONObject order = espressoConnect.placeOrder(orderParams);
	
	}
	
//	Modify Order
	
	public void modifyOrder(EspressoConnect espressoConnect) throws EspressoAPIException, IOException {

		OrderParams orderParams = new OrderParams();
		orderParams.orderId = "XXXXXX";
		orderParams.customerId=(long) XXXXXX;
		orderParams.scripCode=2475;
		orderParams.tradingSymbol = "ONGC";
		orderParams.exchange = "NC";             // Allowed parameters NC/BC/NF/RN/MX
		orderParams.transactionType="B";           // Allowed parameters (B, S, BM, SM, SAM)
		orderParams.quantity = (long) 1;
		orderParams.disclosedQty=(long) 0;
		orderParams.executedQty=(long) 0;
		orderParams.price = "156";
		orderParams.triggerPrice="0";
		orderParams.rmsCode="SKNSE1";
		orderParams.afterHour="N";
		orderParams.orderType = "NORMAL";
		orderParams.channelUser="XXXXXX";       //	LoginId of the user
		orderParams.validity="GFD";              // Validity of an order (GFD/MyGTD/IOC)
		orderParams.requestType="MODIFY";
		orderParams.productType = "CNC";        // Note: For Equity Exchange – CNC (Normal),For F&O – CNF(Normal), MIS or MIS+)
		//Below parameters are mandatory for F&0
		orderParams.instrumentType="FUTCUR";    //(Future Stocks(FS)/ Future Index(FI)/ Option Index(OI)/ Option Stocks(OS)/ Future Currency(FUTCUR)/ Option Currency(OPTCUR))    
		orderParams.strikePrice="-1";
		orderParams.optionType="XX";         //(XX/PE/CE) 
		orderParams.expiry="31/03/2023";
	
		
		JSONObject order = espressoConnect.modifyorder(orderParams);
		
	}
	
//	Cancel order
	
	public void cancelOrder(EspressoConnect espressoConnect) throws EspressoAPIException, IOException {

		OrderParams orderParams = new OrderParams();
		orderParams.orderId = "XXXXXX";
		orderParams.customerId=(long) XXXXXX;
		orderParams.scripCode=2475;
		orderParams.tradingSymbol = "ONGC";
		orderParams.exchange = "NC";             // Allowed parameters NC/BC/NF/RN/MX
		orderParams.transactionType="B";           // Allowed parameters (B, S, BM, SM, SAM)
		orderParams.quantity = (long) 1;
		orderParams.disclosedQty=(long) 0;
		orderParams.executedQty=(long) 0;
		orderParams.price = "156";
		orderParams.triggerPrice="0";
		orderParams.rmsCode="SKNSE1";
		orderParams.afterHour="N";
		orderParams.orderType = "NORMAL";
		orderParams.channelUser="XXXXXX";       //	LoginId of the user
		orderParams.validity="GFD";              // Validity of an order (GFD/MyGTD/IOC)
		orderParams.requestType="CANCEL";
		orderParams.productType = "CNC";        // Note: For Equity Exchange – CNC (Normal),For F&O – CNF(Normal), MIS or MIS+)
		//Below parameters are mandatory for F&0
		orderParams.instrumentType="FUTCUR";    //(Future Stocks(FS)/ Future Index(FI)/ Option Index(OI)/ Option Stocks(OS)/ Future Currency(FUTCUR)/ Option Currency(OPTCUR))    
		orderParams.strikePrice="-1";
		orderParams.optionType="XX";         //(XX/PE/CE) 
		orderParams.expiry="31/03/2023";
	
		JSONObject order = espressoConnect.cancelOrder(orderParams);
		
	}
	
//	Funds --> limit_statement
	
	public void getFunds(EspressoConnect espressoConnect) throws EspressoAPIException, IOException {
		String exchange = "NC";
		Long customerId = (long) XXXXXX;
		JSONObject response = espressoConnect.getFunds(exchange,customerId);
	}
	
//	Order --> orders_history
	
	public void getOrder(EspressoConnect espressoConnect) throws EspressoAPIException, IOException {
		
		Long customerId = (long) XXXXXX;
		JSONObject response = espressoConnect.getOrder(customerId);
	}
	
//	Position --> trades_history
	
	public void getPosition(EspressoConnect espressoConnect) throws EspressoAPIException, IOException {
		
		Long customerId = (long) XXXXXX;
		JSONObject response = espressoConnect.getPosition(customerId);
	}
	
//	Order History -->order_history
	
	public void orderHistory(EspressoConnect espressoConnect) throws EspressoAPIException, IOException {
		String exchange = "NC";
		Long customerId = (long) XXXXXX;
		String orderId="XXXXXX";
		JSONObject response = espressoConnect.orderHistory(exchange,customerId,orderId);
	}
	
//	Trade --> trade_history
	
	public void getTrades(EspressoConnect espressoConnect) throws EspressoAPIException, IOException {
		String exchange = "NC";
		Long customerId = (long) XXXXXX;
		String orderId="XXXXXX";
		JSONObject response = espressoConnect.getTrades(exchange,customerId,orderId);
	}
	
//	Holdings --> holdings
	
	public void getHolding(EspressoConnect espressoConnect) throws EspressoAPIException, IOException {
		
		Long customerId = (long) XXXXXX;
		JSONObject response = espressoConnect.getHolding(customerId);
	}
	
//	Active Scripts --> master
	 
	 public void getActiveScript(EspressoConnect espressoConnect) throws EspressoAPIException, IOException {
		
    	String exchange = "NC";
    		JSONObject response = espressoConnect.getActiveScript(exchange);
    	}
    	
//	Symbol (without access token)
	   
	   public void getSymbol(EspressoConnect espressoConnect) throws EspressoAPIException, IOException {
		
	String exchange = "NC";
		JSONObject response = espressoConnect.getSymbol(exchange);
	}
	
//	Historical
	
	public void getHistorical(EspressoConnect espressoConnect) throws EspressoAPIException, IOException {
		
    	String exchange = "MX";
    	String scripCode = "XXXXXX";
    	String interval="daily";
    		JSONObject response = espressoConnect.getHistorical(exchange,scripCode,interval);
    	}
    	
//	Cancel by orderId
	
	public void cancelOrderById(EspressoConnect espressoConnect) throws EspressoAPIException, IOException {
		Long orderId=(long) XXXXXX;
		JSONObject response = espressoConnect.cancelOrderById(orderId);
	}
	
//	WEBSOCKET
    
    public void espressoWebsocketUsage(String accessToken, String apiKey)
	
			throws EspressoAPIException {

		EspressoWebsocket espressoWebsocket = new EspressoWebsocket(accessToken, apiKey);
		
//		Subscribe request

		JSONObject jsonObject = new JSONObject();
		JSONArray keyArray = new JSONArray();
		JSONArray valueArray = new JSONArray();

		jsonObject.put("action", "subscribe");
		keyArray.put("feed");
		valueArray.put("");
		jsonObject.put("key", keyArray);
		jsonObject.put("value", valueArray);
		JSONObject subscribe = jsonObject;

//		Feed Request

		JSONObject jsonObject1 = new JSONObject();
		JSONArray keyArray1 = new JSONArray();
		JSONArray valueArray1 = new JSONArray();

		jsonObject1.put("action", "feed");
		keyArray1.put("ltp");
		valueArray1.put("RN1314");
		jsonObject1.put("key", keyArray1);
		jsonObject1.put("value", valueArray1);
		
		JSONObject feed = jsonObject1;
		
		
//		Unsubscribe request

		JSONObject jsonObject2 = new JSONObject();
		JSONArray keyArray2 = new JSONArray();
		JSONArray valueArray2 = new JSONArray();

		jsonObject2.put("action", "unsubscribe");
		keyArray2.put("feed");
		valueArray2.put("NC22,NF37833,NF37834,MX253461,RN7719");
		jsonObject2.put("key", keyArray2);
		jsonObject2.put("value", valueArray2);
		
		JSONObject unsubscribe = jsonObject2;

//	    Calling subscribe,feed n unsubscribe		
		

		espressoWebsocket.setOnConnectedListener(new  EspressoWSOnConnect() {
			@Override
			public void onConnected() {
				espressoWebsocket.subscribe(subscribe);
				System.out.println("subscribe request sent!");
				espressoWebsocket.subscribe(feed);
				System.out.println("feed request sent!");
				espressoWebsocket.unsubscribe(unsubscribe);
  				
				System.out.println("unsubscribe request sent!");
				
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

		
			
//		Connection	
	
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
		
//		Disconnection		

		// After using EspressoAPI ticker, close websocket connection.
		espressoWebsocket.disconnect();
		
		
		
	}

	
	
	
	
