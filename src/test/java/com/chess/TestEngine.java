package com.chess;

import com.chess.engine.board.Board;
import com.chess.engine.player.ai.MinMax;
import com.chess.engine.player.ai.MoveStrategy;
import com.chess.pgn.Utilities;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;


public class TestEngine {

    @Test
    public void testPosition2KiwipeteDepth1() {
        final Board board = Utilities.createGameFromFEN("r3k2r/p1ppqpb1/bn2pnp1/3PN3/1p2P3/2N2Q1p/PPPBBPPP/R3K2R w KQkq - 0 1");
        final MoveStrategy minMax = new MinMax(1);
        minMax.execute(board);
        assertEquals(minMax.getNumberOfBoardsEvaluated(), 48L);
    }

    @Test
    public void testPosition2KiwipeteDepth2() {
        final Board board = Utilities.createGameFromFEN("r3k2r/p1ppqpb1/bn2pnp1/3PN3/1p2P3/2N2Q1p/PPPBBPPP/R3K2R w KQkq - 0 1");
        final MoveStrategy minMax = new MinMax(2);
        minMax.execute(board);
        assertEquals(minMax.getNumberOfBoardsEvaluated(), 2039L);
    }

    @Test
    public void testPosition2KiwipeteDepth3() {
        final Board board = Utilities.createGameFromFEN("r3k2r/p1ppqpb1/bn2pnp1/3PN3/1p2P3/2N2Q1p/PPPBBPPP/R3K2R w KQkq - 0 1");
        final MoveStrategy minMax = new MinMax(3);
        minMax.execute(board);
        assertEquals(minMax.getNumberOfBoardsEvaluated(), 97862L);
    }

    @Test
    public void testPosition2KiwipeteDepth4() {
        final Board board = Utilities.createGameFromFEN("r3k2r/p1ppqpb1/bn2pnp1/3PN3/1p2P3/2N2Q1p/PPPBBPPP/R3K2R w KQkq - 0 1");
        final MoveStrategy minMax = new MinMax(4);
        minMax.execute(board);
        assertEquals(minMax.getNumberOfBoardsEvaluated(), 4085603L);
    }

    //Too much time
    //@Test
    //public void testPosition2KiwipeteDepth5() {
    //    final Board board = Utilities.createGameFromFEN("r3k2r/p1ppqpb1/bn2pnp1/3PN3/1p2P3/2N2Q1p/PPPBBPPP/R3K2R w KQkq - 0 1");
    //    final MoveStrategy minMax = new MinMax(5);
    //    minMax.execute(board);
    //    assertEquals(minMax.getNumBoardsEvaluated(), 193690690L);
    //}

    //Too much time
    // @Test
    // public void testPosition2KiwipeteDepth6() {
    // final Board board = Utilities.createGameFromFEN("r3k2r/p1ppqpb1/bn2pnp1/3PN3/1p2P3/2N2Q1p/PPPBBPPP/R3K2R w KQkq - 0 1");
    // final MoveStrategy minMax = new MinMax(6);
    // minMax.execute(board);
    // assertEquals(minMax.getNumBoardsEvaluated(), 8031647685L);
    // }

    @Test
    public void testPosition3Depth1() {
        final Board board = Utilities.createGameFromFEN("8/2p5/3p4/KP5r/1R3p1k/8/4P1P1/8 w - -");
        final MoveStrategy minMax = new MinMax(1);
        minMax.execute(board);
        assertEquals(minMax.getNumberOfBoardsEvaluated(), 14L);
    }

    @Test
    public void testPosition3Depth2() {
        final Board board = Utilities.createGameFromFEN("8/2p5/3p4/KP5r/1R3p1k/8/4P1P1/8 w - -");
        final MoveStrategy minMax = new MinMax(2);
        minMax.execute(board);
        assertEquals(minMax.getNumberOfBoardsEvaluated(), 191L);
    }

    @Test
    public void testPosition3Depth3() {
        final Board board = Utilities.createGameFromFEN("8/2p5/3p4/KP5r/1R3p1k/8/4P1P1/8 w - -");
        final MoveStrategy minMax = new MinMax(3);
        minMax.execute(board);
        assertEquals(minMax.getNumberOfBoardsEvaluated(), 2812L);
    }

