package com.chess;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Tests: https://www.chessprogramming.org/Perft_Results
 */

@RunWith(Suite.class)
@Suite.SuiteClasses({TestPieces.class,
                     TestBoard.class,
                     TestStaleMate.class,
                     TestPlayer.class,
                     TestCheckmate.class,
                     TestMinMax.class,
                     TestCastling.class,
                     TestPawnStructure.class,
                     TestRookStructure.class,
                     TestFENParser.class,
                     TestEngine.class,
                     TestIterativeDeepening.class,
                     TestKingSafety.class
})
public class ChessTestSuite {
}
