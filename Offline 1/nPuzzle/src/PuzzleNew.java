import java.util.*;

public class PuzzleNew {
    
    // Bottom, left, top, right
    int[] row = {1, 0, -1, 0};
    int[] col = {0, -1, 0, 1};
    HashMap <Integer,Integer> rowmap = new HashMap<>();
    HashMap <Integer,Integer> colmap = new HashMap<>();

    public boolean isSolvable(int[][] matrix) {
        int count = 0;
        List<Integer> array = new ArrayList<>();

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                array.add(matrix[i][j]);
            }
        }

        Integer[] anotherArray = new Integer[array.size()];
        array.toArray(anotherArray);

        for (int i = 0; i < anotherArray.length - 1; i++) {
            for (int j = i + 1; j < anotherArray.length; j++) {
                if (anotherArray[i] != 0 && anotherArray[j] != 0 && anotherArray[i] > anotherArray[j]) {
                    count++;
                }
            }
        }

        System.out.println("Number of Inversion : " + count);
        int b=0;
        for(int i=matrix.length - 1; i>=0; i--)
        {
            for(int j=0;j<matrix.length;j++)
            {
                if (matrix[i][j] == 0) {
                    b = i;
                    break;
                }
            }
        }

        if( matrix.length%2 == 0){
            if(b%2==0 && count%2==1){
                System.out.println("It is Solvable");
                return true;
            }
            else if(b%2==1 && count%2==0){
                System.out.println("It is Solvable");
                return true;
            }
            else{
                System.out.println("It is NOT Solvable");
                return false;
            }
        }
        else{
            if(count%2==0){
                System.out.println("It is Solvable");
                return true;
            }
            else{
                System.out.println("It is NOT Solvable");
                return false;
            }
        }
    }

    public int calculateCost(int[][] initial, int[][] goal) {
        int count = 0;
        int n = initial.length;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (initial[i][j] != 0 && initial[i][j] != goal[i][j]) {
                    count++;
                }
            }
        }
        return count;
    }

    public void Shifting(int[][]matrix)
    {
        for(int i=0;i<matrix.length;i++)
        {
            for(int j=0;j<matrix.length;j++)
            {
                rowmap.put(matrix[i][j],i);
                colmap.put(matrix[i][j],j);

            }
        }
    }

    public int ManhattonCost(int [][] initial,int [][] goal)
    {
        int cst=0;
        int v;
        for(int i=0;i<initial.length;i++)
        {
            for(int j=0;j<initial.length;j++)
            {
                if(goal [i][j]!=0 && initial[i][j]!= goal[i][j])
                {
                    v = goal[i][j];
                    cst = cst + Count(v,i,j);

                }
            }
        }
        return cst;
    }

    public int Count(int a ,int i, int j)
    {

        Integer p = rowmap.get(a);
        Integer q = colmap.get(a);
        return Math.abs(p-i) + Math.abs(q-j);
    }

    public int LinearConflictCost(int [][] initial, int[][]goal){
        int conflicts = 0;

        // row conflicts -
        for (int r = 0; r < initial.length; r++) {
            for (int cl = 0; cl < initial.length; cl++) {
                for (int cr = cl + 1; cr < initial.length; cr++) {
                    if (goal[r][cl] !=0 && goal[r][cr] !=0 &&
                            r == rowmap.get(goal[r][cl]) && rowmap.get(goal[r][cl]).equals(rowmap.get(goal[r][cr]))
                            && colmap.get(goal[r][cl]) > colmap.get(goal[r][cr]) ) {
                        conflicts++;
                    }
                }
            }
        }

        // column conflicts -
        for (int c = 0; c < initial.length; c++) {
            for (int rU = 0; rU < initial.length; rU++) {
                for (int rD = rU + 1; rD < initial.length; rD++) {
                    if (goal[rU][c] != 0 && goal[rD][c] != 0 && c == colmap.get(goal[rU][c]) && colmap.get(goal[rU][c]).equals(colmap.get(goal[rD][c])) 
                            && rowmap.get(goal[rU][c]) > rowmap.get(goal[rD][c])) {
                        conflicts++;
                    }
                }
            }
        }

        return ManhattonCost(initial,goal) + 2 * conflicts;
    }

    public int SwapNumber(int [] [] matrix1,int [][] matrix2)
    {
        int count=0;
        for(int i=0;i<matrix1.length;i++)
        {
            for(int j=0;j<matrix1.length;j++)
            {
                if(matrix1[i][j]!=matrix2[i][j])
                {
                    count++;
                }
            }
        }
        return count;
    }

    public boolean isSafe(int x, int y,int dimension) {
        return (x >= 0 && x < dimension && y >= 0 && y < dimension);
    }

    public int solveHamming(int[][] initial, int[][] goal, int x, int y) {
        int nodes = 0;
        PriorityQueue <Node> pq= new PriorityQueue<>(1000, Comparator.comparingInt((Node a) -> (a.cost + a.level)));
        Node root = new Node(initial, x, y, x, y, 0, null);
        root.cost = calculateCost(initial, goal);
        pq.add(root);
        while (!pq.isEmpty()) {
            Node min = pq.poll();
            nodes++;
            if (min.cost == 0) {
                printPath(min);
                return nodes;
            }

            for (int i = 0; i < 4; i++) {
                if (isSafe(min.x + row[i], min.y + col[i],initial.length)) {
                    Node child = new Node(min.matrix, min.x, min.y, min.x + row[i], min.y + col[i], min.level + 1, min);
                    child.cost = calculateCost(child.matrix, goal);
                    pq.add(child);
                }
            }
        }
        return nodes;
    }

    public int solveManhatton(int[][] initial, int[][] goal, int x, int y) {
        int nodes = 0;
        PriorityQueue <Node>pq= new PriorityQueue<>(1000, Comparator.comparingInt((Node a) -> (a.cost + a.level)));
        Node root = new Node(initial, x, y, x, y, 0, null);
        root.cost = ManhattonCost(initial, goal);
        pq.add(root);

        while (!pq.isEmpty()) {
            Node min = pq.poll();
             nodes++;
            if (min.cost == 0) {
                printPath(min);
                return nodes;
            }

            for (int i = 0; i < 4; i++) {
                if (isSafe(min.x + row[i], min.y + col[i], initial.length)) {
                    Node child = new Node(min.matrix, min.x, min.y, min.x + row[i], min.y + col[i], min.level + 1, min);
                    child.cost = ManhattonCost(child.matrix, goal);
                    pq.add(child);
                }
            }
        }
        return nodes;
    }

    public int solveLinearConflict(int[][] initial, int[][] goal, int x, int y) {
        int nodes = 0;
        PriorityQueue <Node>pq= new PriorityQueue<>(1000, Comparator.comparingInt((Node a) -> (a.cost + a.level)));
        Node root = new Node(initial, x, y, x, y, 0, null);
        root.cost = LinearConflictCost(initial, goal);
        pq.add(root);

        while (!pq.isEmpty()) {
            Node min = pq.poll();
            nodes++;
            if (min.cost == 0) {
                printPath(min);
                return nodes;
            }

            for (int i = 0; i < 4; i++) {
                if (isSafe(min.x + row[i], min.y + col[i], initial.length)) {
                    Node child = new Node(min.matrix, min.x, min.y, min.x + row[i], min.y + col[i], min.level + 1, min);
                    child.cost = LinearConflictCost(child.matrix, goal);
                    pq.add(child);
                }
            }
        }
        return nodes;
    }
    
    public void printPath(Node root) {
        if (root == null) {
            return;
        }
        printPath(root.parent);
        printMatrix(root.matrix);
        System.out.println();
    }

    public void printMatrix(int[][] matrix) {
        String[][] init = new String[matrix.length][matrix.length];

        for(int i=0;i< matrix.length;i++)
        {
            for(int j=0;j< matrix.length;j++)
            {
                if(matrix[i][j] != 0) {
                    init[i][j] = String.valueOf(matrix[i][j]);
                }
                else{
                    init[i][j] = "*";
                }
            }
        }
        for (int i = 0; i < init.length; i++) {
            for (int j = 0; j < init.length; j++) {
                System.out.print(init[i][j] + " ");
            }
            System.out.println();
        }
    }
    
    public static void main(String[] args) throws ArrayIndexOutOfBoundsException
    {
        while (true) {
            System.out.println("Please Enter Grid Size: ");
            Scanner s = new Scanner(System.in);

            int x = 0;
            int y = 0;

            int dim = s.nextInt();

            String[][] init = new String[dim][dim];
            System.out.println("Please Enter Initial matrix : ");
            for (int i = 0; i < dim; i++) {
                for (int j = 0; j < dim; j++) {
                    init[i][j] = s.next();
                    if (init[i][j].equals("*")) {
                        x = i;
                        y = j;
                    }
                }
            }
            init[x][y] = "0";
            int[][] initial = new int[dim][dim];

            for (int i = 0; i < dim; i++) {
                for (int j = 0; j < dim; j++) {
                    initial[i][j] = Integer.parseInt(init[i][j]);
                }
            }

            int[][] goal = new int[dim][dim];
            int k = 1;
            for (int i = 0; i < dim; i++) {
                for (int j = 0; j < dim; j++) {
                    if (k < dim * dim) {
                        goal[i][j] = k;
                        k++;
                    } else {
                        goal[i][j] = 0;
                    }
                }
            }

            PuzzleNew puzzle = new PuzzleNew();
            while (true) {
                System.out.println("\nPlease enter a choice: ");
                System.out.println("1. Hamming Distance \n2. Manhattan Distance \n3. Linear Conflicts \n4. New Matrix \n5. Exit \n");
                Scanner p = new Scanner(System.in);
                int choice = p.nextInt();

                if (choice == 1) {
                    if (puzzle.isSolvable(initial)) {
                        int c = puzzle.SwapNumber(initial, goal);
                        int n = puzzle.solveHamming(initial, goal, x, y);
                        int costing = puzzle.calculateCost(initial, goal);
                        System.out.println("Hamming Heuristic cost is : " + costing);
                        System.out.println("Number of explored nodes : " + n);
                        System.out.println("Number of expanded nodes : " + c);
                    } else {
                        int costing = puzzle.calculateCost(initial, goal);
                        System.out.println("Hamming Heuristic cost is : " + costing);
                    }
                }
                if (choice == 2) {
                    if (puzzle.isSolvable(initial)) {
                        int c = puzzle.SwapNumber(initial, goal);
                        puzzle.Shifting(initial);
                        puzzle.ManhattonCost(initial, goal);
                        int n = puzzle.solveManhatton(initial, goal, x, y);
                        int man = puzzle.ManhattonCost(initial, goal);
                        System.out.println("Manhattan Heuristic cost is : " + man);
                        System.out.println("Number of explored nodes : " + n);
                        System.out.println("Number of expanded nodes : " + c);
                    } else {
                        puzzle.Shifting(initial);
                        int man = puzzle.ManhattonCost(initial, goal);
                        System.out.println("Manhattan Heuristic cost is : " + man);
                    }

                }
                if (choice == 3) {
                    if (puzzle.isSolvable(initial)) {
                        int c = puzzle.SwapNumber(initial, goal);
                        puzzle.Shifting(initial);
                        puzzle.LinearConflictCost(initial, goal);
                        int n = puzzle.solveLinearConflict(initial, goal, x, y);
                        int lin = puzzle.LinearConflictCost(initial, goal);
                        System.out.println("Linear Conflict Heuristic cost is : " + lin);
                        System.out.println("Number of explored nodes : " + n);
                        System.out.println("Number of expanded nodes : " + c);
                    } else {
                        puzzle.Shifting(initial);
                        int lin = puzzle.LinearConflictCost(initial, goal);
                        System.out.println("Linear Conflict Heuristic cost is : " + lin);
                    }

                }
                if (choice == 4) {
                    break;
                }
                if (choice == 5) {
                    return;
                }
            }
        }
    }
}