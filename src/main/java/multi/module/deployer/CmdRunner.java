package multi.module.deployer;

public interface CmdRunner {

    /**
     * Sets the directory where the commands will be executed
     *
     * @param workingDirPath the absolute path of the directory to set
     */
    void setWorkingDir(String workingDirPath);

    /**
     * Executes commands in a new detached terminal instance
     *
     * @param unixCmd commands that will be executed in Unix-like environments
     * @param windowsCmd commands that will be executed in Windows environments
     */
    void exec(String unixCmd, String windowsCmd);
}
