package net;

import items.Item;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by tomas on 4/24/2017.
 */
public class Puller {
    public static String pullFromWebsite(String link) throws IOException {
        URL url = new URL(link);
        URLConnection con = url.openConnection();
        BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));

        String data = "", line;

        while ((line = br.readLine()) != null) {
            data += line;
        }

        return data;
    }

    public static String pullItemFromWebsite(Item item) throws IOException {
        return pullFromWebsite("http://warframe.market/api/get_orders/" + item.getValue(Item.ITEM_TYPE) + "/" + item.getValue(Item.ITEM_NAME).replaceAll(" ", "%20"));
    }
}
