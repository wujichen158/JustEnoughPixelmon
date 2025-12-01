package com.duckfox.jep.plugin.shopkeepers;

import com.duckfox.jep.plugin.DuckRecipeCategory;
import com.pixelmonmod.pixelmon.config.PixelmonItemsHeld;
import com.pixelmonmod.pixelmon.entities.npcs.registry.NPCRegistryData;
import com.pixelmonmod.pixelmon.entities.npcs.registry.ServerNPCRegistry;
import com.pixelmonmod.pixelmon.entities.npcs.registry.ShopkeeperData;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class ShopKeepersRecipeCategory extends DuckRecipeCategory<ShopKeepersRecipeWrapper> {
    public ShopKeepersRecipeCategory(IGuiHelper helper) {
        super("shopkeepers", "jep.shopkeepers", helper.createBlankDrawable(170, 150), new ItemStack(PixelmonItemsHeld.amuletCoin));
    }

    @Override
    public void setupRecipes(IModRegistry registry) {
        List<ShopKeepersRecipeWrapper> recipes = new ArrayList<>();
        NPCRegistryData registryData = ServerNPCRegistry.data.get(ServerNPCRegistry.en_us);
        ArrayList<ShopkeeperData> list = registryData.shopkeepers;
        list.addAll(registryData.shopkeeperSpawns);
        for (ShopkeeperData data : list) {
            ShopKeepersRecipeWrapper wrapper = new ShopKeepersRecipeWrapper(data);
            recipes.add(wrapper);
        }
        registry.addRecipes(recipes, getUid());
    }

    @Override
    public void setRecipe(IRecipeLayout iRecipeLayout, ShopKeepersRecipeWrapper wrapper, IIngredients iIngredients) {
        int itemsPerRow = wrapper.getShopItems().size() > 54 ? 18 : 9; // 每行最多显示的物品数
        int xStart = 0; // 起始X坐标
        int yStart = 50; // 起始Y坐标
        int xSpacing = 18; // X方向间距
        int ySpacing = 18; // Y方向间距
        iRecipeLayout.getItemStacks().addTooltipCallback(wrapper);


        for (int i = 0; i < wrapper.getShopItems().size(); i++) {
            int row = i / itemsPerRow; // 计算当前行
            int col = i % itemsPerRow; // 计算当前列

            iRecipeLayout.getItemStacks().init(i, false, xStart + col * xSpacing, yStart + row * ySpacing);
            iRecipeLayout.getItemStacks().set(i, wrapper.getShopItems().get(i).getItemStack());
        }
    }
}
