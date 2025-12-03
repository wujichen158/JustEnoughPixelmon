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
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.item.ItemStack;

import java.util.Collections;
import java.util.List;
import java.util.ArrayList;
import java.util.Objects;

public class HeadbuttRecipeCategory extends MoveSkillRecipeCategory<HeadbuttRecipeWrapper>{
    public static final RecipeType<HeadbuttRecipeWrapper> TYPE = RecipeType.create("justenoughpixelmon", "headbutt", HeadbuttRecipeWrapper.class);

    public HeadbuttRecipeCategory(IGuiHelper helper) {
        super("headbutt", TYPE, helper);
    }

    @Override
    public List<HeadbuttRecipeWrapper> getRecipes() {
        List<HeadbuttRecipeWrapper> recipes = new ArrayList<>();
        for (SpawnSet set : PixelmonSpawning.headbutt) {
            if (Objects.equals(set.id, "Headbutt Loot")) {
                SpawnInfoCollection spawnInfo = (SpawnInfoCollection) set.spawnInfos.get(0);
                for (SpawnInfo info : spawnInfo.collection) {
                    SpawnInfoItem spawnInfoItem = (SpawnInfoItem) info;
                    MoveSkillItem moveSkillItem = new MoveSkillItem(spawnInfoItem.itemStack, MoveSkill.getMoveSkillByID("headbutt"), spawnInfoItem.rarity, spawnInfoItem.condition);
                    recipes.add(new HeadbuttRecipeWrapper(moveSkillItem));
                }
                break;
            }
        }
        return recipes;
    }
}
