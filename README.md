# Multi Module Deployer

Multi Module Deployer it's a library built to speed up the deployment of microservice based applications.

In particular it allows to:
- build and run each application module
- configure deployment dependencies between modules

by just creating and running a simple application.

## Installation

Add to your `build.gradle` the following function:

```gradle
def downloadLibFromUrl(String libSaveDir, String libName, String libUrl) {
    def folder = new File(libSaveDir)
    if (!folder.exists()) {
        folder.mkdirs()
    }
    def file = new File(libSaveDir + '/' + libName)
    if (!file.exists()) {
        new URL(libUrl).withInputStream { i -> file.withOutputStream { it << i } }
    }
    getDependencies().add('compile', fileTree(dir: libSaveDir, include: libName))
}
```

and then add the following code to your dependencies declaration:

```gradle
dependencies {
    /* ... */
    def libSaveDir = 'build/libs'
    def libName = 'multi-module-deployer-1.0.0.jar'
    def url = 'https://github.com/FlamingTuri/multi-module-deployer/releases/download/1.0.0/multi-module-deployer-1.0.0.jar'
    downloadLibFromUrl(libSaveDir, libName, url)
}
```

## Usage example

```java
import multi.module.deployer.MultiModuleDeployer;
import multi.module.deployer.moduleconfig.ModuleConfig;
import multi.module.deployer.moduleconfig.ModuleConfigFactory;

public class App {

    public static void main(String[] args) {
        String commonCmd;
        String linuxCmd;
        String windowsCmd;
        MultiModuleDeployer multiModuleDeployer = new MultiModuleDeployer();

        // commands to run the first module
        linuxCmd = "linux commands to deploy first module";
        windowsCmd = "windows commands to deploy first module";
        ModuleConfig firstModuleConfig = ModuleConfigFactory.httpModuleConfig(linuxCmd, windowsCmd, 8080, "localhost", "/api/...");
        // adds the first configuration to the deployment list
        multiModuleDeployer.add(firstModuleConfig);

        // commands to run the second module
        linuxCmd = "linux commands to deploy second module";
        windowsCmd = "windows commands to deploy second module";
        ModuleConfig secondModuleConfig = ModuleConfigFactory.httpModuleConfig(linuxCmd, windowsCmd, 3000, "localhost", "/api/...");

        // adds the second configuration to the deployment list
        // it will be started only after the first one is "ended"
        multiModuleDeployer.add(secondModuleConfig);

        // deploys the modules
        multiModuleDeployer.deploy();
    }
}
```

For a running example look at the [java example](https://github.com/FlamingTuri/module-deployer-examples/tree/master/module-deployer-java-example) project
 in the module-deployer-examples [repo](https://github.com/FlamingTuri/module-deployer-examples).

## Built With

* [Vert.x](https://vertx.io/) - tool-kit for building reactive applications on the JVM
* [Gradle](https://gradle.org/) - dependency management tool

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details
