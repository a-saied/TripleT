#!/bin/bash


# count=1
# while [ $count -le 2 ]
# do  
# 	# cd Desktop/TripleT/Basics
# 	printf 'Easy' | java BasicClient 4321
# 	sleep 0.5
# 	(( count++ ))
# done


shopt -s nocasematch
read -p " Execute script? (y/n): " response
if [[ $response == y ]]; then
    printf " Loading....\\n"
    for ((x = 0; x<2; x++)); do
        printf " Open %s Terminal\\n" $x
        osascript -e 'tell application "Terminal" to do script "cd Desktop/TripleT/Basics\n java BasicClient 4321\nEasy"' >/dev/null
    done
fi
shopt -u nocasematch