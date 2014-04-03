package puma.application.webapp.documents;

import java.util.List;


public interface DocumentService {
	
	public Document getDocumentById(Long id);
	
	public void addDocument(Document document);
	
	public List<Document> getAllDocuments();
	
	public List<Document> getDocumentsByDestination(String destination);
	
	public List<Document> getDocumentsByOrigin(String origin);
	
	public List<Document> getDocumentsByCreatingTenant(String tenantId);

	void deleteDocument(Long docId);

}
