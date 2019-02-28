#include <fstream>
#include <iostream>
using namespace std;


int main () {
  bool FILEEX1 = false;
  bool PHONEEX = false;
  bool SEARCHEX = false;
  bool STREX = false;
  bool READEX = true;
  bool APPENDEX = false;
  if(FILEEX1){
    char data[100];
    // open a file in write mode.
    ofstream outfile;
    outfile.open("afile.dat");
    cout << "Writing to the file" << endl;
    cout << "Enter your name: ";
    cin>> data;
    // write inputted data into the file.
    outfile << data << endl;
    cout << "Enter your age: ";
    cin >> data;

    // again write inputted data into the file.
    outfile << data << endl;
    // close the opened file.
    outfile.close();
    // open a file in read mode.
    ifstream infile;
    infile.open("afile.dat");
    cout << "Reading from the file" << endl;
    infile >> data;
    // write the data at the screen.
    cout << data << endl;
    // again read the data from the file and display it.
    infile >> data;
    cout << data << endl;
    // close the opened file.
    infile.close();
    return 0;
  }
  if(PHONEEX){
    fstream phonefile("Contact.txt", ios::out);
    string number, name;
    cout<<"Enter contact's info (0 for quit):"<<endl;
    for(;;)
    {
      cout<<"\nNumber:";
      cin>>number;
      if(number=="0")
      break;
      cout<<"Name:";
      cin>>name;
      phonefile<<number<<" "<<name<<" "<<endl;
    }
    cout<<"All contacts stored in Contact.txt"<<endl;
    return(0);
  }
  if(SEARCHEX){
    fstream phonefile("Contact.txt", ios::in);
    string number, name, searchname;
    bool isfound = false;
    cout<<"Enter a name for searching:"<<endl;
    cin>>searchname;
    while(phonefile>>number)
    {
      phonefile>>name;
      if(searchname==name)
      {
        cout<<name<<" "<<number<<endl;
        isfound=true;
      }
    }
    if(!isfound)
    cout<<searchname<<" is not in this contact file."<<endl;

    return(0);

  }
  if(STREX){
    string str="This is a simple text. \nThis is line 1 \nand this is line 2.";
    ofstream myfile("simple.txt");
    myfile<<str;
    return(0);
  }
  if(READEX){
    fstream myfile("simple.txt", ios::in);
    if(!myfile)
    {
      cout<<"Error in opening the file"<<endl;
      exit(1);
    }
    string str;
    while(getline(myfile, str))
    cout<<str<<endl;
    return(0);
  }
  if(APPENDEX){
    ofstream file("simple.txt", ios::app);
     string newStr = "I am new line\n";
     file<<newStr;
     cout<<"Text Appended."<<endl;
     return(0);
  }
}
