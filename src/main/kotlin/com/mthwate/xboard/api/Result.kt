package com.mthwate.xboard.api

/**
 * @author mthwate
 */
enum class Result constructor(private val text: String) {

	WHITE_WIN("1-0"),
	BLACK_WIN("0-1"),
	TIE("1/2-1/2");

	override fun toString(): String = text

}
