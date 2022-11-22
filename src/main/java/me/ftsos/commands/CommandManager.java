package me.ftsos.commands;

import dev.triumphteam.cmd.bukkit.*;
import dev.triumphteam.cmd.core.BaseCommand;
import dev.triumphteam.cmd.core.annotation.Default;
import dev.triumphteam.cmd.core.annotation.SubCommand;
import dev.triumphteam.cmd.core.execution.ExecutionProvider;
import dev.triumphteam.cmd.core.execution.SyncExecutionProvider;
import dev.triumphteam.cmd.core.processor.AbstractCommandProcessor;
import dev.triumphteam.cmd.core.registry.RegistryContainer;
import dev.triumphteam.cmd.core.requirement.RequirementKey;
import dev.triumphteam.cmd.core.sender.SenderMapper;
import dev.triumphteam.cmd.core.sender.SenderValidator;
import me.ftsos.UHC;
import me.ftsos.commands.commands.uhc.UhcCommand;
import me.ftsos.commands.commands.uhc.arguments.GameOptionsArgument;
import me.ftsos.commands.commands.uhc.requirements.HasPermissionRequirement;
import me.ftsos.commands.commands.uhc.subcommands.ForceStopSubCommand;
import me.ftsos.commands.commands.uhc.subcommands.InfoSubCommand;
import me.ftsos.commands.commands.uhc.subcommands.JoinSubCommand;
import me.ftsos.commands.commands.uhc.subcommands.StartSubCommand;
import me.ftsos.commands.utils.ISubCommand;
import me.ftsos.commands.utils.Requirement;
import me.ftsos.game.GameOptions;
import me.ftsos.game.UhcGamesManager;
import me.ftsos.game.UhcGamesManagerWrapper;
import me.ftsos.managers.Manager;
import me.ftsos.utils.config.Permissions;
import org.bukkit.command.CommandSender;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

public class CommandManager extends Manager {
    private UHC plugin;
    private BukkitCommandManager<CommandSender> manager;
    private Map<String, String> permissionsMap;

    public CommandManager(UHC plugin) {
        super(plugin);
        this.plugin = plugin;
        this.permissionsMap = new HashMap<>();
    }

    @Override
    public void enable() {
        this.manager = BukkitCommandManager.create(plugin);
        registerArguments();
        registerPermissions();
        registerCommands();
    }

    @Override
    public void disable() {

    }

    public void registerArguments(){
        manager.registerArgument(GameOptions.class, (sender, args) -> {
            GameOptionsArgument gameOptionsArgument = new GameOptionsArgument();
            return gameOptionsArgument.validate(sender, args);
        });
    }

    public void registerRequirement(Requirement requirement) {
        if(doesRequirementKeyExist(requirement.getName())) return;
        manager.registerRequirement(RequirementKey.of(requirement.getName()), sender -> requirement.validate(sender));
    }

    public void registerPermissionsRequirements() {
        for(Map.Entry<String, String> entry : this.permissionsMap.entrySet()) {
            HasPermissionRequirement requirement = new HasPermissionRequirement(entry.getKey(), entry.getValue());
            registerRequirement(requirement);
        }
    }

    public void registerPermission(String key, String permission) {
        this.permissionsMap.put(key, permission);
    }

    public void registerPermissions() {
        registerPermission("baseCommand", Permissions.UHC_BASE_COMMAND_PERMISSION);
        registerPermission("infoCommand", Permissions.UHC_INFO_SUB_COMMAND_PERMISSION);
        registerPermission("startSubCommand", Permissions.UHC_START_SUB_COMMAND_PERMISSION);
        registerPermission("joinSubCommand", Permissions.UHC_JOIN_SUB_COMMAND_PERMISSION);
        registerPermission("forceStopSubCommand", Permissions.UHC_FORCE_STOP_SUB_COMMAND_PERMISSION);
        registerPermissionsRequirements();
    }

    private boolean doesRequirementKeyExist(String key) {
        final boolean[] result = {false};
        RequirementKey.getRegisteredKeys().forEach(requirementKey -> {
            if(requirementKey.getKey() == key) result[0] = true;
        });
        return result[0];
    }

