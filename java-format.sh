echo "Running java format on all files"

if [[ -z "$1" ]]; then
    echo "No eclipse path provided. Please provide path to eclipse executable as first parameter"
    read -p "Script could not start. Press Enter to close..." </dev/tty
    exit 1
fi

if [[ -z "${JAVA_HOME}" ]]; then
    echo "No java home provided. Please set environment variable JAVA_HOME to the bin directory of your java installation"
    read -p "Script could not start. Press Enter to close..." </dev/tty
    exit 1
fi

$1 -noSplash -vm "$JAVA_HOME/javaw.exe" -data ./java-format-workspace/ -application org.eclipse.jdt.core.JavaCodeFormatter -config ./releng/eclipse/code-formatter.xml .
rm -R ./java-format-workspace

if [[ -z "${CI}" ]]; then
    read -p "Script finished. Press Enter to close..." </dev/tty
fi