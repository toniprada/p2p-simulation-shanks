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
public class ADSLCable extends Link {

	public static final String STATUS_CONNECTED = "CONNECTED";
	public static final String STATUS_DISCONNECTED = "DISCONNECTED";
	public static final String STATUS_OVERLOADED = "OVERLOADED";
	
	public static final String PROPERTY_BANDWIDTH = "BANDWIDTH"; 

	/**
	 * Constructor ethernet cable
	 * 
	 * @param id
	 * @param length
	 * @throws UnsupportedNetworkElementStatusException
	 */
	public ADSLCable(String id, String status)
			throws UnsupportedNetworkElementStatusException {
		super(id, status, 2);
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
		String status = this.getCurrentStatus();
		if (status.equals(ADSLCable.STATUS_CONNECTED)) {
			this.changeProperty(ADSLCable.PROPERTY_BANDWIDTH, 10.0);
		} else if (status.equals(ADSLCable.STATUS_DISCONNECTED)) {
			this.changeProperty(ADSLCable.PROPERTY_BANDWIDTH, 0.0);
		} else if (status.equals(ADSLCable.STATUS_OVERLOADED)) {
			this.changeProperty(ADSLCable.PROPERTY_BANDWIDTH, 0.5);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see es.upm.dit.gsi.shanks.model.element.NetworkElement#checkStatus()
	 */
	@Override
	public void checkStatus() throws UnsupportedNetworkElementStatusException {
		double ratio = (Double) this.getProperty(ADSLCable.PROPERTY_BANDWIDTH);
		if (ratio < 0.1) {
			this.updateStatusTo(ADSLCable.STATUS_DISCONNECTED);
		} else if (ratio >= 1) {
			this.updateStatusTo(ADSLCable.STATUS_CONNECTED);
		} else {
			this.updateStatusTo(ADSLCable.STATUS_OVERLOADED);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * es.upm.dit.gsi.shanks.model.element.NetworkElement#fillIntialProperties()
	 */
	@Override
	public void fillIntialProperties() {
		this.addProperty(ADSLCable.PROPERTY_BANDWIDTH, 10);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * es.upm.dit.gsi.shanks.model.element.NetworkElement#setPossibleStates()
	 */
	@Override
	public void setPossibleStates() {
		this.addPossibleStatus(ADSLCable.STATUS_CONNECTED);
		this.addPossibleStatus(ADSLCable.STATUS_DISCONNECTED);
		this.addPossibleStatus(ADSLCable.STATUS_OVERLOADED);
	}

}
