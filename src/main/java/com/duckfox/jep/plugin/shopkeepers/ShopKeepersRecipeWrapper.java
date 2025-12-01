package com.duckfox.jep.plugin.shopkeepers;

import com.duckfox.jep.utils.Reflections;
import com.duckfox.jep.utils.StringUtils;
import com.pixelmonmod.pixelmon.client.gui.GuiHelper;
import com.pixelmonmod.pixelmon.entities.npcs.registry.BaseShopItem;
import com.pixelmonmod.pixelmon.entities.npcs.registry.ShopItem;
import com.pixelmonmod.pixelmon.entities.npcs.registry.ShopkeeperData;
import mezz.jei.api.gui.ITooltipCallback;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;

import java.util.List;
import java.util.stream.Collectors;

public class ShopKeepersRecipeWrapper implements IRecipeWrapper, ITooltipCallback<ItemStack> {
    private final ShopkeeperData data;
    private List<ShopItem> items;

    @SuppressWarnings("unchecked")
    public List<ShopItem> getShopItems() {
        if (items == null) {
            items = Reflections.get(data, "items", List.class);
        }
        return items;
    }

    public ShopKeepersRecipeWrapper(ShopkeeperData data) {
        this.data = data;
    }

    @Override
    public void getIngredients(IIngredients iIngredients) {
        iIngredients.setOutputs(VanillaTypes.ITEM, getShopItems().stream().map(ShopItem::getItemStack).collect(Collectors.toList()));
    }

    @Override
    @SuppressWarnings("unchecked")
    public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
        List<String> names = Reflections.get(data, "names", List.class);
        int x = recipeWidth/2;
        GuiHelper.drawCenteredString(I18n.format("jep.shopkeepers.names"), x, 0, 0xffffff, true);
        List<String> lines = StringUtils.joinAndSplitToLines(names, ",", 32);
        int offset = 0;
        for (String line : lines) {
            GuiHelper.drawCenteredString(line, x, 10 + offset, 0xffffff, true);
            offset += 10;
        }
        List<String> biomes = Reflections.get(data, "biomes", List.class);
        GuiHelper.drawCenteredString(I18n.format("jep.condition.biome"), x, 10 + offset, 0xffffff, true);
        lines = StringUtils.joinAndSplitToLines(biomes, ",", 32);
        for (String line : lines) {
            GuiHelper.drawCenteredString(line, x, 20 + offset, 0xffffff, true);
            offset += 10;
        }
        GuiHelper.drawCenteredString(I18n.format("jep.shopkeepers.type", data.type.toString()), x, 20 + offset, 0xffffff, true);
    }


    @Override
    public void onTooltip(int i, boolean b, ItemStack itemStack, List<String> list) {
        for (ShopItem item : getShopItems()) {
            if (item.getItemStack().equals(itemStack)) {
                list.add(I18n.format("jep.shopkeepers.rarity", (item.getRarity() * 100) + "%"));
                BaseShopItem baseItem = item.getBaseItem();
                Boolean canVary = Reflections.get(item, "canPriceVary", Boolean.class);
                Integer buy = Reflections.get(baseItem, "buy", Integer.class);
                Integer sell = Reflections.get(baseItem, "sell", Integer.class);
                list.add(I18n.format("jep.shopkeepers.price.price", buy, sell));
                if (canVary) {
                    list.add(I18n.format("jep.shopkeepers.price.canvary"));
                    list.add(I18n.format("jep.shopkeepers.price.varyrange"));
                }
            }
        }
    }
}
