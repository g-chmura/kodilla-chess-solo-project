package com.chess.engine.bitboards;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class  BitBoard {

	private static ChessBitSet whiteKnights;
	private static ChessBitSet whiteBishops;
	private static ChessBitSet whiteRooks;
	private static ChessBitSet whiteQueen;
	private static ChessBitSet whiteKing;
	private static ChessBitSet whitePawns;

	private static ChessBitSet blackKnights;
	private static ChessBitSet blackBishops;
	private static ChessBitSet blackRooks;
	private static ChessBitSet blackQueen;
	private static ChessBitSet blackKing;
	private static ChessBitSet blackPawns;

	private static ChessBitSet whiteLegalLocations;
	private static ChessBitSet blackLegalLocations;

	private final List<Move> boardLegalMoves;

	private static final ChessBitSet COLUMN_A = new ChessBitSet();
	private static final ChessBitSet COLUMN_B = new ChessBitSet();
	private static final ChessBitSet COLUMN_C = new ChessBitSet();
	private static final ChessBitSet COLUMN_D = new ChessBitSet();
	private static final ChessBitSet COLUMN_E = new ChessBitSet();
	private static final ChessBitSet COLUMN_F = new ChessBitSet();
	private static final ChessBitSet COLUMN_G = new ChessBitSet();
	private static final ChessBitSet COLUMN_H = new ChessBitSet();

	private static Map<Integer, ChessBitSet> ALL_COLUMNS = new HashMap<>();

	private static final ChessBitSet ROW_1 = new ChessBitSet();
	private static final ChessBitSet ROW_2 = new ChessBitSet();
	private static final ChessBitSet ROW_3 = new ChessBitSet();
	private static final ChessBitSet ROW_4 = new ChessBitSet();
	private static final ChessBitSet ROW_5 = new ChessBitSet();
	private static final ChessBitSet ROW_6 = new ChessBitSet();
	private static final ChessBitSet ROW_7 = new ChessBitSet();
	private static final ChessBitSet ROW_8 = new ChessBitSet();

	private static Map<Integer, ChessBitSet> ALL_ROWS = new HashMap<>();

	private static final ChessBitSet RIGHT_DIAGONAL_1 = new ChessBitSet();
	private static final ChessBitSet RIGHT_DIAGONAL_2 = new ChessBitSet();
	private static final ChessBitSet RIGHT_DIAGONAL_3 = new ChessBitSet();
	private static final ChessBitSet RIGHT_DIAGONAL_4 = new ChessBitSet();
	private static final ChessBitSet RIGHT_DIAGONAL_5 = new ChessBitSet();
	private static final ChessBitSet RIGHT_DIAGONAL_6 = new ChessBitSet();
	private static final ChessBitSet RIGHT_DIAGONAL_7 = new ChessBitSet();
	private static final ChessBitSet RIGHT_DIAGONAL_8 = new ChessBitSet();
	private static final ChessBitSet RIGHT_DIAGONAL_9 = new ChessBitSet();
	private static final ChessBitSet RIGHT_DIAGONAL_10 = new ChessBitSet();
	private static final ChessBitSet RIGHT_DIAGONAL_11 = new ChessBitSet();
	private static final ChessBitSet RIGHT_DIAGONAL_12 = new ChessBitSet();
	private static final ChessBitSet RIGHT_DIAGONAL_13 = new ChessBitSet();
	private static final ChessBitSet RIGHT_DIAGONAL_14 = new ChessBitSet();
	private static final ChessBitSet RIGHT_DIAGONAL_15 = new ChessBitSet();

	private static Map<Integer, ChessBitSet> ALL_RIGHT_DIAGONALS = new HashMap<>();

	private static final ChessBitSet LEFT_DIAGONAL_1 = new ChessBitSet();
	private static final ChessBitSet LEFT_DIAGONAL_2 = new ChessBitSet();
	private static final ChessBitSet LEFT_DIAGONAL_3 = new ChessBitSet();
	private static final ChessBitSet LEFT_DIAGONAL_4 = new ChessBitSet();
	private static final ChessBitSet LEFT_DIAGONAL_5 = new ChessBitSet();
	private static final ChessBitSet LEFT_DIAGONAL_6 = new ChessBitSet();
	private static final ChessBitSet LEFT_DIAGONAL_7 = new ChessBitSet();
	private static final ChessBitSet LEFT_DIAGONAL_8 = new ChessBitSet();
	private static final ChessBitSet LEFT_DIAGONAL_9 = new ChessBitSet();
	private static final ChessBitSet LEFT_DIAGONAL_10 = new ChessBitSet();
	private static final ChessBitSet LEFT_DIAGONAL_11 = new ChessBitSet();
	private static final ChessBitSet LEFT_DIAGONAL_12 = new ChessBitSet();
	private static final ChessBitSet LEFT_DIAGONAL_13 = new ChessBitSet();
	private static final ChessBitSet LEFT_DIAGONAL_14 = new ChessBitSet();
	private static final ChessBitSet LEFT_DIAGONAL_15 = new ChessBitSet();

	private static Map<Integer, ChessBitSet> ALL_LEFT_DIAGONALS = new HashMap<>();

	private static final ChessBitSet EMPTY_SET = new ChessBitSet();
	private static final ChessBitSet FULL_SET = new ChessBitSet();

	private static final String[] algebraicNotation = {
			"a8", "b8", "c8", "d8", "e8", "f8", "g8", "h8",
			"a7", "b7", "c7", "d7", "e7", "f7", "g7", "h7",
			"a6", "b6", "c6", "d6", "e6", "f6", "g6", "h6",
			"a5", "b5", "c5", "d5", "e5", "f5", "g5", "h5",
			"a4", "b4", "c4", "d4", "e4", "f4", "g4", "h4",
			"a3", "b3", "c3", "d3", "e3", "f3", "g3", "h3",
			"a2", "b2", "c2", "d2", "e2", "f2", "g2", "h2",
			"a1", "b1", "c1", "d1", "e1", "f1", "g1", "h1" };

	static {
		initializeColumns();
		initializeLeftDiagonals();
		initializeRightDiagonals();
		initializeRows();
		FULL_SET.set(0, 63);
		EMPTY_SET.clear();
	}

	public BitBoard() {
		//white pieces setting
		whiteRooks = new ChessBitSet();
		whiteRooks.set(56);
		whiteRooks.set(63);

		whiteKnights = new ChessBitSet();
		whiteKnights.set(57);
		whiteKnights.set(62);

		whiteBishops = new ChessBitSet();
		whiteBishops.set(58);
		whiteBishops.set(61);

		whiteQueen = new ChessBitSet();
		whiteQueen.set(59);

		whitePawns = new ChessBitSet();
		whitePawns.set(48);
		whitePawns.set(49);
		whitePawns.set(50);
		whitePawns.set(51);
		whitePawns.set(52);
		whitePawns.set(53);
		whitePawns.set(54);
		whitePawns.set(55);

		whiteKing = new ChessBitSet();
		whiteKing.set(60);

		//black pieces setting
		blackRooks = new ChessBitSet();
		blackRooks.set(0);
		blackRooks.set(7);

		blackKnights = new ChessBitSet();
		blackKnights.set(1);
		blackKnights.set(6);

		blackBishops = new ChessBitSet();
		blackBishops.set(2);
		blackBishops.set(5);

		blackQueen = new ChessBitSet();
		blackQueen.set(3);

		blackPawns = new ChessBitSet();
		blackPawns.set(8);
		blackPawns.set(9);
		blackPawns.set(10);
		blackPawns.set(11);
		blackPawns.set(12);
		blackPawns.set(13);
		blackPawns.set(14);
		blackPawns.set(15);

		blackKing = new ChessBitSet();
		blackKing.set(4);

		whiteLegalLocations = new ChessBitSet();
		blackLegalLocations = new ChessBitSet();

		boardLegalMoves = new ArrayList<>();

		generateStandardLegalMoves();

	}

	private static void initializeRows() {
		for (int i = 0; i < 8; i++) {
			ROW_1.set(i);
			ALL_ROWS.put(i, ROW_1);
		}
		for (int i = 8; i < 16; i++) {
			ROW_2.set(i);
			ALL_ROWS.put(i, ROW_2);
		}
		for (int i = 16; i < 24; i++) {
			ROW_3.set(i);
			ALL_ROWS.put(i, ROW_3);
		}
		for (int i = 24; i < 32; i++) {
			ROW_4.set(i);
			ALL_ROWS.put(i, ROW_4);
		}
		for (int i = 32; i < 40; i++) {
			ROW_5.set(i);
			ALL_ROWS.put(i, ROW_5);
		}
		for (int i = 40; i < 48; i++) {
			ROW_6.set(i);
			ALL_ROWS.put(i, ROW_6);
		}
		for (int i = 48; i < 56; i++) {
			ROW_7.set(i);
			ALL_ROWS.put(i, ROW_7);
		}
		for (int i = 56; i < 64; i++) {
			ROW_8.set(i);
			ALL_ROWS.put(i, ROW_8);
		}
		ALL_ROWS = Collections.unmodifiableMap(ALL_ROWS);
	}

	private static void initializeLeftDiagonals() {
		LEFT_DIAGONAL_1.set(7);
		LEFT_DIAGONAL_2.set(6);
		LEFT_DIAGONAL_2.set(15);
		LEFT_DIAGONAL_3.set(5);
		LEFT_DIAGONAL_3.set(14);
		LEFT_DIAGONAL_3.set(23);
		LEFT_DIAGONAL_4.set(4);
		LEFT_DIAGONAL_4.set(13);
		LEFT_DIAGONAL_4.set(22);
		LEFT_DIAGONAL_4.set(31);
		LEFT_DIAGONAL_5.set(3);
		LEFT_DIAGONAL_5.set(12);
		LEFT_DIAGONAL_5.set(21);
		LEFT_DIAGONAL_5.set(30);
		LEFT_DIAGONAL_5.set(39);
		LEFT_DIAGONAL_6.set(2);
		LEFT_DIAGONAL_6.set(11);
		LEFT_DIAGONAL_6.set(20);
		LEFT_DIAGONAL_6.set(29);
		LEFT_DIAGONAL_6.set(38);
		LEFT_DIAGONAL_6.set(47);
		LEFT_DIAGONAL_7.set(1);
		LEFT_DIAGONAL_7.set(10);
		LEFT_DIAGONAL_7.set(19);
		LEFT_DIAGONAL_7.set(28);
		LEFT_DIAGONAL_7.set(37);
		LEFT_DIAGONAL_7.set(46);
		LEFT_DIAGONAL_7.set(55);
		LEFT_DIAGONAL_8.set(0);
		LEFT_DIAGONAL_8.set(9);
		LEFT_DIAGONAL_8.set(18);
		LEFT_DIAGONAL_8.set(27);
		LEFT_DIAGONAL_8.set(36);
		LEFT_DIAGONAL_8.set(45);
		LEFT_DIAGONAL_8.set(54);
		LEFT_DIAGONAL_8.set(63);
		LEFT_DIAGONAL_9.set(8);
		LEFT_DIAGONAL_9.set(17);
		LEFT_DIAGONAL_9.set(26);
		LEFT_DIAGONAL_9.set(35);
		LEFT_DIAGONAL_9.set(44);
		LEFT_DIAGONAL_9.set(53);
		LEFT_DIAGONAL_9.set(62);
		LEFT_DIAGONAL_10.set(16);
		LEFT_DIAGONAL_10.set(25);
		LEFT_DIAGONAL_10.set(34);
		LEFT_DIAGONAL_10.set(43);
		LEFT_DIAGONAL_10.set(52);
		LEFT_DIAGONAL_10.set(61);
		LEFT_DIAGONAL_11.set(24);
		LEFT_DIAGONAL_11.set(33);
		LEFT_DIAGONAL_11.set(42);
		LEFT_DIAGONAL_11.set(51);
		LEFT_DIAGONAL_11.set(60);
		LEFT_DIAGONAL_12.set(32);
		LEFT_DIAGONAL_12.set(41);
		LEFT_DIAGONAL_12.set(50);
		LEFT_DIAGONAL_12.set(59);
		LEFT_DIAGONAL_13.set(40);
		LEFT_DIAGONAL_13.set(49);
		LEFT_DIAGONAL_13.set(58);
		LEFT_DIAGONAL_14.set(48);
		LEFT_DIAGONAL_14.set(57);
		LEFT_DIAGONAL_15.set(56);
		ALL_LEFT_DIAGONALS.put(7, LEFT_DIAGONAL_1);
		ALL_LEFT_DIAGONALS.put(6, LEFT_DIAGONAL_2);
		ALL_LEFT_DIAGONALS.put(15, LEFT_DIAGONAL_2);
		ALL_LEFT_DIAGONALS.put(5, LEFT_DIAGONAL_3);
		ALL_LEFT_DIAGONALS.put(14, LEFT_DIAGONAL_3);
		ALL_LEFT_DIAGONALS.put(23, LEFT_DIAGONAL_3);
		ALL_LEFT_DIAGONALS.put(4, LEFT_DIAGONAL_4);
		ALL_LEFT_DIAGONALS.put(13, LEFT_DIAGONAL_4);
		ALL_LEFT_DIAGONALS.put(22, LEFT_DIAGONAL_4);
		ALL_LEFT_DIAGONALS.put(31, LEFT_DIAGONAL_4);
		ALL_LEFT_DIAGONALS.put(3, LEFT_DIAGONAL_5);
		ALL_LEFT_DIAGONALS.put(12, LEFT_DIAGONAL_5);
		ALL_LEFT_DIAGONALS.put(21, LEFT_DIAGONAL_5);
		ALL_LEFT_DIAGONALS.put(30, LEFT_DIAGONAL_5);
		ALL_LEFT_DIAGONALS.put(39, LEFT_DIAGONAL_5);
		ALL_LEFT_DIAGONALS.put(2, LEFT_DIAGONAL_6);
		ALL_LEFT_DIAGONALS.put(11, LEFT_DIAGONAL_6);
		ALL_LEFT_DIAGONALS.put(20, LEFT_DIAGONAL_6);
		ALL_LEFT_DIAGONALS.put(29, LEFT_DIAGONAL_6);
		ALL_LEFT_DIAGONALS.put(38, LEFT_DIAGONAL_6);
		ALL_LEFT_DIAGONALS.put(47, LEFT_DIAGONAL_6);
		ALL_LEFT_DIAGONALS.put(1, LEFT_DIAGONAL_7);
		ALL_LEFT_DIAGONALS.put(10, LEFT_DIAGONAL_7);
		ALL_LEFT_DIAGONALS.put(19, LEFT_DIAGONAL_7);
		ALL_LEFT_DIAGONALS.put(28, LEFT_DIAGONAL_7);
		ALL_LEFT_DIAGONALS.put(37, LEFT_DIAGONAL_7);
		ALL_LEFT_DIAGONALS.put(46, LEFT_DIAGONAL_7);
		ALL_LEFT_DIAGONALS.put(55, LEFT_DIAGONAL_7);
		ALL_LEFT_DIAGONALS.put(0, LEFT_DIAGONAL_8);
		ALL_LEFT_DIAGONALS.put(9, LEFT_DIAGONAL_8);
		ALL_LEFT_DIAGONALS.put(18, LEFT_DIAGONAL_8);
		ALL_LEFT_DIAGONALS.put(27, LEFT_DIAGONAL_8);
		ALL_LEFT_DIAGONALS.put(36, LEFT_DIAGONAL_8);
		ALL_LEFT_DIAGONALS.put(45, LEFT_DIAGONAL_8);
		ALL_LEFT_DIAGONALS.put(54, LEFT_DIAGONAL_8);
		ALL_LEFT_DIAGONALS.put(63, LEFT_DIAGONAL_8);
		ALL_LEFT_DIAGONALS.put(8, LEFT_DIAGONAL_9);
		ALL_LEFT_DIAGONALS.put(17, LEFT_DIAGONAL_9);
		ALL_LEFT_DIAGONALS.put(26, LEFT_DIAGONAL_9);
		ALL_LEFT_DIAGONALS.put(35, LEFT_DIAGONAL_9);
		ALL_LEFT_DIAGONALS.put(44, LEFT_DIAGONAL_9);
		ALL_LEFT_DIAGONALS.put(53, LEFT_DIAGONAL_9);
		ALL_LEFT_DIAGONALS.put(62, LEFT_DIAGONAL_9);
		ALL_LEFT_DIAGONALS.put(16, LEFT_DIAGONAL_10);
		ALL_LEFT_DIAGONALS.put(25, LEFT_DIAGONAL_10);
		ALL_LEFT_DIAGONALS.put(34, LEFT_DIAGONAL_10);
		ALL_LEFT_DIAGONALS.put(43, LEFT_DIAGONAL_10);
		ALL_LEFT_DIAGONALS.put(52, LEFT_DIAGONAL_10);
		ALL_LEFT_DIAGONALS.put(61, LEFT_DIAGONAL_10);
		ALL_LEFT_DIAGONALS.put(24, LEFT_DIAGONAL_11);
		ALL_LEFT_DIAGONALS.put(33, LEFT_DIAGONAL_11);
		ALL_LEFT_DIAGONALS.put(42, LEFT_DIAGONAL_11);
		ALL_LEFT_DIAGONALS.put(51, LEFT_DIAGONAL_11);
		ALL_LEFT_DIAGONALS.put(60, LEFT_DIAGONAL_11);
		ALL_LEFT_DIAGONALS.put(32, LEFT_DIAGONAL_12);
		ALL_LEFT_DIAGONALS.put(41, LEFT_DIAGONAL_12);
		ALL_LEFT_DIAGONALS.put(50, LEFT_DIAGONAL_12);
		ALL_LEFT_DIAGONALS.put(59, LEFT_DIAGONAL_12);
		ALL_LEFT_DIAGONALS.put(40, LEFT_DIAGONAL_13);
		ALL_LEFT_DIAGONALS.put(49, LEFT_DIAGONAL_13);
		ALL_LEFT_DIAGONALS.put(58, LEFT_DIAGONAL_13);
		ALL_LEFT_DIAGONALS.put(48, LEFT_DIAGONAL_14);
		ALL_LEFT_DIAGONALS.put(57, LEFT_DIAGONAL_14);
		ALL_LEFT_DIAGONALS.put(56, LEFT_DIAGONAL_15);
		ALL_LEFT_DIAGONALS = Collections.unmodifiableMap(ALL_LEFT_DIAGONALS);
	}

	private static void initializeRightDiagonals() {
		RIGHT_DIAGONAL_1.set(0);
		RIGHT_DIAGONAL_2.set(1);
		RIGHT_DIAGONAL_2.set(8);
		RIGHT_DIAGONAL_3.set(2);
		RIGHT_DIAGONAL_3.set(9);
		RIGHT_DIAGONAL_3.set(16);
		RIGHT_DIAGONAL_4.set(3);
		RIGHT_DIAGONAL_4.set(10);
		RIGHT_DIAGONAL_4.set(17);
		RIGHT_DIAGONAL_4.set(24);
		RIGHT_DIAGONAL_5.set(4);
		RIGHT_DIAGONAL_5.set(11);
		RIGHT_DIAGONAL_5.set(18);
		RIGHT_DIAGONAL_5.set(25);
		RIGHT_DIAGONAL_5.set(32);
		RIGHT_DIAGONAL_6.set(5);
		RIGHT_DIAGONAL_6.set(12);
		RIGHT_DIAGONAL_6.set(19);
		RIGHT_DIAGONAL_6.set(26);
		RIGHT_DIAGONAL_6.set(33);
		RIGHT_DIAGONAL_6.set(40);
		RIGHT_DIAGONAL_7.set(6);
		RIGHT_DIAGONAL_7.set(13);
		RIGHT_DIAGONAL_7.set(20);
		RIGHT_DIAGONAL_7.set(27);
		RIGHT_DIAGONAL_7.set(34);
		RIGHT_DIAGONAL_7.set(41);
		RIGHT_DIAGONAL_7.set(48);
		RIGHT_DIAGONAL_8.set(7);
		RIGHT_DIAGONAL_8.set(14);
		RIGHT_DIAGONAL_8.set(21);
		RIGHT_DIAGONAL_8.set(28);
		RIGHT_DIAGONAL_8.set(35);
		RIGHT_DIAGONAL_8.set(42);
		RIGHT_DIAGONAL_8.set(49);
		RIGHT_DIAGONAL_8.set(56);
		RIGHT_DIAGONAL_9.set(15);
		RIGHT_DIAGONAL_9.set(22);
		RIGHT_DIAGONAL_9.set(29);
		RIGHT_DIAGONAL_9.set(36);
		RIGHT_DIAGONAL_9.set(43);
		RIGHT_DIAGONAL_9.set(50);
		RIGHT_DIAGONAL_9.set(57);
		RIGHT_DIAGONAL_10.set(23);
		RIGHT_DIAGONAL_10.set(30);
		RIGHT_DIAGONAL_10.set(37);
		RIGHT_DIAGONAL_10.set(44);
		RIGHT_DIAGONAL_10.set(51);
		RIGHT_DIAGONAL_10.set(58);
		RIGHT_DIAGONAL_11.set(31);
		RIGHT_DIAGONAL_11.set(38);
		RIGHT_DIAGONAL_11.set(45);
		RIGHT_DIAGONAL_11.set(52);
		RIGHT_DIAGONAL_11.set(59);
		RIGHT_DIAGONAL_12.set(39);
		RIGHT_DIAGONAL_12.set(46);
		RIGHT_DIAGONAL_12.set(53);
		RIGHT_DIAGONAL_12.set(60);
		RIGHT_DIAGONAL_13.set(47);
		RIGHT_DIAGONAL_13.set(54);
		RIGHT_DIAGONAL_13.set(61);
		RIGHT_DIAGONAL_14.set(55);
		RIGHT_DIAGONAL_14.set(62);
		RIGHT_DIAGONAL_15.set(63);
		ALL_RIGHT_DIAGONALS.put(0, RIGHT_DIAGONAL_1);
		ALL_RIGHT_DIAGONALS.put(1, RIGHT_DIAGONAL_2);
		ALL_RIGHT_DIAGONALS.put(8, RIGHT_DIAGONAL_2);
		ALL_RIGHT_DIAGONALS.put(2, RIGHT_DIAGONAL_3);
		ALL_RIGHT_DIAGONALS.put(9, RIGHT_DIAGONAL_3);
		ALL_RIGHT_DIAGONALS.put(16, RIGHT_DIAGONAL_3);
		ALL_RIGHT_DIAGONALS.put(3, RIGHT_DIAGONAL_4);
		ALL_RIGHT_DIAGONALS.put(10, RIGHT_DIAGONAL_4);
		ALL_RIGHT_DIAGONALS.put(17, RIGHT_DIAGONAL_4);
		ALL_RIGHT_DIAGONALS.put(24, RIGHT_DIAGONAL_4);
		ALL_RIGHT_DIAGONALS.put(4, RIGHT_DIAGONAL_5);
		ALL_RIGHT_DIAGONALS.put(11, RIGHT_DIAGONAL_5);
		ALL_RIGHT_DIAGONALS.put(18, RIGHT_DIAGONAL_5);
		ALL_RIGHT_DIAGONALS.put(25, RIGHT_DIAGONAL_5);
		ALL_RIGHT_DIAGONALS.put(32, RIGHT_DIAGONAL_5);
		ALL_RIGHT_DIAGONALS.put(5, RIGHT_DIAGONAL_6);
		ALL_RIGHT_DIAGONALS.put(12, RIGHT_DIAGONAL_6);
		ALL_RIGHT_DIAGONALS.put(19, RIGHT_DIAGONAL_6);
		ALL_RIGHT_DIAGONALS.put(26, RIGHT_DIAGONAL_6);
		ALL_RIGHT_DIAGONALS.put(33, RIGHT_DIAGONAL_6);
		ALL_RIGHT_DIAGONALS.put(40, RIGHT_DIAGONAL_6);
		ALL_RIGHT_DIAGONALS.put(6, RIGHT_DIAGONAL_7);
		ALL_RIGHT_DIAGONALS.put(13, RIGHT_DIAGONAL_7);
		ALL_RIGHT_DIAGONALS.put(20, RIGHT_DIAGONAL_7);
		ALL_RIGHT_DIAGONALS.put(27, RIGHT_DIAGONAL_7);
		ALL_RIGHT_DIAGONALS.put(34, RIGHT_DIAGONAL_7);
		ALL_RIGHT_DIAGONALS.put(41, RIGHT_DIAGONAL_7);
		ALL_RIGHT_DIAGONALS.put(48, RIGHT_DIAGONAL_7);
		ALL_RIGHT_DIAGONALS.put(7, RIGHT_DIAGONAL_8);
		ALL_RIGHT_DIAGONALS.put(14, RIGHT_DIAGONAL_8);
		ALL_RIGHT_DIAGONALS.put(21, RIGHT_DIAGONAL_8);
		ALL_RIGHT_DIAGONALS.put(28, RIGHT_DIAGONAL_8);
		ALL_RIGHT_DIAGONALS.put(35, RIGHT_DIAGONAL_8);
		ALL_RIGHT_DIAGONALS.put(42, RIGHT_DIAGONAL_8);
		ALL_RIGHT_DIAGONALS.put(49, RIGHT_DIAGONAL_8);
		ALL_RIGHT_DIAGONALS.put(56, RIGHT_DIAGONAL_8);
		ALL_RIGHT_DIAGONALS.put(15, RIGHT_DIAGONAL_9);
		ALL_RIGHT_DIAGONALS.put(22, RIGHT_DIAGONAL_9);
		ALL_RIGHT_DIAGONALS.put(29, RIGHT_DIAGONAL_9);
		ALL_RIGHT_DIAGONALS.put(36, RIGHT_DIAGONAL_9);
		ALL_RIGHT_DIAGONALS.put(43, RIGHT_DIAGONAL_9);
		ALL_RIGHT_DIAGONALS.put(50, RIGHT_DIAGONAL_9);
		ALL_RIGHT_DIAGONALS.put(57, RIGHT_DIAGONAL_9);
		ALL_RIGHT_DIAGONALS.put(23, RIGHT_DIAGONAL_10);
		ALL_RIGHT_DIAGONALS.put(30, RIGHT_DIAGONAL_10);
		ALL_RIGHT_DIAGONALS.put(37, RIGHT_DIAGONAL_10);
		ALL_RIGHT_DIAGONALS.put(44, RIGHT_DIAGONAL_10);
		ALL_RIGHT_DIAGONALS.put(51, RIGHT_DIAGONAL_10);
		ALL_RIGHT_DIAGONALS.put(58, RIGHT_DIAGONAL_10);
		ALL_RIGHT_DIAGONALS.put(31, RIGHT_DIAGONAL_11);
		ALL_RIGHT_DIAGONALS.put(38, RIGHT_DIAGONAL_11);
		ALL_RIGHT_DIAGONALS.put(45, RIGHT_DIAGONAL_11);
		ALL_RIGHT_DIAGONALS.put(52, RIGHT_DIAGONAL_11);
		ALL_RIGHT_DIAGONALS.put(59, RIGHT_DIAGONAL_11);
		ALL_RIGHT_DIAGONALS.put(39, RIGHT_DIAGONAL_12);
		ALL_RIGHT_DIAGONALS.put(46, RIGHT_DIAGONAL_12);
		ALL_RIGHT_DIAGONALS.put(53, RIGHT_DIAGONAL_12);
		ALL_RIGHT_DIAGONALS.put(60, RIGHT_DIAGONAL_12);
		ALL_RIGHT_DIAGONALS.put(47, RIGHT_DIAGONAL_13);
		ALL_RIGHT_DIAGONALS.put(54, RIGHT_DIAGONAL_13);
		ALL_RIGHT_DIAGONALS.put(61, RIGHT_DIAGONAL_13);
		ALL_RIGHT_DIAGONALS.put(55, RIGHT_DIAGONAL_14);
		ALL_RIGHT_DIAGONALS.put(62, RIGHT_DIAGONAL_14);
		ALL_RIGHT_DIAGONALS.put(63, RIGHT_DIAGONAL_15);
		ALL_RIGHT_DIAGONALS = Collections.unmodifiableMap(ALL_RIGHT_DIAGONALS);
	}

	private static void initializeColumns() {
		for (int i = 0; i < 64; i += 8) {
			COLUMN_A.set(i);
			ALL_COLUMNS.put(i, COLUMN_A);
		}
		for (int i = 1; i < 64; i += 8) {
			COLUMN_B.set(i);
			ALL_COLUMNS.put(i, COLUMN_B);
		}
		for (int i = 2; i < 64; i += 8) {
			COLUMN_C.set(i);
			ALL_COLUMNS.put(i, COLUMN_C);
		}
		for (int i = 3; i < 64; i += 8) {
			COLUMN_D.set(i);
			ALL_COLUMNS.put(i, COLUMN_D);
		}
		for (int i = 4; i < 64; i += 8) {
			COLUMN_E.set(i);
			ALL_COLUMNS.put(i, COLUMN_E);
		}
		for (int i = 5; i < 64; i += 8) {
			COLUMN_F.set(i);
			ALL_COLUMNS.put(i, COLUMN_F);
		}
		for (int i = 6; i < 64; i += 8) {
			COLUMN_G.set(i);
			ALL_COLUMNS.put(i, COLUMN_G);
		}
		for (int i = 7; i < 64; i += 8) {
			COLUMN_H.set(i);
			ALL_COLUMNS.put(i, COLUMN_H);
		}
		ALL_COLUMNS = Collections.unmodifiableMap(ALL_COLUMNS);
	}

	private void generateStandardLegalMoves() {

		boardLegalMoves.clear();
		whiteLegalLocations.clear();
		blackLegalLocations.clear();

		for (final KindsOfPieces p : KindsOfPieces.values()) {
			boardLegalMoves.addAll(p.calculateLegalMoves());
		}
	}

	@Override
	public String toString() {

		final char[] tiles = new char[64];

		final ChessBitSet allKnights = KindsOfPieces.allKnights();

		for (int currentKnightLocation = allKnights.nextSetBit(0); currentKnightLocation >= 0; currentKnightLocation = allKnights
		        .nextSetBit(currentKnightLocation + 1)) {
			tiles[currentKnightLocation] = 'N';
		}

		final ChessBitSet allBishops = KindsOfPieces.allBishops();

		for (int currentBishopLocation = allBishops.nextSetBit(0); currentBishopLocation >= 0; currentBishopLocation = allBishops
		        .nextSetBit(currentBishopLocation + 1)) {
			tiles[currentBishopLocation] = 'B';
		}

		final ChessBitSet allRooks = KindsOfPieces.allRooks();

		for (int currentRookLocation = allRooks.nextSetBit(0); currentRookLocation >= 0; currentRookLocation = allRooks
		        .nextSetBit(currentRookLocation + 1)) {
			tiles[currentRookLocation] = 'R';
		}

		final ChessBitSet allPawns = KindsOfPieces.allPawns();

		for (int currentPawnLocation = allPawns.nextSetBit(0); currentPawnLocation >= 0; currentPawnLocation = allPawns
		        .nextSetBit(currentPawnLocation + 1)) {
			tiles[currentPawnLocation] = 'P';
		}

		final ChessBitSet allQueens = KindsOfPieces.allQueens();

		for (int currentQueenLocation = allQueens.nextSetBit(0); currentQueenLocation >= 0; currentQueenLocation = allQueens
		        .nextSetBit(currentQueenLocation + 1)) {
			tiles[currentQueenLocation] = 'Q';
		}

		final ChessBitSet allKings = KindsOfPieces.allKings();

		for (int currentKingLocation = allKings.nextSetBit(0); currentKingLocation >= 0; currentKingLocation = allKings
		        .nextSetBit(currentKingLocation + 1)) {
			tiles[currentKingLocation] = 'K';
		}

		final StringBuilder result = new StringBuilder();

		for (int i = 0; i < tiles.length; i++) {

			if (tiles[i] != 0) {
				result.append(tiles[i]);
			} else {
				result.append(".");
			}

			result.append(" ");

			if ((i + 1) % 8 == 0) {
				result.append("\n");
			}
		}

		return result.toString();

	}

	enum KindsOfPieces {

		WHITE_KNIGHTS() {
			@Override
			public List<Move> calculateLegalMoves() {
				return calculateKnightLegalMoves(whiteKnights);
			}

			@Override
			public ChessBitSet alliedPieces() {
				return allWhitePieces();
			}

			@Override
			public ChessBitSet enemyPieces() {
				return allBlackPieces();
			}

			@Override
			public void setBits(final int moveLocation) {
				whiteLegalLocations.set(moveLocation);
			}

		},

		WHITE_BISHOPS {
			@Override
			public List<Move> calculateLegalMoves() {
				return calculateBishopLegalMoves(whiteBishops);
			}

			@Override
			public ChessBitSet alliedPieces() {
				return allWhitePieces();
			}

			@Override
			public ChessBitSet enemyPieces() {
				return allBlackPieces();
			}

			@Override
			public void setBits(final int moveLocation) {
				whiteLegalLocations.set(moveLocation);
			}
		},
		WHITE_ROOKS {
			@Override
			public List<Move> calculateLegalMoves() {
				return calculateRookLegalMoves(whiteRooks);
			}

			@Override
			public ChessBitSet alliedPieces() {
				return allWhitePieces();
			}

			@Override
			public ChessBitSet enemyPieces() {
				return allBlackPieces();
			}

			@Override
			public void setBits(final int moveLocation) {
				whiteLegalLocations.set(moveLocation);
			}
		},
		WHITE_PAWNS {
			@Override
			public List<Move> calculateLegalMoves() {

				final List<Move> legalMoves = new ArrayList<>();
				final ChessBitSet allPieces = allPieces();
				final ChessBitSet enemyPieces = enemyPieces();
				final ChessBitSet pawnAdvances = new ChessBitSet(whitePawns);
				pawnAdvances.shift(-8);
				pawnAdvances.andNot(allPieces);
				final ChessBitSet pawnJumps = new ChessBitSet(whitePawns);
				pawnJumps.shift(-16);
				pawnJumps.andNot(allPieces);
				final ChessBitSet pawnAttacksLeft = new ChessBitSet(whitePawns);
				pawnAttacksLeft.shift(-9);
				pawnAttacksLeft.and(enemyPieces);
				final ChessBitSet pawnAttacksRight = new ChessBitSet(whitePawns);
				pawnAttacksRight.shift(-7);
				pawnAttacksRight.and(enemyPieces);

				for (int currentPawnLocation = whitePawns.nextSetBit(0); currentPawnLocation >= 0; currentPawnLocation = whitePawns
				        .nextSetBit(currentPawnLocation + 1)) {

					int candidateLocation = currentPawnLocation - 8;

					if (pawnAdvances.get(candidateLocation)) {
						legalMoves.add(new Move(currentPawnLocation,
						        candidateLocation, this));
					}

					candidateLocation = currentPawnLocation - 16;

					if (pawnJumps.get(candidateLocation)) {
						legalMoves.add(new Move(currentPawnLocation,
						        candidateLocation, this));
					}

				}

				return legalMoves;
			}

			@Override
			public ChessBitSet alliedPieces() {
				return allWhitePieces();
			}

			@Override
			public ChessBitSet enemyPieces() {
				return allBlackPieces();
			}

			@Override
			public void setBits(final int moveLocation) {
				whiteLegalLocations.set(moveLocation);
			}
		},
		BLACK_KNIGHTS() {
			@Override
			public List<Move> calculateLegalMoves() {
				return calculateKnightLegalMoves(blackKnights);
			}

			@Override
			public ChessBitSet alliedPieces() {
				return allBlackPieces();
			}

			@Override
			public ChessBitSet enemyPieces() {
				return allWhitePieces();
			}

			@Override
			public void setBits(final int moveLocation) {
				blackLegalLocations.set(moveLocation);
			}

		},
		BLACK_BISHOPS {
			@Override
			public List<Move> calculateLegalMoves() {
				return calculateBishopLegalMoves(blackBishops);
			}

			@Override
			public ChessBitSet alliedPieces() {
				return allBlackPieces();
			}

			@Override
			public ChessBitSet enemyPieces() {
				return allWhitePieces();
			}

			@Override
			public void setBits(final int moveLocation) {
				blackLegalLocations.set(moveLocation);
			}
		},
		BLACK_ROOKS {
			@Override
			public List<Move> calculateLegalMoves() {
				return calculateRookLegalMoves(blackRooks);
			}

			@Override
			public ChessBitSet alliedPieces() {
				return allBlackPieces();
			}

			@Override
			public ChessBitSet enemyPieces() {
				return allWhitePieces();
			}

			@Override
			public void setBits(final int moveLocation) {
				blackLegalLocations.set(moveLocation);
			}
		},
		BLACK_PAWNS {
			@Override
			public List<Move> calculateLegalMoves() {

				final List<Move> legalMoves = new ArrayList<>();
				final ChessBitSet allPieces = allPieces();
				final ChessBitSet enemyPieces = enemyPieces();
				final ChessBitSet pawnAdvances = new ChessBitSet(blackPawns);
				pawnAdvances.shift(8);
				pawnAdvances.andNot(allPieces);
				final ChessBitSet pawnJumps = new ChessBitSet(blackPawns);
				pawnJumps.shift(16);
				pawnJumps.andNot(allPieces);
				final ChessBitSet pawnAttacksLeft = new ChessBitSet(blackPawns);
				pawnAttacksLeft.shift(9);
				pawnAttacksLeft.and(enemyPieces);
				final ChessBitSet pawnAttacksRight = new ChessBitSet(blackPawns);
				pawnAttacksRight.shift(7);
				pawnAttacksRight.and(enemyPieces);

				for (int currentPawnLocation = blackPawns.nextSetBit(0); currentPawnLocation >= 0; currentPawnLocation = blackPawns
				        .nextSetBit(currentPawnLocation + 1)) {

					int candidateLocation = currentPawnLocation + 8;

					if (pawnAdvances.get(candidateLocation)) {
						legalMoves.add(new Move(currentPawnLocation,
						        candidateLocation, this));
					}

					candidateLocation = currentPawnLocation + 16;

					if (pawnJumps.get(candidateLocation)) {
						legalMoves.add(new Move(currentPawnLocation,
						        candidateLocation, this));
					}

				}

				whiteLegalLocations.or(pawnAdvances);
				whiteLegalLocations.or(pawnJumps);

				return legalMoves;
			}

			@Override
			public ChessBitSet alliedPieces() {
				return allBlackPieces();
			}

			@Override
			public ChessBitSet enemyPieces() {
				return allWhitePieces();
			}

			@Override
			public void setBits(final int moveLocation) {
				blackLegalLocations.set(moveLocation);
			}
		};

		public abstract List<Move> calculateLegalMoves();

		public abstract ChessBitSet alliedPieces();

		public abstract ChessBitSet enemyPieces();

		public abstract void setBits(int moveLocation);

		private static boolean isValidTile(final int moveLocation) {
			return moveLocation >= 0 && moveLocation < 64;
		}

		public List<Move> calculateRookLegalMoves(final ChessBitSet rookBitSet) {

			final ChessBitSet alliedUnits = alliedPieces();
			final ChessBitSet enemyUnits = enemyPieces();
			final List<Move> legalMoves = new ArrayList<>();

			for (int currentRookLocation = rookBitSet.nextSetBit(0); currentRookLocation >= 0; currentRookLocation = rookBitSet
			        .nextSetBit(currentRookLocation + 1)) {

				final ChessBitSet vertical = ALL_COLUMNS.get(currentRookLocation);
				final ChessBitSet horizontal = ALL_ROWS
				        .get(currentRookLocation);

				// up
				int candidateLocation = currentRookLocation - 8;
				int endPos = vertical.nextSetBit(0);
				while (candidateLocation >= endPos) {
					if (alliedUnits.get(candidateLocation)) {
						break;
					}
					setBits(candidateLocation);
					legalMoves.add(new Move(currentRookLocation,
					        candidateLocation, this));
					if (enemyUnits.get(candidateLocation)) {
						break;
					}
					candidateLocation -= 8;
				}

				// down
				candidateLocation = currentRookLocation + 8;
				endPos = vertical.length();
				while (endPos >= candidateLocation) {
					if (alliedUnits.get(candidateLocation)) {
						break;
					}
					setBits(candidateLocation);
					legalMoves.add(new Move(currentRookLocation,
					        candidateLocation, this));
					if (enemyUnits.get(candidateLocation)) {
						break;
					}
					candidateLocation += 8;
				}

				// left
				candidateLocation = currentRookLocation - 1;
				endPos = horizontal.nextSetBit(0);
				while (candidateLocation >= endPos) {
					if (alliedUnits.get(candidateLocation)) {
						break;
					}
					setBits(candidateLocation);
					legalMoves.add(new Move(currentRookLocation,
					        candidateLocation, this));
					if (enemyUnits.get(candidateLocation)) {
						break;
					}
					candidateLocation--;
				}

				// right
				candidateLocation = currentRookLocation + 1;
				endPos = horizontal.length() - 1;
				while (candidateLocation <= endPos) {
					if (alliedUnits.get(candidateLocation)) {
						break;
					}
					setBits(candidateLocation);
					legalMoves.add(new Move(currentRookLocation,
					        candidateLocation, this));
					if (enemyUnits.get(candidateLocation)) {
						break;
					}
					candidateLocation++;
				}

			}

			return legalMoves;

		}

		public List<Move> calculateBishopLegalMoves(final ChessBitSet bishopBitSet) {

			final ChessBitSet alliedUnits = alliedPieces();
			final ChessBitSet enemyUnits = enemyPieces();
			final List<Move> legalMoves = new ArrayList<>();

			for (int currentBishopLocation = bishopBitSet.nextSetBit(0); currentBishopLocation >= 0; currentBishopLocation = bishopBitSet
			        .nextSetBit(currentBishopLocation + 1)) {

				final ChessBitSet rightDiag = ALL_RIGHT_DIAGONALS
				        .get(currentBishopLocation);
				final ChessBitSet leftDiag = ALL_LEFT_DIAGONALS
				        .get(currentBishopLocation);

				// Up to the right
				int candidateLocation = currentBishopLocation - 7;
				int endPos = rightDiag.nextSetBit(0);
				while (candidateLocation >= endPos) {
					if (alliedUnits.get(candidateLocation)) {
						break;
					}
					setBits(candidateLocation);
					legalMoves.add(new Move(currentBishopLocation,
					        candidateLocation, this));
					if (enemyUnits.get(candidateLocation)) {
						break;
					}
					candidateLocation -= 7;
				}

				// Down to the left
				candidateLocation = currentBishopLocation + 7;
				endPos = rightDiag.length() - 1;
				while (endPos >= candidateLocation) {
					if (alliedUnits.get(candidateLocation)) {
						break;
					}
					setBits(candidateLocation);
					legalMoves.add(new Move(currentBishopLocation,
					        candidateLocation, this));
					if (enemyUnits.get(candidateLocation)) {
						break;
					}
					candidateLocation += 7;
				}

				// Up to the left
				candidateLocation = currentBishopLocation - 9;
				endPos = leftDiag.nextSetBit(0);
				while (candidateLocation >= endPos) {
					if (alliedUnits.get(candidateLocation)) {
						break;
					}
					setBits(candidateLocation);
					legalMoves.add(new Move(currentBishopLocation,
					        candidateLocation, this));
					if (enemyUnits.get(candidateLocation)) {
						break;
					}
					candidateLocation -= 9;
				}

				// Down to the right
				candidateLocation = currentBishopLocation + 9;
				endPos = leftDiag.length() - 1;
				while (candidateLocation <= endPos) {
					if (alliedUnits.get(candidateLocation)) {
						break;
					}
					setBits(candidateLocation);
					legalMoves.add(new Move(currentBishopLocation,
					        candidateLocation, this));
					if (enemyUnits.get(candidateLocation)) {
						break;
					}
					candidateLocation += 9;
				}

			}

			return legalMoves;
		}

		public List<Move> calculateKnightLegalMoves(final ChessBitSet knightBitSet) {

			final ChessBitSet alliedUnits = alliedPieces();
			final ChessBitSet enemyUnits = enemyPieces();
			final List<Move> legalMoves = new ArrayList<>();

			for (int currentKnightLocation = knightBitSet.nextSetBit(0); currentKnightLocation >= 0; currentKnightLocation = knightBitSet
			        .nextSetBit(currentKnightLocation + 1)) {
				if (!(COLUMN_G.get(currentKnightLocation) || COLUMN_H
				        .get(currentKnightLocation))) {
					final int candidateLocation = currentKnightLocation - 6;
					if (isValidTile(candidateLocation)) {
						if (!alliedUnits.get(candidateLocation)
						        || enemyUnits.get(candidateLocation)) {
							setBits(candidateLocation);
							legalMoves.add(new Move(currentKnightLocation,
							        candidateLocation, this));
						}
					}
				}
				if (!(COLUMN_A.get(currentKnightLocation) || COLUMN_B
				        .get(currentKnightLocation))) {
					final int candidateLocation = currentKnightLocation - 10;
					if (isValidTile(candidateLocation)) {
						if (!alliedUnits.get(candidateLocation)
						        || enemyUnits.get(candidateLocation)) {
							setBits(candidateLocation);
							legalMoves.add(new Move(currentKnightLocation,
							        candidateLocation, this));
						}
					}
				}
				if (!(COLUMN_H.get(currentKnightLocation))) {
					final int candidateLocation = currentKnightLocation - 15;
					if (isValidTile(candidateLocation)) {
						if (!alliedUnits.get(candidateLocation)
						        || enemyUnits.get(candidateLocation)) {
							setBits(candidateLocation);
							legalMoves.add(new Move(currentKnightLocation,
							        candidateLocation, this));
						}
					}
				}
				if (!(COLUMN_A.get(currentKnightLocation))) {
					final int candidateLocation = currentKnightLocation - 17;
					if (isValidTile(candidateLocation)) {
						if (!alliedUnits.get(candidateLocation)
						        || enemyUnits.get(candidateLocation)) {
							setBits(candidateLocation);
							legalMoves.add(new Move(currentKnightLocation,
							        candidateLocation, this));
						}
					}
				}
				if (!(COLUMN_A.get(currentKnightLocation) || COLUMN_B
				        .get(currentKnightLocation))) {
					final int candidateLocation = currentKnightLocation + 6;
					if (isValidTile(candidateLocation)) {
						if (!alliedUnits.get(candidateLocation)
						        || enemyUnits.get(candidateLocation)) {
							setBits(candidateLocation);
							legalMoves.add(new Move(currentKnightLocation,
							        candidateLocation, this));
						}
					}
				}
				if (!(COLUMN_G.get(currentKnightLocation) || COLUMN_H
				        .get(currentKnightLocation))) {
					final int candidateLocation = currentKnightLocation + 10;
					if (isValidTile(candidateLocation)) {
						if (!alliedUnits.get(candidateLocation)
						        || enemyUnits.get(candidateLocation)) {
							setBits(candidateLocation);
							legalMoves.add(new Move(currentKnightLocation,
							        candidateLocation, this));
						}
					}
				}
				if (!(COLUMN_A.get(currentKnightLocation))) {
					final int candidateLocation = currentKnightLocation + 15;
					if (isValidTile(candidateLocation)) {
						if (!alliedUnits.get(candidateLocation)
						        || enemyUnits.get(candidateLocation)) {
							setBits(candidateLocation);
							legalMoves.add(new Move(currentKnightLocation,
							        candidateLocation, this));
						}
					}
				}
				if (!(COLUMN_H.get(currentKnightLocation))) {
					final int candidateLocation = currentKnightLocation + 17;
					if (isValidTile(candidateLocation)) {
						if (!alliedUnits.get(candidateLocation)
						        || enemyUnits.get(candidateLocation)) {
							setBits(candidateLocation);
							legalMoves.add(new Move(currentKnightLocation,
							        candidateLocation, this));
						}
					}
				}
			}

			return legalMoves;

		}

		public static ChessBitSet allPawns() {
			final ChessBitSet allPawns = new ChessBitSet();
			allPawns.or(whitePawns);
			allPawns.or(blackPawns);
			return allPawns;
		}

		public static ChessBitSet allKnights() {
			final ChessBitSet allKnights = new ChessBitSet();
			allKnights.or(whiteKnights);
			allKnights.or(blackKnights);
			return allKnights;
		}

		public static ChessBitSet allBishops() {
			final ChessBitSet allBishops = new ChessBitSet();
			allBishops.or(whiteBishops);
			allBishops.or(blackBishops);
			return allBishops;
		}

		public static ChessBitSet allRooks() {
			final ChessBitSet allRooks = new ChessBitSet();
			allRooks.or(whiteRooks);
			allRooks.or(blackRooks);
			return allRooks;
		}

		public static ChessBitSet allQueens() {
			final ChessBitSet allQueens = new ChessBitSet();
			allQueens.or(whiteQueen);
			allQueens.or(blackQueen);
			return allQueens;
		}

		public static ChessBitSet allKings() {
			final ChessBitSet allKings = new ChessBitSet();
			allKings.or(whiteKing);
			allKings.or(blackKing);
			return allKings;
		}

		public static ChessBitSet allPieces() {
			final ChessBitSet allPieces = new ChessBitSet();
			allPieces.or(whiteRooks);
			allPieces.or(whiteKnights);
			allPieces.or(whiteBishops);
			allPieces.or(whiteQueen);
			allPieces.or(whitePawns);
			allPieces.or(blackRooks);
			allPieces.or(blackKnights);
			allPieces.or(blackBishops);
			allPieces.or(blackQueen);
			allPieces.or(blackPawns);
			return allPieces;
		}

		public static ChessBitSet allWhitePieces() {
			final ChessBitSet allWhitePieces = new ChessBitSet();
			allWhitePieces.or(whiteRooks);
			allWhitePieces.or(whiteKnights);
			allWhitePieces.or(whiteBishops);
			allWhitePieces.or(whiteQueen);
			allWhitePieces.or(whitePawns);
			return allWhitePieces;
		}

		public static ChessBitSet allBlackPieces() {
			final ChessBitSet allBlackPieces = new ChessBitSet();
			allBlackPieces.or(blackRooks);
			allBlackPieces.or(blackKnights);
			allBlackPieces.or(blackBishops);
			allBlackPieces.or(blackQueen);
			allBlackPieces.or(blackPawns);
			return allBlackPieces;
		}

	}

	static String getPositionAtCoordinate(final int c) {

		return algebraicNotation[c];

	}

}