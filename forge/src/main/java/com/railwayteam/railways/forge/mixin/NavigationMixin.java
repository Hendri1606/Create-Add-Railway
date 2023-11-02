package com.railwayteam.railways.forge.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import com.railwayteam.railways.Railways;
import com.railwayteam.railways.mixin_interfaces.IGenerallySearchableNavigation;
import com.railwayteam.railways.mixin_interfaces.ILimitedGlobalStation;
import com.railwayteam.railways.mixin_interfaces.IWaypointableNavigation;
import com.railwayteam.railways.registry.CRTrackMaterials;
import com.simibubi.create.content.trains.entity.Navigation;
import com.simibubi.create.content.trains.entity.Train;
import com.simibubi.create.content.trains.graph.TrackEdge;
import com.simibubi.create.content.trains.graph.TrackNode;
import com.simibubi.create.content.trains.station.GlobalStation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.Map;

//fixme temp until i eventually port create fabric
@Mixin(value = Navigation.class, remap = false)
public abstract class NavigationMixin implements IWaypointableNavigation, IGenerallySearchableNavigation {
    @Shadow public Train train;

    @Redirect(method = "search(DDZLjava/util/ArrayList;Lcom/simibubi/create/content/trains/entity/Navigation$StationTest;)V", at = @At(value = "INVOKE", target = "Lcom/simibubi/create/content/trains/station/GlobalStation;getPresentTrain()Lcom/simibubi/create/content/trains/entity/Train;"))
    private Train replacePresentTrain(GlobalStation instance) {
        return ((ILimitedGlobalStation) instance).orDisablingTrain(instance.getPresentTrain(), train);
    }

    @SuppressWarnings("unused")
    @ModifyExpressionValue(method = "search(DDZLjava/util/ArrayList;Lcom/simibubi/create/content/trains/entity/Navigation$StationTest;)V",
            at = @At(value = "INVOKE", target = "Ljava/util/Set;contains(Ljava/lang/Object;)Z"))
    private boolean isNavigationIncompatible(boolean original, @Local Map.Entry<TrackNode, TrackEdge> target) {
        if (target.getValue().getTrackMaterial().trackType == CRTrackMaterials.CRTrackType.UNIVERSAL)
            return true;
        return original;
    }

    @Inject(method = "search(DDZLjava/util/ArrayList;Lcom/simibubi/create/content/trains/entity/Navigation$StationTest;)V", at = @At("HEAD"))
    private void recordSearch(double maxDistance, double maxCost, boolean forward, ArrayList<GlobalStation> destinations, Navigation.StationTest stationTest, CallbackInfo ci) {
        Railways.navigationCallDepth += 1;
    }

    @Inject(method = "search(DDZLjava/util/ArrayList;Lcom/simibubi/create/content/trains/entity/Navigation$StationTest;)V", at = @At("RETURN"))
    private void recordSearchReturn(double maxDistance, double maxCost, boolean forward, ArrayList<GlobalStation> destinations, Navigation.StationTest stationTest, CallbackInfo ci) {
        if (Railways.navigationCallDepth > 0)
            Railways.navigationCallDepth -= 1;
    }
}
