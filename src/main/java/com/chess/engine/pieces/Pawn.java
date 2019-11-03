package com.chess.engine.pieces;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtils;
import com.chess.engine.board.Move;
import com.chess.engine.board.Move.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public final class Pawn
        extends Piece {

    private final static int[] CANDIDATE_MOVE_COORDINATES = {8, 16, 7, 9};

    public Pawn(final Alliance allegiance,
                final int piecePosition) {
        super(PieceType.PAWN, allegiance, piecePosition, true);
    }

    public Pawn(final Alliance alliance,
                final int piecePosition,
                final boolean isFirstMove) {
        super(PieceType.PAWN, alliance, piecePosition, isFirstMove);
    }

    @Override
    public int locationBonus() {
        return this.pieceAlliance.pawnBonus(this.piecePosition);
    }

    @Override
    public Collection<Move> calculateLegalMoves(final Board board) {
        final List<Move> legalMoves = new ArrayList<>();
        for (final int candidateOffset : CANDIDATE_MOVE_COORDINATES) {
            int possibleDestination =
                    this.piecePosition + (this.pieceAlliance.getDirection() * candidateOffset);
            if (!BoardUtils.isValidTileCoordinate(possibleDestination)) {
                continue;
            }
            if (candidateOffset == 8 && board.getPiece(possibleDestination) == null) {
                if (this.pieceAlliance.isPawnPromotionSquare(possibleDestination)) {
                    legalMoves.add(new PawnPromotion(
                            new PawnMove(board, this, possibleDestination), PieceUtils.INSTANCE.getMovedQueen(this.pieceAlliance, possibleDestination)));
                    legalMoves.add(new PawnPromotion(
                            new PawnMove(board, this, possibleDestination), PieceUtils.INSTANCE.getMovedRook(this.pieceAlliance, possibleDestination)));
                    legalMoves.add(new PawnPromotion(
                            new PawnMove(board, this, possibleDestination), PieceUtils.INSTANCE.getMovedBishop(this.pieceAlliance, possibleDestination)));
                    legalMoves.add(new PawnPromotion(
                            new PawnMove(board, this, possibleDestination), PieceUtils.INSTANCE.getMovedKnight(this.pieceAlliance, possibleDestination)));
                }
                else {
                    legalMoves.add(new PawnMove(board, this, possibleDestination));
                }
            }
            else if (candidateOffset == 16 && this.isFirstMove() &&
                    ((BoardUtils.INSTANCE.SECOND_ROW.get(this.piecePosition) && this.pieceAlliance.isBlack()) ||
                     (BoardUtils.INSTANCE.SEVENTH_ROW.get(this.piecePosition) && this.pieceAlliance.isWhite()))) {
                final int behindCandidateDestinationCoordinate =
                        this.piecePosition + (this.pieceAlliance.getDirection() * 8);
                if (board.getPiece(possibleDestination) == null &&
                    board.getPiece(behindCandidateDestinationCoordinate) == null) {
                    legalMoves.add(new PawnJump(board, this, possibleDestination));
                }
            }
            else if (candidateOffset == 7 &&
                    !((BoardUtils.INSTANCE.EIGHTH_COLUMN.get(this.piecePosition) && this.pieceAlliance.isWhite()) ||
                      (BoardUtils.INSTANCE.FIRST_COLUMN.get(this.piecePosition) && this.pieceAlliance.isBlack()))) {
                if(board.getPiece(possibleDestination) != null) {
                    final Piece pieceOnCandidate = board.getPiece(possibleDestination);
                    if (this.pieceAlliance != pieceOnCandidate.getPieceAlliance()) {
                        if (this.pieceAlliance.isPawnPromotionSquare(possibleDestination)) {
                            legalMoves.add(new PawnPromotion(
                                    new PawnAttackMove(board, this, possibleDestination, pieceOnCandidate), PieceUtils.INSTANCE.getMovedQueen(this.pieceAlliance, possibleDestination)));
                            legalMoves.add(new PawnPromotion(
                                    new PawnAttackMove(board, this, possibleDestination, pieceOnCandidate), PieceUtils.INSTANCE.getMovedRook(this.pieceAlliance, possibleDestination)));
                            legalMoves.add(new PawnPromotion(
                                    new PawnAttackMove(board, this, possibleDestination, pieceOnCandidate), PieceUtils.INSTANCE.getMovedBishop(this.pieceAlliance, possibleDestination)));
                            legalMoves.add(new PawnPromotion(
                                    new PawnAttackMove(board, this, possibleDestination, pieceOnCandidate), PieceUtils.INSTANCE.getMovedKnight(this.pieceAlliance, possibleDestination)));
                        }
                        else {
                            legalMoves.add(
                                    new PawnAttackMove(board, this, possibleDestination, pieceOnCandidate));
                        }
                    }
                } else if (board.getEnPassantPawn() != null && board.getEnPassantPawn().getPiecePosition() ==
                           (this.piecePosition + (this.pieceAlliance.getOppositeDirection()))) {
                    final Piece pieceOnCandidate = board.getEnPassantPawn();
                    if (this.pieceAlliance != pieceOnCandidate.getPieceAlliance()) {
                        legalMoves.add(
                                new PawnEnPassantAttack(board, this, possibleDestination, pieceOnCandidate));

                    }
                }
            }
            else if (candidateOffset == 9 &&
                    !((BoardUtils.INSTANCE.FIRST_COLUMN.get(this.piecePosition) && this.pieceAlliance.isWhite()) ||
                      (BoardUtils.INSTANCE.EIGHTH_COLUMN.get(this.piecePosition) && this.pieceAlliance.isBlack()))) {
                if(board.getPiece(possibleDestination) != null) {
                    if (this.pieceAlliance !=
                            board.getPiece(possibleDestination).getPieceAlliance()) {
                        if (this.pieceAlliance.isPawnPromotionSquare(possibleDestination)) {
                            legalMoves.add(new PawnPromotion(
                                    new PawnAttackMove(board, this, possibleDestination,
                                            board.getPiece(possibleDestination)), PieceUtils.INSTANCE.getMovedQueen(this.pieceAlliance, possibleDestination)));
                            legalMoves.add(new PawnPromotion(
                                    new PawnAttackMove(board, this, possibleDestination,
                                            board.getPiece(possibleDestination)), PieceUtils.INSTANCE.getMovedRook(this.pieceAlliance, possibleDestination)));
                            legalMoves.add(new PawnPromotion(
                                    new PawnAttackMove(board, this, possibleDestination,
                                            board.getPiece(possibleDestination)), PieceUtils.INSTANCE.getMovedBishop(this.pieceAlliance, possibleDestination)));
                            legalMoves.add(new PawnPromotion(
                                    new PawnAttackMove(board, this, possibleDestination,
                                            board.getPiece(possibleDestination)), PieceUtils.INSTANCE.getMovedKnight(this.pieceAlliance, possibleDestination)));
                        }
                        else {
                            legalMoves.add(
                                    new PawnAttackMove(board, this, possibleDestination,
                                            board.getPiece(possibleDestination)));
                        }
                    }
                } else if (board.getEnPassantPawn() != null && board.getEnPassantPawn().getPiecePosition() ==
                        (this.piecePosition - (this.pieceAlliance.getOppositeDirection()))) {
                    final Piece pieceOnCandidate = board.getEnPassantPawn();
                    if (this.pieceAlliance != pieceOnCandidate.getPieceAlliance()) {
                        legalMoves.add(
                                new PawnEnPassantAttack(board, this, possibleDestination, pieceOnCandidate));

                    }
                }
            }
        }
        return Collections.unmodifiableList(legalMoves);
    }

    @Override
    public String toString() {
        return this.pieceType.toString();
    }

    @Override
    public Pawn movePiece(final Move move) {
        return PieceUtils.INSTANCE.getMovedPawn(move.getMovedPiece().getPieceAlliance(), move.getDestinationCoordinate());
    }

}