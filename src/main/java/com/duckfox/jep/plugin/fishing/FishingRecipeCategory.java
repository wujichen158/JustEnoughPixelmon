package com.duckfox.jep.plugin.fishing;

import com.duckfox.jep.api.item.FishingItem;
import com.duckfox.jep.plugin.DuckRecipeCategory;
import com.pixelmonmod.pixelmon.api.spawning.SpawnInfo;
import com.pixelmonmod.pixelmon.api.spawning.SpawnSet;
import com.pixelmonmod.pixelmon.api.spawning.archetypes.entities.collection.SpawnInfoCollection;
import com.pixelmonmod.pixelmon.api.spawning.archetypes.entities.items.SpawnInfoItem;
import com.pixelmonmod.pixelmon.api.spawning.conditions.LocationType;
import com.pixelmonmod.pixelmon.config.PixelmonItems;
import com.pixelmonmod.pixelmon.spawning.PixelmonSpawning;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

import java.util.ArrayList;
import java.util.List;

public class FishingRecipeCategory extends DuckRecipeCategory<FishingRecipeWrapper> {
    public static final RecipeType<FishingRecipeWrapper> TYPE = RecipeType.create("justenoughpixelmon", "fishing", FishingRecipeWrapper.class);

    public FishingRecipeCategory(IGuiHelper helper) {
        super("fishing", TYPE, helper.createBlankDrawable(170, 125), helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(PixelmonItems.superRod)));
    }

    @Override
    public List<FishingRecipeWrapper> getRecipes() {
        List<FishingRecipeWrapper> recipes = new ArrayList<>();
        ArrayList<SpawnSet> fishing = PixelmonSpawning.fishing;
        for (SpawnSet spawnSet : fishing) {
            if (spawnSet.id.equals("Water Loot")) {
                List<FishingItem> oldItems = new ArrayList<>();
                List<FishingItem> goodItems = new ArrayList<>();
                List<FishingItem> superItems = new ArrayList<>();

                SpawnInfoCollection spawnInfo = (SpawnInfoCollection) spawnSet.spawnInfos.get(0);

                for (SpawnInfo info : spawnInfo.collection) {
                    SpawnInfoItem spawnInfoItem = (SpawnInfoItem) info;
                    for (LocationType type : spawnInfoItem.locationTypes) {
                        if (type == LocationType.OLD_ROD) {
                            oldItems.add(new FishingItem(spawnInfoItem.itemStack, spawnInfoItem.rarity, spawnInfoItem.percentage == null ? 0.0f : spawnInfoItem.percentage, spawnInfoItem.condition));
                        } else if (type == LocationType.GOOD_ROD) {
                            goodItems.add(new FishingItem(spawnInfoItem.itemStack, spawnInfoItem.rarity, spawnInfoItem.percentage == null ? 0.0f : spawnInfoItem.percentage, spawnInfoItem.condition));
                        } else if (type == LocationType.SUPER_ROD) {
                            superItems.add(new FishingItem(spawnInfoItem.itemStack, spawnInfoItem.rarity, spawnInfoItem.percentage == null ? 0.0f : spawnInfoItem.percentage, spawnInfoItem.condition));
                        }
                    }
                }
                recipes.add(new FishingRecipeWrapper(oldItems, new ItemStack(PixelmonItems.oldRod), true));
                recipes.add(new FishingRecipeWrapper(goodItems, new ItemStack(PixelmonItems.goodRod), true));
                recipes.add(new FishingRecipeWrapper(superItems, new ItemStack(PixelmonItems.superRod), true));
            }
            if (spawnSet.id.equals("Lava Loot")) {
                List<FishingItem> oldItems = new ArrayList<>();
                List<FishingItem> goodItems = new ArrayList<>();
                List<FishingItem> superItems = new ArrayList<>();

                SpawnInfoCollection spawnInfo = (SpawnInfoCollection) spawnSet.spawnInfos.get(0);

                for (SpawnInfo info : spawnInfo.collection) {
                    SpawnInfoItem spawnInfoItem = (SpawnInfoItem) info;
                    for (LocationType type : spawnInfoItem.locationTypes) {
                        if (type == LocationType.OLD_ROD_LAVA) {
                            oldItems.add(new FishingItem(spawnInfoItem.itemStack, spawnInfoItem.rarity, spawnInfoItem.percentage == null ? 0.0f : spawnInfoItem.percentage, spawnInfoItem.condition));
                        } else if (type == LocationType.GOOD_ROD_LAVA) {
                            goodItems.add(new FishingItem(spawnInfoItem.itemStack, spawnInfoItem.rarity, spawnInfoItem.percentage == null ? 0.0f : spawnInfoItem.percentage, spawnInfoItem.condition));
                        } else if (type == LocationType.SUPER_ROD_LAVA) {
                            superItems.add(new FishingItem(spawnInfoItem.itemStack, spawnInfoItem.rarity, spawnInfoItem.percentage == null ? 0.0f : spawnInfoItem.percentage, spawnInfoItem.condition));
                        }
                    }
                }
                recipes.add(new FishingRecipeWrapper(oldItems, new ItemStack(PixelmonItems.oldRod), false));
                recipes.add(new FishingRecipeWrapper(goodItems, new ItemStack(PixelmonItems.goodRod), false));
                recipes.add(new FishingRecipeWrapper(superItems, new ItemStack(PixelmonItems.superRod), false));
            }
        }
        return recipes;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, FishingRecipeWrapper recipe, IFocusGroup focuses) {
        builder.addSlot(RecipeIngredientRole.INPUT, 0, 0).addItemStack(recipe.getRod());
        builder.addSlot(RecipeIngredientRole.INPUT, 18, 0).addIngredient(VanillaTypes.FLUID,
                recipe.isWater() ? new FluidStack(FluidRegistry.WATER, 1000) : new FluidStack(FluidRegistry.LAVA, 1000));

        int xOffset = 0;
        int yOffset = 0;
        int slotX = 5;
        int slotY = 17;
        int col = 0;
        int row = 0;
        for (int i = 0; i < recipe.getFishingItems().size(); i++) {
            builder.addSlot(RecipeIngredientRole.OUTPUT, slotX + xOffset, slotY + yOffset)
                    .addItemStack(recipe.getFishingItems().get(i).getItemStack());
            col++;
            xOffset += 18;
            if (col == 9) {
                col = 0;
                xOffset = 0;
                row++;
                yOffset = row * 18;
            }
        }
    }
}
