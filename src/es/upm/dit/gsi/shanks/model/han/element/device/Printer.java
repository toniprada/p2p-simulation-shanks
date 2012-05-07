/**
 * 
 */
package es.upm.dit.gsi.shanks.model.han.element.device;

import es.upm.dit.gsi.shanks.model.element.device.Device;
import es.upm.dit.gsi.shanks.model.element.exception.UnsupportedNetworkElementStatusException;

/**
 * @author a.carrera
 *
 */
public class Printer extends Device {

	public Printer(String id, String initialState, boolean isGateway)
			throws UnsupportedNetworkElementStatusException {
		super(id, initialState, isGateway);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see es.upm.dit.gsi.shanks.model.element.NetworkElement#checkProperties()
	 */
	@Override
	public void checkProperties()
			throws UnsupportedNetworkElementStatusException {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see es.upm.dit.gsi.shanks.model.element.NetworkElement#checkStatus()
	 */
	@Override
	public void checkStatus() throws UnsupportedNetworkElementStatusException {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see es.upm.dit.gsi.shanks.model.element.NetworkElement#fillIntialProperties()
	 */
	@Override
	public void fillIntialProperties() {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see es.upm.dit.gsi.shanks.model.element.NetworkElement#setPossibleStates()
	 */
	@Override
	public void setPossibleStates() {
		// TODO Auto-generated method stub

	}

}
