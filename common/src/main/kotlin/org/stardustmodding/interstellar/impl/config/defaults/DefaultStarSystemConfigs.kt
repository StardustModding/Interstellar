package org.stardustmodding.interstellar.impl.config.defaults

import org.stardustmodding.interstellar.api.builder.StarSystemConfigBuilder
import org.stardustmodding.interstellar.impl.Interstellar

object DefaultStarSystemConfigs {
    val SOL =
            StarSystemConfigBuilder(Interstellar.MOD_ID, "sol")
                    .id(1000)
                    .planet(1001) // mercury
                    .planet(1002) // venus
                    .planet(0) // earth
                    .planet(1003) // moon
                    .planet(1004) // mars
                    .planet(1005) // saturn
                    .planet(1006) // urnaus
                    .planet(1007) // neptune
                    .planet(1008) // pluto
                    .build()
}
