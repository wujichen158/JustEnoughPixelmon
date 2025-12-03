package com.duckfox.jep.plugin.pokemon;

import com.duckfox.jep.plugin.DuckRecipeCategory;
import com.duckfox.jep.utils.PokeSprites;
import com.duckfox.jep.utils.Settings;
import com.pixelmonmod.pixelmon.api.pokemon.drops.ItemWithChance;
import com.pixelmonmod.pixelmon.api.pokemon.drops.PokemonDropInformation;
import com.pixelmonmod.pixelmon.api.pokemon.species.Species;
import com.pixelmonmod.pixelmon.api.registries.PixelmonSpecies;
import com.pixelmonmod.pixelmon.entities.npcs.registry.DropItemRegistry;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import net.minecraft.client.gui.GuiGraphics;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class PokeDropRecipeCategory extends DuckRecipeCategory<PokeDropRecipeWrapper> {

    public static final RecipeType<PokeDropRecipeWrapper> TYPE = RecipeType.create("justenoughpixelmon", "pokedrop", PokeDropRecipeWrapper.class);

    public PokeDropRecipeCategory(IGuiHelper helper) {
        super(
                "pokedrop",
                TYPE,
                helper.createBlankDrawable(170, 120),
                helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, PokeSprites.getSprite(PixelmonSpecies.MISSINGNO.getOrThrow()))
        );
    }

    @Override
    public List<PokeDropRecipeWrapper> getRecipes() {
        List<PokeDropRecipeWrapper> recipes = new ArrayList<>();
        List<Species> sortedSpecies = PixelmonSpecies.getAll();
        // Int2ObjectOpenHashMap#values() 自然有序，无需手排
        for (Species species : sortedSpecies) {
            for (PokemonDropInformation info : DropItemRegistry.pokemonDrops.get(species)) {
                recipes.add(new PokeDropRecipeWrapper(info));
            }
        }
        return recipes;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, PokeDropRecipeWrapper recipe, IFocusGroup focuses) {
        builder.addSlot(RecipeIngredientRole.INPUT, 30, 12)
                .addItemStack(recipe.getSprite());

        int xOffset = 0;
        int slotX = 97;
        int slotY = 12;
        int col = 0;
        for (int i = 0; i < Math.min(recipe.getDrops().size(), Settings.ITEMS_PER_ROW * Settings.ITEMS_PER_COLUMN); i++) {
            ItemWithChance drop = recipe.getDrops().get(i);
            builder.addSlot(RecipeIngredientRole.OUTPUT, slotX + xOffset, slotY)
                    .addItemStacks(List.of(drop.getItemStack()))
                    .addRichTooltipCallback((view, tooltip) -> tooltip.add(recipe.genDropTooltips(drop)));
            xOffset += 72 / Settings.ITEMS_PER_ROW;
            col++;
            if (col == Settings.ITEMS_PER_ROW) {
                col = 0;
                xOffset = 0;
                slotY += 18;
            }
        }
    }

    @Override
    public void draw(@NotNull PokeDropRecipeWrapper recipe, @NotNull IRecipeSlotsView recipeSlotsView, @NotNull GuiGraphics guiGraphics, double mouseX, double mouseY) {
        for (PokeDropRecipeWrapper pokeDropRecipeWrapper : getRecipes()) {
            pokeDropRecipeWrapper.draw(guiGraphics, mouseX, mouseY);
        }
    }
}
