package mca.network.client;

import mca.ClientProxy;
import mca.network.S2CNbtDataMessage;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;

import java.io.Serial;

public class GetFamilyResponse extends S2CNbtDataMessage {
    @Serial
    private static final long serialVersionUID = -8537919427646877115L;

    public GetFamilyResponse(NbtCompound data) {
        super(data);
    }

    @Override
    public void receive(PlayerEntity player) {
        ClientProxy.getNetworkHandler().handleFamilyDataResponse(this);
    }
}
