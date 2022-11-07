package xyz.j8bit_forager.cloakmix.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import xyz.j8bit_forager.cloakmix.CloakMix;
import xyz.j8bit_forager.cloakmix.block.ModBlocks;
import xyz.j8bit_forager.cloakmix.item.ModItems;
import xyz.j8bit_forager.cloakmix.screen.SpectralLoomMenu;

public class SpectralLoomBlockEntity extends BlockEntity implements MenuProvider {

    private final ItemStackHandler itemHandler = new ItemStackHandler(5){
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
        }
    };

    // cloak input is 0
    // cloak materials are 1-3
    // cloak output is 4

    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();

    private int progress = 0;
    private int maxProgress = 10;
    protected final ContainerData data;

    public SpectralLoomBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntityTypes.SPECTRAL_LOOM_BLOCK_ENTITY.get(), pPos, pBlockState);
        this.data = new ContainerData() {
            @Override
            public int get(int index) {
                return switch(index){
                    case 0 -> SpectralLoomBlockEntity.this.progress;
                    case 1 -> SpectralLoomBlockEntity.this.maxProgress;
                    default -> 0;
                };
            }

            @Override
            public void set(int index, int value) {
                switch(index){
                    case 0 -> SpectralLoomBlockEntity.this.progress = value;
                    case 1 -> SpectralLoomBlockEntity.this.maxProgress = value;
                };
            }

            @Override
            public int getCount() {
                return 0;
            }
        };
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable(CloakMix.MOD_ID, ModBlocks.SPECTRAL_LOOM.getId());
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
        return new SpectralLoomMenu(pContainerId, pPlayerInventory, this, this.data);
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY){
            return lazyItemHandler.cast();
        }

        return super.getCapability(cap, side);
    }

    @Override
    public void onLoad() {
        super.onLoad();
        lazyItemHandler = LazyOptional.of(() -> itemHandler);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        lazyItemHandler.invalidate();
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        pTag.put("inventory", itemHandler.serializeNBT());

        super.saveAdditional(pTag);
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        itemHandler.deserializeNBT(serializeNBT().getCompound("inventory"));
    }

    public void drops(){

        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());

        for (int i = 0; i < itemHandler.getSlots(); i++){

            inventory.setItem(i, itemHandler.getStackInSlot(i));

        }

        Containers.dropContents(this.level, this.worldPosition, inventory);

    }

    public static void tick(Level level, BlockPos blockPos, BlockState state, SpectralLoomBlockEntity entity){
        if (level.isClientSide){
            return;
        }

        if (hasRecipe(entity)){
            entity.progress++;
            setChanged(level, blockPos, state);

            if (entity.progress >= entity.maxProgress){
                craftItem(entity);
            }
        }
        else{
            entity.resetProgress();
            setChanged(level, blockPos, state);
        }

    }

    private void resetProgress() {
        this.progress = 0;
    }

    private static void craftItem(SpectralLoomBlockEntity entity) {

        if (hasRecipe(entity)){
            entity.itemHandler.extractItem(1, 1, false);
            entity.itemHandler.setStackInSlot(4, new ItemStack(Items.DIAMOND,
                    entity.itemHandler.getStackInSlot(4).getCount() + 1));
            entity.resetProgress();
        }

    }

    private static boolean hasRecipe(SpectralLoomBlockEntity entity) {
        SimpleContainer inventory = new SimpleContainer(entity.itemHandler.getSlots());

        for (int i = 0; i < entity.itemHandler.getSlots(); i++){

            inventory.setItem(i, entity.itemHandler.getStackInSlot(i));

        }

        boolean hasCloakInFirstSlot = entity.itemHandler.getStackInSlot(0).getItem() == ModItems.BASIC_CLOAK.get();

        return hasCloakInFirstSlot && canInsertAmountIntoSlot(inventory) &&
                canInsertItemIntoOutputSlot(inventory, new ItemStack(Items.DIAMOND, 1));

    }

    private static boolean canInsertItemIntoOutputSlot(SimpleContainer inventory, ItemStack itemStack) {
        return inventory.getItem(1).getItem() == itemStack.getItem() || inventory.getItem(1).isEmpty();
    }

    private static boolean canInsertAmountIntoSlot(SimpleContainer inventory) {
        return inventory.getItem(1).getMaxStackSize() > inventory.getItem(1).getCount();
    }

}
