package structures;
import java.util.function.BooleanSupplier;

public class Transition {
    private State start;
    private State end;

    private BooleanSupplier force;
    private BooleanSupplier blocker;

    public enum TransitionStatus{
        FORCED,
        AVAILABLE,
        UNAVAILABLE,
        BLOCKED,
    }
    
    public Transition(State start, State end){
        this.start = start;
        this.end = end;

        force = () -> false;
        blocker = () -> false;
    }

    public Transition(State start, State end, BooleanSupplier blocker){
        this.start = start;
        this.end = end;

        this.force = () -> false;
        this.blocker = blocker;
    }

    public Transition(State start, State end, BooleanSupplier force, BooleanSupplier blocker){
        this.start = start;
        this.end = end;

        this.force = force;
        this.blocker = blocker;
    }

    public TransitionStatus update(State current){
        if(current.equals(start)){
            if(force.getAsBoolean() && !blocker.getAsBoolean()){
                return TransitionStatus.FORCED;
            } else if (blocker.getAsBoolean()){
                return TransitionStatus.BLOCKED;
            } else {
                return TransitionStatus.AVAILABLE;
            }

        } else{
            return TransitionStatus.UNAVAILABLE;
        }
    }

    public State getStart() {
        return start;
    }

    public State getEnd() {
        return end;
    }

    public BooleanSupplier getBlocker() {
        return blocker;
    }

    public BooleanSupplier getForce() {
        return force;
    }

    public boolean equals(Transition other){
        return this.getStart().equals(other.getStart()) && this.getEnd().equals(other.getEnd());
        //TODO: Add differentiation by blocker and force
    }

    public String toString(){
        return this.getStart().getName() + " -> " + this.getEnd().getName();
    }
}
