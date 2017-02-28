package com.alamkanak.weekview.sample.apiclient;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.nfc.Tag;
import android.util.Log;

import com.alamkanak.weekview.WeekViewEvent;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import com.alamkanak.weekview.sample.AsynchronousActivity;

/**
 * Created by ANVESH on 10-02-2017.
 */



/**
 * An event model that was built for automatic serialization from json to object.
 * Created by Raquib-ul-Alam Kanak on 1/3/16.
 * Website: http://alamkanak.github.io
 */

//{"schedule_of":"IT-3-2","day":"1","from_time":"09:55","to_time":"10:45","subject":"CC","details":"manasa"}

public class WeeklyEvent {

    private static final String TAG = WeeklyEvent.class.getSimpleName();


    @Expose
    @SerializedName("subject")
    private String mName;
    @Expose @SerializedName("day")
    private int mDay;
    @Expose @SerializedName("from_time")
    private String mStartTime;
    @Expose @SerializedName("to_time")
    private String mEndTime;

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        this.mName = name;
    }

    public int getDay() {
        return mDay;
    }

    public void setDay(int day) {
        this.mDay = day;
    }

    public String getStartTime() {
        return mStartTime;
    }

    public void setStartTime(String startTime) {
        this.mStartTime = startTime;
    }

    public String getEndTime() {
        return mEndTime;
    }

    public void setEndTime(String endTime) {
        this.mEndTime = endTime;
    }

    @SuppressLint("SimpleDateFormat")
    public ArrayList<WeekViewEvent> toWeekViewEvent(int month,int year, long id){

        // Parse time.
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        Date start = new Date();
        Date end = new Date();
        try {
            start = sdf.parse(getStartTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        try {
            end = sdf.parse(getEndTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//dd/MM/yyyy
        //Log.d(TAG, "Common weekly event recieved : "+getName()+", time : "+ sdfDate.format(start) +" - "+ sdfDate.format(end));

        // Initialize start and end time.
        Calendar now = Calendar.getInstance();
        // Create an week view event.
        ArrayList<WeekViewEvent> list = new ArrayList<WeekViewEvent>();
        // Create a WeekViewEvent holder
        //WeekViewEvent weekViewEvent = new WeekViewEvent();
/*        int i, first_day;
        first_day = getFirstDay(1,AsynchronousActivity.visibleMonth,AsynchronousActivity.visibleYear);
        if(getDay() > first_day)
            i = getDay() - first_day + 1;
        else
            i = getDay() + first_day ;

        for( ; i<now.getActualMaximum(Calendar.DAY_OF_MONTH);i+=7  ){

            Calendar startTime = (Calendar) now.clone();
            startTime.setTimeInMillis(start.getTime());
            startTime.set(Calendar.YEAR, AsynchronousActivity.visibleYear);
            startTime.set(Calendar.MONTH, AsynchronousActivity.visibleMonth);
            startTime.set(Calendar.DAY_OF_MONTH, i);
            Calendar endTime = (Calendar) startTime.clone();
            endTime.setTimeInMillis(end.getTime());
            endTime.set(Calendar.YEAR, AsynchronousActivity.visibleYear);
            endTime.set(Calendar.MONTH, AsynchronousActivity.visibleMonth);
            endTime.set(Calendar.DAY_OF_MONTH, i);

            // Set values into event.
            weekViewEvent.setName(getName());
            weekViewEvent.setStartTime(startTime);
            weekViewEvent.setEndTime(endTime);
            weekViewEvent.setColor(Color.GREEN);


            Log.d(TAG, "weekly event recieved : "+getName()+", time : "+weekViewEvent.getStartTime().getTimeInMillis() +" - "+weekViewEvent.getEndTime().getTimeInMillis());
            // Append WeekViewEvent to the list.
            list.add(weekViewEvent);
        }
*/


        Calendar today = Calendar.getInstance();



        today.set(Calendar.HOUR_OF_DAY, 0); // ! clear would not reset the hour of day !
        today.clear(Calendar.MINUTE);
        today.clear(Calendar.SECOND);
        today.clear(Calendar.MILLISECOND);
        today.getTime();

        today.clear();
        today.set(Calendar.DAY_OF_MONTH, 1);
        today.set(Calendar.YEAR,year);
        today.set(Calendar.MONTH, month-1);
//        today.set(Calendar.WEEK_OF_MONTH,today.getActualMinimum(Calendar.WEEK_OF_MONTH));
//        today.set(Calendar.DAY_OF_WEEK, getDay()+1);
        today.set(Calendar.DAY_OF_MONTH, 1);
        today.add(Calendar.DAY_OF_YEAR,0); //(today.get(Calendar.DAY_OF_WEEK)+ getDay() - 1 ) % 7);
        today.getTime();


        Log.d(TAG, "Test : "+getName()+", time : "+ today.getTime()+"  ,year : "+year+"   ,month : "+ month);

        id = id*10;


        for(int i = 0; i<today.getActualMaximum(Calendar.WEEK_OF_MONTH);i++){

            Calendar startTime = (Calendar) today.clone();
            Calendar endTime = (Calendar) today.clone();

            startTime.setTimeInMillis(start.getTime());
            startTime.set(Calendar.YEAR, year);
            startTime.set(Calendar.MONTH,month-1);
            startTime.add(Calendar.DAY_OF_YEAR,7*i); // ((today.get(Calendar.DAY_OF_WEEK))+ getDay() - 1 ) % 7 + 7*i);
            startTime.set(Calendar.DAY_OF_WEEK,getDay()+1);
            startTime.getTime();

            if(startTime.get(Calendar.MONTH) == month)
                break;

            endTime.setTimeInMillis(end.getTime());
            endTime.set(Calendar.YEAR, year);
            endTime.set(Calendar.MONTH,month-1);
            endTime.add(Calendar.DAY_OF_YEAR,7*i); // ((today.get(Calendar.DAY_OF_WEEK))+ getDay() - 1 ) % 7+ 7*i);
            endTime.set(Calendar.DAY_OF_WEEK,getDay()+1);
            endTime.getTime();


            // Set values into event.
            WeekViewEvent weekViewEvent = new WeekViewEvent();
            weekViewEvent.setName(getName());
            weekViewEvent.setId(id++);
            weekViewEvent.setStartTime(startTime);
            weekViewEvent.setEndTime(endTime);
            weekViewEvent.setColor(Color.GREEN);

            Log.d(TAG, "weekly event recieved : "+getName()+",  id : "+weekViewEvent.getId()+", time : "+ startTime.getTime() +" - "+ endTime.getTime());
            // Append WeekViewEvent to the list.
            list.add(weekViewEvent);


        }









/*        for( int i = 0; i<today.getActualMaximum(Calendar.WEEK_OF_MONTH);i++  ){

            Calendar startTime = (Calendar) today.clone();
            startTime.setTimeInMillis(start.getTime());
            startTime.set(Calendar.YEAR, year);
            startTime.set(Calendar.MONTH, month);
            startTime.set(Calendar.WEEK_OF_MONTH, i);
            startTime.set(Calendar.DAY_OF_WEEK,getDay()+1);

            Calendar endTime = (Calendar) startTime.clone();
            endTime.setTimeInMillis(end.getTime());
            endTime.set(Calendar.YEAR, year);
            endTime.set(Calendar.MONTH, month);
            endTime.set(Calendar.WEEK_OF_MONTH, i);
            endTime.set(Calendar.DAY_OF_WEEK,getDay()+1);

            // Set values into event.
            WeekViewEvent weekViewEvent = new WeekViewEvent();
            weekViewEvent.setName(getName());
            weekViewEvent.setId(id++);
            weekViewEvent.setStartTime(startTime);
            weekViewEvent.setEndTime(endTime);
            weekViewEvent.setColor(Color.GREEN);

            Log.d(TAG, "weekly event recieved : "+getName()+",  id : "+weekViewEvent.getId()+", time : "+ startTime.getTime() +" - "+ endTime.getTime());
            // Append WeekViewEvent to the list.
            list.add(weekViewEvent);
        }

*/






        Log.d(TAG, "list is updating "+list.size()+" events.");

        return list;
    }




    public int getFirstDay(int day, int month, int year)
    {
        Calendar cal = new GregorianCalendar();
        cal.set(Calendar.DATE, day);
        cal.set(Calendar.MONTH, month);
        cal.set(Calendar.YEAR, year);
        switch (cal.get(Calendar.DAY_OF_WEEK)) {

            case Calendar.MONDAY:
                return 0;
            case Calendar.TUESDAY:
                return 1;
            case Calendar.WEDNESDAY:
                return 2;
            case Calendar.THURSDAY:
                return 3;
            case Calendar.FRIDAY:
                return 4;
            case Calendar.SATURDAY:
                return 5;
            case Calendar.SUNDAY:
                return 6;
        }
        return -1;
    }

    public int getLastDay(int day, int month, int year)
    {
        if(month == 11)
            month = -1;
        switch (getFirstDay(1, month+1, year)) {
            case 6:
                return 5;
            case 0:
                return 6;
            case 1:
                return 0;
            case 2:
                return 1;
            case 3:
                return 2;
            case 4:
                return 3;
            case 5:
                return 4;
        }
        return -1;
    }


}
