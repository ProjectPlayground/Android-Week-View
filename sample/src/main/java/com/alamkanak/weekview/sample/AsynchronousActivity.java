package com.alamkanak.weekview.sample;

import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.alamkanak.weekview.WeekViewEvent;
import com.alamkanak.weekview.sample.apiclient.AppController;
import com.alamkanak.weekview.sample.apiclient.Event;
import com.alamkanak.weekview.sample.apiclient.MyJsonService;
import com.alamkanak.weekview.sample.apiclient.RepeatedEvents;
import com.alamkanak.weekview.sample.apiclient.WeeklyEvent;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Cache;
import com.android.volley.Cache.Entry;
import com.android.volley.Request.Method;
//import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
/*import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;
*/
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/*import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
*/
/**
 * An example of how events can be fetched from network and be displayed on the week view.
 * Created by Raquib-ul-Alam Kanak on 1/3/2014.
 * Website: http://alamkanak.github.io
 */


public class AsynchronousActivity extends BaseActivity   {

    private static final String TAG = AsynchronousActivity.class.getSimpleName();
    private List<WeekViewEvent> events;
    private HashMap<String,List<WeekViewEvent>> allEvents = new HashMap<String,List<WeekViewEvent>>() ;

    @Override
    public List<? extends WeekViewEvent> onMonthChange(int newYear, int newMonth) {


        if(allEvents.containsKey(newMonth+"-"+newYear))
            return allEvents.get(newMonth+"-"+newYear);

        this.events =  new ArrayList<WeekViewEvent>();
        RepeatedEvents week = new RepeatedEvents();
        week.setWeekView(getWeekView());
        this.events.addAll(week.getWeekEvents(newYear, newMonth, getApplicationContext()));
        allEvents.put(newMonth+"-"+newYear,this.events);
        Log.d(TAG, "final returning event list has "+this.events.size()+" events.");
        return this.events;
        /*
        visibleMonth = newMonth;
        visibleYear = newYear;
        Log.d(TAG, "month changed - "+newMonth);
        // Download events from network if it hasn't been done already. To understand how events are
        // downloaded using retrofit, visit http://square.github.io/retrofit
        if (!calledNetwork) {
            RestAdapter retrofit = new RestAdapter.Builder()
                    .setEndpoint(AppConstants.IPAdrees)//https://api.myjson.com/bins
                    .build();
            MyJsonService service = retrofit.create(MyJsonService.class);
            service.listWeekEvents(this);
            calledNetwork = true;
        }

        */

/*        final String URL = "http://192.168.0.5/practice/schedule/jsons/IT-3-2.json";
        // Post params to be sent to the server
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("token", "AbCdEfGh123456");

        JsonObjectRequest req = new JsonObjectRequest(URL,null /*new JSONObject(params),*/
/*                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        Gson gson = new Gson();
                        VolleyLog.v("Response:%n %s", response);
                        makeWeeklyEvents((List<WeeklyEvent>) gson.fromJson(response.toString(),WeeklyEvent.class) );
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void  onErrorResponse(VolleyError error) {

                        VolleyLog.e("Error: ", error.getMessage());

                    }
        });

// add the request object to the queue to be executed
//        AppController.getInstance().addToRequestQueue(req);
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(req);

*/


/*
        JSONObject obj = new JSONObject();
        obj.put("id", "1");
        obj.put("name", "myname");

        RequestQueue queue = MYVolley.getRequestQueue();
        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.POST,SPHERE_URL,obj,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Gson gson = new Gson();
                        VolleyLog.v("Response:%n %s", response);
                        makeWeeklyEvents((List<WeeklyEvent>) gson.fromJson(response.toString(),WeeklyEvent.class) );
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                });
        queue.add(jsObjRequest);

*/
        /*
        // Return only the events that matches newYear and newMonth.
        List<WeekViewEvent> matchedEvents = new ArrayList<WeekViewEvent>();
        for (WeekViewEvent event : events) {
            if (eventMatches(event, newYear, newMonth)) {
                matchedEvents.add(event);
            }
        }
        return matchedEvents;
        */
    }



    public static void refreshWeekView(){
        getWeekView().notifyDatasetChanged();
        Log.d(TAG, "onMonthChange: notifyDataset Changed has been invoked.");
    }


    /**
     * Checks if an event falls into a specific year and month.
     * @param event The event to check for.
     * @param year The year.
     * @param month The month.
     * @return True if the event matches the year and month.
     */
    private boolean eventMatches(WeekViewEvent event, int year, int month) {
        return (event.getStartTime().get(Calendar.YEAR) == year && event.getStartTime().get(Calendar.MONTH) == month - 1) || (event.getEndTime().get(Calendar.YEAR) == year && event.getEndTime().get(Calendar.MONTH) == month - 1);
    }

/*    @Override
    public void success(List<WeeklyEvent> events, Response response) {
        this.events.clear();
        for (WeeklyEvent event : events) {
            for(WeekViewEvent v : event.toWeekViewEvent())
                this.events.add(v);
            Log.d(TAG, "size of events : "+this.events.size());
        }

        getWeekView().notifyDatasetChanged();
    }


    @Override
    public void failure(RetrofitError error) {
        error.printStackTrace();
        Toast.makeText(this, R.string.async_error, Toast.LENGTH_SHORT).show();
    }

*/
}
