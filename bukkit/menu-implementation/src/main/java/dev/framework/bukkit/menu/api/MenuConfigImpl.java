package dev.framework.bukkit.menu.api;

import static java.util.Objects.requireNonNull;

import dev.framework.bukkit.menu.api.slot.Position;
import dev.framework.bukkit.menu.api.slot.Slot;
import dev.framework.commons.collection.MoreCollections;
import dev.framework.commons.collection.xy.XYCollection;
import net.kyori.adventure.text.Component;
import org.bukkit.event.inventory.InventoryType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;

final class MenuConfigImpl implements MenuConfig {

  private final Component title;
  private final InventoryType inventoryType;
  private final int rows;
  private final XYCollection<Slot> slots;

  MenuConfigImpl(
      final @NotNull Component title,
      final @NotNull InventoryType inventoryType,
      final int rows,
      final @NotNull XYCollection<Slot> slots) {
    this.title = requireNonNull(title);
    this.inventoryType = inventoryType;
    this.rows = rows;
    this.slots = slots;
  }

  @Override
  public @NotNull XYCollection<? extends Slot> slots() {
    return slots;
  }

  @Override
  public @NotNull Component title() {
    return title;
  }

  @Override
  public @NotNull InventoryType type() {
    return inventoryType;
  }

  @Override
  public int rows() {
    return rows;
  }

  static final class MenuConfigBuilderImpl implements MenuConfigBuilder {

    private Component title = Component.text("Menu title");
    private InventoryType inventoryType = InventoryType.CHEST;
    private int rows = 1;
    private final XYCollection<Slot> slots = MoreCollections.xyCollection(9, 6);

    @Override
    public MenuConfigBuilder title(@NotNull Component title) {
      this.title = title;
      return this;
    }

    @Override
    public MenuConfigBuilder type(@NotNull InventoryType inventoryType) {
      this.inventoryType = inventoryType;
      return this;
    }

    @Override
    public MenuConfigBuilder rows(@Range(from = 1, to = 6) int rows) {
      this.rows = rows;
      return this;
    }

    @Override
    public <T extends Slot> MenuConfigBuilder assertSlot(@NotNull T slot) {
      final Position position = slot.position();

      slots.insertIfAbsent(position.x(), position.y(), slot);

      return this;
    }

    @Override
    public MenuConfig build() {
      return new MenuConfigImpl(title, inventoryType, rows, slots);
    }
  }
}
