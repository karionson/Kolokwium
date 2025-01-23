#include <iostream>

int main() {
    int n;
    std::cout << "Podaj n: ";
    std::cin >> n;

    // Utwórz tablicę arr i wczytaj liczby elementów dla każdego wiersza
    int* arr = new int[n];
    std::cout << "Podaj " << n << " liczb większych od 0:\n";
    for(int i = 0; i < n; i++) {
        do {
            std::cin >> arr[i];
        } while(arr[i] <= 0);
    }

    // Utwórz tablicę wskaźników arr2d
    double** arr2d = new double*[n];
    
    // Zaalokuj pamięć dla każdego wiersza
    for(int i = 0; i < n; i++) {
        arr2d[i] = new double[arr[i]];
    }

    // Wypełnij arr2d liczbami od użytkownika
    std::cout << "Podaj elementy tablicy arr2d:\n";
    for(int i = 0; i < n; i++) {
        for(int j = 0; j < arr[i]; j++) {
            std::cin >> arr2d[i][j];
        }
    }

    // Utwórz tablicę result i oblicz sumy
    double* result = new double[n];
    for(int i = 0; i < n; i++) {
        result[i] = 0;
        for(int j = 0; j < arr[i]; j++) {
            result[i] += arr2d[i][j];
        }
    }

    // Wyświetl wyniki
    std::cout << "Sumy wierszy:\n";
    for(int i = 0; i < n; i++) {
        std::cout << result[i] << " ";
    }
    std::cout << std::endl;

    // Zwolnij pamięć
    for(int i = 0; i < n; i++) {
        delete[] arr2d[i];
    }
    delete[] arr2d;
    delete[] arr;
    delete[] result;

    return 0;
}
