package com.mthwate.xboard.test.piece

import com.mthwate.xboard.api.Move
import com.mthwate.xboard.test.Board
import java.util.*

/**
 * @author mthwate
 */
object Pawn : Piece {

	override val value = 1

	override fun moves(board: Board, x: Int, y: Int): Collection<Move> {
		val moves = LinkedList<Move>()
		val color = board.colors[x, y]
		val direction = if (color) 1 else -1
		val ny = y + direction

		val promote = ny !in 1..6

		if (board.pieces[x, ny] == null) {
			moves.add(Move(x, y, x, ny))
			if (promote) {
				moves.add(Move(x, y, x, ny, Queen))
				moves.add(Move(x, y, x, ny, Knight))
			}
			if ((if (color) 1 else 6) == y && board.pieces[x, y + (2 * direction)] == null) {
				moves.add(Move(x, y, x, y + (2 * direction)))
			}
		}

		if (x - 1 in 0..7 && board.pieces[x - 1, ny] != null && board.colors[x - 1, ny] != color) {
			moves.add(Move(x, y, x - 1, ny))
		}
		if (x + 1 in 0..7 && board.pieces[x + 1, ny] != null && board.colors[x + 1, ny] != color) {
			moves.add(Move(x, y, x + 1, ny))
		}

		//TODO en passant
		return moves
	}

}