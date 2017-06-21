package persistency;

import data.AlarmTimer;

import java.io.*;
import java.util.ArrayList;

/**
 * Created by tomas on 6/21/2017.
 */
public class Loader {
    public static void load(){
        String home = System.getenv("APPDATA");
        File dir = new File(home, ".warframemarketutility").getAbsoluteFile();

        try{
            try{
                File saveFile = new File(dir, "alarms.ser");
                ObjectInputStream ois = new ObjectInputStream(new FileInputStream(saveFile));
                AlarmTimer.setAlarmTimers((ArrayList<AlarmTimer>) ois.readObject());
            }catch(FileNotFoundException e){}
        }catch(IOException | ClassNotFoundException e){
            e.printStackTrace();
        }
    }
}
