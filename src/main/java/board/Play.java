package board;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Play {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Number of rows : ");
        int r = Integer.parseInt(br.readLine());
        System.out.println("Number of columns : ");
        int c = Integer.parseInt(br.readLine());
        System.out.println("Number of snakes : ");
        int s = Integer.parseInt(br.readLine());
        System.out.println("Number of ladders : ");
        int l = Integer.parseInt(br.readLine());
        Board board = new Board(r , c , s , l);
        board.print();
        while(true) {
            System.out.println("Enter a number : ");
            int move = Integer.parseInt(br.readLine()) % 6 + 1;
            board.move(move);
            board.print();
            if(board.isWin()) {
                System.out.println("you won!! end game");
                break;
            }
        }
    }
}
