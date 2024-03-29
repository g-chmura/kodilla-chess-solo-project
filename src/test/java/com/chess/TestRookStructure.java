package com.chess;

import static junit.framework.Assert.assertEquals;

import com.chess.engine.pieces.Rook;
import com.chess.engine.player.ai.RookStructureAnalyzer;
import org.junit.Test;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.Board.Builder;
import com.chess.engine.pieces.King;

public class TestRookStructure {

    @Test
    public void test1() {
        final Board board = Board.createStandardBoard();
        assertEquals(RookStructureAnalyzer.get().rookStructureScore(board, board.whitePlayer()), 0);
        assertEquals(RookStructureAnalyzer.get().rookStructureScore(board, board.whitePlayer()), 0);
    }

    @Test
    public void test2() {
        final Builder builder = new Builder();
        // Black
        builder.setPiece(new Rook(Alliance.BLACK, 0));
        builder.setPiece(new King(Alliance.BLACK, 4, false, false));
        // White
        builder.setPiece(new Rook(Alliance.WHITE, 63));
        builder.setPiece(new King(Alliance.WHITE, 60, false, false));
        builder.setMoveMaker(Alliance.WHITE);
        // Set current player
        final Board board = builder.build();
        assertEquals(RookStructureAnalyzer.get().rookStructureScore(board, board.whitePlayer()), 25);
        assertEquals(RookStructureAnalyzer.get().rookStructureScore(board, board.whitePlayer()), 25);
    }


}