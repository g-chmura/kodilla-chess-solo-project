package com.chess.engine.player.ai;

import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtils;
import com.chess.engine.board.Move;
import com.chess.engine.board.MoveTransition;
import com.chess.engine.player.Player;
import com.google.common.collect.ComparisonChain;
import com.google.common.collect.Ordering;

import java.util.Collection;
import java.util.Comparator;
import java.util.Observable;

import static com.chess.engine.board.BoardUtils.mvvlva;
import static com.chess.engine.board.Move.MoveFactory;

public class StockAlphaBeta extends Observable implements MoveStrategy {

    private final BoardEvaluator evaluator;
    private final int searchDifficulty;
    private long boardsEvaluated;
    private int quiescenceCount;
    private static final int MAX_QUIESCENCE = 5000*10;

    private enum MoveSorter {

        STANDARD {
            @Override
            Collection<Move> sort(final Collection<Move> moves) {
                return Ordering.from((Comparator<Move>) (move1, move2) -> ComparisonChain.start()
                        .compareTrueFirst(move1.isCastlingMove(), move2.isCastlingMove())
                        .compare(mvvlva(move2), mvvlva(move1))
                        .result()).immutableSortedCopy(moves);
            }
        },
        EXPENSIVE {
            @Override
            Collection<Move> sort(final Collection<Move> moves) {
                return Ordering.from((Comparator<Move>) (move1, move2) -> ComparisonChain.start()
                        .compareTrueFirst(BoardUtils.kingThreat(move1), BoardUtils.kingThreat(move2))
                        .compareTrueFirst(move1.isCastlingMove(), move2.isCastlingMove())
                        .compare(mvvlva(move2), mvvlva(move1))
                        .result()).immutableSortedCopy(moves);
            }
        };

        abstract  Collection<Move> sort(Collection<Move> moves);
    }


    public StockAlphaBeta(final int searchDifficulty) {
        this.evaluator = StandardBoardEvaluator.get();
        this.searchDifficulty = searchDifficulty;
        this.boardsEvaluated = 0;
        this.quiescenceCount = 0;
    }

    @Override
    public String toString() {
        return "AI difficulty";
    }

    @Override
    public long getNumberOfBoardsEvaluated() {
        return this.boardsEvaluated;
    }

    @Override
    public Move execute(final Board board) {
        final long startTime = System.currentTimeMillis();
        final Player currentPlayer = board.currentPlayer();
        Move bestMove = MoveFactory.getNullMove();
        int highestSeenValue = Integer.MIN_VALUE;
        int lowestSeenValue = Integer.MAX_VALUE;
        int currentValue;
        System.out.println(board.currentPlayer() + " THINKING with difficulty = " + this.searchDifficulty);
        int moveCounter = 1;
        int numMoves = board.currentPlayer().getLegalMoves().size();
        Collection<Move> sortedMoves = MoveSorter.EXPENSIVE.sort((board.currentPlayer().getLegalMoves()));
        for (final Move move : sortedMoves) {
            final MoveTransition moveTransition = board.currentPlayer().makeMove(move);
            this.quiescenceCount = 0;
            final String s;
            if (moveTransition.getMoveStatus().isDone()) {
                final long candidateMoveStartTime = System.nanoTime();
                currentValue = currentPlayer.getAlliance().isWhite() ?
                        min(moveTransition.getToBoard(), this.searchDifficulty - 1, highestSeenValue, lowestSeenValue) :
                        max(moveTransition.getToBoard(), this.searchDifficulty - 1, highestSeenValue, lowestSeenValue);
                if (currentPlayer.getAlliance().isWhite() && currentValue > highestSeenValue) {
                    highestSeenValue = currentValue;
                    bestMove = move;
                    if(moveTransition.getToBoard().blackPlayer().isInCheckMate()) {
                        break;
                    }
                }
                else if (currentPlayer.getAlliance().isBlack() && currentValue < lowestSeenValue) {
                    lowestSeenValue = currentValue;
                    bestMove = move;
                    if(moveTransition.getToBoard().whitePlayer().isInCheckMate()) {
                        break;
                    }
                }

                final String quiescenceInfo = " " + score(currentPlayer, highestSeenValue, lowestSeenValue) + " quiescence counter: " +this.quiescenceCount;
                s = "\t" + toString() + "(" +this.searchDifficulty + "), move counter: (" +moveCounter+ "/" +numMoves+ ") " + move + ", best move:  " + bestMove

                        + quiescenceInfo + ", t: " +calculateTimeTaken(candidateMoveStartTime, System.nanoTime());
            } else {
                s = "\t" + toString() + ", move counter: (" +moveCounter+ "/" +numMoves+ ") " + move + " is illegal! best move: " +bestMove;
            }
            System.out.println(s);
            setChanged();
            notifyObservers(s);
            moveCounter++;
        }

        long executionTime = System.currentTimeMillis() - startTime;
        final String result = board.currentPlayer() + " SELECTS " +bestMove+ " [#boards evaluated = " +this.boardsEvaluated+
                " time taken = " + executionTime /1000+ " rate = " +(1000 * ((double)this.boardsEvaluated/ executionTime));
        System.out.printf("%s SELECTS %s [#boards evaluated = %d, time taken = %d ms, rate = %.1f\n", board.currentPlayer(),
                bestMove, this.boardsEvaluated, executionTime, (1000 * ((double)this.boardsEvaluated/ executionTime)));
        setChanged();
        notifyObservers(result);
        return bestMove;
    }

