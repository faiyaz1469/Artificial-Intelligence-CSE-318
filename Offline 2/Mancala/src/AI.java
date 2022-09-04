public class AI {
    /*Use IDS search to find the best move. The step starts from 1 and increments by step 1.
     * The max Iterative Depth is MAX_DEPTH which means it calculates MAX_DEPTH moves ahead.
     * The search can be interrupted by time limit.
     */
    public static int MAX_DEPTH = 8;
    protected int move1; //stores the current best move for the player1
    protected int move2; //stores the current best move for the player2
    public int additionalMove = 0;
    public int capturedStones = 0;


    public int moveMax(GameState state, int heuristics) {
        int i = 1;
        while(i <= MAX_DEPTH){
            moveValue newMove = maxAction(state, i, heuristics);
            this.move1 = newMove.getBin();
            i++;
        }
        return this.move1;
    }

    public int moveMin(GameState state, int heuristics) {
        int i = 1;
        while(i <= MAX_DEPTH){
            moveValue newMove = minAction(state, i, heuristics);
            this.move2 = newMove.getBin();
            i++;
        }
        return this.move2;
    }

    //Return best move for max player. wrapper function created for ease of use.
    public moveValue maxAction(GameState state, int maxDepth, int heuristics) {
        return maxAction(state, 0, maxDepth, -10000, 10000, heuristics);
    }

    //Return best move for min player. wrapper function created for ease of use.
    public moveValue minAction(GameState state, int maxDepth, int heuristics) {
        return minAction(state, 0, maxDepth, -10000, 10000, heuristics);
    }

    //return best move for max player
    public moveValue maxAction(GameState state, int currentDepth, int maxDepth, int alpha, int beta,int heuristics) {
        moveValue newMove = new moveValue(-10001, 1);
        if(state.gameOver() || currentDepth == maxDepth){
            newMove.setBin(14);
            if (heuristics == 1) {
                newMove.setValue(boardStat1Max(state));
            }
            else if(heuristics == 2){
                newMove.setValue(boardStat2Max(state));
            }
            else if(heuristics == 3){
                newMove.setValue(boardStat3Max(state));
            }
            else if(heuristics == 4){
                newMove.setValue(boardStat4Max(state));
            }
            else if(heuristics == 5){
                newMove.setValue(boardStat5(state));
            }
            else if(heuristics == 6){
                newMove.setValue(boardStat6(state));
            }
            return newMove;
        }
        int v = -10000;
        for(int bin = 5; bin >= 0; bin--){
            if(!state.illegalMove(bin)){

                GameState copy = new GameState(state);
                boolean extraTurn = copy.applyMove(bin);

                capturedStones = copy.stoneCount(6)-state.stoneCount(6);
                if(extraTurn)
                    additionalMove++;

                int beforeV = v;
                if(!extraTurn)
                    v = Math.max(v, minAction(copy, currentDepth + 1, maxDepth, alpha, beta, heuristics).getValue());
                else {
                    //additionalMove++;
                    v = Math.max(v, maxAction(copy, currentDepth + 1, maxDepth, alpha, beta, heuristics).getValue());

                }
                if(beforeV < v){
                    newMove.setValue(v);
                    newMove.setBin(bin);
                }
                alpha = Math.max(alpha, v);
                if(v > beta)
                    return newMove;
            }
        }
        additionalMove = 0;
        capturedStones = 0;
        return newMove;
    }

    //return best move for min player
    public moveValue minAction(GameState state, int currentDepth, int maxDepth, int alpha, int beta, int heuristics){
        moveValue newMove = new moveValue(10001, 1);
        if(state.gameOver() || currentDepth == maxDepth){
            newMove.setBin(14);
            if (heuristics == 1) {
                newMove.setValue(boardStat1Min(state));
            }
            else if(heuristics == 2){
                newMove.setValue(boardStat2Min(state));
            }
            else if(heuristics == 3){
                newMove.setValue(boardStat3Min(state));
            }
            else if(heuristics == 4){
                newMove.setValue(boardStat4Min(state));
            }
            else if(heuristics == 5){
                newMove.setValue(boardStat6(state));
            }
            else if(heuristics == 6){
                newMove.setValue(boardStat5(state));
            }
            return newMove;
        }
        int v = 10000;
        for(int bin = 12; bin > 6; bin--){
            if(!state.illegalMove(bin)){

                GameState copy = new GameState(state);
                boolean extraTurn = copy.applyMove(bin);

                capturedStones = copy.stoneCount(13)-state.stoneCount(13);
                if(extraTurn)
                    additionalMove++;

                int beforeV = v;
                if(!extraTurn)
                    v = Math.min(v, maxAction(copy, currentDepth + 1, maxDepth, alpha, beta, heuristics).getValue());
                else {
                    //additionalMove++;
                    v = Math.min(v, minAction(copy, currentDepth + 1, maxDepth, alpha, beta, heuristics).getValue());

                }
                if(beforeV > v){
                    newMove.setValue(v);
                    newMove.setBin(bin);
                }
                beta = Math.min(beta, v);
                if(v < alpha)
                    return newMove;
            }
        }
        additionalMove = 0;
        capturedStones = 0;
        return newMove;
    }

    //the boardStat function calculates different heuristics
    private int boardStat1Max(GameState state) {
        return (state.stoneCount(6) - state.stoneCount(13));
    }

    private int boardStat1Min(GameState state) {
        return (state.stoneCount(13) - state.stoneCount(6));
    }

    private int boardStat2Max(GameState state) {
        return 3 * (state.stoneCount(6) - state.stoneCount(13)) +
               2 * ((state.stoneCount(5) + state.stoneCount(4) + state.stoneCount(3) + state.stoneCount(2) + state.stoneCount(1) + state.stoneCount(0))
                        -(state.stoneCount(12) + state.stoneCount(11) + state.stoneCount(10) + state.stoneCount(9) + state.stoneCount(8) + state.stoneCount(7))
                );
    }

    private int boardStat2Min(GameState state) {
        return 3 * (state.stoneCount(13) - state.stoneCount(6))+
                2 * ((state.stoneCount(12) + state.stoneCount(11) + state.stoneCount(10) + state.stoneCount(9) + state.stoneCount(8) + state.stoneCount(7))
                        -(state.stoneCount(5) + state.stoneCount(4) + state.stoneCount(3) + state.stoneCount(2) + state.stoneCount(1) + state.stoneCount(0))
                );

    }

    private int boardStat3Max(GameState state) {
        return 4 * (state.stoneCount(6) - state.stoneCount(13))+
                2 * ((state.stoneCount(5) + state.stoneCount(4) + state.stoneCount(3) + state.stoneCount(2) + state.stoneCount(1) + state.stoneCount(0))
                        -(state.stoneCount(12) + state.stoneCount(11) + state.stoneCount(10) + state.stoneCount(9) + state.stoneCount(8) + state.stoneCount(7)))+
                2 * additionalMove;
    }

    private int boardStat3Min(GameState state) {
        return 4 * (state.stoneCount(13) - state.stoneCount(6))+
                2 * ((state.stoneCount(12) + state.stoneCount(11) + state.stoneCount(10) + state.stoneCount(9) + state.stoneCount(8) + state.stoneCount(7))
                        -(state.stoneCount(5) + state.stoneCount(4) + state.stoneCount(3) + state.stoneCount(2) + state.stoneCount(1) + state.stoneCount(0)))+
                2 * additionalMove;
    }

    private int boardStat4Max(GameState state) {
        return 4 * (state.stoneCount(6) - state.stoneCount(13)) +
                2 * ((state.stoneCount(5) + state.stoneCount(4) + state.stoneCount(3) + state.stoneCount(2) + state.stoneCount(1) + state.stoneCount(0))
                        -(state.stoneCount(12) + state.stoneCount(11) + state.stoneCount(10) + state.stoneCount(9) + state.stoneCount(8) + state.stoneCount(7)))+
                (2 * additionalMove) + capturedStones;
    }

    private int boardStat4Min(GameState state) {
        return 4 * (state.stoneCount(13) - state.stoneCount(6)) +
                2 * ((state.stoneCount(12) + state.stoneCount(11) + state.stoneCount(10) + state.stoneCount(9) + state.stoneCount(8) + state.stoneCount(7))
                        -(state.stoneCount(5) + state.stoneCount(4) + state.stoneCount(3) + state.stoneCount(2) + state.stoneCount(1) + state.stoneCount(0)))+
                (2 * additionalMove) + capturedStones;
    }

    private int boardStat5(GameState state) {
        return ((state.stoneCount(5) + state.stoneCount(4) + state.stoneCount(3) + state.stoneCount(2) + state.stoneCount(1) + state.stoneCount(0)));
    }

    private int boardStat6(GameState state) {
        return (state.stoneCount(12) + state.stoneCount(11) + state.stoneCount(10) + state.stoneCount(9) + state.stoneCount(8) + state.stoneCount(7));
    }
}
