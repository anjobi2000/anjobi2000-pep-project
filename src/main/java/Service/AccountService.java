package Service;

import DAO.AccountDAO;
import Model.Account;


public class AccountService {
    private AccountDAO accountDAO = new AccountDAO();

    public Account register(Account account) {
        // Validate: username must not be blank, and password must be at least 4 characters
        if (account.getUsername().isBlank() || account.getPassword().length() < 4) {
            return null;
        }
        return accountDAO.registerAccount(account);
    }

    public Account login(Account account) {
        // Call the DAO's loginAccount method to authenticate
        return accountDAO.loginAccount(account);//
    }
}
