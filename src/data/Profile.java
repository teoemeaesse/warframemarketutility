package data;

import parser.Parser;

import java.util.ArrayList;

/**
 * Created by tomas on 4/25/2017.
 */
public class Profile {
    private static ArrayList<Item> alarmQueue = new ArrayList<>();

    public static void listItem(Item item){
        boolean queued = false;
        for(Item i : alarmQueue)
            if(i.getValue(Item.ITEM_NAME).equals(item.getValue(Item.ITEM_NAME)))
                queued = true;
        if(!queued){
            alarmQueue.add(item);
            System.out.print("\nQueued alarm for " + item.getValue(Item.ITEM_NAME) + "\n");
        }
        else
            System.out.print("\nAlarm already set for this item\n");
    }

    public static void checkForCheaperListings(){
        for(Item li : alarmQueue){
            Item[] comparedItems = null;
            try{comparedItems = Parser.parseRawMarketData(li.getValue(Item.ITEM_NAME).replaceAll(" ", "").toLowerCase());}catch(NullPointerException e){}
            if(comparedItems != null){
                boolean printAlert = false;

                for(Item ci : comparedItems){
                    if(Integer.parseInt(ci.getValue(Item.PRICE)) < Integer.parseInt(li.getValue(Item.PRICE)) && Boolean.parseBoolean(li.getValue(Item.ONLINE_INGAME)) == Boolean.parseBoolean(ci.getValue(Item.ONLINE_INGAME))){
                        printAlert = true;
                        break;
                    }
                }

                if(printAlert)
                    System.out.print("\n  Cheaper " + li.getValue(Item.ITEM_NAME) + " listed on the market by: \n");

                for(Item ci : comparedItems){
                    if(Integer.parseInt(ci.getValue(Item.PRICE)) < Integer.parseInt(li.getValue(Item.PRICE)) && Boolean.parseBoolean(li.getValue(Item.ONLINE_INGAME)) == Boolean.parseBoolean(ci.getValue(Item.ONLINE_INGAME))){
                        li.fireAlarm(ci);
                    }
                }
            }
        }
    }
}
