public class Transition_block {

    public double prob;
    public int ipos;
    public int jpos;

    public Transition_block(double prob, int ipos, int jpos) {
        this.prob = prob;
        this.ipos = ipos;
        this.jpos = jpos;
    }

    @Override
    public String toString() {
        return "< " + Math.round(prob * 10000.0) / 10000.0 + "," + ipos + "," + jpos + " >";
    }
}