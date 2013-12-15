package puma.application.webapp.documents;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import puma.application.webapp.msgs.MessageManager;
import puma.applicationpdp.ApplicationPEP;
import puma.peputils.Action;
import puma.peputils.Environment;
import puma.peputils.Subject;
import puma.peputils.attributes.EnvironmentAttributeValue;
import puma.peputils.attributes.ObjectAttributeValue;

@Controller
public class DocumentController {

	@Autowired
	private DocumentService docService;

	@RequestMapping(value = "/docs", method = RequestMethod.GET)
	public String listDocuments(ModelMap model, HttpSession session) {
		String userEmail = (String) session.getAttribute("user-email");
		List<Document> receivedDocuments = docService.getDocumentsByDestination(userEmail);
		List<Document> sentDocuments = docService.getDocumentsByOrigin(userEmail);
		model.addAttribute("receivedDocuments", receivedDocuments);
		model.addAttribute("sentDocuments", sentDocuments);
		model.addAttribute("msgs", MessageManager.getInstance().getMessages(session));
		return "documents/list-documents";
		
//		String sent = "Sent = [ ";
//		for(Document doc: sentDocuments) {
//			sent += doc.getName() + " ";
//		}
//		sent += " ] ";
//		
//		String received = "Received = [ ";
//		for(Document doc: receivedDocuments) {
//			received += doc.getName() + " ";
//		}
//		received += " ] ";
//		model.addAttribute("output", received + "   " + sent);
//		return "test";
	}

	@RequestMapping(value = "/docs/create", method = RequestMethod.GET)
	public String createDocument(ModelMap model) {
		// TODO first check whether the subject can create new Documents 
		// (not security here, but usability)
		
		return "documents/create-document";
	}

	@RequestMapping(value = "/docs/create-impl", method = RequestMethod.POST)
	public String createDocumentImplementation(ModelMap model,
			@RequestParam("name") String name,
			@RequestParam("destination") String destination, HttpSession session) {
		// TODO first check whether the subject can create new Documents 
		// (not security here, but usability)
		
		// Create the Document
		String origin = (String) session.getAttribute("user-email");
		Document doc = new Document(name, origin, destination);
		docService.addDocument(doc);
		Long newId = doc.getId();
		MessageManager.getInstance().addMessage(session, "success", "Document successfully created.");
		
		// TODO create the entity in de PUMA PIPs
		
		return "redirect:/docs/" + newId;
	}

	@RequestMapping("/docs/{docId}")
	public String viewDocument(@PathVariable("docId") Long docId, ModelMap model, HttpSession session) {
		Document doc = docService.getDocumentById(docId);
		
		// 1. First, check whether the document exists
		if(doc == null) {
			MessageManager.getInstance().addMessage(session, "failure", "Document with id " + docId + " not found.");
			return "redirect:/docs";			
		}
		
		// 2. Then, check whether the current user is allowed to read the document
		// Note that the PDP is already initialized by the PDPInitializer 
		Subject subject = (Subject) session.getAttribute("subject");
		puma.peputils.Object object = constructAuthzObject(doc);		
		Action action = new Action("read");		
		Environment environment = constructEnvironment();
		boolean authorized = ApplicationPEP.getInstance().isAuthorized(subject, object, action, environment);
		// Enforce the decision
		if(!authorized) {
			MessageManager.getInstance().addMessage(session, "failure", "You are not allowed to access document #" + doc.getId());
			return "redirect:/docs";
		}
		
		// 3. Finally, if authorized, show the document
		model.addAttribute("doc", doc);
		model.addAttribute("msgs", MessageManager.getInstance().getMessages(session));
		return "documents/view-document";
	}

	@RequestMapping("/docs/{docId}/delete")
	public String deleteDocument(@PathVariable("docId") Long docId, HttpSession session) {
		Document doc = docService.getDocumentById(docId);

		// 1. First, check whether the document exists
		if(doc == null) {
			MessageManager.getInstance().addMessage(session, "failure", "Document with id " + docId + " not found.");
			return "redirect:/docs";			
		}
		
		// 2. Then, check whether the user is allowed to delete the document
		// Note that the PDP is already initialized by the PDPInitializer 
		Subject subject = (Subject) session.getAttribute("subject");
		puma.peputils.Object object = constructAuthzObject(doc);		
		Action action = new Action("delete");		
		Environment environment = constructEnvironment();
		boolean authorized = ApplicationPEP.getInstance().isAuthorized(subject, object, action, environment);
		// Enforce the decision
		if(!authorized) {
			MessageManager.getInstance().addMessage(session, "failure", "You are not allowed to access document #" + doc.getId());
			return "redirect:/docs";
		}
		
		// 3. Finally, delete the document
		// 3.1 Delete the document in the application
		String name = doc.getName();
		docService.deleteDocument(docId);
		MessageManager.getInstance().addMessage(session, "success", "Document \"" + name + "\" successfully deleted.");
		// 3.2 Delete the document entity in PUMA PIPs as well TODO
		
		return "redirect:/docs";
	}
	
	/**
	 * Helper function for converting a Document to an authorization Object.
	 * 
	 * @param doc
	 * @return
	 */
	private puma.peputils.Object constructAuthzObject(Document doc) {
		puma.peputils.Object object = new puma.peputils.Object("" + doc.getId());
		object.addAttributeValue(new ObjectAttributeValue("type", "document"));
		object.addAttributeValue(new ObjectAttributeValue("name", doc.getName()));
		object.addAttributeValue(new ObjectAttributeValue("sent-date", doc.getDate()));
		object.addAttributeValue(new ObjectAttributeValue("creating-tenant", "TODO")); //TODO
		object.addAttributeValue(new ObjectAttributeValue("owning-tenant", "TODO")); //TODO
		object.addAttributeValue(new ObjectAttributeValue("content", "TODO.pdf")); // TODO
		object.addAttributeValue(new ObjectAttributeValue("origin", doc.getOrigin()));
		object.addAttributeValue(new ObjectAttributeValue("destination", doc.getDestination()));
		return object;
	}
	
	/**
	 * Helper function to construct the authorization Environment.
	 */
	private Environment constructEnvironment() {
		Environment environment = new Environment();
		environment.addAttributeValue(new EnvironmentAttributeValue("system-status", "overload")); // TODO add something useful here?
		environment.addAttributeValue(new EnvironmentAttributeValue("system-load", 90)); // TODO add something useful here?
		return environment;		
	}
}