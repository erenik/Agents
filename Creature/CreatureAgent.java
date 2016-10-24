/**
	1- A multi-agent system consists of 2 Agent Types, CreatorAgent and Creature. When you
	run an instance of CreatoreAgent (in this case “CreatorAgent#1”), it will do the
	followings:
	a- Creates an Instance of Creature and names it Bender-The-Offender.
	b- Runs the Bender-The-Offender in the same container as itself.
	c- Sends a message to Bender-The-Offender with Performative “INFORM” and
	content “Hi”
	Bender-The-Offender, in return replies any message by Performative “INFORM” and
	content “Hi Yourself”
	The contents of the CreatureAgent.java and Creature.java are listed below, but the
	codes are incomplete. Complete the codes whenever is required, so that the above
	scenario can be executed by these two Agents. In total 17 lines of modifications are
	required. A Possible output of the run is as follow:
*/

import jade.core.Agent;
import jade.core.behaviours.*;
import jade.core.AID;
import jade.wrapper.AgentContainer; // 1- Ipmort Appropriate Jade’s Class
import jade.wrapper. // 2- Import Appropriate Jade’s Class
import jade.lang.acl.*;

public class CreatureAgent extends Agent //3- Complete the Class definition 
{
	String name = "Bender-The-Offender" ;
	AID Bender = new AID("Creature", ISLOCALNAME);// 4-Complete the code to Instantiate this Object from AID class
	protected void setup()
	{
		AgentContainer c = getContainerController(); // 5-Get the name of current container and assign it to “c”
		try {
			AgentController a = c.createNewAgent(name, "Creature", null); // 6-Create Bender-The-Offender in the current container
			ag.start(); // 7-Start your agent
			System.out.println("+++ Created: " + Bender);
		}
		catch (Exception e){}
		addBehaviour(
			new TickerBehaviour(this, 1000)  // 8-Create a behavior from an appropriate Behaviour
			{
				int n = 0;
				public void action()
				{
					ACLMessage msg = new
					ACLMessage(ACLMessage.INFORM);
					msg.setContent("Hi" );
					msg.addReceiver(new AID(name, AID.ISLOCALNAME); //9- Add the Receiver
					System.out.println("+++ Sending: " + n);
					this.send(msg);	//10- Send the Message
					block( 1000 );
				}
				public boolean done() {
					return ++n <= 3; // 11-use variable ”n” to run this Behaviour 3 times 
				}
			}
		);
	}
}