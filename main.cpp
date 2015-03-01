#include <iostream>
#include <stdlib.h>

using namespace std;

const char version[] = "v0.1";

using namespace std;
int main()
{
    cout << "Starting PI3 launcher version " << version << ".." << endl;
    cout << "Detecting if java is installed..." << endl;
    char javahome[] = getenv("JAVA_HOME");
    cout << "JAVA_HOME=" << javahome << endl;
    return 0;
}
