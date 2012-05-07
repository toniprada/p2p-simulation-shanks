/**
 * 
 */


import java.util.Properties;

import es.upm.dit.gsi.shanks.ShanksSimulation;
import es.upm.dit.gsi.shanks.ShanksSimulation2DGUI;
import es.upm.dit.gsi.shanks.model.han.scenario.HANScenario;
import es.upm.dit.gsi.shanks.model.scenario.Scenario;
import es.upm.dit.gsi.shanks.model.scenario.portrayal.Scenario2DPortrayal;

/**
 * @author a.carrera
 *
 */
public class TutorialSimulation2DGUI extends ShanksSimulation2DGUI {

    /**
     * @param sim
     */
    public TutorialSimulation2DGUI(ShanksSimulation sim) {
        super(sim);
    }

    /**
     * @return
     */
    public static String getName() {
        return "Tutorial Shanks - Home Area Nertwork - 2D";
    }
	/* (non-Javadoc)
	 * @see es.upm.dit.gsi.shanks.ShanksSimulation2DGUI#addDisplays(es.upm.dit.gsi.shanks.model.scenario.portrayal.Scenario2DPortrayal)
	 */
	@Override
	public void addDisplays(Scenario2DPortrayal paramScenario2DPortrayal) {
		// TODO Auto-generated method stub

	}
	
	public static void main (String[] args) {
		try {
			Properties scenarioProperties = new Properties();
	        scenarioProperties.put(Scenario.SIMULATION_GUI, Scenario.SIMULATION_2D);
//	        scenarioProperties.put(Scenario.SIMULATION_GUI, Scenario.SIMULATION_3D);
//	         scenarioProperties.put(Scenario.SIMULATION_GUI, Scenario.NO_GUI);
			TutorialSimulation tut = new TutorialSimulation(System.currentTimeMillis(), HANScenario.class, "ADSL scenario", HANScenario.STATUS_NORMAL, scenarioProperties);
			TutorialSimulation2DGUI gui = new TutorialSimulation2DGUI(tut);
			gui.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
