package Service;

import Model.Message;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import DAO.MessageDAO;

public class MessageService {
    MessageDAO messageDAO;

    public MessageService() {
        this.messageDAO = new MessageDAO();
    }

    public Message createMessage(Message message) {
        if (message == null || message.getMessage_text() == null || message.getMessage_text().trim().isEmpty()) {
            return null;
        }
        if (message.getMessage_text().length() > 255) {
            return null;
        }
        
        return messageDAO.insertMessage(message);
    }

    public int deleteMessageById(int id) {
        return messageDAO.deleteMessageById(id);
    }

    public Message updateMessage(int id,Message updatedMessage) {
        if (updatedMessage == null || updatedMessage.getMessage_text() == null || updatedMessage.getMessage_text().trim().isEmpty()) {
            return null;
        }
        if (updatedMessage.getMessage_text().length() > 255) {
            return null;
        }
        return messageDAO.updateMessage(id, updatedMessage.getMessage_text());
    }

    public List<Message> getMessagesByAccountId(int accountId) {
        return messageDAO.getMessagesByAccountId(accountId);
    }

    public List<Message> getAllMessages() {
        try {
            return messageDAO.getAllMessages();
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
    public Message getMessageById(int id) throws SQLException {
        try {
        return messageDAO.getMessageById(id);
    } catch (SQLException e) {
        e.printStackTrace();
        return null;
    }
}

}
