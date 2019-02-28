/*
 * sumUpTo.cpp
 *
 *  Created on: Feb 11, 2019
 *      Author: arebe
 */
#include <iostream>
using namespace std;
int main(int argc, char ** argv) {
	unsigned long number = atol(argv[1]);
	long sum = 0;
	for (long i = 0; i <= number; i++)
		sum += i;
	cout << "Sum = " << sum << endl;
	return (0);
}

