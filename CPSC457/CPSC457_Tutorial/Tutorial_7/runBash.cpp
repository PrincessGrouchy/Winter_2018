/*
 * runBash.cpp
 *
 *  Created on: Feb 11, 2019
 *      Author: arebe
 */
// run bash file from c++ program
#include <iostream>
#include <cstdlib>
#include <cstring>
using namespace std;
int main()
{
cout<<"Hi!"<<endl;
system("./sumBash.sh");
cout<<"BYE!"<<endl;
return 0;
}

