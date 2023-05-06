#!/bin/zsh

WORKSPACE_PATH=./

while [[ $# -gt 0 ]]
do
    item=$1
    case $item in
        b|build)
            gradle clean build
            shift;
        ;;
        r|run)
            gradle run --continuous
            shift;
        ;;
        *)
            echo "Unrecognized command '$item'";
            exit 1;
        ;;
    esac
done