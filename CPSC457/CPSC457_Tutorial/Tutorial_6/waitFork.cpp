/*Let us now control the flow of execution in a multi-process environment:
 Hint: use wait(). You can use "man 2 wait" to see the manual for wait() system call.
 */
// Solution
#include <iostream>
#include <cstdio>
#include <unistd.h>
#include <sys/wait.h>
using namespace std;
int main() {
	pid_t pid;
	int status;
	if (fork()) {
		cout << "I'm the Parent, and waiting\n" << endl;
		pid = wait(&status);
		cout << "I'm the Parent. my son's PID is " << pid
				<< " my son's exit status is" << status << endl;
	} else {
		cout << "I'm the Son, and sleeping\n" << endl;
		sleep(1);
		cout << "I'm the Son, and exiting\n";
	}
	cout << "Goodbye World\n";

	return (0);
}
