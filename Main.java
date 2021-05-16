
import java.util.Scanner;

public class Main {
    static String[][] board = new String[3][3];
    public static void main(String[] args) {
        //MINIMAX
        AI ai = new AI();
        Scanner scanner = new Scanner(System.in);
        userInterface(scanner, ai);
    }
    public static void userInterface(Scanner scanner, AI ai){
        generateBoard();
        while(true){
            System.out.print("Input command: ");
            String command = scanner.nextLine();
            String[] args = command.split(" ");
            if(args.length == 1 && args[0].equals("exit")){
                return;
            }
            if((args.length != 3  || !menuInputVerifier(args))){
                System.out.println("Bad parameters!");
            } else {
                showBoard();
                startGameMode(args, ai, scanner);
                return;
            }
        }
    }
    public static boolean menuInputVerifier(String[] args){
        return ((args[0].equals("start"))
                && (args[1].equals("user") 
                || args[1].equals("easy") 
                || args[1].equals("medium") 
                ||args[1].equals("hard") )
                && (args[2].equals("user") 
                || args[2].equals("easy") 
                || args[2].equals("medium") 
                ||args[2].equals("hard")));
    }
    public static void startGameMode(String[]args, AI ai, Scanner scanner){
        while(true){
            int num = 1;
            while(num < 3) {
                switch (args[num]) {
                    case "user":
                        playerMove(scanner, num == 1?"X":"O");
                        if (checkWinCondition(num == 1?"X":"O")) return;
                        break;
                    case "easy":
                        System.out.println("Making move level \"easy\"");
                        board = ai.easy(board, num == 1?"X":"O");
                        if (checkWinCondition(num == 1?"X":"O")) return;
                        break;
                    case "medium":
                        System.out.println("Making move level \"medium\"");
                        board = ai.medium(board,num == 1?"X":"O");
                        if (checkWinCondition(num == 1?"X":"O")) return;
                        break;
                    case "hard":
                        System.out.println("Making move level \"hard\"");
                        board = ai.hard(board,num == 1?"X":"O");
                        if (checkWinCondition(num == 1?"X":"O")) return;
                        break;
                }
                num++;
            }
        }
    }

    public static boolean playerMove(Scanner scanner, String sign){
        while(true){
            try{
                System.out.println("Enter the coordinates: ");
                String coordinates = scanner.nextLine();
                coordinates = coordinates.trim();
                coordinates = coordinates.replace(" ", "");
                if(!isInteger(coordinates.charAt(0) + "") || !isInteger(coordinates.charAt(1)+ "")){
                    System.out.println("You should enter numbers!");
                }
                int[]xy = {Integer.parseInt(coordinates.charAt(0)+ "")-1,Integer.parseInt(coordinates.charAt(1)+ "")-1};
                if (xy[0] < 0 || xy[0] > 2 || xy[1] < 0 || xy[1] > 2) {
                    System.out.println("Coordinates should be from 1 to 3!");
                } else if (!board[xy[0]][xy[1]].equals(" ")) {
                    System.out.println("This cell is occupied! Choose another one!");
                } else {
                    board[xy[0]][xy[1]] = sign;
                    showBoard();
                    return true;
                }
            }catch(Exception e){
                System.out.println("something went wrong");
            }
        }
    }
    public static boolean checkWinCondition(String sign){
        if(checkDIAGONALS("X") || checkROWS("X") || checkCOLUMNS("X")){
            System.out.println("X wins");
            return true;
        }
        if(checkDIAGONALS("O") || checkROWS("O") || checkCOLUMNS("O")){
            System.out.println("O wins");
            return true;
        }
        if(draw("X") || draw("O")){
            System.out.println("Draw");
            return true;
        }
        return false;
    }

    public static boolean draw(String sign){
        return !checkCOLUMNS(sign) && !checkROWS(sign) && !checkDIAGONALS(sign) && countSign(" ") == 0;
    }

    public static int countSign(String sign){
        int count = 0;
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                if(board[i][j].equals(sign)){
                    count++;
                }
            }
        }
        return count;
    }
    public static boolean checkROWS(String sign){
        for(int i = 0; i < 3; i++){
            if(board[i][0].equals(sign) && board[i][1].equals(sign) && board[i][2].equals(sign)){
                return true;
            }
        }
        return false;
    }
    public static boolean checkCOLUMNS(String sign){
        for(int i = 0; i < 3; i++){
            if(board[0][i].equals(sign) && board[1][i].equals(sign) && board[2][i].equals(sign)){
                return true;
            }
        }
        return false;
    }
    public static boolean checkDIAGONALS(String sign){
        if(board[0][0].equals(sign) && board[1][1].equals(sign) && board[2][2].equals(sign)){
            return true;
        }
        if(board[0][2].equals(sign) && board[1][1].equals(sign) && board[2][0].equals(sign)){
            return true;
        }
        return false;
    }

    public static void generateBoard(){
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                board[i][j] = " ";
            }
        }
    }
    public static void showBoard(){
        System.out.println("---------");
        for(int i = 0; i < 3; i++){
            System.out.print("| ");
            for(int j = 0; j < 3; j++){
                System.out.print(board[i][j] + " ");
            }
            System.out.println("|");
        }
        System.out.println("---------");
    }
    public static boolean isInteger(String num){
        try{
            Integer.parseInt(num);
            return true;
        }catch(Exception e){
            return false;
        }
    }
}

