#!/bin/bash

#exec mvn javadoc:javadoc

# 多模块聚合文档，生成在target/site/apidocs
exec mvn javadoc:aggregate

bin_home="$(dirname ${BASH_SOURCE[0]})"

# 拷贝自定义的index.html到聚合文档目录
cp -vf $bin_home/../docs/apidocs/index.html $bin_home/../target/reports/apidocs/
