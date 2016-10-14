package com.mthwate.xboard.test

import com.mthwate.xboard.api.Color

/**
 * @author mthwate
 */
class Minimax {

	fun run(board: Board): Int {
		return minimax(board, 0, true, board.turn)
	}

	fun minimax(board: Board, depth: Int, maximizingPlayer: Boolean, turn: Color): Int {

		if (depth == 3) {
			return board.getValue(turn)
		}

		var best = if (maximizingPlayer) Int.MIN_VALUE else Int.MAX_VALUE
		val action = if (maximizingPlayer) { a: Int, b: Int -> Math.max(a, b) } else { a: Int, b: Int -> Math.min(a, b) }

		for (move in board.getMoves()) {
			best = action(best, minimax(board.move(move), depth + 1, !maximizingPlayer, !turn))
		}

		return best
	}

}