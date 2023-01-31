package board;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Board {
    Piece[][] board;
    int currentR = 0;
    int currentC = 0;
    Board(int r , int c , int l , int s) {
        board = new Piece[r][c];
        for(int i = 0 ; i < r ; i++) {
            for(int j = 0 ; j < c ; j++) {
                board[i][j] = new Piece();
                board[i][j].r = i;
                board[i][j].c = j;
            }
        }
//        for(int i = 0 ; i < s ; i++) while(!assignSnake(i));
//        for(int i = 0 ; i < l ; i++) while(!assignLadder(i));
        assignSnakeAndLadder(s , l);
    }

    private void assignSnakeAndLadder(int s, int l) {
        System.out.println(s);
        System.out.println(l);
        List<Piece> pieces = new ArrayList<>();
        for(int i = 0 ; i < board.length ; i++) {
            for(int j = 0 ; j < board[0].length ; j++) {
                if(i == 0 && j == 0) continue;
                if(i == board.length - 1 && j == board[0].length - 1) continue;
                pieces.add(board[i][j]);
            }
        }
        Collections.shuffle(pieces);
        for(int i = 0 ; i < pieces.size() ; i += 4) {
            if(i + 3 >= pieces.size()) break;
            if(s > 0) {
                assignSnake(pieces.get(i) , pieces.get(i + 1), s);
                s--;
            }
            if(l > 0) {
                assignLadder(pieces.get(i + 2), pieces.get(i + 3), l);
                l--;
            }
        }
    }

    private void assignLadder(Piece a, Piece b, int l) {
        if(a.r > b.r) {
            a.endingPiece = b;
        } else {
            b.endingPiece = a;
        }
        b.label = a.label = "L" + l;
    }

    private void assignSnake(Piece a, Piece b, int s) {
        if(b.r > a.r) {
            b.endingPiece = a;
        } else {
            a.endingPiece = b;
        }
        a.label = b.label = "S" + s;
    }

    private boolean isPieceEligible(Piece piece) {
        if(piece.r == 0 && piece.c == 0) return false;
        if(piece.r == board.length - 1 && piece.c == board[0].length - 1) return false;
        if(new Random().nextInt(100) < 96) return false;
        if(piece.endingPiece != null) return false;
        if(piece.backPointer != null) return false;
        return true;
    }

    public void move(int move) {
        int dupR = currentR;
        int dupC = currentC;
        boolean possible = true;
        for(int i = 0; i < move ; i++) {
           dupC++;
           if(dupC == board[0].length) {
               dupC = 0;
               dupR++;
               if(dupR == board.length) {
                   possible = false;
                   break;
               }
           }
        }
        if(possible) {
            Piece endingPiece = board[dupR][dupC].endingPiece;
            if(endingPiece != null) {
                currentR = endingPiece.r;
                currentC = endingPiece.c;
            } else {
                currentR = dupR;
                currentC = dupC;
            }
        }

    }

    public void print() {
        List<String> rows = new ArrayList<>();
        for(int i = 0 ; i < board.length ; i++) {
            String row = "";
            for(int j = 0 ; j < board[0].length ; j++) {
                if(i == currentR && j == currentC) {
                    row = "O\t" + row;
                    continue;
                }
                row = board[i][j].label + "\t" + row;
            }
            rows.add(row);
        }
        for(int i = rows.size() - 1 ; i >= 0 ; i--) {
            System.out.println(rows.get(i));
        }
    }

    public boolean isWin() {
        return currentC == board[0].length - 1 && currentR == board.length - 1;
    }
}
