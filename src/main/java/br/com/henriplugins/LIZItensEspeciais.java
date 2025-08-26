package br.com.henriplugins;

import br.com.henriplugins.armors.*;
import br.com.henriplugins.commands.GiveItemCommand;
import br.com.henriplugins.listeners.*;
import br.com.henriplugins.managers.Config;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public class LIZItensEspeciais extends JavaPlugin {
    private Economy economy;
    private Config configEconomia;

    @Override
    public void onEnable() {
        configEconomia = new Config(this);
        configEconomia.monitorarAlteracoes();
        if (!setupEconomy()) {
            getLogger().severe("Vault não encontrado!");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        getServer().getPluginManager().registerEvents(new VillagerTrapListener(), this);
        getServer().getPluginManager().registerEvents(new FornalhaVelozListener(), this);
        getServer().getPluginManager().registerEvents(new VipPardoListener(), this);
        getServer().getPluginManager().registerEvents(new VipPolarListener(), this);
        getServer().getPluginManager().registerEvents(new TagListener(), this);
        getServer().getPluginManager().registerEvents(new PolarArmorListener(), this);
        getServer().getPluginManager().registerEvents(new PardoArmorListener(),this);
        getServer().getPluginManager().registerEvents(new PandaArmorListener(),this);
        getServer().getPluginManager().registerEvents(new DragonZombieArmorListener(),this);
        getServer().getPluginManager().registerEvents(new ObsidianArmorListener(), this);
        getServer().getPluginManager().registerEvents(new EscamasListener(),this);
        this.getServer().getPluginManager().registerEvents(new TarantulaSwordListener(), this);
        getServer().getPluginManager().registerEvents(new DragonGeladoArmorListener(),this);
        getServer().getPluginManager().registerEvents(new TrapListener(this),this);
        getServer().getPluginManager().registerEvents(new MontariaListener(),this);
        getServer().getPluginManager().registerEvents(new FerramentasListener(this),this);
        getServer().getPluginManager().registerEvents(new ArcoGuiaListener(),this);
        getServer().getPluginManager().registerEvents(new EspadaDeusListener(),this);
        getCommand("lizitem").setExecutor(new GiveItemCommand());
        getLogger().info("LIZItensEspeciais carregado com sucesso!");
    }
    private boolean setupEconomy() {
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) return false;
        economy = rsp.getProvider();
        return economy != null;
    }
    @Override
    public void onDisable() {
        getLogger().info("LIZItensEspeciais desativado!");
    }
    public Config getConfigEconomia() {
        return configEconomia;
    }
    public Economy getEconomy() {
        return economy;
    }
}

