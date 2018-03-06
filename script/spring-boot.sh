#!/bin/bash
#
# 打包启动，停止，重启
#
cd "${0%/*}"

cd cd ..

echo " starting to clean spring-boot "

mvn clean -DskipTests -U

echo " cleaning spring-boot finished "

echo " starting  to install spring-boot "

mvn install -DskipTests -U

echo " install spring-boot finished "

echo " docker building spring-boot "

mvn docker:build

echo " docker finished building spring-boot  "

exit