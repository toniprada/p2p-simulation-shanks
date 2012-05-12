/**
 * 
 */
package es.upm.dit.gsi.shanks.model.han.element.link;

import java.util.HashMap;

import es.upm.dit.gsi.shanks.model.element.exception.UnsupportedNetworkElementStatusException;
import es.upm.dit.gsi.shanks.model.element.link.Link;
import es.upm.dit.gsi.shanks.model.han.element.device.Server;

/**
 * @author a.carrera
 * 
 */
public class ADSLConnection extends Link {

	public static final String STATUS_CONNECTED = "CONNECTED";
	public static final String STATUS_DISCONNECTED = "DISCONNECTED";
	
	public static final String PROPERTY_BANDWIDTH_USAGE = "BANDWIDTH_USAGE"; 

	/**
	 * Constructor ethernet cable
	 * 
	 * @param id
	 * @param length
	 * @throws UnsupportedNetworkElementStatusException
	 */
	public ADSLConnection(String id)
			throws UnsupportedNetworkElementStatusException {
		super(id, ADSLConnection.STATUS_CONNECTED, 2);
//		this.changeProperty(ADSLCable.PROPERTY_BANDWIDTH, 10.0);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see es.upm.dit.gsi.shanks.model.element.NetworkElement#checkProperties()
	 */
	@Override
	public void checkProperties()
			throws UnsupportedNetworkElementStatusException {
//		String status = this.getCurrentStatus();
//		if (status.equals(ADSLConnection.STATUS_CONNECTED)) {
//			this.changeProperty(ADSLConnection.PROPERTY_BANDWIDTH_USAGE, 10.0);
//		} else if (status.equals(ADSLConnection.STATUS_DISCONNECTED)) {
//			this.changeProperty(ADSLConnection.PROPERTY_BANDWIDTH_USAGE, 0.0);
//		} 
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see es.upm.dit.gsi.shanks.model.element.NetworkElement#checkStatus()
	 */
	@Override
	public void checkStatus() throws UnsupportedNetworkElementStatusException {
//		double bw = (Double) this.getProperty(ADSLConnection.PROPERTY_BANDWIDTH_USAGE);
//		if (bw < 0.1) {
//			this.updateStatusTo(ADSLConnection.STATUS_DISCONNECTED);
//		} else if (bw >= 1) {
//			this.updateStatusTo(ADSLConnection.STATUS_CONNECTED);
//		} else {
//			this.updateStatusTo(ADSLConnection.STATUS_OVERLOADED);
//		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * es.upm.dit.gsi.shanks.model.element.NetworkElement#fillIntialProperties()
	 */
	@Override
	public void fillIntialProperties() {
		this.addProperty(ADSLConnection.PROPERTY_BANDWIDTH_USAGE, 0);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * es.upm.dit.gsi.shanks.model.element.NetworkElement#setPossibleStates()
	 */
	@Override
	public void setPossibleStates() {
		this.addPossibleStatus(ADSLConnection.STATUS_CONNECTED);
		this.addPossibleStatus(ADSLConnection.STATUS_DISCONNECTED);
	}
	
	public void addUsage(double bandwidth) {
		double actual = (Double) this.getProperty(ADSLConnection.PROPERTY_BANDWIDTH_USAGE);
//		if (actual > 0)
//		this.changeProperty(ADSLConnection.PROPERTY_BANDWIDTH_USAGE, );
//		HashMap properties = new HashMap<String, Double>();
//		properties.add(ADSLConnection.PROPERTY_BANDWIDTH_USAGE, Server.STREAMING_BANDWIDTH);
//		link.setProperties(properties)
	}

}
