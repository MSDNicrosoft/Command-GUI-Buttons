package work.msdnicrosoft.commandbuttons.compat.minecraft;

import net.minecraft.network.chat.Component;
//#if MC < 11900
//$$ import net.minecraft.network.chat.TextComponent;
//$$ import net.minecraft.network.chat.TranslatableComponent;
//#endif
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class ComponentCompatApi {
    @Contract(pure = true)
    public static @NotNull Component literal(String string) {
        //#if MC >= 11900
        return Component.literal(string);
        //#else
        //$$ return new TextComponent(string);
        //#endif
    }

    @Contract(pure = true)
    public static @NotNull Component translatable(String string) {
        //#if MC >= 11900
        return Component.translatable(string);
        //#else
        //$$ return new TranslatableComponent(string);
        //#endif
    }

    @Contract(pure = true)
    public static @NotNull Component translatable(String string, Object... args) {
        //#if MC >= 11900
        return Component.translatable(string, args);
        //#else
        //$$ return new TranslatableComponent(string, args);
        //#endif
    }
}
