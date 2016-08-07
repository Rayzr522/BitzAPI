package com.rayzr522.bitzapi.testcommands;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Map;

/**
 * Created by Ethan on 8/6/16.
 */
public class Command {
    private Map<String, String> msgs;
    private Map<String, Command> subs;
    private PluginCommand command;

    public Command(String name, CommandExecutor cme, JavaPlugin plugin) {
        command = plugin.getCommand(name);
    }

    public void addSubcommand(Command cmd) {

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

}
