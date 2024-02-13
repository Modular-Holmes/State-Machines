import machine.StateMachine;
import structures.State;
import structures.Transition;

public class Main {
    public static void main(String[] args) {
        State state1 = new State("1");
        State state2 = new State("2");
        State state3 = new State("3");

        StateMachine machine = new StateMachine("Machine");

        machine.register(new Transition(state1, state2));
        machine.register(new Transition(state2, state3, ()-> true ,() -> false));
        machine.register(new Transition(state3, state1));

        machine.setCurrentState(state1);


        machine.attemptTransition(state2);

        machine.attemptTransition(state1);

        System.out.println(machine.getCurrent());
    }
}
