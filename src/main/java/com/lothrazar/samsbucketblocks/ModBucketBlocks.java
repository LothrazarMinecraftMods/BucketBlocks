package com.lothrazar.samsbucketblocks;

import java.util.ArrayList;

import org.apache.logging.log4j.Logger;

import com.lothrazar.samsblocks.BlockRegistry;
import com.lothrazar.samsblocks.CommonProxy;
import com.lothrazar.samsblocks.ConfigRegistry;
import com.lothrazar.samsblocks.ModBlocks;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = ModBucketBlocks.MODID, useMetadata=true)
public class ModBucketBlocks
{
    public static final String MODID = "samsbucketblocks";
	public static final String TEXTURE_LOCATION = ModBucketBlocks.MODID + ":"; 
	@Instance(value = ModBucketBlocks.MODID)
	public static ModBucketBlocks instance;
	public static Logger logger; 
	//public static ConfigRegistry cfg;
	@SidedProxy(clientSide="com.lothrazar.samsbucketblocks.ClientProxy", serverSide="com.lothrazar.samsbucketblocks.CommonProxy")
	public static CommonProxy proxy;  
	public static CreativeTabs tabSamsContent = new CreativeTabs("tabSamsBlocks") 
	{ 
		@Override
		public Item getTabIconItem() 
		{ 
			return Item.getItemFromBlock(BlockRegistry.block_storeempty);
		}
	};    
    
	@EventHandler
	public void onPreInit(FMLPreInitializationEvent event)
	{ 
		logger = event.getModLog();  
		
		//cfg = new ConfigRegistry(new Configuration(event.getSuggestedConfigurationFile()));

		BlockRegistry.registerBlocks();
		
		
		
		
	  	ArrayList<Object> handlers = new ArrayList<Object>();
	    
     	handlers.add(instance                         ); 
     	handlers.add(BlockRegistry.block_storelava    );//TODO: why are these four done so weirdly
		handlers.add(BlockRegistry.block_storewater   );
		handlers.add(BlockRegistry.block_storemilk    ); 
		handlers.add(BlockRegistry.block_storeempty   );   

     	for(Object h : handlers)
     		if(h != null)
	     	{ 
	    		FMLCommonHandler.instance().bus().register(h); 
	    		MinecraftForge.EVENT_BUS.register(h); 
	     	} 
	}
    @EventHandler
    public void init(FMLInitializationEvent event)
    {
		proxy.registerRenderers();
    }
    
    
    
	@SubscribeEvent
	public void onBreakEvent(BreakEvent event)
	{
		TileEntity ent = event.world.getTileEntity(event.pos);
		  
		//TODO; check tool/pickaxe? if notHarvestable or whatever, drop the buckets and the ..glass?
		 
		if(ent != null && ent instanceof TileEntityBucketStorage)
		{
			TileEntityBucketStorage t = (TileEntityBucketStorage)ent;
			ItemStack stack = new ItemStack(event.state.getBlock());
			
			ModBlocks.setItemStackNBT(stack, BlockBucketStorage.NBTBUCKETS, t.getBuckets());
		
			ModBlocks.dropItemStackInWorld(event.world, event.pos, stack);

			t.setBuckets(0);
		}
	}
}
