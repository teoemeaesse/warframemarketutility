package parser;

import com.sun.istack.internal.Nullable;
import items.Attribute;
import items.Item;
import net.Puller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by tomas on 4/23/2017.
 */
public class DataParser {
    public static Item[] parseRawMarketData(String itemName){
        ArrayList<Item> items = new ArrayList<>();
        String data = "", sellData;
        try{data = Puller.pullItemFromWebsite(Item.getItem(itemName));}catch(IOException e){e.printStackTrace();}

        String[] splitData = data.split("\"buy\": \\[");
        sellData = splitData[0];

        String tempstr = "";
        for(int i = 35; i < sellData.length(); i++){
            if(sellData.charAt(i) == '}'){
                Item item = parseRawData("\"[a-z_]{1,}\":(\")?[A-Za-z0-9 '\\-\\&\\(\\)\"]{1,}(\")?", tempstr, itemName);
                items.add(item);
                tempstr = "";
                i += 3;
            }
            if(i < sellData.length())
                tempstr += sellData.charAt(i);
        }

        return items.toArray(new Item[items.size()]);
    }

    public static Item[] parseRawItems() {
        ArrayList<Item> items = new ArrayList<>();
        BufferedReader br = new BufferedReader(new InputStreamReader(DataParser.class.getResourceAsStream("rawitems.txt")));
        String rawData;

        int line = 0;
        try{
            while((rawData = br.readLine()) != null){
                items.add(parseRawData("\"[a-z_]{1,}\":\"[A-Za-z0-9 \\_'\\-\\&\\(\\)]{1,}\"", rawData, null));
                line++;
            }
        }catch(IOException e){e.printStackTrace();}

        return items.toArray(new Item[items.size()]);
    }

    private static Item parseRawData(String regex, String rawData, String itemName){
        ArrayList<Attribute> attributes = new ArrayList<>();
        String[] processedData = getRegex(regex, rawData);

        String tempstr = null;
        for(int i = 0; i < processedData.length; i++){
            String identifier = processedData[i].split(":")[0].replaceAll("\"", "").trim(),
            value = processedData[i].split(":")[1].replaceAll("\"", "").trim();

            if(identifier.equals(Item.ITEM_NAME)) tempstr = value.toLowerCase().replaceAll(" ", "");

            attributes.add(new Attribute(identifier, value));
        }

        if(itemName != null){
            attributes.add(new Attribute(Item.ITEM_NAME, Item.getItem(itemName).getValue(Item.ITEM_NAME)));
            attributes.add(new Attribute(Item.ITEM_TYPE, Item.getItem(itemName).getValue(Item.ITEM_TYPE)));
        }

        attributes.add(new Attribute(Item.BASIC_ITEM_NAME, tempstr));

        return new Item(attributes);
    }


    @Nullable private static String[] getRegex(String regex, String raw){
        ArrayList<String> processed = new ArrayList<>();
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(raw);

        while(matcher.find())
            if(matcher.group().length() != 0)
                processed.add(matcher.group().trim());

        return processed.toArray(new String[processed.size()]);
    }
}