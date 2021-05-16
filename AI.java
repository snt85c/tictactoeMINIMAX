
import java.util.Random;

public class AI {
    Random rand = new Random();

    public String[][] easy(String[][] board, String sign){
        //method for EASY move, just goes random on empty cells
        while(true){
            int first = rand.nextInt(3);
            int second = rand.nextInt(3);
            if(board[first][second].equals(" ")){
                board[first][second] = sign;
                Main.showBoard();
                Main.checkWinCondition(sign);
                return board;
            }
        }
    }
    
    public String[][] medium (String[][] board, String sign){
        /*method for MEDIUM move, checks if there are two signs together, 
        wins or prevents the loss, otherwise goes random*/
        while(true){
            if(oneMoveRow(board, sign, sign) 
            || oneMoveRow(board, sign.equals("X")?"O":"X", sign )){
                break;
            } else if(oneMoveColumn(board, sign, sign) 
            || oneMoveColumn(board,sign.equals("X")?"O":"X", sign )){
                break;
            } else if(oneMoveDiagonal(board, sign, sign) 
            || oneMoveDiagonal(board,sign.equals("X")?"O":"X", sign )){
                break;
              }else {
                easy(board, sign);
                break;
            }
        }
        return board;
    }
    
    public boolean oneMoveRow(String[][] board, String sign, String assignment){
        //method for MEDIUM move
          for(int row = 0; row < 3; row++){ 
            /*declare and initialize / reset between rows, sign and assignemtn differs 
            depending on checking for winning in one move / prevent loss in one move*/
            int first = -1;
            int second = -1;
            int counter = 0;
            for(int col = 0; col < 3; col++){
                if(board[row][col].equals(sign)){
                    counter++;
                }
                if(board[row][col].equals(" ")){
                    first = row;
                    second = col;
                }
                if(counter == 2 && first != -1){
                    board[first][second] = assignment;
                    Main.showBoard();
                    return true;
                }
            }
        }
      return false;
    }

    public boolean oneMoveColumn(String[][] board, String sign, String assignment){
        //method for MEDIUM move
        for(int col = 0; col < 3; col++){
           /*declare and initialize / reset between columns, sign and assignemtn differs 
            depending on checking for winning in one move / prevent loss in one move*/
            int first = -1;
            int second = -1;
            int counter = 0;
            for(int row = 0; row < 3; row++){
                if(board[row][col].equals(sign)){
                    counter++;
                }
                if(board[row][col].equals(" ")){
                    first = row;
                    second = col;
                }
                if(counter == 2 && first != -1){
                    board[first][second] = assignment;
                    Main.showBoard();
                    return true;
                }
            }
        }
      return false;
    }

