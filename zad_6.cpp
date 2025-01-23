#include <iostream>
#include <cstring>

// Funkcja obliczająca ranking graczy
// input - tablica napisów zawierających statystyki graczy
// output - tablica wyjściowa na przetworzone wyniki
// size - liczba graczy do przetworzenia
void calculateRanking(char** input, char** output, int size) {
    for (int i = 0; i < size; i++) {
        char username[13] = {0};  // Bufor na nazwę użytkownika (max 12 znaków)
        int wins = 0, games = 0, kills = 0;
        
        // Parsowanie napisu wejściowego na poszczególne wartości
        char* token = strtok(input[i], ";");
        if (token) strncpy(username, token, 12);
        
        token = strtok(NULL, ";");
        if (token) wins = atoi(token);  // Liczba wygranych meczy
        
        token = strtok(NULL, ";");
        if (token) games = atoi(token); // Liczba wszystkich meczy
        
        token = strtok(NULL, ";");
        if (token) kills = atoi(token); // Liczba likwidacji
        
        // Obliczenie wyniku całkowitego (wygrane + wszystkie mecze + likwidacje)
        int total = wins + games + kills;
        
        // Formatowanie napisu wyjściowego (nazwa + spacja + wynik)
        sprintf(output[i], "%s %d", username, total);
    }
}

int main() {
    const int SIZE = 4;
    char** input = new char*[SIZE];
    for (int i = 0; i < SIZE; i++) {
        input[i] = new char[100];
    }
    
    strcpy(input[0], "MystiqueHero;1365;6890;15210");
    strcpy(input[1], "Frizz;5400;6200;11000");
    strcpy(input[2], "Ziemniak;9965;11000;73000");
    strcpy(input[3], "Evel00na;10;10;90");
    
    // Alokacja tablicy wyjściowej
    char** output = new char*[SIZE];
    for (int i = 0; i < SIZE; i++) {
        output[i] = new char[24];
    }
    
    calculateRanking(input, output, SIZE);
    
    for (int i = 0; i < SIZE; i++) {
        std::cout << output[i] << std::endl;
    }
    
    for (int i = 0; i < SIZE; i++) {
        delete[] input[i];
        delete[] output[i];
    }
    delete[] input;
    delete[] output;
    
    return 0;
}

