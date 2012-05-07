/**
 * 
 */
package es.upm.dit.gsi.shanks.model.adsl.failure;

import java.util.Set;

import es.upm.dit.gsi.shanks.model.adsl.element.device.ADSLModem;
import es.upm.dit.gsi.shanks.model.element.NetworkElement;
import es.upm.dit.gsi.shanks.model.failure.Failure;

/**
 * @author a.carrera
 *
 */
public class LostSynchronismFailure extends Failure {

	public LostSynchronismFailure() {
		super(LostSynchronismFailure.class.getName(), 0.005);
	}

	/* (non-Javadoc)
	 * @see es.upm.dit.gsi.shanks.model.failure.Failure#addPossibleAffectedElements()
	 */
	@Override
	public void addPossibleAffectedElements() {
		this.addPossibleAffectedElements(ADSLModem.class, ADSLModem.NOK_STATUS);
	}

	@Override
	public boolean isResolved() {
		Set<NetworkElement> elements = this.getAffectedElements().keySet();
		boolean resolved = false;
		for (NetworkElement element : elements) {
			if (element instanceof ADSLModem) {
				if (element.getCurrentStatus().equals(ADSLModem.OK_STATUS)) {
					resolved = true;
				} else {
					resolved = false;
					break;
				}
			}
		}
		return resolved;
	}

}
