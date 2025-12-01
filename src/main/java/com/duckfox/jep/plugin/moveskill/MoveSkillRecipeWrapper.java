package com.duckfox.jep.plugin.moveskill;

import com.duckfox.jep.api.item.MoveSkillItem;
import com.duckfox.jep.utils.StringUtils;
import com.pixelmonmod.pixelmon.api.spawning.conditions.SpawnCondition;
import com.pixelmonmod.pixelmon.api.world.WeatherType;
import com.pixelmonmod.pixelmon.api.world.WorldTime;
import com.pixelmonmod.pixelmon.client.gui.GuiHelper;
import mezz.jei.api.gui.ITooltipCallback;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.world.biome.Biome;

import java.util.ArrayList;
import java.util.List;

public class MoveSkillRecipeWrapper implements IRecipeWrapper, ITooltipCallback<ItemStack> {
    public MoveSkillItem moveSkillItem;

    protected MoveSkillRecipeWrapper(MoveSkillItem moveSkillItem) {
        this.moveSkillItem = moveSkillItem;
    }

    @Override
    public void getIngredients(IIngredients iIngredients) {
        iIngredients.setOutput(VanillaTypes.ITEM, moveSkillItem.getItemStack());
    }

    @Override
    public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
        GuiHelper.drawCenteredString(I18n.format("jep.moveskill.text",
                        I18n.format(moveSkillItem.getMoveSkill().name),
                        moveSkillItem.getItemStack().getDisplayName()),
                84, 2, 0xffffff, true);

        // 只保留名称显示，其他信息移到tooltip
        GuiHelper.drawCenteredString(I18n.format("jep.condition.rarity",
                        moveSkillItem.getRarity()),
                84, 30, 0xffffff, true);
    }

    @Override
    public void onTooltip(int slotIndex, boolean input, ItemStack ingredient, List<String> tooltip) {
        // 添加基础信息
        tooltip.add(I18n.format("jep.condition.rarity", moveSkillItem.getRarity()));

        SpawnCondition condition = moveSkillItem.getSpawnCondition();
        if (condition == null) {
            return;
        }

        // 添加时间条件
        if (condition.times != null) {
            tooltip.add(I18n.format("jep.condition.time"));
            for (WorldTime time : condition.times) {
                tooltip.add("  " + time.getLocalizedName());
            }
        }

        // 添加天气条件
        if (condition.weathers != null) {
            tooltip.add(I18n.format("jep.condition.weather"));
            for (WeatherType weather : condition.weathers) {
                tooltip.add("  " + weather.getLocalizedName());
            }
        }

        // 添加生物群系条件
        if (condition.biomes != null && !condition.biomes.isEmpty()) {
            tooltip.add(I18n.format("jep.condition.biome"));
            List<String> names = new ArrayList<>();
            for (Biome biome : condition.biomes) {
                names.add(biome.getBiomeName());
            }
            for (String line : StringUtils.joinAndSplitToLines(names, ", ", 50)) {
                tooltip.add("  " + line);
            }
        }

        // 添加其他条件
        if (condition.seesSky != null) {
            tooltip.add(I18n.format("jep.condition.seesky", condition.seesSky));
        }
        if (condition.maxY != null) {
            tooltip.add(I18n.format("jep.condition.maxy", condition.maxY));
        }
        if (condition.minY != null) {
            tooltip.add(I18n.format("jep.condition.miny", condition.minY));
        }
    }
}