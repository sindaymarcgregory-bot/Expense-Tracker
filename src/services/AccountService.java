package services;

import java.util.ArrayList;
import model.Account;

public class AccountService {

    private static ArrayList<Account> accounts = new ArrayList<>();

    public boolean register(Account account) {

        for (Account a : accounts) {

            if (a.getUser().getUsername()
                    .equalsIgnoreCase(account.getUser().getUsername())) {

                return false;
            }

        }

        accounts.add(account);

        return true;
    }

    public boolean login(String username, String password) {

        for (Account a : accounts) {

            if (a.getUser().getUsername().equals(username)
                    && a.getUser().getPassword().equals(password)) {

                return true;
            }

        }

        return false;

    }

    public ArrayList<Account> getAccounts() {
        return accounts;
    }

}