    //TODO: Abstract this method more, and if possible move to changes inside the library
    public void registerCommand(BaseCommand command, ISubCommand... ISubCommands) {
        try {
            Class<BukkitCommandManager> managerClazz = BukkitCommandManager.class;
            Class<dev.triumphteam.cmd.core.CommandManager> commandManagerAbstractClass = dev.triumphteam.cmd.core.CommandManager.class;


            //Registry Container
            Method getRegistryContainerMethod = managerClazz.getDeclaredMethod("getRegistryContainer");
            getRegistryContainerMethod.setAccessible(true);
            RegistryContainer registryContainer = (RegistryContainer) getRegistryContainerMethod.invoke(this.manager);
            //Sender Mapper
            Method getSenderMapperMethod = commandManagerAbstractClass.getDeclaredMethod("getSenderMapper");
            getSenderMapperMethod.setAccessible(true);
            SenderMapper senderMapper = (SenderMapper) getSenderMapperMethod.invoke(this.manager);
            //Sender Validator
            Field senderValidatorField = commandManagerAbstractClass.getDeclaredField("senderValidator");
            senderValidatorField.setAccessible(true);
            SenderValidator senderValidator = (SenderValidator) senderValidatorField.get(this.manager);
            //Sync Execution Provider
            Field syncExecutionProviderField = managerClazz.getDeclaredField("syncExecutionProvider");
            syncExecutionProviderField.setAccessible(true);
            ExecutionProvider syncExecutionProvider = (SyncExecutionProvider) syncExecutionProviderField.get(this.manager);
            //Async Execution Provider
            Field asyncExecutionProviderField = managerClazz.getDeclaredField("asyncExecutionProvider");
            asyncExecutionProviderField.setAccessible(true);
            BukkitAsyncExecutionProvider asyncExecutionProviderNullable = (BukkitAsyncExecutionProvider) asyncExecutionProviderField.get(this.manager);
            ExecutionProvider asyncExecutionProvider = asyncExecutionProviderNullable == null ? new BukkitAsyncExecutionProvider(this.plugin) : asyncExecutionProviderNullable;
            //Base Permission
            Field basePermissionField = managerClazz.getDeclaredField("basePermission");
            basePermissionField.setAccessible(true);
            CommandPermission basePermission = (CommandPermission) basePermissionField.get(this.manager);
            //BukkitCommandProcessor Construction
            Class bukkitCommandProcessorClazz = Class.forName("dev.triumphteam.cmd.bukkit.BukkitCommandProcessor");
            Constructor constructor = bukkitCommandProcessorClazz.getDeclaredConstructor(BaseCommand.class, RegistryContainer.class, SenderMapper.class, SenderValidator.class, ExecutionProvider.class, ExecutionProvider.class, CommandPermission.class);
            constructor.setAccessible(true);
            Object processor = constructor.newInstance(command,
                    registryContainer,
                    senderMapper,
                    senderValidator,
                    syncExecutionProvider,
                    asyncExecutionProvider,
                    basePermission);
            //Processor Name
            Class<AbstractCommandProcessor> abstractCommandProcessorClass = AbstractCommandProcessor.class;
            Method getProcessorName = abstractCommandProcessorClass.getMethod("getName");
            String processorName = (String) getProcessorName.invoke(processor);

            //Commands Field
            Field commandsField = managerClazz.getDeclaredField("commands");
            commandsField.setAccessible(true);
            Map<String, BukkitCommand<CommandSender>> commands = (Map<String, BukkitCommand<CommandSender>>) commandsField.get(this.manager);

            //Create And Register Command Method
            Method createAndRegisterCommandMethod = managerClazz.getDeclaredMethod("createAndRegisterCommand", String.class, bukkitCommandProcessorClazz);
            createAndRegisterCommandMethod.setAccessible(true);
            //Get a New Bukkit Command instance
            BukkitCommand<CommandSender> bukkitCommand = commands.computeIfAbsent(processorName, ignored -> {
                try {
                    return (BukkitCommand<CommandSender>) createAndRegisterCommandMethod.invoke(this.manager, processorName, processor);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
                return null;
            });

            //Sub Command Processor
            Method getSubCommandProcessorMethod = bukkitCommandProcessorClazz.getDeclaredMethod("createProcessor", Method.class);
            getSubCommandProcessorMethod.setAccessible(true);
            for (ISubCommand ISubCommand : ISubCommands) {
                for(Method method : ISubCommand.getClass().getDeclaredMethods()){
                    if(!method.isAnnotationPresent(SubCommand.class)) continue;
                    //Get Sub Command Processor
                    Object subCommandProcessor = getSubCommandProcessorMethod.invoke(processor, method);
                    //Get Sub Sub Command Name
                    Method getSubCommandNameMethod = subCommandProcessor.getClass().getSuperclass().getDeclaredMethod("getName");
                    getSubCommandNameMethod.setAccessible(true);
                    String subCommandName = (String) getSubCommandNameMethod.invoke(subCommandProcessor);

                    //Is Processor Async Method
                    Method isProcessorAsyncMethod = subCommandProcessor.getClass().getSuperclass().getDeclaredMethod("isAsync");
                    isProcessorAsyncMethod.setAccessible(true);
                    boolean isProcessorAsync = (boolean) isProcessorAsyncMethod.invoke(subCommandProcessor);

                    ExecutionProvider executionProvider = isProcessorAsync ? asyncExecutionProvider : syncExecutionProvider;
                    //createSub Command Method and add it
                    Method createSubCommandMethod = bukkitCommandProcessorClazz.getDeclaredMethod("createSubCommand", subCommandProcessor.getClass(), ExecutionProvider.class, Object.class);
                    createSubCommandMethod.setAccessible(true);
                    BukkitSubCommand subCommandLibObj = (BukkitSubCommand) createSubCommandMethod.invoke(processor, subCommandProcessor, executionProvider, ISubCommand);
                    bukkitCommand.addSubCommand(subCommandName, subCommandLibObj);

                    //Aliases Managing
                    Method getAliasSubCommandMethod = subCommandProcessor.getClass().getSuperclass().getDeclaredMethod("getAlias");
                    getAliasSubCommandMethod.setAccessible(true);
                    List<String> aliases = (List<String>) getAliasSubCommandMethod.invoke(subCommandProcessor);
                    aliases.forEach(alias -> bukkitCommand.addSubCommandAlias(alias, subCommandLibObj));
                }
            }

            //Sub Command Adding by registering default and sub commands inside class
            for(Method method : command.getClass().getDeclaredMethods()) {
                if(!(method.isAnnotationPresent(SubCommand.class) || method.isAnnotationPresent(Default.class))) continue;
                //Get Sub Command Processor
                Object subCommandProcessor = getSubCommandProcessorMethod.invoke(processor, method);
                //Get Sub Sub Command Name
                Method getSubCommandNameMethod = subCommandProcessor.getClass().getSuperclass().getDeclaredMethod("getName");
                getSubCommandNameMethod.setAccessible(true);
                String subCommandName = (String) getSubCommandNameMethod.invoke(subCommandProcessor);

                //Is Processor Async Method
                Method isProcessorAsyncMethod = subCommandProcessor.getClass().getSuperclass().getDeclaredMethod("isAsync");
                isProcessorAsyncMethod.setAccessible(true);
                boolean isProcessorAsync = (boolean) isProcessorAsyncMethod.invoke(subCommandProcessor);

                ExecutionProvider executionProvider = isProcessorAsync ? asyncExecutionProvider : syncExecutionProvider;
                //createSub Command Method and add it
                Method createSubCommandMethod = bukkitCommandProcessorClazz.getDeclaredMethod("createSubCommand", subCommandProcessor.getClass(), ExecutionProvider.class, Object.class);
                createSubCommandMethod.setAccessible(true);
                BukkitSubCommand subCommandLibObj = (BukkitSubCommand) createSubCommandMethod.invoke(processor, subCommandProcessor, executionProvider, command);
                bukkitCommand.addSubCommand(subCommandName, subCommandLibObj);

                //Aliases Managing
                Method getAliasSubCommandMethod = subCommandProcessor.getClass().getSuperclass().getDeclaredMethod("getAlias");
                getAliasSubCommandMethod.setAccessible(true);
                List<String> aliases = (List<String>) getAliasSubCommandMethod.invoke(subCommandProcessor);
                aliases.forEach(alias -> bukkitCommand.addSubCommandAlias(alias, subCommandLibObj));
            }

            //Aliases BukkitCommand Manager Managing
            Method getAliasMethod = bukkitCommandProcessorClazz.getSuperclass().getDeclaredMethod("getAlias");
            getAliasMethod.setAccessible(true);
            List<String> aliases = (List<String>) getAliasMethod.invoke(processor);
            aliases.forEach(it -> {
                final BukkitCommand aliasCommand = commands.computeIfAbsent(it, ignored -> {
                    try {
                        return (BukkitCommand<CommandSender>) createAndRegisterCommandMethod.invoke(this.manager, it, processor);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                    return null;
                });
                try {
                    //Sub Command Processing but for alias
                    for (ISubCommand ISubCommand : ISubCommands) {
                        for(Method method : ISubCommand.getClass().getDeclaredMethods()) {
                            if (!method.isAnnotationPresent(SubCommand.class)) continue;
                            //Get Sub Command Processor
                            Object subCommandProcessor = getSubCommandProcessorMethod.invoke(processor, method);
                            //Get Sub Sub Command Name
                            Method getSubCommandNameMethod = subCommandProcessor.getClass().getSuperclass().getDeclaredMethod("getName");
                            getSubCommandNameMethod.setAccessible(true);
                            String subCommandName = (String) getSubCommandNameMethod.invoke(subCommandProcessor);

                            //Is Processor Async Method
                            Method isProcessorAsyncMethod = subCommandProcessor.getClass().getSuperclass().getDeclaredMethod("isAsync");
                            isProcessorAsyncMethod.setAccessible(true);
                            boolean isProcessorAsync = (boolean) isProcessorAsyncMethod.invoke(subCommandProcessor);

                            ExecutionProvider executionProvider = isProcessorAsync ? asyncExecutionProvider : syncExecutionProvider;

                            //createSub Command Method and add it
                            Method createSubCommandMethod = bukkitCommandProcessorClazz.getDeclaredMethod("createSubCommand", subCommandProcessor.getClass(), ExecutionProvider.class, Object.class);
                            createSubCommandMethod.setAccessible(true);
                            BukkitSubCommand subCommandLibObj = (BukkitSubCommand) createSubCommandMethod.invoke(processor, subCommandProcessor, executionProvider, ISubCommand);
                            aliasCommand.addSubCommand(subCommandName, subCommandLibObj);

                            //Aliases Managing
                            Method getAliasSubCommandMethod = subCommandProcessor.getClass().getSuperclass().getDeclaredMethod("getAlias");
                            getAliasSubCommandMethod.setAccessible(true);
                            List<String> aliasesAliases = (List<String>) getAliasSubCommandMethod.invoke(subCommandProcessor);
                            aliasesAliases.forEach(alias -> aliasCommand.addSubCommandAlias(alias, subCommandLibObj));
                        }
                    }

                    //Sub Command adding by registering default and sub commands inside class but for alias
                    for(Method method : command.getClass().getDeclaredMethods()) {
                        if(!(method.isAnnotationPresent(SubCommand.class) || method.isAnnotationPresent(Default.class))) continue;
                        //Get Sub Command Processor
                        Object subCommandProcessor = getSubCommandProcessorMethod.invoke(processor, method);
                        //Get Sub Sub Command Name
                        Method getSubCommandNameMethod = subCommandProcessor.getClass().getSuperclass().getDeclaredMethod("getName");
                        getSubCommandNameMethod.setAccessible(true);
                        String subCommandName = (String) getSubCommandNameMethod.invoke(subCommandProcessor);

                        //Is Processor Async Method
                        Method isProcessorAsyncMethod = subCommandProcessor.getClass().getSuperclass().getDeclaredMethod("isAsync");
                        isProcessorAsyncMethod.setAccessible(true);
                        boolean isProcessorAsync = (boolean) isProcessorAsyncMethod.invoke(subCommandProcessor);

                        ExecutionProvider executionProvider = isProcessorAsync ? asyncExecutionProvider : syncExecutionProvider;
                        //createSub Command Method and add it
                        Method createSubCommandMethod = bukkitCommandProcessorClazz.getDeclaredMethod("createSubCommand", subCommandProcessor.getClass(), ExecutionProvider.class, Object.class);
                        createSubCommandMethod.setAccessible(true);
                        BukkitSubCommand subCommandLibObj = (BukkitSubCommand) createSubCommandMethod.invoke(processor, subCommandProcessor, executionProvider, command);
                        bukkitCommand.addSubCommand(subCommandName, subCommandLibObj);

                        //Aliases Managing
                        Method getAliasSubCommandMethod = subCommandProcessor.getClass().getSuperclass().getDeclaredMethod("getAlias");
                        getAliasSubCommandMethod.setAccessible(true);
                        List<String> aliase = (List<String>) getAliasSubCommandMethod.invoke(subCommandProcessor);
                        aliase.forEach(alias -> bukkitCommand.addSubCommandAlias(alias, subCommandLibObj));
                    }
                } catch (Exception exception) {
                    exception.printStackTrace();
                }

            });
        } catch (ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    public void registerCommands() {
        UhcGamesManagerWrapper uhcGamesManagerWrapper = this.plugin.getManagerHandler().find(UhcGamesManager.class).getUhcGamesManagerWrapper();
        this.registerCommand(new UhcCommand(),
                new StartSubCommand(uhcGamesManagerWrapper),
                new InfoSubCommand(),
                new JoinSubCommand(uhcGamesManagerWrapper),
                new ForceStopSubCommand(uhcGamesManagerWrapper)
                );
    }
}
