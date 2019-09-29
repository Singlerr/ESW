package io.github.alpen;

import io.github.alpen.businfo.BusInfo;
import io.github.alpen.businfo.BusInfoRequest;
import io.github.alpen.socket.Client;
import io.github.alpen.socket.Socket;
import io.github.alpen.task.BusInfoUpdater;

import java.io.IOException;

public class Main {
    public static BusInfo busInfo;
    public static BusInfoUpdater updater;
    public static Client buttonClient;
    public static Socket socket;
    public static void main(String[] args){
        BusInfoRequest req = new BusInfoRequest("222000105");
        try{
            busInfo = new BusInfo(req.get());
        }catch (Exception ex){
            ex.printStackTrace();
        }
        updater = new BusInfoUpdater();
        socket = new Socket();
        try{
            socket.start();
        }catch (IOException ex){
            ex.printStackTrace();
        }
    }

}
