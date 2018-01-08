#!/bin/bash

LABEL_FILE=$(grep -l pcf8574a /sys/class/gpio/*/*label)
BASE_FILE=$(dirname $LABEL_FILE)/base
BASE=$(cat $BASE_FILE)

echo LABEL_FILE=$LABEL_FILE
echo BASE_FILE=$BASE_FILE
echo BASE=$BASE

pinsStart=408

function exportPin() {
	pinNumber=$1
	index=$((pinsStart+pinNumber))
	sudo sh -c 'echo '$index' > /sys/class/gpio/export'
}

function unexportPin() {
	pinNumber=$1
	index=$((pinsStart+pinNumber))
	sudo sh -c 'echo '$index' > /sys/class/gpio/unexport'
}

function getPinDirection() {
	pinNumber=$1
	index=$((pinsStart+pinNumber))
	cat /sys/class/gpio/gpio$index/direction 
}

function getPinValue() {
	pinNumber=$1
	index=$((pinsStart+pinNumber))
	cat /sys/class/gpio/gpio$index/value 
}

function setPinDirection() {
	pinNumber=$1
	direction=$2
	index=$((pinsStart+pinNumber))
	sudo sh -c 'echo '$direction' > /sys/class/gpio/gpio'$index'/direction'
}

#GPIO 1-6 translate to 2-7
exportPin 2
exportPin 3
exportPin 4
exportPin 5
exportPin 6
exportPin 7

setPinDirection 2 in

getPinValue 2

unexportPin 2
unexportPin 3
unexportPin 4
unexportPin 5
unexportPin 6
unexportPin 7
