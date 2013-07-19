
package be.kuleuven.cs.projects.samples.up.document_sending_service.clients;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.Action;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;
import be.kuleuven.cs.projects.samples.up.document_sending_service.clients.resources.ObjectFactory;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.4-b01
 * Generated source version: 2.2
 * 
 */
@WebService(name = "AccessControlService", targetNamespace = "http://puma/sp")
@XmlSeeAlso({
    ObjectFactory.class
})
public interface AccessControlService {


    /**
     * 
     * @param arg0
     * @return
     *     returns java.lang.Boolean
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "isAuthenticated", targetNamespace = "http://puma/sp", className = "puma.sp.IsAuthenticated")
    @ResponseWrapper(localName = "isAuthenticatedResponse", targetNamespace = "http://puma/sp", className = "puma.sp.IsAuthenticatedResponse")
    @Action(input = "http://puma/sp/AccessControlService/isAuthenticatedRequest", output = "http://puma/sp/AccessControlService/isAuthenticatedResponse")
    public Boolean isAuthenticated(
        @WebParam(name = "arg0", targetNamespace = "")
        String arg0);

    /**
     * 
     * @return
     *     returns java.lang.String
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getAuthenticationAddress", targetNamespace = "http://puma/sp", className = "puma.sp.GetAuthenticationAddress")
    @ResponseWrapper(localName = "getAuthenticationAddressResponse", targetNamespace = "http://puma/sp", className = "puma.sp.GetAuthenticationAddressResponse")
    @Action(input = "http://puma/sp/AccessControlService/getAuthenticationAddressRequest", output = "http://puma/sp/AccessControlService/getAuthenticationAddressResponse")
    public String getAuthenticationAddress();

    /**
     * 
     * @param arg1
     * @param arg0
     * @return
     *     returns java.lang.String
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "retrieveAttributes", targetNamespace = "http://puma/sp", className = "puma.sp.RetrieveAttributes")
    @ResponseWrapper(localName = "retrieveAttributesResponse", targetNamespace = "http://puma/sp", className = "puma.sp.RetrieveAttributesResponse")
    @Action(input = "http://puma/sp/AccessControlService/retrieveAttributesRequest", output = "http://puma/sp/AccessControlService/retrieveAttributesResponse")
    public String retrieveAttributes(
        @WebParam(name = "arg0", targetNamespace = "")
        String arg0,
        @WebParam(name = "arg1", targetNamespace = "")
        String arg1);

    /**
     * 
     * @param arg0
     * @return
     *     returns java.lang.String
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "generateSharedPassword", targetNamespace = "http://puma/sp", className = "puma.sp.GenerateSharedPassword")
    @ResponseWrapper(localName = "generateSharedPasswordResponse", targetNamespace = "http://puma/sp", className = "puma.sp.GenerateSharedPasswordResponse")
    @Action(input = "http://puma/sp/AccessControlService/generateSharedPasswordRequest", output = "http://puma/sp/AccessControlService/generateSharedPasswordResponse")
    public String generateSharedPassword(
        @WebParam(name = "arg0", targetNamespace = "")
        String arg0);

    /**
     * 
     * @param arg3
     * @param arg2
     * @param arg1
     * @param arg0
     * @return
     *     returns java.lang.Boolean
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "registerAccess", targetNamespace = "http://puma/sp", className = "puma.sp.RegisterAccess")
    @ResponseWrapper(localName = "registerAccessResponse", targetNamespace = "http://puma/sp", className = "puma.sp.RegisterAccessResponse")
    @Action(input = "http://puma/sp/AccessControlService/registerAccessRequest", output = "http://puma/sp/AccessControlService/registerAccessResponse")
    public Boolean registerAccess(
        @WebParam(name = "arg0", targetNamespace = "")
        String arg0,
        @WebParam(name = "arg1", targetNamespace = "")
        String arg1,
        @WebParam(name = "arg2", targetNamespace = "")
        String arg2,
        @WebParam(name = "arg3", targetNamespace = "")
        String arg3);

    /**
     * 
     * @param arg3
     * @param arg2
     * @param arg1
     * @param arg0
     * @return
     *     returns java.lang.Boolean
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "isAllowedAccess", targetNamespace = "http://puma/sp", className = "puma.sp.IsAllowedAccess")
    @ResponseWrapper(localName = "isAllowedAccessResponse", targetNamespace = "http://puma/sp", className = "puma.sp.IsAllowedAccessResponse")
    @Action(input = "http://puma/sp/AccessControlService/isAllowedAccessRequest", output = "http://puma/sp/AccessControlService/isAllowedAccessResponse")
    public Boolean isAllowedAccess(
        @WebParam(name = "arg0", targetNamespace = "")
        String arg0,
        @WebParam(name = "arg1", targetNamespace = "")
        String arg1,
        @WebParam(name = "arg2", targetNamespace = "")
        String arg2,
        @WebParam(name = "arg3", targetNamespace = "")
        String arg3);

    /**
     * 
     * @param arg0
     * @return
     *     returns java.lang.Integer
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getTenant", targetNamespace = "http://puma/sp", className = "puma.sp.GetTenant")
    @ResponseWrapper(localName = "getTenantResponse", targetNamespace = "http://puma/sp", className = "puma.sp.GetTenantResponse")
    @Action(input = "http://puma/sp/AccessControlService/getTenantRequest", output = "http://puma/sp/AccessControlService/getTenantResponse")
    public Integer getTenant(
        @WebParam(name = "arg0", targetNamespace = "")
        String arg0);

}
