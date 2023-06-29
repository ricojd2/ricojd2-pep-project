package Service;

import Model.Account;
import DAO.AccountDAO;
import java.util.*;


public class AccountService {
    private AccountDAO accountDAO;

    public AccountService() {
        accountDAO = new AccountDAO();
    }

    public AccountService (AccountDAO accountDAO) {
        this.accountDAO = accountDAO;
    }

    // 1. Process New User Registration (Account Service)
    public Account registerAccount(Account account) {
        Account existingAccount = accountDAO.getUsername(account.getUsername());
        if (existingAccount == null && account.getPassword().length() >= 4 && !account.getUsername().isBlank() && !account.getPassword().isBlank()) {
            return accountDAO.createAccount(account);
        }
        return null;
    }
    
    // 2. Process User Login (Account Service)
    public Account logIntoAccount(Account logAccount) {
        Account currentAccount = accountDAO.userWithPassword(logAccount.getUsername(), logAccount.getPassword());
        if (currentAccount != null) {
            return currentAccount;
        }
        return null;
        }
    }