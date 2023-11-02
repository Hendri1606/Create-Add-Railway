package com.railwayteam.railways.content.roller_extensions.forge;

import com.simibubi.create.content.contraptions.behaviour.MovementContext;
import com.simibubi.create.foundation.item.ItemHelper;
import net.minecraft.world.item.ItemStack;

public class TrackReplacePaverImpl {
    //fixme properly fix this once i port create fabric
    public static ItemStack extract(ItemStack filter, MovementContext context, int amt) {
        return ItemHelper.extract(context.contraption.getSharedInventory(),
                stack -> context.getFilterFromBE().test(context.world, stack), amt, false);
    }
}
