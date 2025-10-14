package ratcam2;

import org.springframework.oxm.Unmarshaller;
import org.springframework.oxm.XmlMappingException;
import org.springframework.ws.client.WebServiceClientException;
import org.springframework.ws.client.support.interceptor.ClientInterceptor;
import org.springframework.ws.soap.SoapBody;
import org.springframework.ws.soap.SoapHeaderElement;
import org.springframework.ws.soap.SoapMessage;
import org.springframework.ws.soap.server.SoapEndpointInterceptor;
import org.w3c.dom.NodeList;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.ws.handler.MessageContext;
import onvif.wsdl.GetSnapshotUri;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.xml.transform.Source;
import javax.xml.transform.dom.DOMSource;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Element;

public class SoapInterceptor implements ClientInterceptor {
	private static final Logger log = LoggerFactory.getLogger(ClientInterceptor.class);

	@Override
	// remove the namespace elements from request element
	public boolean handleRequest(org.springframework.ws.context.MessageContext messageContext)
			throws WebServiceClientException {
		// TODO Auto-generated method stub
		SoapMessage soapMessage = (SoapMessage) messageContext.getRequest();
		soapMessage.getEnvelope().addNamespaceDeclaration("ns3", "http://www.onvif.org/ver10/media/wsdl");
		log.info("updated soap header = " + soapMessage.getEnvelope().toString());

		SoapBody soapBody = soapMessage.getSoapBody();
		Source bodySource = soapBody.getPayloadSource();
		DOMSource bodyDomSource = (DOMSource) bodySource;

		Element el = (Element) bodyDomSource.getNode(); // the request eleemnt
	

		NamedNodeMap attributes = el.getAttributes();
		
		ArrayList<String> s = new ArrayList<String>();
		for (int k = 0; k < attributes.getLength(); k++) {
		 {
			String attr = attributes.item(k).getNodeName();
			log.info("att: " + attr);
			s.add(attr);
					}
		}
		 for (Iterator<String> it = s.iterator(); it.hasNext(); ) {
	            String fruit = it.next(); // Get the next element
	            el.removeAttribute(fruit);	
	        }
		

		return true;
	}

	@Override
	public boolean handleResponse(org.springframework.ws.context.MessageContext messageContext)
			throws WebServiceClientException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean handleFault(org.springframework.ws.context.MessageContext messageContext)
			throws WebServiceClientException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void afterCompletion(org.springframework.ws.context.MessageContext messageContext, Exception ex)
			throws WebServiceClientException {
		// TODO Auto-generated method stub

	}

	// ... other methods here
}