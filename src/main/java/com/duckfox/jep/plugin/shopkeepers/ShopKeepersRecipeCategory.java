package com.duckfox.jep.plugin.shopkeepers;

import com.duckfox.jep.plugin.DuckRecipeCategory;
import com.pixelmonmod.pixelmon.entities.npcs.registry.NPCRegistryData;
import com.pixelmonmod.pixelmon.entities.npcs.registry.ServerNPCRegistry;
import com.pixelmonmod.pixelmon.entities.npcs.registry.ShopItem;
import com.pixelmonmod.pixelmon.entities.npcs.registry.ShopkeeperData;
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

public class ShopKeepersRecipeCategory extends DuckRecipeCategory<ShopKeepersRecipeWrapper> {
    public static final RecipeType<ShopKeepersRecipeWrapper> TYPE = RecipeType.create("justenoughpixelmon", "shopkeepers", ShopKeepersRecipeWrapper.class);

    public ShopKeepersRecipeCategory(IGuiHelper helper) {
        super("shopkeepers", TYPE, helper.createBlankDrawable(170, 150), helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(Items.PAPER)));
    }

    @Override
    public List<ShopKeepersRecipeWrapper> getRecipes() {
        List<ShopKeepersRecipeWrapper> recipes = new ArrayList<>();
        NPCRegistryData registryData = ServerNPCRegistry.data.get(ServerNPCRegistry.en_us);
        List<ShopkeeperData> list = new ArrayList<>(registryData.shopkeepers);
        list.addAll(registryData.shopkeeperSpawns);
        for (ShopkeeperData data : list) {
            List<ShopItem> items = new ArrayList<>(data.items);
            recipes.add(new ShopKeepersRecipeWrapper(data, items));
        }
        return recipes;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, ShopKeepersRecipeWrapper recipe, IFocusGroup focuses) {
        int itemsPerRow = recipe.getShopItems().size() > 54 ? 18 : 9;
        int xStart = 0;
        int yStart = 50;
        int xSpacing = 18;
        int ySpacing = 18;
        for (int i = 0; i < recipe.getShopItems().size(); i++) {
            int row = i / itemsPerRow;
            int col = i % itemsPerRow;
            builder.addSlot(RecipeIngredientRole.OUTPUT, xStart + col * xSpacing, yStart + row * ySpacing)
                    .addItemStack(recipe.getShopItems().get(i).getItemStack());
        }
    }
}
