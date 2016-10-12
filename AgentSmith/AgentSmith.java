import jade.core.Agent;
import jade.core.*;
import jade.core.behaviours.*;
import jade.lang.acl.ACLMessage;
import jade.gui.*;
import java.lang.Long;

import jade.domain.AMSService;
import jade.domain.FIPAAgentManagement.AMSAgentDescription;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.event.*;
import java.util.ArrayList;
import jade.domain.*;
import jade.domain.FIPAAgentManagement.*;
import jade.lang.acl.*;
import jade.content.*;
import jade.content.lang.*;
import jade.content.lang.sl.*;
import jade.lang.acl.ACLMessage;
import jade.content.onto.*;
import jade.content.onto.basic.*;
import jade.util.leap.*;
import jade.domain.DFService;
import jade.domain.FIPAException;

import java.util.Iterator;
import java.util.Date;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.OutputStreamWriter;


/**
This example shows a minimal agent that just prints "Hallo World!" 
and then terminates.
@author Giovanni Caire - TILAB
 */
public class AgentSmith extends Agent {
	
	public Gui gui;
	
	/// Default messages that may be sent and replied to.
//public static String targetAgent = "ReplaceMe";	
	
	// Logs text. Is printed both to std out and to the gui?
	void Log(String txt)
	{
		System.out.println("AgentSmith.Log: "+txt);
		gui.Log(txt);
	}
	
	static ArrayList<String> RequestMessages()
	{
		ArrayList<String> messages = new ArrayList<String>();
		messages.add("Who are you?");
		messages.add("What do you want?");
		messages.add("Kill");
        return messages;
	}
	static String ReplyForRequest(String request)
	{
		
		System.out.println(request);
		if (request.equals("Who are you?"))
			return "Agent Smith: Me? I guess you could say, that I am the Alpha... ";
		return "...";
	}
	
	// setup agent
	protected void setup() 
	{
		
		// some nice quote
        System.out.println("'Never send a human to do a machine's job.' - Agent " + getLocalName());
        // initializes the gui
        gui = new Gui();
        // associates the gui
        gui.setAgent(this);
        gui.Log("'Never send a human to do a machine's job.' - Agent " + getLocalName());
        
        /** Registration with the DF */
        DFAgentDescription dfd = new DFAgentDescription();
        ServiceDescription sd = new ServiceDescription();
        sd.setType("SenderAgent");
        sd.setName(getName());
        sd.setOwnership("ExampleReceiversOfJADE");
        sd.addOntologies("SenderAgent");
        dfd.setName(getAID());
        dfd.addServices(sd);
        try {
			DFService.register(this,dfd);
        } catch (FIPAException e) {
			System.err.println(getLocalName()+" registration with DF unsucceeded. Reason: "+e.getMessage());
			doDelete();
        }    
        addBehaviour(new CyclicBehaviour(this){
			public void action()
			{
				ACLMessage msg = receive();
				if (msg != null)
				{
					String content = msg.getContent();
					String performative = msg.getPerformative(msg.getPerformative());
					String senderName = msg.getSender().getName(); // Name + IP
					String senderNameOnly = senderName.split("@")[0]; // Given Name only
					if (performative.equals("INFORM"))
					{
						Log(senderNameOnly+": "+content);
					}
					if (performative.equals("REQUEST"))
					{
						Log(senderNameOnly+" Req: "+content);
						if (content.equals("Kill"))
						{
							doDelete(); // Kill self.
							// Kill program too?
							System.exit(1);
							return;
						}
						String replyStr = ReplyForRequest(content);						
						System.out.println("Reply: "+replyStr);
							
						// Send the reply.
						ACLMessage reply = new ACLMessage(ACLMessage.INFORM);
						reply.addReceiver(new AID(senderNameOnly, AID.ISLOCALNAME));
						reply.setLanguage("English");
						reply.setContent(replyStr);
						myAgent.send(reply);
						Log("-> "+senderNameOnly+" : "+replyStr);
					}
					else 
					{
						System.out.println("Received message of performative: "+performative);
					}
				}
			}
		}
);
    }
	
	// performs action when something is interacting with the GUI
	protected void onGuiEvent(GuiEvent ev) {
        // nothing yet
    }
	
