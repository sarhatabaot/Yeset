package net.sarhatabaot.yeset.config;

import org.bukkit.plugin.java.JavaPlugin;
import org.spongepowered.configurate.CommentedConfigurationNode;
import org.spongepowered.configurate.ConfigurateException;
import org.spongepowered.configurate.yaml.YamlConfigurationLoader;

import java.nio.file.Paths;

public abstract class SimpleConfigurate extends SimpleConfigFile {
    protected final YamlConfigurationLoader.Builder loaderBuilder = YamlConfigurationLoader.builder().
            path(Paths.get(folder+"/"+fileName));
    protected final YamlConfigurationLoader loader;
    protected CommentedConfigurationNode rootNode;

    public SimpleConfigurate(JavaPlugin plugin, final String resourcePath, String fileName, String folder) throws ConfigurateException {
        super(plugin, resourcePath,fileName, folder);
        preLoaderBuild();
        this.loader = loaderBuilder.build();
        this.rootNode = loader.load();

        this.saveDefaultConfig();
        initValues();
    }

    protected abstract void initValues() throws ConfigurateException;

    /**
     * Register type serializers here.
     */
    protected abstract void preLoaderBuild();

    @Override
    public void reloadConfig()  {
        try {
            this.rootNode = loader.load();
            initValues();
        } catch (ConfigurateException e) {
            plugin.getLogger().severe(e.getMessage());
        }
    }
}