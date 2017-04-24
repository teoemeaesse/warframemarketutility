package app;

import items.Item;
import parser.DataParser;

import java.util.Scanner;

/**
 * Created by tomas on 4/23/2017.
 */
public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        out("Thank you for using the Warframe Market Utility. Input is not case-sensitive or have whitespaces.\n");

        while(true){
            out("\nEnter the item you're interested in: \n>> ");
            String input = in.nextLine();

            String name = null;
            Item[] listings;

            try{
                name = Item.getItemBasic(input.toLowerCase().replaceAll(" ", "")).getValue(Item.ITEM_NAME);
            }catch(NullPointerException e){
                out("\nItem not found\n");
            }

            if(name != null){
                listings = DataParser.parseRawMarketData(name);

                Item.sortListings(listings);
            }
        }
    }

    private static void out(String out){
        System.out.print(out);
    }
}
