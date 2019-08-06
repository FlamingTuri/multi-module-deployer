package multi.module.deployer.moduleconfig.configs;

import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import multi.module.deployer.cmdrunner.CmdRunner;
import multi.module.deployer.moduleconfig.AbstractModuleConfig;
import multi.module.deployer.moduleconfig.info.ServiceInfo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.function.Consumer;

/**
 * Class that waits for a module termination
 */
public class SetupModuleConfig extends AbstractModuleConfig<Void, ServiceInfo> {

    private Process process;

    /**
     * @param unixCmd    the commands to run on Unix-like environments
     * @param windowsCmd the commands to run on Windows environments
     */
    public SetupModuleConfig(String unixCmd, String windowsCmd) {
        super(unixCmd, windowsCmd, null);
    }

    @Override
    public void deploy(CmdRunner cmdRunner) {
        process = cmdRunner.exec(unixCmd, windowsCmd);
    }

    @Override
    public Future<Void> waitDeployment(Vertx vertx) {
        setVertxInstance(vertx);
        return waitProcessTermination(process);
    }

    /**
     * Waits a process termination
     *
     * @param process the process to wait for
     * @return a future that will be completed after the process terminates
     */
    private Future<Void> waitProcessTermination(Process process) {
        System.out.println("Waiting process termination");
        Promise<Void> promise = Promise.promise();
        vertx.executeBlocking(blockingCodePromise -> {
            try {
                readOutput(process);
                process.waitFor();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            blockingCodePromise.complete();
        }, resultHandler -> promise.complete());
        return promise.future();
    }

    /**
     * Gets process input and error stream
     *
     * @param process the process
     */
    private void readOutput(Process process) {
        readStream(process.getInputStream(), System.out::println);
        readStream(process.getErrorStream(), System.err::println);
    }

    private void readStream(InputStream in, Consumer<String> lineConsumer) {
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        String line;
        try {
            while ((line = br.readLine()) != null) {
                lineConsumer.accept(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
