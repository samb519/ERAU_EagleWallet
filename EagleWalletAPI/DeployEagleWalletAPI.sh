#!/bin/bash

systemctl stop EagleWalletAPI.service

git clean -df
git checkout Tyler_Transactions
git pull
dotnet build

systemctl start EagleWalletAPI.service
systemctl status EagleWalletAPI.service
