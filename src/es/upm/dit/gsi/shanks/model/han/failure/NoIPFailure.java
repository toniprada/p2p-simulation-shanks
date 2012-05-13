/**
 * 
 */
package es.upm.dit.gsi.shanks.model.han.failure;

import java.util.Set;

import es.upm.dit.gsi.shanks.model.element.NetworkElement;
import es.upm.dit.gsi.shanks.model.failure.Failure;
import es.upm.dit.gsi.shanks.model.han.element.device.Client;
import es.upm.dit.gsi.shanks.model.han.element.device.EthernetRouter;

/**
 * @author a.carrera
 * 
 */
public class NoIPFailure extends Failure {

	public NoIPFailure() {
		super(NoIPFailure.class.getName(), 0.01);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * es.upm.dit.gsi.shanks.model.failure.Failure#addPossibleAffectedElements()
	 */
	@Override
	public void addPossibleAffectedElements() {
		this.addPossibleAffectedElements(EthernetRouter.class,
				EthernetRouter.STATUS_NODHCP);
		this.addPossibleAffectedElements(Client.class,
				Client.STATUS_OVERLOADED);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see es.upm.dit.gsi.shanks.model.failure.Failure#isResolved()
	 */
	@Override
	public boolean isResolved() {
		Set<NetworkElement> elements = this.getAffectedElements().keySet();
		boolean resolved = false;
		for (NetworkElement element : elements) {
			if (element instanceof EthernetRouter) {
				String dhcp = (String) element
						.getProperty(EthernetRouter.PROPERTY_DHCP);
				if (dhcp.equals("ON")) {
					resolved = true;
				} else {
					resolved = false;
					break;
				}
			} else if (element instanceof Client) {
//				String connection = (String) element
//						.getProperty(Computer.PROPERTY_CONNECTION);
//				if (connection.equals("IP OK")) {
//					resolved = true;
//				} else {
//					resolved = false;
//					break;
//				}
//
			}
		}
		return resolved;
	}

}
