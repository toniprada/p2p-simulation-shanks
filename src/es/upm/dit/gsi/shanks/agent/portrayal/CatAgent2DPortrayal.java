/**
 * 
 */
package es.upm.dit.gsi.shanks.agent.portrayal;

import java.awt.Color;
import java.awt.Graphics2D;

import sim.portrayal.DrawInfo2D;
import es.upm.dit.gsi.shanks.agent.CatAgent;

/**
 * @author a.carrera
 *
 */
public class CatAgent2DPortrayal extends ShanksAgent2DPortrayal {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7990116715142215447L;

    @Override
    public void draw(Object object, Graphics2D graphics, DrawInfo2D info) {

        CatAgent agent = (CatAgent) object;
        final double width = 5;
        final double height = 5;

        // Draw the devices
        final int x = (int) (info.draw.x - width / 2.0);
        final int y = (int) (info.draw.y - height / 2.0);
        final int w = (int) (width);
        final int h = (int) (height);        
        graphics.setColor(Color.orange);
        graphics.fillOval(x, y, w, h);

        // Draw the devices ID ID
        graphics.setColor(Color.black);
        graphics.drawString(agent.getID(), x - 3, y);

    }

}
