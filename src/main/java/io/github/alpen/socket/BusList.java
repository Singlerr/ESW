package io.github.alpen.socket;

import java.nio.channels.AsynchronousChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.util.List;
import java.util.Map;

public class BusList {

    private static BusList list = new BusList();

    private Map<String, Client> busList;

    private BusList(){

    }

    public static Map<String,Client> getBusMap() {
        return list.busList;
    }
}
