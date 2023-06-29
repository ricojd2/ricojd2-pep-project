package Service;

import Model.Message;
import DAO.MessageDAO;
import java.util.*;

public class MessageService {
    private MessageDAO messageDAO;

    public MessageService() {
        messageDAO = new MessageDAO();
      }

    public MessageService(MessageDAO messageDAO) {
        this.messageDAO = messageDAO;
    }

        // 3. Process New Message Creation (Message Service)
        public Message addNewMessage(Message newMessage) {
        if (newMessage != null && newMessage.getMessage_text() != null && !newMessage.getMessage_text().isEmpty() && newMessage.getMessage_text().length() <= 254) {
            return messageDAO.messageCreation(newMessage);
          }
        return null;
          }
           
       // 4. Retrieve All Messages (Message Service) 
        public List<Message> getMessages() {
         return messageDAO.getAllMessageList();
        }

       // 5. Retrieve Messages By ID (Message Service)
       public Message getMessageId(int id){
        return messageDAO.getMessageByTheId(id);
        }

       // 6. Delete Message By Message ID (Message Service)
        public Message deleteMessage(int deleteId) {
         return messageDAO.getMessageByTheId(deleteId); 
      }

       // 7. Update Message Text By Message ID (Message Service) 
        public Message updatingMessages(int id, Message message) {
        Message existing = messageDAO.getMessageByTheId(id);
        String text = message.getMessage_text();
         
        if (existing != null && !text.isEmpty() && text.length() <= 255) {
        existing.setMessage_text(text);
         return messageDAO.updatingTheMessage(existing);
           }
         return null;
      }

        // 8. Retrieve Messages Written By User (Message Service) 
           public List<Message> getAccountId(int user) {
            return messageDAO.getUserMessage(user);
           }
        }
