package ratcam2;




import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.core.SoapActionCallback;

import onvif.wsdl.GetSnapshotUri;
import onvif.wsdl.GetSnapshotUriResponse;



public class OnvifClient extends WebServiceGatewaySupport {

  private static final Logger log = LoggerFactory.getLogger(OnvifClient.class);

  public GetSnapshotUriResponse getSnapshot() {

	 GetSnapshotUri request = new GetSnapshotUri();
	 request.setProfileToken("hello");
	 

    log.info("getting snapshot");

    GetSnapshotUriResponse response = (GetSnapshotUriResponse) getWebServiceTemplate()

        .marshalSendAndReceive("http://192.168.10.109:80/onvif/device_service", request,
            new SoapActionCallback(
                "http://www.onvif.org/ver10/media/wsdl/GetSnapshotUri"));
    log.info("snapshot url :" +  response.getMediaUri().getUri()) ;

    return response;
  }

}