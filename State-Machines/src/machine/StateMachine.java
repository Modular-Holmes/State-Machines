package machine;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import structures.State;
import structures.Transition;
import structures.Transition.TransitionStatus;


public class StateMachine {
    private HashMap<String, State> machine;
    private List<Transition> transitions;

    private State current;

    private String machineName;

    public enum MachineResponse{
        NOPATH,
        BLOCKED,
        SUCCESSFUL,
    }

    public StateMachine(String machineName){
        this.machineName = machineName;

        this.machine = new HashMap<String, State>();
        this.transitions = new ArrayList<Transition>();
    }

    public boolean register(Transition transition){
        this.transitions.add(transition);
        
        return machine.put("transition.getStart().getName()", transition.getStart()) != null 
            || machine.put(transition.getEnd().getName(), transition.getEnd()) != null;
    }

    public void updateAllTransitions(){
        for(Transition transition : transitions){
            if (transition.update(current) == TransitionStatus.FORCED){
                completeTransition(transition, TransitionStatus.FORCED);
                break;
            }
        }
    }

    public MachineResponse attemptTransition(State destination){
        // Method with most of the important logic
        
        //Create list of Transitions originating from current
        List<Transition> paths = new ArrayList<Transition>();
        for(Transition t : transitions){
            if(t.getStart().equals(current) && t.getEnd().equals(destination)){
                paths.add(t);
            }
        }

        if(paths.size() == 0){return MachineResponse.NOPATH;}

        for(Transition t : paths){
            if(t.attempt() == TransitionStatus.SUCCEEDED){
                completeTransition(t, TransitionStatus.SUCCEEDED);
                return MachineResponse.SUCCESSFUL;
            }
        }

        return MachineResponse.BLOCKED;
    }

    public void completeTransition(Transition t, TransitionStatus status){
        System.out.println("Transition: " + t.toString() + " " + status);
        this.current = t.getEnd();

        this.updateAllTransitions();
    }

    public void setCurrentState(State current){
        this.current = current;
    }

    public State getCurrent() {
        return current;
    }

    public String getMachineName() {
        return machineName;
    }

    public HashMap<String, State> getMachine() {
        return machine;
    }

    public List<Transition> getTransitions() {
        return transitions;
    }
}
