/*
 * sum.cpp
 *
 *  Created on: Feb 11, 2019
 *      Author: arebe
 *
 *
 */

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <iostream>
#include <fstream>
#include <pthread.h>
using namespace std;

void * thread_sum(void* arg);

#define maxLength 1000000

struct sum_struct {
	int first;
	int last;
	int sum;
};

int inputNums[maxLength];

int main(int argc, char ** argv) {
	if (argc != 3) {
		cerr << "Need 2 inputs: <fileName.extension> and <threadNumber>"
				<< endl;
		return -1;
	}
	char fileName[maxLength];
	strcpy(fileName, argv[1]);
	int threadNumber = atoi(argv[2]);
	if (threadNumber <= 0) {
		cerr
				<< "<threadNumber> invalid input. cannot be 0 or a negative or not-a-number"
				<< endl;
		return -1;
	}
	//reading file
	/*ifstream infile;
	 infile.open(fileName);
	 char data[maxLength];
	 infile >> data;
	 */
	FILE * fp = fopen(fileName, "r");
	if (fp == NULL) {
		cerr << "popen failed:" << endl;
		return -1;
	}
	char buffer[maxLength];
	int position = 0;
	while (fgets(buffer, maxLength, fp)) {
		int len = strlen(buffer);
		int k = atoi(strndup(buffer, len));
		inputNums[position] = k;
		position++;
	}
	//reading 3 numbers (0,1,2) will make position 3
	fclose(fp);

	//after read file
	struct sum_struct threadArgs[threadNumber];
	pthread_t threadArr[threadNumber];
	int status;
	int firstEle = 0, lastEle = -1;
	int numElesPer = position / threadNumber; // = (N/T)
	for (int i = 0; i < threadNumber; i++) {
		//printf("creating thread %d\n", i);
		if ((i + 1) < (position % threadNumber)) {
			firstEle = lastEle + 1;
			lastEle = ((i + 1) * numElesPer) + 1;
		} else {
			firstEle = lastEle + 1;
			lastEle = ((i + 1) * numElesPer);
		}
		threadArgs[i].first = firstEle;
		threadArgs[i].last = lastEle;
		pthread_attr_t attr;
		pthread_attr_init(&attr);
		status = pthread_create(&threadArr[i], &attr, thread_sum,
				&threadArgs[i]);

		if (status != 0) {
			cerr << "Oops, pthread_create returned error code" << status
					<< endl;
			return -1;
		}

	}

	for (int i = 0; i < threadNumber; i++) {
		pthread_join(threadArr[i], NULL);
	}

	int final_sum = 0;
	for (int i = 0; i < threadNumber; i++) {
		//int toAdd = threadArgs[i].sum;
		cout << "Thread " << i + 1 << ": " << threadArgs[i].sum << endl;
		final_sum += threadArgs[i].sum;
	}
	cout << "Sum = " << final_sum << endl;
	//cout << "testing = " << final_sum << endl;
	return 0;

}

void * thread_sum(void* arg) {
	// casting arg from void* to desired type
	struct sum_struct *arg_struct = (struct sum_struct*) arg;
	arg_struct->sum = 0;
	for (int i = arg_struct->first; i <= arg_struct->last; i++) {
		//printf(" %d, ", i);
		arg_struct->sum += inputNums[i];
	}
	//printf("\n\n");
	pthread_exit(0);
}
