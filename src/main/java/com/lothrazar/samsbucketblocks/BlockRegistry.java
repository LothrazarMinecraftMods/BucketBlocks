package com.lothrazar.samsbucketblocks;

import java.util.ArrayList;

import com.lothrazar.samsblocks.BlockCommandBlockCraftable.CommandType;

import net.minecraft.block.Block;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
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
		if(ModBlocks.cfg.block_fragile)
		{
			block_fragile = new BlockFragile();
			BlockRegistry.registerBlock(block_fragile, "block_fragile"); 
			block_fragile.addRecipe();
		}
		
		  
		if(ModBlocks.cfg.storeBucketsBlock)
		{
			BlockRegistry.block_storewater = new BlockBucketStorage(Items.water_bucket);  
			registerBucketBlock(BlockRegistry.block_storewater, "block_storewater");

			BlockRegistry.block_storemilk = new BlockBucketStorage(Items.milk_bucket);  
			registerBucketBlock(BlockRegistry.block_storemilk, "block_storemilk");
		 
			BlockRegistry.block_storelava = new BlockBucketStorage(Items.lava_bucket);  
			registerBucketBlock(BlockRegistry.block_storelava, "block_storelava");	  
	
			GameRegistry.registerTileEntity(TileEntityBucketStorage.class, ModBlocks.MODID);
		
			BlockRegistry.block_storeempty = new BlockBucketStorage(null); //null for emtpy, no liquids stored inside
			BlockRegistry.block_storeempty.setCreativeTab(ModBlocks.tabSamsContent); 
			registerBucketBlock(BlockRegistry.block_storeempty, "block_storeempty");
			
			BlockRegistry.block_storeempty.addRecipe();
		}
		
		if(ModBlocks.cfg.shearSheepBlock)
		{
			BlockRegistry.block_shear_sheep = new BlockShearWool(); 
			
			BlockRegistry.registerBlock(BlockRegistry.block_shear_sheep, "block_shear_sheep");

			BlockShearWool.addRecipe();
		}
		
		if(ModBlocks.cfg.fishingNetBlock)
		{
			BlockRegistry.block_fishing = new BlockFishing(); 
			
			registerBlock(BlockRegistry.block_fishing, "block_fishing");

			BlockFishing.addRecipe();
		}
  
		if(ModBlocks.cfg.weatherBlock) 
		{
			BlockRegistry.command_block_weather = new BlockCommandBlockCraftable(CommandType.Weather);
	 
			BlockRegistry.registerBlock(BlockRegistry.command_block_weather,"command_block_weather");
	
			BlockCommandBlockCraftable.addRecipe(BlockRegistry.command_block_weather,new ItemStack(Items.water_bucket));
		}
		 
		if(ModBlocks.cfg.teleportSpawnBlock) 
		{ 
			BlockRegistry.command_block_tpspawn = new BlockCommandBlockCraftable(CommandType.TeleportSpawn);
	 
			BlockRegistry.registerBlock(BlockRegistry.command_block_tpspawn,"command_block_tpspawn");
	
			BlockCommandBlockCraftable.addRecipe(BlockRegistry.command_block_tpspawn,new ItemStack(Items.ender_eye));
		}

		if(ModBlocks.cfg.teleportBedBlock) 
		{ 
			BlockRegistry.command_block_tpbed = new BlockCommandBlockCraftable(CommandType.TeleportBed);
	 
			BlockRegistry.registerBlock(BlockRegistry.command_block_tpbed,"command_block_tpbed");
			
			BlockCommandBlockCraftable.addRecipe(BlockRegistry.command_block_tpbed,new ItemStack(Items.bed));
		} 
	}
}
