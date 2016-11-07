
package com.rayzr522.bitzapi.commands;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 
 * Provides information for command registry, regex matching, and player help
 * 
 * <br>
 * <hr>
 * For use of the {@code baseCommand} and {@code basePerm} property of a
 * CommandHandler use
 * <p>
 * <code>@CommandInfo(...usage("/{command} some command [args]...")...)</code>
 * </p>
 * and
 * <p>
 * <code>@CommandInfo(...perm("{base}.some.perm...")...)</code>
 * </p>
 * respectively. The {@code pattern} supports regex.
 * <hr>
 * <br>
 * 
 * @author Rayzr522
 * @see BitzCommand
 *
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface CommandInfo {

    /**
     * The name of the command. Purely descriptive.
     * 
     * @return
     */
    public abstract String name();

    /**
     * The usage of this command. This is what is shown to the player.
     * 
     * @return
     */
    public abstract String usage();

    /**
     * The description of the command. Also shown to the player.
     * 
     * @return
     */
    public abstract String desc();

    /**
     * The regex pattern (or just a plain string) that is used to match this
     * command.
     * 
     * @return
     */
    public abstract String pattern();

    /**
     * What permission is required to use this.
     * 
     * @return
     */
    public abstract String perm();

}
