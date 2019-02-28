/*
 * sumUpTo_Threads.cpp
 *
 *  Created on: Feb 11, 2019
 *      Author: arebe
 */
#include <pthread.h>
#include <iostream>
#include <cstdlib>
using namespace std;
unsigned long sum;
void *adder(void * number) {
	cout << "Thread ID = " << pthread_self() << endl;
	unsigned long num = (unsigned long) number;
	for (int i = 1; i <= num; i++) {
		sum += i;
	}

	pthread_exit(0);
}
int main(int argc, char *argv[]) {
	unsigned long number = atol(argv[1]);
	pthread_t tid;
	pthread_create(&tid, NULL, adder, (void *) (number / 2));
	long sum2 = 0;
	for (long i = (number / 2) + 1; i <= number; i++)
		sum2 += i;
	// Wait for the thread to exit
	pthread_join(tid, NULL);

	cout << "Sum (" << 1 << ", " << number << ") = " << sum + sum2 << endl;

	return 0;
}
