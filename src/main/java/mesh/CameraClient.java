package mesh;

import javax.xml.soap.SOAPConstants;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;


import feign.Feign;
import feign.Logger;
import feign.jaxb.JAXBContextFactory;
import feign.slf4j.Slf4jLogger;
import feign.soap.SOAPDecoder;
import feign.soap.SOAPEncoder;
import feign.soap.SOAPErrorDecoder;
import onvif.GetSnapshotUri;
import onvif.GetSnapshotUriResponse;
import javax.xml.soap.SOAPConstants;


public class CameraClient  {
	
	private JAXBContextFactory jaxbFactory;
	OnvifSoapClient client;
	
    public  CameraClient(){

    	jaxbFactory = new JAXBContextFactory.Builder()
    		      .withMarshallerJAXBEncoding("UTF-8").build();

    	SOAPEncoder.Builder soapEncoderbuilder = new SOAPEncoder.Builder();
    	SOAPEncoder soapEncoder = soapEncoderbuilder.withJAXBContextFactory(jaxbFactory).withSOAPProtocol(SOAPConstants.SOAP_1_2_PROTOCOL).build();
    	
    	SOAPDecoder.Builder soapDecoderBuilder = new SOAPDecoder.Builder();
    	SOAPDecoder soapDecoder = soapDecoderBuilder.withJAXBContextFactory(jaxbFactory).withSOAPProtocol(SOAPConstants.SOAP_1_2_PROTOCOL).build();
    	OnvifSoapInterceptor interceptor = new OnvifSoapInterceptor();
    	interceptor.setSoapDecoder(soapDecoder);
    	
    	 client = Feign.builder()
    			  .encoder(soapEncoder)
    			  .errorDecoder(new SOAPErrorDecoder())
    			  .logger(new Slf4jLogger())
    			  .logLevel(Logger.Level.FULL)
    			  .decoder(soapDecoder)
    			  .requestInterceptor( new OnvifSoapInterceptor())
    			  .target(OnvifSoapClient.class, "http://192.168.10.109:80/onvif/device_service");
    	
    }

    public void getSnapshot() throws Exception {
    	
        //System.out.println("Application started. Invoking MyBean's method...");
        //onvifClient.getSnapshot();
        //onvifClient.getEvents();
    
    	

   	 	GetSnapshotUri request = new GetSnapshotUri();
   	 	request.setProfileToken("hello");
    	GetSnapshotUriResponse response = client.getSnapshot(request);
    	
    	System.out.println("snapshot url :" +  response.getMediaUri().getUri()) ;

        
    }
}