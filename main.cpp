#include <iostream>
#include <stdlib.h>
#include <windows.h>
#include <stdio.h>
#include <tchar.h>
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
void download() {
    CreateDirectory("c:\\ProjectInstaller3", NULL);
    //HRESULT hr = URLDownloadToFile ( NULL, _T("https://www.dropbox.com/s/t5bnmsg7g6uokzp/PI3.jar?dl=1"), _T("c:\\ProjectInstaller3\\PI3.jar"), 0, NULL );
}
int main()
{
    cout << "Starting PI3 launcher version " << version << ".." << endl;
    LoadLibrary("urlmon.dll");
    cout << GetLastError() << endl;
    cout << "Detecting if java is installed...";
    Sleep(1000);
    wstring value;
    HKEY key;
    if (RegOpenKey(HKEY_LOCAL_MACHINE, TEXT("SOFTWARE\\JavaSoft\\Java Runtime Environment\\"), &key) != ERROR_SUCCESS)
    {
        cout << "Unable to open registry key";
    }
    GetStringRegKey(key, s2ws("CurrentVersion"), value, s2ws("ERROR"));
    wcout << value << endl;
    RegCloseKey(key);
    if(value ==
       wstring(L"ERROR")) {
        cout << "In order to run PI3, you need to have Java installed.\nPress enter to open the java download page...";
        if (cin.get() == '\n'){
            ShellExecute(0, 0, "https://java.com/download/", 0, 0 , SW_SHOW );
        }
        cout << endl;
        return 0;
    }
    if(value != wstring(L"1.8")&&value != wstring(L"1.7")) {
        cout << "You have java ";
        wcout << value;
        cout << " installed.";
        cout << "In order to run PI3, you need to have at least Java 1.7 installed.\nPress enter to open the java download page...";
        if (cin.get() == '\n'){
            ShellExecute(0, 0, "https://java.com/download/", 0, 0 , SW_SHOW );
        }
        cout << endl;
        return 0;
    }
    cout << "You have java ";
    wcout << value;
    cout << " installed.\nYou are good to go!";
    download();



    return 0;
}
