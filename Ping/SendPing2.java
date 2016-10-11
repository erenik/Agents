import jade.core.*;
import jade.core.behaviours.SimpleBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.DFService;
import jade.domain.FIPAException;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

public class SendPing2 extends Agent {

    protected  void setup() {

        DFAgentDescription dfd = new DFAgentDescription();
        ServiceDescription sd = new ServiceDescription();
        sd.setType("SendPing"); // something must be here; otherwise won't register
        sd.setName(getName());
        //sd.addOntologies("PingAgent"); // simple strings ok
        dfd.setName(getAID());
        dfd.addServices(sd);

        try {
            DFService.register(this, dfd);
        } catch (FIPAException e) {
            System.err.println(getLocalName() + " registration with DF failed. Reason: " +  e.getMessage());
            doDelete();
        }
         addBehaviour(new SimpleBehaviour() {

                private boolean finished = false;

                public void action() {
                    System.out.println("Enter the message 'ping'.");
                    String line = null;
                    try {
                        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                        line = br.readLine();
                    } catch (IOException ioe) {
                        ioe.printStackTrace();
                    }
                     ACLMessage msg = new ACLMessage(ACLMessage.QUERY_REF);
                     msg.setContent(line);
                     msg.setSender(getAID());
                     AID pingAgent = new AID("ping", false);
                     msg.addReceiver(pingAgent);
                     send(msg);

                     msg = blockingReceive();
                     if(msg != null) {
                         if(msg.getPerformative() == ACLMessage.INFORM ) {
                            System.out.println("[" + msg.getSender().getName()+ "] says " + msg.getContent());
                        } else if(msg.getPerformative() == ACLMessage.NOT_UNDERSTOOD) {
                            System.out.println("[" + msg.getSender().getName()+ "] says " + msg.getContent());
                             System.out.println("Not understood ?? This is the end!!");
                             finished = true;
                         } else {
                             System.out.println("A mysterious message");
                         }
                    }
                } // end action()

                public boolean done() {
                    return finished;
                }
              });
    }



}