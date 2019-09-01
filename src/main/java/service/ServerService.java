package service;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import model.Account;
import model.MessageToServer;

import java.sql.*;

import java.util.*;
import java.util.Date;

import lombok.extern.java.Log;
import model.ResponseFromServerToReceipient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ResponseBody;

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

    public @ResponseBody ResponseEntity refresh(String username) {
        List<ResponseFromServerToReceipient> messages = new LinkedList<>();
        String query =
                "SELECT * FROM chat_data WHERE receiver = " + "'" + username + "'";
        try {
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                ResponseFromServerToReceipient response = new ResponseFromServerToReceipient(
                        resultSet.getString(1),
                        resultSet.getString(4),
                        resultSet.getString(3)
                );
                messages.add(response);
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
            log.info("Error occured while refreshing");
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<List<ResponseFromServerToReceipient>>(messages, HttpStatus.OK);
    }

    public @ResponseBody ResponseEntity processRequest(MessageToServer messageToServer) {
        String from = messageToServer.getFrom();
        String to = messageToServer.getTo();
        String data = messageToServer.getData();
        if (checkUserValidity(from) && checkUserValidity(to)) {
            log.info("Users have been validated");
            String query = "INSERT INTO chat_data (sender, receiver, " +
                    "timestamp, data) VALUES (" + "'" + from + "'" + ", " + "'" + to + "'" +
                    ", " + "'" + new Timestamp(new Date().getTime()) + "'" + ", " +  "'" + data +  "'" + ")";
            try {
                statement.execute(query);
            } catch (SQLException exception) {
                exception.printStackTrace();
                log.info("Unable to send message. Please try again");
            }
            return new ResponseEntity("Message sent!", HttpStatus.OK);
        } else {
            log.info("Users " + from + " and/or " + to + " are invalid ");
            return new ResponseEntity(HttpStatus.NOT_FOUND);

        }
    }

    public @ResponseBody ResponseEntity newUser(Account account) {
        if (checkUserValidity(account.getUsername())) {
            return new ResponseEntity(HttpStatus.CONFLICT);
        } else {
            String query =
                    "INSERT INTO users_list (USERNAME) values " + "('" +
                            account.getUsername() + "')";
            try {
                statement.execute(query);
            } catch (SQLException exception) {
                log.info("Error while processing create request - " + query);
                exception.printStackTrace();
                return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        return new ResponseEntity<String>("Account successfully created!",
                HttpStatus.ACCEPTED);
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
