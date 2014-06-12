/*******************************************************************************
 * Copyright 2014 KU Leuven Research and Developement - iMinds - Distrinet 
 * 
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 * 
 *        http://www.apache.org/licenses/LICENSE-2.0
 * 
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 *    
 *    Administrative Contact: dnet-project-office@cs.kuleuven.be
 *    Technical Contact: maarten.decat@cs.kuleuven.be
 *    Author: maarten.decat@cs.kuleuven.be
 ******************************************************************************/
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

	@Override
	public List<Document> getDocumentsByCreatingTenant(String tenantId) {
		return documentDAO.getDocumentsByCreatingTenant(tenantId);
	}

}
