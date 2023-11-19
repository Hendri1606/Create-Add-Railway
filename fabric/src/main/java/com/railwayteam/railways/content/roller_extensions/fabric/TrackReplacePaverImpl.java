package com.railwayteam.railways.content.roller_extensions.fabric;

import com.simibubi.create.content.contraptions.behaviour.MovementContext;
import com.simibubi.create.content.logistics.filter.FilterItem;
import com.simibubi.create.foundation.item.ItemHelper;
import net.minecraft.world.item.ItemStack;

public class TrackReplacePaverImpl {
    public static ItemStack extract(ItemStack filter, MovementContext context, int amt) {
        return ItemHelper.extract(context.contraption.getSharedInventory(),
                stack -> context.getFilterFromBE().test(context.world, stack), amt, false);
    }
}
