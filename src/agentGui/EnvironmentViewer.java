/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agentGui;

import aslcore.ACLMessage;
import aslcore.MessageData;
import com.sun.corba.se.impl.io.InputStreamHook;
import com.sun.corba.se.impl.protocol.giopmsgheaders.Message;
import com.sun.org.apache.xerces.internal.parsers.XMLParser;
import core.Agent;
import core.htmlparser.HTMLParser;
import core.xmlparser.XmlMarshalDemarshal;
import core.xmlparser.XmlParser;
import dataAgent.AgentList;
import dataAgent.AgentListController;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.web.WebView;
import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.xml.bind.JAXBException;
import sun.awt.image.ToolkitImage;
import workagents.Truck;
import workagents.TruckList;

/**
 *
 * @author Stanislav
 */
public class EnvironmentViewer extends JFrame{
    private final HTMLParser parser;
    public java.net.URL htmlStrc;
    private JFXPanel jFXPanel;
    private WebView webView;

    public void initGUI() throws IOException, ClassNotFoundException, JAXBException{
        this.setBounds(100, 100, 1100, 600);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        htmlStrc = EnvironmentViewer.class.getResource("/maps.html");
        JTree tree;
        DefaultMutableTreeNode top = new DefaultMutableTreeNode("Environment");
        createNodes(top);
        tree = new JTree(top);
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.add(tree);
        JPanel p2 = new JPanel();
        p2.setBackground(Color.white);
        p2.setLayout((new BoxLayout(p2, BoxLayout.X_AXIS)));
        
        ImageIcon imageIcon = createImageIco("/truck.png");
        if(imageIcon!=null){
            
            DefaultTreeCellRenderer renderer = new DefaultTreeCellRenderer();
            renderer.setLeafIcon(imageIcon);
            tree.setCellRenderer(renderer);
        }
        
        jFXPanel = new JFXPanel();
        JScrollPane treeView = new JScrollPane(tree);
        //treeView.se
        p2.add(treeView);
        p2.add(javax.swing.Box.createRigidArea(new Dimension(0,10)));
        p2.add(jFXPanel);
        
        Container contentPane = getContentPane();
       // contentPane.add(p,BorderLayout.WEST);
        contentPane.add(p2,BorderLayout.NORTH);
        
        
        Platform.runLater(() -> {
            webView = new WebView();
            jFXPanel.setScene(new Scene(webView,800,600));
            webView.getEngine().load(htmlStrc.toString());
        });
        this.setVisible(true);
        
    };
    
    private void updateMaps(){
        Platform.runLater(() -> {
            webView.getEngine().reload();
        });
    }
    
    private void createNodes(DefaultMutableTreeNode top) throws IOException, ClassNotFoundException, JAXBException{
        DefaultMutableTreeNode agents = null;
        DefaultMutableTreeNode agent = null;
        TruckList agentlist = TruckList.getInstance();
        agents = new DefaultMutableTreeNode("Agents");
        
        
        Iterator<Truck> iterator = getAgents().iterator();
        String file="";
        while (iterator.hasNext()) {
            Truck next = iterator.next();
            if(next.getUID_agent().contains("#Truck"))
            {
                file = parser.parse(htmlStrc.getPath());
                file =parser.insertMarker(file, next.getCurrentLocation(), next.getAgentName(),next.getStatus(),next.getUID_agent());
                parser.saveHtmlFile(htmlStrc.getPath(), file);
                agent = new DefaultMutableTreeNode(next.getUID_agent());
                agents.add(agent);
            }
            
        }
        updateMaps();
        top.add(agents);
    }
    
    protected static ImageIcon createImageIco(String path){
        java.net.URL imgURL = EnvironmentViewer.class.getResource(path);
        if(imgURL!=null){
            ImageIcon ico = new ImageIcon(imgURL);
            Image image = ico.getImage();
            ToolkitImage toolkitImage = (ToolkitImage)image;
            BufferedImage bufferdImage = toolkitImage.getBufferedImage();
            BufferedImage resizedImage = resizeImage(bufferdImage, 25, 25);
            
            return new ImageIcon(resizedImage);
        }else{
            return null;
        }
    }
        
   
    public  static BufferedImage resizeImage(BufferedImage image, int width, int height) {
        ColorModel cm = image.getColorModel();
        WritableRaster raster = cm.createCompatibleWritableRaster(width, height);
        boolean isRasterPremultiplied = cm.isAlphaPremultiplied();
        BufferedImage target = new BufferedImage(cm, raster, isRasterPremultiplied, null);
        Graphics2D g2 = target.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        double scalex = (double) target.getWidth() / image.getWidth();
        double scaley = (double) target.getHeight() / image.getHeight();
        AffineTransform xform = AffineTransform.getScaleInstance(scalex, scaley);
        g2.drawRenderedImage(image, xform);
        g2.dispose();
        return target;
    }
    
    
    private ArrayList<Truck> getAgents() throws IOException, ClassNotFoundException, JAXBException{
        Socket socket = new Socket("localhost", 1234);
        MessageData msd = new MessageData();
        msd.setType(ACLMessage.TRUCK_LIST);
        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
        ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
        oos.writeObject(msd);
        msd = (MessageData)ois.readObject();
        XmlParser parser = new XmlMarshalDemarshal();
        TruckList list = (TruckList) parser.unmarhallParser(msd.getContent(), TruckList.class);
        oos.close();
        ois.close();
        socket.close();
        
        return list.getAgentList();
    }
    
    
    public EnvironmentViewer() throws HeadlessException {
        super("EnviromentViewer");
        this.parser = new HTMLParser();
    }
    
    
    
    
    public static void main(String[] args){
        SwingUtilities.invokeLater(() -> {
            EnvironmentViewer app = new EnvironmentViewer();
            try {
                app.initGUI();
            } catch (IOException ex) {
                Logger.getLogger(EnvironmentViewer.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(EnvironmentViewer.class.getName()).log(Level.SEVERE, null, ex);
            } catch (JAXBException ex) {
                Logger.getLogger(EnvironmentViewer.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }
    
}
