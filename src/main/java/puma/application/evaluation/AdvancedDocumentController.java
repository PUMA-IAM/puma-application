package puma.application.evaluation;

import java.sql.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import puma.application.webapp.documents.Document;
import puma.application.webapp.documents.DocumentService;
import puma.applicationpdp.ApplicationPEP;
import puma.peputils.Action;
import puma.peputils.Environment;
import puma.peputils.Subject;
import puma.peputils.attributes.EnvironmentAttributeValue;
import puma.peputils.attributes.ObjectAttributeValue;
import puma.peputils.attributes.SubjectAttributeValue;
import puma.sp.mgmt.model.attribute.Attribute;
import puma.sp.mgmt.model.attribute.RetrievalStrategy;
import puma.sp.mgmt.model.user.User;
import puma.sp.mgmt.repositories.user.UserService;

@Controller
public class AdvancedDocumentController {
	@Autowired
	private DocumentService docService;
	
	@Autowired
	private UserService userService;

	/**
	* Creates an document with the given attributes. Retrus the identifier of the document
	* @param params All attributes of the document. 'name', 'date' (in long format), 'tenant'
	* @return Identifier of the created document
	*/
	@ResponseBody
	@RequestMapping(value = "/createDocument", method = RequestMethod.GET)
	public String createDocument(@RequestParam MultiValueMap<String, String> params) {
		Document doc = new Document();
		if (params.containsKey("tenant"))
			doc.setCreatingTenant(params.getFirst("tenant"));
		if (params.containsKey("name"))
			doc.setName(params.getFirst("name"));
		if (params.containsKey("origin"))
			doc.setOrigin(params.getFirst("origin"));
		if (params.containsKey("destination"))
			doc.setDestination(params.getFirst("destination"));
		if (params.containsKey("date"))
			doc.setDate(new Date(Long.parseLong(params.getFirst("date"))));
		this.docService.addDocument(doc);
		return doc.getId().toString();
	}
	
	@ResponseBody
	@RequestMapping(value = "/removeAllDocuments", method = RequestMethod.GET)
	public void removeAll() {
		for (Document next: this.docService.getAllDocuments())
			this.docService.deleteDocument(next.getId());
	}
	
	@ResponseBody
	@RequestMapping(value = "/removeDocument", method = RequestMethod.GET)
	public String removeDocument(@RequestParam("id") Long id) {
		Document d = this.docService.getDocumentById(id);
		if (d == null)
			return Boolean.FALSE.toString();
		else
			this.docService.deleteDocument(d.getId());
		return Boolean.TRUE.toString();
	}

	/**
	* Checks whether a certain document can be accessed. Assumes no environment attributes to be set.
	* @param params Contains at least 'user' (user id), 'document' (document id).
	* @return True if user is permitted to access the document
	*/
	@ResponseBody
	@RequestMapping(value = "/accessDocument/{action}", method = RequestMethod.GET)
	public String access(@PathVariable("action") String action, @RequestParam MultiValueMap<String, String> params) {
		Environment env = new Environment();
		env.addAttributeValue(new EnvironmentAttributeValue("currentTimeBetween7And19", false));
		env.addAttributeValue(new EnvironmentAttributeValue("currentDateBetween20And25", false));
		return this.access(new Action(action), params, env);
	}
	
	/**
	* Similar to accessDocument, but assumes time between 7 and 19, the current date is not between 20 and 25.
	*
	*/
	@ResponseBody
	@RequestMapping(value = "/accessDocumentBetween7And19/{action}", method = RequestMethod.GET)
	public String accessBetween7s(@PathVariable("action") String action, @RequestParam MultiValueMap<String, String> params) {
		Environment env = new Environment();
		env.addAttributeValue(new EnvironmentAttributeValue("currentTimeBetween7And19", true));
		env.addAttributeValue(new EnvironmentAttributeValue("currentDateBetween20And25", false));
		return this.access(new Action(action), params, env);
	}
	
