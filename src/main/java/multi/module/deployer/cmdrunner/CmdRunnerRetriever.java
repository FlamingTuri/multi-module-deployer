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
     *
     * @param replaceScript whether the local exec-in-new-terminal script should be replaced
     */
    private static void initCmdRunnerForCurrentOs(boolean replaceScript) {
        if (SystemUtils.IS_OS_WINDOWS) {
            cmdRunner = new WindowsCmdRunner(replaceScript);
        } else {
            cmdRunner = new UnixCmdRunner(replaceScript);
        }
    }

    /**
     * Gets the correct command runner instance for the current OS environment,
     * replacing the local exec-in-new-terminal script (if it exists)
     *
     * @return the command runner instance
     */
    public static CmdRunner get() {
        return get(true);
    }

    /**
     * Gets the correct command runner instance for the current OS environment
     *
     * @param replaceScript whether the local exec-in-new-terminal script should be replaced
     * @return the command runner instance
     */
    public static CmdRunner get(boolean replaceScript) {
        if (cmdRunner == null) {
            initCmdRunnerForCurrentOs(replaceScript);
        }
        return cmdRunner;
    }
}
