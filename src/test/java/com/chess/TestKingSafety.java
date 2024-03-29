package com.chess;

import com.chess.engine.player.ai.KingSafetyAnalyzer;
import org.junit.Test;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.Board.Builder;
import com.chess.engine.pieces.King;
import com.chess.engine.pieces.Pawn;

import static org.junit.Assert.assertEquals;

public class TestKingSafety {

    @Test
    public void test1() {
        final Builder builder = new Builder();
        // Black
        builder.setPiece(new King(Alliance.BLACK, 4, false, false));
        builder.setPiece(new Pawn(Alliance.BLACK, 12));
        // White
        builder.setPiece(new Pawn(Alliance.WHITE, 52));
        builder.setPiece(new King(Alliance.WHITE, 60, false, false));
        builder.setMoveMaker(Alliance.WHITE);
        // Set current player
        final Board board = builder.build();

        assertEquals(KingSafetyAnalyzer.get().calculateKingTropism(board.whitePlayer()).tropismScore(), 40);
    }

}