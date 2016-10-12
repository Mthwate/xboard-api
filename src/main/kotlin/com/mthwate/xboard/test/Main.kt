package com.mthwate.xboard.test

import com.mthwate.xboard.api.CmdIssuer
import com.mthwate.xboard.api.EngineHandler
import java.io.File
import java.util.concurrent.TimeUnit

/**
 * @author mthwate
 */
object Main {

	@JvmStatic fun main(args: Array<String>) {
		val handler = EngineHandler(TestEngine(CmdIssuer()), CmdIssuer())
		handler.run()
	}

}
