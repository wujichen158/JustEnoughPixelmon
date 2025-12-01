package com.duckfox.jep.plugin.boss;

import com.duckfox.jep.plugin.DuckRecipeCategory;
import com.pixelmonmod.pixelmon.config.PixelmonItemsPokeballs;
import com.pixelmonmod.pixelmon.entities.npcs.registry.DropItemRegistry;
import com.pixelmonmod.pixelmon.enums.EnumBossMode;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import net.minecraft.item.ItemStack;

import java.util.*;

public class BossDropRecipeCategory extends DuckRecipeCategory<BossDropRecipeWrapper> {
    public BossDropRecipeCategory(IGuiHelper helper) {
        super("bossdrop", "jep.bossdrop", helper.createBlankDrawable(170, 110), new ItemStack(PixelmonItemsPokeballs.parkBall));
    }

    @Override
    public void setupRecipes(IModRegistry registry) {
        List<BossDropRecipeWrapper> recipes = new ArrayList<>();

        for (Map.Entry<EnumBossMode, ArrayList<ItemStack>> outer : DropItemRegistry.bossDrops.entrySet()) {
            recipes.add(new BossDropRecipeWrapper(outer.getValue(), outer.getKey()));
        }
        recipes.sort(Comparator.comparingInt(o -> o.getMode().index));
        registry.addRecipes(recipes, getUid());
    }

    public static final int FIRST_ITEM_X = 5;
    public static final int FIRST_ITEM_Y = 15;
    @Override
    public void setRecipe(IRecipeLayout iRecipeLayout, BossDropRecipeWrapper bossDropRecipeWrapper, IIngredients iIngredients) {
        int xOffset = 0;
        int yOffset = 0;
        int slot = 0;
        for (int i=0;i<5;i++)
        {
            for (int j=0;j<9;j++)
            {
                iRecipeLayout.getItemStacks().init(slot++, false, FIRST_ITEM_X + xOffset, FIRST_ITEM_Y+yOffset);
                xOffset += 18;
            }
            xOffset = 0;
            yOffset += 18;
        }



        slot = 0;
        for (int i=0;i<bossDropRecipeWrapper.getItemStacks().size();i++)
        {
            iRecipeLayout.getItemStacks().set(slot++, bossDropRecipeWrapper.getItemStacks().get(i));
        }
    }
}
