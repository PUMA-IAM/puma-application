package puma.application.webapp.users;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Account {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;  
	
	public Long getId() {
		return this.id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	@Basic
	private String favoriteAnimal;
	
	public String getFavoriteAnimal() {
		return this.favoriteAnimal;
	}
	
	public void setFavoriteAnimal(String fa) {
		this.favoriteAnimal = fa;
	}

}
