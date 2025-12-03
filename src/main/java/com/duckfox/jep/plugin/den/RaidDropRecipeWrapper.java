package com.duckfox.jep.plugin.den;

import com.duckfox.jep.api.item.RaidDrop;
import com.pixelmonmod.pixelmon.enums.EnumType;

import java.util.List;

public class RaidDropRecipeWrapper {
    private final int star;
    private final EnumType type;
    private final List<RaidDrop> drops;

    public RaidDropRecipeWrapper(int star, EnumType type, List<RaidDrop> drops) {
        this.star = star;
        this.type = type;
        this.drops = drops;
    }

    public int getStar() {
        return star;
    }

    public EnumType getType() {
        return type;
    }

    public List<RaidDrop> getDrops() {
        return drops;
    }
}
