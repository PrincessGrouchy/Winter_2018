/* CPSC 457 (Winter 2019)
 * Week 3 - Section 1
 * Multiprocess Programming
 * Sina Keshvadi
 *
 * Notes: No error handling!
 *
 * ================================
 * A process is a program in execution
 * 1. Create a file called multiproc1.cpp and write the following piece of code:
 * 	a. Compile and run the code.
 * 	b. Run the executable multiple times on the same terminal.
 * 		-Does the process id (pid) change on each run? Reason
 * 		-Does the parent process id (pid) change on each run? Reason
 ======================================
 */

#include <iostream>
#include <cstdio>
#include <unistd.h>
using namespace std;
int main() {
	cout << "Process ID id :" << getpid() << endl;
	cout << "Parent Process id is " << getppid << endl;
	return 0;
}
