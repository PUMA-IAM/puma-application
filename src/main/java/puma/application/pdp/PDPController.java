package puma.application.pdp;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import puma.applicationpdp.PEPHelpers;
import puma.rest.domain.Flag;
import puma.rest.domain.Identifier;
import puma.rest.domain.Policy;
import puma.rest.domain.Status;
import puma.rmi.pdp.mgmt.ApplicationPDPMgmtRemote;

@Controller
@RequestMapping(value = "/pdp")
public class PDPController {
	
	@ResponseBody
	@RequestMapping(value = "/{name}/id", method = RequestMethod.GET, produces="application/json")
	public Identifier getId(@PathVariable String name) {
		return new Identifier(pdp(name).getId());
	}
	
	@ResponseBody
	@RequestMapping(value = "/{name}/status", method = RequestMethod.GET, produces="application/json")
	public Status getStatus(@PathVariable String name) {
		return new Status(pdp(name).getStatus());
	}
	
	@ResponseStatus(value = HttpStatus.OK)
	@RequestMapping(value = "/{name}/policy", method = RequestMethod.PUT, consumes="application/json")
	public void loadApplicationPolicy(@PathVariable String name, @RequestBody Policy policy) {
		pdp(name).loadApplicationPolicy(policy.getPolicy());
	}
	
	@ResponseBody
	@RequestMapping(value = "/{name}/policy", method = RequestMethod.GET, produces="application/json")
	public Policy getApplicationPolicy(@PathVariable String name) {
		return new Policy(pdp(name).getApplicationPolicy());
	}
	
	@ResponseStatus(value = HttpStatus.OK)
	@RequestMapping(value = "/{name}/remoteDBAccess", method = RequestMethod.PUT, consumes="application/json")
	public void setRemoteDBAccess(@PathVariable String name, @RequestBody Flag enabled) {
		pdp(name).setRemoteDBAccess(enabled.getValue());
	}
	
	private ApplicationPDPMgmtRemote pdp(String name) {
		return PEPHelpers.getPDPMgmtHelper().getPDPMgmt(name.toUpperCase());
	}

}
