package com.duckfox.jep.plugin.moveskill;

import com.duckfox.jep.plugin.DuckRecipeCategory;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import net.minecraft.item.ItemStack;

public abstract class MoveSkillRecipeCategory<T extends MoveSkillRecipeWrapper> extends DuckRecipeCategory<T> {
    public MoveSkillRecipeCategory(String titleId, String uid, ItemStack catalyst, IGuiHelper helper) {
        super(titleId, uid, helper.createBlankDrawable(170,40), catalyst);
    }


    @Override
    public void setRecipe(IRecipeLayout iRecipeLayout, MoveSkillRecipeWrapper moveSkillRecipeWrapper, IIngredients iIngredients) {
        iRecipeLayout.getItemStacks().init(0,false,81,12);
        iRecipeLayout.getItemStacks().set(0,moveSkillRecipeWrapper.moveSkillItem.getItemStack());
        iRecipeLayout.getItemStacks().addTooltipCallback(moveSkillRecipeWrapper);

    }
}
