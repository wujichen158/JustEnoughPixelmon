package com.duckfox.jep.plugin.infuser;

import net.minecraft.world.item.ItemStack;

public class InfuserRecipeWrapper {
    private final ItemStack input1;
    private final ItemStack input2;
    private final int ticks;
    private final ItemStack output;

    public InfuserRecipeWrapper(ItemStack input1, ItemStack input2, int ticks, ItemStack output) {
        this.input1 = input1;
        this.input2 = input2;
        this.ticks = ticks;
        this.output = output;
    }

    public ItemStack getInput1() {
        return input1;
    }

    public ItemStack getInput2() {
        return input2;
    }

    public int getTicks() {
        return ticks;
    }

    public ItemStack getOutput() {
        return output;
    }
}
