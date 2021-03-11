/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package loginappointment;

import java.io.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.sql.Connection;
import java.util.Scanner;
import utils.DBConnection;
import model.User;

/**
 *
 * @author ajw51
 */
/**This class creates an app that connects to a MySQL database. There are 2 additional lambdas here in the main method.*/
public class LoginAppointment extends Application {

    /**
     * This is the main method.The first method that gets called. There are two lambdas here that keep track and store all of the successful and failed login attempts.
     * @param args --.
     * @throws java.io.FileNotFoundException File Not Found Exception
     * 
     */
    public static void main(String[] args) throws IOException {
        // TODO code application logic here
        
    String filename = "login_activity.txt";
    Scanner keyboard = new Scanner(System.in);
    
    FileWriter fwriter = new FileWriter(filename, true);
    PrintWriter outputFile = new PrintWriter(fwriter);
        
        Connection conn = DBConnection.startConnection();
        
        launch(args);
        
        /**Lambda that keeps track and stores all of the successful login attempts. */
        PrintInterface message = s -> "Login Successes: " + s;
        outputFile.println(message.getMessage(User.getLog()));
       
        
        /**Lambda that keeps track and stores all of the failed login attempts. */
        FailedInterface failmessage = f -> "Login Fails: " + f;
        outputFile.println(failmessage.failmessage(User.failLog));
        
        outputFile.close();
        System.out.println("printed");
        
        
        DBConnection.closeConnection();
    }
    
        @Override
    /**Pulling up the Main Menu. */
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/view/LoginMenu.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Login Menu");
        stage.show();
    }
}
