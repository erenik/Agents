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
        // initializes the gui
        gui = new Gui();
        // associates the gui
        gui.setAgent(this);
    }
	
	// performs action when something is interacting with the GUI
	protected void onGuiEvent(GuiEvent ev) {
        // nothing yet
    }
	
	
}

class Gui extends JFrame implements ActionListener {

    private AgentSmith myAgent;
    public JButton B;
    private JToolBar jToolBar1;

    protected void frameInit() {
        super.frameInit();
        setSize(500, 300);
        setTitle("I am an Agent GUI, Click my Button to Get My Agent's Name");
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        jToolBar1 = new JToolBar();
        this.add(jToolBar1);
        B = new JButton();
        B.setFont(new java.awt.Font("Arial", 0, 14));

//    B.setSize(10, 10);
        B.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        B.setFocusable(true);
        B.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        B.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        B.setText("Get Agent Name");
        jToolBar1.add(B);
        B.addActionListener(this);
        setVisible(true);
    }

    public void setAgent(AgentSmith a) {
        myAgent = a;
    }

    public void actionPerformed(java.awt.event.ActionEvent ae) {
        // TODO add your handling code here:

        GuiEvent ge = new GuiEvent(this, 1);

        if (ae.getSource() == this.B) {
            myAgent.postGuiEvent(ge);
        }
    }
}