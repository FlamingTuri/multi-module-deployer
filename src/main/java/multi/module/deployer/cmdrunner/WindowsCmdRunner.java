package multi.module.deployer.cmdrunner;

/**
 * Class implementing a command runner for Windows environments
 */
public class WindowsCmdRunner extends AbstractCmdRunner {

    public WindowsCmdRunner() {
        this(true);
    }

    public WindowsCmdRunner(boolean replaceScript) {
        super("bat", "cmd.exe", "/c", replaceScript);
    }

    @Override
    protected void postCopyOperations() {
    }

    @Override
    public Process exec(String unixCmd, String windowsCmd) {
        return run(wrap(windowsCmd));
    }

    @Override
    public Process execInNewTerm(String unixCmd, String windowsCmd) {
        return runInNewTerm(wrap(windowsCmd));
    }
}
