package com.noesdev.ade.numbers.AsyncTask;

import android.util.Log;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.noesdev.ade.numbers.BuildConfig;


import org.json.JSONObject;


import java.util.Random;

import static com.noesdev.ade.numbers.MainActivity.NETWORK_ERROR;
import static com.noesdev.ade.numbers.fragment.HomeFragment.KEY_TODAY;
import static com.noesdev.ade.numbers.fragment.SearchFragment.KEY_DATE;
import static com.noesdev.ade.numbers.fragment.SearchFragment.KEY_MATH;
import static com.noesdev.ade.numbers.fragment.SearchFragment.KEY_RANDOM;
import static com.noesdev.ade.numbers.fragment.SearchFragment.KEY_TRIVIA;
import static com.noesdev.ade.numbers.fragment.SearchFragment.KEY_YEAR;

public class GetRes {

    private String result, numberGet;
    private CallBackResult callBackResult;

    public GetRes(int number, int month, String acc, CallBackResult callBackResult) {
        this.callBackResult = callBackResult;
        String access = null;
        switch (acc) {
            case KEY_TODAY :
               case  KEY_DATE: {
                access = "/" + month + "/" + number + "/date";
            }
            break;

            case KEY_RANDOM: {
                String[] rand = {"/"+number+"/year", "/"+number,"/"+number+"/math"};
                int index = new Random().nextInt(rand.length);
                access = (rand[index]);
            }
            break;

            case KEY_MATH : {access= "/"+number+"/math";} break;
            case KEY_TRIVIA : {access= "/"+number;} break;
            case KEY_YEAR : {access= "/"+number+"/year";} break;
        }

        loadInBackground(access);
    }


    public String loadInBackground(String param) {


        AndroidNetworking.get(BuildConfig.API_NUMBERS + param + "?json")
                .setTag("TEST")
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            result = numberGet = null;
                            //     String result = new String(response.toString());
                            JSONObject respon = new JSONObject(response.toString());
                            result = respon.getString("text");

                            numberGet = String.valueOf(respon.getInt("number"));
                            Log.e("got",respon.getString("type")+" "+result+" "+numberGet);
                            callBackResult.hasil(result);
                            callBackResult.nomor(numberGet);

                        } catch (Exception e) {
                            e.printStackTrace();
                            callBackResult.hasil(NETWORK_ERROR);
                            Log.e("err", e.getMessage());
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        anError.getMessage();
                        callBackResult.hasil(NETWORK_ERROR);

                    }
                });


        return result;
    }
}
