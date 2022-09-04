public class Node {

    public Node parent;
    public int[][] matrix;

    // * tile coordinates
    public int x, y;

    // Number of misplaced tiles
    public int cost;

    // The number of moves so far
    public int level;

    public Node(int[][] matrix, int x, int y, int newX, int newY, int level, Node parent) {
        this.parent = parent;
        this.matrix = new int[matrix.length][matrix.length];
        for (int i = 0; i < matrix.length; i++) {
            System.arraycopy(matrix[i], 0, this.matrix[i], 0, matrix.length);
        }

        // Swap value
        this.matrix[x][y]       = this.matrix[x][y] + this.matrix[newX][newY];
        this.matrix[newX][newY] = this.matrix[x][y] - this.matrix[newX][newY];
        this.matrix[x][y]       = this.matrix[x][y] - this.matrix[newX][newY];


        this.cost = Integer.MAX_VALUE;
        this.level = level;
        this.x = newX;
        this.y = newY;

    }
}
