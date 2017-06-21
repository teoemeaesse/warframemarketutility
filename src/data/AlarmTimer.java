package data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by tomas on 4/25/2017.
 */
public class AlarmTimer implements Serializable {
    private static Timer timer;
    private static transient ArrayList<AlarmTimer> alarmTimers = new ArrayList<>();

    public AlarmTimer(){
        timer = new Timer();
        alarmTimers.add(this);
        start();
    }

    private void start() {
        timer.schedule(new TimerTask() {
            public void run() {
                action();
                start();
            }
        }, 1000 * 360);
    }

    private void action(){
        Profile.checkForCheaperListings();
    }


    public static ArrayList<AlarmTimer> getAlarmTimers() {
        return alarmTimers;
    }
    public static void setAlarmTimers(ArrayList<AlarmTimer> alarmTimers) {
        AlarmTimer.alarmTimers = alarmTimers;
    }
}