    @Test
    public void testPosition3Depth4() {
        final Board board = Utilities.createGameFromFEN("8/2p5/3p4/KP5r/1R3p1k/8/4P1P1/8 w - -");
        final MoveStrategy minMax = new MinMax(4);
        minMax.execute(board);
        assertEquals(minMax.getNumberOfBoardsEvaluated(), 43238L);
    }

    @Test
    public void testPosition3Depth5() {
        final Board board = Utilities.createGameFromFEN("8/2p5/3p4/KP5r/1R3p1k/8/4P1P1/8 w - -");
        final MoveStrategy minMax = new MinMax(5);
        minMax.execute(board);
        assertEquals(minMax.getNumberOfBoardsEvaluated(), 674624L);
    }
    //Too much time
    // @Test
    // public void testPosition3Depth6() {
    // final Board board = Utilities.createGameFromFEN("8/2p5/3p4/KP5r/1R3p1k/8/4P1P1/8 w - -");
    // final MoveStrategy minMax = new MinMax(6);
    // minMax.execute(board);
    // assertEquals(minMax.getNumBoardsEvaluated(), 11030083L);
    // }

    @Test
    public void testPosition4Depth1() {
        final Board board = Utilities.createGameFromFEN("r3k2r/Pppp1ppp/1b3nbN/nP6/BBP1P3/q4N2/Pp1P2PP/R2Q1RK1 w kq - 0 1");
        final MoveStrategy minMax = new MinMax(1);
        minMax.execute(board);
        assertEquals(minMax.getNumberOfBoardsEvaluated(), 6L);
    }

    @Test
    public void testPosition4Depth2() {
        final Board board = Utilities.createGameFromFEN("r3k2r/Pppp1ppp/1b3nbN/nP6/BBP1P3/q4N2/Pp1P2PP/R2Q1RK1 w kq - 0 1");
        final MoveStrategy minMax = new MinMax(2);
        minMax.execute(board);
        assertEquals(minMax.getNumberOfBoardsEvaluated(), 264L);
    }

    @Test
    public void testPosition4Depth3() {
        final Board board = Utilities.createGameFromFEN("r3k2r/Pppp1ppp/1b3nbN/nP6/BBP1P3/q4N2/Pp1P2PP/R2Q1RK1 w kq - 0 1");
        final MoveStrategy minMax = new MinMax(3);
        minMax.execute(board);
        assertEquals(minMax.getNumberOfBoardsEvaluated(), 9467L);
    }

    @Test
    public void testPosition4Depth4() {
        final Board board = Utilities.createGameFromFEN("r3k2r/Pppp1ppp/1b3nbN/nP6/BBP1P3/q4N2/Pp1P2PP/R2Q1RK1 w kq - 0 1");
        final MoveStrategy minMax = new MinMax(4);
        minMax.execute(board);
        assertEquals(minMax.getNumberOfBoardsEvaluated(), 422333L);
    }

    @Test
    public void testPosition4Depth5() {
        final Board board = Utilities.createGameFromFEN("r3k2r/Pppp1ppp/1b3nbN/nP6/BBP1P3/q4N2/Pp1P2PP/R2Q1RK1 w kq - 0 1");
        final MoveStrategy minMax = new MinMax(5);
        minMax.execute(board);
        assertEquals(minMax.getNumberOfBoardsEvaluated(), 15833292L);
    }

    //Too much time
    // @Test
    // public void testPosition4Depth6() {
    // final Board board = Utilities.createGameFromFEN("r3k2r/Pppp1ppp/1b3nbN/nP6/BBP1P3/q4N2/Pp1P2PP/R2Q1RK1 w kq - 0 1");
    // final MoveStrategy minMax = new MinMax(5);
    // minMax.execute(board);
    // assertEquals(minMax.getNumBoardsEvaluated(), 706045033L);
    // }

    @Test
    public void testPosition5Depth1() {
        final Board board = Utilities.createGameFromFEN("rnbq1k1r/pp1Pbppp/2p5/8/2B5/8/PPP1NnPP/RNBQK2R w KQ - 1 8");
        final MoveStrategy minMax = new MinMax(1);
        minMax.execute(board);
        assertEquals(minMax.getNumberOfBoardsEvaluated(), 44L);
    }

    @Test
    public void testPosition5Depth2() {
        final Board board = Utilities.createGameFromFEN("rnbq1k1r/pp1Pbppp/2p5/8/2B5/8/PPP1NnPP/RNBQK2R w KQ - 1 8");
        final MoveStrategy minMax = new MinMax(2);
        minMax.execute(board);
        assertEquals(minMax.getNumberOfBoardsEvaluated(), 1486L);
    }

