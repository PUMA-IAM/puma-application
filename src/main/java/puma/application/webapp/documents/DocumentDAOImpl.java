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

	@Override
	public List<Document> getDocumentsByCreatingTenant(String tenantId) {
		TypedQuery<Document> query = em.createNamedQuery("documentsByCreatingTenant", Document.class);
		query.setParameter("creatingTenant", tenantId);
		List<Document> results = query.getResultList();
		return results;
	}
}
