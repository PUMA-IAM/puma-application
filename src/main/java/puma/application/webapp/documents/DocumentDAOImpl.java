package puma.application.webapp.documents;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

@Repository
public class DocumentDAOImpl implements DocumentDAO {

	@PersistenceContext
	private EntityManager em;

	@Override
	public List<Document> getDocumentsByDestination(String destination) {
		TypedQuery<Document> query = em.createNamedQuery("documentsByDestination", Document.class);
		query.setParameter("destination", destination);
		List<Document> results = query.getResultList();
		return results;
	}

	@Override
	public List<Document> getDocumentsByOrigin(String origin) {
		TypedQuery<Document> query = em.createNamedQuery("documentsByOrigin", Document.class);
		query.setParameter("origin", origin);
		List<Document> results = query.getResultList();
		return results;
	}

}
