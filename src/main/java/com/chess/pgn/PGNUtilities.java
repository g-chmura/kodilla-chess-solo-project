package com.chess.pgn;

import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtils;
import com.chess.engine.board.Move;
import com.chess.gui.Table.MoveLog;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.chess.engine.board.Move.MoveFactory;

public class PGNUtilities {

    private static final Pattern KING_SIDE_CASTLE = Pattern.compile("K-KR#?\\+?");
    private static final Pattern QUEEN_SIDE_CASTLE = Pattern.compile("K-QR#?\\+?");
    private static final Pattern PLAIN_PAWN_MOVE = Pattern.compile("^([a-h][0-8])(\\+)?(#)?$");
    private static final Pattern PAWN_ATTACK_MOVE = Pattern.compile("(^[a-h][0-8])(x)([a-h][0-8])(\\+)?(#)?$");
    private static final Pattern PLAIN_MAJOR_MOVE = Pattern.compile("^([B|N|R|Q|K])([a-h]|[1-8])?([a-h][0-8])(\\+)?(#)?$");
    private static final Pattern MAJOR_ATTACK_MOVE = Pattern.compile("^([B|N|R|Q|K])([a-h]|[1-8])?(x)([a-h][0-8])(\\+)?(#)?$");
    private static final Pattern PAWN_PROMOTION_MOVE = Pattern.compile("(.*?)=(.*?)");
    private static final Pattern ATTACK_PAWN_PROMOTION_MOVE = Pattern.compile("(.*?)x(.*?)=(.*?)");

    private PGNUtilities() {
        throw new RuntimeException("Not Instantiable!");
    }

    public static Move createMove(final Board board, final String pgnText) {

        final Matcher kingSideCastleMatcher = KING_SIDE_CASTLE.matcher(pgnText);
        final Matcher queenSideCastleMatcher = QUEEN_SIDE_CASTLE.matcher(pgnText);
        final Matcher plainPawnMatcher = PLAIN_PAWN_MOVE.matcher(pgnText);
        final Matcher attackPawnMatcher = PAWN_ATTACK_MOVE.matcher(pgnText);
        final Matcher pawnPromotionMatcher = PAWN_PROMOTION_MOVE.matcher(pgnText);
        final Matcher attackPawnPromotionMatcher = ATTACK_PAWN_PROMOTION_MOVE.matcher(pgnText);
        final Matcher plainMajorMatcher = PLAIN_MAJOR_MOVE.matcher(pgnText);
        final Matcher attackMajorMatcher = MAJOR_ATTACK_MOVE.matcher(pgnText);

        int currentCoordinate;
        int destinationCoordinate;

        if(kingSideCastleMatcher.matches()) {
            return extractCastleMove(board, "K-KR");
        } else if (queenSideCastleMatcher.matches()) {
            return extractCastleMove(board, "K-QR");
        } else if(plainPawnMatcher.matches()) {
            final String destinationSquare = plainPawnMatcher.group(1);
            destinationCoordinate = BoardUtils.INSTANCE.getCoordinateAtPosition(destinationSquare);
            currentCoordinate = drawCurrentCoordinate(board, "P", destinationSquare, "");
            return MoveFactory.createMove(board, currentCoordinate, destinationCoordinate);
        } else if(attackPawnMatcher.matches()) {
            final String destinationSquare = attackPawnMatcher.group(3);
            destinationCoordinate = BoardUtils.INSTANCE.getCoordinateAtPosition(destinationSquare);
            final String disambiguationFile = attackPawnMatcher.group(1) != null ? attackPawnMatcher.group(1) : "";
            currentCoordinate = drawCurrentCoordinate(board, "P", destinationSquare, disambiguationFile);
            return MoveFactory.createMove(board, currentCoordinate, destinationCoordinate);
        } else if (attackPawnPromotionMatcher.matches()) {
            final String destinationSquare = attackPawnPromotionMatcher.group(2);
            final String disambiguationFile = attackPawnPromotionMatcher.group(1) != null ? attackPawnPromotionMatcher.group(1) : "";
            destinationCoordinate = BoardUtils.INSTANCE.getCoordinateAtPosition(destinationSquare);
            currentCoordinate = drawCurrentCoordinate(board, "P", destinationSquare, disambiguationFile);
            return MoveFactory.createMove(board, currentCoordinate, destinationCoordinate);
        } else if(pawnPromotionMatcher.find()) {
            final String destinationSquare = pawnPromotionMatcher.group(1);
            destinationCoordinate = BoardUtils.INSTANCE.getCoordinateAtPosition(destinationSquare);
            currentCoordinate = drawCurrentCoordinate(board, "P", destinationSquare, "");
            return MoveFactory.createMove(board, currentCoordinate, destinationCoordinate);
        } else if (plainMajorMatcher.find()) {
            final String destinationSquare = plainMajorMatcher.group(3);
            destinationCoordinate = BoardUtils.INSTANCE.getCoordinateAtPosition(destinationSquare);
            final String disambiguationFile = plainMajorMatcher.group(2) != null ? plainMajorMatcher.group(2) : "";
            currentCoordinate = drawCurrentCoordinate(board, plainMajorMatcher.group(1), destinationSquare, disambiguationFile);
            return MoveFactory.createMove(board, currentCoordinate, destinationCoordinate);
        } else if(attackMajorMatcher.find()) {
            final String destinationSquare = attackMajorMatcher.group(4);
            destinationCoordinate = BoardUtils.INSTANCE.getCoordinateAtPosition(destinationSquare);
            final String disambiguationFile = attackMajorMatcher.group(2) != null ? attackMajorMatcher.group(2) : "";
            currentCoordinate = drawCurrentCoordinate(board, attackMajorMatcher.group(1), destinationSquare, disambiguationFile);
            return MoveFactory.createMove(board, currentCoordinate, destinationCoordinate);
        }

        return MoveFactory.getNullMove();

    }

