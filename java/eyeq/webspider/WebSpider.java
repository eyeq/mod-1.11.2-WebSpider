package eyeq.webspider;

import com.google.common.collect.Lists;
import eyeq.util.client.renderer.ResourceLocationFactory;
import eyeq.util.client.resource.ULanguageCreator;
import eyeq.util.client.resource.USoundCreator;
import eyeq.util.client.resource.gson.SoundResourceManager;
import eyeq.util.client.resource.lang.LanguageResourceManager;
import eyeq.util.common.registry.UEntityRegistry;
import eyeq.util.common.registry.USoundEventRegistry;
import eyeq.util.world.biome.BiomeUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderSnowball;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import eyeq.webspider.entity.projectile.EntityWeb;
import eyeq.webspider.entity.monster.EntityWebSpider;
import eyeq.webspider.client.renderer.entity.RenderWebSpider;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.io.File;
import java.util.List;

import static eyeq.webspider.WebSpider.MOD_ID;

@Mod(modid = MOD_ID, version = "1.0", dependencies = "after:eyeq_util")
public class WebSpider {
    public static final String MOD_ID = "eyeq_webspider";

    @Mod.Instance(MOD_ID)
    public static WebSpider instance;

    private static final ResourceLocationFactory resource = new ResourceLocationFactory(MOD_ID);

    public static SoundEvent entityWebThrow;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        registerEntities();
        registerSoundEvents();
        if(event.getSide().isServer()) {
            return;
        }
        registerEntityRenderings();
        createFiles();
    }

    public static void registerEntities() {
        UEntityRegistry.registerModEntity(resource, EntityWeb.class, "Web", 0, instance);

        EntityList.EntityEggInfo egg = EntityList.ENTITY_EGGS.get(new ResourceLocation("spider"));
        UEntityRegistry.registerModEntity(resource, EntityWebSpider.class, "WebSpider", 1, instance, egg);
        List<Biome> biomes = BiomeUtils.getSpawnBiomes(EntitySpider.class, EnumCreatureType.MONSTER);
        EntityRegistry.addSpawn(EntityWebSpider.class, 5, 2, 4, EnumCreatureType.MONSTER, biomes.toArray(new Biome[0]));
    }

    public static void registerSoundEvents() {
        entityWebThrow = new SoundEvent(resource.createResourceLocation("entityWebThrow"));

        USoundEventRegistry.registry(entityWebThrow);
    }

    @SideOnly(Side.CLIENT)
    public static void registerEntityRenderings() {
        RenderingRegistry.registerEntityRenderingHandler(EntityWeb.class, manager -> new RenderSnowball(manager, Item.getItemFromBlock(Blocks.WEB), Minecraft.getMinecraft().getRenderItem()));

        RenderingRegistry.registerEntityRenderingHandler(EntityWebSpider.class, RenderWebSpider::new);
    }

    public static void createFiles() {
        File project = new File("../1.11.2-WebSpider");

        LanguageResourceManager language = new LanguageResourceManager();

        language.register(LanguageResourceManager.EN_US, EntityWeb.class, "Web");
        language.register(LanguageResourceManager.JA_JP, EntityWeb.class, "クモの巣");
        language.register(LanguageResourceManager.EN_US, EntityWebSpider.class, "Web Spider");
        language.register(LanguageResourceManager.JA_JP, EntityWebSpider.class, "ウェブスパイダー");

        ULanguageCreator.createLanguage(project, MOD_ID, language);

        SoundResourceManager sound = new SoundResourceManager();

        sound.register(entityWebThrow, SoundCategory.PLAYERS.getName(), Lists.newArrayList("random/bow"));

        USoundCreator.createSoundJson(project, MOD_ID, sound);
    }
}
