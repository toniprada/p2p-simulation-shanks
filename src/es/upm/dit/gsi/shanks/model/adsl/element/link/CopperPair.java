/**
 * 
 */
package es.upm.dit.gsi.shanks.model.adsl.element.link;

import es.upm.dit.gsi.shanks.model.element.exception.UnsupportedNetworkElementStatusException;
import es.upm.dit.gsi.shanks.model.element.link.Link;

/**
 * @author a.carrera
 *
 */
public class CopperPair extends Link {

    public static final String OK_STATUS = "OK";
    public static final String BROKEN_STATUS = "BROKEN";
    
    public static final String DISTANCE_PROPERTY = "Distance";
    public static final String LINK_TYPE_PROPERTY = "LinkType";
    
    /**
     * @param id
     * @param initialState
     * @param capacity
     * @throws UnsupportedNetworkElementStatusException
     */
    public CopperPair(String id) throws UnsupportedNetworkElementStatusException {
        super(id, OK_STATUS, 2);
    }

    /* (non-Javadoc)
     * @see es.upm.dit.gsi.shanks.model.element.NetworkElement#setPossibleStates()
     */
    @Override
    public void setPossibleStates() {
        this.addPossibleStatus(CopperPair.OK_STATUS);
        this.addPossibleStatus(CopperPair.BROKEN_STATUS);
    }

    /* (non-Javadoc)
     * @see es.upm.dit.gsi.shanks.model.element.NetworkElement#checkProperties()
     */
    @Override
    public void checkProperties() throws UnsupportedNetworkElementStatusException {
        String status = this.getCurrentStatus();
        if (status.equals(CopperPair.BROKEN_STATUS)) {
            this.changeProperty(CopperPair.DISTANCE_PROPERTY, 0.0);
        } else if (status.equals(CopperPair.OK_STATUS)) {
            this.changeProperty(CopperPair.DISTANCE_PROPERTY, 3.5);
        }
    }

    /* (non-Javadoc)
     * @see es.upm.dit.gsi.shanks.model.element.NetworkElement#fillIntialProperties()
     */
    @Override
    public void fillIntialProperties() {
        this.addProperty(CopperPair.LINK_TYPE_PROPERTY, "Ethernet");
        this.addProperty(CopperPair.DISTANCE_PROPERTY, 3.5);
    }

    /* (non-Javadoc)
     * @see es.upm.dit.gsi.shanks.model.element.NetworkElement#checkStatus()
     */
    @Override
    public void checkStatus() throws UnsupportedNetworkElementStatusException {
        Double distance = (Double) this.getProperty(CopperPair.DISTANCE_PROPERTY);
        if (distance>0){
            this.updateStatusTo(CopperPair.OK_STATUS);
        }
    }
}
