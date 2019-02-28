/*
 * myFind.cpp
 *
 *  Created on: Feb 11, 2019
 *      Author: arebe
 */
/*$ find . -type f
 * recursively scan the current directory for all files ending with the given
 suffix, and then print the the filenames of the largest N files to the
 standard output,
 */
#include <sys/types.h>
#include <dirent.h>
#include <iostream>
#include <stdio.h>
#include <string.h>
using namespace std;

void myDirReader(const string name, const string aboveFolder);
//void myDirReader(const struct dirent *old_dp);
//void myDirReader(const char* name);
string oneDot = ".";
string twoDot = "..";
string backSlash = "/";

int main() {
	/*DIR* dir = opendir("./a/b/c");
	 struct dirent * dp = readdir(dir);
	 cout << dp->d_name << "name" << endl;
	 cout << dp->d_type << "type" << endl;
	 struct dirent * dp2 = readdir(dir);
	 cout << dp2->d_name << "name" << endl;
	 cout << dp2->d_type << "type" << endl;*/
	myDirReader(".", "");
}
void myDirReader(const string name, const string aboveFolder) {
//void myDirReader(const char* name) {
	//cout << "start name:" << name << endl;
	string startOpen = aboveFolder + name;
	//strcat(startOpen, aboveFolder.c_str());	// ./a/
	//strcat(startOpen, name);				// ./a/b
	//cout << "startOpen:" << startOpen << endl;
	DIR* dir = opendir(startOpen.c_str());
	//cout << "after open" << endl;
	struct dirent * dp;
	//cout << "after dp" << endl;
	while ((dp = readdir(dir)) != NULL) {
		//cout << dp->d_name << "in while d_name" << endl;
		//cout << dp->d_type << "type" << endl;
		if (dp->d_type == DT_DIR && strcmp(dp->d_name, oneDot.c_str()) != 0
				&& strcmp(dp->d_name, twoDot.c_str()) != 0) {
			string sendingFolder;

			sendingFolder += aboveFolder;			// ./a/
			//strcat(sendingFolder, aboveFolder);
			sendingFolder += name;
			sendingFolder += backSlash;
			//strcat(sendingFolder, backSlash);
			//sendingFolder += dp->d_name;			// ./a/b
			//strcat(sendingFolder, dp->d_name);
			//sendingFolder += backSlash;				// ./a/b/
			//cout << dp->d_name << " sendingfolder:" << sendingFolder << endl;
			//cout << dp->d_name << " sendingfolder " << endl;
			myDirReader(dp->d_name, sendingFolder);

		} else if (strcmp(dp->d_name, oneDot.c_str()) != 0
				&& strcmp(dp->d_name, twoDot.c_str()) != 0) {
//			cout << oneDot << dp->d_name << endl;
			cout << aboveFolder << name << backSlash
					<< dp->d_name << endl;

		} else {

			//do nothing
			//this is bad boolean algebra but whatever
			//cout << "should never happen" << endl;
			//cout << "			" << dp->d_name << "name" << endl;
			//cout << "			" << dp->d_type << "type" << endl;
		}
	}
	closedir(dir);
	//cout << "			" << "yay" << endl;
}

void find(const string dirpath) {

}
/*#include <iostream>
 #include <String.h>
 #include <vector>
 #include <algorithm>
 #include <iterator>
 #include <sys/types.h>
 #include <dirent.h>
 using namespace std;

 int main() {
 vector<string> v;
 readdir_r(".", v);
 std::copy(v.begin(), v.end(),
 std::ostream_iterator < std::string > (std::cout, "\n"));
 }
 void read_directory(const std::string& name, vector<string>& v) {
 DIR* dirp = opendir(name.c_str());
 struct dirent * dp;
 while ((dp = readdir(dirp)) != NULL) {
 v.push_back(dp->d_name);
 }
 closedir(dirp);
 }
 */
/*#include <fstream>
 #include <iostream>
 #include <experimental/filesystem>
 //namespace fs = std::filesystem;

 int main()
 {
 std::filesystem::create_directories("sandbox/a/b");
 std::ofstream("sandbox/file1.txt");
 std::ofstream("sandbox/file2.txt");
 for(auto& p: std::filesystem::directory_iterator("sandbox"))
 std::cout << p.path() << '\n';
 std::filesystem::remove_all("sandbox");
 }*/

