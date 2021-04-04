package com.cbp.orderbook;

import org.apache.commons.io.IOUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TestHelper {

    public static String create_snapshot(String fileName) throws IOException {
        FileInputStream fis = new FileInputStream("src/test/java/com/cbp/orderbook/testMessage/" + fileName);
        String snapshot = IOUtils.toString(fis, "UTF-8");
        return snapshot;
    }

    public static List<String> create_l2updates(String fileName) throws IOException {
        FileInputStream fis = new FileInputStream("src/test/java/com/cbp/orderbook/testMessage/" + fileName);
        String input = IOUtils.toString(fis, "UTF-8");
        String[] messages = input.split("\n");
        ArrayList<String> l2updates = new ArrayList<>();
        for (String message : messages) {
            l2updates.add(message);
        }
        return l2updates;
    }
}
