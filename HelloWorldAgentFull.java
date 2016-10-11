
/**
   This example shows a minimal agent that just prints "Hello     
   World!" 
   The Agent also returns its name
   and then terminates.
 */
import jade.core.Agent;
import java.util.Iterator;

public class HelloWorldAgentFull extends Agent {

  protected void setup() {
  	System.out.println("Hello World! My name is "+getAID().getLocalName());
        System.out.println("Hello World. I’m an agent!");
        System.out.println("My local-name is "+getAID().getLocalName());
        System.out.println("My GUID is "+getAID().getName());
        System.out.println("My addresses are:");
        Iterator it = getAID().getAllAddresses();
        while (it.hasNext()) {
        System.out.println("- "+it.next());
        }
  	// Make this agent terminate
  	//doDelete();
  } 
}

