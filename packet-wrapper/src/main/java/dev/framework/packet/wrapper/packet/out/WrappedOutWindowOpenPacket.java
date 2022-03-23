package dev.framework.packet.wrapper.packet.out;

import dev.framework.packet.wrapper.packet.WindowPacket;
import dev.framework.packet.wrapper.packet.bound.OutPacket;
import org.jetbrains.annotations.NotNull;

public final class WrappedOutWindowOpenPacket implements OutPacket, WindowPacket {

    private final int windowId;
    private final WindowType windowType;
    private final String title;
    private final int rows;

    public WrappedOutWindowOpenPacket(
            final int windowId,
            final @NotNull WindowType windowType,
            final @NotNull String title,
            final int rows) {
        this.windowId = windowId;
        this.windowType = windowType;
        this.title = title;
        this.rows = rows;
    }

    @NotNull
    public WindowType windowType() {
        return windowType;
    }

    @NotNull
    public String title() {
        return title;
    }

    public int rows() {
        return rows;
    }

    @Override
    public int windowId() {
        return windowId;
    }

    @Override
    public String toString() {
        return "WrappedOutWindowOpenPacket{" +
                "windowId=" + windowId +
                ", windowType=" + windowType +
                ", title='" + title + '\'' +
                ", rows=" + rows +
                '}';
    }

    public enum WindowType {
        CHEST
    }
}
