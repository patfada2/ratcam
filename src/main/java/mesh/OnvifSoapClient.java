package mesh;

import org.springframework.web.bind.annotation.RequestAttribute;

import feign.Headers;
import feign.RequestLine;
import onvif.GetSnapshotUri;
import onvif.GetSnapshotUriResponse;

public interface  OnvifSoapClient {
	

    @RequestLine("POST")
    @Headers({"Content-Type: text/xml;charset=UTF-8"})
    GetSnapshotUriResponse getSnapshot(GetSnapshotUri request);
}
