/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agentGui;

import dataAgent.order.Order;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;


/**
 *
 * @author Stanislav
 */
public class AgentOrderViewer extends Application{

    Browser browser;
    java.net.URL htmlStrc;
    
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        Order order = new Order();
        order.setCustomer("Stanislav");
        order.setOrderStatus(Order.NEW);
        ObservableList<Order> orders = FXCollections.observableArrayList(order);
        
        htmlStrc = TruckViewer.class.getResource("/mapsroute.html");
        
        TableView table = new TableView();
        table.setMaxWidth(400);
        table.setMinWidth(400);
        table.setMinHeight(450);
        
        TableColumn idOrder = new TableColumn("ID");
        idOrder.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        TableColumn customerOrder = new TableColumn("Customer");
        idOrder.setCellValueFactory(new PropertyValueFactory<>("customer"));
        TableColumn statusOrder = new TableColumn("Status");
        idOrder.setCellValueFactory(new PropertyValueFactory<Order,String>("orderStatus"));
        TableColumn truckOrder = new TableColumn("Truck");
        idOrder.setCellValueFactory(new PropertyValueFactory<Order,String>("customer"));
        
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
        primaryStage.show();
    }
    
    public static void main(String[] args){
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
            return 700;
        }

        @Override
        protected double computePrefHeight(double width) {
            return 600;
        }
    }
    
}
