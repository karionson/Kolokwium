#include <iostream>     // dla cout, endl
#include <cmath>        // dla funkcji sin, cos, round
#include <cstdio>       // dla printf

struct Point {
    double R, F;  // R - promień (odległość od środka), F - kąt w radianach
    double x, y;  // współrzędne kartezjańskie
    
    Point() : R(0), F(0), x(0), y(0) {}  // domyślny konstruktor
    
    Point(double r, double f) : R(r), F(f) {
        x = R * cos(F);
        y = R * sin(F);
    }
};

// Sortowanie bąbelkowe punktów według promienia (R) i kąta (F)
void bubbleSort(Point* points, int n) {
    for (int i = 0; i < n - 1; i++) {
        for (int j = 0; j < n - i - 1; j++) {
            if (points[j].R > points[j + 1].R || 
                (points[j].R == points[j + 1].R && points[j].F < points[j + 1].F)) {
                Point temp = points[j];
                points[j] = points[j + 1];
                points[j + 1] = temp;
            }
        }
    }
}

void sortRadarObjects(const double* coordinates, int size, Point* result) {
    // Konwersja tablicy wejściowej na tablicę punktów
    for (int i = 0; i < size; i += 2) {
        result[i/2] = Point(coordinates[i], coordinates[i + 1]);
    }
    
    bubbleSort(result, size/2);
}

int main() {
    double input[] = {1.2, 6.1, 1.2, 1.1, 5.4, 3.1};
    int size = sizeof(input) / sizeof(input[0]);
    Point* result = new Point[size/2];
    
    sortRadarObjects(input, size, result);
    
    // Używamy %.3f dla dokładnie 3 miejsc po przecinku
    for (int i = 0; i < size/2; i++) {
        printf("%.3f %.3f\n", result[i].x, result[i].y);
    }
    
    delete[] result;
    return 0;
} 