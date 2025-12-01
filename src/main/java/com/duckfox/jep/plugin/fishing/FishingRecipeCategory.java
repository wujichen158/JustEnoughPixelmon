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
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

import java.util.ArrayList;
import java.util.List;

public class FishingRecipeCategory extends DuckRecipeCategory<FishingRecipeWrapper> {
    public FishingRecipeCategory(IGuiHelper helper) {
        super("fishing", "jep.fishing", helper.createBlankDrawable(170, 125), new ItemStack(PixelmonItems.superRod));
    }

    private static final ItemStack OLD_ROD = new ItemStack(PixelmonItems.oldRod);
    private static final ItemStack GOOD_ROD = new ItemStack(PixelmonItems.goodRod);
    private static final ItemStack SUPER_ROD = new ItemStack(PixelmonItems.superRod);

    @Override
    public void setupRecipes(IModRegistry registry) {
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
                            oldItems.add(new FishingItem(spawnInfoItem.itemStack, spawnInfoItem.rarity, spawnInfoItem.percentage==null?0.0f:spawnInfoItem.percentage, spawnInfoItem.condition));
                        } else if (type == LocationType.GOOD_ROD) {
                            goodItems.add(new FishingItem(spawnInfoItem.itemStack, spawnInfoItem.rarity, spawnInfoItem.percentage==null?0.0f:spawnInfoItem.percentage, spawnInfoItem.condition));
                        } else if (type == LocationType.SUPER_ROD) {
                            superItems.add(new FishingItem(spawnInfoItem.itemStack, spawnInfoItem.rarity, spawnInfoItem.percentage==null?0.0f:spawnInfoItem.percentage, spawnInfoItem.condition));
                        }
                    }
                }
                recipes.add(new FishingRecipeWrapper(oldItems, OLD_ROD, true));
                recipes.add(new FishingRecipeWrapper(goodItems, GOOD_ROD, true));
                recipes.add(new FishingRecipeWrapper(superItems, SUPER_ROD, true));
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
                            oldItems.add(new FishingItem(spawnInfoItem.itemStack, spawnInfoItem.rarity, spawnInfoItem.percentage==null?0.0f:spawnInfoItem.percentage, spawnInfoItem.condition));
                        } else if (type == LocationType.GOOD_ROD_LAVA) {
                            goodItems.add(new FishingItem(spawnInfoItem.itemStack, spawnInfoItem.rarity, spawnInfoItem.percentage==null?0.0f:spawnInfoItem.percentage, spawnInfoItem.condition));
                        } else if (type == LocationType.SUPER_ROD_LAVA) {
                            superItems.add(new FishingItem(spawnInfoItem.itemStack, spawnInfoItem.rarity, spawnInfoItem.percentage==null?0.0f:spawnInfoItem.percentage, spawnInfoItem.condition));
                        }
                    }
                }
                recipes.add(new FishingRecipeWrapper(oldItems, OLD_ROD, false));
                recipes.add(new FishingRecipeWrapper(goodItems, GOOD_ROD, false));
                recipes.add(new FishingRecipeWrapper(superItems, SUPER_ROD, false));
            }
        }

        registry.addRecipes(recipes, getUid());
    }

    public static final int FIRST_ITEM_X = 5;
    public static final int FIRST_ITEM_Y = 17;

    @Override
    public void setRecipe(IRecipeLayout iRecipeLayout, FishingRecipeWrapper fishingRecipeWrapper, IIngredients iIngredients) {
        iRecipeLayout.getItemStacks().init(0, true, 0, 0);
        iRecipeLayout.getItemStacks().set(0, fishingRecipeWrapper.getRod());
        iRecipeLayout.getItemStacks().addTooltipCallback(fishingRecipeWrapper);
        iRecipeLayout.getFluidStacks().init(1, true, 18, 0);
        iRecipeLayout.getFluidStacks().set(1, fishingRecipeWrapper.isWater() ? new FluidStack(FluidRegistry.WATER, 1000) : new FluidStack(FluidRegistry.LAVA, 1000));


        int xOffset = 0;
        int yOffset = 0;
        int slot = 2;
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 9; j++) {
                iRecipeLayout.getItemStacks().init(slot++, false, FIRST_ITEM_X + xOffset, FIRST_ITEM_Y + yOffset);
                xOffset += 18;
            }
            xOffset = 0;
            yOffset += 18;
        }
        slot = 2;
        iRecipeLayout.getItemStacks().addTooltipCallback(fishingRecipeWrapper);

        for (int i = 0; i < fishingRecipeWrapper.getFishingItems().size(); i++) {
            iRecipeLayout.getItemStacks().set(slot++, fishingRecipeWrapper.getFishingItems().get(i).getItemStack());
        }
    }
}
