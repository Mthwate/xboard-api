package com.mthwate.xboard.test.piece

import com.mthwate.xboard.api.Move
import com.mthwate.xboard.test.Board
import com.mthwate.xboard.test.MoveUtils
import java.util.*

/**
 * @author mthwate
 */
object Rook : Piece {

	override val value = 5

	override fun moves(board: Board, x: Int, y: Int): Collection<Move> {
		val moves = LinkedList<Move>()

		MoveUtils.moveLine(board, moves, x, y, 1, 0)
		MoveUtils.moveLine(board, moves, x, y, -1, 0)
		MoveUtils.moveLine(board, moves, x, y, 0, 1)
		MoveUtils.moveLine(board, moves, x, y, 0, -1)

		return moves
	}

}