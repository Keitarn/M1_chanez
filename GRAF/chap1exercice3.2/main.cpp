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

    int i = 0;
    for (int j = i; j < size_SA; ++j) {
        if (SA[j] == 0) {
            i++;
        }

        int nbZero = 0;
        for (int k = 0; k < size_SA; ++k) {
            if (PA[k] == 0) {
                nbZero++;
            }

            if (nbZero == SA[j]) {
                for (int l = size_SA - 1; l > k; --l) {
                    PA[l] = PA[l - 1];
                }
                PA[k] = i + 1;

                for (int l = SA[j]; l < size_AFS; ++l) {
                    AFP[l]++;
                }
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