package com.lothrazar.samsbucketblocks;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemBlockBucket extends ItemBlock
{
//http://www.minecraftforum.net/forums/mapping-and-modding/minecraft-mods/modification-development/1432714-forge-using-addinformation-on-a-block
	public ItemBlockBucket(Block block) 
	{
		super(block);
	}
	@Override
	public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean par4)
	{
		 list.add(BlockBucketStorage.getBucketsStored(itemstack)+"");
	}
}
