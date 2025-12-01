package com.duckfox.jep.plugin.infuser;

import com.pixelmonmod.pixelmon.client.gui.GuiHelper;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;

import java.util.Arrays;

public class InfuserRecipeWrapper implements IRecipeWrapper {

    private ItemStack input1;
    private ItemStack input2;
    private int ticks;
    private ItemStack output;

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

    public InfuserRecipeWrapper(ItemStack input1, ItemStack input2, int ticks, ItemStack output) {
        this.input1 = input1;
        this.input2 = input2;
        this.ticks = ticks;
        this.output = output;
    }

    @Override
    public void getIngredients(IIngredients iIngredients) {
        iIngredients.setInputs(VanillaTypes.ITEM, Arrays.asList(input1, input2));
        iIngredients.setOutput(VanillaTypes.ITEM, output);
    }

    @Override
    public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
        GuiHelper.drawCenteredString(getTicks() + " ticks", 105, 41, 0xffffff, true);
    }
}
