package me.j360.lts.common.compiler.support;


import me.j360.lts.common.extension.Adaptive;
import me.j360.lts.common.extension.ExtensionLoader;

/**
 * AdaptiveCompiler. (SPI, Singleton, ThreadSafe)
 */
@Adaptive
public class AdaptiveCompiler implements me.j360.lts.common.compiler.Compiler {

    private static volatile String DEFAULT_COMPILER;

    public static void setDefaultCompiler(String compiler) {
        DEFAULT_COMPILER = compiler;
    }

    public Class<?> compile(String code, ClassLoader classLoader) {
        me.j360.lts.common.compiler.Compiler compiler;
        ExtensionLoader<me.j360.lts.common.compiler.Compiler> loader = ExtensionLoader.getExtensionLoader(me.j360.lts.common.compiler.Compiler.class);
        String name = DEFAULT_COMPILER; // copy reference
        if (name != null && name.length() > 0) {
            compiler = loader.getExtension(name);
        } else {
            compiler = loader.getDefaultExtension();
        }
        return compiler.compile(code, classLoader);
    }

}
