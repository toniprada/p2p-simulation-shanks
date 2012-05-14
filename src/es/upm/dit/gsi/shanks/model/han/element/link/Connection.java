/**
 * 
 */
package es.upm.dit.gsi.shanks.model.han.element.link;

import es.upm.dit.gsi.shanks.model.element.exception.UnsupportedNetworkElementStatusException;
import es.upm.dit.gsi.shanks.model.element.link.Link;

/**
 * @author a.carrera
 * 
 */
public class Connection extends Link {
	
	public static final double UPLOAD = 0.3;

	public static final String STATUS_CONNECTED = "CONNECTED";
	public static final String STATUS_DISCONNECTED = "DISCONNECTED";
	public static final String STATUS_OVERLOADED = "OVERLOADED";
	
	public static final String PROPERTY_BANDWIDTH_USAGE = "BANDWIDTH_USAGE"; 

	/**
	 * Constructor ethernet cable
	 * 
	 * @param id
	 * @param length
	 * @throws UnsupportedNetworkElementStatusException
	 */
	public Connection(String id)
			throws UnsupportedNetworkElementStatusException {
		super(id, Connection.STATUS_CONNECTED, 2);
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
		this.addProperty(Connection.PROPERTY_BANDWIDTH_USAGE, 0.0);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * es.upm.dit.gsi.shanks.model.element.NetworkElement#setPossibleStates()
	 */
	@Override
	public void setPossibleStates() {
		this.addPossibleStatus(Connection.STATUS_CONNECTED);
		this.addPossibleStatus(Connection.STATUS_DISCONNECTED);
		this.addPossibleStatus(Connection.STATUS_OVERLOADED);
	}
	
	public void changeUsage(double bandwidth) {
		try {
			double actual = (Double) this.getProperty(Connection.PROPERTY_BANDWIDTH_USAGE);	
			this.changeProperty(Connection.PROPERTY_BANDWIDTH_USAGE, actual + bandwidth);
		} catch (UnsupportedNetworkElementStatusException e) {
		}
	}
	
	public void removeUsage() {
		try {
			this.changeProperty(Connection.PROPERTY_BANDWIDTH_USAGE, 0.0);
		} catch (UnsupportedNetworkElementStatusException e) {
		}
	}

	public double getUsage() {
		return (Double) this.getProperty(Connection.PROPERTY_BANDWIDTH_USAGE);	
	}
	
}
