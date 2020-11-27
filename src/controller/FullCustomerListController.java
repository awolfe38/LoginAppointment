/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import model.FullCustTable;
import utils.DBConnection;

/**
 * FXML Controller class
 *
 * @author ajw51
 */
public class FullCustomerListController implements Initializable {
    
    ObservableList<FullCustTable> oblist = FXCollections.observableArrayList();
    ObservableList<String> countries = FXCollections.observableArrayList("United States", "Canada", "United Kingdom");

    @FXML
    private TableView<FullCustTable> customerTableView;

    @FXML
    private TableColumn<FullCustTable, Integer> custIdCol;

    @FXML
    private TableColumn<FullCustTable, String> custNameCol;

    @FXML
    private TableColumn<FullCustTable, String> custAddressCol;

    @FXML
    private TableColumn<FullCustTable, String> custPostCol;

    @FXML
    private TableColumn<FullCustTable, String> custPhoneCol;

    @FXML
    private TableColumn<FullCustTable, String> custCreatedOnCol;

    @FXML
    private TableColumn<FullCustTable, String> custCreateCol;

    @FXML
    private TableColumn<FullCustTable, String> custUpdateCol;

    @FXML
    private TableColumn<FullCustTable, String> custLastUpdateCol;

    @FXML
    private TableColumn<FullCustTable, Integer> custDivisionCol;

    @FXML
    private TextField addName;

    @FXML
    private TextField addAddress;

    @FXML
    private TextField addPostal;

    @FXML
    private TextField addPhone;

    @FXML
    private ComboBox<String> addCountry;

    @FXML
    private ComboBox<String> addDivision;

    @FXML
    private TextField updateName;

    @FXML
    private TextField updateAddress;

    @FXML
    private TextField updateCode;

    @FXML
    private TextField updatePhone;

    @FXML
    private ComboBox<String> updateCountry;

    @FXML
    private ComboBox<String> updateDivision;

