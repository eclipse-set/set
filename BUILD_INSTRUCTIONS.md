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
- Eclipse IDE (2023-12)
- [hugo](https://gohugo.io/) (v0.101.0+)

# Development build

This is the recommended way to build and debug for development. 

1. Clone this repository or download a source archive.
2. Create a new workspace in the Eclipse IDE
3. Import projects from `java/` via File -> Import -> Existing Projects into Workspace
4. Import the Checkstyle configuration from `releng/eclipse/CheckstyleEclipse.xml` via Window -> Preferences -> Checkstyle
5. Set the target platform in `org.eclipse.set.releng.target`
6. Launch the product in `org.eclipse.set.releng.set.product`
7. Adapt the working directory in the launch configuration to a local directory, which contains an unpacked copy of a full Eclipse SET build (use a recent Github Actions build of the same branch for compatibility). 

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
