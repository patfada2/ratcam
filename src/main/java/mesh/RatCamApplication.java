package mesh;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@SpringBootApplication
@EnableScheduling
public class RatCamApplication {

	private static final int CONNECT_TIMEOUT = 0;

	private static final int READ_TIMEOUT = 0;

	@Autowired
	CameraClient camera;

	private String FILE_PATH = "C:\\Users\\patri\\git\\ratcam\\snapshots\\";

	private void downloadFile(String url) {

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH_mm_ss");
		LocalDateTime now = LocalDateTime.now();
		String formattedDateTime = now.format(formatter);
		String fileName = FILE_PATH + formattedDateTime + ".cgi";
		System.out.println("saving " + url);

		try {
			FileUtils.copyURLToFile(new URL(url), new File(fileName), CONNECT_TIMEOUT, READ_TIMEOUT);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Scheduled(fixedRate = 10000)
	public void scheduleFixedRateTask() {
		System.out.println("...");
		try {
			
			if (camera.pullMessages()) {
				downloadFile(camera.getSnapshot());
				System.out.println("!!!!motion detected");
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {

		// SpringApplication.run(RatCam3Application.class, args);
		SpringApplication.run(RatCamApplication.class, args);
	}

}
