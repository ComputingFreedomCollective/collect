package org.gpssurvey.collect.async

interface Cancellable {
    fun cancel(): Boolean
}
