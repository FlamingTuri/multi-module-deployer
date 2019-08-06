package multi.module.deployer.cmdrunner;

import org.apache.commons.lang3.SystemUtils;

/**
 * Static class to retrieve the command runner for the current OS environment
 */
public class CmdRunnerRetriever {

    private static CmdRunner cmdRunner;

    private CmdRunnerRetriever() {
    }

    /**
     * Inits the correct command runner based on the current OS environment
     */
    private static void initCmdRunnerForCurrentOs() {
        if (SystemUtils.IS_OS_WINDOWS) {
            cmdRunner = new WindowsCmdRunner();
        } else {
            cmdRunner = new UnixCmdRunner();
        }
    }

    /**
     * Gets the correct command runner instance for the current OS environment
     *
     * @return the command runner instance
     */
    public static CmdRunner get() {
        if (cmdRunner == null) {
            initCmdRunnerForCurrentOs();
        }
        return cmdRunner;
    }
}
