package cxfdemo.server;

import javax.jws.WebService;

@WebService(endpointInterface = "cxfdemo.server.HelloWorld")
public class HelloWorldImpl implements HelloWorld {

	@Override
	public String sayHi(String name) {

		System.out.println("server: say->" + name);

		return "say-->" + name;

	}
}
