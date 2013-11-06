package puma.application.webapp.users;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl implements AccountService {
	
	@Autowired
	private AccountDAO accountDAO;

	@Override
	public void addAccount(Account account) {
		accountDAO.addAccount(account);
	}

	@Override
	public Collection<Account> getAllAccounts() {
		return accountDAO.getAllAccounts();
	}

	@Override
	public Collection<Account> getAllAccountsWithFavoriteAnimal(String fa) {
		return accountDAO.getAllAccountsWithFavoriteAnimal(fa);
	}

}
