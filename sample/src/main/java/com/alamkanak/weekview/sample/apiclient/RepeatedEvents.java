package com.alamkanak.weekview.sample.apiclient;

import android.app.ActivityManager;
import android.content.Context;
import android.os.StrictMode;
import android.util.Log;
import android.widget.Toast;

import com.alamkanak.weekview.WeekView;
import com.alamkanak.weekview.WeekViewEvent;
import com.alamkanak.weekview.sample.AppConstants;
import com.alamkanak.weekview.sample.AsynchronousActivity;
import com.alamkanak.weekview.sample.BaseActivity;
import com.alamkanak.weekview.sample.BasicActivity;
import com.alamkanak.weekview.sample.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/*import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
*/
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.Response;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Created by ANVESH on 21-02-2017.
 */

public class RepeatedEvents /*implements Callback<List<WeeklyEvent>>*/ {

    private static final String TAG = RepeatedEvents.class.getSimpleName();
    private List<WeekViewEvent> events = new ArrayList<WeekViewEvent>();
    boolean calledNetwork = false;
    private Context mContext;
    private WeekView mWeekView;

    public void RepeatedEvents(){

    }

    public void setWeekView(WeekView mWeekView){
        this.mWeekView = mWeekView;
    }

    public List<? extends WeekViewEvent> getWeekEvents(int newYear, int newMonth, Context context) {

        this.mContext = context;
        Log.d(TAG, "month changed - "+newMonth);
        // Download events from network if it hasn't been done already. To understand how events are
        // downloaded using retrofit, visit http://square.github.io/retrofit
        if (!calledNetwork) {

            int SDK_INT = android.os.Build.VERSION.SDK_INT;
            if (SDK_INT > 8)
            {
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                        .permitAll().build();
                StrictMode.setThreadPolicy(policy);
                //your codes here
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(AppConstants.IPAdrees)//https://api.myjson.com/bins
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

/*
            RestAdapter retrofit = new RestAdapter.Builder()
                    .setEndpoint(AppConstants.IPAdrees)//https://api.myjson.com/bins
                    .build();
*/

                try {

                    MyJsonService service = retrofit.create(MyJsonService.class);
                    Call<List<WeeklyEvent>> call = service.listWeekEvents();
                    long id = newMonth*100;
                    List<WeeklyEvent> evts = call.execute().body();
                    events.clear();
                    for (WeeklyEvent event : evts) {
                        events.addAll(event.toWeekViewEvent(newMonth, newYear, id++));
                        //    Log.d(TAG, "size of events : "+this.events.size());
                    }
                    mWeekView.notifyDatasetChanged();
                    Log.d(TAG, "WeekView dataset is set to refresh. ");
                }catch (IOException ex ){
                    Toast.makeText(mContext, R.string.async_error, Toast.LENGTH_SHORT).show();
                }

            }





/*            call.enqueue (new Callback<List<WeeklyEvent>>(){
                @Override
                public void onResponse(Call<List<WeeklyEvent>> call, Response<List<WeeklyEvent>> response) {
                    // response.isSuccessful() is true if the response code is 2xx
                    if (response.isSuccessful()) {
                        //User user = response.body();
                        List<WeeklyEvent> evts = response.body();
                        events.clear();
                        for (WeeklyEvent event : evts) {
                            for(WeekViewEvent v : event.toWeekViewEvent(visibleMonth,visibleYear))
                                events.add(v);
                            //    Log.d(TAG, "size of events : "+this.events.size());
                        }
                        mWeekView.notifyDatasetChanged();
                        Log.d(TAG, "WeekView dataset is set to refresh. ");

                    } else {
                        int statusCode = response.code();

                    }

                }

                @Override
                public void onFailure(Call<List<WeeklyEvent>> call, Throwable t) {
                    Toast.makeText(mContext, R.string.async_error, Toast.LENGTH_SHORT).show();
                }
            });
*/

            calledNetwork = true;
        }

        List<WeekViewEvent> matchedEvents = new ArrayList<WeekViewEvent>();
        Log.d(TAG, "getWeekEvents: events size before invoking matched events is : "+events.size());
        for (WeekViewEvent event : events) {
            if (eventMatches(event, newYear, newMonth)) {
                matchedEvents.add(event);
            }
        }
        return matchedEvents;
    }

/*    @Override
    public void success(List<WeeklyEvent> events, Response response) {
        this.events.clear();
        for (WeeklyEvent event : events) {
            for(WeekViewEvent v : event.toWeekViewEvent(visibleMonth,visibleYear))
                this.events.add(v);
//            Log.d(TAG, "size of events : "+this.events.size());
        }
        this.mWeekView.notifyDatasetChanged();
        Log.d(TAG, "WeekView dataset is set to refresh. ");

    }


    @Override
    public void failure(RetrofitError error) {
        error.printStackTrace();
        Toast.makeText(this.context, R.string.async_error, Toast.LENGTH_SHORT).show();
    }
*/

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


}




