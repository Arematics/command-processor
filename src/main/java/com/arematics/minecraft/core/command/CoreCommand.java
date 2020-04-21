package com.arematics.minecraft.core.command;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.reflect.MethodUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public abstract class CoreCommand implements CommandExecutor {

    private final static String NO_PERMS_KEY = "cmd_noperms";
    private static final String NOT_VALID_LENGHT = "cmd_not_valid_length";

    public abstract String[] getCommandNames();

    public abstract boolean matchAnyAccess();

    private final List<CommandAccess> accesses = new ArrayList<CommandAccess>(){{
       add(new RangAccess());
    }};

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(canAccessCommand(commandSender, command))
            return process(commandSender, command, strings);
        else
            commandSender.sendMessage("Warnung");
        return true;
    }

    private boolean process(CommandSender sender, Command command, String[] args){
        boolean isDefault = args.length == 0;
        List<String> annos = new ArrayList<>();
        try{
            for (final Method method : this.getClass().getDeclaredMethods()){
                if(isDefault) {
                    if (method.isAnnotationPresent(Default.class)) {
                        return (boolean) method.invoke(this, sender);
                    }
                }else if(method.isAnnotationPresent(Sub.class)){
                    String value = getSerializedValue(method);
                    if(annos.contains(value)){
                        sender.sendMessage("Methoden mit selben Sub Command vorhanden");
                        return true;
                    }
                    annos.add(value);
                    String[] split = value.contains(" ") ? value.split(" ") : new String[]{value};
                    if(split.length == args.length && isMatch(split, args)){
                        Object[] order;
                        try{
                            order = Parser.getInstance()
                                    .fillParameters(sender, split, method.getParameterTypes(), args);
                        }catch (ParserException e){
                            sender.sendMessage("Fehler: " + e.getMessage());
                            return true;
                        }

                        if(ArrayUtils.isEmpty(order)){
                            return (boolean) method.invoke(this, sender);
                        }
                        else return (boolean) MethodUtils.invokeMethod(this, method.getName(), order,
                                method.getParameterTypes());
                    }
                }
            }
        }catch (Exception ignore){
            sender.sendMessage("Fehler im Command, bitte dem Team melden");
        }
        return true;
    }

    private boolean isMatch(String[] annotation, String[] src){
        boolean match = true;
        for(int i = 0; i < annotation.length; i++){
            String annotationString = annotation[i];
            if(!annotationString.startsWith("{") && !annotationString.endsWith("}")){
                if(match)
                    match = annotationString.equals(src[i]);
            }
        }

        return match;
    }

    private String getSerializedValue(Method method) {
        return method.getAnnotation(Sub.class).value();
    }

    public boolean canAccessCommand(CommandSender sender, Command command){
        if(matchAnyAccess()) return getAccesses().stream().anyMatch(access -> access.hasAccess(sender, command.getName()));
        else return getAccesses().stream().allMatch(access -> access.hasAccess(sender, command.getName()));
    }

    public List<CommandAccess> getAccesses() {
        return accesses;
    }
}
