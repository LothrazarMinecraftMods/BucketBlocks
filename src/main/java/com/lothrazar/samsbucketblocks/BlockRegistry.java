package com.lothrazar.samsbucketblocks;

import java.util.ArrayList; 

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items; 
import net.minecraftforge.fml.common.registry.GameRegistry; 
  
public class BlockRegistry 
{  

	public static BlockBucketStorage block_storelava;
	public static BlockBucketStorage block_storewater;
	public static BlockBucketStorage block_storemilk;
	public static BlockBucketStorage block_storeempty;

	//store blocks in a list - because this is used by the ModelMesher in the client proxy later
	public static ArrayList<Block> blocks = new ArrayList<Block>();
	
	public static void registerBlock(Block s, String name)
	{    
		s.setUnlocalizedName(name); 
		 
		GameRegistry.registerBlock(s, name);
		  
		blocks.add(s);
	}
	public static void registerBucketBlock(Block s, String name)
	{    
		s.setUnlocalizedName(name); 
		 
		GameRegistry.registerBlock(s, ItemBlockBucket.class,name);
		  
		blocks.add(s);
	}
	public static void registerBlocks() 
	{  
 
 
		BlockRegistry.block_storewater = new BlockBucketStorage(Items.water_bucket);  
		registerBucketBlock(BlockRegistry.block_storewater, "block_storewater");

		BlockRegistry.block_storemilk = new BlockBucketStorage(Items.milk_bucket);  
		registerBucketBlock(BlockRegistry.block_storemilk, "block_storemilk");
	 
		BlockRegistry.block_storelava = new BlockBucketStorage(Items.lava_bucket);  
		registerBucketBlock(BlockRegistry.block_storelava, "block_storelava");	  

		GameRegistry.registerTileEntity(TileEntityBucketStorage.class, ModBucketBlocks.MODID);
	
		BlockRegistry.block_storeempty = new BlockBucketStorage(null); //null for emtpy, no liquids stored inside
		BlockRegistry.block_storeempty.setCreativeTab(CreativeTabs.tabMisc); 
		registerBucketBlock(BlockRegistry.block_storeempty, "block_storeempty");
		
		BlockRegistry.block_storeempty.addRecipe();
	
	}
}
