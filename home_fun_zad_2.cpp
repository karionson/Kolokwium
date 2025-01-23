#include <iostream>

using namespace std;
 
void* dd(void **arr, int N, size_t sizen, int x, int y) {
    for (int i = 0; i < N; ++i) {
        for (int j = 0; j < N - i; ++j) {

    if(x < N && x >= 0 && y < N - i && y >= 0) {
        return static_cast<char*>(arr[y]) + x * sizen;
    }
    else return nullptr;
        }
    }
 }

int main() {
    const int N = 6;
    int x = 0, y = 4;
    int **arr = new int*[N];
    for (int i = N - 1; i >= 0; --i) {
        arr[i] = new int[i];
        

    }
    for (int i = 0; i < N; ++i) {
        for (int j = 0; j < N - i; ++j) {
            arr[i][j] = i + j + 5;
        }
    }
    for (int i = 0; i < N; ++i) {
        for(int j = 0; j < N - i; ++j) {
            cout << arr[i][j] << " ";
        }
        cout << endl;
    }
    cout << dd((void**)arr, N, sizeof(int), x, y);
}

//
//Napisz program w języku C++, który stworzy dynamiczną tablicę dwuwymiarową
// trójkątną arr o rozmiarze NxN wybranego typu liczbowego
 // i wypełni ją dowolnymi wartościami. Zdefiniuj funkcję, która w parametrze przyjmie 
 //wskaźnik na wskaźnik na typ void (do tablicy trójkątnej arr), rozmiar N, wielkość w
 // bajtach pojedynczego elementu sizen tablicy przekazanej w pierwszym parametrze oraz dwie współrzędne x, y typu całkowitego.
 // Funkcja ma zwrócić wskaźnik typu void ustawiony na podany we współrzędnych x, y element tablicy 
 //lub nullptr jeżeli nie ma takiego elementu. Pokaż użycie tej funkcji i wykonaj dealokację tablicy arr.
//Przykład:
// 0  1  2  3  4
 //5  6  7  8
 //8  9 10
//11 12
//13
//OUT: 7 (dla x=2, y=1)