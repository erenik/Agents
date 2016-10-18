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

import jade.wrapper.ContainerController;
import jade.wrapper.AgentContainer;
import jade.wrapper.StaleProxyException;
import jade.wrapper.*;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JList;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;



public class Architect {
	
	public Gui gui;
	
	// setup agent
	protected void setup() 
	{
		
		// some nice quote
        System.out.println("'I'm here to destroy servers and chew bubble gum, and i'm all out of bubble gum.' - Architect " + getLocalName());
        // initializes the gui
        gui = new Gui();
        // associates the gui
        //gui.setAgent(this);
        
        /** Registration with the DF */
        DFAgentDescription dfd = new DFAgentDescription();
        ServiceDescription sd = new ServiceDescription();
        sd.setType("Architect");
        sd.setName(getName());
        sd.setOwnership("The Architect itself");
        sd.addOntologies("Architect");
        dfd.setName(getAID());
        dfd.addServices(sd);
        try {
			DFService.register(this,dfd);
        } catch (FIPAException e) {
			System.err.println(getLocalName()+" registration with DF unsucceeded. Reason: "+e.getMessage());
			doDelete();
        }  
        
	}

}


class GUI extends JFrame {

	private JPanel contentPane;
	private JTextField ip;
	private JTextField port;
	private JTextField content;
	private JTextField period;


	/**
	 * Create the frame.
	 */
	public GUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		setTitle("Architect - Create soldier agents");
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblIP = new JLabel("IP");
		lblIP.setBounds(40, 43, 175, 14);
		contentPane.add(lblIP);
		
		JLabel lblPort = new JLabel("Port");
		lblPort.setBounds(40, 79, 175, 14);
		contentPane.add(lblPort);
		
		JLabel lblContent = new JLabel("Content");
		lblContent.setBounds(40, 120, 175, 14);
		contentPane.add(lblContent);
		
		JLabel lblPeriod = new JLabel("Periodicity");
		lblPeriod.setBounds(40, 164, 175, 14);
		contentPane.add(lblPeriod);
		
		ip = new JTextField();
		ip.setBounds(225, 40, 199, 20);
		ip.setText("balancertcpfibo-1404729259.us-west-2.elb.amazonaws.com");
		contentPane.add(ip);
		ip.setColumns(10);
		
		port = new JTextField();
		port.setBounds(225, 76, 199, 20);
		port.setText("4032");
		contentPane.add(port);
		port.setColumns(10);
		
		content = new JTextField();
		content.setBounds(225, 117, 199, 20);
		content.setText("47");
		contentPane.add(content);
		content.setColumns(10);
		
		period = new JTextField();
		period.setBounds(225, 161, 199, 20);
		period.setText("1");
		contentPane.add(period);
		period.setColumns(10);
		
		JButton btnCreate = new JButton("Create Agent");
		btnCreate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				// getting data
				Object[] args = { ip.getText(), Integer.valueOf(port.getText()),content.getText(), Integer.valueOf(period.getText())}; 
				
				//Get the JADE runtime interface (singleton)
				jade.core.Runtime runtime = jade.core.Runtime.instance();
				//Create a Profile, where the launch arguments are stored
				Profile profile = new ProfileImpl();
				profile.setParameter(Profile.CONTAINER_NAME, "Attack");
				profile.setParameter(Profile.MAIN_HOST, "localhost");
				//create a non-main agent container
				ContainerController container = runtime.createAgentContainer(profile);
				try {
						String name = "Agent "+String.valueOf(Math.round(Math.random()*1000));
				        AgentController ag = container.createNewAgent(name, 
				                                      "TCPAgent", 
				                                      args);//arguments
				        ag.start();
				} catch (StaleProxyException e) {
				    e.printStackTrace();
				}
			}
		});
		btnCreate.setBounds(146, 211, 157, 23);
		contentPane.add(btnCreate);
	}
}