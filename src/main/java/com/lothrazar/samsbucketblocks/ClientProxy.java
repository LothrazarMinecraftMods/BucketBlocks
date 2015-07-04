package com.lothrazar.samsbucketblocks;

import  net.minecraft.item.Item; 
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemModelMesher;
import net.minecraft.client.resources.model.ModelResourceLocation;

public class ClientProxy extends CommonProxy 
{  
 
 
    @Override
    public void registerRenderers() 
    {  
    	registerKeyBindings(); 

        registerModels();  
    }
     
	private void registerModels() 
	{
		//More info on proxy rendering
        //http://www.minecraftforge.net/forum/index.php?topic=27684.0
       //http://www.minecraftforum.net/forums/mapping-and-modding/minecraft-mods/modification-development/2272349-lessons-from-my-first-mc-1-8-mod
   
        ItemModelMesher mesher = Minecraft.getMinecraft().getRenderItem().getItemModelMesher();

        String name;
        Item item;
	 
        for(Block b : BlockRegistry.blocks)
        { 
        	item = Item.getItemFromBlock(b);
        	name = ModBlocks.TEXTURE_LOCATION + b.getUnlocalizedName().replaceAll("tile.", "");

   			mesher.register(item, 0, new ModelResourceLocation( name , "inventory"));	 
        }
         
    /*
        if(ModBlocks.cfg.respawn_egg)
        {
        	for(Object key : EntityList.entityEggs.keySet())
            {
            	mesher.register(ItemRegistry.respawn_egg, (Integer)key, new ModelResourceLocation(Reference.TEXTURE_LOCATION + "respawn_egg" , "inventory"));	 
            }
        }*/
	}

	private void registerKeyBindings() 
	{/*
		keyShiftUp = new KeyBinding(Reference.keyUpName, Keyboard.KEY_Y, Reference.keyCategoryInventory);
        ClientRegistry.registerKeyBinding(ClientProxy.keyShiftUp);
    
		keyShiftDown = new KeyBinding(Reference.keyDownName, Keyboard.KEY_H, Reference.keyCategoryInventory); 
        ClientRegistry.registerKeyBinding(ClientProxy.keyShiftDown); 

        keyBarUp = new KeyBinding(Reference.keyBarUpName, Keyboard.KEY_U, Reference.keyCategoryInventory);
        ClientRegistry.registerKeyBinding(ClientProxy.keyBarUp);
         
        keyBarDown = new KeyBinding(Reference.keyBarDownName, Keyboard.KEY_J, Reference.keyCategoryInventory); 
        ClientRegistry.registerKeyBinding(ClientProxy.keyBarDown);

        keyBindMacro1 = new KeyBinding(Reference.keyBind1Name, Keyboard.KEY_N, Reference.keyCategoryMacro); 
        ClientRegistry.registerKeyBinding(ClientProxy.keyBindMacro1);

        keyBindMacro2 = new KeyBinding(Reference.keyBind2Name, Keyboard.KEY_M, Reference.keyCategoryMacro); 
        ClientRegistry.registerKeyBinding(ClientProxy.keyBindMacro2);

        keyTransform = new KeyBinding(Reference.keyTransformName, Keyboard.KEY_V, Reference.keyCategoryBlocks); 
        ClientRegistry.registerKeyBinding(ClientProxy.keyTransform);
 
        keyPush = new KeyBinding(Reference.keyPushName, Keyboard.KEY_G, Reference.keyCategoryBlocks); 
        ClientRegistry.registerKeyBinding(ClientProxy.keyPush);
        keyPull = new KeyBinding(Reference.keyPullName, Keyboard.KEY_B,  Reference.keyCategoryBlocks); 
        ClientRegistry.registerKeyBinding(ClientProxy.keyPull);
         */
	}
/*
	public static String getKeyDescription(int key)
	{
		//getKeyDescription gets something like 'key.macro1' like lang file data
		
		//thanks http://stackoverflow.com/questions/10893455/getting-keytyped-from-the-keycode
	 
		KeyBinding binding = null;
		switch(key)//TODO:...maybe find better way. switch for now
		{
		case 1:
			binding = keyBindMacro1;
			break;
		case 2:
			binding = keyBindMacro2;
			break;
		}
		
		 
		if(binding == null)
			return "";
		else
			return GameSettings.getKeyDisplayString(binding.getKeyCode());
			//return I18n.format(binding.getKeyDescription(), new Object[0]);
			//return java.awt.event.KeyEvent.getKeyText(binding.getKeyCode());
	}*/
}
