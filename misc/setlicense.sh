#!/bin/bash

LICENSE=$(dirname $0)/apache.java
LEN=$(wc -l $LICENSE | awk '{ print $1 }')

find . -name '*.java' | while read j; do
    head -$LEN $j | diff $LICENSE - || \
        ( ( cat $LICENSE; echo; cat $j) > /tmp/$(basename $j).new; mv /tmp/$(basename $j).new $j )
done
