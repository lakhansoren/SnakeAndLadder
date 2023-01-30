package board;

public class Piece {
    int r;
    int c;
    String label = "X";
    Piece endingPiece = null;
    Piece backPointer = null;
}
