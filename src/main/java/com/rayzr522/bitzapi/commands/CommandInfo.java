
package com.rayzr522.bitzapi.commands;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 
 * Provides information for command registry, regex matching, and player help
 * 
 * <br>
 * <hr>
 * For use of the {@code baseCommand} and {@code basePerm} property of a CommandHandler use
 * <p><code>@CommandInfo(...usage("/{command} some command [args]...")...)</code></p>
 * and
 * <p><code>@CommandInfo(...perm("{base}.some.perm...")...)</code></p>
 * respectively.
 * <hr>
 * <br>
 * 
 * @author PeterBlood
 * @see BitzCommand
 *
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface CommandInfo {

	public abstract String name();

	public abstract String usage();

	public abstract String desc();

	public abstract String pattern();

	public abstract String perm();

}
