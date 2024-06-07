package org.stardustmodding.interstellar.mixin;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.stardustmodding.interstellar.api.block.BlockUtil;
import org.stardustmodding.interstellar.api.math.McExtensions;
import org.stardustmodding.interstellar.api.physics.Physics;
import physx.common.PxIDENTITYEnum;
import physx.common.PxTransform;
import physx.physics.PxRigidDynamic;

@Mixin(AbstractBlock.AbstractBlockState.class)
public class BlockMixin {
    @Unique
    private PxRigidDynamic interstellar$body = null;

    @Inject(method = "onBlockAdded", at = @At("RETURN"))
    public void onBlockAdded(World world, BlockPos pos, BlockState state, boolean notify, CallbackInfo ci) {
        interstellar$updateCollider(state, world, pos);
    }

    @Inject(method = "onStateReplaced", at = @At("RETURN"))
    public void onStateReplaced(World world, BlockPos pos, BlockState state, boolean notify, CallbackInfo ci) {
        interstellar$updateCollider(state, world, pos);
    }

    @Unique
    private void interstellar$updateCollider(BlockState state, World world, BlockPos pos) {
        assert Physics.physics != null;
        assert Physics.scene != null;

        if (interstellar$body != null) {
            Physics.scene.removeActor(interstellar$body);
            interstellar$body = null;
        }

        var geom = BlockUtil.getBlockBounds(state, world, pos).asGeometry();
        var shape = Physics.physics.createShape(geom, Physics.material, true, Physics.shapeFlags);
        var transform = new PxTransform(PxIDENTITYEnum.PxIdentity);
        var vec = McExtensions.INSTANCE.toPx(pos);

        transform.setP(vec);

        interstellar$body = Physics.physics.createRigidDynamic(transform);
        interstellar$body.attachShape(shape);

        Physics.scene.addActor(interstellar$body);
    }
}
