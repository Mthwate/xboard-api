package com.mthwate.xboard.test

import com.mthwate.xboard.api.ChessEngine
import com.mthwate.xboard.api.CmdIssuer
import com.mthwate.xboard.api.Move

/**
 * @author mthwate
 */
class TestEngine(val issuer: CmdIssuer) : ChessEngine() {

	var board = Board()

	override fun onMove(move: Move) {
		board = board.move(move)
		startThinking()
	}

	override fun startThinking() {
		var bestMove: Move = Move(-1, -1, -1, -1)
		var bestVal: Int = Int.MIN_VALUE

		board.getMoves().forEach {
			val minimax = Minimax().run(board.move(it))
			if (minimax > bestVal) {
				bestMove = it
				bestVal = minimax
			}
		}

		issuer.move(bestMove)
		board = board.move(bestMove)
	}
	
	override fun resetBoard() {
		board = Board()
	}

	override fun onRequestDraw(): Boolean = false

	override fun moveNow() {
		throw UnsupportedOperationException("not implemented")
	}

	override fun resetTimeControls() {
		//throw UnsupportedOperationException("not implemented")
	}

	override fun useWallClock() {
		//throw UnsupportedOperationException("not implemented")
	}

	override fun dontPonderThisMove() {
		//throw UnsupportedOperationException("not implemented")
	}

	override fun removeSearchDepthLimit() {
		//throw UnsupportedOperationException("not implemented")
	}

}