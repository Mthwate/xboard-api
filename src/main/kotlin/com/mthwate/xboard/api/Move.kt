package com.mthwate.xboard.api

import com.mthwate.xboard.test.piece.Piece

/**
 * @author mthwate
 */
class Move(val ix: Int, val iy: Int, val fx: Int, val fy: Int, val piece: Piece? = null) {

	override fun toString(): String {
		val ixc = (ix + 'a'.toInt()).toChar()
		val fxc = (fx + 'a'.toInt()).toChar()
		return "$ixc${iy+1}$fxc${fy+1}"
	}

}
