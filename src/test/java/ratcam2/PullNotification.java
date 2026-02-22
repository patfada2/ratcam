package ratcam2;

import static org.junit.jupiter.api.Assertions.*;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.junit.jupiter.api.Test;

import onvif.ItemList;
import onvif.Message;
import onvif.NotificationMessageHolderType;
import onvif.PropertyOperation;
import onvif.PullMessagesResponse;
import onvif.TopicExpressionType;
import onvif.SimpleItem;


class PullNotification {
	
	//output from camera

	private String PULL_MESSAGE_RESPONSE = "<tev:PullMessagesResponse xmlns:SOAP-ENV=\"http://www.w3.org/2003/05/soap-envelope\" xmlns:SOAP-ENC=\"http://www.w3.org/2003/05/soap-encoding\" xmlns:tev=\"http://www.onvif.org/ver10/events/wsdl\" xmlns:wsnt=\"http://docs.oasis-open.org/wsn/b-2\" xmlns:wsa5=\"http://www.w3.org/2005/08/addressing\" xmlns:chan=\"http://schemas.microsoft.com/ws/2005/02/duplex\" xmlns:wsa=\"http://www.w3.org/2005/08/addressing\" xmlns:tt=\"http://www.onvif.org/ver10/schema\" xmlns:tns1=\"http://www.onvif.org/ver10/topics\">\r\n"
			+ "<tev:CurrentTime>2026-02-19T02:27:29Z</tev:CurrentTime>\r\n"
			+ "<tev:TerminationTime>2026-02-19T02:32:29Z</tev:TerminationTime>\r\n" + "<wsnt:NotificationMessage>\r\n"
			+ "<wsnt:Topic Dialect=\"http://www.onvif.org/ver10/tev/topicExpression/ConcreteSet\">tns1:RuleEngine/CellMotionDetector/Motion</wsnt:Topic>\r\n"
			+ "<wsnt:Message>\r\n" + "<tt:Message UtcTime=\"2026-02-19T02:27:29Z\" PropertyOperation=\"Changed\">\r\n"
			+ "<tt:Source>\r\n"
			+ "<tt:SimpleItem Name=\"VideoSourceConfigurationToken\" Value=\"videoSourceToken\" />\r\n"
			+ "<tt:SimpleItem Name=\"VideoAnalyticsConfigurationToken\" Value=\"VideoAnalyticsToken\" />\r\n"
			+ "<tt:SimpleItem Name=\"Rule\" Value=\"MyMotionDetectorRule\" />\r\n" + "</tt:Source>\r\n"
			+ "<tt:Data>\r\n" + "<tt:SimpleItem Name=\"IsMotion\" Value=\"true\" />\r\n" + "</tt:Data>\r\n"
			+ "</tt:Message>\r\n" + "</wsnt:Message>\r\n" + "</wsnt:NotificationMessage>\r\n"
			+ "</tev:PullMessagesResponse>\r\n";

	private String PULL_MESSAGE_RESPONSE_2 = "<tev:PullMessagesResponse xmlns:SOAP-ENV=\"http://www.w3.org/2003/05/soap-envelope\" xmlns:SOAP-ENC=\"http://www.w3.org/2003/05/soap-encoding\" xmlns:tev=\"http://www.onvif.org/ver10/events/wsdl\" xmlns:wsnt=\"http://docs.oasis-open.org/wsn/b-2\" xmlns:wsa5=\"http://www.w3.org/2005/08/addressing\" xmlns:chan=\"http://schemas.microsoft.com/ws/2005/02/duplex\" xmlns:wsa=\"http://www.w3.org/2005/08/addressing\" xmlns:tt=\"http://www.onvif.org/ver10/schema\" xmlns:tns1=\"http://www.onvif.org/ver10/topics\">\r\n"
			+ "<tev:CurrentTime>2026-02-19T02:27:29Z</tev:CurrentTime>\r\n"
			+ "<tev:TerminationTime>2026-02-19T02:32:29Z</tev:TerminationTime>\r\n" + "<wsnt:NotificationMessage>\r\n"
			+ "<wsnt:Topic Dialect=\"http://www.onvif.org/ver10/tev/topicExpression/ConcreteSet\">tns1:RuleEngine/CellMotionDetector/Motion</wsnt:Topic>\r\n"
			+ "</wsnt:NotificationMessage>\r\n" + "</tev:PullMessagesResponse>\r\n";
	
