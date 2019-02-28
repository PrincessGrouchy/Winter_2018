/* CPSC 457 (Winter 2019)
 * Week 3 - Section 1
 * Multiprocess Programming
 * Sina Keshvadi
 *
 * Notes: No error handling!
 * ======================================
 *
 */

#include <iostream>
#include <cstdio>
#include <unistd.h>
using namespace std;
int main() {
	cout << "Hello" << endl;
	pid_t pid;
	pid = fork();
	if (pid < 0)
		cout << "Folk Failed";
	else if (pid == 0) {
		cout << endl << "I am Child" << endl;
		for (char i = 'a'; i <= 'g'; i++) // change loop to A to z
			cout << i << endl;
		cout << endl;
	} else {
		cout << endl << "I am Parent" << endl;
		for (int i = 1; i <= 4; i++)
			cout << i << endl;
		cout << endl;
	}
	cout << "Good Bye" << endl;
	return 0;
}
