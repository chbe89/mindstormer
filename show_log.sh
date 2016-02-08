#!/bin/bash
while :
do
	LOG=$(curl --silent -X GET 127.0.0.1:5000)
	LOG=$(echo $LOG | sed 's/<br>/\'$'\n/g')
	echo $LOG
	sleep 4
	clear
done
