package com.espresso.sample;

import com.espresso.EspressoConnect;
import com.espresso.http.exceptions.EspressoAPIException;

public class Test {

    public static void main(String[] args) throws EspressoAPIException {
        String apiKey = "Enter-your-apiKey";
        String accessToken = "Enter-your-accessToken";
        String vendorKey = "Enter-your-vendorKey";

        try {
            EspressoConnect espressoConnect = new EspressoConnect(apiKey, accessToken, vendorKey);

            Example examples = new Example();

            examples.getLoginURL(espressoConnect);
//			examples.generateSession(espressoConnect);
//			examples.placeOrder(espressoConnect);
//			examples.modifyOrder(espressoConnect);
//			examples.cancelOrder(espressoConnect);
//			examples.getFunds(espressoConnect);
//			examples.getOrder(espressoConnect);
//			examples.getPosition(espressoConnect);
//			examples.orderHistory(espressoConnect);
//			examples.getTrades(espressoConnect);
//			examples.getHolding(espressoConnect);
//			examples.getActiveScript(espressoConnect);
//			examples.getSymbol(espressoConnect);
//			examples.getHistorical(espressoConnect);
//			examples.cancelOrderById(espressoConnect);


//			examples.espressoWebSocketUsage(accessToken,apiKey);

        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
            e.printStackTrace();
        }

    }

}
