package de.maxhenkel.corpse.entities;

import com.mojang.authlib.GameProfile;
import de.maxhenkel.corpse.Main;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.RemotePlayer;
import net.minecraft.core.NonNullList;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.player.PlayerModelPart;
import net.minecraft.world.entity.player.ProfilePublicKey;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public class DummyPlayer extends RemotePlayer {

    private final byte model;

    public DummyPlayer(ClientLevel world, GameProfile gameProfile, NonNullList<ItemStack> equipment, byte model) {
        this(world, gameProfile, null, equipment, model);
    }

    public DummyPlayer(ClientLevel world, GameProfile gameProfile, @Nullable ProfilePublicKey profilePublicKey, NonNullList<ItemStack> equipment, byte model) {
        super(world, gameProfile, profilePublicKey);
        this.model = model;
        if (Main.SERVER_CONFIG.renderEquipment.get()) {
            for (EquipmentSlot type : EquipmentSlot.values()) {
                setItemSlot(type, equipment.get(type.ordinal()));
            }
        }
        refreshDimensions();

        setPos(0D, 0D, 0D);
        xo = 0D;
        yo = 0D;
        zo = 0D;
    }

    @Override
    public boolean isSpectator() {
        return false;
    }

    @Override
    public EntityDimensions getDimensions(Pose pose) {
        return new EntityDimensions(super.getDimensions(pose).width, 1000, true);
    }

    @Override
    public boolean isModelPartShown(PlayerModelPart part) {
        return (model & part.getMask()) == part.getMask();
    }
}
