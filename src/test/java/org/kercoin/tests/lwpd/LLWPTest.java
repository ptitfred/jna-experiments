package org.kercoin.tests.lwpd;


import org.fest.assertions.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LLWPTest {

    private static final Logger log = LoggerFactory.getLogger(LLWPTest.class);

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void testPid() {
        // when
        final int pid = LLWP.getThreadPid();

        // then
        log.info("{}", pid);
        Assertions.assertThat(pid).isGreaterThan(0);
    }
}
