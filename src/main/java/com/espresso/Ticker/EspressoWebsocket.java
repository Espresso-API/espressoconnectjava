package com.espresso.Ticker;


import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

import java.util.List;
import java.util.Map;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.zip.DataFormatException;


import javax.net.ssl.SSLContext;


import org.json.JSONObject;

import com.espresso.Routes;
import com.espresso.http.exceptions.EspressoAPIException;
import com.espresso.utils.SecureSSLContext;
import com.neovisionaries.ws.client.WebSocket;
import com.neovisionaries.ws.client.WebSocketAdapter;
import com.neovisionaries.ws.client.WebSocketException;
import com.neovisionaries.ws.client.WebSocketFactory;
import com.neovisionaries.ws.client.WebSocketFrame;

public class EspressoWebsocket {

	private Routes routes = new Routes();
	private final String wsurl = routes.getSWsurl();
	private EspressoWSOnTicks onTickerArrivalListener;
	private EspressoWSOnConnect onConnectedListener;
	private EspressoWSOnDisconnect onDisconnectedListener;
	private EspressoWSOnError onErrorListener;
	private WebSocket ws;

	private String apiKey;
	
	private String accessToken;
	private boolean shouldStopThread = false;
	
	
	public EspressoWebsocket( String accessToken, String apiKey) {

		
		this.accessToken = accessToken;
		this.apiKey = apiKey;
		

		try {
			String swsurl = wsurl + "?ACCESS_TOKEN=" + this.accessToken + "&API_KEY="
					+ this.apiKey;
			SSLContext context = null;
			try {
				context = SecureSSLContext.getInstance("TLS");
			} catch (KeyManagementException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			ws = new WebSocketFactory().setSSLContext(context).setVerifyHostname(false).createSocket(swsurl);
//			System.out.println(ws);
			System.out.println(swsurl);

		} catch (IOException e) {
			if (onErrorListener != null) {
				onErrorListener.onError(e);
			}
			return;
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		ws.addListener(getWebsocketAdapter());

	}
	/**
	 * Set error listener.
	 * 
	 * @param listener of type OnError which listens to all the type of errors that
	 *                 may arise in SmartAPITicker class.
	 */
	public void setOnErrorListener(EspressoWSOnError listener) {
		this.onErrorListener = listener;
	}

	/**
	 * Set listener for listening to ticks.
	 * 
	 * @param onTickerArrivalListener is listener which listens for each tick.
	 */
	public void setOnTickerArrivalListener(EspressoWSOnTicks onTickerArrivalListener) {
		this.onTickerArrivalListener = onTickerArrivalListener;
	}

	/**
	 * Set listener for on connection established.
	 * 
	 * @param listener is used to listen to onConnected event.
	 */
	public void setOnConnectedListener(EspressoWSOnConnect listener) {
		this.onConnectedListener = listener;
	}

	/**
	 * Set listener for on connection is disconnected.
	 * 
	 * @param listener is used to listen to onDisconnected event.
	 */
	public void setOnDisconnectedListener(EspressoWSOnDisconnect listener) {
		this.onDisconnectedListener = listener;
	}

	/** Returns a WebSocketAdapter to listen to ticker related events. */
	public WebSocketAdapter getWebsocketAdapter() {
		return new WebSocketAdapter() {

			@Override
			public void onConnected(WebSocket websocket, Map<String, List<String>> headers) throws WebSocketException {
				onConnectedListener.onConnected();
				
				Runnable runnable = new Runnable() {
					@SuppressWarnings("unused")
					public void run(char[] feed) {
						JSONObject wsMWJSONRequest = new JSONObject();
						wsMWJSONRequest.put("accessToken", accessToken);
						wsMWJSONRequest.put("apikey", apiKey);
						ws.sendText(wsMWJSONRequest.toString());
						System.out.println("Hearbeat sent successfully");
						System.out.println(feed);
					}

					@Override
					public void run() {
						
						 if (shouldStopThread) {
				                return; // Stop thread execution
				            }
					}
				};

				ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
				service.scheduleAtFixedRate(runnable, 0, 1, TimeUnit.MINUTES);
				
				 System.out.println("WebSocket connection established!");

			}

			@Override
			public void onTextMessage(WebSocket websocket, String message) throws IOException, DataFormatException {
//				System.out.println( message+"data received 111234");
				
//					JSONArray tickerData = new JSONArray(message);
					JSONObject tickerData = new JSONObject(message);
					
//					System.out.println(tickerData+ "data received 111");

					if (onTickerArrivalListener != null) {
						onTickerArrivalListener.onTicks(tickerData);

//						System.out.println(tickerData);
					}else {
						System.out.println("no data received");
					}

				
//				catch(Exception e){
//					System.out.println("Error");
//				}
				
			}	

			@Override
			public void onBinaryMessage(WebSocket websocket, byte[] binary) {
				try {
					super.onBinaryMessage(websocket, binary);
				} catch (Exception e) {
					e.printStackTrace();
					if (onErrorListener != null) {
						onErrorListener.onError(e);
					}
				}
			}

			/**
			 * On disconnection, return statement ensures that the thread ends.
			 *
			 * @param websocket
			 * @param serverCloseFrame
			 * @param clientCloseFrame
			 * @param closedByServer
			 * @throws Exception
			 */
			@Override
			public void onDisconnected(WebSocket websocket, WebSocketFrame serverCloseFrame,
					WebSocketFrame clientCloseFrame, boolean closedByServer) {
				if (onDisconnectedListener != null) {
					onDisconnectedListener.onDisconnected();
				}
				return;
			}

			@Override
			public void onError(WebSocket websocket, WebSocketException cause) {
				try {
					super.onError(websocket, cause);
				} catch (Exception e) {
					e.printStackTrace();
					if (onErrorListener != null) {
						onErrorListener.onError(e);
					}
				}
			}
			
			

		};
	}

	/** Disconnects websocket connection. */
	public void disconnect() {

		if (ws != null && ws.isOpen()) {	
			ws.disconnect();
		}
	}

	
	public void subscribe(JSONObject message) {

		if (ws != null) {
			if (ws.isOpen()) {
				try {
				        ws.sendText(message.toString());
//				        System.out.println("Message sent: " + message.toString());
				    } catch (Exception e) {
				        System.err.println("Error sending message: " + e.getMessage());
				                          }
//                      System.out.println(message.toString());
				} else {
				if (onErrorListener != null) {
					onErrorListener.onError(new EspressoAPIException("ticker is not connected", "504"));
				}
			}
		} else {
			if (onErrorListener != null) {
				onErrorListener.onError(new EspressoAPIException("ticker is null not connected", "504"));
			}
		}
	}
	
	public void unsubscribe(JSONObject message) {
		

		if (ws != null) {
			if (ws.isOpen()) {
				shouldStopThread = true; 
				 
				try {
					shouldStopThread = true; 
				        ws.sendText(message.toString());

				        
				       
//				        System.out.println("Message sent: " + message.toString());
				    } catch (Exception e) {
				        System.err.println("Error sending message: " + e.getMessage());
				                          }
//                      System.out.println(message.toString());
				} else {
				if (onErrorListener != null) {
					onErrorListener.onError(new EspressoAPIException("ticker is not connected", "504"));
				}
			}
		} else {
			if (onErrorListener != null) {
				onErrorListener.onError(new EspressoAPIException("ticker is null not connected", "504"));
			}
		}
	}
	
	/**
	 * Returns true if websocket connection is open.
	 * 
	 * @return boolean
	 */
	public boolean isConnectionOpen() {
		if (ws != null) {
			if (ws.isOpen()) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Subscribes script.
	 */
	public void runscript() {

		if (ws != null) {
			if (ws.isOpen()) {

				JSONObject wsMWJSONRequest = new JSONObject();
				wsMWJSONRequest.put("accessToken", this.accessToken);

				wsMWJSONRequest.put("apikey", this.apiKey);

				ws.sendText(wsMWJSONRequest.toString());

			} else {
				if (onErrorListener != null) {
					onErrorListener.onError(new EspressoAPIException("ticker is not connected", "504"));
				}
			}
		} else {
			if (onErrorListener != null) {
				onErrorListener.onError(new EspressoAPIException("ticker is null not connected", "504"));
			}
		}
	}


	public void connect() {
		try {
			ws.connect();
		} catch (WebSocketException e) {
			e.printStackTrace();
		}

	}
//	public String send(String message) {
//		
//		  if (ws != null) {
//		        ws.sendText(message);
//		    } else {
//		        throw new IllegalStateException("WebSocket is not initialized");
//		    }
//		return message;
//	}
	
}
