package me.j360.lts.common.test;

import me.j360.lts.common.logger.Logger;
import me.j360.lts.common.logger.LoggerFactory;
import org.junit.Test;

/**
 * Created with j360-lts -> me.j360.lts.common.test.
 * User: min_xu
 * Date: 2015/11/6
 * Time: 12:42
 * 说明：
 */
public class LogTest {

    @Test
    public void logTest(){
        System.setProperty("lts.logger","jcl");
        Logger logger = LoggerFactory.getLogger(LogTest.class);

        logger.info("hello world");
    }
}
