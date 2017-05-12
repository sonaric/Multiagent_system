/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package geodata;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 *
 * @author Stanislav
 */
public class GeoDistanceTime {
    
    public long getTime(String response){
        int part11 = response.indexOf("<duration>");
        int part12 = response.indexOf("</duration>");
        String duration = "";
        for(int i = part11+11; i<part12-4; i++){
            duration+=response.charAt(i);
        }
        String wordtime = "";
        int part21 = duration.indexOf("<value>");
        int part22 = duration.indexOf("</value>");
        
        for(int i = part21+7; i<part22; i++){
            wordtime+=duration.charAt(i);
        }
        return Long.valueOf(wordtime);
    }
    private String receiveResponse(String[] startPos, String[] stopPos) throws Exception {

        String url = "https://maps.googleapis.com/maps/api/distancematrix/xml?origins="+startPos[0]+","+startPos[1]+"&destinations="+stopPos[0]+","+stopPos[1]+"&key=AIzaSyDinrlloNLbTcoyOatO4rVVytmgW_nCteA";

        URL obj = new URL(url);
        
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        // optional default is GET
        con.setRequestMethod("GET");
        int responseCode = con.getResponseCode();

        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
            response.append('\n');
        }
        in.close();

        //print result
        return response.toString();

    }
    public static void main(String[] args) throws Exception{
        GeoDistanceTime gdt = new GeoDistanceTime();
        String str[]={"46.9629667","32.0031164"};
        String str2[]={"46.9778336","32.0815855"};
       
        System.out.println(gdt.getTime(gdt.receiveResponse(str, str2)));
    }
    
}
