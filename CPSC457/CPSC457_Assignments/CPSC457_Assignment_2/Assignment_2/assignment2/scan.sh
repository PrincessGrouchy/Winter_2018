#!/bin/bash
limit=$2;
dot='*.';
find $directory -name "$dot$1" -printf '%p %s\n' | sort -nr | head -$limit

#$end="$dot$1";
#find $directory -type f -name $dot$1 -printf  | sort -nr | head -$limit
#echo $dot$1;
#find $directory -printf '%s %p\n'| sort -nr | head -$limit
#find $directory -name $dot$1 | awk '{ x += $5 ; print $0 } END { print "total bytes: " x }'
#while IFS= read -r line; do echo "$line"; done< <(ls *.txt)