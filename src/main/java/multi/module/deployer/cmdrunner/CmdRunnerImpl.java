package multi.module.deployer.cmdrunner;

import org.apache.commons.lang3.SystemUtils;

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
    public Process exec(String unixCmd, String windowsCmd) {
        return cmdRunner.exec(unixCmd, windowsCmd);
    }

    @Override
    public Process execInNewTerm(String unixCmd, String windowsCmd) {
        return cmdRunner.execInNewTerm(unixCmd, windowsCmd);
    }
}
