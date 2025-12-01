package com.duckfox.jep.plugin.den;

import com.duckfox.jep.api.item.RaidDrop;
import com.pixelmonmod.pixelmon.client.gui.GuiHelper;
import com.pixelmonmod.pixelmon.enums.EnumType;
import mezz.jei.api.gui.ITooltipCallback;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;

import java.util.List;
import java.util.stream.Collectors;

public class RaidDropRecipeWrapper implements IRecipeWrapper, ITooltipCallback<ItemStack> {

    private int star;
    private EnumType type;
    private List<RaidDrop> drops;

    public List<RaidDrop> getDrops() {
        return drops;
    }

    public EnumType getType() {
        return type;
    }

    public int getStar() {
        return star;
    }

    public RaidDropRecipeWrapper(int star, EnumType type, List<RaidDrop> drops) {
        this.star = star;
        this.type = type;
        this.drops = drops;
    }

    @Override
    public void getIngredients(IIngredients iIngredients) {
        iIngredients.setOutputs(VanillaTypes.ITEM, drops.stream().map(RaidDrop::getItemStack).collect(Collectors.toList()));
    }

    @Override
    public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
        GuiHelper.drawCenteredString(I18n.format("jep.raiddrop.text", star, type == null ? "Any" : type.getLocalizedName()),84,2,0xffffff,true);
    }

    @Override
    public void onTooltip(int i, boolean b, ItemStack itemStack, List<String> list) {
        for (RaidDrop drop : drops) {
            if (drop.getItemStack().isItemEqual(itemStack)) {
                list.addAll(drop.getTooltipText());
            }
        }
    }
}
