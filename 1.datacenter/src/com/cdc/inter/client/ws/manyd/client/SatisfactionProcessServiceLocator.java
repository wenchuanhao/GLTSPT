/**
 * SatisfactionProcessServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.cdc.inter.client.ws.manyd.client;

import com.cdc.common.properties.DCConfig;

public class SatisfactionProcessServiceLocator extends org.apache.axis.client.Service implements com.cdc.inter.client.ws.manyd.client.SatisfactionProcessService {

    public SatisfactionProcessServiceLocator() {
    }


    public SatisfactionProcessServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public SatisfactionProcessServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for satisfactionSurvey
    private java.lang.String satisfactionSurvey_address = DCConfig.getProperty("MANYD_URL");

    public java.lang.String getsatisfactionSurveyAddress() {
        return satisfactionSurvey_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String satisfactionSurveyWSDDServiceName = "satisfactionSurvey";

    public java.lang.String getsatisfactionSurveyWSDDServiceName() {
        return satisfactionSurveyWSDDServiceName;
    }

    public void setsatisfactionSurveyWSDDServiceName(java.lang.String name) {
        satisfactionSurveyWSDDServiceName = name;
    }

    public com.cdc.inter.client.ws.manyd.client.SatisfactionProcess getsatisfactionSurvey() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(satisfactionSurvey_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getsatisfactionSurvey(endpoint);
    }

    public com.cdc.inter.client.ws.manyd.client.SatisfactionProcess getsatisfactionSurvey(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            com.cdc.inter.client.ws.manyd.client.SatisfactionSurveySoapBindingStub _stub = new com.cdc.inter.client.ws.manyd.client.SatisfactionSurveySoapBindingStub(portAddress, this);
            _stub.setPortName(getsatisfactionSurveyWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setsatisfactionSurveyEndpointAddress(java.lang.String address) {
        satisfactionSurvey_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (com.cdc.inter.client.ws.manyd.client.SatisfactionProcess.class.isAssignableFrom(serviceEndpointInterface)) {
                com.cdc.inter.client.ws.manyd.client.SatisfactionSurveySoapBindingStub _stub = new com.cdc.inter.client.ws.manyd.client.SatisfactionSurveySoapBindingStub(new java.net.URL(satisfactionSurvey_address), this);
                _stub.setPortName(getsatisfactionSurveyWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("satisfactionSurvey".equals(inputPortName)) {
            return getsatisfactionSurvey();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName(DCConfig.getProperty("MANYD_URL"), "SatisfactionProcessService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName(DCConfig.getProperty("MANYD_URL"), "satisfactionSurvey"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("satisfactionSurvey".equals(portName)) {
            setsatisfactionSurveyEndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
