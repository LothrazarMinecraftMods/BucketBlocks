package com.lothrazar.samsbucketblocks;

import net.minecraft.init.Blocks;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;

@Mod(modid = ExampleMod.MODID, useMetadata=true)
public class ExampleMod
{
    public static final String MODID = "samsbucketblocks";
    
    @EventHandler
    public void init(FMLInitializationEvent event)
    {
		//TODO: importing things from /Blocks/ mod
        System.out.println("DIRT BLOCK >> "+Blocks.dirt.getUnlocalizedName());
    }
}
