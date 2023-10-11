package dev.niostone.interstellar.config.defaults;

import dev.niostone.interstellar.Interstellar;
import dev.niostone.interstellar.config.StarSystemConfig;
import dev.niostone.interstellar.config.builder.StarSystemConfigBuilder;

public class DefaultStarSystemConfigs {
    public static final StarSystemConfig SOL =
        new StarSystemConfigBuilder(Interstellar.MOD_ID, "sol")
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
            .build();
}
