/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agentGui;

import static agentGui.EnvironmentViewer.resizeImage;
import aslcore.ACLMessage;
import aslcore.MessageData;
import core.htmlparser.HTMLParser;
import core.xmlparser.XmlMarshalDemarshal;
import core.xmlparser.XmlParser;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker.State;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javax.swing.ImageIcon;
import javax.xml.bind.JAXBException;
import sun.awt.image.ToolkitImage;
import workagents.Truck;
import workagents.TruckList;

/**
 *
 * @author Stanislav
 */
public class TruckViewer extends Application {

    Browser browser;
    HTMLParser parser = new HTMLParser();
    java.net.URL htmlStrc;
    Button btn;
    EventHandler<Event> handle;
    String file = "";

    @Override
    public void start(Stage primaryStage) throws IOException, ClassNotFoundException, JAXBException {
        htmlStrc = TruckViewer.class.getResource("/maps.html");

        TreeItem<String> rootItem = new TreeItem<>("Trucks");
        rootItem.setExpanded(true);

        TreeView<String> tree = new TreeView<String>(rootItem);

        createNodes(rootItem);

        tree.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<TreeItem<String>>() {
            @Override
            public void changed(ObservableValue<? extends TreeItem<String>> observable, TreeItem<String> oldValue, TreeItem<String> newValue) {
                TreeItem<String> selectedItem = newValue;
                if (newValue.getValue().indexOf("Trucks") < 0) {
                    int pos_sharp = newValue.getValue().indexOf("#");
                    String truck_name = "";
                    for (int i = 0; i < pos_sharp; i++) {
                        truck_name += newValue.getValue().charAt(i);
                    }
                    browser.webEngine.executeScript("centerMarker(map," + truck_name + ")");
                } else {
                    browser.webEngine.executeScript("centerMarker(map,homemarker)");
                }
            }
        });

        btn = new Button();
        btn.setText("Update");
        btn.setMinWidth(250);
        btn.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                browser.update();
            }
        });

        GridPane root = new GridPane();
        root.setAlignment(Pos.CENTER);
        root.setHgap(10);
        root.setVgap(10);
        root.setPadding(new Insets(10, 10, 10, 10));
        root.add(tree, 0, 0);
        root.add(btn, 0, 1);

        browser = new Browser(htmlStrc.toString());
        root.add(browser, 1, 0, 2, 3);

        Scene scene = new Scene(root, 1100, 600);

        primaryStage.setTitle("TruckViewer");
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                file = parser.backChange(file);
                parser.saveHtmlFile(htmlStrc.getPath(), file);
            }
        });
    }

    private EventHandler<Event> buttonClick() {
        EventHandler<Event> event = (Event event1) -> {
            browser.update();
            event1.consume();
        };
        return event;
    }

    void createNodes(TreeItem<String> rootItem) throws IOException, ClassNotFoundException, JAXBException {
        Iterator<Truck> iterator = getAgents().iterator();
        Node node;
        while (iterator.hasNext()) {
            Truck next = iterator.next();
            file = parser.parse(htmlStrc.getPath());
            file = parser.insertMarker(file, next.getCurrentLocation(), next.getAgentName(), next.getStatus(), next.getUID_agent());
            parser.saveHtmlFile(htmlStrc.getPath(), file);
            TreeItem<String> item = new TreeItem<String>(next.getUID_agent(), new ImageView(createImageIco("/truck.png")));
            rootItem.getChildren().add(item);
        }
    }

    private ArrayList<Truck> getAgents() throws IOException, ClassNotFoundException, JAXBException {
        Socket socket = new Socket("localhost", 1234);
        MessageData msd = new MessageData();
        msd.setType(ACLMessage.TRUCK_LIST);
        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
        ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
        oos.writeObject(msd);
        msd = (MessageData) ois.readObject();
        XmlParser parser = new XmlMarshalDemarshal();
        TruckList list = (TruckList) parser.unmarhallParser(msd.getContent(), TruckList.class);
        oos.close();
        ois.close();
        socket.close();

        return list.getAgentList();
    }

    protected static Image createImageIco(String path) {
        java.net.URL imgURL = EnvironmentViewer.class.getResource(path);
        if (imgURL != null) {
            ImageIcon ico = new ImageIcon(imgURL);
            java.awt.Image image = ico.getImage();
            ToolkitImage toolkitImage = (ToolkitImage) image;
            BufferedImage bufferdImage = toolkitImage.getBufferedImage();
            BufferedImage resizedImage = resizeImage(bufferdImage, 25, 25);

            return SwingFXUtils.toFXImage(resizedImage, null);
        } else {
            return null;
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    class Browser extends Region {

        final WebView browser = new WebView();
        public final WebEngine webEngine = browser.getEngine();
        private final String filePath;

        public Browser(String filePath) {
            this.filePath = filePath;

            //apply the styles
            getStyleClass().add("browser");
            // load the web page
            webEngine.load(this.filePath);
            //add the web view to the scene
            getChildren().add(browser);

        }

        private Node createSpacer() {
            Region spacer = new Region();
            HBox.setHgrow(spacer, Priority.ALWAYS);
            return spacer;
        }

        public void update() {
            webEngine.reload();
        }

        @Override
        protected void layoutChildren() {
            double w = getWidth();
            double h = getHeight();
            layoutInArea(browser, 0, 0, w, h, 0, HPos.CENTER, VPos.CENTER);
        }

        @Override
        protected double computePrefWidth(double height) {
            return 900;
        }

        @Override
        protected double computePrefHeight(double width) {
            return 600;
        }
    }
}
