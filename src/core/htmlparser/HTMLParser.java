/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core.htmlparser;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 *
 * @author Stanislav
 */
public class HTMLParser {

    public HTMLParser() {
        this.parseString = "";
    }

    public String getParseString() {
        return parseString;
    }

    public void setParseString(String parseString) {
        this.parseString = parseString;
    }
    private String parseString;
    
    public String parse(String path) throws IOException{
        String file = "";
        for(int i=3;i<path.length(); i++){
            file+=path.charAt(i);
        }
       return readUsingFiles(file);
    }
    
    public void saveHtmlFile(String filepath, String file){
        try(FileWriter writer = new FileWriter(filepath, false))
        {
            writer.write(file);
            writer.flush();
        }
        catch(IOException ex){
             
            System.out.println(ex.getMessage());
        } 
    }
    
    public String insertMarker(String file, String[] position, String name,String status,String truck_id){
        String tamplate_var = "\n var %s;\n<!--var-->";
        String template = "\n%s = new google.maps.Marker({\n" +
                                 "position: new google.maps.LatLng(%s,%s),\n" +
                                 "icon: \"truckmarker.png\",\n" +
                                 "map: map \n"+
                                 "});\n"+
                                 "var infowindow%s = new google.maps.InfoWindow({\n" +
                                    "    content: '%s<br><b>Status:</b>%s</br>'\n" +
                                    "  });\n" +
                                    "  infowindow%s.open(map,%s);\n" +
                "google.maps.event.addListener(%s,'click',function() {\n" +
                "    map.setZoom(13);\n" +
                "    map.setCenter(%s.getPosition());\n" +
                " });\n"+
                                 "<!--start-->\n";
        int search_pos_var = file.lastIndexOf("<!--var-->");
        
        int search_pos = file.lastIndexOf("<!--start-->");
        String result = "";
        for(int i=0; i<search_pos_var+10; i++){
            
            result+=file.charAt(i);
        }
        result+=String.format(tamplate_var,name);
        for(int i=search_pos_var+10; i<search_pos+12; i++){
            
            result+=file.charAt(i);
        }
        
        result+=String.format(template, name, position[0],position[1], name,name,status,name,name,name,name);
        for(int i=search_pos+12; i<file.length(); i++){
            
            result+=file.charAt(i);
        }
        
        return result;
    }
    
    public String backChange(String file){
        
        String result="";
        int first_pos_var = file.indexOf("<!--var-->");
        int second_pos_var = file.indexOf("<!--end-->");
        int first_pos = file.indexOf("<!--start-->");
        int second_pos = file.indexOf("<!--stop-->");
        
        for(int i = 0; i<first_pos_var+10; i++){
            result+=file.charAt(i);
        }
        for(int i = second_pos_var; i<first_pos+12; i++){
            result+=file.charAt(i);
        }
        for(int i = second_pos; i<file.length(); i++){
            result+=file.charAt(i);
        }
        return result;
    }
    
    private static String readUsingFiles(String fileName) throws IOException {
        return new String(Files.readAllBytes(Paths.get(fileName)));
    }
    
}
