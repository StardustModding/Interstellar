package dev.niostone.interstellar;

import dev.architectury.injectables.annotations.ExpectPlatform;
import dev.architectury.platform.Platform;

import java.nio.file.Path;

public class ModExpectPlatform {
    @ExpectPlatform
    public static Path getConfigDirectory() {
        throw new AssertionError();
    }
}
