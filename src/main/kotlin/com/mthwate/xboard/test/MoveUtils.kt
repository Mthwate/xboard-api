package com.mthwate.xboard.test

import com.mthwate.xboard.api.Move
import com.mthwate.xboard.test.Board
import java.util.*

/**
 * @author mthwate
 */
object MoveUtils {

	fun moveLine(board: Board, moves: LinkedList<Move>, x: Int, y: Int, ix: Int, iy: Int) {
		var nx = x
		var ny = y
		do {
			nx += ix
			ny += iy
		} while (tryMove(board, moves, x, y, nx, ny))
	}

	private fun tryMove(board: Board, moves: LinkedList<Move>, x: Int, y: Int, nx: Int, ny: Int): Boolean {
		if (!onBoard(nx, ny)) {
			return false
		} else if (board.pieces[nx, ny] == null) {
			moves.add(Move(x, y, nx, ny))
			return true
		} else {
			if (board.colors[x, y] != board.colors[nx, ny]) {
				moves.add(Move(x, y, nx, ny))
			}
			return false
		}
	}

	fun onBoard(vararg i: Int): Boolean {
		i.forEach {
			if (it !in 0..7) {
				return false
			}
		}
		return true
	}

}