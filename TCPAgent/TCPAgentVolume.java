import jade.core.Agent;
import jade.core.*;
import jade.core.behaviours.*;
import jade.lang.acl.ACLMessage;
import jade.gui.*;
import java.lang.Long;
import java.net.Socket;

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
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.OutputStreamWriter;
import java.lang.Exception;
import java.lang.Math;
import java.io.BufferedReader;
import java.io.InputStreamReader;




public class TCPAgentVolume extends Agent {
	
	private String ip;
	private int port;
	private String content;
	private int period;
	
	// setup agent
	protected void setup() 
	{
		Object[] args = getArguments();
		ip = args[0].toString();
		port = Integer.valueOf(args[1].toString());
		content = args[2].toString();
		period = Integer.valueOf(args[3].toString());
		// Parse into integer.
		int length = Integer.parseInt(content);
		char[] cArr = new char[length];
		content = new String(cArr);
	
		
		// some nice quote
        System.out.println("I'm here to spam some TCP server - " + getLocalName());
        
        /** Registration with the DF */
        DFAgentDescription dfd = new DFAgentDescription();
        ServiceDescription sd = new ServiceDescription();
        sd.setType("TCPAgentVolume");
        sd.setName(getName());
        sd.setOwnership("Val&Emil");
        sd.addOntologies("TCPAgentVolume");
        dfd.setName(getAID());
        dfd.addServices(sd);
        try {
			DFService.register(this,dfd);
        } catch (FIPAException e) {
			System.err.println(getLocalName()+" registration with DF unsucceeded. Reason: "+e.getMessage());
			doDelete();
        }  
        
        addBehaviour(new CyclicBehaviour(this){
        	public void action(){
        		// we send a message every second to a tcp server        		
        		try {
        			SendMessageVolume th = new SendMessageVolume(getLocalName(), ip, port, content);
        			th.start();
        			Thread.sleep(period);
        			
        		} catch (Exception e) {
        			System.out.println("error "+e.getMessage());
        		}
        	}
        });
    } // end setup
	

}

class SendMessageVolume extends Thread {
	
	private String ip;
	private int port;
	private String msg;
	private Socket s;
	private String id;
	
	public SendMessageVolume(String in_id, String nIp, int nPort, String nMsg) {
		ip = nIp;
		port = nPort;
		msg = nMsg;
		id = in_id;
	}
	
	public void run() 
	{
		try {
//			System.out.println(id+": trying to connect...");
    		Socket s = new Socket(ip, port);
  //  		System.out.println(id+":Connected.");
    		BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
			PrintWriter out = new PrintWriter(s.getOutputStream(), true);
    		
    		out.println(msg);
    		out.flush();
    		System.out.println(id+": Sent "+msg.length()+" bytes.");
    		@SuppressWarnings("deprecation")
			String line = in.readLine();
    		System.out.println(id+": Received:"+line);
    		s.close();
		} catch (Exception e) {
			System.out.println(id+": error "+e.getMessage());
		}
	}
}
