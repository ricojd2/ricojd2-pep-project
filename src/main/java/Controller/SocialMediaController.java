package Controller;

import io.javalin.Javalin;
import io.javalin.http.Context;
import Model.Account;
import Model.Message;
import Service.MessageService;
import Service.AccountService;
import java.util.List;
import java.util.*;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {

  // Create Global Variables
  MessageService messageService;
  AccountService accountService;
 

  // Create SocialMediaController Constructor and Initialize Global Variables
    public SocialMediaController() {

       this.messageService = new MessageService();
       this.accountService = new AccountService();
  }
    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
    Javalin app = Javalin.create();
    app.get("example-endpoint", this::exampleHandler);

      // 1. Process New User Registration (POST)
      app.post("/register", this::postRegisterHandler);
    
      // 2. Process User Login (POST)
      app.post("/login", this::postLoginHandler);
 
      // 3. Process New Message Creation (POST)
      app.post("/messages", this::postMessagesHandler);
 
      // 4. Retrieve All Messages (GET)
      app.get("/messages", this::getRetrieveAllHandler);
 
      // 5. Retrieve Messages By ID (GET)
      app.get("/messages/{message_id}", this::getRetrieveMessageId);
 
      // 6. Delete Message By Message ID (DELETE)
      app.delete("/messages/{message_id}", this::deleteMessagesId);
 
      // 7. Update Message Text By Message ID (PATCH)
      app.patch("/messages/{message_id}", this::patchUpdateMessageId);
 
      // 8. Retrieve Messages Written By User (GET)
      app.get("/accounts/{account_id}/messages", this::getRetrieveByUser);
 
        return app;
    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
      private void exampleHandler(Context context) {
        context.json("sample text");
      }
    
         // 1. Process New User Registration (Handler)
          private void postRegisterHandler(Context context) {
          Account accountClass = context.bodyAsClass(Account.class);
          Account createdAccount = accountService.registerAccount(accountClass);

          if (createdAccount != null) {
              context.json(createdAccount);
          } else {
              context.status(400);
          }
        }
  
      // 2. Process User Login (Handler)
      private void postLoginHandler(Context context) {
        Account theAccountClass = context.bodyAsClass(Account.class);
        Account loggedInAccount = accountService.logIntoAccount(theAccountClass);

        if (loggedInAccount != null) {
            context.json(loggedInAccount);
        } else {
            context.status(401);
        }
      }

      // 3. Process New Message Creation (Handler)
      private void postMessagesHandler(Context context) {
      Message messageClass = context.bodyAsClass(Message.class);
      Message postMessage = messageService.addNewMessage(messageClass);

      if (postMessage != null) {
          context.json(postMessage);
      } else {
          context.status(400);
      }
    }

      // 4. Retrieve All Messages (Handler) 
      public void getRetrieveAllHandler(Context context) {
      List<Message> allMessages = messageService.getMessages();
          context.json(allMessages);
          }
      
      // 5. Retrieve Messages By ID (Handler)
      public void getRetrieveMessageId (Context context) {
      int getMessageById = Integer.parseInt(context.pathParam("message_id"));
      Message retrieveId = messageService.getMessageId(getMessageById);

        if (retrieveId != null) {
          context.json(retrieveId);
        }
     }

      // 6. Delete Message By Message ID (Handler)  
        private void deleteMessagesId(Context context) {
        int deleteMessageId = Integer.parseInt(context.pathParam("message_id"));
        Message deletedTime = messageService.deleteMessage(deleteMessageId);

        if (deletedTime != null) {
            context.json(deletedTime);
        } else {
          context.status(200);
        }
     }
      
      // 7. Update Message Text By Message ID (Handler)
      public void patchUpdateMessageId(Context context) {

        int messageId = Integer.parseInt(context.pathParam("message_id"));
        Message updateBody = context.bodyAsClass(Message.class);
        Message update = messageService.updatingMessages(messageId, updateBody);

        if (update != null) {
          context.json(update);
        } else {
          context.status(400);
        } 
      }
      
      
     // 8. Retrieve Messages Written By User (Handler) 
       public void getRetrieveByUser (Context context){
      int theAccountId = Integer.parseInt(context.pathParam("account_id"));
      List<Message> retrieveList = messageService.getAccountId(theAccountId);
      context.json(retrieveList);
            }
          }