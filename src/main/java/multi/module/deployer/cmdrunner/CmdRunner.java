package multi.module.deployer.cmdrunner;

public interface CmdRunner {

    /**
     * Sets the directory where the commands will be executed
     *
     * @param workingDirPath the absolute path of the directory to set
     */
    void setWorkingDir(String workingDirPath);

    /**
     * Executes string command in a terminal
     *
     * @param unixCmd    string command that will be executed in Unix-like environments
     * @param windowsCmd string command that will be executed in Windows environments
     */
    Process exec(String unixCmd, String windowsCmd);

    /**
     * Executes string command in a new detached terminal instance
     *
     * @param unixCmd    string commands that will be executed in Unix-like environments
     * @param windowsCmd string commands that will be executed in Windows environments
     */
    Process execInNewTerm(String unixCmd, String windowsCmd);
}
