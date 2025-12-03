package com.duckfox.jep.plugin.moveskill;

import com.duckfox.jep.api.item.MoveSkillItem;
import com.pixelmonmod.pixelmon.api.moveskills.MoveSkill;
import com.pixelmonmod.pixelmon.api.spawning.SpawnInfo;
import com.pixelmonmod.pixelmon.api.spawning.SpawnSet;
import com.pixelmonmod.pixelmon.api.spawning.archetypes.entities.collection.SpawnInfoCollection;
import com.pixelmonmod.pixelmon.api.spawning.archetypes.entities.items.SpawnInfoItem;
import com.pixelmonmod.pixelmon.config.PixelmonItems;
import com.pixelmonmod.pixelmon.spawning.PixelmonSpawning;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.RecipeType;

import java.util.Collections;
import java.util.List;
import java.util.ArrayList;
import java.util.Objects;

public class ForageRecipeCategory extends MoveSkillRecipeCategory<ForageRecipeWrapper> {
    public static final RecipeType<ForageRecipeWrapper> TYPE = RecipeType.create("justenoughpixelmon", "forage", ForageRecipeWrapper.class);

    public ForageRecipeCategory(IGuiHelper helper) {
        super("forage", TYPE, helper);
    }

    @Override
    public List<ForageRecipeWrapper> getRecipes() {
        List<ForageRecipeWrapper> recipes = new ArrayList<>();
        for (SpawnSet set : PixelmonSpawning.forage) {
            if (Objects.equals(set.id, "Forage Loot")) {
                SpawnInfoCollection spawnInfo = (SpawnInfoCollection) set.spawnInfos.get(0);
                for (SpawnInfo info : spawnInfo.collection) {
                    SpawnInfoItem spawnInfoItem = (SpawnInfoItem) info;
                    MoveSkillItem moveSkillItem = new MoveSkillItem(spawnInfoItem.itemStack, MoveSkill.getMoveSkillByID("forage"), spawnInfoItem.rarity, spawnInfoItem.condition);
                    recipes.add(new ForageRecipeWrapper(moveSkillItem));
                }
                break;
            }
        }
        return recipes;
    }
}
