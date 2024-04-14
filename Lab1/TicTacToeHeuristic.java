import java.util.Scanner;

public class TicTacToeHeuristic {

    public static void main(String[] args) {
        char[][] board = {
                {' ', ' ', ' '},
                {' ', ' ', ' '},
                {' ', ' ', ' '}
        };

        char currentPlayer = 'X';

        while (!isGameOver(board)) {
            printBoard(board);

            if (currentPlayer == 'X') {
                playerMove(board);
            } else {
                heuristicMove(board);
            }

            currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
        }

        printBoard(board);
        char winner = getWinner(board);
        if (winner == ' ') {
            System.out.println("It's a draw!");
        } else {
            System.out.println("Player " + winner + " wins!");
        }
    }

    private static void printBoard(char[][] board) {
        System.out.println("-------------");
        for (int i = 0; i < 3; i++) {
            System.out.print("| ");
            for (int j = 0; j < 3; j++) {
                System.out.print(board[i][j] + " | ");
            }
            System.out.println("\n-------------");
        }
    }

    private static void playerMove(char[][] board) {
        Scanner scanner = new Scanner(System.in);
        int row, col;
        do {
            System.out.println("Enter your move (row and column, separated by space): ");
            row = scanner.nextInt() - 1;
            col = scanner.nextInt() - 1;
        } while (!isValidMove(board, row, col));

        board[row][col] = 'X';
    }

    private static void heuristicMove(char[][] board) {
        int[] bestMove = findBestMove(board, 'O');
        board[bestMove[0]][bestMove[1]] = 'O';
    }

    private static boolean isValidMove(char[][] board, int row, int col) {
        return (row >= 0 && row < 3 && col >= 0 && col < 3 && board[row][col] == ' ');
    }

    private static boolean isGameOver(char[][] board) {
        return getWinner(board) != ' ' || isBoardFull(board);
    }

    private static boolean isBoardFull(char[][] board) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == ' ') {
                    return false;
                }
            }
        }
        return true;
    }

    private static char getWinner(char[][] board) {
        // Check rows, columns, and diagonals for a winner
        for (int i = 0; i < 3; i++) {
            if (board[i][0] == board[i][1] && board[i][1] == board[i][2] && board[i][0] != ' ') {
                return board[i][0];
            }
            if (board[0][i] == board[1][i] && board[1][i] == board[2][i] && board[0][i] != ' ') {
                return board[0][i];
            }
        }

        if (board[0][0] == board[1][1] && board[1][1] == board[2][2] && board[0][0] != ' ') {
            return board[0][0];
        }

        if (board[0][2] == board[1][1] && board[1][1] == board[2][0] && board[0][2] != ' ') {
            return board[0][2];
        }

        return ' '; // No winner yet
    }

    private static int[] findBestMove(char[][] board, char player) {
        int[] bestMove = {-1, -1};
        int bestScore = Integer.MIN_VALUE;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == ' ') {
                    board[i][j] = player;
                    int score = minimax(board, 0, false);
                    board[i][j] = ' '; // Undo the move

                    if (score > bestScore) {
                        bestScore = score;
                        bestMove[0] = i;
                        bestMove[1] = j;
                    }
                }
            }
        }

        // Check if the best move is symmetric and adjust accordingly
        int[] symmetricMove = getSymmetricMove(bestMove);
        if (bestScore < 0 && isValidMove(board, symmetricMove[0], symmetricMove[1])) {
            return symmetricMove;
        }

        return bestMove;
    }

    private static int[] getSymmetricMove(int[] move) {
        return new int[]{2 - move[0], 2 - move[1]};
    }

    private static int minimax(char[][] board, int depth, boolean isMaximizing) {
        char winner = getWinner(board);

        if (winner != ' ') {
            return (winner == 'O') ? 1 : -1;
        }

        if (isBoardFull(board)) {
            return 0;
        }

        if (isMaximizing) {
            int bestScore = Integer.MIN_VALUE;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (board[i][j] == ' ') {
                        board[i][j] = 'O';
                        int score = minimax(board, depth + 1, false);
                        board[i][j] = ' '; // Undo the move
                        bestScore = Math.max(score, bestScore);
                    }
                }
            }
            return bestScore;
        } else {
            int bestScore = Integer.MAX_VALUE;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (board[i][j] == ' ') {
                        board[i][j] = 'X';
                        int score = minimax(board, depth + 1, true);
                        board[i][j] = ' '; // Undo the move
                        bestScore = Math.min(score, bestScore);
                    }
                }
            }
            return bestScore;
        }
    }
}
