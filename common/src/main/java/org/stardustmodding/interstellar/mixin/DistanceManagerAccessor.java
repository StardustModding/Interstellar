package org.stardustmodding.interstellar.mixin;

import net.minecraft.server.world.ChunkTicketManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(ChunkTicketManager.class)
public interface DistanceManagerAccessor {
    @Accessor("simulationDistance")
    int getSimulationDistance();
}
