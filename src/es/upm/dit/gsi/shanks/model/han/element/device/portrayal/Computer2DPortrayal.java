/**
 * 
 */
package es.upm.dit.gsi.shanks.model.han.element.device.portrayal;

import java.awt.Color;
import java.awt.Graphics2D;

import sim.portrayal.DrawInfo2D;
import es.upm.dit.gsi.shanks.model.element.device.Device;
import es.upm.dit.gsi.shanks.model.element.device.portrayal.Device2DPortrayal;
import es.upm.dit.gsi.shanks.model.han.element.device.Computer;

/**
 * @author a.carrera
 *
 */
public class Computer2DPortrayal extends Device2DPortrayal {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1566002502140466217L;

    /* (non-Javadoc)
     * @see es.upm.dit.gsi.shanks.model.element.device.portrayal.Device2DPortrayal#draw(java.lang.Object, java.awt.Graphics2D, sim.portrayal.DrawInfo2D)
     */
    @Override
    public void draw(Object object, Graphics2D graphics, DrawInfo2D info) {

        Device device = (Device) object;
        final double width = 10;
        final double height = 10;

        String status = device.getCurrentStatus();
        if (status.equals(Computer.STATUS_ON)) {
            graphics.setColor(Color.green);
        } else if (status.equals(Computer.STATUS_OFF)) {
            graphics.setColor(Color.white);
        } else if (status.equals(Computer.STATUS_WAITING)) {
        	graphics.setColor(Color.gray);
        }

        // Draw the devices
        final int x = (int) (info.draw.x - width / 2.0);
        final int y = (int) (info.draw.y - height / 2.0);
        final int w = (int) (width);
        final int h = (int) (height);
        graphics.fillRect(x, y, w, h);

        // Draw the devices ID ID
        graphics.setColor(Color.black);
        graphics.drawString(device.getID(), x - 3, y);

    }
}
