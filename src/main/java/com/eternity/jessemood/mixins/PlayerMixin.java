package com.eternity.jessemood.mixins;

import net.minecraft.client.player.LocalPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@OnlyIn(Dist.CLIENT)
@Mixin(LivingEntity.class)
public abstract class PlayerMixin {

    @Unique
    private static float jesus_Ding$lastHP = 0;

    @Inject(method = "setHealth", at = @At("TAIL"))
    public void onHurt(float HP, CallbackInfo ci) {
        LivingEntity entity = (LivingEntity) (Object) this;
        if (entity instanceof LocalPlayer player && (!player.isSpectator() && !player.isCreative())) {
            float stabilizeHP = Mth.clamp(HP, 0.0F, player.getMaxHealth());
            if (stabilizeHP >= jesus_Ding$lastHP) {
                jesus_Ding$lastHP = stabilizeHP;
                return;
            }
            jesus_Ding$lastHP = stabilizeHP;
            if (stabilizeHP > 0 && stabilizeHP <= 1)
                com.eternity.jessemood.client.guihandler.triggerDisplay();
        }
    }
}