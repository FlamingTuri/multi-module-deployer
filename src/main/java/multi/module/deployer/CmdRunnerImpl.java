package multi.module.deployer;

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
    public void exec(String unixCmd, String windowsCmd) {
        cmdRunner.exec(unixCmd, windowsCmd);
    }

    @Override
    public void execInNewTerm(String unixCmd, String windowsCmd) {
        cmdRunner.execInNewTerm(unixCmd, windowsCmd);
    }
}
