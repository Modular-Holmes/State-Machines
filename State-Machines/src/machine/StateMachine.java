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
    }

    public boolean register(Transition transition){
        this.transitions.add(transition);
        return machine.put(transition.getStart().getName(), transition.getStart()) != null 
            || machine.put(transition.getEnd().getName(), transition.getEnd()) != null;
    }

    public void updateAllTransitions(){
        for(Transition transition : transitions){
            if (transition.update(current) == TransitionStatus.FORCED){
                current = transition.getEnd();
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
            if(t.attempt() == TransitionStatus.SUCCEEDED){return MachineResponse.SUCCESSFUL;}
        }

        return MachineResponse.BLOCKED;
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
