#!/bin/bash

exec mvn -T 1C clean source:jar javadoc:javadoc package -Dmaven.test.skip=true -Dmaven.javadoc.skip=false
