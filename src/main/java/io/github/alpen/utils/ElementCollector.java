package io.github.alpen.utils;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.List;
import java.util.Vector;
import java.util.stream.Collectors;

public class ElementCollector {
    public static Node get(NodeList nodeList,CollectFunc<Node> flag){
        Node node = null;
        for(int i = 0;i<nodeList.getLength();i++){
            Node e = nodeList.item(i);
            if(flag.isTrue(e)){
                node = e;
            }
        }
        return node;
    }
    public static List<Node> collect(NodeList nodeList,CollectFunc<Node> bool){
        List<Node> rt = new Vector<>();
            for(int i = 0;i<nodeList.getLength();i++){
                Node node = nodeList.item(i);
                if(bool.isTrue(node))
                    rt.add(node);
            }

        return rt;
    }
    public static List<Node> collect(Vector<Node> nodeList,CollectFunc<Node> bool){
        List<Node> rt = nodeList.stream().filter(x -> bool.isTrue(x)).collect(Collectors.toList());
        return rt;
    }
    @FunctionalInterface
    public interface CollectFunc<T>{
        boolean isTrue(T t);
    }
}
