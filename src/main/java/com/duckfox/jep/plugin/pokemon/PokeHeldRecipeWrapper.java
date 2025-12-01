package com.duckfox.jep.plugin.pokemon;

import com.duckfox.jep.api.item.PokeHeldItem;
import com.duckfox.jep.utils.PokeSprites;
import com.pixelmonmod.pixelmon.client.gui.GuiHelper;
import com.pixelmonmod.pixelmon.enums.EnumSpecies;
import mezz.jei.api.gui.ITooltipCallback;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;

import java.util.List;
import java.util.stream.Collectors;

public class PokeHeldRecipeWrapper implements IRecipeWrapper, ITooltipCallback<ItemStack> {

    private EnumSpecies species;
    private List<PokeHeldItem> heldItems;
    private int form;

    public int getForm() {
        return form;
    }

    public EnumSpecies getSpecies() {
        return species;
    }

    public List<PokeHeldItem> getHeldItems() {
        return heldItems;
    }

    public PokeHeldRecipeWrapper(EnumSpecies species, List<PokeHeldItem> heldItems) {
        this.species = species;
        this.heldItems = heldItems;
    }
    public PokeHeldRecipeWrapper(EnumSpecies species, List<PokeHeldItem> heldItems,int form) {
        this.species = species;
        this.heldItems = heldItems;
        this.form = form;
    }

    @Override
    public void getIngredients(IIngredients iIngredients) {
        iIngredients.setInput(VanillaTypes.ITEM, PokeSprites.getSprite(species));
        iIngredients.setOutputs(VanillaTypes.ITEM, this.heldItems.stream().map(PokeHeldItem::getItemStack).collect(Collectors.toList()));
    }

    @Override
    public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
//        IRecipeWrapper.super.drawInfo(minecraft, recipeWidth, recipeHeight, mouseX, mouseY);
        GuiHelper.drawCenteredString(getName(), 40, 4, 0xffffff, true);
        GuiHelper.drawCenteredString(I18n.format("jep.pokeheld.text"), 135, 4, 0xffffff, true);
    }

    public String getName() {
        return "No." + this.species.getNationalPokedexNumber() + " " + this.species.getLocalizedName();
    }

    @Override
    public void onTooltip(int i, boolean b, ItemStack itemStack, List<String> list) {
        for (PokeHeldItem item : heldItems) {
            if (item.getItemStack().equals(itemStack)) {
                list.addAll(item.getTooltip());
            }
        }
    }
}
