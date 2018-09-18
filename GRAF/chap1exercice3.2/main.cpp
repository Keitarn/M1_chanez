#include <iostream>

int main() {

    int SA[] = {2, 3, 0, 3, 4, 6, 0, 6, 0, 5, 0, 2, 0, 4, 0};
    const int size_SA = 15;
    int AFS[] = {0, 3, 7, 9, 11, 13};
    const int size_AFS = 6;

    // init array
    int PA[size_SA] = {0};
    int AFP[size_AFS];
    for (int i = 0; i < size_AFS; ++i) {
        AFP[i] = i;
    }

    for (int i = 0; i < size_AFS; ++i) {
        for (int j = i; j < size_SA; ++j) {
            if (SA[j] == 0)
                break;
            PA[SA[j] - 1] = i + 1;
            for (int k = SA[j]; k < size_AFS; ++k) {
                AFP[k]++;
            }
        }
    }

    // result
    for (int i = 0; i < size_SA; ++i) {
        std::cout << PA[i] << " ";
    }

    std::cout << std::endl;

    for (int i = 0; i < size_AFS; ++i) {
        std::cout << AFP[i] << " ";
    }

    return 0;
}