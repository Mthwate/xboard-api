package com.mthwate.xboard.api

/**
 * @author mthwate
 */
class CmdIssuer {

	fun feature(features: Map<String, String>) {
		val sb = StringBuilder()
		sb.append("feature")
		for ((key, value) in features) {
			sb.append(" ")
			sb.append(key)
			sb.append("=")
			sb.append(value)
		}
		issue(sb)
	}

	fun illegalMove(move: Move, reason: String? = null) {
		val sb = StringBuilder()
		sb.append("illegal move")
		if (reason != null) {
			sb.append(" ($reason)")
		}
		sb.append(": $move")
		issue(sb)
	}

	fun error(type: String, command: String) {
		issue("error ($type): $command")
	}

	fun move(move: Move) {
		issue("move $move")
	}

	fun result(result: Result, comment: String) {
		issue("$result {$comment}")
	}

	fun resign() {
		issue("resign")
	}

	fun offerDraw() {
		issue("offer draw")
	}

	fun pong(n: Int) {
		issue("pong $n")
	}

	fun tellOpponent(message: String) {
		issue("tellopponent $message")
	}

	fun tellOthers(message: String) {
		val sb = StringBuilder()
		sb.append("tellothers ")
		sb.append(message)
		issue(sb)
	}

	fun tellAll(message: String) {
		val sb = StringBuilder()
		sb.append("tellall ")
		sb.append(message)
		issue(sb)
	}

	fun tellUser(message: String) {
		val sb = StringBuilder()
		sb.append("telluser ")
		sb.append(message)
		issue(sb)
	}

	fun tellUserError(message: String) {
		val sb = StringBuilder()
		sb.append("tellusererror ")
		sb.append(message)
		issue(sb)
	}

	fun askUser(reptag: String, message: String) {
		assertNoWhitespace(reptag)
		val sb = StringBuilder()
		sb.append("askuser ")
		sb.append(reptag)
		sb.append(" ")
		sb.append(message)
		issue(sb)
	}

	fun tellIcs(message: String) {
		val sb = StringBuilder()
		sb.append("tellics ")
		sb.append(message)
		issue(sb)
	}

	fun tellIcsNoAlias(message: String) {
		val sb = StringBuilder()
		sb.append("tellicsnoalias ")
		sb.append(message)
		issue(sb)
	}

	fun comment(comment: String) {
		val sb = StringBuilder()
		sb.append("# ")
		sb.append(comment)
		issue(sb)
	}

	private fun issue(sb: StringBuilder) {
		issue(sb.toString())
	}

	private fun issue(str: String) {
		assertNoNewline(str)
		println(str)
	}

	private fun assertNoWhitespace(str: String) {
		if (str.matches("\\s".toRegex())) {
			throw IllegalArgumentException("Cannot contain whitespace")
		}
	}

	private fun assertNoNewline(str: String) {
		if (str.contains(NEWLINE)) {
			throw IllegalArgumentException("Cannot contain newline")
		}
	}

	companion object {

		private val NEWLINE = System.getProperty("line.separator")
	}

}
