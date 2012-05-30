#!/bin/bash
#
# Usage:
# ./release.sh <stable_commit> [last_commit [new_commit]]
# If last_commit is not specified, it's assumed to be stable_commit.
# Either new_commit is not specified (in such case the current date will be
# used), or it is NOT a date (such as @{yesterday}).

(( $# <= 3 )) || exit 1

stable="$1"
last="$2"
new="$3"

if [ -z "$stable" ]; then
    stable='@{yesterday 23:59}'
fi

if [ -z "$last" ]; then
    last="$stable"
fi

if [ -z $new ]; then
    vers=`date +%F`
    new="@{1 second ago}"
else
    vers=${new#v}
fi

branch=$(git branch --no-color | grep '\*' | cut -d' ' -f2)
if [ $branch = 'master' ]; then
    branch=
fi
prefix=ceppo-"$vers"
path="$HOME/Labs/backups/ceppo/$branch/$prefix"
tar="$prefix".tar

mkdir -p "$path"
git log --no-merges "$last".."$new" > "$path"/ChangeLog-$vers
git shortlog --no-merges "$last".."$new" > "$path"/ShortLog-$vers
git diff --stat --summary -C "$last" "$new" > "$path"/diffstat-$vers
git diff "$stable" "$new" | gzip -9 > "$path"/patch-$vers.gz
git archive --prefix=$prefix/ "$new" | bzip2 -c > "$path"/$prefix.tar.bz2
cd "$path"/..
tar cf "$tar" "$prefix"
