// Writing in a file by c
#include <stdio.h>
#include <stdlib.h>
int main() {
  int writing = 0;
  int reading = 1;
  if(writing){
    FILE *fp;
    fp = fopen("ctest.txt", "w+");
    fprintf(fp, "This is testing for fprintf...\n");
    fputs("This is testing for fputs...\n", fp);
    fclose(fp);
    printf("File saved.");
    return 0;
  }
  // Reading from a file in c
  if (reading){
    FILE *fp;
    char buff[255];
    fp = fopen("ctest.txt", "r");
    fscanf(fp, "%s", buff);
    printf("1 : %s\n", buff );
    fgets(buff, 255, (FILE*)fp);
    printf("2: %s\n", buff );
    fgets(buff, 255, (FILE*)fp);
    printf("3: %s\n", buff );
    fclose(fp);
    return 0;
  }
}
