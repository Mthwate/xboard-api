package com.mthwate.xboard.api

/**
 * @author mthwate
 */
enum class Color {

	WHITE,
	BLACK;

	operator fun not(): Color {
		return when(this) {
			WHITE -> BLACK
			BLACK -> WHITE
		}
	}

	fun isWhite(): Boolean {
		return this == Color.WHITE
	}

}