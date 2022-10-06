package xyz.j8bit_forager.cloakmix.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ToolAction;
import org.jetbrains.annotations.Nullable;

public class FlammableRotatedPillarBlock extends RotatedPillarBlock {

    public FlammableRotatedPillarBlock(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public boolean isFlammable(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
        return true;
    }

    @Override
    public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
        return 5;
    }

    @Override
    public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
        return 5;
    }

    @Override
    public @Nullable BlockState getToolModifiedState(BlockState state, UseOnContext context, ToolAction toolAction, boolean simulate) {
        if (context.getItemInHand().getItem() instanceof AxeItem){

            if (state.is(ModBlocks.BALD_CYPRESS_LOG.get())){

                return ModBlocks.STRIPPED_BALD_CYPRESS_LOG.get().defaultBlockState().setValue(AXIS, state.getValue(AXIS));

            }
            else if (state.is(ModBlocks.BALD_CYPRESS_WOOD.get())){

                return ModBlocks.STRIPPED_BALD_CYPRESS_WOOD.get().defaultBlockState().setValue(AXIS, state.getValue(AXIS));

            }

        }
        return super.getToolModifiedState(state, context, toolAction, simulate);
    }
}
