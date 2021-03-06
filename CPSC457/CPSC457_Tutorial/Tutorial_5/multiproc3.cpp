/* CPSC 457 (Winter 2019)
 * Week 3 - Section 1
 * Multiprocess Programming
 * Sina Keshvadi
 *
 * Notes: No error handling!
 * ======================================
 * fork() duplicates the current process
 *  The only way to differentiate the child and parent process is
 *  looking to the return value of the function
 *  Returns 0 in the child process
 *  Returns child process pid in the parent
 *  fork() is the only way to create a process in
 *  Unix-like operating systems.
 *  a. Compile and run this code.
 *  b. Run the executable.
 *  	- How many times �Goodbye World� is printed? Reason.
 *	======================================
 *  change the code to this:
 *  - How many times �Hello World� is printed? Reason.
 *  - How many times �Goodbye World� is printed? Reason.
 */

#include <iostream>
#include <cstdio>
#include <unistd.h>
using namespace std;
int main() {
	pid_t pid;
	cout << "Hello World" << endl;
	pid = fork();
	pid = fork();
	pid = fork();
	cout << "Goodbye World " << pid << endl;
	return 0;
}
