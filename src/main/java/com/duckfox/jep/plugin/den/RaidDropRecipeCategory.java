package com.duckfox.jep.plugin.den;

import com.duckfox.jep.api.item.RaidDrop;
import com.duckfox.jep.plugin.DuckRecipeCategory;
import com.duckfox.jep.utils.Reflections;
import com.pixelmonmod.pixelmon.battles.raids.WeightedItemStacks;
import com.pixelmonmod.pixelmon.config.PixelmonItems;
import com.pixelmonmod.pixelmon.entities.npcs.registry.DropItemRegistry;
import com.pixelmonmod.pixelmon.enums.EnumType;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Tuple;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RaidDropRecipeCategory extends DuckRecipeCategory<RaidDropRecipeWrapper> {
    public RaidDropRecipeCategory(IGuiHelper helper) {
        super("raiddrop", "jep.raiddrop", helper.createBlankDrawable(170, 50), new ItemStack(PixelmonItems.wishingPiece));
    }

    @Override
    @SuppressWarnings("unchecked")
    public void setupRecipes(IModRegistry registry) {
        List<RaidDropRecipeWrapper> recipes = new ArrayList<>();

        for (Map.Entry<Integer, HashMap<EnumType, WeightedItemStacks>> outer : DropItemRegistry.raidDrops.entrySet()) {
            for (Map.Entry<EnumType, WeightedItemStacks> inner : outer.getValue().entrySet()) {
                if (inner.getKey()==EnumType.Mystery){
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
        registry.addRecipes(recipes, getUid());
    }

    public static final int FIRST_ITEM_X = 5;
    public static final int FIRST_ITEM_Y = 15;
    @Override
    public void setRecipe(IRecipeLayout iRecipeLayout, RaidDropRecipeWrapper raidDropRecipeWrapper, IIngredients iIngredients) {
        int xOffset = 0;
        int yOffset = 0;
        int slot = 0;
        for (int i=0;i<2;i++)
        {
            for (int j=0;j<9;j++)
            {
                iRecipeLayout.getItemStacks().init(slot++, false, FIRST_ITEM_X + xOffset, FIRST_ITEM_Y+yOffset);
                xOffset += 18;
            }
            xOffset = 0;
            yOffset += 18;
        }

        iRecipeLayout.getItemStacks().addTooltipCallback(raidDropRecipeWrapper);


        slot = 0;
        for (int i=0;i<raidDropRecipeWrapper.getDrops().size();i++)
        {
            iRecipeLayout.getItemStacks().set(slot++, raidDropRecipeWrapper.getDrops().get(i).getItemStack());
        }
    }
}
