package com.duckfox.jep.plugin.boss;

import com.duckfox.jep.api.item.RaidDrop;
import com.google.common.collect.Lists;
import com.pixelmonmod.pixelmon.client.gui.GuiHelper;
import com.pixelmonmod.pixelmon.enums.EnumBossMode;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;

import java.util.List;
import java.util.stream.Collectors;

public class BossDropRecipeWrapper implements IRecipeWrapper {
    private List<ItemStack> itemStacks;
    private EnumBossMode mode;

    public EnumBossMode getMode() {
        return mode;
    }

    public List<ItemStack> getItemStacks() {
        return itemStacks;
    }

    public BossDropRecipeWrapper(List<ItemStack> itemStacks, EnumBossMode mode) {
        this.itemStacks = itemStacks;
        this.mode = mode;
    }
    @Override
    public void getIngredients(IIngredients iIngredients) {
        iIngredients.setOutputs(VanillaTypes.ITEM, Lists.newArrayList(itemStacks));
    }

    @Override
    public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
        GuiHelper.drawCenteredString(I18n.format("jep.bossdrop.text",mode.getBossText()),84,2,this.mode.colour.getRGB(),true);
        if (itemStacks.isEmpty()){
            GuiHelper.drawCenteredString(I18n.format("jep.bossdrop.noitem"),84,37,0xffffff,true);
        }
    }
}
