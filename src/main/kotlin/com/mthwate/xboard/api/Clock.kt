package com.mthwate.xboard.api

import java.util.concurrent.TimeUnit

class Clock(private var nano: Long) {

	private var start = 0L

	private var isRunning = false

	fun start() {
		if (!isRunning) {
			start = System.nanoTime()
			isRunning = true
		}
	}

	fun stop() {
		if (isRunning) {
			nano -= System.nanoTime() - start
			isRunning = false
		}
	}

	fun getRemaining(unit: TimeUnit): Long {
		return unit.convert(nano, TimeUnit.NANOSECONDS)
	}

}
