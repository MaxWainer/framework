package dev.framework.stack.wrapper.nbt;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class NBTList implements NBTBase, Iterable<NBTBase> {

    private final List<NBTBase> members;

    private NBTList(final @NotNull List<NBTBase> members) {
        this.members = new ArrayList<>(members);
    }

    public NBTList() {
        this.members = new ArrayList<>();
    }

    @Override
    public @NotNull String asString() {
        return members.toString();
    }

    @Override
    public boolean empty() {
        return members.isEmpty();
    }

    @Override
    public @NotNull NBTBase copy() {
        return new NBTList(this.members);
    }

    @Override
    public boolean isList() {
        return true;
    }

    @Override
    public NBTList asNBTList() {
        return this;
    }

    public void add(final @NotNull NBTBase base) {
        this.members.add(base);
    }

    public void remove(final @NotNull NBTBase base) {
        this.members.remove(base);
    }

    public boolean containsAll(@NotNull final Collection<? extends NBTBase> collection) {
        return this.members.containsAll(collection);
    }

    public void addAll(@NotNull final Collection<? extends NBTBase> collection) {
        this.members.addAll(collection);
    }

    public void addAll(final int index, @NotNull final Collection<? extends NBTBase> collection) {
        this.members.addAll(index, collection);
    }

    @Nullable
    public NBTBase get(final int index) {
        return this.members.get(index);
    }

    public void set(final int index, final @NotNull NBTBase element) {
        this.members.set(index, element);
    }

    public void add(final int index, final @NotNull NBTBase element) {
        this.members.add(index, element);
    }

    public void remove(final int index) {
        this.members.remove(index);
    }

    @NotNull
    public List<NBTBase> subList(final int fromIndex, final int toIndex) {
        return this.members.subList(fromIndex, toIndex);
    }

    public boolean contains(final @NotNull NBTBase base) {
        return this.members.contains(base);
    }

    @NotNull
    public Iterator<NBTBase> iterator() {
        return this.members.iterator();
    }
}
