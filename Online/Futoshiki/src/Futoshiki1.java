import org.chocosolver.solver.Model;
import org.chocosolver.solver.Solver;
import org.chocosolver.solver.variables.IntVar;


public class Futoshiki1 {
    public static void main(String[] args) {


        int i, j, k;


// 1. Create a Model
        Model model = new Model("Futoshiki 01");
// 2. Create variables



        /* the board which is 9 X 9 */
        /* (0, 0) is the top left position and (8, 8) is the bottom right position */
        /*each cell is an integer variable taking their value in [1, 9] */
        IntVar[][] bd = model.intVarMatrix("bd", 9, 9, 1, 9);


        /* the nine rows */
        /* each row is an array of 9 integer variables taking their value in [1, 9] */
        IntVar[] r0 = model.intVarArray("r0", 9, 1, 9);
        IntVar[] r1 = model.intVarArray("r1", 9, 1, 9);
        IntVar[] r2 = model.intVarArray("r2", 9, 1, 9);
        IntVar[] r3 = model.intVarArray("r3", 9, 1, 9);
        IntVar[] r4 = model.intVarArray("r4", 9, 1, 9);
        IntVar[] r5 = model.intVarArray("r5", 9, 1, 9);
        IntVar[] r6 = model.intVarArray("r6", 9, 1, 9);
        IntVar[] r7 = model.intVarArray("r7", 9, 1, 9);
        IntVar[] r8 = model.intVarArray("r8", 9, 1, 9);

        /* the nine columns */
        /* each column is an array of 9 integer variables taking their value in [1, 9] */

        IntVar[] c0 = model.intVarArray("c0", 9, 1, 9);
        IntVar[] c1 = model.intVarArray("c1", 9, 1, 9);
        IntVar[] c2 = model.intVarArray("c2", 9, 1, 9);
        IntVar[] c3 = model.intVarArray("c3", 9, 1, 9);
        IntVar[] c4 = model.intVarArray("c4", 9, 1, 9);
        IntVar[] c5 = model.intVarArray("c5", 9, 1, 9);
        IntVar[] c6 = model.intVarArray("c6", 9, 1, 9);
        IntVar[] c7 = model.intVarArray("c7", 9, 1, 9);
        IntVar[] c8 = model.intVarArray("c8", 9, 1, 9);

        /* the nine blocks or boxes */
        /* each box is an array of 9 integer variables taking their value in [1, 9] */


// 3. Post constraints

        /* post constraints for the given hints or clues */

        //post constraints for given hints
        model.arithm (bd[0][5], "=", 9).post();
        model.arithm (bd[0][8], "=", 2).post();

        model.arithm (bd[1][1], "=", 8).post();
        model.arithm (bd[1][7], "=", 4).post();

        model.arithm (bd[2][6], "=", 2).post();
        model.arithm (bd[2][7], "=", 3).post();

        model.arithm (bd[3][5], "=", 2).post();


        model.arithm (bd[4][0], "=", 3).post();
        model.arithm (bd[4][2], "=", 5).post();
        model.arithm (bd[4][5], "=", 6).post();


        model.arithm (bd[5][1], "=", 7).post();
        model.arithm (bd[5][6], "=", 6).post();

        model.arithm (bd[7][3], "=", 7).post();

        //more constraints

        model.arithm (bd[0][2], ">", bd[1][2]).post();
        model.arithm (bd[0][3], "<", bd[0][4]).post();
        model.arithm (bd[0][6], "<", bd[0][7]).post();
        model.arithm (bd[0][6], ">", bd[1][6]).post();
        model.arithm (bd[0][8], ">", bd[1][8]).post();

        model.arithm (bd[1][3], "<", bd[1][4]).post();
        model.arithm (bd[1][5], "<", bd[1][6]).post();

        model.arithm (bd[2][0], ">", bd[3][0]).post();
        model.arithm (bd[2][1], "<", bd[3][1]).post();
        model.arithm (bd[2][2], ">", bd[2][3]).post();

        model.arithm (bd[3][4], "<", bd[3][5]).post();

        model.arithm (bd[4][4], ">", bd[4][5]).post();
        model.arithm (bd[4][3], "<", bd[5][3]).post();
        model.arithm (bd[4][6], ">", bd[5][6]).post();
        model.arithm (bd[4][8], ">", bd[5][8]).post();

        model.arithm (bd[5][0], "<", bd[6][0]).post();
        model.arithm (bd[5][3], "<", bd[6][3]).post();
        model.arithm (bd[5][4], "<", bd[6][4]).post();
        model.arithm (bd[5][7], "<", bd[5][8]).post();
        model.arithm (bd[5][8], ">", bd[6][8]).post();

        model.arithm (bd[6][0], ">", bd[6][1]).post();

        model.arithm (bd[7][1], ">", bd[8][1]).post();
        model.arithm (bd[7][5], "<", bd[7][6]).post();
        model.arithm (bd[7][7], ">", bd[8][7]).post();
        model.arithm (bd[7][8], ">", bd[8][8]).post();

        model.arithm (bd[8][2], ">", bd[8][3]).post();
        model.arithm (bd[8][5], "<", bd[8][6]).post();

        /* for the nine row variables */
        /* each row variable is associated with appropriate cell positions in board */

        for ( j = 0; j < 9; j++)
            model.arithm (bd[0][j], "=", r0[j]).post();

        for ( j = 0; j < 9; j++)
            model.arithm (bd[1][j], "=", r1[j]).post();

        for ( j = 0; j < 9; j++)
            model.arithm (bd[2][j], "=", r2[j]).post();

        for ( j = 0; j < 9; j++)
            model.arithm (bd[3][j], "=", r3[j]).post();

        for ( j = 0; j < 9; j++)
            model.arithm (bd[4][j], "=", r4[j]).post();

        for ( j = 0; j < 9; j++)
            model.arithm (bd[5][j], "=", r5[j]).post();

        for ( j = 0; j < 9; j++)
            model.arithm (bd[6][j], "=", r6[j]).post();

        for ( j = 0; j < 9; j++)
            model.arithm (bd[7][j], "=", r7[j]).post();

        for ( j = 0; j < 9; j++)
            model.arithm (bd[8][j], "=", r8[j]).post();



        /* for the nine column variables */
        /* each column variable is associated with appropriate cell positions in board */


        for ( i = 0; i < 9; i++)
            model.arithm (bd[i][0], "=", c0[i]).post();

        for ( i = 0; i < 9; i++)
            model.arithm (bd[i][1], "=", c1[i]).post();

        for ( i = 0; i < 9; i++)
            model.arithm (bd[i][2], "=", c2[i]).post();

        for ( i = 0; i < 9; i++)
            model.arithm (bd[i][3], "=", c3[i]).post();

        for ( i = 0; i < 9; i++)
            model.arithm (bd[i][4], "=", c4[i]).post();

        for ( i = 0; i < 9; i++)
            model.arithm (bd[i][5], "=", c5[i]).post();

        for ( i = 0; i < 9; i++)
            model.arithm (bd[i][6], "=", c6[i]).post();
        for ( i = 0; i < 9; i++)
            model.arithm (bd[i][7], "=", c7[i]).post();
        for ( i = 0; i < 9; i++)
            model.arithm (bd[i][8], "=", c8[i]).post();



        /* post global constraint alldiff for the nine rows */

        model.allDifferent(r0).post();
        model.allDifferent(r1).post();
        model.allDifferent(r2).post();
        model.allDifferent(r3).post();
        model.allDifferent(r4).post();
        model.allDifferent(r5).post();
        model.allDifferent(r6).post();
        model.allDifferent(r7).post();
        model.allDifferent(r8).post();



        /* post global constraint alldiff for the nine columns */

        model.allDifferent(c0).post();
        model.allDifferent(c1).post();
        model.allDifferent(c2).post();
        model.allDifferent(c3).post();
        model.allDifferent(c4).post();
        model.allDifferent(c5).post();
        model.allDifferent(c6).post();
        model.allDifferent(c7).post();
        model.allDifferent(c8).post();




        /* post global constraint alldiff for the nine boxes */


// 4. Solve the problem

        Solver solver = model.getSolver();

        solver.showStatistics();
        solver.showSolutions();
        solver.findSolution();


// 5. Print the solution

        for ( i = 0; i < 9; i++)
        {
            for ( j = 0; j < 9; j++)
            {

                System.out.print(" ");
                /* get the value for the board position [i][j] for the solved board */
                k = bd [i][j].getValue();
                System.out.print(k);
            }
            System.out.println();
        }


    }

}

