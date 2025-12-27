package mesh;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


// see https://spring.io/guides/gs/serving-web-content
// and https://www.baeldung.com/thymeleaf-in-spring-mvc
@Controller
public class DisplayController {
    @RequestMapping("/")
    public String home(){
        return "index";
    }
}