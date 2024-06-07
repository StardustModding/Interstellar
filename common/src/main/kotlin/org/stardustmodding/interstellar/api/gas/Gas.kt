package org.stardustmodding.interstellar.api.gas

import kotlinx.serialization.Serializable
import net.minecraft.text.Text

@Serializable
class Gas(val id: Text, val breathable: Boolean = false, val toxic: Boolean = false)
