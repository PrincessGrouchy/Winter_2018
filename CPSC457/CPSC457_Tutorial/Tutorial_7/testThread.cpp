// Sample thread structure
#include <pthread.h>
#include <iostream>
#include <cstdlib>
#include <unistd.h> // for sleep
using namespace std;
void *print_hello_world(void *unused) {
	cout << "Hello World\n";
	return NULL;
}
int main(int argc, char *argv[]) {
	pthread_t mythread;
	cout << "Main here ...\n";
	pthread_create(&mythread, NULL, &print_hello_world, NULL);
	cout << "Main again...\n";
	sleep(1);
	return 0;
}
// compile thread codes by : g++ -lpthread a.cpp
// What's happen if you remove sleep(1); line?