    private static Move extractCastleMove(final Board board,
                                          final String castleMove) {
        for (final Move move : board.currentPlayer().getLegalMoves()) {
            if (move.isCastlingMove() && move.toString().equals(castleMove)) {
                return move;
            }
        }
        return MoveFactory.getNullMove();
    }

    private static int drawCurrentCoordinate(final Board board,
                                             final String movedPiece,
                                             final String destinationSquare,
                                             final String disambiguationFile) throws RuntimeException {
        final List<Move> currentCandidates = new ArrayList<>();
        final int destinationCoordinate =  BoardUtils.INSTANCE.getCoordinateAtPosition(destinationSquare);
        for (final Move move : board.currentPlayer().getLegalMoves()) {
            if (move.getDestinationCoordinate() == destinationCoordinate && move.getMovedPiece().toString().equals(movedPiece)) {
                currentCandidates.add(move);
            }
        }

        if(currentCandidates.size() == 0) {
            return -1;
        }

        return currentCandidates.size() == 1
                ? currentCandidates.iterator().next().getCurrentCoordinate()
                : extractFurther(currentCandidates, movedPiece, disambiguationFile);

    }

    private static int extractFurther(final List<Move> candidateMoves,
                                      final String movedPiece,
                                      final String disambiguationFile) {

        final List<Move> currentCandidates = new ArrayList<>();

        for(final Move move : candidateMoves) {
            if(move.getMovedPiece().getPieceType().toString().equals(movedPiece)) {
                currentCandidates.add(move);
            }
        }

        if(currentCandidates.size() == 1) {
            return currentCandidates.iterator().next().getCurrentCoordinate();
        }

        final List<Move> candidatesRefined = new ArrayList<>();

        for (final Move move : currentCandidates) {
            final String pos = BoardUtils.INSTANCE.getPositionAtCoordinate(move.getCurrentCoordinate());
            if (pos.contains(disambiguationFile)) {
                candidatesRefined.add(move);
            }
        }

        if(candidatesRefined.size() == 1) {
            return candidatesRefined.iterator().next().getCurrentCoordinate();
        }

        return -1;
    }


}
