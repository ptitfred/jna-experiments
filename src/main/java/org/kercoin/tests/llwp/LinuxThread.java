package org.kercoin.tests.llwp;

public class LinuxThread extends Thread {

    public static final int UNKNOWN = -1;

    private int pid = UNKNOWN;

    interface LinuxAwareRunnable extends Runnable {
        void setCurrentPid(int pid);
    }

    public LinuxThread(Runnable target, String name) {
        super(target, name);
        if (target instanceof LinuxAwareRunnable) {
            this.target = (LinuxAwareRunnable) target;
        }
    }

    public LinuxThread(Runnable target) {
        super(target);
        if (target instanceof LinuxAwareRunnable) {
            this.target = (LinuxAwareRunnable) target;
        }
    }

    public LinuxThread(ThreadGroup group, Runnable target, String name,
            long stackSize) {
        super(group, target, name, stackSize);
        if (target instanceof LinuxAwareRunnable) {
            this.target = (LinuxAwareRunnable) target;
        }
    }

    public LinuxThread(ThreadGroup group, Runnable target, String name) {
        super(group, target, name);
        if (target instanceof LinuxAwareRunnable) {
            this.target = (LinuxAwareRunnable) target;
        }
    }

    public LinuxThread(ThreadGroup group, Runnable target) {
        super(group, target);
        if (target instanceof LinuxAwareRunnable) {
            this.target = (LinuxAwareRunnable) target;
        }
    }

    LinuxAwareRunnable target = null;

    @Override
    public void run() {
        this.pid = LLWP.getThreadPid();
        if (target != null) {
            target.setCurrentPid(pid);
        }
        super.run();
    }

    public int getPid() {
        return pid;
    }

}
