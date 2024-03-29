package com.chess.engine.player.ai;

import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtils;
import com.chess.engine.board.Move;
import com.chess.engine.board.MoveTransition;
import com.google.common.collect.ComparisonChain;
import com.google.common.primitives.Ints;

import java.util.*;

import static com.chess.engine.board.Move.*;
import static com.google.common.collect.Ordering.from;

public class IterativeDeepening extends Observable implements MoveStrategy {

    private final BoardEvaluator evaluator;
    private final int searchDifficulty;
    private final MoveSorter moveSorter;
    private long boardsEvaluated;
    private int cutOffsProduced;

    private enum MoveSorter {

        SORT {
            @Override
            Collection<Move> sort(final Collection<Move> moves) {
                return from(SMART_SORT).immutableSortedCopy(moves);
            }
        };

        public static Comparator<Move> SMART_SORT = (move1, move2) -> ComparisonChain.start()
                .compareTrueFirst(BoardUtils.isThreatenedBoardImmediate(move1.getBoard()), BoardUtils.isThreatenedBoardImmediate(move2.getBoard()))
                .compareTrueFirst(move1.isAttack(), move2.isAttack())
                .compareTrueFirst(move1.isCastlingMove(), move2.isCastlingMove())
                .compare(move2.getMovedPiece().getPieceValue(), move1.getMovedPiece().getPieceValue())
                .result();

        abstract Collection<Move> sort(Collection<Move> moves);
    }

    public IterativeDeepening(final int searchDifficulty) {
        this.evaluator = StandardBoardEvaluator.get();
        this.searchDifficulty = searchDifficulty;
        this.moveSorter = MoveSorter.SORT;
        this.boardsEvaluated = 0;
        this.cutOffsProduced = 0;
    }

    @Override
    public String toString() {
        return "ID";
    }

    @Override
    public long getNumberOfBoardsEvaluated() {
        return this.boardsEvaluated;
    }

    @Override
    public Move execute(final Board board) {

        final long startTime = System.currentTimeMillis();
        System.out.println(board.currentPlayer() + " THINKING with difficulty level = " + this.searchDifficulty);

        MoveOrderingBuilder builder = new MoveOrderingBuilder();
        builder.setOrder(board.currentPlayer().getAlliance().isWhite() ? Ordering.DESC : Ordering.ASC);
        for(final Move move : board.currentPlayer().getLegalMoves()) {
            builder.addMoveOrderingRecord(move, 0);
        }

        Move bestMove = MoveFactory.getNullMove();
        int currentDepth = 1;


        while (currentDepth <= this.searchDifficulty) {
            final long subTimeStart = System.currentTimeMillis();
            int highestSeenValue = Integer.MIN_VALUE;
            int lowestSeenValue = Integer.MAX_VALUE;
            int currentValue;
            final List<MoveScoreRecord> records = builder.build();
            builder = new MoveOrderingBuilder();
            builder.setOrder(board.currentPlayer().getAlliance().isWhite() ? Ordering.DESC : Ordering.ASC);
            for (final MoveScoreRecord record : records) {
                final Move move = record.getMove();
                final MoveTransition moveTransition = board.currentPlayer().makeMove(move);
                if (moveTransition.getMoveStatus().isDone()) {
                    currentValue = board.currentPlayer().getAlliance().isWhite() ?
                            min(moveTransition.getToBoard(), currentDepth - 1, highestSeenValue, lowestSeenValue) :
                            max(moveTransition.getToBoard(), currentDepth - 1, highestSeenValue, lowestSeenValue);
                    builder.addMoveOrderingRecord(move, currentValue);
                    if (board.currentPlayer().getAlliance().isWhite() && currentValue > highestSeenValue) {
                        highestSeenValue = currentValue;
                        bestMove = move;
                    } else if (board.currentPlayer().getAlliance().isBlack() && currentValue < lowestSeenValue) {
                        lowestSeenValue = currentValue;
                        bestMove = move;
                    }
                }
            }
            final long subTime = System.currentTimeMillis()- subTimeStart;
            System.out.println("\t" +toString()+ " bestMove = " +bestMove+ " Depth = " +currentDepth+ " took " +(subTime) + " ms, ordered moves : " +records);
            setChanged();
            notifyObservers(bestMove);
            currentDepth++;
        }
        long executionTime = System.currentTimeMillis() - startTime;
        System.out.printf("%s SELECTS %s [#boards evaluated = %d, time taken = %d ms, eval rate = %.1f cutoffCount = %d prune percent = %.2f\n", board.currentPlayer(),
                bestMove, this.boardsEvaluated, executionTime, (1000 * ((double)this.boardsEvaluated/ executionTime)), this.cutOffsProduced, 100 * ((double)this.cutOffsProduced/this.boardsEvaluated));
        return bestMove;
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
        for (final Move move : this.moveSorter.sort((board.currentPlayer().getLegalMoves()))) {
            final MoveTransition moveTransition = board.currentPlayer().makeMove(move);
            if (moveTransition.getMoveStatus().isDone()) {
                currentHighest = Math.max(currentHighest, min(moveTransition.getToBoard(),
                        difficulty - 1, currentHighest, lowest));
                if (lowest <= currentHighest) {
                    this.cutOffsProduced++;
                    break;
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
        for (final Move move : this.moveSorter.sort((board.currentPlayer().getLegalMoves()))) {
            final MoveTransition moveTransition = board.currentPlayer().makeMove(move);
            if (moveTransition.getMoveStatus().isDone()) {
                currentLowest = Math.min(currentLowest, max(moveTransition.getToBoard(),
                        difficulty - 1, highest, currentLowest));
                if (currentLowest <= highest) {
                    this.cutOffsProduced++;
                    break;
                }
            }
        }
        return currentLowest;
    }

    private static class MoveScoreRecord implements Comparable<MoveScoreRecord> {
        final Move move;
        final int score;

        MoveScoreRecord(final Move move, final int score) {
            this.move = move;
            this.score = score;
        }

        Move getMove() {
            return this.move;
        }

        int getScore() {
            return this.score;
        }

        @Override
        public int compareTo(MoveScoreRecord o) {
            return Integer.compare(this.score, o.score);
        }

        @Override
        public String toString() {
            return this.move + " : " +this.score;
        }
    }

    enum Ordering {
        ASC {
            @Override
            List<MoveScoreRecord> order(final List<MoveScoreRecord> moveScoreRecords) {
                moveScoreRecords.sort((o1, o2) -> Ints.compare(o1.getScore(), o2.getScore()));
                return moveScoreRecords;
            }
        },
        DESC {
            @Override
            List<MoveScoreRecord> order(final List<MoveScoreRecord> moveScoreRecords) {
                moveScoreRecords.sort((o1, o2) -> Ints.compare(o2.getScore(), o1.getScore()));
                return moveScoreRecords;
            }
        };

        abstract List<MoveScoreRecord> order(final List<MoveScoreRecord> moveScoreRecords);
    }


    private static class MoveOrderingBuilder {
        List<MoveScoreRecord> moveScoreRecords;
        Ordering ordering;

        MoveOrderingBuilder() {
            this.moveScoreRecords = new ArrayList<>();
        }

        void addMoveOrderingRecord(final Move move,
                                   final int score) {
            this.moveScoreRecords.add(new MoveScoreRecord(move, score));
        }

        void setOrder(final Ordering order) {
            this.ordering = order;
        }

        List<MoveScoreRecord> build() {
            return this.ordering.order(moveScoreRecords);
        }
    }


}