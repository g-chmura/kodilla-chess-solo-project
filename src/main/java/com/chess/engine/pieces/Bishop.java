package com.chess.engine.pieces;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtils;
import com.chess.engine.board.Move;
import com.chess.engine.board.Move.MajorAttackMove;
import com.chess.engine.board.Move.MajorMove;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public final class Bishop extends Piece {

    private final static int[] CANDIDATE_MOVE_COORDINATES = {-9, -7, 7, 9};

    public Bishop(final Alliance alliance, final int piecePosition) {
         super(PieceType.BISHOP, alliance, piecePosition, true);
    }

    public Bishop(final Alliance alliance, final int piecePosition, final boolean isFirstMove) {
        super(PieceType.BISHOP, alliance, piecePosition, isFirstMove);
    }

    @Override
    public Collection<Move> calculateLegalMoves(final Board board) {
        final List<Move> legalMoves = new ArrayList<>();
        for (final int candidateOffset : CANDIDATE_MOVE_COORDINATES) {
            int possibleDestination = this.piecePosition;
            while (BoardUtils.isValidTileCoordinate(possibleDestination)) {
                if (isFirstColumnException(candidateOffset, possibleDestination) ||
                    isEighthColumnException(candidateOffset, possibleDestination)) {
                    break;
                }
                possibleDestination += candidateOffset;
                if (BoardUtils.isValidTileCoordinate(possibleDestination)) {
                    final Piece pieceAtDestination = board.getPiece(possibleDestination);
                    if (pieceAtDestination == null) {
                        legalMoves.add(new MajorMove(board, this, possibleDestination));
                    }
                    else {
                        final Alliance pieceAlliance = pieceAtDestination.getPieceAlliance();
                        if (this.pieceAlliance != pieceAlliance) {
                            legalMoves.add(new MajorAttackMove(board, this, possibleDestination,
                                    pieceAtDestination));
                        }
                        break;
                    }
                }
            }
        }
        return Collections.unmodifiableList(legalMoves);
    }

    @Override
    public int locationBonus() {
        return this.pieceAlliance.bishopBonus(this.piecePosition);
    }

    @Override
    public Bishop movePiece(final Move move) {
        return PieceUtils.INSTANCE.getMovedBishop(move.getMovedPiece().getPieceAlliance(), move.getDestinationCoordinate());
    }

    @Override
    public String toString() {
        return this.pieceType.toString();
    }

    private static boolean isFirstColumnException(final int currentCandidate, final int candidateDestinationCoordinate) {
        return (BoardUtils.INSTANCE.FIRST_COLUMN.get(candidateDestinationCoordinate) &&
                ((currentCandidate == -9) || (currentCandidate == 7)));
    }

    private static boolean isEighthColumnException(final int currentCandidate, final int candidateDestinationCoordinate) {
        return BoardUtils.INSTANCE.EIGHTH_COLUMN.get(candidateDestinationCoordinate) &&
                        ((currentCandidate == -7) || (currentCandidate == 9));
    }

}