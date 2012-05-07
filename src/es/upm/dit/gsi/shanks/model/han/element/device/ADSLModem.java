/**
 * 
 */
package es.upm.dit.gsi.shanks.model.han.element.device;

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
    public static final String HIGHTEMP_STATUS = "HIGHTEMP";
    public static final String DDOS_ATTACK = "DDOS";
    public static final String PROXY_CONFG_ERROR = "PROXY_CONFG_ERROR";
    public static final String MTU_SPEED = "MTU_SPEED";
    
    public static final String PROPERTY_MODEL = "Model";
    public static final String TEMPERATURE_PROPERTY = "Temperature";
    public static final String BIT_RATE = "Bit Rate";
    public static final String PROXY_CONF = "Proxy Configuration";
    
    /* (non-Javadoc)
     * @see es.upm.dit.gsi.shanks.model.element.NetworkElement#setPossibleStates()
     */
    @Override
    public void setPossibleStates() {
        this.addPossibleStatus(ADSLModem.OK_STATUS);
        this.addPossibleStatus(ADSLModem.NOK_STATUS);
        this.addPossibleStatus(ADSLModem.UNKOWN_STATUS);
        this.addPossibleStatus(ADSLModem.HIGHTEMP_STATUS);
        this.addPossibleStatus(ADSLModem.DDOS_ATTACK);
        this.addPossibleStatus(ADSLModem.PROXY_CONFG_ERROR);
        this.addPossibleStatus(ADSLModem.MTU_SPEED);

    }

    /* (non-Javadoc)
     * @see es.upm.dit.gsi.shanks.model.element.NetworkElement#fillIntialProperties()
     */
    @Override
    public void fillIntialProperties() {
        this.addProperty(ADSLModem.PROPERTY_MODEL, "SAGEM");
        this.addProperty(ADSLModem.TEMPERATURE_PROPERTY, 15.5);
        this.addProperty(ADSLModem.BIT_RATE, 20000000); //20Mbps
        this.addProperty(ADSLModem.PROXY_CONF, true);
    }

    /* (non-Javadoc)
     * @see es.upm.dit.gsi.shanks.model.element.NetworkElement#checkProperties()
     */
    @Override
    public void checkProperties() throws UnsupportedNetworkElementStatusException {
        String status = this.getCurrentStatus();
        if (status.equals(ADSLModem.OK_STATUS)) {
            this.changeProperty(ADSLModem.TEMPERATURE_PROPERTY, 30);
            this.changeProperty(ADSLModem.BIT_RATE, 20000000); //20Mbps
            this.changeProperty(ADSLModem.PROXY_CONF, true);  
            
        } else if (status.equals(ADSLModem.NOK_STATUS)) {
            this.changeProperty(ADSLModem.TEMPERATURE_PROPERTY, 90);
            this.changeProperty(ADSLModem.BIT_RATE, 1000000); //1Mbps
            this.changeProperty(ADSLModem.PROXY_CONF, false);
            
        } else if (status.equals(ADSLModem.UNKOWN_STATUS)) {
            this.changeProperty(ADSLModem.TEMPERATURE_PROPERTY, null);
            this.changeProperty(ADSLModem.BIT_RATE, null);
            this.changeProperty(ADSLModem.PROXY_CONF, null);
            
        }else if (status.equals(ADSLModem.DDOS_ATTACK)){
        	this.changeProperty(ADSLModem.TEMPERATURE_PROPERTY, 30);
            this.changeProperty(ADSLModem.BIT_RATE, 1000);
            this.changeProperty(ADSLModem.PROXY_CONF, false);
            
        }else if (status.equals(ADSLModem.PROXY_CONFG_ERROR)){
        	this.changeProperty(ADSLModem.TEMPERATURE_PROPERTY, 30);
            this.changeProperty(ADSLModem.BIT_RATE, 20000000);
            this.changeProperty(ADSLModem.PROXY_CONF, false);
            
        }else if(status.equals(ADSLModem.HIGHTEMP_STATUS)){
        	this.changeProperty(ADSLModem.TEMPERATURE_PROPERTY, 90);
            this.changeProperty(ADSLModem.BIT_RATE, 20000000);
            this.changeProperty(ADSLModem.PROXY_CONF, true);
            
        }else if (status.equals(ADSLModem.MTU_SPEED)){
        	this.changeProperty(ADSLModem.TEMPERATURE_PROPERTY, 30);
            this.changeProperty(ADSLModem.BIT_RATE, 1000);
            this.changeProperty(ADSLModem.PROXY_CONF, true);
        }
    }

    /* (non-Javadoc)
     * @see es.upm.dit.gsi.shanks.model.element.NetworkElement#checkStatus()
     */
    @Override
    public void checkStatus() throws UnsupportedNetworkElementStatusException {
        Integer temp = (Integer) this.getProperty(ADSLModem.TEMPERATURE_PROPERTY);
        Integer speed = (Integer) this.getProperty(ADSLModem.BIT_RATE);
        boolean proxy = (Boolean) this.getProperty(ADSLModem.PROXY_CONF);
        if (temp<70 && speed > 12000000 && proxy) {
            this.updateStatusTo(ADSLModem.OK_STATUS);
        }else if(temp>70 && speed < 12000000 && !proxy){
        	this.updateStatusTo(ADSLModem.NOK_STATUS);
        }else if(temp<70 && speed < 12000000 && !proxy){
        	this.updateStatusTo(ADSLModem.DDOS_ATTACK);
        }else if(temp<70 && speed > 12000000 && !proxy){
        	this.updateStatusTo(ADSLModem.PROXY_CONFG_ERROR);
        }else if(temp<70 && speed < 12000000 && proxy){
        	this.updateStatusTo(ADSLModem.MTU_SPEED);
        }else if(temp>70 && speed > 12000000 && proxy){
        	this.updateStatusTo(ADSLModem.HIGHTEMP_STATUS);
        }else{
        	this.updateStatusTo(ADSLModem.UNKOWN_STATUS);
        }
    }
}
