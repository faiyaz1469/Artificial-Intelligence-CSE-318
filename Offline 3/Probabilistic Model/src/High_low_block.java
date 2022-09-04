public class High_low_block{

    public double highlyProb;
    public double lowProb;

    public High_low_block(double highlyProb, double lowProb) {
        this.highlyProb = highlyProb;
        this.lowProb = lowProb;
    }

    @Override
    public String toString() {
        return "< "+ Math.round(highlyProb * 10000.0) / 10000.0 + "," + Math.round(lowProb * 10000.0) / 10000.0+" >";
    }


}