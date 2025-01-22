#include <iostream>
#include <iomanip>

float fun_aux(float a, float b, float c) {
    return std::max(std::max(a, b), c);
}

void fun_arr(float* arr1, float* arr2, int N, float (*fun_aux)(float, float, float)) {
    for (int i = 0; i < N; i++) {
        arr2[i] = fun_aux(arr1[3*i], arr1[3*i + 1], arr1[3*i + 2]);
    }
}

int main() {
    int N = 4;
    
    try {
        float* arr1 = new float[3 * N];
        float* arr2 = new float[N];
        
        float example[] = {1.0, 1.0, 2.5, 2.0, 1.0, -0.5, -1.5, -3.0, -4.0, 0.0, 0.0, 0.0};
        for (int i = 0; i < 3 * N; i++) {
            arr1[i] = example[i];
        }
        
        fun_arr(arr1, arr2, N, fun_aux);
        
        std::cout << "Tablica wynikowa: ";
        for (int i = 0; i < N; i++) {
            std::cout << std::fixed << std::setprecision(1) << arr2[i] << " ";
        }
        std::cout << std::endl;
        
        // Zwolnienie pamięci z użyciem delete[]
        delete[] arr1;
        delete[] arr2;
    }
    catch (const std::bad_alloc& e) {
        std::cerr << "Błąd alokacji pamięci: " << e.what() << std::endl;
        return 1;
    }
    
    return 0;
}
