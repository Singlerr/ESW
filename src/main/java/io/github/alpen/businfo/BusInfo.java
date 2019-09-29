package io.github.alpen.businfo;

import io.github.alpen.utils.ElementCollector;
import io.github.alpen.utils.ElementTransformulator;
import org.json.simple.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.StringReader;
import java.util.List;
import java.util.stream.Collectors;

public class BusInfo {
    private Document res;
    private List<Node> busCodes;
    private List<Node> buses;
    public BusInfo(String response) throws ParserConfigurationException, IOException, SAXException {

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = factory.newDocumentBuilder();
        InputSource is = new InputSource(new StringReader(response));
        res = documentBuilder.parse(is);
        Element root = res.getDocumentElement();
        NodeList list = root.getChildNodes();
        Element busloc = (Element) ElementCollector.collect(list,x -> x.getNodeType() == Node.ELEMENT_NODE).stream().filter(n -> ((Element) n).getNodeName().equals("msgBody")).findAny().get();
         buses = ElementCollector.collect(busloc.getChildNodes(),x -> x.getNodeType() == Node.ELEMENT_NODE).stream().filter(n -> n.getNodeName().equals("busLocationList")).collect(Collectors.toList());
        busCodes = buses.stream().filter(n -> n.getNodeName().equals("plateNo")).collect(Collectors.toList());

    }

    public List<Node> getBuses() {
        return buses;
    }

    public List<Node> getBusCodes() {
        return busCodes;
    }
    public void update() throws ParserConfigurationException, IOException, SAXException {
        BusInfoRequest req = new BusInfoRequest("222000105");
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = factory.newDocumentBuilder();
        InputSource is = new InputSource(new StringReader(req.get()));
        res = documentBuilder.parse(is);
        Element root = res.getDocumentElement();
        NodeList list = root.getChildNodes();
        Element busloc = (Element) ElementCollector.collect(list,x -> x.getNodeType() == Node.ELEMENT_NODE).stream().filter(n -> ((Element) n).getNodeName().equals("msgBody")).findAny().get();
        List<Node> buses = ElementCollector.collect(busloc.getChildNodes(),x -> x.getNodeType() == Node.ELEMENT_NODE).stream().filter(n -> n.getNodeName().equals("busLocationList")).collect(Collectors.toList());
        busCodes = buses.stream().filter(n -> n.getNodeName().equals("plateNo")).collect(Collectors.toList());
    }
}
