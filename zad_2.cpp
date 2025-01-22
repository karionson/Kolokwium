#include <iostream>
using namespace std;

// Funkcja zwracająca wskaźnik do elementu tablicy trójkątnej
void* getElement(void** arr, int N, size_t sizen, int x, int y) {
    // Sprawdzenie czy współrzędne są prawidłowe
    if (x < 0 || y < 0 || x >= N || y >= N-x) {
        return nullptr;
    }
    
    // Zwrócenie wskaźnika na element o zadanych współrzędnych
    return static_cast<char*>(arr[y]) + x * sizen;
}

int main() {
    const int N = 5;
    
    // Utworzenie tablicy wskaźników
    int** arr = new int*[N];
    
    for (int i = 0; i < N; i++) {
        arr[i] = new int[N-i];
    }
    
    int value = 0;
    for (int i = 0; i < N; i++) {
        for (int j = 0; j < N-i; j++) {
            arr[i][j] = value++;
        }
    }
    
    for (int i = 0; i < N; i++) {
        for (int j = 0; j < N-i; j++) {
            cout << " " << arr[i][j];
        }
        cout << endl;
    }
    
    int x = 0, y = 4;
    void* element = getElement((void**)arr, N, sizeof(int), x, y);
    
    if (element != nullptr) {
        cout << "OUT: " << *static_cast<int*>(element) << " (dla x=" << x << ", y=" << y << ")" << endl;
    } else {
        cout << "Nieprawidłowe współrzędne!" << endl;
    }
    
    // Dealokacja pamięci
    for (int i = 0; i < N; i++) {
        delete[] arr[i];
    }
    delete[] arr;
    
    return 0;
}