  public boolean oneMoveDiagonal(String[][] board, String sign, String assignment){
        /*method for MEDIUM move, sign and assignemtn differs 
        depending on checking for winning in one move / prevent loss in one move*/
        int first = -1;
        int second = -1;
        int counter = 0;
        for(int i = 0; i < 3; i++){
            if(board[i][i].equals(sign)){
                counter++;
            }
            if(board[i][i].equals(" ")){
                first = i;
                second = i;
            }
            if(counter == 2 && first != -1){
                board[first][second] = assignment;
                Main.showBoard();
                return true;
            }
        }
        first = -1;
        second = -1;
        counter = 0;
        int  d = 2;
        for(int i = 0; i < 3; i++){
            if(board[i][d].equals(sign)){
                counter++;
            }
            if(board[i][d].equals(" ")){
                first = i;
                second = d;
            }
            if(counter == 2 && first != -1){
                board[first][second] = assignment;
                Main.showBoard();
                return true;
            }
            d--;
        }
        return false;
    }
    
public String[][] hard (String[][] board, String sign ){
    //method for HARD move
    String counterSign = sign.equals("X")?"O":"X"; //set the opposite sign
    int[] bestMove = new int[]{-1, -1};//sets array for optimal move
    int bestValue = Integer.MIN_VALUE;//set initial value of bestValue
        for (int row = 0; row < 3; row++) {//cycle between the entire matrix
            for (int col = 0; col < 3; col++) {
                if (board[row][col] == " ") {//when a cell is found empty
                    board[row][col] = sign;//assign the current sign
                    int moveValue = miniMax(board, 0, false, sign, counterSign);//return minimax for this value
                    board[row][col] = " ";//remove the sign we assigned before
                    if (moveValue > bestValue) {
                        bestMove[0] = row;
                        bestMove[1] = col;
                        bestValue = moveValue;
                    }
                }
            }
        }
      board[bestMove[0]][bestMove[1]] = sign;//assign optimal move 
      Main.showBoard();
      return board;
}

public int miniMax(String[][] board, int depth, boolean isMaximizing, String sign, String counterSign){
    //MINIMAX for HARD move
       int boardVal = checkWinCondition4MiniMax(board, depth, sign, counterSign);
       /*after assigning a sign to an empty slot, we check if there is a win(+10,-10, 0)*/
        if (Math.abs(boardVal) == 10 || Math.abs(boardVal) == -10) {//base case, if found, return.
             return boardVal;
        }
        if(Main.countSign(" ") == 0){//draw case, if no more slots empty, return 0 as draw
          return 0;
        }
        // Maximising player, find the maximum attainable value. snce there was no base case, we continue
        if (isMaximizing) {
            int highestVal = Integer.MIN_VALUE;//set a lowest value, just for comparison later
            for (int row = 0; row < 3; row++) {
                for (int col = 0; col < 3; col++) {//cycle trough the matrix
                    if (board[row][col] == " ") {
                        board[row][col] = sign;//assign a sign to an empty slot
                        highestVal = Math.max(highestVal, miniMax(board, depth + 1, !isMaximizing, sign, counterSign));
                      /*// RECURSION//start a new method, checks if the new board(we assigned something to an empty slot) 
                      hits the base case, then as we are passing !isMaximazing, is the other player turn to be simulated 
                      (goes to the else section, assign counterSign to an empty slot, the recursion again until base case and so on)*/
                        board[row][col] = " ";//remove the sign 
                    }
                }
            }
            return highestVal;
            // Minimising player, find the minimum attainable value, the same, but with a different sign;
        } else {
            int lowestVal = Integer.MAX_VALUE;
              for (int row = 0; row < 3; row++) {
                for (int col = 0; col < 3; col++) {
                    if (board[row][col] == " ") {
                         board[row][col] = counterSign;
                        lowestVal = Math.min(lowestVal, miniMax(board,
                                depth + 1, !isMaximizing, sign, counterSign));
                        board[row][col] = " ";
                    }
                }
            }
            
         return lowestVal;
    }
}


    public int checkWinCondition4MiniMax(String[][] board,int depth, String sign, String counterSign){
        //method for MINIMAX in HARD move
      //i had to unwrap it from the methods in Main, as there was a bug somewhere that switched the sign 
       for(int row = 0; row < 3; row++){//check rows for winning with one sign
            if(board[row][0].equals(sign) && board[row][1].equals(sign) && board[row][2].equals(sign)){
                return 10;
            }
        }

        for(int row = 0; row < 3; row++){//check rows for win on the other sign
            if(board[row][0].equals(counterSign) && board[row][1].equals(counterSign) && board[row][2].equals(counterSign)){
                return -10;
            }
        }
        for(int col = 0; col < 3; col++){//check columns for one sign
            if(board[0][col].equals(sign) && board[1][col].equals(sign) && board[2][col].equals(sign)){
                return 10;
            }
        }
        for(int col = 0; col < 3; col++){//check columns for the other sign
            if(board[0][col].equals(counterSign) && board[1][col].equals(counterSign) && board[2][col].equals(counterSign)){
                return -10;
            }
        }
        if(board[0][0].equals(sign) && board[1][1].equals(sign) && board[2][2].equals(sign)){
          //check first diagonal one sign
            return 10;
        }
        if(board[0][2].equals(sign) && board[1][1].equals(sign) && board[2][0].equals(sign)){
            //check second diagonal one sign
            return 10;
        }
        if(board[0][0].equals(counterSign) && board[1][1].equals(counterSign) && board[2][2].equals(counterSign)){
            //check first diagonal other sign
            return -10;
        }
        if(board[0][2].equals(counterSign) && board[1][1].equals(counterSign) && board[2][0].equals(counterSign)){
            //check second diagonal other sign
            return -10;
        }
        return 0;
    }
    //end
}
