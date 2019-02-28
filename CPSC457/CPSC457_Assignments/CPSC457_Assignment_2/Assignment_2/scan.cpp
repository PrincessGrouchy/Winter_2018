/*
 * scan.cpp
 *
 *  Created on: Feb 11, 2019
 *      Author: arebe
 *
 *       recursively scan the current directory for all files ending with the given
 suffix, then sort the files by their size, and then print the the filenames of the largest N files to the
 standard output, followed by the sum of their sizes. Each file reported should be followed by the
 size of the file in bytes. If the total number of files found is less then N, report all files. The listed
 files should be sorted by the size, in descending order.
 *
 */

#include <stdio.h>
#include <string.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <unistd.h>
#include <iostream>
#include <dirent.h>
using namespace std;

#define maxLength 512
#define maxNumFiles 1000
//int wont work , use define
// I mean, could use final or something but idk
typedef struct fileAndSize {
	char fileName[maxLength];
	int fileSize;
} fileAndSize;

int main(int argc, char ** argv) {
	if (argc != 3) {
		cerr << "Need 2 inputs: <fileType> and <resultlimit>" << endl;
		return -1;
	}
	char fileType[maxLength];
	int resultlimit = 0;
	strcpy(fileType, ".");
	strcat(fileType, argv[1]);
	//cout << "fileType:" << fileType << endl;
	resultlimit = atoi(argv[2]);
	//cout << "resultlimit:" << resultlimit << endl;

	//FILE * fp = popen("./myFind", "r");
	FILE * fp = popen("./myFind", "r");
	//FILE * fp = popen("find . –type f", "r");
	if (fp == NULL) {
		cerr << "myFind failed. Oops." << endl;
		return -1;
	}

	char * readType;	// extension read from line
	char buffer[maxLength];	//should be read into, 1 filepath
	char * myTypeFiles[maxNumFiles]; //should only contain wanted filetypes
	int numFilesRead = 0; //num of wanted filetypes read. Could avoid with 2d array, but iunno

	while (fgets(buffer, maxLength, fp)) {
		readType = strrchr(buffer, '.');
		//cout << "read type:" << readType << endl;
		//cout << "fileType:" << fileType << endl;
		//cout << "strncmp(fileType, readType):"
		int elen = strlen(readType) - 1;
		if (readType[elen] == '\n')
			readType[elen] = '\0';
		//	<< strncmp(fileType, readType, strlen(fileType)) << endl;
		//should return in order the extension, like txt(not .txt)
		if (strncmp(fileType, readType, strlen(fileType)) == 0) {
			//should use toLowerCase... but too much work
			myTypeFiles[numFilesRead] = strndup(buffer, strlen(buffer));
			//cout << "myTypeFiles[i]:" << myTypeFiles[numFilesRead] << endl;
			//strndup is just like strcpy but has a control mechanism for long stuff
			numFilesRead++;
		}
	}
	fclose(fp);
	//don't need all results now, work ONLY myTypeFiles
	if (resultlimit > numFilesRead)
		resultlimit = numFilesRead;

	int totalWantedSize = 0; //total size of ALL wanted files.

	struct fileAndSize filesAndSizesArray[numFilesRead];
	struct stat givenStat;
	struct fileAndSize givenFile;
	for (int i = 0; i < numFilesRead; i++) {
		int success = stat(myTypeFiles[i], &givenStat);
		if (success != 0) {
			cerr << "stat() failed. Oops." << endl;
			continue;
		}
		strcpy(givenFile.fileName, myTypeFiles[i]);
		givenFile.fileSize = givenStat.st_size;
		filesAndSizesArray[i] = givenFile;

		totalWantedSize += givenStat.st_size;
	}

	//sort();

	for (int j = 0; j < resultlimit; j++) {
		cout << filesAndSizesArray[j].fileName << "	"<< filesAndSizesArray[j].fileSize
				 << endl;
	}
	cout << "Total size: " << totalWantedSize << " bytes." << endl;
	return 0;
}
