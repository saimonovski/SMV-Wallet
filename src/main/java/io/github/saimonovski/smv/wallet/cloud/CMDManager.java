package io.github.saimonovski.smv.wallet.cloud;

import io.github.saimonovski.smv.wallet.Wallet;
import io.github.saimonovski.smv.wallet.system.gems.cmd.AGemCMD;
import io.github.saimonovski.smv.wallet.system.gems.cmd.GemCMD;
import io.github.saimonovski.smv.wallet.system.wallet.cmd.AWalletCMD;
import io.github.saimonovski.smv.wallet.system.wallet.cmd.WalletCMD;
import org.bukkit.command.CommandSender;
import org.incendo.cloud.SenderMapper;
import org.incendo.cloud.annotations.AnnotationParser;
import org.incendo.cloud.execution.ExecutionCoordinator;
import org.incendo.cloud.minecraft.extras.MinecraftHelp;
import org.incendo.cloud.paper.LegacyPaperCommandManager;

public class CMDManager {
    private final Wallet plugin;

    public CMDManager(Wallet plugin) {
        this.plugin = plugin;

        final LegacyPaperCommandManager<CommandSender> manager = new LegacyPaperCommandManager<>(
                plugin,
                ExecutionCoordinator.simpleCoordinator(),
                SenderMapper.identity()
        );
        manager.captionRegistry().registerProvider(MinecraftHelp.defaultCaptionsProvider());

        CaptionRegister.registerStandard(manager);




        var annotationParser = new AnnotationParser<>(manager, CommandSender.class);

        parseCommands(annotationParser);
    }

    private  void parseCommands(AnnotationParser<CommandSender> annotationParser) {
        long now = System.currentTimeMillis();
        annotationParser.parse(
                new AWalletCMD(this.plugin),
                new WalletCMD(this.plugin.config()),
                new AGemCMD(this.plugin),
                new GemCMD(this.plugin.getGemConfig())
        );
    }
}
