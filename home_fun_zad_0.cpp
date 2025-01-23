#include <iostream>
#include <algorithm>

using namespace std;

void sort_arr(int *arr, int N) {
        cout << arr[N - 3];

}

int main() {
    const int N = 5;
    int *arr = new int [N]{3,2,4,5,7};
    //sort_arr(arr, N);


    int **arr2 = new int*[N];
    for(int i = 0; i < N; ++i) {
        arr2[i] = new int[N];
    }
    
    
    for (int i = 0; i < N; ++i) {
        for(int j = 0; j < N; ++j) {
            arr2[i][j] = i + j + 1;
        }
    }
        for (int i = 0; i < N; ++i) {
        for(int j = 0; j < N; ++j) {
            cout << arr2[i][j];
        }
        cout << endl;
    }

}