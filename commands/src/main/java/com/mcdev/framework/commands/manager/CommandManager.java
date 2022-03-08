package com.mcdev.framework.commands.manager;

import com.mcdev.framework.commands.Command;
import com.mcdev.framework.commands.sender.SenderFactory;
import org.jetbrains.annotations.NotNull;

public interface CommandManager<H, S extends SenderFactory<H>> {

  @NotNull S senderFactory();

  boolean registerCommand(final @NotNull Command command);

}
