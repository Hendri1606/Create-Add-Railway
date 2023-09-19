package com.railwayteam.railways.ponder.scenes.temp;

import java.util.function.Supplier;

import net.createmod.ponder.foundation.PonderScene;
import net.createmod.ponder.foundation.element.ParrotElement;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.phys.Vec3;

public class ExpandedParrotElement extends ParrotElement {

    protected boolean deferConductor = false;

    protected ExpandedParrotElement(Vec3 location, Supplier<? extends ParrotPose> pose) {
        super(location, pose);
    }

    @Override
    public void reset(PonderScene scene) {
        super.reset(scene);
//        entity.getPersistentData().remove("TrainHat");
        deferConductor = false;
    }

    @Override
    public void tick(PonderScene scene) {
        boolean wasNull = entity == null;
        super.tick(scene);
        if (wasNull) {
            if (deferConductor) {
                setConductor(true);
            }
            deferConductor = false;
        }
    }

    public void setConductor(boolean isConductor) {
        if (entity == null) {
            deferConductor = isConductor;
            return;
        }
//        CompoundTag data = entity.getPersistentData();
//        if (isConductor)
//            data.putBoolean("TrainHat", true);
//        else
//            data.remove("TrainHat");
    }
}
