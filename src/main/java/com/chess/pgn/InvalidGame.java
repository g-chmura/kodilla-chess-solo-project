package com.chess.pgn;

import java.util.Collections;

public class InvalidGame extends Game {

    private final String gameText;

    InvalidGame(final PGNGameTags tags,
                final String gameText,
                final String outcome) {
        super(tags, Collections.emptyList(), outcome);
        this.gameText = gameText;
    }

    @Override
    public String toString() {
        return "Invalid Game " + this.tags;
    }

    @Override
    public boolean isValid() {
        return false;
    }

}
