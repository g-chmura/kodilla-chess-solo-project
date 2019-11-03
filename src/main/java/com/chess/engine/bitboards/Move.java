package com.chess.engine.bitboards;

import com.chess.engine.bitboards.BitBoard.KindsOfPieces;

public class Move {

	private final int currentLocation;
	private final int destinationLocation;
	private final KindsOfPieces movedKindsOfPieces;

	public Move(final int currentLocation,
                final int destinationLocation,
                final KindsOfPieces moved) {
		this.currentLocation = currentLocation;
		this.destinationLocation = destinationLocation;
		this.movedKindsOfPieces = moved;
	}

	@Override
	public String toString() {
		return BitBoard.getPositionAtCoordinate(this.currentLocation) + "-"
		        + BitBoard.getPositionAtCoordinate(this.destinationLocation);
	}

	@Override
	public int hashCode() {
		return this.movedKindsOfPieces.hashCode() +
			   this.currentLocation +
               this.destinationLocation;
	}

	@Override
	public boolean equals(final Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof Move)) {
			return false;
		}
		final Move otherMove = (Move) other;
		return (this.movedKindsOfPieces == otherMove.getMovedKindsOfPieces())
		        && (this.currentLocation == otherMove.getCurrentLocation())
		        && (this.destinationLocation == otherMove.getDestinationLocation());
	}

	private int getDestinationLocation() {
		return this.destinationLocation;
	}

	private int getCurrentLocation() {
		return this.currentLocation;
	}

	private KindsOfPieces getMovedKindsOfPieces() {
		return this.movedKindsOfPieces;
	}

}
