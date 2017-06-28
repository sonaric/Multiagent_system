/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agentGui;

import core.AgentController;
import dataAgent.order.Order;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javax.xml.bind.JAXBException;
import workagents.OrderAgent;


/**
 *
 * @author Stanislav
 */
public class OrderAgentView extends Application{
    
    TextField customer_name = new TextField();
    TextField outputPoint = new TextField();
    TextField inputPoint = new TextField();
    TextField altPoint = new TextField();
    TextField volumeWeight = new TextField();
    DatePicker startDate = new DatePicker();
    DatePicker endDate = new DatePicker();
    OrderAgent oa;
    

    @Override
    public void start(Stage primaryStage) throws Exception {
        
        customer_name.setPromptText("ім'я замовника");
        outputPoint.setPromptText("адреса навантаження");
        inputPoint.setPromptText("адреса доставки");
        altPoint.setPromptText("альтернативна адреса");
        volumeWeight.setPromptText("об'ємна вага");
        startDate.setPromptText("дата навантаження");
        endDate.setPromptText("дата розвантаження");
        
        Label customView = new Label("Ім'я замовника:");
        Label outputView = new Label("Місце погрузки:");
        Label inputView = new Label("Адреса доставка:");
        Label alterView = new Label("Альтернативна адреса доставка:");
        Label startDataView = new Label("Дата і час погрузки:");
        Label endDataView = new Label("Дата і час доставки:");
        Label newOrder = new Label("      Оформлення замовлення");
        Label volumeView = new Label("Об'ємна вага:");
        newOrder.setId("title");
        Image img = new Image(this.getClass().getResource("..//logo.png").toString());
        ImageView logo = new ImageView(img);
        
        
        

        //volumeWeight.setAlignment(Pos.BASELINE_RIGHT);

        
        Button acceptOrder = new Button("Підтвердити");
        acceptOrder.setMinWidth(100);
        
        Button cancelOrder = new Button("Скасувати");
        cancelOrder.setId("cancel");
        HBox hb = new HBox(5);
        
        hb.getChildren().addAll(acceptOrder, cancelOrder);
        

        acceptOrder.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                oa = new OrderAgent();
                try {
                    oa.setSocket(new Socket("localhost", 1234));
                } catch (IOException ex) {
                   
                }
                try {
                    oa.setAgentName(String.valueOf(new Date().getTime())) ;
                } catch (UnknownHostException ex) {
                    
                }
                Order o = new Order();
                o.setCustomer(customer_name.getText());
                o.setOrderStatus(Order.NEW);
                oa.setOrder(o);
                AgentController ac = new AgentController(oa);
                try {
                    ac.setup();
                } catch (IOException | ClassNotFoundException | JAXBException ex) {
                    
                }
            }
        });
       // TetxField 
        
        GridPane root = new GridPane();
        root.setAlignment(Pos.CENTER);
        root.setHgap(10);
        root.setVgap(10);
        root.setId("pane");
        
        root.add(newOrder, 0, 0, 2, 1);
        root.add(customView, 0, 1);
        root.add(volumeView, 0, 2);
        root.add(outputView, 0, 3);
        root.add(inputView, 0, 4);
        root.add(alterView, 0, 5);
        root.add(startDataView, 0, 6);
        root.add(endDataView, 0, 8);
        
        root.add(customer_name, 1, 1);
        root.add(volumeWeight, 1, 2);
        root.add(outputPoint, 1, 3);
        root.add(inputPoint, 1, 4);
        root.add(altPoint, 1, 5);
        root.add(startDate, 1, 6);
        root.add(endDate, 1, 8);
        root.add(logo, 0, 11);
        root.add(hb, 1, 11);
        
        Scene scene = new Scene(root, 600, 400);
        scene.getStylesheets().addAll(this.getClass().getResource("..//orderViewStyle.css").toExternalForm());
        
        primaryStage.setTitle("Order Agent");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    
    public static void main(String[] args){
        launch(args);
    }
    
}
