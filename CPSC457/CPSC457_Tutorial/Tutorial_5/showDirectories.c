/* CPSC 457 (Winter 2019)
 * Week 3 - Section 1
 * Multiprocess Programming
 * Sina Keshvadi
 *
 * Notes: No error handling!
 * ======================================
 * This code show all directories.
 * Write this code in c.
 *
 * if want to go so many folders up, use while loop until reach a directory (E:/)
 * how know if get there? idk
 */
#include <stdio.h>
#include <dirent.h>
#include <string.h>
void visitDir(const char *path, int depth) {
	DIR *directory = opendir(path);
	struct dirent* element = NULL;
	if (!directory)
		return;
	while (element = readdir(directory)) {
		for (int i = 0; i < depth; i++)
			printf("\t");
		printf("%s\n", element->d_name);
		if (element->d_type == DT_DIR) {
			if (strcmp(element->d_name, ".") && strcmp(element->d_name, "..")) {
				char buffer[1000];
				sprintf(buffer, "%s/%s", path, element->d_name);
				visitDir(buffer, depth + 1);
			}
		}
	}
}
int main() {
	visitDir("..", 0);
	return 0;
}
