package com.chess.pgn;

import java.sql.*;

import com.chess.engine.board.Board;
import com.chess.engine.board.Move;
import com.chess.engine.player.Player;

public class GamePersistence implements PGNPersistence {

    private final Connection dbConnection;

    private static GamePersistence INSTANCE = new GamePersistence();
    private static final String NEXT_BEST_MOVE_QUERY =
            "SELECT SUBSTR(g1.moves, LENGTH('%s') + %d, INSTR(SUBSTR(g1.moves, LENGTH('%s') + %d, LENGTH(g1.moves)), ',') - 1), " +
                    "COUNT(*) FROM game g1 WHERE g1.moves LIKE '%s%%' AND (outcome = '%s') GROUP BY substr(g1.moves, LENGTH('%s') + %d, " +
                    "INSTR(substr(g1.moves, LENGTH('%s') + %d, LENGTH(g1.moves)), ',') - 1) ORDER BY 2 DESC";


    private GamePersistence() {
        this.dbConnection = (Connection) INSTANCE;

    }


    public static GamePersistence get() {
        return INSTANCE;
    }

    @Override
    public Move getNextBestMove(final Board board,
                                final Player player,
                                final String gameText) {
        return queryBestMove(board, player, gameText);
    }

    private Move queryBestMove(final Board board,
                               final Player player,
                               final String gameText) {

        String bestMove = "";
        String count = "0";
        try {
            final int offSet = gameText.isEmpty() ? 1 : 3;
            final String sqlString = String.format(NEXT_BEST_MOVE_QUERY, gameText, offSet, gameText, offSet, gameText,
                    player.getAlliance().name(), gameText, offSet, gameText, offSet);
            System.out.println(sqlString);
            final Statement gameStatement = this.dbConnection.createStatement();
            gameStatement.execute(sqlString);
            final ResultSet rs = gameStatement.getResultSet();
            if(rs.next()) {
                bestMove = rs.getString(1);
                count = rs.getString(2);
            }
            gameStatement.close();
        }
        catch (final SQLException e) {
            e.printStackTrace();
        }
        return PGNUtilities.createMove(board, bestMove);
    }
}