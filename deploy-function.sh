#!/bin/bash

is_path_set=false

while (($#)); do
    case $1 in
        "--path" | "-p")
            shift
            is_path_set=true

            if [ ! $? -eq 0 ]; then
                echo -e "\033[1;91m[ERROR] Path $1 not found. \033[0m"
                exit 1
            fi

            cd $1
            shift
        ;;
    esac
done

if ! $is_path_set; then
    echo -e "\033[1;91m[ERROR] Path should be specified. \033[0m"
    exit 1
fi

echo -e "\033[1;96m[INFO] Running 'mvn clean package' \033[0m"
mvn clean package

if [ ! $? -eq 0 ]; then
    echo -e "\033[1;91m[ERROR] 'mvn clean package' failed to run \033[0m"
    exit 1
fi


echo -e "\033[1;96m[INFO] Running 'mvn azure-functions:deploy' \033[0m"
mvn azure-functions:deploy

if [ ! $? -eq 0 ]; then
    echo -e "\033[1;91m[ERROR] 'mvn azure-functions:deploy' failed to run \033[0m"
    exit 1
fi
