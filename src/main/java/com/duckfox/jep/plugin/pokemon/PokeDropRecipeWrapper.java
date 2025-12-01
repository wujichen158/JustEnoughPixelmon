package com.duckfox.jep.plugin.pokemon;

import com.duckfox.jep.api.item.PokeDrop;
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

public class PokeDropRecipeWrapper implements IRecipeWrapper, ITooltipCallback<ItemStack> {

    private EnumSpecies species;
    private List<PokeDrop> drops;
    private int form = -10086;

    public int getForm() {
        return form;
    }

    public EnumSpecies getSpecies() {
        return species;
    }

    public List<PokeDrop> getDrops() {
        return drops;
    }

    public PokeDropRecipeWrapper(EnumSpecies species, List<PokeDrop> drops) {
        this.species = species;
        this.drops = drops;
    }

    public PokeDropRecipeWrapper(EnumSpecies species, List<PokeDrop> drops, int form) {
        this.species = species;
        this.drops = drops;
        this.form = form;
    }

    @Override
    public void getIngredients(IIngredients iIngredients) {
        if (form != -10086) {
            iIngredients.setInput(VanillaTypes.ITEM, PokeSprites.forceNewSpriteWithForm(species, form));
        } else {
            iIngredients.setInput(VanillaTypes.ITEM, PokeSprites.getSprite(species));
        }
        iIngredients.setOutputs(VanillaTypes.ITEM, this.drops.stream().map(PokeDrop::getDrops).flatMap(List::stream).collect(Collectors.toList()));

    }

    @Override
    public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
        GuiHelper.drawCenteredString(getName(), 40, 4, 0xffffff, true);
        GuiHelper.drawCenteredString(I18n.format("jep.pokedrop.text"), 135, 4, 0xffffff, true);
    }

    public String getName() {
        return "No." + this.species.getNationalPokedexNumber() + " " + this.species.getLocalizedName();
    }

    @Override
    public void onTooltip(int i, boolean b, ItemStack itemStack, List<String> list) {
        for (PokeDrop drop : drops) {
            if (drop.item.equals(itemStack)) {
                list.addAll(drop.getTooltipText());
            }
        }
    }
}
