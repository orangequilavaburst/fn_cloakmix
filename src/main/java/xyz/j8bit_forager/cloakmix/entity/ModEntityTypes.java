package xyz.j8bit_forager.cloakmix.entity;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import xyz.j8bit_forager.cloakmix.CloakMix;
import xyz.j8bit_forager.cloakmix.entity.client.custom.SanguineDaggerProjectile;

public class ModEntityTypes {

    public static DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, CloakMix.MOD_ID);

    public static RegistryObject<EntityType<SanguineDaggerProjectile>> SANGUINE_DAGGER_PROJECTILE =
            ENTITY_TYPES.register("sanguine_dagger_projectile",
                    () -> EntityType.Builder.of((EntityType.EntityFactory<SanguineDaggerProjectile>)SanguineDaggerProjectile::new, MobCategory.MISC).build("sanguine_dagger_projectile"));

    public static void register(IEventBus modEventBus) {

        ENTITY_TYPES.register(modEventBus);

    }
}
