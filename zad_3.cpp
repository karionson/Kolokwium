#include <iostream>
#include <cmath>

// Funkcja pomocnicza obliczająca długość wektora
double vector_length(double x, double y) {
    return sqrt(x*x + y*y);
}

// Główna funkcja przetwarzająca tablicę
void arr_fun(double* begin, double* end, double* result, double (*func)(double, double)) {
    double *curr = begin;
    int i = 0;
    
    while (curr < end) {
        result[i] = func(curr[0], curr[1]);
        curr += 2;
        i++;
    }
}

int main() {
    // Przykładowa tablica wektorów: [1.0,1.0, 2.0,1.0, 1.0,3.0, 3.0,1.0]
    const int vector_count = 4;
    double* vectors = new double[vector_count * 2] {
        1.0, 1.0,
        2.0, 1.0,
        1.0, 3.0,
        3.0, 1.0
    };
    
    // Utworzenie tablicy wynikowej na długości wektorów
    double* lengths = new double[vector_count];
    
    // Obliczenie długości przy użyciu arr_fun
    arr_fun(vectors, vectors + (vector_count * 2), lengths, vector_length);
    
    // Wyświetlenie wyników
    std::cout << "Długości wektorów: ";
    for (int i = 0; i < vector_count; i++) {
        std::cout << lengths[i] << " ";
    }
    std::cout << std::endl;
    
    // Zwolnienie pamięci
    delete[] vectors;
    delete[] lengths;
    
    return 0;
}