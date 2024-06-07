package org.stardustmodding.interstellar.api.gas

import kotlinx.serialization.Serializable

@Serializable
class Gas(val breathable: Boolean = false, val toxic: Boolean = false)
