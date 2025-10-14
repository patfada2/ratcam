package ratcam2;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.ws.WebServiceMessageFactory;
import org.springframework.ws.client.support.interceptor.ClientInterceptor;
import org.springframework.ws.soap.SoapVersion;
import org.springframework.ws.soap.saaj.SaajSoapMessageFactory;
import org.springframework.ws.soap.server.SoapEndpointInterceptor;

@Configuration
public class RatCamConfiguration {

  @Bean
  public Jaxb2Marshaller marshaller() {
    Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
    // this package must match the package in the <generatePackage> specified in
    // pom.xml
    marshaller.setContextPath("onvif.wsdl");
    return marshaller;
  }

 
  @Bean
  public SaajSoapMessageFactory soap12MessageFactory() {
      SaajSoapMessageFactory messageFactory = new SaajSoapMessageFactory();
      messageFactory.setSoapVersion(SoapVersion.SOAP_12);
      return messageFactory;
  }
  
  
  @Bean
  public OnvifClient onvifClient(Jaxb2Marshaller marshaller) {
	 OnvifClient client = new OnvifClient();
    client.setDefaultUri("http://192.168.10.109:80/onvif/device_service");
    client.setMarshaller(marshaller);
    client.setUnmarshaller(marshaller);
    client.setMessageFactory(soap12MessageFactory());
    client.setInterceptors(new ClientInterceptor[] {new SoapInterceptor()});

    return client;
  }
  
 

}