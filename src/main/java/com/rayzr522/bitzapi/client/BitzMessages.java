
package com.rayzr522.bitzapi.client;

public enum BitzMessages {

	CMD_DERP(new Message("Are you sure you know what you're doing?", Messenger.NOTE)),
	COMMAND_NOT_ALLOWED(new Message("The command '%' is not allowed!", Messenger.WARNING)),
	NO_PERMISSION(new Message("You don't have the permission '%'.\nPlease contact an admin for assistance.", Messenger.ERROR)),
	MULTIPLE_MATCHES(new Message("There are multiple matches for your command.", Messenger.WARNING)),
	NO_MATCHES(new Message("There were no matches found for your command.", Messenger.WARNING)),
	NO_ARG(new Message("The argument '%' was not provided.", Messenger.ERROR)),
	ONLY_PLAYERS(new Message("Only players can use this command!", Messenger.INFO)),
	NO_SUCH_PLAYER(new Message("The player '%' doesn't exist!", Messenger.ERROR)),

	NO_REGION_SEL(new Message("You need to make a region selection!", Messenger.ERROR)),
	NO_LOC_SEL(new Message("You need to make a location selection!", Messenger.ERROR)),
	NO_LOCS_SEL(new Message("You need to list at least one location in the location list!", Messenger.ERROR));

	public final Message msg;

	BitzMessages(Message msg) {
		this.msg = msg;
	}

}
