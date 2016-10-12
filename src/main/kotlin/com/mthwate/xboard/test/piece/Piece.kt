package com.mthwate.xboard.test.piece

import com.mthwate.xboard.api.Move
import com.mthwate.xboard.test.Board

/**
 * @author mthwate
 */
interface Piece {

	val value: Int

	fun moves(board: Board, x: Int, y: Int): Collection<Move>

}