import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
public class HelloWorldAgentOSB extends Agent {
protected void setup() {
addBehaviour(new OneShotBehaviour() {
public void action(){
System.out.println("Hello World! My name is "+getLocalName());
}
});
}
}