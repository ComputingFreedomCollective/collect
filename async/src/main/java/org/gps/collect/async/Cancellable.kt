package org.gps.collect.async

interface Cancellable {
    fun cancel(): Boolean
}
