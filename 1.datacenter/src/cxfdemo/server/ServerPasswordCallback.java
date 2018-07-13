package cxfdemo.server;

import java.io.IOException;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;

import org.apache.wss4j.common.ext.WSPasswordCallback;

public class ServerPasswordCallback implements CallbackHandler {

	@Override
	public void handle(Callback[] callbacks) throws IOException,

	UnsupportedCallbackException {

		WSPasswordCallback pc = (WSPasswordCallback) callbacks[0];

		String identifier = pc.getIdentifier();

		String password = pc.getPassword();
		

		//if ("admin".equals(identifier) && "888888".equals(password)) {
		if ("admin".equals(identifier) ) {
			
			pc.setPassword("888888");

			System.out.println("验证通过！");
		} else {

			throw new SecurityException("验证失败");

		}

	}
}