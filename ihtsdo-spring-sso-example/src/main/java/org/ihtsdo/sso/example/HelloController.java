package org.ihtsdo.sso.example;

import org.ihtsdo.sso.integration.SecurityUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

	@RequestMapping("/")
	public String index() {
		return "Hello " + SecurityUtil.getUsername();
	}

}