    private static String score(final Player currentPlayer,
                                final int highestSeenValue,
                                final int lowestSeenValue) {

        if(currentPlayer.getAlliance().isWhite()) {
            return "[score: " +highestSeenValue + "]";
        } else if(currentPlayer.getAlliance().isBlack()) {
            return "[score: " +lowestSeenValue+ "]";
        }
        throw new RuntimeException("Bad!");
    }

    private int max(final Board board,
                    final int difficulty,
                    final int highest,
                    final int lowest) {
        if (difficulty == 0 || BoardUtils.isEndGame(board)) {
            this.boardsEvaluated++;
            return this.evaluator.evaluate(board, difficulty);
        }
        int currentHighest = highest;
        for (final Move move : MoveSorter.STANDARD.sort((board.currentPlayer().getLegalMoves()))) {
            final MoveTransition moveTransition = board.currentPlayer().makeMove(move);
            if (moveTransition.getMoveStatus().isDone()) {
                currentHighest = Math.max(currentHighest, min(moveTransition.getToBoard(),
                        calculateQuiescenceDifficulty(moveTransition, difficulty), currentHighest, lowest));
                if (currentHighest >= lowest) {
                    return lowest;
                }
            }
        }
        return currentHighest;
    }

    private int min(final Board board,
                    final int difficulty,
                    final int highest,
                    final int lowest) {
        if (difficulty == 0 || BoardUtils.isEndGame(board)) {
            this.boardsEvaluated++;
            return this.evaluator.evaluate(board, difficulty);
        }
        int currentLowest = lowest;
        for (final Move move : MoveSorter.STANDARD.sort((board.currentPlayer().getLegalMoves()))) {
            final MoveTransition moveTransition = board.currentPlayer().makeMove(move);
            if (moveTransition.getMoveStatus().isDone()) {
                currentLowest = Math.min(currentLowest, max(moveTransition.getToBoard(),
                        calculateQuiescenceDifficulty(moveTransition, difficulty), highest, currentLowest));
                if (currentLowest <= highest) {
                    return highest;
                }
            }
        }
        return currentLowest;
    }

    private int calculateQuiescenceDifficulty(final MoveTransition moveTransition,
                                              final int difficulty) {
        if(difficulty == 1 && this.quiescenceCount < MAX_QUIESCENCE) {
            int activityMeasure = 0;
            if (moveTransition.getToBoard().currentPlayer().isInCheck()) {
                activityMeasure += 1;
            }
            for(final Move move: BoardUtils.lastNMoves(moveTransition.getToBoard(), 2)) {
                if(move.isAttack()) {
                    activityMeasure += 1;
                }
            }
            if(activityMeasure >= 2) {
                this.quiescenceCount++;
                return 1;
            }
        }
        return difficulty - 1;
    }

    private static String calculateTimeTaken(final long start, final long end) {
        final long timeTaken = (end - start) / 1000000;
        return timeTaken + " ms";
    }

}