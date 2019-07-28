package multi.module.deployer.cmdrunner;

import org.apache.commons.lang3.SystemUtils;

import java.io.File;

/**
 * Class that initializes the correct command runner for the current OS environment
 */
public class CmdRunnerImpl implements CmdRunner {

    private final CmdRunner cmdRunner;

    public CmdRunnerImpl() {
        if (SystemUtils.IS_OS_WINDOWS) {
            cmdRunner = new WindowsCmdRunner();
        } else {
            cmdRunner = new UnixCmdRunner();
        }
    }

    @Override
    public void setWorkingDir(String workingDirPath) {
        cmdRunner.setWorkingDir(workingDirPath);
    }

    @Override
    public void setWorkingDir(File workingDir) {
        cmdRunner.setWorkingDir(workingDir);
    }

    @Override
    public Process exec(String unixCmd, String windowsCmd) {
        return cmdRunner.exec(unixCmd, windowsCmd);
    }

    @Override
    public Process execInNewTerm(String unixCmd, String windowsCmd) {
        return cmdRunner.execInNewTerm(unixCmd, windowsCmd);
    }
}
