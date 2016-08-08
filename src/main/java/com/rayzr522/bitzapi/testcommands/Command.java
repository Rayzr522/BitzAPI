package com.rayzr522.bitzapi.testcommands;

import com.rayzr522.bitzapi.utils.data.ArrayUtils;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Map;

/**
 * Created by Ethan on 8/6/16.
 */
public class Command implements CommandExecutor {
    private String name;
    private Map<String, String> msgs;
    private Map<String, Command> subs;
    private PluginCommand command;
    private CommandExecutor commandExecutor;

    /**
     * @param name
     * @param cme
     * @param plugin
     */
    public Command(String name, CommandExecutor cme, JavaPlugin plugin) {
        this.commandExecutor = cme;
        command = plugin.getCommand(name);
        if (command != null) {
            command.setExecutor(this);
        }
        this.name = name.toLowerCase();
    }

    public boolean onCommand(CommandSender sender, org.bukkit.command.Command cmd, String label, String[] args) { 
        if (args.length > 1) {
            Command subcommand;
            if ((subcommand = (Command) subs.get(args[0].toLowerCase())) != null) {
                subcommand.onCommand(sender, cmd, args[0].toLowerCase(), ArrayUtils.removeFirst(args));
            }
        }
        return true;
    }

    public void addSubcommand(Command cmd) {
        subs.put(cmd.getName(), cmd);
    }

    public void putMessage(String key, String msg) {
        msgs.put(key, msg);
    }

    public Map<String, String> getMessageMap() {
        return msgs;
    }

    public void setMessages(Map<String, String> msgs) {
        this.msgs = msgs;
    }

    public String getMsg(String key) {
        return msgs.get(key);
    }

    public org.bukkit.command.Command getBukkitCmd() {
        return command;
    }

    public String getName() {
        return name;
    }
}
