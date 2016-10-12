package com.mthwate.xboard.test.piece

import com.mthwate.xboard.api.Move
import com.mthwate.xboard.test.Board
import java.util.*

/**
 * @author mthwate
 */
object Knight : Piece {

	override val value = 3

	val coords = arrayOf(
			Pair(1, 2),
			Pair(-1, 2),
			Pair(1, -2),
			Pair(-1, -2),
			Pair(2, 1),
			Pair(-2, 1),
			Pair(2, -1),
			Pair(-2, -1)
	)

	override fun moves(board: Board, x: Int, y: Int): Collection<Move> {
		val moves = LinkedList<Move>()
		val color = board.colors[x, y]

		coords.forEach {
			val nx = x + it.first
			val ny = y + it.second
			if (nx in 0..7 && ny in 0..7) {
				if (board.pieces[nx, ny] == null || board.colors[nx, ny] != color) {
					moves.add(Move(x, y, nx, ny))
				}
			}
		}

		return moves
	}

}