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

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@NamedQueries({
	@NamedQuery(name = "documentsByDestination", query = "SELECT doc FROM Document doc WHERE doc.destination = :destination"),
	@NamedQuery(name = "documentsByOrigin", query = "SELECT doc FROM Document doc WHERE doc.origin = :origin"),
	@NamedQuery(name = "documentsByCreatingTenant", query = "SELECT doc FROM Document doc WHERE doc.creatingTenant = :creatingTenant")
})
public class Document {

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
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Temporal(TemporalType.DATE)
	private Date date;

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	@Basic
	private String origin;

	public String getOrigin() {
		return origin;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}
	
	@Basic
	private String destination;

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}
	
	@Basic
	private String creatingTenant;

	public String getCreatingTenant() {
		return creatingTenant;
	}

	public void setCreatingTenant(String creatingTenant) {
		this.creatingTenant = creatingTenant;
	}
	
	/**
	 * For JPA
	 */
	public Document() {
		
	}
	
	public Document(String name, String origin, String destination, String creatingTenant) {
		this.name = name;
		this.origin = origin;
		this.destination = destination;
		this.creatingTenant = creatingTenant;
		this.date = new Date();
	}
	
	/**
	 * Other
	 */
	@Override
	public boolean equals(Object other) {
		if (!(other instanceof Document))
			return false;
		Document doc = (Document) other;
		return doc.getId().equals(this.getId());
	}

}
