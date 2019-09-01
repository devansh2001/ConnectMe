package service;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import model.MessageToServer;

import java.sql.*;


import lombok.extern.java.Log;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

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
        this.url = "jdbc:mysql://localhost:3306/ConnectMe?serverTimezone=UTC";
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
            exception.printStackTrace();
        } catch (Exception exception) {
            log.info("Connection error while connecting to " + this.url + ". " +
                    "ClassNotFound");
        }
    }

    public ResponseEntity processRequest(MessageToServer messageToServer) {
        String from = messageToServer.getFrom();
        String to = messageToServer.getTo();
        System.out.println("Connection: " + this.connection);
        System.out.println("Statement: " + this.statement);
        if (checkUserValidity(from) && checkUserValidity(to)) {
            log.info("Users have been validated");
            return new ResponseEntity(HttpStatus.OK);
        } else {
            log.info("Users " + from + " and/or " + to + " are invalid ");
            return new ResponseEntity(HttpStatus.NOT_FOUND);

        }
    }

    private boolean checkUserValidity(String username) {
        String query =
                "SELECT * FROM users_list WHERE USERNAME = '" + username + "'";
        try {
            ResultSet rs = statement.executeQuery(query);
//            System.out.println(resultSet);
            ResultSetMetaData rsmd = rs.getMetaData();
            int columnsNumber = rsmd.getColumnCount();
            while (rs.next()) {
                if (username.equals(rs.getString(1))) {
                    return true;
                }
            }
        } catch (SQLException exception) {
            log.info("Error while querying users_list for " + username);
            return false;
        }
        return false;
    }
}
