package eyeq.webspider.entity.monster;

import eyeq.util.world.WorldUtils;
import eyeq.webspider.WebSpider;
import eyeq.webspider.entity.projectile.EntityWeb;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackRanged;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class EntityWebSpider extends EntitySpider implements IRangedAttackMob {
    public EntityWebSpider(World worldIn) {
        super(worldIn);
        this.setSize(1.8F, 0.9F);
    }

    @Override
    protected void initEntityAI() {
        super.initEntityAI();
        this.tasks.addTask(2, new EntityAIAttackRanged(this, 1.25, 120, 20.0F));
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(10.0);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.2);
    }

    @Override
    public void attackEntityWithRangedAttack(EntityLivingBase target, float speed) {
        if(this.getDistanceSq(target.posX, target.getEntityBoundingBox().minY, target.posZ) < 4.5) {
            this.attackEntityAsMob(target);
            return;
        }
        EntityWeb web = new EntityWeb(this.world, this);
        double x = target.posX - this.posX;
        double y = target.posY + target.getEyeHeight() - 1.1;
        double z = target.posZ - this.posZ;
        float f = MathHelper.sqrt(x * x + z * z) * 0.2F;
        web.setThrowableHeading(x, y + f - web.posY, z, 1.6F, 12.0F);
        WorldUtils.playSound(world, this, WebSpider.entityWebThrow, rand);
        this.world.spawnEntity(web);
    }
}
