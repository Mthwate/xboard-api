package com.mthwate.xboard.test

import com.mthwate.xboard.api.Move
import com.mthwate.xboard.test.piece.*
import java.util.*

/**
 * @author mthwate
 */
class Board(val pieces: Array2D<Piece?>, val colors: Array2D<Boolean>) : Cloneable {

	constructor(): this(
			Array2D<Piece?>(8, 8) { x, y ->
				when (y) {
					1, 6 -> Pawn
					0, 7 -> when (x) {
						0, 7 -> Rook
						1, 6 -> Knight
						2, 5 -> Bishop
						3 -> Queen
						4 -> King
						else -> null
					}
					else -> null
				}
			},
			Array2D<Boolean>(8, 8) { x, y -> (y < 4) }
	)

	fun move(move: Move): Board {
		val clone = clone()
		clone.pieces[move.fx, move.fy] = move.piece ?: clone.pieces[move.ix, move.iy]
		clone.colors[move.fx, move.fy] = clone.colors[move.ix, move.iy]
		clone.pieces[move.ix, move.iy] = null
		return clone
	}

	override fun clone(): Board {
		return Board(pieces.clone(), colors.clone())
	}

	fun getMoves(white: Boolean): List<Move> {
		val moves = LinkedList<Move>()
		pieces.array.forEachIndexed { i, piece ->
			if (piece != null && colors.array[i] == white) {
				piece.moves(this, i % 8, i / 8).forEach {
					if (!this.move(it).inCheck(white)) {
						moves.add(it)
					}
				}
			}
		}
		return moves
	}

	fun inCheck(white: Boolean): Boolean {
		var kx = 0
		var ky = 0
		for (x in 0..7) {
			for (y in 0..7) {
				if (pieces[x, y] == King && colors[x, y] == white) {
					kx = x
					ky = y
					//TODO break?
				}
			}
		}

		checkThreatLine(kx, ky, 1, 0, arrayOf(Queen, Rook))
		checkThreatLine(kx, ky, -1, 0, arrayOf(Queen, Rook))
		checkThreatLine(kx, ky, 0, 1, arrayOf(Queen, Rook))
		checkThreatLine(kx, ky, 0, -1, arrayOf(Queen, Rook))

		checkThreatLine(kx, ky, 1, 1, arrayOf(Queen, Bishop))
		checkThreatLine(kx, ky, 1, -1, arrayOf(Queen, Bishop))
		checkThreatLine(kx, ky, -1, 1, arrayOf(Queen, Bishop))
		checkThreatLine(kx, ky, -1, -1, arrayOf(Queen, Bishop))

		Knight.coords.forEach {
			val nx = kx + it.first
			val ny = ky + it.second
			if (nx in 0..7 && ny in 0..7) {
				if (pieces[nx, ny] == Knight && colors[nx, ny] != white) {
					return true
				}
			}
		}


		val pi = if (white) 1 else -1
		if (ky + pi in 0..7) {
			if (kx + 1 in 0..7 && colors[kx + 1, ky + pi] != white && pieces[kx + 1, ky + pi] == Pawn) {
				return true
			}
			if (kx - 1 in 0..7 && colors[kx - 1, ky + pi] != white && pieces[kx - 1, ky + pi] == Pawn) {
				return true
			}
		}

		//TODO en passant
		//TODO king

		//TODO
		return false
	}

	/**
	 * Checks for a threat to the king in a straight line.
	 * Used to check if the king is in check by a queen, rook, or bishop.
	 *
	 * @param kx The x position of the king
	 * @param ky The y position of the king
	 * @param ix The amount to increment the x position by (should be 1, -1, or 0)
	 * @param iy The amount to increment the y position by (should be 1, -1, or 0)
	 * @param check An array of the pieces to check for in the line
	 * @return True if the king is in check (in the specified line by the specified pieces), false if not
	 */
	private fun checkThreatLine(kx: Int, ky: Int, ix: Int, iy: Int, check: Array<Piece>): Boolean {
		for (i in 1..7) {
			val cx = kx + (ix * i)
			val cy = ky + (iy * i)
			if (!MoveUtils.onBoard(cx, cy)) {
				return false
			} else if (pieces[cx, cy] != null) {
				if (colors[kx, ky] == colors[cx, cy]) {
					return false
				} else {
					return check.contains(pieces[cx, cy])
				}
			}
		}
		return false
	}

	override fun toString(): String {
		val sb = StringBuilder()

		for (y in 0..7) {
			for (x in 0..7) {
				sb.append(when (pieces[x, 7-y]) {
					Pawn -> 'P'
					King -> 'K'
					Queen -> 'Q'
					Knight -> 'N'
					Bishop -> 'B'
					Rook -> 'R'
					else -> 'X'
				})
			}
			sb.appendln()
		}

		return sb.toString()
	}

	fun getValue(white: Boolean): Int {
		var value = 0
		pieces.array.forEachIndexed { i, piece ->
			if (piece != null) {
				value += piece.value * if (colors.array[i] == white) 1 else -1
			}
		}
		return value
	}

}