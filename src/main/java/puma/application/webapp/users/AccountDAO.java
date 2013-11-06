package puma.application.webapp.users;

import java.util.Collection;

public interface AccountDAO {
	
	public void addAccount(Account account);
	
	public Collection<Account> getAllAccounts();
	
	public Collection<Account> getAllAccountsWithFavoriteAnimal(String fa);

}
