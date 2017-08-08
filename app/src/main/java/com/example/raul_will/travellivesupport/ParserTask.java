package com.example.raul_will.travellivesupport;

import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.PolyUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by raul_Will on 6/28/2017.
 */

public class ParserTask extends AsyncTask<String,Void,List<List<HashMap<String,String>>>>{

    //access this for polyline
    public static ArrayList<LatLng> allpoints;

    @Override
    protected List<List<HashMap<String, String>>> doInBackground(String... params) {

        String s=params[0];


        List<List<HashMap<String,String>>> route= new ArrayList<>();

        try {
            JSONObject parent= new JSONObject(s);

            JSONArray routes = parent.getJSONArray("routes");

            for(int i=0;i< routes.length();i++) {
                List path =new ArrayList<>();
                JSONObject object= routes.getJSONObject(i);

                JSONArray legs= object.getJSONArray("legs");
                for(int j=0;j< legs.length();j++) {
                    JSONObject object1= legs.getJSONObject(j);

                    JSONArray steps= object1.getJSONArray("steps");
                    for(int k=0;k< steps.length();k++) {

                        JSONObject object2 = steps.getJSONObject(k
                        );

                        JSONObject polyline;

                        polyline= object2.getJSONObject("polyline");
                        List<LatLng> var = PolyUtil.decode(polyline.getString("points"));

                            for(int m=0;m<var.size(); m++) {
                                HashMap<String,String> hm= new HashMap<>();
                                hm.put("lat", Double.toString(var.get(i).latitude));
                                hm.put("lng",Double.toString(var.get(i).longitude));
                                path.add(hm);
                            }

                      }
                }
                route.add(path);
            }



        } catch (JSONException e) {
            e.printStackTrace();
        }
        return  route;
    }

    @Override
    protected void onPostExecute(List<List<HashMap<String, String>>> lists) {

        try {
            allpoints= getPoints(lists);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public ArrayList<LatLng> getPoints(List<List<HashMap<String,String>>> list) throws JSONException {
        ArrayList<LatLng> points = null;
        for(int i=0; i< list.size(); i++){
            points = new ArrayList<>();

            List<HashMap<String,String>> path= list.get(i);

            for(int j=0; j< path.size(); j++){
                HashMap<String ,String> obj= path.get(j);

                Double lat=Double.parseDouble(obj.get("lat"));
                Double lng=Double.parseDouble(obj.get("lng"));
                LatLng start=new LatLng(lat, lng);

                points.add(start);
            }

        }
        return points;
    }

}
