package com.duckfox.jep.plugin.fishing;

import com.duckfox.jep.api.item.FishingItem;
import com.pixelmonmod.pixelmon.api.spawning.conditions.SpawnCondition;
import com.pixelmonmod.pixelmon.api.world.WeatherType;
import com.pixelmonmod.pixelmon.api.world.WorldTime;
import com.pixelmonmod.pixelmon.client.gui.GuiHelper;
import mezz.jei.api.gui.ITooltipCallback;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

import java.util.List;
import java.util.stream.Collectors;

public class FishingRecipeWrapper implements IRecipeWrapper, ITooltipCallback<ItemStack> {

    private List<FishingItem> fishingItems;
    private ItemStack rod;
    private boolean water;

    public FishingRecipeWrapper(List<FishingItem> fishingItems, ItemStack rod, boolean water) {
        this.fishingItems = fishingItems;
        this.rod = rod;
        this.water = water;
    }

    @Override
    public void getIngredients(IIngredients ingredients) {
        ingredients.setInput(VanillaTypes.ITEM, rod);
        ingredients.setInput(VanillaTypes.FLUID,
                water ? new FluidStack(FluidRegistry.WATER, 1000) : new FluidStack(FluidRegistry.LAVA, 1000));
        ingredients.setOutputs(VanillaTypes.ITEM,
                fishingItems.stream().map(FishingItem::getItemStack).collect(Collectors.toList()));
    }

    @Override
    public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
    }

    @Override
    public void onTooltip(int slotIndex, boolean input, ItemStack ingredient, List<String> tooltip) {
        // 如果是输出槽（钓鱼物品）
        if (!input && slotIndex>=2) {
            FishingItem fishingItem = fishingItems.get(slotIndex-2);
            SpawnCondition condition = fishingItem.getSpawnCondition();

            if (condition == null) {
                return;
            }

            if (fishingItem.getRarity()==0.0f)
            {
                tooltip.add(I18n.format("jep.condition.percentage", String.format("%.2f%%",fishingItem.getPercentage())));
            }else {
                tooltip.add(I18n.format("jep.condition.rarity", fishingItem.getRarity()));

            }

            // 添加时间条件
            if (condition.times != null) {
                tooltip.add(I18n.format("jep.condition.time"));
                for (WorldTime time : condition.times) {
                    tooltip.add("  " + time.getLocalizedName());
                }
            }

            // 添加天气条件
            if (condition.weathers != null) {
                tooltip.add(I18n.format("jep.condition.weather"));
                for (WeatherType weather : condition.weathers) {
                    tooltip.add("  " + weather.getLocalizedName());
                }
            }

            // 添加生物群系条件
            if (condition.biomes != null && !condition.biomes.isEmpty()) {
                tooltip.add(I18n.format("jep.condition.biome"));
                for (Biome biome : condition.biomes) {
                    tooltip.add("  " + biome.getBiomeName());
                }
            }

            // 添加其他条件
            if (condition.seesSky != null) {
                tooltip.add(I18n.format("jep.condition.seesky", condition.seesSky));
            }
            if (condition.maxY != null) {
                tooltip.add(I18n.format("jep.condition.maxy", condition.maxY));
            }
            if (condition.minY != null) {
                tooltip.add(I18n.format("jep.condition.miny", condition.minY));
            }
        }
    }

    public List<FishingItem> getFishingItems() {
        return fishingItems;
    }

    public ItemStack getRod() {
        return rod;
    }

    public boolean isWater() {
        return water;
    }
}