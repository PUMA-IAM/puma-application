package puma.application.webapp.documents;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class DocumentServiceImpl implements DocumentService {
	
	@Autowired
	private DocumentDAO documentDAO;
	
	@Autowired
	private DocumentRepository documentRepository;
	
	@Override
	public Document getDocumentById(Long id) {
		return documentRepository.findOne(id);
	}

	@Override
	public void addDocument(Document document) {
		documentRepository.saveAndFlush(document);
	}

	@Override
	public void deleteDocument(Long docId) {
		documentRepository.delete(docId);
	}

	@Override
	public List<Document> getAllDocuments() {
		return documentRepository.findAll();
	}

	@Override
	public List<Document> getDocumentsByDestination(String destination) {
		return documentDAO.getDocumentsByDestination(destination);
	}

	@Override
	public List<Document> getDocumentsByOrigin(String origin) {
		return documentDAO.getDocumentsByOrigin(origin);
	}

}
