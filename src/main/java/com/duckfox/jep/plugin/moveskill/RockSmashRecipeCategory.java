package com.duckfox.jep.plugin.moveskill;

import com.duckfox.jep.api.item.MoveSkillItem;
import com.pixelmonmod.pixelmon.api.moveskills.MoveSkill;
import com.pixelmonmod.pixelmon.api.spawning.SpawnInfo;
import com.pixelmonmod.pixelmon.api.spawning.SpawnSet;
import com.pixelmonmod.pixelmon.api.spawning.archetypes.entities.collection.SpawnInfoCollection;
import com.pixelmonmod.pixelmon.api.spawning.archetypes.entities.items.SpawnInfoItem;
import com.pixelmonmod.pixelmon.spawning.PixelmonSpawning;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class RockSmashRecipeCategory extends MoveSkillRecipeCategory<RockSmashRecipeWrapper> {
    public RockSmashRecipeCategory(IGuiHelper helper) {
        super("rock_smash", "jep.rock_smash", new ItemStack(Blocks.STONE), helper);
    }

    public static final MoveSkill ROCKSMASH_MOVE_SKILL = MoveSkill.getMoveSkillByID("rock_smash");

    @Override
    public void setupRecipes(IModRegistry registry) {
        List<RockSmashRecipeWrapper> recipes = new ArrayList<>();

        for (SpawnSet set : PixelmonSpawning.rocksmash) {
            if (Objects.equals(set.id, "Rock Smash Loot")) {
                SpawnInfoCollection spawnInfo = (SpawnInfoCollection) set.spawnInfos.get(0);
                for (SpawnInfo info : spawnInfo.collection) {
                    SpawnInfoItem spawnInfoItem = (SpawnInfoItem) info;
                    MoveSkillItem moveSkillItem = new MoveSkillItem(spawnInfoItem.itemStack, ROCKSMASH_MOVE_SKILL, spawnInfoItem.rarity, spawnInfoItem.condition);
                    recipes.add(new RockSmashRecipeWrapper(moveSkillItem));
                }
                break;
            }
        }
        registry.addRecipes(recipes, getUid());
    }

}
