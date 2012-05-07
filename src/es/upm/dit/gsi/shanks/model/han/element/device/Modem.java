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
public abstract class Modem extends Device {

	/**
	 * @param id
	 * @param initialState
	 * @param isGateway
	 * @throws UnsupportedNetworkElementStatusException
	 */
	public Modem(String id, String initialState, boolean isGateway)
			throws UnsupportedNetworkElementStatusException {
		super(id, initialState, isGateway);
	}

}
