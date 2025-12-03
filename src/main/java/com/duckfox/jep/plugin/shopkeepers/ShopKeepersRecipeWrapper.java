package com.duckfox.jep.plugin.shopkeepers;
import com.pixelmonmod.pixelmon.entities.npcs.registry.ShopItem;
import com.pixelmonmod.pixelmon.entities.npcs.registry.ShopkeeperData;

import java.util.List;

public class ShopKeepersRecipeWrapper {
    private final ShopkeeperData data;
    private final List<ShopItem> shopItems;

    public ShopKeepersRecipeWrapper(ShopkeeperData data, List<ShopItem> shopItems) {
        this.data = data;
        this.shopItems = shopItems;
    }

    public ShopkeeperData getData() {
        return data;
    }

    public List<ShopItem> getShopItems() {
        return shopItems;
    }
}
