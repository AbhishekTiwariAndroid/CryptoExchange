package com.example.cryptoexchange;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;

public class LiveActivity extends AppCompatActivity {

    private TextView textView, textView2, currency, textView3;
    private OkHttpClient client;


    final class EchoWebSocketListener extends WebSocketListener {
        private static final int NORMAL_CLOSURE_STATUS = 1000;

        @Override
        public void onOpen(WebSocket webSocket, Response response) {
            webSocket.send("{\"method\": \"SUBSCRIBE\",\"params\": [\"btcusdt@aggTrade\", \"bnbusdt@aggTrade\", \"ethusdt@aggTrade\", \"bchusdt@aggTrade\",  \"xrpusdt@aggTrade\"],\"id\": 1}");


        }


        @Override
        public void onMessage(WebSocket webSocket, String text) {
            output("Receiving : " + text);
            System.out.println("Receiving: " + text);
            try {
                JSONObject jsonObject = new JSONObject(text);
                String price = jsonObject.getString("p");
                price = String.valueOf(Float.valueOf(price).floatValue( ));
                String quantity = jsonObject.getString("q");
                quantity = String.valueOf(Float.valueOf(quantity).floatValue( ));
                String currency = jsonObject.getString("s");

                String finalQuantity = quantity;
                String finalPrice = price;
                runOnUiThread(new Runnable( ) {
                    @Override
                    public void run() {
                        if(currency.equals("BTCUSDT")) {
                            ((TextView) findViewById(R.id.textView)).setText(finalPrice);
                            ((TextView) findViewById(R.id.textView2)).setText(finalQuantity);
//                            ((TextView) findViewById(R.id.currency)).setText(currency);
                        }else if(currency.equals("BNBUSDT")){
                            ((TextView) findViewById(R.id.textView4)).setText(finalPrice);
                            ((TextView) findViewById(R.id.textView5)).setText(finalQuantity);
                        }else if(currency.equals("ETHUSDT")){
                            ((TextView) findViewById(R.id.textView7)).setText(finalPrice);
                            ((TextView) findViewById(R.id.textView8)).setText(finalQuantity);
                        }else if(currency.equals("BCHUSDT")){
                            ((TextView) findViewById(R.id.textView10)).setText(finalPrice);
                            ((TextView) findViewById(R.id.textView11)).setText(finalQuantity);
                        }
                    }
                });
            } catch (JSONException e) {
                e.printStackTrace( );
            }
        }

        @Override
        public void onMessage(WebSocket webSocket, ByteString bytes) {
            output("Receiving bytes : " + bytes.hex( ));
        }

        @Override
        public void onClosing(WebSocket webSocket, int code, String reason) {
            webSocket.close(NORMAL_CLOSURE_STATUS, null);
            webSocket.send("{\n  \"method\": \"UNSUBSCRIBE\",\n  \"params\": [\n    \"btcusdt@depth\"\n  ],\n  \"id\": 312\n}");
            output("Closing : " + code + " / " + reason);


        }

        @Override
        public void onFailure(WebSocket webSocket, Throwable t, Response response) {
            output("Error : " + t.getMessage( ));
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live);


        client = new OkHttpClient( );

        Request request = new Request.Builder( ).url("wss://stream.binance.com:9443/ws/btcusdt@depth/btcusdt@aggTrade").build( );
        LiveActivity.EchoWebSocketListener listener = new LiveActivity.EchoWebSocketListener( );
        WebSocket ws = client.newWebSocket(request, listener);


        client.dispatcher( ).executorService( ).shutdown( );


    }





    private void output(final String txt) {
        runOnUiThread(new Runnable( ) {
            @Override
            public void run() {

            }
        });
    }



}