package app;

import data.AlarmTimer;
import data.Attribute;
import data.Item;
import data.Profile;
import parser.Parser;

import java.util.Scanner;

/**
 * Created by tomas on 4/23/2017.
 */
public class Main {
    private static final Scanner in = new Scanner(System.in);

    public static void main(String[] args) {
        init();

        out("Thank you for using the Warframe Market Utility. Input is not case-sensitive and does not need whitespaces.\n");

        while(true){
            out("\nEnter the item you want to search\n>> ");
            String input = in.nextLine().replaceAll(" ", "").toLowerCase();
            searchForItem(input);
        }
    }

    private static void init(){
       new AlarmTimer();
    }


    private static void searchForItem(String input){
        Item.sortListings(Parser.parseRawMarketData(input));
        out("\nSet alarm? (y/enter)\n>> ");
        String alarm = in.nextLine();
        if(alarm.replaceAll(" ", "").toLowerCase().equals("y"))
            createAlarm(input);
    }

    private static void createAlarm(String input){
        Item basic = Item.getItemBasic(input);
        boolean a = true;

        while(a){
            if(basic == null){
                out("\nItem not found\n");
                break;
            }
            out("\nPrice: \n>> ");
            input = in.nextLine();
            try{
                Integer.parseInt(input);
                basic.assignAttribute(new Attribute(Item.PRICE, input));
                a = false;
            }catch(NumberFormatException e){}

            a = true;
            while(a){
                out("\nMode: \n1. In-game\n2. In-site\n>> ");
                switch(in.nextLine()){
                    case "1":
                        basic.assignAttribute(new Attribute(Item.ONLINE_INGAME, "true"));
                        a = false;
                        break;
                    case "2":
                        basic.assignAttribute(new Attribute(Item.ONLINE_INGAME, "false"));
                        a = false;
                        break;
                }
            }
        }

        if(basic != null)
            Profile.listItem(basic);
    }

    private static void out(String out){
        System.out.print(out);
    }
}
