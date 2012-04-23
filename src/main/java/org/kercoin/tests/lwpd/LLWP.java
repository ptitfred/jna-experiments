package org.kercoin.tests.lwpd;

import com.sun.jna.LastErrorException;
import com.sun.jna.Native;
import com.sun.jna.Platform;

public class LLWP {

    /**
     * <p>Inspired from Hotspot Linux source code.
     * See C code below:</p>
     * <pre>
     *    // pid_t gettid()
     *   //
     *   // Returns the kernel thread id of the currently running thread. Kernel
     *   // thread id is used to access /proc.
     *   //
     *   // (Note that getpid() on LinuxThreads returns kernel thread id too; but
     *   // on NPTL, it returns the same pid for all threads, as required by POSIX.)
     *   //
     *   pid_t os::Linux::gettid() {
     *     int rslt = syscall(SYS_gettid);
     *     if (rslt == -1) {
     *        // old kernel, no NPTL support
     *        return getpid();
     *     } else {
     *        return (pid_t)rslt;
     *     }
     *   }
     * </pre>
     */
    public static int getThreadPid() {
        int tid = getTid();
        if (tid == -1) {
            return getPid();
        }
        return tid;
    }

    public static int getPid() {
        return getpid();
    }

    private static final long SYS_gettid_32b = 224;
    private static final long SYS_gettid_64b = 186;

    /**
     * <p>Not portable gettid syscall (not supported by libc and constant value for direct syscall depends on architecture).</p>
     * @return
     */
    public static int getTid() {
        return syscall(Platform.is64Bit() ? SYS_gettid_64b : SYS_gettid_32b);
    }

    static {
        Native.register("c");
    }

    @Deprecated
    long SYS_getpid = 20; // rather use libc getpid() which brings caching

    static native int getpid() throws LastErrorException;
    static native int syscall(long callid) throws LastErrorException;
}
