package service;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import model.MessageToServer;

import java.sql.*;


import lombok.extern.java.Log;

@Log
@Slf4j
@Data
public class ServerService {
    private String url;
    private String user;
    private String password;
    private Connection connection;
    private Statement statement;

    public ServerService() {
        System.out.println("Hello");
        this.url = "jdbc:mysql://localhost:3306/ConnectMe?useSSL=false";
        this.user = "root";
        this.password = "adminadmin";
//        this.url += "user=" + this.user + "&password=" + this.password;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            this.connection = DriverManager.getConnection(this.url, this.user
                    , this.password);
            this.statement = connection.createStatement();
        } catch (SQLException exception) {
            log.info("Connection error while connecting to " + this.url);
        } catch (Exception exception) {
            log.info("Connection error while connecting to " + this.url + ". " +
                    "ClassNotFound");
        }
    }

    public void processRequest(MessageToServer messageToServer) {
        String from = messageToServer.getFrom();
        String to = messageToServer.getTo();
        System.out.println("Connection: " + this.connection);
        System.out.println("Statement: " + this.statement);
        if (checkUserValidity(from) && checkUserValidity(to)) {
            log.info("Would have written");
        }
        log.info("Nothing here");
    }

    private boolean checkUserValidity(String username) {
        String query =
                "SELECT * FROM users_list WHERE USERNAME = '" + username + "'";
        try {
            System.out.println(statement);
            System.out.println(connection);
            ResultSet resultSet = statement.executeQuery(query);
            log.info("Queried to check validity of user: " + username);
            if (resultSet.wasNull()) {
                return false;
            }
        } catch (SQLException exception) {
            log.info("Error while querying users_list for " + username);
            return false;
        }
        return true;
    }
}
