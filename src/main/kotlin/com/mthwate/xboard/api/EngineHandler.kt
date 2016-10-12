package com.mthwate.xboard.api

import java.io.FileNotFoundException
import java.io.PrintWriter
import java.util.HashMap
import java.util.Scanner
import java.util.regex.Pattern

/**
 * @author mthwate
 */
class EngineHandler(engine: ChessEngine, issuer: CmdIssuer) : Runnable {

	private val handlers = HashMap<String, (String, String) -> Unit>()

	private var protover = 1

	var log = PrintWriter("engine.log")

	init {
		//we already expect 
		handlers.put("xboard", { cmd, params -> })

		handlers.put("protover", { cmd, params ->
			protover = params.toInt()
			val features = HashMap<String, String>()
			features.put("usermove", "1")
			features.put("ping", "1")
			features.put("done", "1")
			issuer.feature(features)
		})

		//if the features are accepted, great!
		handlers.put("accepted", { cmd, params -> })

		//if the features are rejected, we deal with it by always supporting defaults, no action needed
		handlers.put("rejected", { cmd, params -> })

		handlers.put("new", { cmd, params ->
			engine.resetBoard()
			engine.turn = Color.WHITE
			engine.playing = true
			engine.color = Color.BLACK
			engine.runClocks = false
			engine.resetClocks()
			engine.resetTimeControls() //TODO
			engine.useWallClock() //TODO
			engine.dontPonderThisMove() //TODO
			engine.removeSearchDepthLimit() //TODO
			engine.random = false
		})

		handlers.put("variant", { cmd, params -> throw UnsupportedOperationException() })

		handlers.put("quit", { cmd, params -> throw UnsupportedOperationException() })

		handlers.put("random", { cmd, params -> engine.random = !engine.random })

		handlers.put("force", { cmd, params ->
			engine.playing = false
			engine.runClocks = false
		})

		handlers.put("go", { cmd, params ->
			engine.playing = true
			engine.color = engine.turn
			engine.runClocks = true
			engine.startThinking()
		})

		handlers.put("playother", { cmd, params -> throw UnsupportedOperationException() })

		handlers.put("white", { cmd, params ->
			engine.turn = Color.WHITE
			engine.color = Color.BLACK
			engine.runClocks = false
		})

		handlers.put("black", { cmd, params ->
			engine.turn = Color.BLACK
			engine.color = Color.WHITE
			engine.runClocks = false
		})

		handlers.put("level", { cmd, params ->
			val split = params.split(" ")
			engine.movesPerTimeControl = split[0].toInt()
			engine.secInControl = split[1].let {
				val split = it.split(":")
				var time = split[0].toInt() * 60
				if (split.size == 2) {
					time += split[1].toInt()
				}
				time
			}
			engine.timeInc = split[2].toInt()
		})

		handlers.put("st", { cmd, params -> throw UnsupportedOperationException() })

		handlers.put("sd", { cmd, params -> throw UnsupportedOperationException() })

		handlers.put("nps", { cmd, params -> throw UnsupportedOperationException() })

		handlers.put("time", { cmd, params -> engine.setEngineClock(params.toLong() * 10000) })

		handlers.put("otim", { cmd, params -> engine.setOpponentClock(params.toLong() * 10000) })

		handlers.put("usermove", { cmd, params ->

			val move = if (Pattern.matches("([a-z]\\d+[a-z]\\d+)", params)) {
				Move(params[0].toInt() - 'a'.toInt(), params[1].toInt() - '1'.toInt(), params[2].toInt() - 'a'.toInt(), params[3].toInt() - '1'.toInt())
			} else {
				null
			}

			if (move == null) {
				issuer.error("ambiguous move", params)
			} else {
				engine.onMove(move)
			}

			//TODO Implementation incomplete
		})

		handlers.put("?", { cmd, params -> engine.moveNow() })

		handlers.put("ping", { cmd, params -> issuer.pong(params.toInt()) })

		handlers.put("draw", { cmd, params ->
			if (engine.onRequestDraw()) {
				issuer.offerDraw()
			}
		})

		handlers.put("result", { cmd, params -> throw UnsupportedOperationException() })

		handlers.put("setboard", { cmd, params -> throw UnsupportedOperationException() })

		handlers.put("edit", { cmd, params -> throw UnsupportedOperationException() })

		handlers.put("hint", { cmd, params -> throw UnsupportedOperationException() })

		handlers.put("bk", { cmd, params -> throw UnsupportedOperationException() })

		handlers.put("undo", { cmd, params -> throw UnsupportedOperationException() })

		handlers.put("remove", { cmd, params -> throw UnsupportedOperationException() })

		handlers.put("hard", { cmd, params -> engine.canPonder = true })

		handlers.put("easy", { cmd, params -> engine.canPonder = false })

		handlers.put("post", { cmd, params -> engine.post = true })

		handlers.put("nopost", { cmd, params -> engine.post = false })

		handlers.put("analyze", { cmd, params -> throw UnsupportedOperationException() })

		handlers.put("name", { cmd, params -> engine.opponentName = params })

		handlers.put("rating", { cmd, params -> throw UnsupportedOperationException() })

		handlers.put("ics", { cmd, params -> throw UnsupportedOperationException() })

		handlers.put("computer", { cmd, params -> })

		handlers.put("pause", { cmd, params -> throw UnsupportedOperationException() })

		handlers.put("resume", { cmd, params -> throw UnsupportedOperationException() })

		handlers.put("memory", { cmd, params -> throw UnsupportedOperationException() })

		handlers.put("cores", { cmd, params -> throw UnsupportedOperationException() })

		handlers.put("egtpath", { cmd, params -> throw UnsupportedOperationException() })

		handlers.put("options", { cmd, params -> throw UnsupportedOperationException() })

		handlers.put("exclude", { cmd, params -> throw UnsupportedOperationException() })

		handlers.put("include", { cmd, params -> throw UnsupportedOperationException() })

		handlers.put("setscore", { cmd, params -> throw UnsupportedOperationException() })

		handlers.put("lift", { cmd, params -> throw UnsupportedOperationException() })

		handlers.put("put", { cmd, params -> throw UnsupportedOperationException() })

		handlers.put("hover", { cmd, params -> throw UnsupportedOperationException() })

	}

	override fun run() {

		log.println("START")
		log.flush()

		val input = Scanner(System.`in`)

		try {
			while (true) {

				var line = input.nextLine()

				if (isMove(line)) {
					line = "usermove " + line
				}

				val split = line.split(" ", limit = 2)
				val cmdName = split[0]
				val params = if (split.size == 2) { split[1] } else { "" }

				log.println(line)
				log.flush()

				val handler = handlers.getOrElse(cmdName) {
					{ cmd, params -> println("Error (unknown command): $cmd $params") }
				}

				handler.invoke(cmdName, params)

			}
		} catch (e: Exception) {
			e.printStackTrace(log)
			log.flush()
		}

	}

	private fun isMove(line: String): Boolean {
		if (Pattern.matches("([a-z]\\d+[a-z]\\d+)", line)) {
			return true
		}

		return false
	}

}
