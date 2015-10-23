package com.rayzr522.bitzapi;

import com.rayzr522.bitzapi.commands.bitz.BitzCommandABMsg;
import com.rayzr522.bitzapi.commands.bitz.BitzCommandClearSel;
import com.rayzr522.bitzapi.commands.bitz.BitzCommandCreateInv;
import com.rayzr522.bitzapi.commands.bitz.BitzCommandRename;
import com.rayzr522.bitzapi.commands.bitz.BitzCommandSet;
import com.rayzr522.bitzapi.commands.bitz.BitzCommandShow;
import com.rayzr522.bitzapi.commands.bitz.BitzCommandTools;
import com.rayzr522.bitzapi.commands.bitz.BitzCommandVersion;

public class BitzAPI extends BitzPlugin {

	public static BitzAPI instance;

	public void onEnable() {

		super.onEnable();

		instance = this;

		registerEventHandler(new BitzHandler(this));

		commandHandler.autoSetup();

		registerCommands();

	}

	public void onDisable() {

		super.onDisable();

	}

	private void registerCommands() {

		commandHandler.registerCommand(BitzCommandVersion.class);
		commandHandler.registerCommand(BitzCommandTools.class);
		commandHandler.registerCommand(BitzCommandClearSel.class);
		commandHandler.registerCommand(BitzCommandShow.class);
		commandHandler.registerCommand(BitzCommandSet.class);
		commandHandler.registerCommand(BitzCommandCreateInv.class);
		commandHandler.registerCommand(BitzCommandRename.class);
		commandHandler.registerCommand(BitzCommandABMsg.class);

	}

	@Override
	public String getBitzVersion() {
		return this.getDescription().getVersion();
	}

}
