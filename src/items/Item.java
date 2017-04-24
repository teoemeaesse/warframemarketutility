package items;

import parser.DataParser;

import java.util.ArrayList;

/**
 * Created by tomas on 4/23/2017.
 */
public class Item {
    public static final String PLAYER_NAME = "ingame_name", ONLINE_INGAME = "online_ingame", ONLINE_INSITE = "online_status", PRICE = "price", QUANTITY = "count", ITEM_TYPE = "item_type", BASIC_ITEM_NAME = "basic_name", ITEM_NAME = "item_name";
    public static final int INGAME = 0, INSITE = 1, OFFLINE = 2;
    private ArrayList<Attribute> attributes = new ArrayList<>();
    public static final Item[] items = DataParser.parseRawItems();

    public Item(ArrayList<Attribute> attributes) {
        this.attributes = attributes;
    }

    public static void sortListings(Item[] listings){
        System.out.print("\nItem name: " + listings[0].getValue(Item.ITEM_NAME) +
                "\n\tCheapest online in-game seller: " + Item.getCheapestItem(listings, Item.INGAME).getValue(Item.PLAYER_NAME) + " at " + Item.getCheapestItem(listings, Item.INGAME).getValue(Item.PRICE) + "p" +
                "\n\tCheapest online in-site seller: " + Item.getCheapestItem(listings, Item.INSITE).getValue(Item.PLAYER_NAME) + " at " + Item.getCheapestItem(listings, Item.INSITE).getValue(Item.PRICE) + "p" +
                "\n\tCheapest offline seller: " + Item.getCheapestItem(listings, Item.OFFLINE).getValue(Item.PLAYER_NAME) + " at " + Item.getCheapestItem(listings, Item.OFFLINE).getValue(Item.PRICE) + "p" +
                "\n\n\tRecommended sell price: " + Math.max(Integer.parseInt(Item.getCheapestItem(listings, Item.INGAME).getValue(Item.PRICE)) - 1, 1) + "p" +
                "\n"
        );
    }

    public String getValue(String identifier){
        String value = null;

        for(Attribute a : attributes)
            if(a.getIdentifier().equals(identifier))
                value = a.getValue();

        return value;
    }
    public static Item getItem(String name){
        Item result = null;

        for(Item i : items){
            if(i.getValue(Item.ITEM_NAME).equals(name)) {
                result = i;
                break;
            }
        }

        return result;
    }
    public static Item getItemBasic(String basic){
        Item result = null;

        for(Item i : items){
            if(i.getValue(Item.BASIC_ITEM_NAME).equals(basic)){
                result = i;
                break;
            }
        }

        return result;
    }
    public static Item getCheapestItem(Item[] items, int onlineMode){
        Integer cheapestValue = null;
        Item cheapestItem = null;

        for(Item i : items){
            if(Boolean.parseBoolean(i.getValue(ONLINE_INSITE)) && onlineMode == INSITE || Boolean.parseBoolean(i.getValue(ONLINE_INGAME)) && onlineMode == INGAME || !Boolean.parseBoolean(i.getValue(ONLINE_INGAME)) && !Boolean.parseBoolean(i.getValue(ONLINE_INSITE)) && onlineMode == OFFLINE){
                cheapestItem = i;
                cheapestValue = Integer.parseInt(i.getValue(PRICE));
                break;
            }
        }
        for(Item i : items){
            if(i.getValue(PRICE) != null && Integer.parseInt(i.getValue(PRICE)) < cheapestValue){
                if(Boolean.parseBoolean(i.getValue(ONLINE_INSITE)) && onlineMode == INSITE || Boolean.parseBoolean(i.getValue(ONLINE_INGAME)) && onlineMode == INGAME || !Boolean.parseBoolean(i.getValue(ONLINE_INGAME)) && !Boolean.parseBoolean(i.getValue(ONLINE_INSITE)) && onlineMode == OFFLINE){
                    cheapestItem = i;
                    cheapestValue = Integer.parseInt(i.getValue(PRICE));
                }
            }
        }

        return cheapestItem;
    }

}