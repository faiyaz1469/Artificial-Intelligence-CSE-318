import java.util.ArrayList;
import java.util.Scanner;
import java.math.BigDecimal;
import java.text.DecimalFormat;

public class HMM {
    private int n, m, k;
    private double[][] grid;
    private High_low_block[][] high_low_grid;
    private ArrayList<Transition_block>[][] transitionProbs_1;
    private double cumProb;
    private double sensorReadingProb;

    public HMM(){}

    public HMM(int n, int m, int k, double cumProb) {
        this.n = n;
        this.m = m;
        this.k = k;
        this.cumProb = cumProb;
        grid = new double[n][m];
        high_low_grid = new High_low_block[n][m];
        transitionProbs_1 = new ArrayList[n][m];
        this.init();
    }

    public void setSensorReadingCorrectnessProbability(double p) { sensorReadingProb = p; }

    public double getSensorReadingCorrectnessProbability() { return sensorReadingProb;}

    public void setGrid(int i, int j, double value) {
        grid[i][j] = value;
    }

    public void init() {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                grid[i][j] = 1;
            }
        }
    }

    public void setInitialProbability() {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (grid[i][j] == 1) {
                    grid[i][j] = (double) 1 / (n * m - k);
                }
            }
        }
    }

    public void setEdgeCornerProbability() {
        int edge;
        int corner;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                edge = 0;
                corner = 0;
                if(grid[i][j]==0)  {
                    high_low_grid[i][j] = new High_low_block(0,0);
                    continue;
                }

                if(i-1>=0 && grid[i-1][j]!=0) {
                    edge++;
                }
                if(i+1<n && grid[i+1][j]!=0) {
                    edge++;
                }
                if(j-1>=0 && grid[i][j-1]!=0) {
                    edge++;
                }
                if(j+1<m && grid[i][j+1]!=0) {
                    edge++;
                }

                if(i-1>=0 && j-1>=0 && grid[i-1][j-1]!=0) {
                    corner++;
                }
                if(i-1>=0 && j+1<m && grid[i-1][j+1]!=0) {
                    corner++;
                }
                if(i+1<n && j-1>=0 && grid[i+1][j-1]!=0) {
                    corner++;
                }
                if(i+1<n && j+1<m && grid[i+1][j+1]!=0) {
                    corner++;
                }

                corner++;

                if(edge==0 && corner==1) {
                    high_low_grid[i][j] = new High_low_block(0,0);
                    continue;
                }

                high_low_grid[i][j] = new High_low_block(cumProb/edge,(1-cumProb)/(corner));

            }
        }
    }

    public void setTransitionalProbability() {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                transitionProbs_1[i][j] = new ArrayList<>();
                if(grid[i][j] == 0) {
                    transitionProbs_1[i][j].add(new Transition_block(0.0,i,j));
                    continue;
                }

                //right
                if(j+1<m && grid[i][j+1]!=0) {
                    transitionProbs_1[i][j].add(new Transition_block(high_low_grid[i][j+1].highlyProb,i,j+1));
                }
                //bottom right
                if(i+1<n && j+1<m && grid[i+1][j+1]!=0) {
                    transitionProbs_1[i][j].add(new Transition_block(high_low_grid[i+1][j+1].lowProb,i+1,j+1));

                }
                //bottom
                if(i+1<n && grid[i+1][j]!=0) {
                    transitionProbs_1[i][j].add(new Transition_block(high_low_grid[i+1][j].highlyProb,i+1,j));

                }
                //bottom left
                if(i+1<n && j-1>=0 && grid[i+1][j-1]!=0) {
                    transitionProbs_1[i][j].add(new Transition_block(high_low_grid[i+1][j-1].lowProb,i+1,j-1));
                }

                //left
                if(j-1>=0 && grid[i][j-1]!=0) {
                    transitionProbs_1[i][j].add(new Transition_block(high_low_grid[i][j-1].highlyProb,i,j-1));
                }

                //top left
                if(i-1>=0 && j-1>=0 && grid[i-1][j-1]!=0) {
                    transitionProbs_1[i][j].add(new Transition_block(high_low_grid[i-1][j-1].lowProb,i-1,j-1));
                }
                //top
                if(i-1>=0 && grid[i-1][j]!=0) {
                    transitionProbs_1[i][j].add(new Transition_block(high_low_grid[i-1][j].highlyProb,i-1,j));
                }

                //top right
                if(i-1>=0 && j+1<m && grid[i-1][j+1]!=0) {
                    transitionProbs_1[i][j].add(new Transition_block(high_low_grid[i-1][j+1].lowProb,i-1,j+1));
                }

                transitionProbs_1[i][j].add(new Transition_block(high_low_grid[i][j].lowProb,i,j));
            }
        }
    }

    public void calculateBeforeObservation(){

        //take a copy of previous one
        double[][] temp = new double[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                temp[i][j] = grid[i][j];
            }
        }

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (temp[i][j] == 0) {
                    continue;
                }
                double sum = 0.0;
                for (int k = 0; k < transitionProbs_1[i][j].size(); k++) {

                    //calculate  B'(X_t):
                    //belief before observation
                    Transition_block tb = transitionProbs_1[i][j].get(k);
                    double p = tb.prob;
                    double gv = temp[tb.ipos][tb.jpos];
                    sum += p * gv ;
                }

                //update the probability of the cell
                grid[i][j] = sum;
            }
        }
    }

    private void updateNeighbour(int i, int j, double prob){

        //top
        if(i-1>=0) {
            grid[i-1][j] = grid[i-1][j]*prob;
        }
        //top right
        if(i-1>=0 && j+1<m) {
            grid[i - 1][j + 1] = grid[i - 1][j + 1] * prob;
        }
        //right
        if(j+1<m) {
            grid[i][j + 1] = grid[i][j + 1] * prob;
        }
        //bottom right
        if(i+1<n && j+1<m) {
            grid[i + 1][j + 1] = grid[i + 1][j + 1] * prob;
        }
        //bottom
        if(i+1<n) {
            grid[i + 1][j] = grid[i + 1][j] * prob;
        }
        //bottom left
        if(i+1<n && j-1>=0) {
            grid[i + 1][j - 1] = grid[i + 1][j - 1] * prob;
        }
        //left
        if(j-1>=0) {
            grid[i][j - 1] = grid[i][j - 1] * prob;
        }
        //top left
        if(i-1>=0 && j-1>=0) {
            grid[i - 1][j - 1] = grid[i - 1][j - 1] * prob;
        }

        //self
        grid[i][j] = grid[i][j]*prob;

    }

    private void updateOthers(int u, int v, double prob){
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if(checkIfAdjacent(i,j,u,v))
                    continue;
                grid[i][j] = grid[i][j]*prob;
            }
        }
    }

    private boolean checkIfAdjacent(int i, int j, int x, int y){

        boolean b = (j == y || j == y - 1 || j == y + 1);

        if(i==x && b)
            return true;
        if(i==x-1 && b)
            return true;
        if(i==x+1 && b)
            return true;

        return false;
    }

    public double getSum(){
        double sum = 0.0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                sum+=grid[i][j];
            }
        }
        return sum;
    }

    private void normalize(){
        double sum = getSum();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (grid[i][j] == 0) {
                    continue;
                }
                grid[i][j] = grid[i][j]/sum;
            }
        }
    }

    //calculate the Belief after observation
    public void updateAfterObservation(int i, int j , int b){

        calculateBeforeObservation();

        if (b == 1) {
            updateNeighbour(i,j,getSensorReadingCorrectnessProbability());
            updateOthers(i,j,1-getSensorReadingCorrectnessProbability());
        }
        else {
            updateNeighbour(i,j,1-getSensorReadingCorrectnessProbability());
            updateOthers(i,j,getSensorReadingCorrectnessProbability());
        }

        normalize();

    }

    public void showCasperPossibleLocation(){
        double max = 0;
        int max_i = -1;
        int max_j = -1;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if(grid[i][j]>max) {
                    max = grid[i][j];
                    max_i = i;
                    max_j = j;
                }

            }
        }

        System.out.println("Casper is most probably at (" + max_i + "," + max_j + ")");
    }

    static String test(double val) {
        final BigDecimal value = new BigDecimal(val);
        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(4);
        df.setMinimumFractionDigits(4);
        return df.format(value);
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                str.append(test(grid[i][j])).append("\t");
            }
            str.append("\n");
        }
        return str.toString();
    }

    public static void main(String[] args) {

            int n, m , k;
            HMM hmm;
            double cumulativeProbability = 0.9;
            double sensorCorrectProbability = 0.85;

            Scanner scanner = new Scanner(System.in);
            n = scanner.nextInt();
            m = scanner.nextInt();
            k = scanner.nextInt();
            hmm = new HMM(n, m, k,cumulativeProbability);
            hmm.setSensorReadingCorrectnessProbability(sensorCorrectProbability);

            int u, v;

            for (int i = 0; i < k; i++) {
                u  = scanner.nextInt();
                v = scanner.nextInt();
                hmm.setGrid(u,v,0);
            }

            hmm.setInitialProbability();
            System.out.println();
            System.out.println("\nInitial Probability:");
            System.out.println(hmm);

            hmm.setEdgeCornerProbability();
            hmm.setTransitionalProbability();

            String command ;
            int c = 1;
            int ipos, jpos, b;

            while (true) {
                command = scanner.next();
                if (command.equalsIgnoreCase("C")) {
                    hmm.showCasperPossibleLocation();
                }
                else if (command.equalsIgnoreCase("R")) {
                    ipos = scanner.nextInt();
                    jpos = scanner.nextInt();
                    b = scanner.nextInt();
                    hmm.updateAfterObservation(ipos, jpos, b);
                    System.out.println("\nProbability Update ("+"Reading-"+c+"):");
                    System.out.println(hmm);
                    c++;
                }
                else if (command.equalsIgnoreCase("Q")) {
                    System.out.println("\nBye Casper!");
                    break;
                }
            }
    }
}
