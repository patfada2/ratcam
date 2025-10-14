package ratcam2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class RatCamRunner implements CommandLineRunner {

	@Autowired
    private OnvifClient onvifClient;

   
    @Override
    public void run(String... args) throws Exception {
        System.out.println("Application started. Invoking MyBean's method...");
        onvifClient.getSnapshot();
    }
}