package com.duckfox.jep.plugin.pokemon;

import com.duckfox.jep.utils.PokeSprites;
import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import com.pixelmonmod.pixelmon.api.pokemon.drops.ItemWithChance;
import com.pixelmonmod.pixelmon.api.pokemon.drops.PokemonDropInformation;
import com.pixelmonmod.pixelmon.api.pokemon.species.Species;
import com.pixelmonmod.pixelmon.api.util.helpers.SpriteItemHelper;
import com.pixelmonmod.pixelmon.client.gui.ScreenHelper;
import mezz.jei.api.gui.builder.ITooltipBuilder;
import mezz.jei.api.gui.ingredient.IRecipeSlotRichTooltipCallback;
import mezz.jei.api.gui.ingredient.IRecipeSlotView;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.HolderLookup;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.stream.Collectors;

public class PokeDropRecipeWrapper implements IRecipeSlotRichTooltipCallback {
    // TODO: new
    private Pokemon pokemon;
    private final List<ItemWithChance> drops;
    private int form = -10086;

    @Override
    public void getRecipeIngredients(IIngredients iIngredients) {
        if (form != -10086) {
            iIngredients.setInput(VanillaTypes.ITEM, PokeSprites.forceNewSpriteWithForm(species, form));
        } else {
            iIngredients.setInput(VanillaTypes.ITEM, PokeSprites.getSprite(species));
        }
        iIngredients.setOutputs(VanillaTypes.ITEM, this.drops.stream().map(drop -> List.of(drop.getItemStack())).flatMap(List::stream).collect(Collectors.toList()));

    }

    public void draw(GuiGraphics graphics, double mouseX, double mouseY) {
        ScreenHelper.drawCenteredString(graphics, getName(), 40, 4, 0xffffff, true);
        ScreenHelper.drawCenteredString(graphics, Component.translatable("jep.pokedrop.text"), 135, 4, 0xffffff, true);
    }

    public Component getName() {
        return Component.literal("No.").append(this.pokemon.getSpecies().getFormattedDex()).append(" ").append(this.pokemon.getDisplayName());
    }

    public void getTooltips(ItemStack itemStack, List<Component> list) {
        for (ItemWithChance drop : drops) {
            if (drop.getItemStack().equals(itemStack)) {
                list.add(genDropTooltips(drop));
            }
        }
    }

    public Component genDropTooltips(ItemWithChance drop) {
        return Component.literal("§r").append(Component.translatable("jep.tooltip.poke.drop.min", drop.getMin()))
                .append("§r").append(Component.translatable("jep.tooltip.poke.drop.max", drop.getMax()))
                .append("§r").append(Component.translatable("jep.tooltip.poke.drop.rate", String.format("%.2f%%", drop.getChance() * 100)));
    }

    public PokeDropRecipeWrapper(PokemonDropInformation info) {
        this.pokemon = info.getPokemonSpec().create();
        this.drops = info.getDrops();
    }

    public Species getSpecies() {
        return this.pokemon.getSpecies();
    }

    public List<ItemWithChance> getDrops() {
        return this.drops;
    }

    public ResourceLocation getSpriteResource() {
        return this.pokemon.getSprite();
    }

    public ItemStack getSprite() {
        return SpriteItemHelper.getPhoto(this.pokemon);
    }

    @Override
    public void onRichTooltip(@NotNull IRecipeSlotView recipeSlotView, @NotNull ITooltipBuilder tooltip) {

    }
}