# Constants for player symbols
EMPTY = 0
X = 1
O = 2

# Constants for game result
DRAW = 0
X_WINS = 1
O_WINS = 2
IN_PROGRESS = 3

# Define winning combinations
WINNING_COMBINATIONS = [
    [0, 1, 2], [3, 4, 5], [6, 7, 8],  # Rows
    [0, 3, 6], [1, 4, 7], [2, 5, 8],  # Columns
    [0, 4, 8], [2, 4, 6]              # Diagonals
]

# Main function to play the game
def main():
    board = [EMPTY] * 9  # Initialize the board
    current_player = X  # X always starts
    winner = IN_PROGRESS

    while winner == IN_PROGRESS:
        print_board(board)
        if current_player == X:
            move = get_player_move(board)
            board[move] = X
        else:
            move = get_computer_move(board, current_player)
            board[move] = O
        winner = check_winner(board)
        current_player = O if current_player == X else X  # Switch players

    print_board(board)
    if winner == X_WINS:
        print("X wins!")
    elif winner == O_WINS:
        print("O wins!")
    else:
        print("It's a draw!")

# Function to get the player's move from the console
def get_player_move(board):
    while True:
        try:
            move = int(input("Enter your move (0-8): "))
            if 0 <= move <= 8 and board[move] == EMPTY:
                return move
            else:
                print("Invalid move. Please try again.")
        except ValueError:
            print("Invalid input. Please enter a number.")

# Function to get the computer's move using heuristic approach
def get_computer_move(board, player):
    if player == X:
        opponent = O
    else:
        opponent = X

    # Check if computer can win in the next move
    for move in range(9):
        if board[move] == EMPTY:
            board[move] = player
            if check_winner(board) == player:
                board[move] = EMPTY
                return move
            board[move] = EMPTY

    # Check if opponent can win in the next move and block them
    for move in range(9):
        if board[move] == EMPTY:
            board[move] = opponent
            if check_winner(board) == opponent:
                board[move] = EMPTY
                return move
            board[move] = EMPTY

    # Use heuristic: prioritize corners, then center, then edges
    corners = [0, 2, 6, 8]
    for corner in corners:
        if board[corner] == EMPTY:
            return corner

    if board[4] == EMPTY:
        return 4

    edges = [1, 3, 5, 7]
    for edge in edges:
        if board[edge] == EMPTY:
            return edge

# Function to check if there is a winner or if it's a draw
def check_winner(board):
    # Check for winning combinations
    for combination in WINNING_COMBINATIONS:
        if board[combination[0]] != EMPTY and \
                board[combination[0]] == board[combination[1]] and \
                board[combination[1]] == board[combination[2]]:
            return X_WINS if board[combination[0]] == X else O_WINS

    # Check for draw
    if EMPTY not in board:
        return DRAW

    return IN_PROGRESS

# Function to print the current state of the board
def print_board(board):
    print("  0 1 2")
    for i in range(3):
        print(i, end=" ")
        for j in range(3):
            index = i * 3 + j
            if board[index] == X:
                print("X", end=" ")
            elif board[index] == O:
                print("O", end=" ")
            else:
                print("-", end=" ")
        print()

if __name__ == "__main__":
    main()