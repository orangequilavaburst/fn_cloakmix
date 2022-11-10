package xyz.j8bit_forager.cloakmix.item.custom;

import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import xyz.j8bit_forager.cloakmix.CloakMix;

import javax.annotation.Nullable;
import java.util.List;

public class SpectralCloakItem extends ModCloakItem{

    private final String translatableName = "spectral_cloak";
    private final SpectralCloakTypes cloakType;
    public SpectralCloakItem(ArmorMaterial material, Properties settings, SpectralCloakTypes cloakType) {
        super(material, settings);
        this.cloakType = cloakType;
    }


    public SpectralCloakTypes getCloakType() {
        return cloakType;
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> toolTip, TooltipFlag flagIn) {

        String tipItemName = stack.getItem().toString();
        tipItemName = "tooltip." + CloakMix.MOD_ID + "." + tipItemName;
        toolTip.add(Component.translatable(tipItemName).withStyle(ChatFormatting.GRAY));
        toolTip.add(Component.empty());

        tipItemName += ".desc";
        if (Screen.hasShiftDown()) {
            toolTip.add(Component.translatable(tipItemName).withStyle(ChatFormatting.DARK_GRAY));
        }
        else{
            toolTip.add(Component.translatable("tooltip." + CloakMix.MOD_ID + "." + translatableName + ".view_desc").withStyle(ChatFormatting.DARK_GRAY).append(Component.literal(" [SHIFT]").withStyle(ChatFormatting.YELLOW, ChatFormatting.BOLD)));
        }

        super.appendHoverText(stack, worldIn, toolTip, flagIn);
    }

}
