package com.github.abigailfails.modicum.core;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;
import org.apache.commons.lang3.tuple.Pair;

import java.lang.reflect.Field;

public class ModicumConfig {

    public static class Common {
        public final ConfigValue<Boolean> hotbarArmorSwapping;
        public final ConfigValue<Boolean> cauldronDispenserBehavior;
        public final ConfigValue<Boolean> jukeboxDispenserBehavior;
        public final ConfigValue<Boolean> railScaffoldingBehavior;
        public final ConfigValue<Boolean> tntDropping;
        public final ConfigValue<Boolean> tntDefusing;
        public final ConfigValue<Boolean> noPotionShift;

        public final ConfigValue<Integer> tntDroppingCooldown;
        public final ConfigValue<Integer> tntDefusingCooldown;

        public Common(ForgeConfigSpec.Builder builder) {
            hotbarArmorSwapping = builder
                    .comment("Right clicking an armour item in the hotbar swaps it with the currently equipped item")
                    .translation(Modicum.MOD_ID + ".config.hotbarArmorSwappingc")
                    .define("Hotbar armor swapping", true);
            cauldronDispenserBehavior = builder
                    .comment("Dispensers facing towards a cauldron can take water in and out with buckets and bottles")
                    .translation(Modicum.MOD_ID + ".config.cauldronDispenserBehavior")
                    .define("Cauldron dispenser behavior", true);
            jukeboxDispenserBehavior = builder
                    .comment("Dispensers facing towards a jukebox can place records into it")
                    .translation(Modicum.MOD_ID + ".config.jukeboxDispenserBehavior")
                    .define("Jukebox dispenser behavior", true);
            railScaffoldingBehavior = builder
                    .comment("Rails can be placed in the direction you're looking at by clicking on another rail, similar to scaffolding")
                    .translation(Modicum.MOD_ID + "config.railScaffoldingBehavior")
                    .define("Rail scaffolding behavior", true);
            tntDropping = builder
                    .comment("If you have TNT and flint and steel/a fire charge in your hands, right clicking while flying with an elytra places lit TNT at your location")
                    .translation(Modicum.MOD_ID + ".config.tntDropping")
                    .define("TNT dropping", true);
            tntDefusing = builder
                    .comment("If you right click a lit TNT entity with shears, it defuses and drops a TNT item")
                    .translation(Modicum.MOD_ID + ".config.tntDefusing")
                    .define("TNT defusing", true);
            noPotionShift = builder
                    .comment("Prevents the inventory GUI from being moved over when a potion effect is active")
                    .translation(Modicum.MOD_ID + ".config.noPotionShift")
                    .define("No potion shift", true);
            //TODO assembler and clock comparator next

            builder.push("tntDropping");
            tntDroppingCooldown = builder
                    .comment("How many ticks before TNT can be dropped again (Default = 60)")
                    .translation(Modicum.MOD_ID + ".config.tntDroppingCooldown")
                    .define("TNT dropping cooldown", 60);
            builder.pop();
            builder.push("tntDefusing");
            tntDefusingCooldown = builder
                    .comment("How many ticks before TNT can be defused again (Default = 0)")
                    .translation(Modicum.MOD_ID + ".config.tntDefusingCooldown")
                    .define("TNT defusing cooldown", 0);
            builder.pop();

        }
    }

    public static class Client {
        public final ConfigValue<Boolean> jukeboxParticles;

        public Client(ForgeConfigSpec.Builder builder) {
            this.jukeboxParticles = builder
                    .comment("Jukeboxes create note particles when playing a record")
                    .translation(Modicum.MOD_ID + ".config.jukeboxParticles")
                    .define("Jukebox particles", true);
        }
    }

    public static final ForgeConfigSpec COMMON_SPEC;
    public static final Common COMMON;

    public static final ForgeConfigSpec CLIENT_SPEC;
    public static final Client CLIENT;

    static {
        Pair<Common, ForgeConfigSpec> commonSpecPair = new ForgeConfigSpec.Builder().configure(Common::new);
        COMMON_SPEC = commonSpecPair.getRight();
        COMMON = commonSpecPair.getLeft();

        Pair<Client, ForgeConfigSpec> clientSpecPair = new ForgeConfigSpec.Builder().configure(Client::new);
        CLIENT_SPEC = clientSpecPair.getRight();
        CLIENT = clientSpecPair.getLeft();
    }
}

