package br.com.henriplugins.managers;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Config {

    private final Plugin plugin;
    private File configFile;
    private YamlConfiguration config;
    private Map<String, Double> itemPrecos;
    private long lastModified;

    public Config(Plugin plugin) {
        this.plugin = plugin;
        this.itemPrecos = new HashMap<>();
        this.lastModified = 0L;
        inicializar();
    }

    private void inicializar() {
        File pastaEconomia = new File(plugin.getDataFolder(), "economia");
        if (!pastaEconomia.exists()) {
            pastaEconomia.mkdirs();
        }

        configFile = new File(pastaEconomia, "config.yml");
        if (!configFile.exists()) {
            try {
                configFile.createNewFile();
                config = YamlConfiguration.loadConfiguration(configFile);
                config.set("DIAMOND", 100.0);
                config.set("IRON_INGOT", 25.0);
                config.set("GOLD_INGOT", 50.0);
                config.save(configFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        carregarConfiguracoes();
    }

    private void carregarConfiguracoes() {
        config = YamlConfiguration.loadConfiguration(configFile);

        itemPrecos.clear();

        for (String key : config.getKeys(false)) {
            double valor = config.getDouble(key);
            itemPrecos.put(key, valor);
        }
    }

    public double getPreco(String itemId) {
        return itemPrecos.getOrDefault(itemId, 0.0);
    }

    public void salvarConfiguracao() {
        try {
            config.save(configFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void atualizarConfig() {
        carregarConfiguracoes();
    }

    public void monitorarAlteracoes() {

        plugin.getServer().getScheduler().runTaskTimer(plugin, () -> {
            long currentLastModified = configFile.lastModified();
            if (currentLastModified > lastModified) {
                lastModified = currentLastModified;
                carregarConfiguracoes();
            }
        }, 0L, 20L);
    }
}
