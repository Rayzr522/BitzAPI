
package com.rayzr522.bitzapi.message;

public enum BitzMessages {

	// Command Handler only messages
	CMD_DERP(new Message("Are you sure you know what you're doing?", Messenger.NOTE, false)),
	COMMAND_NOT_ALLOWED(new Message("The command '%' is not allowed!", Messenger.WARNING, false)),
	NO_PERMISSION(new Message("You don't have the permission '%'.\nPlease contact an admin for assistance.", Messenger.ERROR, false)),
	MULTIPLE_MATCHES(new Message("There are multiple matches for your command.", Messenger.WARNING, false)),
	NO_MATCHES(new Message("There were no matches found for your command.", Messenger.WARNING, false)),

	// Command only messages
	// These return codes are used for specifying whether or not to display
	// usage
	NO_ARG(new Message("The argument '%' was not provided.", Messenger.ERROR, false)),
	ONLY_PLAYERS(new Message("Only players can use this command!", Messenger.INFO, false)),
	NO_SUCH_PLAYER(new Message("The player '%' doesn't exist!", Messenger.ERROR, false)),
	NO_SUCH_MATERIAL(new Message("The material '%' doesn't exist!", Messenger.ERROR, false)),

	NOT_HOLDING_ITEM(new Message("You need to be holding an item!", Messenger.ERROR, true)),

	NO_REGION_SEL(new Message("You need to make a region selection!", Messenger.ERROR, true)),
	NO_LOC_SEL(new Message("You need to make a location selection!", Messenger.ERROR, true)),
	NO_LOCS_SEL(new Message("You need to list at least one location in the location list!", Messenger.ERROR, true));

	public final Message msg;

	BitzMessages(Message msg) {
		this.msg = msg;
	}

}
