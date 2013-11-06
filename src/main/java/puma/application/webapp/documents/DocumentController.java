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
		return "documents/create-document";
	}

	@RequestMapping(value = "/docs/create-impl", method = RequestMethod.POST)
	public String createDocumentImplementation(ModelMap model,
			@RequestParam("name") String name,
			@RequestParam("destination") String destination, HttpSession session) {
		String origin = (String) session.getAttribute("user-email");
		Document doc = new Document(name, origin, destination);
		docService.addDocument(doc);
		Long newId = doc.getId();
		MessageManager.getInstance().addMessage(session, "success", "Document successfully created.");
		return "redirect:/docs/" + newId;
	}

	@RequestMapping("/docs/{docId}")
	public String viewDocument(@PathVariable("docId") Long docId, ModelMap model, HttpSession session) {
		Document doc = docService.getDocumentById(docId);
		if(doc == null) {
			MessageManager.getInstance().addMessage(session, "failure", "Document with id " + docId + " not found.");
			return "redirect:/docs";			
		}
		model.addAttribute("doc", doc);
		model.addAttribute("msgs", MessageManager.getInstance().getMessages(session));
		return "documents/view-document";
	}

	@RequestMapping("/docs/{docId}/delete")
	public String deleteDocument(@PathVariable("docId") Long docId, HttpSession session) {
		Document doc = docService.getDocumentById(docId);
		if(doc == null) {
			MessageManager.getInstance().addMessage(session, "failure", "Document with id " + docId + " not found.");
			return "redirect:/docs";			
		}
		String name = doc.getName();
		docService.deleteDocument(docId);
		MessageManager.getInstance().addMessage(session, "success", "Document \"" + name + "\" successfully deleted.");
		return "redirect:/docs";
	}
}