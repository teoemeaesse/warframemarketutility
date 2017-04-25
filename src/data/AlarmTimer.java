package data;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by tomas on 4/25/2017.
 */
public class AlarmTimer {
    private static Timer timer;

    public AlarmTimer(){
        timer = new Timer();
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
}
