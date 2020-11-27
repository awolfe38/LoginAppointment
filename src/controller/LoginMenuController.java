/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import utils.DBConnection;
import utils.DBQuery;
import java.sql.*;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;

/**
 * FXML Controller class
 *
 * @author ajw51
 */
public class LoginMenuController implements Initializable {
    Stage stage;
    Parent scene;

    @FXML
    private TextField userTxt;
    @FXML
    private PasswordField passTxt;
    @FXML
    private Label usernameLbl;
    @FXML
    private Label passwordLbl;
    @FXML
    private Label locLbl;
    @FXML
    private Label locationLbl;
    @FXML
    private Button loginBtn;

    @FXML
    private Button exitBtn;
    
    @FXML
    private void onActionLogin(ActionEvent event) {
        String username = userTxt.getText();
        String password = passTxt.getText();

        try {
            Connection conn = DBConnection.getConnection();
            String selectStatement = "SELECT * FROM users WHERE User_Name=? AND Password=?";

            DBQuery.setPreparedStatement(conn, selectStatement);
            PreparedStatement ps = DBQuery.getPreparedStatement();

            ps.setString(1, username);
            ps.setString(2, password);

        ps.execute();

        ResultSet rs = ps.getResultSet();
        if (rs.next()) {
            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view/FullCustomerList.fxml"));
            stage.setScene(new Scene(scene));
            stage.setTitle("Customers");
            stage.show();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Login Error");
            alert.setContentText("Wrong username or password");
            if (!(Locale.getDefault().getLanguage().equals("en"))) {
                ResourceBundle resb = ResourceBundle.getBundle("resources/Nat", Locale.getDefault());
                if (Locale.getDefault().getLanguage().equals("fr")) {
                    alert.setTitle(resb.getString("loginerrortitle"));
                    alert.setContentText(resb.getString("loginerror"));
                }
            }
            alert.showAndWait();

            }
        } catch (Exception e) {

            System.out.println(e.getLocalizedMessage());
        }
    }

    @FXML
    private void onActionExit(ActionEvent event) {
        DBConnection.closeConnection();
        System.exit(0);
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        if (!(Locale.getDefault().getLanguage().equals("en"))) {
            ResourceBundle resb = ResourceBundle.getBundle("resources/Nat", Locale.getDefault());
            if (Locale.getDefault().getLanguage().equals("fr")) {
                usernameLbl.setText(resb.getString("Username") + ":");
                passwordLbl.setText(resb.getString("Password") + ":");
                loginBtn.setText(resb.getString("Login"));
                exitBtn.setText(resb.getString("Exit"));
                locationLbl.setText(resb.getString("Location") + ":");
            }
        }

        locLbl.setText(Locale.getDefault().getDisplayCountry());

    }


}
