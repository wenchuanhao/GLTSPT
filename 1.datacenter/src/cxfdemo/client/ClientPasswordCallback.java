package cxfdemo.client;

import java.io.IOException;   

import javax.security.auth.callback.Callback;   
import javax.security.auth.callback.CallbackHandler;   
import javax.security.auth.callback.UnsupportedCallbackException;   

import org.apache.wss4j.common.ext.WSPasswordCallback;
public class ClientPasswordCallback implements CallbackHandler {   
    @Override  
    public void handle(Callback[] callbacks) throws IOException,   
            UnsupportedCallbackException {   
        WSPasswordCallback pc = (WSPasswordCallback) callbacks[0];  
   
        pc.setIdentifier("admin"); 
        
        pc.setPassword("888888");   
    }   
}  
