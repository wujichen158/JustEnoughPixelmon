package com.duckfox.jep.plugin.moveskill;

import com.duckfox.jep.api.item.MoveSkillItem;
import com.pixelmonmod.pixelmon.api.moveskills.MoveSkill;
import com.pixelmonmod.pixelmon.api.spawning.SpawnInfo;
import com.pixelmonmod.pixelmon.api.spawning.SpawnSet;
import com.pixelmonmod.pixelmon.api.spawning.archetypes.entities.collection.SpawnInfoCollection;
import com.pixelmonmod.pixelmon.api.spawning.archetypes.entities.items.SpawnInfoItem;
import com.pixelmonmod.pixelmon.spawning.PixelmonSpawning;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.RecipeType;

import java.util.Collections;
import java.util.List;
import java.util.ArrayList;
import java.util.Objects;

public class RockSmashRecipeCategory extends MoveSkillRecipeCategory<RockSmashRecipeWrapper> {
    public static final RecipeType<RockSmashRecipeWrapper> TYPE = RecipeType.create("justenoughpixelmon", "rock_smash", RockSmashRecipeWrapper.class);

    public RockSmashRecipeCategory(IGuiHelper helper) {
        super("rock_smash", TYPE, helper);
    }

    @Override
    public List<RockSmashRecipeWrapper> getRecipes() {
        List<RockSmashRecipeWrapper> recipes = new ArrayList<>();
        for (SpawnSet set : PixelmonSpawning.rocksmash) {
            if (Objects.equals(set.id, "Rock Smash Loot")) {
                SpawnInfoCollection spawnInfo = (SpawnInfoCollection) set.spawnInfos.get(0);
                for (SpawnInfo info : spawnInfo.collection) {
                    SpawnInfoItem spawnInfoItem = (SpawnInfoItem) info;
                    MoveSkillItem moveSkillItem = new MoveSkillItem(spawnInfoItem.itemStack, MoveSkill.getMoveSkillByID("rock_smash"), spawnInfoItem.rarity, spawnInfoItem.condition);
                    recipes.add(new RockSmashRecipeWrapper(moveSkillItem));
                }
                break;
            }
        }
        return recipes;
    }
}
