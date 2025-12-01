package com.duckfox.jep.plugin;

import com.duckfox.jep.JustEnoughPixelmon;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.recipe.IRecipeCategory;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public abstract class DuckRecipeCategory<T extends IRecipeWrapper> implements IRecipeCategory<T> {
    private String titleId;
    private String uid;
    private IDrawable background;
    private ItemStack catalyst;

    public DuckRecipeCategory(String titleId,String uid, IDrawable background, ItemStack catalyst) {
        this.titleId = titleId;
        this.uid = uid;
        this.background = background;
        this.catalyst = catalyst;
    }

    public void setup(IModRegistry reg) {
        reg.addRecipeCatalyst(catalyst, uid);
        setupRecipes(reg);
    }

    public abstract void setupRecipes(IModRegistry reg);

    @Override
    public String getTitle() {
        return I18n.format("jep.category."+titleId+".name");
    }

    @Override
    public String getUid() {
        return uid;
    }

    @Override
    public String getModName() {
        return JustEnoughPixelmon.NAME;
    }

    @Override
    public IDrawable getBackground() {
        return background;
    }
}
