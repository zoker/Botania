/**
 * This class was created by <Vazkii>. It's distributed as
 * part of the Botania Mod. Get the Source Code in github:
 * https://github.com/Vazkii/Botania
 *
 * Botania is Open Source and distributed under a
 * Creative Commons Attribution-NonCommercial-ShareAlike 3.0 License
 * (http://creativecommons.org/licenses/by-nc-sa/3.0/deed.en_GB)
 *
 * File Created @ [Jan 25, 2014, 2:04:15 PM (GMT)]
 */
package vazkii.botania.common.item.block;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import vazkii.botania.api.BotaniaAPI;
import vazkii.botania.api.lexicon.IRecipeKeyProvider;
import vazkii.botania.api.subtile.SubTileEntity;
import vazkii.botania.common.block.ModBlocks;
import vazkii.botania.common.block.tile.TileSpecialFlower;
import vazkii.botania.common.core.helper.ItemNBTHelper;
import vazkii.botania.common.lib.LibBlockNames;

import java.util.List;

public class ItemBlockSpecialFlower extends ItemBlock implements IRecipeKeyProvider {

    public ItemBlockSpecialFlower(Block block1) {
        super(block1);
    }

    @Override
    public IIcon getIconIndex(ItemStack stack) {
        return BotaniaAPI.internalHandler.getSubTileIconForName(getType(stack));
    }

    @Override
    public IIcon getIcon(ItemStack stack, int pass) {
        return getIconIndex(stack);
    }

    @Override
    public boolean placeBlockAt(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ, int metadata) {
        boolean placed = super.placeBlockAt(stack, player, world, x, y, z, side, hitX, hitY, hitZ, metadata);
        if(placed) {
            String type = getType(stack);
            TileSpecialFlower tile = (TileSpecialFlower) world.getTileEntity(x, y, z);
            tile.setSubTile(type);
            if(!world.isRemote) world.markBlockForUpdate(x, y, z);
        }

        return placed;
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        return "tile." + LibBlockNames.SPECIAL_FLOWER_PREFIX + getType(stack);
    }

    @Override
    public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4) {
        String refUnlocalized = "tile." + LibBlockNames.SPECIAL_FLOWER_PREFIX + getType(par1ItemStack) + ".reference";
        String refLocalized = StatCollector.translateToLocal(refUnlocalized);
        if(!refLocalized.equals(refUnlocalized)) par3List.add(EnumChatFormatting.ITALIC + refLocalized);
    }

    public static String getType(ItemStack stack) {
        return ItemNBTHelper.getString(stack, SubTileEntity.TAG_TYPE, "");
    }

    public static ItemStack ofType(String type) {
        ItemStack stack = new ItemStack(ModBlocks.specialFlower);
        ItemNBTHelper.setString(stack, SubTileEntity.TAG_TYPE, type);
        return stack;
    }

    @Override
    public String getKey(ItemStack stack) {
        return getType(stack);
    }

}
