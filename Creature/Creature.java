// Agent creaturrre!
import jade.core.Agent;
import jade.core.behaviours.*;
import jade.lang.acl.*;
public class Creature extends Agent // 12-Complete the Class definition
{
	protected void setup()
	{
		addBehaviour( 
			new CyclicBehaviour(this) // 13-Create a behavior from an appropriate
			{
				public void action()
				{
					ACLMessage msg = receive();
					if (msg!=null) 
					{
						System.out.println( " - " + myAgent.getLocalName() + " received: " + msg.getContent() );
						ACLMessage reply = new ACLMessage(ACLMessage.INFORM); // 14-create an ACL message called "reply" // 15-set its performative to INFORM
						reply.setContent("Hi Yourself");// 16-set appropriate message content as mentioned in the question
						reply.addReceiver(msg.getSender());
						myAgent.send(reply); // 17-send the message to the sender
						System.out.println( "My Reply was: Hi Yourself");
					}
					block();
				}
			}
		);
	}
}
