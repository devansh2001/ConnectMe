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
        this.url = "jdbc:mysql://localhost:3306/ConnectMe";
        this.user = "root";
        this.password = "adminadmin";
        try {
            this.connection = DriverManager.getConnection(this.url);
            this.statement = connection.createStatement();
        } catch (SQLException exception) {
            log.info("Connection error while connecting to " + this.url);
        }
    }

    public void processRequest(MessageToServer messageToServer) {
        String from = messageToServer.getFrom();
        String to = messageToServer.getTo();
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