	/**
	* Similar to accessDocument, but assumes the current date is not between 20 and 25, and current time not between 7 and 19.
	*
	*/
	@ResponseBody
	@RequestMapping(value = "/accessDocumentBetween20And25/{action}", method = RequestMethod.GET)
	public String accessEndMonth(@PathVariable("action") String action, @RequestParam MultiValueMap<String, String> params) {
		Environment env = new Environment();
		env.addAttributeValue(new EnvironmentAttributeValue("currentTimeBetween7And19", false));
		env.addAttributeValue(new EnvironmentAttributeValue("currentDateBetween20And25", true));
		return this.access(new Action(action), params, env);
	}
	
	/**
	* Similar to accessDocument, but assumes the current date is not between 20 and 25, and current time between 7 and 19.
	*
	*/
	@ResponseBody
	@RequestMapping(value = "/accessDocumentBetween7And19And20And25/{action}", method = RequestMethod.GET)
	public String accessEndMonthBetween7s(@PathVariable("action") String action, @RequestParam MultiValueMap<String, String> params) {
		Environment env = new Environment();
		env.addAttributeValue(new EnvironmentAttributeValue("currentTimeBetween7And19", true));
		env.addAttributeValue(new EnvironmentAttributeValue("currentDateBetween20And25", true));
		return this.access(new Action(action), params, env);
	}
	
	private String access(Action action, MultiValueMap<String, String> params, Environment environment) {
		if (!params.containsKey("user") || !params.containsKey("document")) {
			throw new IllegalArgumentException("Provide at least user and document ids");
		}
		User u = this.userService.byId(Long.parseLong(params.getFirst("user")));
		if (u == null)
			throw new IllegalArgumentException("Could not find user with specified id~!");
		Subject subject = new Subject(u.getId().toString());
		for (Attribute next: u.getAttributes())
			if (next.getFamily().getRetrievalStrategy().equals(RetrievalStrategy.PUSH))
				addAttribute(subject, next.getFamily().getXacmlIdentifier(), next.getValue());
		Document doc = this.docService.getDocumentById(Long.parseLong(params.getFirst("document")));
		if (doc == null)
			throw new IllegalArgumentException("Could not find document with specified id~!");
		params.remove("document");
		params.remove("user");	
		puma.peputils.Object object = constructAuthzObject(doc, params);
		Boolean decision = ApplicationPEP.getInstance().isAuthorized(subject, object, action, environment);
		return decision.toString();
	}
	
	private static void addAttribute(Subject subject, String key, String value) {		
		String id = key;
		if (!key.startsWith("subject:"))
			id = "subject:" + key;
		SubjectAttributeValue result = subject.getAttributeValue(id);
		if (result == null)
			subject.addAttributeValue(new SubjectAttributeValue(key, value));
		else
			result.addValue(value);		
	}
	
	private puma.peputils.Object constructAuthzObject(Document doc, MultiValueMap<String, String> params) {
		puma.peputils.Object object = new puma.peputils.Object("" + doc.getId());
		object.addAttributeValue(new ObjectAttributeValue("type", "document"));
		object.addAttributeValue(new ObjectAttributeValue("name", doc.getName()));
		object.addAttributeValue(new ObjectAttributeValue("sent-date", doc.getDate()));
		object.addAttributeValue(new ObjectAttributeValue("creating-tenant", doc.getCreatingTenant()));
		//object.addAttributeValue(new ObjectAttributeValue("owning-tenant", doc.getDestination()));
		object.addAttributeValue(new ObjectAttributeValue("content", "TODO.pdf"));
		object.addAttributeValue(new ObjectAttributeValue("origin", doc.getOrigin()));
		object.addAttributeValue(new ObjectAttributeValue("destination", doc.getDestination()));
		for (String nextKey: params.keySet())
			for (String nextValue: params.get(nextKey))
				addAttribute(object, nextKey, nextValue);
		return object;
	}

	private static void addAttribute(puma.peputils.Object object, String key, String value) {		
		String id = key;
		if (!key.startsWith("object:"))
			id = "object:" + key;
		ObjectAttributeValue result = object.getAttributeValue(id);
		if (result == null)
			object.addAttributeValue(new ObjectAttributeValue(key, value));
		else
			result.addValue(value);
	}
}