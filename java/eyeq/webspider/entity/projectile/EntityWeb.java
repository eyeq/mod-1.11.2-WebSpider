package eyeq.webspider.entity.projectile;

import eyeq.util.world.WorldUtils;
import net.minecraft.block.SoundType;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class EntityWeb extends EntityThrowable {
    public EntityWeb(World world) {
        super(world);
    }

    public EntityWeb(World world, EntityLivingBase thrower) {
        super(world, thrower);
    }

    public EntityWeb(World world, double x, double y, double z) {
        super(world, x, y, z);
    }

    @Override
    protected void onImpact(RayTraceResult result) {
        if(this.world.isRemote) {
            return;
        }

        EntityPlayer thrower = null;
        if(this.getThrower() instanceof EntityPlayer) {
            thrower = (EntityPlayer) this.getThrower();
        } else if(!world.getGameRules().getBoolean("mobGriefing")) {
            return;
        }
        BlockPos pos = result.entityHit == null ? this.getPosition() : result.entityHit.getPosition();
        if(!Blocks.WEB.canPlaceBlockAt(this.world, pos)) {
            return;
        }
        SoundType soundType = Blocks.WEB.getSoundType(null, world, pos, this);
        WorldUtils.playSoundBlocks(world, thrower, pos, soundType);
        this.world.setBlockState(pos, Blocks.WEB.getDefaultState());
        this.setDead();
    }
}
