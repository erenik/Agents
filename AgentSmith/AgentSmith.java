import jade.core.Agent;
import jade.core.*;
import jade.core.behaviours.*;
import jade.lang.acl.ACLMessage;
import jade.gui.*;

import javax.swing.*;
import java.awt.event.*;

/**
This example shows a minimal agent that just prints "Hallo World!" 
and then terminates.
@author Giovanni Caire - TILAB
 */
public class AgentSmith extends GuiAgent {
	
	private Gui gui;
	
	// setup agent
	protected void setup() {
		// some nice quote
        System.out.println("'Never send a human to do a machine's job.' - Agent " + getLocalName());
        // initialize the gui
        gui = new Gui();
        // associate the gui
        gui.setAgent(this);
    }
	
	protected void onGuiEvent(GuiEvent ev) {
        RA1.setTitle("My Agent Name is: " + this.getName());
    }
}