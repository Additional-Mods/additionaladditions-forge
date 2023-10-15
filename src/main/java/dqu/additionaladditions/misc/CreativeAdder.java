package dqu.additionaladditions.misc;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.common.util.MutableHashedLinkedMap;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public enum CreativeAdder {

    TOOLS_AND_UTILITIES(),
    INGREDIENTS(),
    BUILDING_BLOCKS(),
    REDSTONE_BLOCKS(),
    FOOD_AND_DRINKS(),
    FUNCTIONAL_BLOCKS(),
    COMBAT();

    private final List<CreativeEntry> entries = new ArrayList<>();

    CreativeAdder() {
    }

    private static void _addAfter(MutableHashedLinkedMap<ItemStack, CreativeModeTab.TabVisibility> tabEntries, ItemLike after, ItemLike... item) {
        for (ItemLike i : item) {
            tabEntries.putAfter(new ItemStack(after), new ItemStack(i), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
        }
    }

    private static void _addBefore(MutableHashedLinkedMap<ItemStack, CreativeModeTab.TabVisibility> tabEntries, ItemLike before, ItemLike... item) {
        for (ItemLike i : item) {
            tabEntries.putBefore(new ItemStack(before), new ItemStack(i), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
        }
    }

    public void add(Supplier<Boolean> condition, ItemLike after, ItemLike... items) {
        CreativeEntry entry = new CreativeEntry(condition, false, after, items);
        this.entries.add(entry);
    }

    public void addBefore(Supplier<Boolean> condition, ItemLike after, ItemLike... items) {
        CreativeEntry entry = new CreativeEntry(condition, true, after, items);
        this.entries.add(entry);
    }

    public void pushAllTo(MutableHashedLinkedMap<ItemStack, CreativeModeTab.TabVisibility> content) {
        for (CreativeEntry entry : entries) {
            if (entry.condition.get()) {
                if (entry.before) {
                    _addBefore(content, entry.anchor, entry.items);
                } else {
                    _addAfter(content, entry.anchor, entry.items);
                }
            }
        }
    }

    private record CreativeEntry(Supplier<Boolean> condition, boolean before, ItemLike anchor, ItemLike... items) { }
}
