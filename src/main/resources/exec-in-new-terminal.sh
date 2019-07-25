#!/bin/bash

# cd to the current script directory
#cd "$(dirname "$0")"
# retrieves the kernel name
KERNEL_NAME="$(uname -s)"
# running /bin/bash after the commands prevents
# the term window from immediately closing
EXEC_BASH="; exec bash"
# the commands to run
COMMANDS="$@ $EXEC_BASH"

case "${KERNEL_NAME}" in
    Linux*)     machine=Linux
        # checks if gnome-terminal is installed
        if command -v gnome-terminal >/dev/null
        then
            gnome-terminal -- bash -c "$COMMANDS"
        else
            x-terminal-emulator -e "$COMMANDS"
        fi
        ;;
    Darwin*)    machine=Mac
        osascript -e "tell application \"Terminal\" to do script \"cd $PWD; clear; $1 $COMMANDS\""
        ;;
    *)          machine="UNSUPPORTED:${KERNEL_NAME}"
esac
