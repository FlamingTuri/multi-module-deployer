package multi.module.deployer.cmdrunner;

public class WindowsCmdRunner extends AbstractCmdRunner {

    public WindowsCmdRunner() {
        super("bat", "cmd.exe", "/c");
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
