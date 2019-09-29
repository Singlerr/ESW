package io.github.alpen.task;

import io.github.alpen.Main;
import io.github.alpen.businfo.BusInfo;
import io.github.alpen.socket.BusList;
import io.github.alpen.socket.Client;
import io.github.alpen.utils.ElementCollector;
import org.w3c.dom.Node;

import java.util.List;
import java.util.stream.Collectors;

public class BusInfoUpdater extends Thread{
    @Override
    public void run() {
        try{
            Main.busInfo.update();
            if(Main.buttonClient != null) {
                List<Node> nodes = Main.busInfo.getBuses();
                List<Node> currentBusCodes = nodes.stream().filter(node -> ElementCollector.get(node.getChildNodes(),n -> n.getNodeName().equals("stationId")).getNodeValue().equals("207000546")).collect(Collectors.toList());
                List<Node> busCodes = nodes.stream().filter(node -> ElementCollector.get(node.getChildNodes(),n -> n.getNodeName().equals("stationId")).getNodeValue().equals("207000158")).collect(Collectors.toList());
                if(! busCodes.isEmpty()){
                    busCodes.stream().forEach(n -> {
                        String busCode = ElementCollector.get(n.getChildNodes(),k -> k.getNodeName().equals("plateNo")).getNodeValue();
                        if(BusList.getBusMap().containsKey(busCode)){
                            Client client = BusList.getBusMap().get(busCode);
                            client.send("NOTICE");
                        }
                    });
                }
                if (!currentBusCodes.isEmpty()) {
                    if (Main.buttonClient != null)
                        Main.buttonClient.send("RESET_BUTTON");

                    currentBusCodes.stream().forEach(n -> {
                        String busCode = ElementCollector.get(n.getChildNodes(), k -> k.getNodeName().equals("plateNo")).getNodeValue();
                        if (BusList.getBusMap().containsKey(busCode)) {
                            Client client = BusList.getBusMap().get(busCode);
                            client.send("RESET_LED");
                        }
                    });
                    Main.buttonClient = null;

                }
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
}
