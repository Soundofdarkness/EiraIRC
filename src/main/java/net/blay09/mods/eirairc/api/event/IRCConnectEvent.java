// Copyright (c) 2014, Christopher "blay09" Baker
// All rights reserved.

package net.blay09.mods.eirairc.api.event;

import net.blay09.mods.eirairc.api.IIRCChannel;
import net.blay09.mods.eirairc.api.IIRCConnection;

public class IRCConnectEvent extends IRCEvent {

	public IRCConnectEvent(IIRCConnection connection) {
		super(connection);
	}

}