	//output of mrashall test
	private String PULL_MESSAGE_RESPONSE_3 = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\r\n"
			+ "<PullMessagesResponse xmlns=\"http://www.onvif.org/ver10/events/wsdl\" xmlns:ns5=\"http://www.w3.org/2005/08/addressing\" xmlns:ns2=\"http://docs.oasis-open.org/wsn/b-2\" xmlns:ns4=\"http://www.onvif.org/ver10/media/wsdl\" xmlns:ns3=\"http://www.onvif.org/ver10/schema\">\r\n"
			+ "    <CurrentTime>2024-01-07T14:30:00</CurrentTime>\r\n"
			+ "    <TerminationTime>2024-01-07T14:30:00</TerminationTime>\r\n"
			+ "    <ns2:NotificationMessage>\r\n"
			+ "        <ns2:Topic Dialect=\"adialect\"/>\r\n"
			+ "        <ns2:Message>\r\n"
			+ "            <ns3:Message UtcTime=\"2024-01-07T14:30:00\" PropertyOperation=\"Changed\">\r\n"
			+ "                <ns3:Source>\r\n"
			+ "                    <ns3:simpleItem Name=\"hello\" Value=\"world\"/>\r\n"
			+ "                    <ns3:simpleItem Name=\"hello2\" Value=\"world2\"/>\r\n"
			+ "                </ns3:Source>\r\n"
			+ "                <ns3:Data>\r\n"
			+ "                    <ns3:simpleItem Name=\"hello\" Value=\"world\"/>\r\n"
			+ "                </ns3:Data>\r\n"
			+ "            </ns3:Message>\r\n"
			+ "        </ns2:Message>\r\n"
			+ "    </ns2:NotificationMessage>\r\n"
			+ "</PullMessagesResponse>\r\n"
			+ "";

	@Test
	void marshall() {
		PullMessagesResponse pmr = new PullMessagesResponse();
		XMLGregorianCalendar xmlCal = null;
		try {
			String dateString = "2024-01-07T14:30:00";
			DatatypeFactory df = DatatypeFactory.newInstance();

			// Parse the string to create the XMLGregorianCalendar
			xmlCal = df.newXMLGregorianCalendar(dateString);

			System.out.println(xmlCal); // Output: 2024-01-07T14:30:00
		} catch (javax.xml.datatype.DatatypeConfigurationException e) {
			e.printStackTrace();
		}

		pmr.setTerminationTime(xmlCal);
		pmr.setCurrentTime(xmlCal);
		NotificationMessageHolderType message = new NotificationMessageHolderType();
		TopicExpressionType t = new TopicExpressionType();
		t.setDialect("adialect");

		message.setTopic(t);
		
		NotificationMessageHolderType.Message msg = new NotificationMessageHolderType.Message();
		SimpleItem si = new SimpleItem();
		si.setName("hello");
		si.setValue("world");
		
		SimpleItem si2 = new SimpleItem();
		si2.setName("hello2");
		si2.setValue("world2");

		Message m = new Message();
		m.setPropertyOperation(PropertyOperation.CHANGED);
		m.setUtcTime(xmlCal);
		
		ItemList s =new ItemList();
		s.getSimpleItem().add(si);
		s.getSimpleItem().add(si2);
		m.setSource(s);
		
		ItemList d =new ItemList();
		d.getSimpleItem().add(si);
		m.setData(d);
		
		msg.setAny(m);
		message.setMessage(msg);
		pmr.getNotificationMessage().add(message);
		

		JAXBContext context;
		try {
			context = JAXBContext.newInstance(PullMessagesResponse.class);
			Marshaller mar = context.createMarshaller();
			mar.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			StringWriter sw = new StringWriter();
			mar.marshal(pmr, sw);
			System.out.println(sw.toString());

		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Test
	void unmarshal() {
		
		
		System.out.println(PULL_MESSAGE_RESPONSE);
		
		//System.out.println(PULL_MESSAGE_RESPONSE_3);

		JAXBContext context;
		try {
			context = JAXBContext.newInstance(PullMessagesResponse.class);
			Unmarshaller unmarshaller = context.createUnmarshaller();
			StringReader reader = new StringReader(PULL_MESSAGE_RESPONSE);

			PullMessagesResponse response = (PullMessagesResponse) unmarshaller.unmarshal(reader);

			List<NotificationMessageHolderType> messages = (List<NotificationMessageHolderType>) response
					.getNotificationMessage();
			NotificationMessageHolderType message = (NotificationMessageHolderType) messages.get(0);

			TopicExpressionType topic = message.getTopic();

			assertNotNull(message.getTopic());
			System.out.println(topic.toString());

		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
