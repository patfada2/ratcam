package mesh;


import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Iterator;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.transform.Source;
import javax.xml.transform.dom.DOMSource;

//import org.springframework.ws.soap.SoapBody;
//import org.springframework.ws.soap.SoapMessage;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import feign.soap.SOAPDecoder;

public class OnvifSoapInterceptor implements RequestInterceptor {
 
	
private SOAPDecoder soapDecoder;

    public void setSoapDecoder(SOAPDecoder soapDecoder) {
	this.soapDecoder = soapDecoder;
}
   private  String GET_SNAPHOT_REQEST = "<soap:Envelope xmlns:soap=\"http://www.w3.org/2003/05/soap-envelope\" xmlns:wsdl=\"http://www.onvif.org/ver10/media/wsdl\">\n"
			+ "	   <soap:Header/>\n"
			+ "	   <soap:Body>\n"
			+ "	      <wsdl:GetSnapshotUri>\n"
			+ "	         <wsdl:ProfileToken>pat1</wsdl:ProfileToken>\n"
			+ "	      </wsdl:GetSnapshotUri>\n"
			+ "	   </soap:Body>\n"
			+ "	</soap:Envelope>";
   
   private String PULL_MESSAGES_REQEST = "<soap:Envelope xmlns:soap=\"http://www.w3.org/2003/05/soap-envelope\" xmlns:wsdl=\"http://www.onvif.org/ver10/events/wsdl\">\r\n"
   		+ "   <soap:Header/>\r\n"
   		+ "   <soap:Body>\r\n"
   		+ "      <wsdl:PullMessages>\r\n"
   		+ "        \r\n"
   		+ "      </wsdl:PullMessages>\r\n"
   		+ "   </soap:Body>\r\n"
   		+ "</soap:Envelope>";
    

	@Override
    public void apply(RequestTemplate requestTemplate) {
		//System.out.println(requestTemplate.toString());
		if (requestTemplate.toString().contains("GetSnapshotUri")){
			requestTemplate.body(GET_SNAPHOT_REQEST);
			System.out.println("getSnapshotUri");
			
		}
    	
		else {
			requestTemplate.body(PULL_MESSAGES_REQEST);
			System.out.println("pull messages");
		}

      
         
    }
}