package com.lothrazar.samsbucketblocks;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.Action;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockBucketStorage extends Block implements ITileEntityProvider {
	private Item bucketItem;

	public BlockBucketStorage(Item bucketIn) {
		super(Material.iron);
		this.setHardness(7F);
		this.setResistance(7F);
		this.setStepSound(SoundType.METAL);
		this.setHarvestLevel("pickaxe", 1);
		bucketItem = bucketIn;
	}

	public static final String NBTBUCKETS = "buckets";

	public static int getBucketsStored(ItemStack item) {
		if (item.getItem() == Item.getItemFromBlock(BlockRegistry.block_storeempty))
			return 0;

		if (item.getTagCompound() == null) {
			item.setTagCompound(new NBTTagCompound());
		}
		return item.getTagCompound().getInteger(NBTBUCKETS) + 1;
	}

	public static int getItemStackBucketNBT(ItemStack item) {
		if (item.getTagCompound() == null) {
			item.setTagCompound(new NBTTagCompound());
		}
		return item.getTagCompound().getInteger(NBTBUCKETS);
	}

	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
		if (stack.getTagCompound() != null) {
			int b = BlockBucketStorage.getItemStackBucketNBT(stack);

			TileEntityBucketStorage container = (TileEntityBucketStorage) worldIn.getTileEntity(pos);

			container.setBuckets(b);
		}
	}

	// http://www.minecraftforge.net/forum/index.php?topic=18754.0
    public boolean isFullyOpaque(IBlockState state)
    {
        return true;//state.getMaterial().isOpaque() && state.isFullCube();
    }
	/*
	@Override
	public boolean isOpaqueCube() {
		return false;// transparency
	}*/

	@Override
	public boolean hasComparatorInputOverride(IBlockState state) {
		return true; 
	}

	@Override
	public int getComparatorInputOverride(IBlockState blockState,World world, BlockPos pos) {
		TileEntityBucketStorage container = (TileEntityBucketStorage) world.getTileEntity(pos);
		return container.getBuckets();
	}

	@SideOnly(Side.CLIENT)
	public BlockRenderLayer getBlockLayer() {
		return BlockRenderLayer.CUTOUT;//EnumWorldBlockLayer.CUTOUT;
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityBucketStorage(meta);
	}

	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		return null;
		// return Item.getItemFromBlock(BlockRegistry.block_storeempty);
	}

	@SubscribeEvent
	public void onPlayerInteract(PlayerInteractEvent event) {
		EntityPlayer entityPlayer = event.getEntityPlayer();
		BlockPos pos = event.getPos();
		World world = event.getWorld();
		EnumFacing face = event.getFace();
		EnumHand hand = entityPlayer.getActiveHand();
		ItemStack held = entityPlayer.getHeldItem(hand);

		if (pos == null) {
			return;
		}
		IBlockState bstate = world.getBlockState(pos);
		if (bstate == null) {
			return;
		}

		Block blockClicked = bstate.getBlock();

		if (blockClicked == null || blockClicked == Blocks.air) {
			return;
		}
		if ((blockClicked instanceof BlockBucketStorage) == false) {
			return;
		}

		BlockBucketStorage block = (BlockBucketStorage) blockClicked;

		TileEntityBucketStorage container = (TileEntityBucketStorage) world.getTileEntity(pos);

		if (entityPlayer.isSneaking() && Action.LEFT_CLICK_BLOCK == event.getAction() && this.bucketItem == null) {
			int inside;
			if (blockClicked == BlockRegistry.block_storeempty)
				inside = 0;
			else
				inside = container.getBuckets() + 1;// yess its messed up?
			entityPlayer.addChatMessage(new TextComponentTranslation(inside + ""));

			return;// no sounds just tell us how much
		}

		if (entityPlayer.isSneaking()) {
			return;
		}// consistent

		if (held == null && Action.RIGHT_CLICK_BLOCK == event.getAction()
				&& block.bucketItem != null && block.bucketItem == this.bucketItem) {
			if (container.getBuckets() > 0) {
				removeBucket(entityPlayer, world, container, block.bucketItem);
			}
			else // it is also empty
			{
				removeBucket(entityPlayer, world, container, block.bucketItem);
				world.setBlockState(pos, BlockRegistry.block_storeempty.getDefaultState());
			}
			world.updateComparatorOutputLevel(pos, blockClicked);
			//ModBucketBlocks.playSoundAt(event.entityPlayer, "tile.piston.out");
			world.playSound(pos.getX(), pos.getY(), pos.getZ(), SoundEvents.block_piston_extend, SoundCategory.BLOCKS, 1.0F, 1.0F,false);
			spawnMyParticle(world, block.bucketItem, pos.offset(face));

		}

		if (Action.LEFT_CLICK_BLOCK == event.getAction()) // LEFT CLICK DEPOSIT INTO
														// block
		{
			// before we add the bucket, wait and should we set the block first?
			if (blockClicked == BlockRegistry.block_storeempty && block.bucketItem == null && held != null) // then
																											// set
																											// this
																											// block
																											// based
																											// on
																											// bucket
			{
				IBlockState state = null;

				if (held.getItem() == Items.lava_bucket) {
					state = BlockRegistry.block_storelava.getDefaultState();
				}
				else if (held.getItem() == Items.water_bucket) {
					state = BlockRegistry.block_storewater.getDefaultState();
				}
				if (held.getItem() == Items.milk_bucket) {
					state = BlockRegistry.block_storemilk.getDefaultState();
				}

				if (state != null) {
					world.setBlockState(pos, state);
					container.addBucket();
					//entityPlayer.destroyCurrentEquippedItem();
					entityPlayer.inventory.decrStackSize(entityPlayer.inventory.currentItem, 1);

					world.updateComparatorOutputLevel(pos, blockClicked);

					//ModBucketBlocks.playSoundAt(event.entityPlayer, "tile.piston.in");
					world.playSound(pos.getX(), pos.getY(), pos.getZ(), SoundEvents.block_piston_extend, SoundCategory.BLOCKS, 1.0F, 1.0F,false);

					spawnMyParticle(world, held.getItem(), pos.offset(face));

				}
			}
			else if (held != null && held.getItem() == block.bucketItem) {

				//public void addBucket(EntityPlayer entityPlayer, World world, TileEntityBucketStorage storage) {
				container.addBucket();
				//entityPlayer.destroyCurrentEquippedItem();
				entityPlayer.inventory.decrStackSize(entityPlayer.inventory.currentItem, 1);
			
				world.updateComparatorOutputLevel(pos, blockClicked);
				//ModBucketBlocks.playSoundAt(event.entityPlayer, "tile.piston.in");
				world.playSound(pos.getX(), pos.getY(), pos.getZ(), SoundEvents.block_piston_extend, SoundCategory.BLOCKS, 1.0F, 1.0F,false);


				spawnMyParticle(world, block.bucketItem, pos.offset(face));

			}
		}
	}

	private void spawnMyParticle(World world, Item item, BlockPos pos) {
		if (item == Items.milk_bucket)
			ModBucketBlocks.spawnParticle(world, EnumParticleTypes.SNOW_SHOVEL, pos);
		else if (item == Items.lava_bucket)
			ModBucketBlocks.spawnParticle(world, EnumParticleTypes.LAVA, pos);
		else if (item == Items.water_bucket)
			ModBucketBlocks.spawnParticle(world, EnumParticleTypes.WATER_SPLASH, pos);
	}

	private void removeBucket(EntityPlayer entityPlayer, World world, TileEntityBucketStorage storage, Item bucketItem) {
		storage.removeBucket();

		ModBucketBlocks.dropItemStackInWorld(world, entityPlayer.getPosition(), new ItemStack(bucketItem));
	}

	public void addRecipe() {
		GameRegistry.addRecipe(new ItemStack(BlockRegistry.block_storeempty), "i i", " o ", "i i", 'o', Blocks.obsidian, 'i', Blocks.iron_block);

		// the filled ones are not crafted, only obtained when filled and then
		// harvested
	}
}