    @Test
    public void testPosition5Depth3() {
        final Board board = Utilities.createGameFromFEN("rnbq1k1r/pp1Pbppp/2p5/8/2B5/8/PPP1NnPP/RNBQK2R w KQ - 1 8");
        final MoveStrategy minMax = new MinMax(3);
        minMax.execute(board);
        assertEquals(minMax.getNumberOfBoardsEvaluated(), 62379L);
    }

    @Test
    public void testPosition5Depth4() {
        final Board board = Utilities.createGameFromFEN("rnbq1k1r/pp1Pbppp/2p5/8/2B5/8/PPP1NnPP/RNBQK2R w KQ - 1 8");
        final MoveStrategy minMax = new MinMax(4);
        minMax.execute(board);
        assertEquals(minMax.getNumberOfBoardsEvaluated(), 2103487L);
    }
    //This test takes too much time to get complete
    // @Test
    // public void testPosition5Depth5() {
    // final Board board = Utilities.createGameFromFEN("rnbq1k1r/pp1Pbppp/2p5/8/2B5/8/PPP1NnPP/RNBQK2R w KQ - 1 8");
    // final MoveStrategy minMax = new MinMax(5);
    // minMax.execute(board);
    // assertEquals(minMax.getNumBoardsEvaluated(), 89941194L);
    // }

    @Test
    public void testPosition6Depth1() {
        final Board board = Utilities.createGameFromFEN("r4rk1/1pp1qppp/p1np1n2/2b1p1B1/2B1P1b1/P1NP1N2/1PP1QPPP/R4RK1 w - - 0 10\n");
        final MoveStrategy minMax = new MinMax(1);
        minMax.execute(board);
        assertEquals(minMax.getNumberOfBoardsEvaluated(), 46L);
    }

    @Test
    public void testPosition6Depth2() {
        final Board board = Utilities.createGameFromFEN("r4rk1/1pp1qppp/p1np1n2/2b1p1B1/2B1P1b1/P1NP1N2/1PP1QPPP/R4RK1 w - - 0 10\n");
        final MoveStrategy minMax = new MinMax(2);
        minMax.execute(board);
        assertEquals(minMax.getNumberOfBoardsEvaluated(), 2079L);
    }

    @Test
    public void testPosition6Depth3() {
        final Board board = Utilities.createGameFromFEN("r4rk1/1pp1qppp/p1np1n2/2b1p1B1/2B1P1b1/P1NP1N2/1PP1QPPP/R4RK1 w - - 0 10\n");
        final MoveStrategy minMax = new MinMax(3);
        minMax.execute(board);
        assertEquals(minMax.getNumberOfBoardsEvaluated(), 89890L);
    }

    @Test
    public void testPosition6Depth4() {
        final Board board = Utilities.createGameFromFEN("r4rk1/1pp1qppp/p1np1n2/2b1p1B1/2B1P1b1/P1NP1N2/1PP1QPPP/R4RK1 w - - 0 10\n");
        final MoveStrategy minMax = new MinMax(4);
        minMax.execute(board);
        assertEquals(minMax.getNumberOfBoardsEvaluated(), 3894594L);
    }
//This test takes too much time to get complete
    //@Test
    // public void testPosition6Depth5() {
    // final Board board = Utilities.createGameFromFEN("r4rk1/1pp1qppp/p1np1n2/2b1p1B1/2B1P1b1/P1NP1N2/1PP1QPPP/R4RK1 w - - 0 10\n");
    // final MoveStrategy minMax = new MinMax(5);
    // minMax.execute(board);
    // assertEquals(minMax.getNumBoardsEvaluated(), 164075551L);
    // }

//This test takes too much time to get complete
//    @Test
//    public void testPosition6Depth6() {
//        final Board board = Utilities.createGameFromFEN("r4rk1/1pp1qppp/p1np1n2/2b1p1B1/2B1P1b1/P1NP1N2/1PP1QPPP/R4RK1 w - - 0 10\n");
//        final MoveStrategy minMax = new MinMax(6);
//        minMax.execute(board);
//        assertEquals(minMax.getNumBoardsEvaluated(), 6923051137L);
//        }
}
