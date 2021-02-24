package com.example.user.myapplication;

/**
 * Created by user on 10/10/2020.
 */

public class Utilities {


    public String changeMilliToTime(long milliSecond){

        String hour = String.valueOf((int)(milliSecond/(60*60*1000)));
        String minute = String.valueOf((int)(milliSecond%(60*60*1000)/(60*1000)));
        String second = String.valueOf((int)((milliSecond%(60*60*1000))%(60*1000)/1000));
        if (Integer.parseInt(hour)<10){
            hour = "0"+hour;
        }
        if (Integer.parseInt(minute)<10){
            minute = "0"+minute;
        }
        if (Integer.parseInt(second)<10){
            second = "0"+second;
        }
        if (Integer.parseInt(hour)>0){
            return hour+":"+minute+":"+second;
        }else {
            return minute+":"+second;
        }
    }

    public int getProgressPercentage(long currentDuration,long totalDuration){
        Double percentage = (double)0;

        long currentSeconds = (long)currentDuration/1000;
        long totalSeconds = (long)totalDuration/1000;

        percentage = ((double)currentSeconds/totalSeconds)*100;

        return percentage.intValue();
    }

    public int progressToTime(int progress ,int totalDuration){
        int currentDuration = 0;
        totalDuration = (int)(totalDuration/1000);
        currentDuration = (int)(((double)progress/100)*totalDuration);

        return currentDuration*1000;
    }


}
