/**
 * 
 */


import java.util.Properties;

import es.upm.dit.gsi.shanks.ShanksSimulation;
import es.upm.dit.gsi.shanks.ShanksSimulation3DGUI;
import es.upm.dit.gsi.shanks.model.han.scenario.HANScenario;
import es.upm.dit.gsi.shanks.model.scenario.Scenario;
import es.upm.dit.gsi.shanks.model.scenario.portrayal.Scenario3DPortrayal;

/**
 * @author a.carrera
 *
 */
public class TutorialSimulation3DGUI extends ShanksSimulation3DGUI {


    /**
     * @param sim
     */
    public TutorialSimulation3DGUI(ShanksSimulation sim) {
        super(sim);
    }

    /**
     * @return
     */
    public static String getName() {
        return "Tutorial Shanks - Home Area Nertwork - 3D";
    }
	
	/* (non-Javadoc)
	 * @see es.upm.dit.gsi.shanks.ShanksSimulation3DGUI#addDisplays(es.upm.dit.gsi.shanks.model.scenario.portrayal.Scenario3DPortrayal)
	 */
	@Override
	public void addDisplays(Scenario3DPortrayal paramScenario3DPortrayal) {
		// TODO Auto-generated method stub

	}
	
	public static void main (String[] args) {
		try {
			Properties scenarioProperties = new Properties();
//	        scenarioProperties.put(Scenario.SIMULATION_GUI, Scenario.SIMULATION_2D);
	        scenarioProperties.put(Scenario.SIMULATION_GUI, Scenario.SIMULATION_3D);
//	         scenarioProperties.put(Scenario.SIMULATION_GUI, Scenario.NO_GUI);
			TutorialSimulation tut = new TutorialSimulation(System.currentTimeMillis(), HANScenario.class, "MyHomeAreaNetwork", HANScenario.STATUS_NORMAL, scenarioProperties);
			TutorialSimulation3DGUI gui = new TutorialSimulation3DGUI(tut);
			gui.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
