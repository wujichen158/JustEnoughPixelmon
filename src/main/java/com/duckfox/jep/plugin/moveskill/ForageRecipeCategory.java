package com.duckfox.jep.plugin.moveskill;

import com.duckfox.jep.api.item.MoveSkillItem;
import com.pixelmonmod.pixelmon.api.moveskills.MoveSkill;
import com.pixelmonmod.pixelmon.api.spawning.SpawnInfo;
import com.pixelmonmod.pixelmon.api.spawning.SpawnSet;
import com.pixelmonmod.pixelmon.api.spawning.archetypes.entities.collection.SpawnInfoCollection;
import com.pixelmonmod.pixelmon.api.spawning.archetypes.entities.items.SpawnInfoItem;
import com.pixelmonmod.pixelmon.config.PixelmonItems;
import com.pixelmonmod.pixelmon.spawning.PixelmonSpawning;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ForageRecipeCategory extends MoveSkillRecipeCategory<ForageRecipeWrapper> {
    public ForageRecipeCategory(IGuiHelper helper) {
        super("forage", "jep.forage", new ItemStack(PixelmonItems.curryFancyApple), helper);
    }

    public static final MoveSkill FORAGE_MOVE_SKILL = MoveSkill.getMoveSkillByID("forage");

    @Override
    public void setupRecipes(IModRegistry registry) {
        List<ForageRecipeWrapper> recipes = new ArrayList<>();

        for (SpawnSet set : PixelmonSpawning.forage) {
            if (Objects.equals(set.id, "Forage Loot")) {
                SpawnInfoCollection spawnInfo = (SpawnInfoCollection) set.spawnInfos.get(0);
                for (SpawnInfo info : spawnInfo.collection) {
                    SpawnInfoItem spawnInfoItem = (SpawnInfoItem) info;
                    MoveSkillItem moveSkillItem = new MoveSkillItem(spawnInfoItem.itemStack, FORAGE_MOVE_SKILL, spawnInfoItem.rarity, spawnInfoItem.condition);
                    recipes.add(new ForageRecipeWrapper(moveSkillItem));
                }
                break;
            }
        }
        registry.addRecipes(recipes, getUid());
    }
}