	protected ArrayList<String> scanForAgents() 
	{
		ArrayList<String> agents = new ArrayList<String>();
        try {
            SearchConstraints sc = new SearchConstraints();
            sc.setMaxResults(new java.lang.Long(-1)); // Max unlimited results
            AMSAgentDescription[] evalAgents = AMSService.search(this, new AMSAgentDescription(), sc);
            for (int i = 0; i < evalAgents.length; ++i)
            {
            	String name = evalAgents[i].getName().getName();
            	System.out.println("Found an agent: "+i+" "+name);
            	gui.Log("Found an agent: "+i+" "+name);
            	agents.add(name);
            }
        } catch(Exception e)
        {
        	System.out.println("Exception in scanForAgents: "+e.toString());
        }
		return agents;
	}
}

class Gui extends JFrame implements ActionListener 
{
    private AgentSmith myAgent;
    public JButton B;
    private JPanel contentPane;
    private JTextArea log;
    public JComboBox<String> receiver, message;

	public void Log(String txt)
	{
		System.out.println("Gui.Log: "+txt);
		log.append("\n"+txt);
	}
	
    protected void frameInit() {
        super.frameInit();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		message = new JComboBox<String>();
		message.setBounds(10, 33, 205, 23);
		contentPane.add(message);
		
		/// Add messages
		message.removeAllItems();
                ArrayList<String> messages = AgentSmith.RequestMessages();
		for (int i = 0; i < messages.size(); ++i)
			message.addItem(messages.get(i));
		
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
		JScrollPane scroll = new JScrollPane(log);
		scroll.setVerticalScrollBarPolicy ( ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS );
		scroll.setHorizontalScrollBarPolicy ( ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS );
		scroll.setBounds(222, 32, 199, 165);
		//log.setBounds(225, 32, 199, 165);
		contentPane.add(scroll);
		
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
				String[] temp;
				for(int i=0;i<agents.size();++i) {
					temp = agents.get(i).split("@");
					receiver.addItem(temp[0]);
				}
			}
		});
		contentPane.add(btnScanForAgents);
				
		JButton btnSendThreat = new JButton("Send Threat");
		btnSendThreat.setBounds(10, 136, 205, 23);
		contentPane.add(btnSendThreat);
		btnSendThreat.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0)
			{
				// Send a default message for now.
				ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);
				String receiverName = receiver.getItemAt(receiver.getSelectedIndex());
				msg.addReceiver(new AID(receiverName, AID.ISLOCALNAME));
				msg.setLanguage("English");
				String messageStr = message.getItemAt(message.getSelectedIndex());
				msg.setContent(messageStr);
				myAgent.send(msg);
//				System.out.println("Sent message");  
				Log("Sending message \""+messageStr+"\" to "+receiverName);
			}	
		});
		
		setVisible(true);

    }

    public void setAgent(AgentSmith a) {
        myAgent = a;
		setTitle("Agent "+myAgent.getLocalName()+" Control center");
    }

    public void actionPerformed(java.awt.event.ActionEvent ae) {
        // TODO add your handling code here:

        addLog(ae.getActionCommand());
    }
    
}

class SendMessage extends OneShotBehaviour {

    public void action() {
        /*ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);
        msg.addReceiver(new AID("R", AID.ISLOCALNAME));
        msg.setLanguage("English");
        msg.setContent("Hello How Are You?");
        send(msg);
        System.out.println("****I Sent Message to::> R1 *****"+"\n"+
                            "The Content of My Message is::>"+ msg.getContent());*/
    }
}

class ReceiveMessage extends CyclicBehaviour {

    // Variable to Hold the content of the received Message
    private String Message_Performative;
    private String Message_Content;
    private String SenderName;
    private String MyPlan;


    public void action() {
        /*ACLMessage msg = receive();
        if(msg != null) {

            Message_Performative = msg.getPerformative(msg.getPerformative());
            Message_Content = msg.getContent();
            SenderName = msg.getSender().getLocalName();
            System.out.println(" ****I Received a Message***" +"\n"+
                    "The Sender Name is::>"+ SenderName+"\n"+
                    "The Content of the Message is::> " + Message_Content + "\n"+
                    "::: And Performative is::> " + Message_Performative + "\n");
            System.out.println("ooooooooooooooooooooooooooooooooooooooo");*/

        }

    }
