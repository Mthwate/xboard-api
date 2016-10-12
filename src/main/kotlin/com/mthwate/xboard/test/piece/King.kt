package com.mthwate.xboard.test.piece

import com.mthwate.xboard.api.Move
import com.mthwate.xboard.test.Board
import java.util.*

/**
 * @author mthwate
 */
object King : Piece {

	override val value = 1000

	override fun moves(board: Board, x: Int, y: Int): Collection<Move> {
		val moves = LinkedList<Move>()
		val color = board.colors[x, y]
		for (ix in -1..1) {
			for (iy in -1..1) {
				if (ix != 0 || iy != 0) {
					val nx = x + ix
					val ny = y + iy
					if (nx in 0..7 && ny in 0..7) {
						if (board.pieces[nx, ny] == null || board.colors[nx, ny] != color) {
							moves.add(Move(x, y, nx, ny))
						}
					}
				}
			}
		}
		//TODO castle
		return moves
	}

}