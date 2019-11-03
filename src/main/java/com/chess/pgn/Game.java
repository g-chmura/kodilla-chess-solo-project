package com.chess.pgn;

import java.util.List;

public abstract class Game implements Playable {

    final PGNGameTags tags;
    protected final List<String> moves;

    Game(final PGNGameTags tags,
         final List<String> moves,
         final String outcome) {
        this.tags = tags;
        this.moves = moves;
        String winner = calculateWinner(outcome);
    }

    @Override
    public String toString() {
        return this.tags.toString();
    }


    private static String calculateWinner(final String gameOutcome) {
        if(gameOutcome.equals("1-0")) {
            return "White";
        }
        if(gameOutcome.equals("0-1")) {
            return "Black";
        }
        if(gameOutcome.equals("1/2-1/2")) {
            return "Tie";
        }
        return "None";
    }

}
