package com.lothrazar.samsbucketblocks;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
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
	public void addInformation(ItemStack item, EntityPlayer player, List list, boolean par4)
	{
		if(item.getItem() != Item.getItemFromBlock(BlockRegistry.block_storeempty))
			list.add(BlockBucketStorage.getBucketsStored(item)+"");
		//else it is the Empty version
	}
}
