/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agentGui;

import core.htmlparser.HTMLParser;
import dataAgent.order.Order;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;


/**
 *
 * @author Stanislav
 */
public class AgentOrderViewer extends Application{

    Browser browser;
    java.net.URL htmlStrc;
    HTMLParser htmlprsr = new HTMLParser();
    String file;
    
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        Order order = new Order();
        order.setCustomer("Stanislav");
        order.setOrderStatus(Order.NEW);
        order.setOrderTruck("/-/-/-/");
        order.setOutputPoint(new double[]{46.9691248,31.9555721});
        order.setInputPoint(new double[]{46.9676322,31.981582});
        Order order2 = new Order();
        order2.setCustomer("Svitlana");
        order2.setOrderStatus(Order.INPROCESS);
        order2.setOrderTruck("truck1");
        order2.setOutputPoint(new double[]{47.0130766,31.9960969});
        order2.setInputPoint(new double[]{46.9447711,32.0364936});
        ObservableList<Order> orders = FXCollections.observableArrayList(order, order2);
        
        htmlStrc = TruckViewer.class.getResource("/mapsroute.html");
        
        TableView table = new TableView();
        table.setMaxWidth(400);
        table.setMinWidth(400);
        table.setMinHeight(450);
        
        TableColumn idOrder = new TableColumn("ID");
        idOrder.setCellValueFactory(new PropertyValueFactory<Order,Double>("orderId"));
        TableColumn customerOrder=  new TableColumn("Customer");
        customerOrder.setCellValueFactory(new PropertyValueFactory<>("customer"));
        TableColumn statusOrder = new TableColumn("Status");
        statusOrder.setCellValueFactory(new PropertyValueFactory<Order,String>("orderStatus"));
        TableColumn truckOrder = new TableColumn("Truck");
        truckOrder.setCellValueFactory(new PropertyValueFactory<Order,String>("orderTruck"));
        
        table.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                try {
                    showRoute((Order)newValue);
                } catch (IOException ex) {
                  
                }
            }
        });
        
        idOrder.setMinWidth(100);
        customerOrder.setMinWidth(100);
        statusOrder.setMinWidth(98);
        truckOrder.setMinWidth(100);
        
        table.getColumns().addAll(idOrder,customerOrder,statusOrder,truckOrder);
        
        table.setItems(orders);
        
        Image img = new Image(this.getClass().getResource("..//logo.png").toString());
        ImageView logo = new ImageView(img);
        
        TextField searchField = new TextField();
        searchField.setPromptText("Search");
        Button searchBtn = new Button("Пошук");
        ComboBox filter = new ComboBox();
        filter.getItems().addAll("Id","Customer","Status","Truck");
        filter.setMinWidth(150);
        filter.setValue("Id");
        searchBtn.setMinWidth(100);
        
        HBox hb = new HBox(10);
        hb.getChildren().addAll(searchField,searchBtn,filter);
        
        browser = new Browser(htmlStrc.toString());
        
        Insets intents = new Insets(10);
        
        GridPane root = new GridPane();
        root.setAlignment(Pos.CENTER);
        root.setHgap(10);
        root.setVgap(10);
        root.setPadding(intents);
        root.setId("panelview");
        
        root.add(table, 0, 0);
        root.add(browser,1,0,1,3);
        root.add(logo,0,2);
        root.add(hb, 0, 1);
        
        Scene scene = new Scene(root, 1100, 600);
   
        scene.getStylesheets().add(this.getClass().getResource("..//truckViewStyle.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                file = htmlprsr.backChangeRoute(file);
                htmlprsr.saveHtmlFile(htmlStrc.getPath(), file);
            }
        });
        primaryStage.show();
    }
    
    public static void main(String[] args){
        launch(args);
    }
    
    private void showRoute(Order order) throws IOException{
        String[] positionA = {String.valueOf(order.getOutputPoint()[0]), String.valueOf(order.getOutputPoint()[1])};
        String[] positionB = {String.valueOf(order.getInputPoint()[0]), String.valueOf(order.getInputPoint()[1])};
        file = htmlprsr.parse(htmlStrc.getPath());
        file = htmlprsr.backChangeRoute(file);
        htmlprsr.saveHtmlFile(htmlStrc.getPath(), file);
        file = htmlprsr.viewRoute(file, positionA, positionB);
        htmlprsr.saveHtmlFile(htmlStrc.getPath(), file);
        browser.update();
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
            return 700;
        }

        @Override
        protected double computePrefHeight(double width) {
            return 600;
        }
    }
    
}
