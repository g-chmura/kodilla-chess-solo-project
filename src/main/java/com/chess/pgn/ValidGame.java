package com.chess.pgn;

import java.util.List;

class ValidGame extends Game {

    ValidGame(final PGNGameTags tags, List<String> moves, final String outcome) {
        super(tags, moves, outcome);
    }

    @Override
    public boolean isValid() {
        return true;
    }

}
