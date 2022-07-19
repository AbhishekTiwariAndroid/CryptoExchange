package com.example.cryptoexchange;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.VerifiedInputEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.zip.Inflater;

import okhttp3.OkHttpClient;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;

public class LiveFragment extends Fragment {


    TextView textView2, textView4, textView6, textView8;
    private OkHttpClient client;


    public LiveFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_live, container, false);

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
                    price = String.valueOf(Float.valueOf(price).floatValue());
                    String quantity = jsonObject.getString("q");
                    quantity = String.valueOf(Float.valueOf(quantity).floatValue());
                    String currency = jsonObject.getString("s");

                    String finalQuantity = quantity;
                    String finalPrice = price;
                    System.out.println(finalPrice);
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (currency.equals("BTCUSDT")) {
                                ((TextView) getActivity().findViewById(R.id.textView)).setText(finalPrice);
//                            ((TextView) findViewById(R.id.currency)).setText(currency);
                            } else if (currency.equals("BNBUSDT")) {
                                ((TextView) getActivity().findViewById(R.id.textView4)).setText(finalPrice);
                            } else if (currency.equals("ETHUSDT")) {
                                ((TextView) getActivity().findViewById(R.id.textView7)).setText(finalPrice);
                            } else if (currency.equals("BCHUSDT")) {
                                ((TextView) getActivity().findViewById(R.id.textView10)).setText(finalPrice);
                            }
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }


            TextView textView = view.findViewById(R.id.textView);

        }

        Button btnfrag;
        btnfrag = view.findViewById(R.id.btnfrag);


        btnfrag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent iNext = new Intent(view.getContext(),LiveActivity.class);
                startActivity(iNext);
            }
        });






        return view;
    }






    private void output(String s) {
    }
}