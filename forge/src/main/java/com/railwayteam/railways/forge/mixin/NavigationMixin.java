package com.railwayteam.railways.forge.mixin;

import com.railwayteam.railways.mixin_interfaces.IGenerallySearchableNavigation;
import com.railwayteam.railways.mixin_interfaces.ILimitedGlobalStation;
import com.railwayteam.railways.mixin_interfaces.IWaypointableNavigation;
import com.simibubi.create.content.trains.entity.Navigation;
import com.simibubi.create.content.trains.entity.Train;
import com.simibubi.create.content.trains.station.GlobalStation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value = Navigation.class, remap = false)
public abstract class NavigationMixin implements IWaypointableNavigation, IGenerallySearchableNavigation {
    @Shadow public Train train;

    @Redirect(method = "search(DDZLjava/util/ArrayList;Lcom/simibubi/create/content/trains/entity/Navigation$StationTest;)V", at = @At(value = "INVOKE", target = "Lcom/simibubi/create/content/trains/station/GlobalStation;getPresentTrain()Lcom/simibubi/create/content/trains/entity/Train;"))
    private Train replacePresentTrain(GlobalStation instance) {
        return ((ILimitedGlobalStation) instance).orDisablingTrain(instance.getPresentTrain(), train);
    }
}
