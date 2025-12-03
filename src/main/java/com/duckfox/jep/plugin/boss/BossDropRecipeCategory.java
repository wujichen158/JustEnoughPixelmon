package com.duckfox.jep.plugin.boss;

import com.duckfox.jep.plugin.DuckRecipeCategory;
import com.pixelmonmod.pixelmon.entities.npcs.registry.DropItemRegistry;
import com.pixelmonmod.pixelmon.enums.EnumBossMode;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class BossDropRecipeCategory extends DuckRecipeCategory<BossDropRecipeWrapper> {
    public static final RecipeType<BossDropRecipeWrapper> TYPE = RecipeType.create("justenoughpixelmon", "bossdrop", BossDropRecipeWrapper.class);

    public BossDropRecipeCategory(IGuiHelper helper) {
        super("bossdrop", TYPE, helper.createBlankDrawable(170, 110), helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(Items.PAPER)));
    }

    @Override
    public List<BossDropRecipeWrapper> getRecipes() {
        List<BossDropRecipeWrapper> recipes = new ArrayList<>();
        for (Map.Entry<EnumBossMode, ArrayList<ItemStack>> outer : DropItemRegistry.bossDrops.entrySet()) {
            recipes.add(new BossDropRecipeWrapper(outer.getValue(), outer.getKey()));
        }
        recipes.sort(Comparator.comparingInt(o -> o.getMode().index));
        return recipes;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, BossDropRecipeWrapper recipe, IFocusGroup focuses) {
        int xOffset = 0;
        int yOffset = 0;
        int slotX = 5;
        int slotY = 15;
        int col = 0;
        int row = 0;
        for (ItemStack stack : recipe.getItemStacks()) {
            builder.addSlot(RecipeIngredientRole.OUTPUT, slotX + xOffset, slotY + yOffset).addItemStack(stack);
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
