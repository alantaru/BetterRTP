package me.SuperRonanCraft.BetterRTP;

import me.SuperRonanCraft.BetterRTP.player.PlayerInfo;
import me.SuperRonanCraft.BetterRTP.player.RTP;
import me.SuperRonanCraft.BetterRTP.player.commands.Commands;
import me.SuperRonanCraft.BetterRTP.player.events.Listener;
import me.SuperRonanCraft.BetterRTP.references.Econ;
import me.SuperRonanCraft.BetterRTP.references.Permissions;
import me.SuperRonanCraft.BetterRTP.references.Updater;
import me.SuperRonanCraft.BetterRTP.references.file.Files;
import me.SuperRonanCraft.BetterRTP.references.file.Messages;
import me.SuperRonanCraft.BetterRTP.references.invs.RTPInventories;
import me.SuperRonanCraft.BetterRTP.references.settings.Settings;
import me.SuperRonanCraft.BetterRTP.references.web.Metrics;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public class Main extends JavaPlugin {
    private Permissions perms = new Permissions();
    private Messages text = new Messages(this);
    private Econ eco = new Econ();
    private Commands cmd = new Commands(this);
    private RTP rtp = new RTP(this);
    private Listener listener = new Listener();
    private static Main instance;
    private Files files = new Files();
    private RTPInventories invs = new RTPInventories();
    private PlayerInfo pInfo = new PlayerInfo();
    private Settings settings = new Settings();

    public void onEnable() {
        instance = this;
        new Updater(this);
        new Metrics(this);
        loadAll();
        listener.registerEvents(this);
    }

    public void onDisable() {
        invs.closeAll();
    }

    public Files getFiles() {
        return files;
    }

    public static Main getInstance() {
        return instance;
    }

    @Override
    public boolean onCommand(CommandSender sendi, Command cmd, String label, String[] args) {
        try {
            this.cmd.commandExecuted(sendi, label, args);
        } catch (NullPointerException e) {
            e.printStackTrace();
            text.error(sendi);
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        return this.cmd.onTabComplete(sender, args);
    }

    public Permissions getPerms() {
        return perms;
    }

    public Messages getText() {
        return text;
    }

    public Econ getEco() {
        return eco;
    }

    public Commands getCmd() {
        return cmd;
    }

    public RTP getRTP() {
        return rtp;
    }

    public Settings getSettings() {
        return settings;
    }

    public void reload(CommandSender sendi) {
        invs.closeAll();
        loadAll();
        text.getReload(sendi);
    }

    public RTPInventories getInvs() {
        return invs;
    }

    public PlayerInfo getpInfo() {
        return pInfo;
    }

    //Load
    private void loadAll() {
        files.loadAll();
        settings.load();
        invs.load();
        rtp.load();
        cmd.load();
        listener.load();
    }
}
