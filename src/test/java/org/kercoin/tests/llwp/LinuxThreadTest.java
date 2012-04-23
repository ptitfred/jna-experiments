package org.kercoin.tests.llwp;

import static org.fest.assertions.Assertions.assertThat;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LinuxThreadTest {

    private static final Logger log = LoggerFactory
            .getLogger(LinuxThreadTest.class);

    @Test
    public void simple() throws InterruptedException {
        int mainPid = LLWP.getThreadPid();
        LinuxThread lt = new LinuxThread(new LinuxThread.LinuxAwareRunnable() {

            private int runningPid = LinuxThread.UNKNOWN;

            @Override
            public void setCurrentPid(int pid) {
                this.runningPid = pid;
            }

            @Override
            public void run() {
                log.info("From thread {}", runningPid);
            }

        });
        assertThat(lt.getPid()).isEqualTo(LinuxThread.UNKNOWN);
        lt.start();
        lt.join();
        int threadPid = lt.getPid();
        assertThat(threadPid).isNotEqualTo(LinuxThread.UNKNOWN);
        assertThat(threadPid).isNotEqualTo(mainPid);
        log.info("main: {} thread: {}", mainPid, threadPid);
    }
}
