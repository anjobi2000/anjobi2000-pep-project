package Controller;

import java.sql.SQLException;
import java.util.List;

import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.MessageService;
import io.javalin.Javalin;
import io.javalin.http.Context;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    AccountService accountService = new AccountService();
    MessageService messageService = new MessageService();
    
    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        
        messageService = new MessageService();
        app.post("/register", this::registerHandler);
        app.post("/login", this::loginHandler);
        app.post("/messages", this::createMessageHandler);
        app.delete("/messages/{message_id}", this::deleteMessageByIdHandler);
        app.patch("/messages/{message_id}", this::updateMessageHandler);
        app.get("/messages/{message_id}", this::getMessageByIdHandler);
        app.get("/accounts/{account_id}/messages", this::getMessagesByAccountIdHandler);
        app.get("/messages", this::getAllMessagesHandler);
        

        return app;
    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void registerHandler(Context context){
        Account reqAccount = context.bodyAsClass(Account.class);
        Account newAccount = accountService.register(reqAccount);
        if (newAccount == null) {
            context.status(400);
        } else {
            context.json(newAccount);
        }
    }
    private void loginHandler(Context context) {
        Account reqAccount = context.bodyAsClass(Account.class);
        Account loginAccount = accountService.login(reqAccount);
        if (loginAccount == null) {
            context.status(401);
        } else {
            context.json(loginAccount);
        }
    }
    private void createMessageHandler(Context context) {
        Message newMsg = context.bodyAsClass(Message.class);
        Message createdMsg = messageService.createMessage(newMsg);
    
    if (createdMsg == null) {
        context.status(400);
    } else {
        context.status(200);
        context.json(createdMsg);

    }  
    }
    private void getAllMessagesHandler(Context context) {
        List<Message> messages = messageService.getAllMessages();
        context.status(200);
        context.json(messages);
    }
    private void getMessageByIdHandler(Context context) {
        int messageId = Integer.parseInt(context.pathParam("message_id"));
    try {
        Message msg = messageService.getMessageById(messageId);
        if (msg == null) {
            context.status(200);
            context.result("");
        } else {
            context.status(200);
            context.json(msg);
        }
    }catch (SQLException e) {
        e.printStackTrace();
        context.status(500);
        context.result("Database error occurred.");
    }
    }
    private void getMessagesByAccountIdHandler(Context context) {
        int accountId = Integer.parseInt(context.pathParam("account_id"));
        List<Message> messages = messageService.getMessagesByAccountId(accountId);
            context.status(200);
            context.json(messages);
        }
    private void deleteMessageByIdHandler(Context context) {
        int id = Integer.parseInt(context.pathParam("message_id"));
    try {
        Message deletedMsg = messageService.getMessageById(id);
        int deleted = messageService.deleteMessageById(id);

        if (deleted == 0 || deletedMsg == null) {
            context.status(200);
            context.result(""); 
        } else {
            context.status(200);
        
            context.json(deletedMsg);
        }
    } catch (SQLException e) {
        e.printStackTrace();
        context.status(500);
        context.result("Internal Server Error");
    }
}
private void updateMessageHandler(Context context) {
    int messageId = Integer.parseInt(context.pathParam("message_id"));
    Message updatedMsgData = context.bodyAsClass(Message.class);

    if (updatedMsgData.getMessage_text() == null || 
        updatedMsgData.getMessage_text().isBlank() || 
        updatedMsgData.getMessage_text().length() > 255) {
        
        context.status(400);
        context.result("");
        return;
        }
        Message updatedMsg = messageService.updateMessage(messageId, updatedMsgData);
        if (updatedMsg == null) {
            context.status(400);
            context.result("");
    } else {
        context.status(200);
        context.json(updatedMsg);
}
}
}