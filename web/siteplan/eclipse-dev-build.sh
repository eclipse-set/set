npm run build
echo "Copying siteplan build to $ECLIPSE_ROOT/web/siteplan"
rm -R $ECLIPSE_ROOT/web/siteplan
cp -r ./dist $ECLIPSE_ROOT/web/siteplan

read -p "Script finished. Press Enter to close..." </dev/tty