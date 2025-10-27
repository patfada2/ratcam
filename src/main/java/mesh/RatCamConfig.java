package mesh;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration

//@ComponentScan(basePackageClasses = Company.class)
public class RatCamConfig {
	@Bean
	CameraClient getCameraClient(){
		return new CameraClient();
	}

}
