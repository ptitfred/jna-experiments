package org.kercoin.tests.llwp;

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
            return getpid();
        }
        return tid;
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

    /**
     * <p>LibC <a href="http://linux.die.net/man/2/getpid">getpid</a> call.</p>
     * <p>Unfortunatly, this will give the PID of the java/javaw executable, not thread's PIDs due to the way the JVM creates threads.</p>
     * <p>{@link #getPid()} will call the GLibC which caches PIDs, but since the JVM bypasses GLibC fork/clone calls, the PIDs is stuck to the parent ID.</p>
     * @see LLWP#getThreadPid() for correct PID.
     * @return
     */
    public static native int getpid() throws LastErrorException;

    static native int syscall(long callid) throws LastErrorException;
}
