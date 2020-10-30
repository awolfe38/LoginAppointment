/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.sql.*;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.MainCustTable;
import utils.DBConnection;

/**
 * FXML Controller class
 *
 * @author ajw51
 */
public class UserMenuController implements Initializable {

    @FXML
    private TableView<MainCustTable> customerTableView;

    @FXML
    private TableColumn<MainCustTable, Integer> custIdCol;

    @FXML
    private TableColumn<MainCustTable, String> custNameCol;

    @FXML
    private TableColumn<MainCustTable, String> custAddressCol;

    @FXML
    private TableColumn<MainCustTable, String> custPostCol;

    @FXML
    private TableColumn<MainCustTable, String> custPhoneCol;

    @FXML
    private Button addCustBtn;

    @FXML
    private Button updateCustBtn;

    @FXML
    private Button deleteCustBtn;

    @FXML
    private Button fullCustBtn;
    
        ObservableList<MainCustTable> oblist = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        try {
            Connection conn = DBConnection.getConnection();
            ResultSet rs = conn.createStatement().executeQuery("select * from customers");
            while(rs.next()){
                oblist.add(new MainCustTable(Integer.parseInt(rs.getString("Customer_ID")), rs.getString("Customer_Name"), rs.getString("Address"), rs.getString("Postal_Code"), rs.getString("Phone")));
            }
            
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        
        custIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        custNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        custAddressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        custPostCol.setCellValueFactory(new PropertyValueFactory<>("post"));
        custPhoneCol.setCellValueFactory(new PropertyValueFactory<>("phone"));
        
        customerTableView.setItems(oblist);

    }    
    
}