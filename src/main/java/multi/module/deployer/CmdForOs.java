package multi.module.deployer;

public interface CmdForOs {

    void setWorkingDir(String workingDirPath);

    void exec(String unixCmd, String windowsCmd);
}
