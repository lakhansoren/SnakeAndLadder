package board;

import java.util.*;

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
        assert l + s < r * c - 1;
        Set<List<Integer>> set = new HashSet<>();
        while(l + s >= 0) {
            if(assignSnake(s)) {

                s--;
            }
            if(assignLadder(l)) {

                l--;
            }
        }
    }
    private boolean assignLadder(int l) {
        boolean found = false;
        for(int i = 0 ; i < board.length ; i++) {
            for(int j = 0 ; j < board[0].length ; j++) {
                if(i == 0 && j == 0) continue;
                if(i == board.length - 1 && j == board[0].length - 1) continue;
                if(isPieceEligible(board[i][j]) && !found) {
                    for(int ii = i + 1 ; ii < board.length ; ii++) {
                        for(int jj = j + 1 ; jj < board[0].length ; jj++) {
                            if(isPieceEligible(board[ii][jj])) {
                                found = true;
                                board[i][j].endingPiece = board[ii][jj];
                                board[ii][jj].backPointer = board[i][j];
                                board[i][j].label = board[ii][jj].label = "L" + l;
                                break;
                            }
                        }
                    }
                }
            }
        }
        return found;
    }

    private boolean isPieceEligible(Piece piece) {
        if(piece.r == 0 && piece.c == 0) return false;
        if(piece.r == board.length - 1 && piece.c == board[0].length - 1) return false;
        if(new Random().nextInt(100) > 70) return false;
        if(piece.endingPiece != null) return false;
        if(piece.backPointer != null) return false;
        return true;
    }

    private boolean assignSnake(int s) {
        boolean found = false;
        for(int i = board.length - 1 ; i >= 0 ; i--) {
            for(int j = board[0].length - 1 ; j >= 0 ; j--) {
                if(isPieceEligible(board[i][j]) && !found) {
                    for(int ii = i - 1 ; ii >= 0 ; ii--) {
                        for(int jj = j - 1 ; jj >= 0 ; jj--) {
                            if(isPieceEligible(board[ii][jj])) {
                                found = true;
                                board[i][j].endingPiece = board[ii][jj];
                                board[ii][jj].backPointer = board[i][j];
                                board[i][j].label = board[ii][jj].label = "S" + s;
                                break;
                            }
                        }
                    }
                }
            }
        }
        return found;
    }

    public void move(int move) {
        int dupR = currentR;
        int dupC = currentC;
        boolean possible = true;
        for(int i = 0; i < move ; i++) {
            for(dupC = currentC + 1 ; dupC <= board[0].length ; dupC++) {
                if(dupC == board[0].length) {
                    if(dupR >= board.length) {
                        possible = false;
                        break;
                    }
                    dupC = -1;
                    dupR++;
                }
            }
        }
        if(possible) {
            Piece endingPiece = board[dupR][dupC].endingPiece;
            if(endingPiece != null) {
                currentR = endingPiece.r;
                currentC = endingPiece.c;
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
