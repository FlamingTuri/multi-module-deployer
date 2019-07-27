package multi.module.deployer.cmdrunner;

public class WindowsCmdRunner extends AbstractCmdRunner {

    public WindowsCmdRunner() {
        super("bat");
    }

    @Override
    protected void postCopyOperations() {
    }

    @Override
    public void exec(String unixCmd, String windowsCmd) {
        run(wrap(windowsCmd));
    }

    @Override
    public void execInNewTerm(String unixCmd, String windowsCmd) {
        runInNewTerm(wrap(windowsCmd));
    }

    private String wrap(String string) {
        return String.format("\"%s\"", string);
    }
}
