package mesh;

import javax.xml.soap.SOAPConstants;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import ch.qos.logback.core.joran.spi.HttpUtil.RequestMethod;
import feign.Feign;
import feign.Logger;
import feign.jaxb.JAXBContextFactory;
import feign.slf4j.Slf4jLogger;
import feign.soap.SOAPDecoder;
import feign.soap.SOAPEncoder;
import feign.soap.SOAPErrorDecoder;
import onvif.GetSnapshotUri;
import onvif.GetSnapshotUriResponse;
import onvif.PullMessages;
import onvif.PullMessagesResponse;
import onvif.NotificationMessageHolderType;

import javax.xml.soap.SOAPConstants;

public class CameraClient {

	private JAXBContextFactory jaxbFactory;
	OnvifSoapClient client;

	public CameraClient() {

		jaxbFactory = new JAXBContextFactory.Builder().withMarshallerJAXBEncoding("UTF-8").build();

		SOAPEncoder.Builder soapEncoderbuilder = new SOAPEncoder.Builder();
		SOAPEncoder soapEncoder = soapEncoderbuilder.withJAXBContextFactory(jaxbFactory)
				.withSOAPProtocol(SOAPConstants.SOAP_1_2_PROTOCOL).build();

		SOAPDecoder.Builder soapDecoderBuilder = new SOAPDecoder.Builder();
		SOAPDecoder soapDecoder = soapDecoderBuilder.withJAXBContextFactory(jaxbFactory)
				.withSOAPProtocol(SOAPConstants.SOAP_1_2_PROTOCOL).build();
		OnvifSoapInterceptor interceptor = new OnvifSoapInterceptor();
		interceptor.setSoapDecoder(soapDecoder);

		client = Feign.builder().encoder(soapEncoder).errorDecoder(new SOAPErrorDecoder()).logger(new Slf4jLogger())
				.logLevel(Logger.Level.FULL).decoder(soapDecoder).requestInterceptor(new OnvifSoapInterceptor())
				.target(OnvifSoapClient.class, "http://192.168.10.109:80/onvif/device_service");

	}

	public String getSnapshot() throws Exception {

		// System.out.println("Application started. Invoking MyBean's method...");
		// onvifClient.getSnapshot();
		// onvifClient.getEvents();
		String url = "?";

		GetSnapshotUri request = new GetSnapshotUri();
		request.setProfileToken("hello");
		GetSnapshotUriResponse response = client.getSnapshot(request);
		url = response.getMediaUri().getUri();
		return url;

	}

	// returns true if there si a notification messgae

	public boolean pullMessages() throws Exception {
		boolean result = false;

		PullMessages request = new PullMessages();
		Object obj = client.pullMessages(request);

		PullMessagesResponse response = (PullMessagesResponse) obj;
		


		if (response.getNotificationMessage() != null) {
			if (!response.getNotificationMessage().isEmpty())
				result = true;
		}
		return result;

	}
}