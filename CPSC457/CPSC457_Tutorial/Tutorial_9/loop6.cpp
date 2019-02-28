/*
 * loop6.cpp
 *
 *  Created on: Feb 25, 2019
 *      Author: arebe
 */
#include <stdio.h>
#include <stdlib.h>
#include <time.h>
int main() {

	int myArray[10];
	srand(time(NULL));
	for (int i = 0; i < 10; i++) {
		myArray[i] = rand() % 10;
	}
	for (int i = 0; i < 10; i++) {
		printf("%d \n", myArray[i]);
	}
	return 0;
}
