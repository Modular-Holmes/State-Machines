package structures;
public class State {
    private String name;
    private Runnable action;

    public State(){
        name = "Unnamed State";
    }

    public State(String name){
        this.name = name;
    }

    public State(String name, Runnable action){
        this.action = action;
    }

    public void runState(){
        this.action.run();
    }

    public Runnable getAction() {
        return action;
    }

    public String getName() {
        return name;
    }

    public void setAction(Runnable action) {
        this.action = action;
    }

    public boolean equals(State other){
        return this.getName() == other.getName();
    }

    public String toString(){
        return this.getName();
    }
}
