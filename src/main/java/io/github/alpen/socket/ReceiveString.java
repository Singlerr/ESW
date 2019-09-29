package io.github.alpen.socket;

import io.github.alpen.Main;

import java.nio.channels.AsynchronousSocketChannel;
import java.util.List;
import java.util.Map;

public class ReceiveString {

    private String receiveData;
    private Client client;

    public ReceiveString(String receiveData,Client client) {
        this.receiveData = receiveData;
        this.client  = client;
    }

    public void execute(){
        if(receiveData.startsWith("POST")){
            String[] split = receiveData.replace("POST_", "").split("_");
            String busCode = split[0];
            Map<String, Client> busMap = BusList.getBusMap();
           busMap.put(busCode,client);
        }else if(receiveData.startsWith("REG_BUTTON")){
            Main.buttonClient = client;
        }
    }
}
