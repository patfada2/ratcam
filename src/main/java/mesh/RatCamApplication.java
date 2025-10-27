package mesh;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@SpringBootApplication
@EnableScheduling
public class RatCamApplication {
	
	@Autowired
	CameraClient camera;
	
	
	@Scheduled(fixedRate = 1000)
	public void scheduleFixedRateTask() {
		try {
			camera.getSnapshot();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
	
		//SpringApplication.run(RatCam3Application.class, args);
		SpringApplication.run(RatCamApplication.class,args);
	}

}
