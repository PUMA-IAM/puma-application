package puma.application.webapp.documents;

import java.util.List;


public interface DocumentDAO {
	
	public List<Document> getDocumentsByDestination(String destination);
	
	public List<Document> getDocumentsByOrigin(String origin);

	public List<Document> getDocumentsByCreatingTenant(String tenantId);
	

}
