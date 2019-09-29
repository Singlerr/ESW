package io.github.alpen.businfo;



import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class BusInfoRequest {
    public static final String SERVICE_KEY = "GBwfbsMWwzjIST8K%2BymCOUt0lXZKHRtvW1FiTNkbQYBAos8z8DTvANPR2VTBMVuVNQiUjka17vb%2B41gvQEDF5A%3D%3D";
    private String url;
    private String result;
    public BusInfoRequest(String routeId) {
        try {
        StringBuilder urlBuilder = new StringBuilder("http://openapi.gbis.go.kr/ws/rest/buslocationservice");
        urlBuilder.append("?" + URLEncoder.encode("ServiceKey","UTF-8") + "="+SERVICE_KEY);
        urlBuilder.append("&" + URLEncoder.encode("routeId","UTF-8") + "=" + URLEncoder.encode(routeId, "UTF-8"));

            URL url = new URL("http://openapi.gbis.go.kr/ws/rest/buslocationservice?ServiceKey=" + SERVICE_KEY + "&routeId=" + routeId);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("Content-type", "application/json");
            BufferedReader reader;
            if (con.getResponseCode() >= 200 && con.getResponseCode() <= 300) {
                reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
            } else {
                reader = new BufferedReader(new InputStreamReader(con.getErrorStream()));
            }
            result = "";
            String line;
            while ((line = reader.readLine()) != null)
                result += line.trim();
            reader.close();
            con.disconnect();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public String get(){
        return result;
    }
    public BusInfoRequest(int routeId,String busId){
        try {
            URL url = new URL("http://openapi.gbis.go.kr/ws/rest/buslocationservice?ServiceKey=" + SERVICE_KEY + "&routeId=" + routeId);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("Content-type","application/json");
            BufferedReader reader;
            if(con.getResponseCode() >= 200 && con.getResponseCode() <= 300){
                reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
            }else{
                reader = new BufferedReader(new InputStreamReader(con.getErrorStream()));
            }
            result = "";
            String line;
            while((line = reader.readLine()) != null)
                result += line.trim();
            reader.close();
            con.disconnect();
        }catch (IOException ex){
            ex.printStackTrace();
        }
    }
}
