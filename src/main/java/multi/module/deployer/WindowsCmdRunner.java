package multi.module.deployer;

public class WindowsCmdRunner extends AbstractCmdRunner {

    public WindowsCmdRunner() {
        super("bat");
    }

    @Override
    protected void postCopyOperations() {
    }

    @Override
    public void exec(String unixCmd, String windowsCmd) {
        run(String.format("\"%s\"", windowsCmd));
    }
}
