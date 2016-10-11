
/**
   This example shows a minimal agent that just prints "Hello     
   World!" 
   The Agent also returns its name
   and then terminates.
 */
import jade.core.Agent;
import java.util.Iterator;

public class HelloWorldAgent extends Agent {

  protected void setup() {
  	System.out.println("Hello World! My name is "+getAID().getLocalName());
    	// Make this agent terminate
  	//doDelete();
  } 
}