    @FXML
    void onActionAdd(ActionEvent event) {
        int did = 0;
        int cid = 0;
        String country = addCountry.getSelectionModel().getSelectedItem();
        String us = "United States";
        String uk = "United Kingdom";
        String can = "Canada";
        
        if(country.equals(us)) {
            cid = 231;
        } else if(country.equals(uk)) {
            cid = 230;
        } else if(country.equals(can)) {
            cid = 38;
        }
        
        try {
            Connection conn = DBConnection.getConnection();

            ResultSet rs2 = conn.createStatement().executeQuery("select * from first_level_divisions where Division = '" + addDivision.getSelectionModel().getSelectedItem() + "' "
            + "and COUNTRY_ID = '" + cid + "'");
            while (rs2.next()) {
            did = rs2.getInt("Division_ID");
            }
            
            
            PreparedStatement ps2 = conn.prepareStatement("insert into customers"
                    + " (Customer_Name, Address, Postal_Code, Phone, Created_By, Last_Updated_By, Division_ID)"
                    + " values(?, ?, ?, ?, 'admin', 'admin', ?)");
            
            ps2.setString(1, addName.getText());
            ps2.setString(2, addAddress.getText());
            ps2.setString(3, addPostal.getText());
            ps2.setString(4, addPhone.getText());
            ps2.setInt(5, did);


            ps2.execute();
            
            oblist.clear();
            ResultSet rs = conn.createStatement().executeQuery("select * from customers");
            while (rs.next()) {
                oblist.add(new FullCustTable(Integer.parseInt(rs.getString("Customer_ID")), rs.getString("Customer_Name"), rs.getString("Address"), rs.getString("Postal_Code"), rs.getString("Phone"),
                        rs.getString("Create_Date"), rs.getString("Created_By"), rs.getString("Last_Update"), rs.getString("Last_Updated_By"), Integer.valueOf(rs.getString("Division_ID"))));
            }
            
            System.out.println("Insert complete!");

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    void onActionUpdate(ActionEvent event) {
        int did = 0;
        int cid = 0;
        int custid = customerTableView.getSelectionModel().getSelectedItem().getId();
        String country = updateCountry.getSelectionModel().getSelectedItem();
        String us = "United States";
        String uk = "United Kingdom";
        String can = "Canada";
        
        if(country.equals(us)) {
            cid = 231;
        } else if(country.equals(uk)) {
            cid = 230;
        } else if(country.equals(can)) {
            cid = 38;
        }
        
        try {
            Connection conn = DBConnection.getConnection();

            ResultSet rs2 = conn.createStatement().executeQuery("select * from first_level_divisions where Division = '" + updateDivision.getSelectionModel().getSelectedItem() + "' "
            + "and COUNTRY_ID = '" + cid + "'");
            while (rs2.next()) {
            did = rs2.getInt("Division_ID");
            }
            
            
            PreparedStatement ps2 = conn.prepareStatement("update customers set Customer_Name = ?, Address = ?, Postal_Code = ?, Phone = ?, Division_ID = ?"
                    + " where Customer_ID = " + custid);
            
            ps2.setString(1, updateName.getText());
            ps2.setString(2, updateAddress.getText());
            ps2.setString(3, updateCode.getText());
            ps2.setString(4, updatePhone.getText());
            ps2.setInt(5, did);


            ps2.execute();
            
            oblist.clear();
            ResultSet rs = conn.createStatement().executeQuery("select * from customers");
            while (rs.next()) {
                oblist.add(new FullCustTable(Integer.parseInt(rs.getString("Customer_ID")), rs.getString("Customer_Name"), rs.getString("Address"), rs.getString("Postal_Code"), rs.getString("Phone"),
                        rs.getString("Create_Date"), rs.getString("Created_By"), rs.getString("Last_Update"), rs.getString("Last_Updated_By"), Integer.valueOf(rs.getString("Division_ID"))));
            }
            
            customerTableView.getSelectionModel().select(custid - 1);
            
            System.out.println("Update complete!");

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    @FXML
    void onClickedCustomer(MouseEvent event) {
        int x = customerTableView.getSelectionModel().getSelectedItem().getDivision();
        
        updateName.setText(customerTableView.getSelectionModel().getSelectedItem().getName());
        updateAddress.setText(customerTableView.getSelectionModel().getSelectedItem().getAddress());
        updateCode.setText(customerTableView.getSelectionModel().getSelectedItem().getPost());
        updatePhone.setText(customerTableView.getSelectionModel().getSelectedItem().getPhone());
        
        if(x < 1000) {
            updateCountry.setValue("Canada");
        } else if(x < 3919) {
            updateCountry.setValue("United Kingdom");
        } else {
            updateCountry.setValue("United States");
        }
        
        try {
                Connection conn = DBConnection.getConnection();
                ResultSet rs = conn.createStatement().executeQuery("select * from first_level_divisions where Division_ID = " + x);
                while (rs.next()) {
                    updateDivision.setValue(rs.getString("Division"));
                }

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        
    }

    @FXML
    void onActionDelete(ActionEvent event) {
        int custid = customerTableView.getSelectionModel().getSelectedItem().getId();

        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement("delete from customers where Customer_ID = " + custid);

            ps.execute();

            oblist.clear();
            ResultSet rs = conn.createStatement().executeQuery("select * from customers");
            while (rs.next()) {
                oblist.add(new FullCustTable(Integer.parseInt(rs.getString("Customer_ID")), rs.getString("Customer_Name"), rs.getString("Address"), rs.getString("Postal_Code"), rs.getString("Phone"),
                        rs.getString("Create_Date"), rs.getString("Created_By"), rs.getString("Last_Update"), rs.getString("Last_Updated_By"), Integer.valueOf(rs.getString("Division_ID"))));
            }
            
            customerTableView.getSelectionModel().select(0);

            System.out.println("Deletion Complete!");

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        try {
            Connection conn = DBConnection.getConnection();
            ResultSet rs = conn.createStatement().executeQuery("select * from customers");
            while (rs.next()) {
                oblist.add(new FullCustTable(Integer.parseInt(rs.getString("Customer_ID")), rs.getString("Customer_Name"), rs.getString("Address"), rs.getString("Postal_Code"), rs.getString("Phone"),
                        rs.getString("Create_Date"), rs.getString("Created_By"), rs.getString("Last_Update"), rs.getString("Last_Updated_By"), Integer.valueOf(rs.getString("Division_ID"))));
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        custIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        custNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        custAddressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        custPostCol.setCellValueFactory(new PropertyValueFactory<>("post"));
        custPhoneCol.setCellValueFactory(new PropertyValueFactory<>("phone"));
        custCreatedOnCol.setCellValueFactory(new PropertyValueFactory<>("createDate"));
        custCreateCol.setCellValueFactory(new PropertyValueFactory<>("createdBy"));
        custUpdateCol.setCellValueFactory(new PropertyValueFactory<>("lastUpdate"));
        custLastUpdateCol.setCellValueFactory(new PropertyValueFactory<>("updatedBy"));
        custDivisionCol.setCellValueFactory(new PropertyValueFactory<>("division"));

        customerTableView.setItems(oblist);
        customerTableView.getSelectionModel().select(0);

        addCountry.setItems(countries);
        updateCountry.setItems(countries);
    }

    public void addCountryChanged(ActionEvent event) {
        addDivision.getItems().clear();

        String country = addCountry.getSelectionModel().getSelectedItem();
        String us = "United States";
        String uk = "United Kingdom";
        String can = "Canada";

        if (country.equals(can)) {

            try {
                Connection conn = DBConnection.getConnection();
                ResultSet rs = conn.createStatement().executeQuery("select * from first_level_divisions where COUNTRY_ID = 38");
                while (rs.next()) {
                    addDivision.getItems().addAll(rs.getString("Division"));
                }

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

        } else if (country.equals(us)) {

            try {
                Connection conn = DBConnection.getConnection();
                ResultSet rs = conn.createStatement().executeQuery("select * from first_level_divisions where COUNTRY_ID = 231");
                while (rs.next()) {
                    addDivision.getItems().addAll(rs.getString("Division"));
                }

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

        } else if (country.equals(uk)) {

            try {
                Connection conn = DBConnection.getConnection();
                ResultSet rs = conn.createStatement().executeQuery("select * from first_level_divisions where COUNTRY_ID = 230");
                while (rs.next()) {
                    addDivision.getItems().addAll(rs.getString("Division"));
                }

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

        }

    }

    public void updateCountryChanged(ActionEvent event) {
        updateDivision.getItems().clear();

        String country = updateCountry.getSelectionModel().getSelectedItem();
        String us = "United States";
        String uk = "United Kingdom";
        String can = "Canada";

        if (country.equals(can)) {

            try {
                Connection conn = DBConnection.getConnection();
                ResultSet rs = conn.createStatement().executeQuery("select * from first_level_divisions where COUNTRY_ID = 38");
                while (rs.next()) {
                    updateDivision.getItems().addAll(rs.getString("Division"));
                }

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

        } else if (country.equals(us)) {

            try {
                Connection conn = DBConnection.getConnection();
                ResultSet rs = conn.createStatement().executeQuery("select * from first_level_divisions where COUNTRY_ID = 231");
                while (rs.next()) {
                    updateDivision.getItems().addAll(rs.getString("Division"));
                }

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

        } else if (country.equals(uk)) {

            try {
                Connection conn = DBConnection.getConnection();
                ResultSet rs = conn.createStatement().executeQuery("select * from first_level_divisions where COUNTRY_ID = 230");
                while (rs.next()) {
                    updateDivision.getItems().addAll(rs.getString("Division"));
                }

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

        }
    }

}
