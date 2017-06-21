package persistency;

import data.AlarmTimer;

import java.io.*;

/**
 * Created by tomas on 6/21/2017.
 */
public class Saver {
    public static void save(){
        String home = System.getenv("APPDATA");
        File dir = new File(home, ".warframemarketutility").getAbsoluteFile();
        dir.mkdir();

        try{
            //save alarms
            File saveFile = new File(dir, "alarms.ser");
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(saveFile));
            oos.writeObject(AlarmTimer.getAlarmTimers());

            oos.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
