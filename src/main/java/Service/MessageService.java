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
        return messageDAO.insertMessage(message);
    }

    public int deleteMessageById(int id) {
        return messageDAO.deleteMessageById(id);
    }

    public Message updateMessage(int id,Message updatedMessage) {
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
        return messageDAO.getMessageById(id);
    }

}
