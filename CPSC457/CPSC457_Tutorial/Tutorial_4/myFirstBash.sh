#!/bin/bash
#Example 01 - Hello World
echo "Hello world"
#Example 02 - Shell Script Arguments 
	#example of using arguments to a script
#run the below code by append arguments in terminal. for example ./a.sh Name Family
echo "My first name is $1"
echo "My surname is $2"
echo "Total number of arguments is $#"
#Example 03 - Hello User 
v="Hello $USER"
echo $v
#Example 04 - Variables 
a=12;
b=13;
str="Hello World"
echo $str
echo $a
echo $b
echo $a+100
echo $b+$a
#Example 05 - Functions
function numbers
{
	for i in {10..20}
	do
		echo $i
	done
}
function hello
{
	echo hello
}
hello
sleep 1
numbers
#Example 06 - IF
pass="Test"
if [ "$pass" = "Test" ]; then
echo "That is okay !"
fi
#Example 07 - IF and Else
pass="wrong"
if [ "$pass" = "Test" ] ; then
 echo "That is okay !"
else
 echo "That is not okay :("
fi
#Example 08
# this script is about password !! and read from user
echo "What is the password ? "
sleep 3
read pass
if [ "$pass" = "password" ] ; then
 echo "That is okay !"
elif [ "$pass" = "ubuntu" ] ; then
 echo " nice "
 else
 echo "That is not okay, " $pass " is not password :("
fi
#Example 09 - Loop
for i in {0..100..10}
do
 echo number $i
done
#Example 10 - Using linux command 
ls > out.txt
cat out.txt
echo $(date)
#Example 11
#This code find all .cpp files in your directory.
echo
while IFS= read -r line; do echo "$line"; done< <(ls *.cpp)
#Example 12
#First create a folder with at least 10 images. Then create an empty folder namely
#"out" in this Folder. Then write this code and run.
#Resize all jpg images and store in folder out
while IFS= read -r line; do convert -resize $a "$line" "out/$line"; done< <(ls *.jpg)