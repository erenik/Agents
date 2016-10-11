import jade.core.Agent;
import jade.core.*;
import jade.core.behaviours.*;
import jade.lang.acl.ACLMessage;
import jade.gui.*;
import java.lang.Long;

import jade.domain.FIPAAgentManagement.SearchConstraints;
import jade.domain.AMSService;
import jade.domain.FIPAAgentManagement.AMSAgentDescription;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.event.*;
import java.util.ArrayList;

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
        gui.addLog("'Never send a human to do a machine's job.' - Agent " + getLocalName());
    }
	
	// performs action when something is interacting with the GUI
	protected void onGuiEvent(GuiEvent ev) {
        // nothing yet
    }
	
	protected ArrayList<String> scanForAgents() {
		// TODO
ArrayList<String> agents = new ArrayList<String>();
            try {
            SearchConstraints sc = new SearchConstraints();
            sc.setMaxResults(new java.lang.Long(-1)); // Max unlimited results
            AMSAgentDescription[] evalAgents = AMSService.search(this, new AMSAgentDescription(), sc);
for (int i = 0; i < evalAgents.length; ++i)
{
String name = evalAgents[i].getName().getName();
System.out.println("Found an agent: "+i+" "+name);
agents.Add(name);
}
           } catch(Exception e)
{
System.out.println("Exception in scanForAgents: "+e.toString());
}
		return null;
	}
	
	
}

class Gui extends JFrame implements ActionListener {

    private AgentSmith myAgent;
    public JButton B;
    private JPanel contentPane;
    private JTextArea log;
    public JComboBox receiver;

    protected void frameInit() {
        super.frameInit();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		setTitle("Agent Smith Control center");
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JComboBox message = new JComboBox();
		message.setBounds(10, 33, 205, 23);
		contentPane.add(message);
		
		JButton btnKillAllHumanity = new JButton("Kill All Humanity");
		btnKillAllHumanity.setBounds(10, 215, 205, 23);
		contentPane.add(btnKillAllHumanity);
		
		JButton btnCallAnotherAgent = new JButton("Call Another Agent");
		btnCallAnotherAgent.setBounds(225, 215, 199, 23);
		contentPane.add(btnCallAnotherAgent);
		
		JLabel lblMessage = new JLabel("Message");
		lblMessage.setBounds(23, 11, 192, 14);
		contentPane.add(lblMessage);
		
		JLabel lblReceiver = new JLabel("Receiver");
		lblReceiver.setBounds(20, 77, 195, 14);
		contentPane.add(lblReceiver);
		
		receiver = new JComboBox<String>();
		receiver.setBounds(10, 102, 205, 23);
		contentPane.add(receiver);
		
		log = new JTextArea();
		log.setBounds(225, 32, 199, 165);
		contentPane.add(log);
		
		JLabel lblLog = new JLabel("Log");
		lblLog.setBounds(240, 11, 184, 14);
		contentPane.add(lblLog);
		
		JButton btnScanForAgents = new JButton("Scan For Agents");
		btnScanForAgents.setBounds(10, 174, 205, 23);
		btnScanForAgents.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// get agents around
				ArrayList<String> agents = myAgent.scanForAgents();
				receiver.removeAllItems();
				for(int i=0;i<agents.size();++i) {
					receiver.addItem(agents.get(i));
				}
			}
		});
		contentPane.add(btnScanForAgents);
		
		JButton btnSendThreat = new JButton("Send Threat");
		btnSendThreat.setBounds(10, 136, 205, 23);
		contentPane.add(btnSendThreat);
		
		
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
    
    public void addLog(String msg) {
    	this.log.setText(this.log.getText()+"/n"+msg);
    }
}
