package dev.niostone.interstellar.config.defaults;

import dev.niostone.interstellar.Interstellar;
import dev.niostone.interstellar.config.GasConfig;
import dev.niostone.interstellar.config.PlanetConfig;
import dev.niostone.interstellar.config.builder.PlanetConfigBuilder;

/**
 * Quick note: If this is off, sorry, I am trying to be
 *             scientifically correct here. This was what
 *             I could find as of October 10, 2023.
 *             - RedstoneWizard08
 */
public class DefaultPlanetConfigs {
    public static final PlanetConfig MERCURY =
        new PlanetConfigBuilder(Interstellar.MOD_ID, "mercury")
            .enable(true)
            .dimension(1001)
            // Number is actually about 0.0000000001 according to my research,
            // but it's basically 0, so I'm not going to bother.
            .withPressure(0.0f)
            // TODO: Put something here, I can't find anything.
            .withRadiation(0.0f)
            .oxygen(true)
            .pressure(true)
            .radiation(true)
            .tier(4)
            .build();

    public static final PlanetConfig VENUS =
        new PlanetConfigBuilder(Interstellar.MOD_ID, "venus")
            .enable(true)
            .dimension(1002)
            .withPressure(9100.0f)
            .withRadiation(0.00041666666f)
            // 97% carbon dioxide, 3% nitrogen
            .gas(new GasConfig(Interstellar.MOD_ID, "carbon_dioxide", 8827.0f))
            .gas(new GasConfig(Interstellar.MOD_ID, "nitrogen", 273.0f))
            .oxygen(true)
            .pressure(true)
            .radiation(true)
            .tier(3)
            .build();

    public static final PlanetConfig EARTH =
        new PlanetConfigBuilder(Interstellar.MOD_ID, "earth")
            .enable(true)
            .dimension(0)
            .withPressure(100.0f)
            .withRadiation(0.0f)
            // 78% nitrogen, 20.95% oxygen, 1% argon, 0.05% carbon dioxide
            .gas(new GasConfig(Interstellar.MOD_ID, "nitrogen", 78.0f))
            .gas(new GasConfig(Interstellar.MOD_ID, "oxygen", 20.95f))
            .gas(new GasConfig(Interstellar.MOD_ID, "argon", 1.0f))
            .gas(new GasConfig(Interstellar.MOD_ID, "carbon_dioxide", 0.05f))
            .oxygen(true)
            .pressure(true)
            // No radiation on earth
            .radiation(false)
            // Anything can access this from any tier
            .tier(0)
            .build();
    
    public static final PlanetConfig MOON =
        new PlanetConfigBuilder(Interstellar.MOD_ID, "moon")
            .enable(true)
            .dimension(1003)
            .withPressure(0.0f)
            .withRadiation(0.00083333333f)
            .oxygen(true)
            .pressure(true)
            .radiation(true)
            .tier(1)
            .build();
    
    public static final PlanetConfig MARS =
        new PlanetConfigBuilder(Interstellar.MOD_ID, "mars")
            .enable(true)
            .dimension(1004)
            .withPressure(0.6f)
            .withRadiation(0.00106481f)
            // 95% carbon dioxide, 3% nitrogen, 1.6% argon, 0.4% methane
            .gas(new GasConfig(Interstellar.MOD_ID, "carbon_dioxide", 0.57f))
            .gas(new GasConfig(Interstellar.MOD_ID, "nitrogen", 0.018f))
            .gas(new GasConfig(Interstellar.MOD_ID, "argon", 0.0096f))
            .gas(new GasConfig(Interstellar.MOD_ID, "methane", 0.0024f))
            .oxygen(true)
            .pressure(true)
            .radiation(true)
            .tier(2)
            .build();
    
    public static final PlanetConfig SATURN =
        new PlanetConfigBuilder(Interstellar.MOD_ID, "saturn")
            .enable(true)
            .dimension(1005)
            .withPressure(140.0f)
            // I couldn't find anything. :(
            .withRadiation(0.0f)
            // 75% hydrogen, 25% helium
            .gas(new GasConfig(Interstellar.MOD_ID, "hydrogen", 105.0f))
            .gas(new GasConfig(Interstellar.MOD_ID, "helium", 35.0f))
            .oxygen(true)
            .pressure(true)
            .radiation(true)
            .tier(5)
            .build();
    
    public static final PlanetConfig URANUS =
        new PlanetConfigBuilder(Interstellar.MOD_ID, "uranus")
            .enable(true)
            .dimension(1006)
            .withPressure(100000.0f)
            // I couldn't find anything. :(
            .withRadiation(0.0f)
            // 60% hydrogen, 38% helium, 2% ammonia (based on estimates)
            .gas(new GasConfig(Interstellar.MOD_ID, "hydrogen", 60000.0f))
            .gas(new GasConfig(Interstellar.MOD_ID, "helium", 38000.0f))
            .gas(new GasConfig(Interstellar.MOD_ID, "ammonia", 2000.0f))
            .oxygen(true)
            .pressure(true)
            .radiation(true)
            .tier(6)
            .build();
    
    public static final PlanetConfig NEPTUNE =
        new PlanetConfigBuilder(Interstellar.MOD_ID, "neptune")
            .enable(true)
            .dimension(1007)
            .withPressure(10000000.0f)
            // I couldn't find anything. :(
            .withRadiation(0.0f)
            // 60% hydrogen, 38% helium, 2% methane (based on estimates)
            .gas(new GasConfig(Interstellar.MOD_ID, "hydrogen", 6000000.0f))
            .gas(new GasConfig(Interstellar.MOD_ID, "helium", 3800000.0f))
            .gas(new GasConfig(Interstellar.MOD_ID, "methane", 200000.0f))
            .oxygen(true)
            .pressure(true)
            .radiation(true)
            .tier(7)
            .build();
    
    public static final PlanetConfig PLUTO =
        new PlanetConfigBuilder(Interstellar.MOD_ID, "pluto")
            .enable(true)
            .dimension(1008)
            // It's almost 0, so whatever.
            .withPressure(0.0f)
            // I couldn't find anything. :(
            .withRadiation(0.0f)
            .oxygen(true)
            .pressure(true)
            .radiation(true)
            .tier(8)
            .build();
}
