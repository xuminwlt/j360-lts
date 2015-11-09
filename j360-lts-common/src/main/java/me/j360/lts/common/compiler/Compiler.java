package me.j360.lts.common.compiler;


import me.j360.lts.common.extension.SPI;

/**
 * Compiler. (SPI, Singleton, ThreadSafe)
 */
@SPI("javassist")
public interface Compiler {

    /**
     * Compile java source code.
     *
     * @param code        Java source code
     * @param classLoader
     * @return Compiled class
     */
    Class<?> compile(String code, ClassLoader classLoader);

}
