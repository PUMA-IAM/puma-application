package puma.application.webapp.users;

import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

@Repository
public class AccountDAOImpl implements AccountDAO {
	
	@PersistenceContext
	private EntityManager em;

	@Override
	public void addAccount(Account account) {
		em.persist(account);
	}

	@Override
	public Collection<Account> getAllAccounts() {
		//em.createQuery(")
		return null;
	}

	@Override
	public Collection<Account> getAllAccountsWithFavoriteAnimal(String fa) {
		// TODO Auto-generated method stub
		return null;
	}

}
