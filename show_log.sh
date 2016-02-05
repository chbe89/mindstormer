#!/bin/bash
while :
do
	LOG=$(curl --silent -X GET 192.168.0.213:5000)
	echo $LOG | sed 's/<br>/\'$'\n/g'
	sleep 4
	clear
done
