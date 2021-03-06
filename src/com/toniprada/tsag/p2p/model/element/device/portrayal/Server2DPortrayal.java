/**
 * 
 */
package com.toniprada.tsag.p2p.model.element.device.portrayal;

import java.awt.Color;
import java.awt.Graphics2D;

import com.toniprada.tsag.p2p.model.element.device.Server;

import sim.portrayal.DrawInfo2D;
import es.upm.dit.gsi.shanks.model.element.device.Device;
import es.upm.dit.gsi.shanks.model.element.device.portrayal.Device2DPortrayal;

/**
 * @author a.carrera
 * 
 */
public class Server2DPortrayal extends Device2DPortrayal {

	private static final long serialVersionUID = -5069108916954757348L;

	@Override
	public void draw(Object object, Graphics2D graphics, DrawInfo2D info) {

		Device device = (Device) object;
		final double width = 20;
		final double height = 20;

		String status = device.getCurrentStatus();
		if (status.equals(Server.STATUS_OK)) {
			graphics.setColor(Color.green);
		} else if (status.equals(Server.STATUS_OVERLOADED)) {
			graphics.setColor(Color.red);
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
