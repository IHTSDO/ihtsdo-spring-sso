package org.ihtsdo.example.ims;

import org.ihtsdo.ims.integration.ControllerHelper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

	@RequestMapping("/")
	public String index() {
		return "Hello " + ControllerHelper.getUsername();
	}

}
