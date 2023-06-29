package DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import Model.Account;
import Util.ConnectionUtil;
import java.sql.*;
import java.util.*;


public class AccountDAO {

     // 1. Process New User Registration (AccountDAO)
    public Account createAccount(Account newAccount) {
        Connection connection = ConnectionUtil.getConnection();
        String sql = "INSERT INTO account (username, password) VALUES (?, ?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, newAccount.getUsername());
            preparedStatement.setString(2, newAccount.getPassword());

            int rows = preparedStatement.executeUpdate();
            if (rows == 1) {
                ResultSet keys = preparedStatement.getGeneratedKeys();
                if (keys.next()) {
                    int id = keys.getInt(1);
                    newAccount.setAccount_id(id);
                    return newAccount;
                }
            }
        } catch (SQLException e) {
           System.out.println(e.getMessage());
        } 
        return null;
    }


    public Account getUsername(String name) {
        Connection connection = ConnectionUtil.getConnection();
        String sql = "SELECT * FROM account WHERE username = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, name);

            ResultSet results = preparedStatement.executeQuery();
            if (results.next()) {
                int idAccount = results.getInt("account_id");
                String password = results.getString("password");
                return new Account(idAccount, name, password);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } 
        return null;
    }

    public Account userWithPassword(String user, String password) {
        Connection connection = ConnectionUtil.getConnection();
        String sql = "SELECT * FROM account WHERE username = ? AND password = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, user);
            preparedStatement.setString(2, password);

            ResultSet results = preparedStatement.executeQuery();
            if (results.next()) {
                int idAccount = results.getInt("account_id");
                return new Account(idAccount, user, password);
            }
        } catch (SQLException e) {
           System.out.println(e.getMessage());
        } 
        return null;
    }    
}