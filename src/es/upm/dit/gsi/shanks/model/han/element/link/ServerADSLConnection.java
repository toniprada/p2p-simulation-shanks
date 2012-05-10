/**
 * 
 */
package es.upm.dit.gsi.shanks.model.han.element.link;

import es.upm.dit.gsi.shanks.model.element.exception.UnsupportedNetworkElementStatusException;

/**
 * @author a.carrera
 * 
 */
public class ServerADSLConnection extends ADSLCable {

	public ServerADSLConnection(String id)
			throws UnsupportedNetworkElementStatusException {
		super(id, ADSLCable.STATUS_CONNECTED);
	}


}
