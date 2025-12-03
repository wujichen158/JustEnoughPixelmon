package com.duckfox.jep.plugin.pokemon;

import com.duckfox.jep.api.item.PokeHeldItem;
import com.duckfox.jep.plugin.DuckRecipeCategory;
import com.duckfox.jep.utils.PokeSprites;
import com.duckfox.jep.utils.Settings;
import com.pixelmonmod.pixelmon.api.spawning.SpawnInfo;
import com.pixelmonmod.pixelmon.api.spawning.SpawnSet;
import com.pixelmonmod.pixelmon.api.spawning.archetypes.entities.collection.SpawnInfoCollection;
import com.pixelmonmod.pixelmon.api.spawning.archetypes.entities.pokemon.SpawnInfoPokemon;
import com.pixelmonmod.pixelmon.api.item.JsonItemStack;
import com.pixelmonmod.pixelmon.spawning.PixelmonSpawning;
import com.pixelmonmod.pixelmon.enums.EnumSpecies;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PokeHeldRecipeCategory extends DuckRecipeCategory<PokeHeldRecipeWrapper> {

    public static final RecipeType<PokeHeldRecipeWrapper> TYPE = RecipeType.create("justenoughpixelmon", "pokeheld", PokeHeldRecipeWrapper.class);

    public PokeHeldRecipeCategory(IGuiHelper helper) {
        super(
                "pokeheld",
                TYPE,
                helper.createBlankDrawable(170, 120),
                helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(Items.PAPER))
        );
    }

    @Override
    public List<PokeHeldRecipeWrapper> getRecipes() {
        List<PokeHeldRecipeWrapper> recipes = new ArrayList<>();
        // 遍历野外宝可梦生成，汇总其可能持有物
        for (SpawnSet set : PixelmonSpawning.wildPokemon) {
            if (Objects.equals(set.id, "Pokemon Spawns") || true) {
                SpawnInfoCollection spawnInfo = (SpawnInfoCollection) set.spawnInfos.get(0);
                for (SpawnInfo info : spawnInfo.collection) {
                    if (info instanceof SpawnInfoPokemon sip) {
                        EnumSpecies species = sip.getSpecies();
                        List<PokeHeldItem> heldItems = new ArrayList<>();
                        if (sip.heldItems != null) {
                            for (JsonItemStack json : sip.heldItems) {
                                heldItems.add(new PokeHeldItem(json));
                            }
                        }
                        int form = sip.getForm() == null ? -1 : sip.getForm();
                        if (!heldItems.isEmpty()) {
                            recipes.add(new PokeHeldRecipeWrapper(species, heldItems, form));
                        }
                    }
                }
            }
        }
        return recipes;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, PokeHeldRecipeWrapper recipe, IFocusGroup focuses) {
        builder.addSlot(RecipeIngredientRole.INPUT, 30, 12)
                .addItemStack(recipe.getForm() >= 0 ? PokeSprites.forceNewSpriteWithForm(recipe.getSpecies(), recipe.getForm()) : PokeSprites.getSprite(recipe.getSpecies()));

        int xOffset = 0;
        int slotX = 97;
        int slotY = 12;
        int col = 0;
        for (int i = 0; i < Math.min(recipe.getHeldItems().size(), Settings.ITEMS_PER_ROW * Settings.ITEMS_PER_COLUMN); i++) {
            PokeHeldItem item = recipe.getHeldItems().get(i);
            builder.addSlot(RecipeIngredientRole.OUTPUT, slotX + xOffset, slotY)
                    .addItemStack(item.getItemStack())
                    .addTooltipCallback((view, tooltip) -> tooltip.addAll(item.getTooltip()));
            xOffset += 72 / Settings.ITEMS_PER_ROW;
            col++;
            if (col == Settings.ITEMS_PER_ROW) {
                col = 0;
                xOffset = 0;
                slotY += 18;
            }
        }
    }
}
