package com.mthwate.xboard.test

import com.mthwate.xboard.api.CmdIssuer
import com.mthwate.xboard.api.EngineHandler

/**
 * @author mthwate
 */
object Main {

	@JvmStatic fun main(args: Array<String>) {
		val handler = EngineHandler(TestEngine(CmdIssuer()), CmdIssuer())
		handler.run()
	}

}
