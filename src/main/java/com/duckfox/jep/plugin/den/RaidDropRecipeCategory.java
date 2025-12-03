package com.duckfox.jep.plugin.den;

import com.duckfox.jep.api.item.RaidDrop;
import com.duckfox.jep.plugin.DuckRecipeCategory;
import com.duckfox.jep.utils.Reflections;
import com.pixelmonmod.pixelmon.battles.raids.WeightedItemStacks;
import com.pixelmonmod.pixelmon.config.PixelmonItems;
import com.pixelmonmod.pixelmon.entities.npcs.registry.DropItemRegistry;
import com.pixelmonmod.pixelmon.enums.EnumType;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.util.Tuple;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RaidDropRecipeCategory extends DuckRecipeCategory<RaidDropRecipeWrapper> {
    public static final RecipeType<RaidDropRecipeWrapper> TYPE = RecipeType.create("justenoughpixelmon", "raiddrop", RaidDropRecipeWrapper.class);

    public RaidDropRecipeCategory(IGuiHelper helper) {
        super("raiddrop", TYPE, helper.createBlankDrawable(170, 50), helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(PixelmonItems.wishingPiece)));
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<RaidDropRecipeWrapper> getRecipes() {
        List<RaidDropRecipeWrapper> recipes = new ArrayList<>();
        for (Map.Entry<Integer, HashMap<EnumType, WeightedItemStacks>> outer : DropItemRegistry.raidDrops.entrySet()) {
            for (Map.Entry<EnumType, WeightedItemStacks> inner : outer.getValue().entrySet()) {
                if (inner.getKey() == EnumType.Mystery) {
                    continue;
                }
                ArrayList<Tuple<Integer, ItemStack>> stacks = (ArrayList<Tuple<Integer, ItemStack>>) Reflections.get(inner.getValue(), "stacks", ArrayList.class);
                List<RaidDrop> drops = new ArrayList<>();
                for (Tuple<Integer, ItemStack> tuple : stacks) {
                    drops.add(new RaidDrop(tuple.getSecond(), tuple.getFirst()));
                }
                recipes.add(new RaidDropRecipeWrapper(outer.getKey(), inner.getKey(), drops));
            }
        }
        return recipes;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, RaidDropRecipeWrapper recipe, IFocusGroup focuses) {
        int xOffset = 0;
        int yOffset = 0;
        int slotX = 5;
        int slotY = 15;
        int col = 0;
        int row = 0;
        for (RaidDrop drop : recipe.getDrops()) {
            builder.addSlot(RecipeIngredientRole.OUTPUT, slotX + xOffset, slotY + yOffset).addItemStack(drop.getItemStack());
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
