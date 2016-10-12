package com.mthwate.xboard.test

import java.util.*

/**
 * @author mthwate
 */
class Minimax {



	fun run(board: Board, whiteTurn: Boolean): Int {
		return minimax(board, 0, true, whiteTurn)
	}

	fun minimax(board: Board, depth: Int, maximizingPlayer: Boolean, whiteTurn: Boolean): Int {

		if (depth == 3) {
			return board.getValue(whiteTurn)
		}

		var best = if (maximizingPlayer) Int.MIN_VALUE else Int.MAX_VALUE
		val action = if (maximizingPlayer) { a: Int, b: Int -> Math.max(a, b) } else { a: Int, b: Int -> Math.min(a, b) }

		for (move in board.getMoves(whiteTurn)) {
			best = action(best, minimax(board.move(move), depth + 1, !maximizingPlayer, !whiteTurn))
		}

		return best
	}

}