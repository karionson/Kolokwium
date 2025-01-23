#include <iostream>
#include <cstring>

using namespace std;

void dd(char **str, char **result, int N) {
    for (int i = 0; i < N; i++) {

        char* token = strtok(str[i], ";");
        char *user_name = token;
        if(token) {
            cout << user_name << endl;
        }
        token = strtok(NULL, ";");
        int cyfry = atoi(token);
        cout << cyfry;
        int suma_cyfr = 0;
        suma_cyfr += cyfry;
    }
}

int main() {
    char **str = new char*[4];
    int N = 4;
    for (int i = 0; i < 4; ++i) {
        str[i] = new char[100];
    }
    // str[0] = "MystiqueHero;1365;6890;15210";
    // str[1] = "Frizz;5400;6200;11000";
    // str[2] = "Ziemniak;9965;11000;73000";
    // str[3] = "Evel00na;10;10;90";

    strcpy(str[0], "MystiqueHero;1365;6890;15210");
    strcpy(str[1], "Frizz;5400;6200;11000");
    strcpy(str[2], "Ziemniak;9965;11000;73000");
    strcpy(str[3], "Evel00na;10;10;90");


   // cout << str[0];
    char **result;
    dd(str, result, N);
}

//Ranking gry NewCSShooter przechowywany jest  na serwerze w następujący sposób:
//MystiqueHero;1365;6890;15210
//Frizz;5400;6200;11000
//Każdy wiersz reprezentuje statystyki rozegranych meczy dla danego użytkownika. Pojedynczy wiersz zawiera następujące informacje
// oddzielone od siebie średnikiem: nazwa użytkownika, ilość wygranych meczy, ilość rozegranych meczy, ilość dokonanych likwidacji. 
//Przykład 
//    MystiqueHero;1365;6890;15210,
//można zinterpretować, że gracz MystiqueHero wygrał 1365 meczy na 6890 wszystkich rozegranych meczy i dokonał 15210 likwidacji.
//Zbuduj ranking graczy obliczając ich wynik rankingowy. Napisz funkcję, która przyjmuje dynamiczną tablicę napisów (c-string), 
//w której w każdym elemencie przechowywany jest zapis statystyki danego gracza oraz drugą taką tablicę  wyjściową na wyniki. 
//Funkcja powinna w drugą tablicę wyjściową wpisać informacje o graczu oraz jego wynik liczony 
//w następujący sposób: wygrane mecze  + wszystkie mecze + ilość likwidacji. 
//Możesz założyć że nazwa użytkownika to nie więcej niż 12 znaków oraz całkowity wynik to liczba całkowita złożona z maksymalnie 10 cyfr. 
//Napisz program testujący zaimplementowaną funkcję.
//IN:
//[
//“MystiqueHero;1365;6890;15210”,
//“Frizz;5400;6200;11000”,
//“Ziemniak;9965;11000;73000”,
//“Evel00na;10;10;90”
//]
//OUT:
//[
//“MystiqueHero 23465”, //(bo 1365 + 6890 + 15210 = 23465)
//“Frizz 22600”,
//“Ziemniak 93965”,
//“Evel00na 110”,
