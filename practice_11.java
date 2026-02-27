import java.util.Scanner;

class practice_11т_InvalidMenuChoiceException extends Exception {
    public practice_11т_InvalidMenuChoiceException(String message) {
        super(message);
    }
}

class practice_11т_InvalidMoveException extends Exception {
    public practice_11т_InvalidMoveException(String message) {
        super(message);
    }
}

public class practice_11 {

    static int[][] field;
    static int rows = 7;
    static int cols = 7;
    static int pacmanRow;
    static int pacmanCol;
    static int score;
    static int foodCount;
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        boolean exit = false;

        while (!exit) {
            try {
                System.out.println("=== PAC-MAN ===");
                System.out.println("1 - Почати гру");
                System.out.println("2 - Інформація");
                System.out.println("3 - Вихід");
                System.out.print("Ваш вибір: ");

                if (!scanner.hasNextInt()) {
                    scanner.next();
                    throw new practice_11т_InvalidMenuChoiceException("Введено не число!");
                }

                int choice = scanner.nextInt();

                if (choice == 1) {
                    startGame();
                } else if (choice == 2) {
                    showInfo();
                } else if (choice == 3) {
                    exit = true;
                } else {
                    throw new practice_11т_InvalidMenuChoiceException("Невірний вибір!");
                }

            } catch (practice_11т_InvalidMenuChoiceException e) {
                System.out.println("Помилка: " + e.getMessage());
            }

            System.out.println();
        }

        System.out.println("Дякуємо за гру!");
    }

    static void showInfo() {
        System.out.println("Pac-Man — навчальна консольна гра.");
        System.out.println("Використовуються двовимірні масиви.");
        System.out.println("Керування: W A S D");
    }

    static void startGame() {
        initField();
        score = 0;
        boolean gameOver = false;

        while (!gameOver) {
            try {
                printField();
                System.out.println("Очки: " + score);
                System.out.println("W A S D - рух | Q - вихід");
                System.out.print("Хід: ");

                String input = scanner.next();

                if (input.length() != 1) {
                    throw new practice_11т_InvalidMoveException("Введено більше одного символу!");
                }

                char move = input.charAt(0);

                if (move == 'q' || move == 'Q') {
                    gameOver = true;
                } else {
                    movePacman(move);
                }

                if (foodCount == 0) {
                    System.out.println("Ви зібрали всю їжу!");
                    System.out.println("Гра завершена. Очки: " + score);
                    gameOver = true;
                }

            } catch (practice_11т_InvalidMoveException e) {
                System.out.println("Помилка: " + e.getMessage());
            }
        }
    }

    static void initField() {
        field = new int[rows][cols];
        foodCount = 0;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (i == 0 || i == rows - 1 || j == 0 || j == cols - 1) {
                    field[i][j] = 1;
                } else {
                    field[i][j] = 3;
                    foodCount++;
                }
            }
        }

        pacmanRow = 1;
        pacmanCol = 1;
        field[pacmanRow][pacmanCol] = 2;
        foodCount--;
    }

    static void movePacman(char move) throws practice_11т_InvalidMoveException {
        int newRow = pacmanRow;
        int newCol = pacmanCol;

        if (move == 'w' || move == 'W') newRow--;
        else if (move == 's' || move == 'S') newRow++;
        else if (move == 'a' || move == 'A') newCol--;
        else if (move == 'd' || move == 'D') newCol++;
        else throw new practice_11т_InvalidMoveException("Невірна команда руху!");
        

        if (field[newRow][newCol] == 1) {
            throw new practice_11т_InvalidMoveException("Стіну не можна пройти!");
        }

        if (field[newRow][newCol] == 3) {
            score++;
            foodCount--;
        }

        field[pacmanRow][pacmanCol] = 0;
        pacmanRow = newRow;
        pacmanCol = newCol;
        field[pacmanRow][pacmanCol] = 2;
    }

    static void printField() {
        System.out.println("ІГРОВЕ ПОЛЕ:");
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (field[i][j] == 1) System.out.print("# ");
                else if (field[i][j] == 2) System.out.print("P ");
                else if (field[i][j] == 3) System.out.print("* ");
                else System.out.print(". ");
            }
            System.out.println();
        }
    }
}