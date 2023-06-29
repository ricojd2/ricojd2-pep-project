package DAO;

import java.sql.*;
import java.util.List;
import java.util.ArrayList;
import Model.Message;
import Util.ConnectionUtil;
import java.util.*;

public class MessageDAO {
   
    
        // 3. Process New Message Creation (MessageDAO) 
            public Message messageCreation(Message message) {
              Connection connection = ConnectionUtil.getConnection();
              String sql = "INSERT INTO message (posted_by, message_text, time_posted_epoch) VALUES (?, ?, ?)";

                try {
                    PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                    preparedStatement.setInt(1, message.getPosted_by());
                    preparedStatement.setString(2, message.getMessage_text());
                    preparedStatement.setLong(3, message.getTime_posted_epoch());
                    preparedStatement.executeUpdate();
        
                    ResultSet keys = preparedStatement.getGeneratedKeys();
                    if (keys.next()) {
                        int messageId = keys.getInt(1);
                        message.setMessage_id(messageId);
                        return message;
                    } else {
                        return null; 
                    }
                } catch(SQLException e) {
                    System.out.println(e.getMessage());
                    return null;
                }
            }

          // 4. Retrieve All Messages (MessageDAO) 
          public List<Message> getAllMessageList() {
            Connection connection = ConnectionUtil.getConnection();
            List<Message> message = new ArrayList<>();
            String sql = "SELECT * FROM message";

            try {
                PreparedStatement preparedStatement = connection.prepareStatement(sql);

                ResultSet results = preparedStatement.executeQuery();
                while(results.next()){
                    Message messageto = new Message(results.getInt("message_id"), results.getInt("posted_by"), results.getString("message_text"), results.getLong("time_posted_epoch"));
                    message.add(messageto);  
                }
              } catch(SQLException e){
                System.out.println(e.getMessage());
            }
                return message;
                }


          // 5. Retrieve Messages By ID (MessageDAO)
        public Message getMessageByTheId(int id) {
          Connection connection = ConnectionUtil.getConnection();
          String sql = "SELECT * FROM message WHERE message_id = ?";
  
          try {
              PreparedStatement preparedStatement = connection.prepareStatement(sql);
              preparedStatement.setInt(1, id);
             
              ResultSet results = preparedStatement.executeQuery();
                if (results.next()) {
                    Message message = new Message();
                    message.setMessage_id(results.getInt("message_id"));
                    message.setPosted_by(results.getInt("posted_by"));
                    message.setMessage_text(results.getString("message_text"));
                    message.setTime_posted_epoch(results.getLong("time_posted_epoch"));
                    return message;
                }
              } catch (SQLException e) {
                System.out.println(e.getMessage());
               }
             return null;
         }
          
         // 6. Delete Message By Message ID (MessageDAO)
          public void deleteMessage(int id) {
            Connection connection = ConnectionUtil.getConnection();
            String sql = "DELETE FROM message WHERE message_id = ?";

            try {
                PreparedStatement preparedStatement = connection.prepareStatement(sql); 
                preparedStatement.setInt(1, id);
                preparedStatement.executeUpdate();
                
            } catch (SQLException e) {
                System.out.println(e.getMessage());
          }
        }
    
        //   7. Update Message Text By Message ID (MessageDAO) 
          public Message updatingTheMessage(Message message) {
            Connection connection = ConnectionUtil.getConnection();
            String sql = "UPDATE message SET message_text = ? WHERE message_id = ?";
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, message.getMessage_text());
                preparedStatement.setInt(2, message.getMessage_id());
                preparedStatement.executeUpdate();

              if (message.getMessage_text().length() < 255 && !message.getMessage_text().isEmpty() && getMessageByTheId(message.getMessage_id()) != null) {
                return new Message(message.getMessage_id(), message.getPosted_by(), message.getMessage_text(), message.getTime_posted_epoch());
              }
               } catch (SQLException e) {
                System.out.println(e.getMessage());
               } 
               return null;
              }                     

          // 8. Retrieve Messages Written By User (MessageDAO) 
          public List<Message> getUserMessage(int user) {
            Connection connection = ConnectionUtil.getConnection();
            List<Message> messageUser = new ArrayList<>();
            String sql = "SELECT * FROM message WHERE posted_by = ?";

            try {
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setInt(1, user);
              
                ResultSet results = preparedStatement.executeQuery();
                while(results.next()){
                    Message messageUser2 = new Message(results.getInt("message_id"), results. getInt("posted_by"), results.getString("message_text"), results.getLong("time_posted_epoch"));
                    messageUser.add(messageUser2);  
                }
            }catch(SQLException e){
                System.out.println(e.getMessage());
            } 
            
            return messageUser;  
              } 
           }
