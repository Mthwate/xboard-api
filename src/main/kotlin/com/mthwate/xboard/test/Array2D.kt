package com.mthwate.xboard.test

import java.util.*

/**
 * @author mthwate
 */
class Array2D<T>(val i: Int, val array: Array<T>): Cloneable {

	companion object {

		inline operator fun <reified T> invoke() = Array2D(0, emptyArray<T>())

		inline operator fun <reified T> invoke(x: Int, y: Int) = Array2D(x, arrayOfNulls<T>(x * y))

		inline operator fun <reified T> invoke(x: Int, y: Int, operator: (Int, Int) -> (T)) = Array2D(x, Array(x * y) { operator(it % x, it / x) } )

	}

	operator fun get(x: Int, y: Int): T {
		return array[i * y + x]
	}

	operator fun set(x: Int, y: Int, t: T) {
		array[i * y + x] = t
	}

	override public fun clone(): Array2D<T> {
		return Array2D<T>(i, Arrays.copyOf(array, array.size))
	}

}