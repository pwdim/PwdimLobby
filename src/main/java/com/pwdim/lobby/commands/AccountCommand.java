package com.pwdim.lobby.commands;

import com.pwdim.lobby.LOBBY;
import com.pwdim.lobby.utils.MyUtils;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AccountCommand implements CommandExecutor {


    private final LOBBY plugin;

    public AccountCommand(LOBBY plugin) {
        this.plugin = plugin;
    }

    public void checkUse(String[] args){
        switch (args[2]){
            case "add":
                break;
            case "remove":
                break;
            case "list":
                break;

        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player) {
            //acc nick rank/permission/medal/tag add/remove/list <nome>
            // Adicionar uma permissão pra cada Linha
            // Fazer um for com cada permissao e a medida que o jogador tenha a permissao adicionar ela na mensagem
            Player p = (Player) sender;

            String usage = MyUtils.color("&cUse: /account <nick> <tipo> <give/remove/list>");
            String accType = "";

            if (plugin.isPremiumPlayer(p)) {
                accType = "&aPremium";
            } else {
                accType = "&cPirata";
            }
            Date dateFirst = new Date(p.getFirstPlayed());
            String firstLogin = new SimpleDateFormat("dd/MM/yyyy - HH:mm:ss").format(dateFirst);
            Date dateLast = new Date(p.getLastPlayed());
            String lastLogin = new SimpleDateFormat("dd/MM/yyyy - HH:mm:ss").format(dateLast);


            String accountMSG =
                            "&eUsuário: &b" + p.getName() + "\n" +
                            "&eUUID: &b" + p.getUniqueId()  + "\n" +
                            "&eTipo: &b" + accType  + "\n" +
                            "&ePrimeiro Login: &b" + firstLogin + "\n" +
                            "&eÚltimo Login: &b" + lastLogin  + "\n" +
                            "&eVersão: &b" + plugin.getPlayerVersion(p)  + "\n" +
                            "&eIP: &b" + p.getAddress()  + "\n" +
                            "&eLocalização: &b" + p.getAddress().getHostName()  + "\n" +
                            "&eRank: &r" + plugin.getRankDisplay(p)  + "\n";

            if (args.length == 0) {
                p.sendMessage(MyUtils.color(accountMSG));
                // /account pwdim rank/permission/medal/tag add/remove/list/ <uso>

                // Usuário
                // UUID
                // Tipo
                // Primeiro Login
                // Último Login
                // Versão (Se estiver Online)
                // IP
                // Localização
                // Rank
            }
            OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);

            String accTypeTarget = "";

            if (plugin.isPremiumPlayer(target)) {
                accTypeTarget = "&aPremium";
            } else {
                accTypeTarget = "&cPirata";
            }
            Date dateFirstTarget = new Date(target.getFirstPlayed());
            String firstLoginTarget = new SimpleDateFormat("dd/MM/yyyy - HH:mm:ss").format(dateFirstTarget);
            Date dateLastTarget = new Date(target.getLastPlayed());
            String lastLoginTarget = new SimpleDateFormat("dd/MM/yyyy - HH:mm:ss").format(dateLastTarget);

            String accountMSGTarget =
                    "&eUsuário: &b" + target.getName() + "\n" +
                            "&eUUID: &b" + target.getUniqueId()  + "\n" +
                            "&eTipo: &b" + accTypeTarget + "\n" +
                            "&ePrimeiro Login: &b" + firstLoginTarget + "\n" +
                            "&eÚltimo Login: &b" + lastLoginTarget  + "\n" +
                            "&eVersão: &b" + plugin.getPlayerVersion(target)  + "\n" +
                            "&eIP: &b" + target.getPlayer().getAddress()  + "\n" +
                            "&eLocalização: &b" + target.getPlayer().getAddress().getHostName()  + "\n" +
                            "&eRank: &r" + plugin.getRankDisplay(target)  + "\n";

            if (args.length == 1){

                p.sendMessage(MyUtils.color(accountMSGTarget));
            }


        }
        return true;
    }
}
