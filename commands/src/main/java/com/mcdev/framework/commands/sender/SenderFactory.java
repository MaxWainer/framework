package com.mcdev.framework.commands.sender;

import org.jetbrains.annotations.NotNull;

public interface SenderFactory<H> {

  @NotNull Sender wrapSender(final @NotNull H handle);

}
