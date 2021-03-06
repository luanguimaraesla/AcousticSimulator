package utils;

import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.ControllerException;
import jade.wrapper.PlatformController;
import jade.wrapper.StaleProxyException;

public class Util{
	
	//Variables delcaration
	private static Runtime rt = Runtime.instance(); // Get a hold on JADE runtime
	private static final Profile profile = new ProfileImpl(null, 1200, null); // Create a default profile
	private static final ProfileImpl pContainer = new ProfileImpl(null, 1200, null); // set the default Profile to start a container
	private static AgentContainer mainContainer = null;
	private static AgentContainer simulatorContainer = null;
	private static AgentController rma = null;
	private static AgentController ambient = null;
	//End of variables declaration
	
	public static void initAgent(PlatformController container, Object[] args, String path, String id){
		final AgentController agentController;
		try {
			agentController = container.createNewAgent(id, path, args);
			agentController.start();			
		} catch (ControllerException e ) {
			e.printStackTrace();
		}
	}	
	
	private static AgentContainer getMainContainer() {
    	if(mainContainer == null){
			mainContainer = rt.createMainContainer(profile);
		}
    	return mainContainer;
    }
    
    private static AgentContainer getSimulatorContainer() {
    	if(simulatorContainer == null) {
    		simulatorContainer = rt.createAgentContainer(pContainer);
    	}
    	return simulatorContainer;
    }
    
	public static void initiateJadeRma() {
		rt.setCloseVM(true); // Exit the JVM when there are no more containers around
		
		if(rma == null){
			try {
				rma = getMainContainer().createNewAgent("rma", "jade.tools.rma.rma", new Object[0]);
				rma.start();
			} catch (StaleProxyException e1) {
			e1.printStackTrace();
			}
		}
		else{
			System.out.println("JADE RMA is already intiated.");
		}
	}
	
	public static void initiateAmbient() {		
		if(ambient == null){
			try {
				ambient = getSimulatorContainer().createNewAgent("Ambient", "simulator.agents.Ambient", new Object[0]);
				ambient.start();
			} catch (StaleProxyException e1) {
				e1.printStackTrace();
			}
		}
		else{
			System.out.println("Ambient is already intiated.");
		}
	}
	
	public static boolean compareDouble(double double1, double double2, double tolerance){
		if(Math.abs(double1 - double2) < tolerance)
			return true;
		return false;
	}
	
	public static double normalizeAngle(double angle){
		while(angle < 0 || angle > 360){
			if(angle<0)
				angle = angle+360;
			if(angle>360)
				angle = angle-360;
		}
		return angle;
	}
}
