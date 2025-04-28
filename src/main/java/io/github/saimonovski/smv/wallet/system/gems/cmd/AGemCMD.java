package io.github.saimonovski.smv.wallet.system.gems.cmd;


import io.github.saimonovski.smv.wallet.api.object.GemWallet;
import static io.github.saimonovski.smv.wallet.messages.replacers.Replacer.replacePlayer;

import io.github.saimonovski.smv.wallet.messages.ChatUtil;
import io.github.saimonovski.smv.wallet.messages.Message;

import io.github.saimonovski.smv.wallet.system.wallet.cmd.AWalletCMD;
import me.clip.placeholderapi.PlaceholderAPI;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.incendo.cloud.annotations.Argument;
import org.incendo.cloud.annotations.Command;
import org.incendo.cloud.annotations.Permission;
import org.incendo.cloud.annotations.suggestion.Suggestions;
import org.incendo.cloud.context.CommandContext;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

@Command("agemy")
@Permission(value = {"svm.portfel.admin"})
@SuppressWarnings("unused")
public class AGemCMD {
    private final GemWallet wallet;
    private final List<String> amountList = new ArrayList<>();

    public AGemCMD(GemWallet wallet) {
        this.wallet = wallet;
        for(double x = 0.0; x < 100; x = x+1){
            amountList.add(String.valueOf(x));
        }
    }
@SuppressWarnings("unused")
    public enum ActionType{
        ADD(
                player -> wallet -> num -> wallet.getGemProvider().addBalance(player.getUniqueId(),num)
        ),
    SET(
                player -> wallet -> num -> wallet.getGemProvider().setBalance(player.getUniqueId(),num)
        ),
    REMOVE(
                player -> wallet -> num -> wallet.getGemProvider().removeBalance(player.getUniqueId(),num)
        ),
        RELOAD(
                player -> wallet -> num ->  {
                    try {
                        wallet.getGemConfig().configFile().reload();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
        );
        private final Function<OfflinePlayer,Function<GemWallet, Consumer<Double>>> fun;
        ActionType(Function<OfflinePlayer,Function<GemWallet, Consumer<Double>>>fun) {
            this.fun = fun;
        }
        public void action(OfflinePlayer player, GemWallet wallet,double num){
            this.fun.apply(player).apply(wallet).accept(num);
        }
    }
    @SuppressWarnings("unused")

    //commands



    //By name*/
    @Command("stan <who>")
    public void checkOffline(Player player ,@Argument(value = "who", suggestions = "players") String who){
        Message mes =  this.wallet.getGemConfig().getMessage("admin-balance-check");
        String text = MiniMessage.miniMessage().serialize(mes.getText());
        mes.setText(ChatUtil.fix(PlaceholderAPI.setPlaceholders(Bukkit.getOfflinePlayer(who),text)));
        mes.send(player,replacePlayer(who));
    }

    @Command("dodaj <who> <amount>")
    public void addOffline( @Argument(value = "who", suggestions = "players") String who,
                     @Argument(value = "amount", suggestions = "amount")  double amount){
        ActionType.ADD.action(Bukkit.getOfflinePlayer(who),this.wallet,amount);
    }
    @SuppressWarnings("unused")

    @Command("dodaj all <amount>")
    public void addAllOffline(
            @Argument(value = "amount", suggestions = "amount")  double amount){
        for (OfflinePlayer who : Bukkit.getOfflinePlayers()) {
            ActionType.ADD.action(who,this.wallet,amount);
        }
    }
    @SuppressWarnings("unused")

    @Command("usun <who> <amount>")
    public void removeOffline(@Argument(value = "who", suggestions = "players") String who,
                       @Argument(value = "amount", suggestions = "amount") double amount){
        ActionType.REMOVE.action(Bukkit.getOfflinePlayer(who),this.wallet,amount);
    }
    @Command("usun all <amount>")  @SuppressWarnings("unused")

    public void removeAllOffline(
            @Argument(value = "amount", suggestions = "amount")  double amount){
        for (OfflinePlayer who : Bukkit.getOfflinePlayers()) {
            ActionType.REMOVE.action(who,this.wallet,amount);
        }
    }
   

    //suggestions
    @Suggestions("players") @SuppressWarnings("unused")
    public List<String> suggestPlayers(CommandContext<CommandSender> context, String input) {
        return Bukkit.getOnlinePlayers().stream()
                .map(Player::getName)
                .filter(name -> name.toLowerCase().startsWith(input))
                .collect(Collectors.toList());
    }
    @Suggestions("amount") @SuppressWarnings("unused")
    public List<String> suggestAmount(CommandContext<CommandSender> context, String input){
      return  amountList.stream().filter(str -> str.startsWith(input)).collect(Collectors.toList());
    }



}
