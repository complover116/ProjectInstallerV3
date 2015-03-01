#include <iostream>
#include <stdlib.h>
#include <windows.h>
using namespace std;

const char version[] = "v0.1";

using namespace std;

LONG GetStringRegKey(HKEY hKey, const std::wstring &strValueName, std::wstring &strValue, const std::wstring &strDefaultValue)
{
    strValue = strDefaultValue;
    WCHAR szBuffer[512];
    DWORD dwBufferSize = sizeof(szBuffer);
    ULONG nError;
    nError = RegQueryValueExW(hKey, strValueName.c_str(), 0, NULL, (LPBYTE)szBuffer, &dwBufferSize);
    if (ERROR_SUCCESS == nError)
    {
        strValue = szBuffer;
    }
    return nError;
}
std::wstring s2ws(const std::string& str)
{
    int size_needed = MultiByteToWideChar(CP_UTF8, 0, &str[0], (int)str.size(), NULL, 0);
    std::wstring wstrTo( size_needed, 0 );
    MultiByteToWideChar(CP_UTF8, 0, &str[0], (int)str.size(), &wstrTo[0], size_needed);
    return wstrTo;
}
int main()
{
    cout << "Starting PI3 launcher version " << version << ".." << endl;
    cout << "Detecting if java is installed..." << endl;
    wstring value;
    HKEY key;
    if (RegOpenKey(HKEY_LOCAL_MACHINE, TEXT("SOFTWARE\\JavaSoft\\Java Runtime Environment\\"), &key) != ERROR_SUCCESS)
    {
        cout << "Unable to open registry key";
    }
    GetStringRegKey(key, s2ws("CurrentVersion"), value, s2ws("ERROR"));
    wcout << value << endl;
    RegCloseKey(key);

    return 0;
}
