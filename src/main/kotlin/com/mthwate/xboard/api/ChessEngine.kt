package com.mthwate.xboard.api

/**
 * @author mthwate
 */
abstract class ChessEngine {

	var color = Color.BLACK

	var turn = Color.WHITE

	var whiteClock = Clock(0)

	var blackClock = Clock(0)

	var canPonder = false

	var random = false

	var opponentName: String? = null

	var movesPerTimeControl = 0

	var secInControl = 0

	var timeInc = 0

	var post = false

	var playing = true

	var runClocks = false
		set(value) {
			//val action = if (value) Clock::start else Clock::stop
			val clock = when (turn) {
				Color.WHITE -> whiteClock
				Color.BLACK -> blackClock
			}

			if (value) clock.start() else clock.stop()

			value
		}

	fun setEngineClock(nano: Long) {
		when (color) {
			Color.WHITE -> whiteClock = Clock(nano)
			Color.BLACK -> blackClock = Clock(nano)
		}
	}

	fun setOpponentClock(nano: Long) {
		when (color) {
			Color.WHITE -> blackClock = Clock(nano)
			Color.BLACK -> whiteClock = Clock(nano)
		}
	}

	fun resetClocks() {
		whiteClock = Clock(0)
		blackClock = Clock(0)
	}

	abstract fun resetBoard()

	abstract fun onRequestDraw(): Boolean

	abstract fun moveNow()

	abstract fun resetTimeControls() //TODO

	abstract fun useWallClock() //TODO

	abstract fun dontPonderThisMove() //TODO

	abstract fun removeSearchDepthLimit() //TODO

	abstract fun onMove(move: Move)

	abstract fun startThinking()

}
