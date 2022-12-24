import java.util.ArrayList;


public class Chromosome  {
    ArrayList<Boolean> genes  = new ArrayList<>();
    int fitness;
    boolean discarded =false;
    boolean selcted =false;


    public Chromosome(){
        discarded=false;
        selcted =false;
    }
    public Chromosome(ArrayList<Boolean> genes, int fitness, boolean discarded, boolean selcted) {
        this.genes = genes;
        this.fitness = fitness;
        this.discarded=discarded;
        this.selcted=selcted;

    }

}
