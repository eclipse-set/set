# Build instructions

Due to the multitude of technologies used by Eclipse SET, building the entirety of the Signalling Engineering Toolbox entirely from scratch is non-trivial, and rarely required. 

As a result, this document contains two sets of instructions.
First, instructions on how to work on the main SET application relying on prebuilt components (e.g. for development) and secondly instructions on how to do a full production build locally are provided. 

## Operating Systems

Eclipse SET currently supports Windows x64 for execution and either Windows x64 or Linux x64 machine for building the code.
Other operating systems or processor architectures may work, but are generally not supported.

## Prerequisites

- A Java Development Kit 21
- node.js 20
- Maven (3.9.6) 
- Eclipse IDE (2024-06)
- [hugo](https://gohugo.io/) (v0.101.0+)

# Eclipse IDE Requirement Plugins
The requirement plugins can install through Eclipse IDE (Help -> Installed New Software) or bash file _releng/eclipse/install_eclipse_plugin.sh_ (Need set environtment variable `ECLIPSE_HOME`).

||Plugin Name| Install URL|
|------|------|------|
1|Eclipse Platform | https://download.eclipse.org/eclipse/updates/4.32
2|Eclipse RPC Target Components | https://download.eclipse.org/eclipse/updates/4.32
3|Eclipse Plugin Developments | https://download.eclipse.org/eclipse/updates/4.32
4|Equinox Target Components | https://download.eclipse.org/eclipse/updates/4.32
5|Eclipse e4 Tools Developer Resources | https://download.eclipse.org/eclipse/updates/4.32
6|Xtext Complete SDK | https://download.eclipse.org/releases/2024-06
7|EMF - Eclipse Modeling Framework SDK | https://download.eclipse.org/releases/2024-06
8|EMF Forms SDK | http://download.eclipse.org/ecp/releases/releases_127
9|Ecore Tools | https://download.eclipse.org/ecoretools/updates/releases/3.5.1/2023-06/
10|M2E PDE Integration | https://download.eclipse.org/releases/2024-06
11|Checkstyle | https://checkstyle.org/eclipse-cs-update-site
12|SWTBot for Eclipse Testing | https://download.eclipse.org/releases/2024-06

# Maven Settings
The SET project required SET-Browser (eclipse-set/browser), SET-Model (eclipse-set/model) to run. // TODO


Add this maven server configuration to maven setting. The env.GITHUB_ACTOR and env.GITHUB_TOKEN can change your Github user and token or set it as environtment variable.
```
  <servers>
    <server>
      <id>set-github-browser</id>
      <username>${env.GITHUB_ACTOR}</username>
      <password>${env.GITHUB_TOKEN}</password>
    </server>
    <server>
      <id>set-github</id>
      <username>${env.GITHUB_ACTOR}</username>
      <password>${env.GITHUB_TOKEN}</password>
    </server>
    <server>
      <id>set-github-model</id>
      <username>${env.GITHUB_ACTOR}</username>
      <password>${env.GITHUB_TOKEN}</password>
    </server>
    <server>
      <id>set-github-toolboxmodel</id>
      <username>${env.GITHUB_ACTOR}</username>
      <password>${env.GITHUB_TOKEN}</password>
    </server>
    <server>
      <id>set-github-build</id>
      <username>${env.GITHUB_ACTOR}</username>
      <password>${env.GITHUB_TOKEN}</password>
    </server>
  </servers>
```

# Development build

This is the recommended way to build and debug for development. 

1. Clone this repository or download a source archive.
2. Set environment variable `ECLIPSE_HOME`
3. Run javascript file `releng/eclipse/development_prepare.js` for complie and copy web package to Eclipse IDE directory.
4. Create a new workspace in the Eclipse IDE
5. Import projects from `java/` via File -> Import -> Existing Projects into Workspace
6. Import the Checkstyle configuration from `releng/eclipse/checkstyle.xml` via Window -> Preferences -> Checkstyle
7. Import the Java formatter configuration from `releng/eclipse/java-formatter.xml` via Window -> Preferences -> Java -> Code Style -> Formatter
8. Set maven setting file to the setting, which contain above server setting `Window -> Preferences -> Maven -> User Settings`
9. Set the target platform in `org.eclipse.set.releng.target` by opening the file `org.eclipse.set.releng.target.target` and click on `Set as Target Platform` or `Reload Target Platform` in the top right corner
10. Synchronize and launch the product in `org.eclipse.set.releng.set.product`
11. Adapt the working directory in the launch configuration to a local directory, which contains an unpacked copy of a full Eclipse SET build (use a recent Github Actions build of the same branch for compatibility).  

# Production build

This is the recommended way if you want a production-style build. This is also what we have implemented on the Jenkins instance.
If you want to develop SET, this is not recommended as the subcomponents are relatively stable and are easy to aquire from our download site.

1. Follow the build instructions for the [model subcomponent](https://gitlab.eclipse.org/eclipse/set/model). 
3. Follow the build instructions for the [browser subcomponent](https://gitlab.eclipse.org/eclipse/set/browser).
4. Adjust the target platform in `java/bundles/org.eclipse.set.releng.target/org.eclipse.set.releng.target.target` to point to your built artifacts.
5. Build web components
    1. In `web/pdf`, `web/siteplan` and `web/textviewer`:
        1. Download dependencies via `npm ci` (or `npm install` if you changed the package.json)
        2. Build via `npm run build`
        4. Copy the contents of the resulting `build`-directory to the matching Eclipse SET directory in `java/bundles/org.eclipse.set.feature/rootdir`. 
    2. In `web/about` and `web/developerhelp`: 
        1. Run `hugo` to build the info pages
        2. Copy the conte
6. Build via maven: `mvn clean verify`

The SET product is now located under `java/bundles/org.eclipse.set.releng.set.product/target/products`. 
