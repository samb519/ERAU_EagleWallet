#!/bin/bash

systemctl stop EagleWalletAPI.service

git clean -df
git checkout main
git pull
dotnet build

systemctl start EagleWalletAPI.service
systemctl status EagleWalletAPI.service
