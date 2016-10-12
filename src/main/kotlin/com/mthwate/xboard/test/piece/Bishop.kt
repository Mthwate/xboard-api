package com.mthwate.xboard.test.piece

import com.mthwate.xboard.api.Move
import com.mthwate.xboard.test.Board
import com.mthwate.xboard.test.MoveUtils
import java.util.*

/**
 * @author mthwate
 */
object Bishop : Piece {

	override val value = 3

	override fun moves(board: Board, x: Int, y: Int): Collection<Move> {
		val moves = LinkedList<Move>()

		MoveUtils.moveLine(board, moves, x, y, 1, 1)
		MoveUtils.moveLine(board, moves, x, y, -1, 1)
		MoveUtils.moveLine(board, moves, x, y, 1, -1)
		MoveUtils.moveLine(board, moves, x, y, -1, -1)

		return moves
	}

}