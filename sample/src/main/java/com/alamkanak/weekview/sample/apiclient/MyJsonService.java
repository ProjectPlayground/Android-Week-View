package com.alamkanak.weekview.sample.apiclient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by Raquib-ul-Alam Kanak on 1/3/16.
 * Website: http://alamkanak.github.io
 */
    public interface MyJsonService {

    @GET("1kpjf")
    Call<List<Event>> listEvents();

    @GET("practice/schedule/jsons/IT-3-2.json")//http://192.168.43.217/practice/schedule/jsons/IT-3-2.json
    Call<List<WeeklyEvent>> listWeekEvents();


    /*    @GET("/1kpjf")
        void listEvents(Callback<List<Event>> eventsCallback);

        @GET("/practice/schedule/jsons/IT-3-2.json")//http://192.168.43.217/practice/schedule/jsons/IT-3-2.json
        void listWeekEvents(Callback<List<WeeklyEvent>> weeklyEventsCallback);
    */

    }




/*

  [
    {"name":"Event 1","dayOfMonth":3,"startTime":"01:00","endTime":"02:00","color":"#F57F68"},
    {"name":"Event 2","dayOfMonth":3,"startTime":"04:00","endTime":"05:00","color":"#87D288"},
    {"name":"Event 184","dayOfMonth":3,"startTime":"05:00","endTime":"06:00","color":"#59DBE0"}
  ]

 */