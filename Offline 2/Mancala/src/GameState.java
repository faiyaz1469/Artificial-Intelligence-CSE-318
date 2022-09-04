public final class GameState
{

    /** returned by status() when the game is not over **/
    public static final int GAME_NOT_OVER  = Integer.MIN_VALUE;

    /** returned by status() when the player on the lower row has won **/
    public static final int GAME_OVER_WIN  =  1;

    /** returned by status() when game is over but the players have tied **/
    public static final int GAME_OVER_TIE  =  0;

    /** returned by status() when the player on the upper row has won **/
    public static final int GAME_OVER_LOSE = -1;

    public int[] state = { 4, 4, 4, 4, 4, 4, 0, 4, 4, 4, 4, 4, 4, 0 };

    /**
     * This default constructor will create an initial board for the beginning
     * of a game. Each pocket has 4 stones initially.
     */
    public GameState() {}

    /**
     * This constructor assigns a different initial number of stones in the
     * the initial number of stones in the bins
     * must be > 0, otherwise it defaults to 4.
     */
    public GameState(int initialStones)
    {
        if (initialStones < 1) initialStones = 4;
        for (int i=0; i<6; i++)
            state[i] = state[i+7] = initialStones;
    }

    /**
     * A copy constructor
     * @param source the object out of which to construct a copy
     */
    public GameState(GameState source)
    {
        this.state = arrayCopy(source.state);
    }

    /**
     * returns a class constant indicating the current status of the game
     * @return the current game status as defined by the class constants
     */
    public int status()
    {
        if (stonesCount(7,12) != 0 && stonesCount(0,5) != 0)
            return GAME_NOT_OVER;
        else if (stonesCount(7,13) < stonesCount(0,6))
            return GAME_OVER_WIN;
        else if (stonesCount(7,13) == stonesCount(0,6))
            return GAME_OVER_TIE;
        else
            return GAME_OVER_LOSE;
    }

    /**
     * returns whether or not the game is over by making a call to status()
     * @return true; the game is over<br>false; otherwise
     */
    public boolean gameOver() {
        return status() != GAME_NOT_OVER;
    }

    /**
     * an accesor for the number of stones in the bin where the bin numbers are
     * indexed in the following way:<pre>
     -----------------------------------------
     |    | 12 |  11 | 10 |  9 |  8 |  7 |    |
     | 13 |-----------------------------|  6 |
     |    |  0 |  1 |  2 |  3 |  4 |  5 |    |
     -----------------------------------------</pre>
     * @param bin the bin to query for number of stones
     * @return the number of stones in the bin
     */
    public int stoneCount(int bin) {
        return state[bin];
    }

    /**
     * @return true; the move from this state is legal<br>
     * false; otherwise
     */
    public boolean illegalMove(int bin) {
        return (state[bin] == 0 || bin < 0 || bin == 6 || bin > 12);
    }

    /**
     * applies the given move to the given state
     * @param bin the move to apply to the state
     * @returns true if the player gets to move again, false otherwise.
     */
    public boolean applyMove(int bin)
    {
        int stones = state[bin];
        //clear the original bin
        state[bin] = 0;

        for (int i = 0; i < stones; ++i)
        {
            int nextBin = (bin+i+1)%14;
            if (!(nextBin == 6 && bin > 6) && !(nextBin == 13 && bin < 7))
                ++state[nextBin];
            else
                ++stones;
        }
        int lastBin = (bin+stones)%14;
        boolean lastBinEmpty = state[lastBin] == 1;
        boolean lastBinOnYourSide = bin/7 == lastBin/7;
        if ((lastBin == 6 || lastBin == 13) && !gameOver())
        {
            return true;
        }
        if (lastBinEmpty && lastBinOnYourSide && lastBin != 6 && lastBin != 13)
        {
            int mancalaBin =  mancalaOf(bin);
            int neighborBin = neighborOf(lastBin);
            state[mancalaBin] += state[neighborBin] + 1;
            state[neighborBin] = 0;
            state[lastBin] = 0;
        }
        if (gameOver())
            stonesToMancalas();
        return false;
    }

    /**
     * switches the board's current perspective so that the bottom and top rows
     * switch.
     */
    public void rotate()
    {
        int[] rotatedState = new int[state.length];
        for (int i = 0; i < state.length; ++i)
            rotatedState[(i+7)%14] = state[i];
        state = rotatedState;
    }

    private int stonesCount(int bin1, int bin2) {
        int stones = 0;
        for (int i = bin1; i <= bin2; ++i)
            stones += state[i];
        return stones;
    }

    void stonesToMancalas() {
        state[6]  += stonesCount(0,5);
        state[13] += stonesCount(7,12);
        for (int i = 0; i < 6; ++i) {
            state[i] = 0;
            state[i+7] = 0;
        }
    }

    public int neighborOf(int bin) {
        if (bin == 13)
            return bin;
        else
            return 12-bin;
    }

    public int mancalaOf(int bin) {
        return bin / 7 * 7 + 6;
    }

    private int[] arrayCopy(int[] source) {
        if (source == null)
            return null;
        int[] sink = new int[source.length];
        for (int i = 0; i < source.length; ++i)
            sink[i] = source[i];
        return sink;
    }

}