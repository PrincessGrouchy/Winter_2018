/* CPSC 457 (Winter 2019)
 * Week 3 - Section 1
 * Multiprocess Programming
 * Sina Keshvadi
 *
 * Notes: No error handling!
 * ======================================
 * Create a file called mp1.cpp and write the following piece of code:
 * - review output
 * - change loop from 5 to 50 and then review the output
 *
 */

#include <iostream>
#include <cstdio>
#include <unistd.h>
using namespace std;
int main() {
	fork();
	for (int i = 1; i <= 50; i++)
		cout << i << endl;
	return 0;
}
