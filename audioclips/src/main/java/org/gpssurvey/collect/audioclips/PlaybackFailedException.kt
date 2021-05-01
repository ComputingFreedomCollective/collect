package org.gpssurvey.collect.audioclips

data class PlaybackFailedException(val uRI: String, val exceptionMsg: Int) : Exception()
