/**
 * 
 */
package es.upm.dit.gsi.shanks.model.adsl.element.device;

import es.upm.dit.gsi.shanks.model.element.exception.UnsupportedNetworkElementStatusException;
import es.upm.dit.gsi.shanks.model.han.element.device.Modem;

/**
 * @author a.carrera
 *
 */
public class ADSLModem extends Modem {



    public ADSLModem(String id)
			throws UnsupportedNetworkElementStatusException {
		super(id, OK_STATUS, true);
	}

	public static final String OK_STATUS = "OK";
    public static final String NOK_STATUS = "NOK";
    public static final String UNKOWN_STATUS = "UNKOWN";
    
    public static final String PROPERTY_MODEL = "Model";
    public static final String TEMPERATURE_PROPERTY = "Temperature";
    
    /* (non-Javadoc)
     * @see es.upm.dit.gsi.shanks.model.element.NetworkElement#setPossibleStates()
     */
    @Override
    public void setPossibleStates() {
        this.addPossibleStatus(ADSLModem.OK_STATUS);
        this.addPossibleStatus(ADSLModem.NOK_STATUS);
        this.addPossibleStatus(ADSLModem.UNKOWN_STATUS);

    }

    /* (non-Javadoc)
     * @see es.upm.dit.gsi.shanks.model.element.NetworkElement#fillIntialProperties()
     */
    @Override
    public void fillIntialProperties() {
        this.addProperty(ADSLModem.PROPERTY_MODEL, "SAGEM");
        this.addProperty(ADSLModem.TEMPERATURE_PROPERTY, 15.5);
    }

    /* (non-Javadoc)
     * @see es.upm.dit.gsi.shanks.model.element.NetworkElement#checkProperties()
     */
    @Override
    public void checkProperties() throws UnsupportedNetworkElementStatusException {
        String status = this.getCurrentStatus();
        if (status.equals(ADSLModem.OK_STATUS)) {
            this.changeProperty(ADSLModem.TEMPERATURE_PROPERTY, 30);
        } else if (status.equals(ADSLModem.NOK_STATUS)) {
            this.changeProperty(ADSLModem.TEMPERATURE_PROPERTY, 90);
        } else if (status.equals(ADSLModem.UNKOWN_STATUS)) {
            this.changeProperty(ADSLModem.TEMPERATURE_PROPERTY, null);
        }
    }

    /* (non-Javadoc)
     * @see es.upm.dit.gsi.shanks.model.element.NetworkElement#checkStatus()
     */
    @Override
    public void checkStatus() throws UnsupportedNetworkElementStatusException {
        Integer temp = (Integer) this.getProperty(ADSLModem.TEMPERATURE_PROPERTY);
        if (temp<70) {
            this.updateStatusTo(ADSLModem.OK_STATUS);
        }
    }

}
