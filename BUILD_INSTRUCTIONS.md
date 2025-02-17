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
The plugins can install over Eclipse -> Help -> Installed New Software

||Plugin Name| Install URL|
|------|------|------|
1|Eclipse Platform | https://download.eclipse.org/eclipse/updates/4.32
2|Eclipse RPC Target Components | https://download.eclipse.org/eclipse/updates/4.32
3|Eclipse Plugin Developments | https://download.eclipse.org/eclipse/updates/4.32
4|Equinox Target Components | https://download.eclipse.org/eclipse/updates/4.32
5|Xtext Complete SDK | https://download.eclipse.org/releases/2024-12
6|EMF - Eclipse Modeling Framwork SDK | https://download.eclipse.org/releases/2024-12
7|M2E PDE Integration | https://download.eclipse.org/releases/2024-12
8|Checkstyle | https://checkstyle.org/eclipse-cs-update-site
9|SWTBot for Eclipse Testing | https://download.eclipse.org/releases/2024-12

# Development build

This is the recommended way to build and debug for development. 

1. Clone this repository or download a source archive.
2. Copy the `data` and `examples` directory from `java/bundles/org.eclipse.set.feature/rootdir` to Eclipse directory
3. Create a new workspace in the Eclipse IDE
4. Import projects from `java/` via File -> Import -> Existing Projects into Workspace
5. Import the Checkstyle configuration from `releng/eclipse/checkstyle.xml` via Window -> Preferences -> Checkstyle
6. Import the Java formatter configuration from `releng/eclipse/java-formatter.xml` via Window -> Preferences -> Java -> Code Style -> Formatter
7. Set the target platform in `org.eclipse.set.releng.target`
8. Launch the product in `org.eclipse.set.releng.set.product`
9. Adapt the working directory in the launch configuration to a local directory, which contains an unpacked copy of a full Eclipse SET build (use a recent Github Actions build of the same branch for compatibility). 

If you want to work on the embedded web components (e.g. the textviewer or the pdf viewer), you need to: 

1. Browse to the appropriate subfolder in `web/`
2. Install npm dependencies via `npm install`
3. Build via `npm run build`
4. Copy the contents of the resulting `build`-directory to the unpacked Eclipse SET build (in the matching `web/` subdirectory). 

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
