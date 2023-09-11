/**
 * Task_1_1_1 HeapSort algorithm
 */

public class Heapsort {
    static void sift(int[] arr, int i, int n) {
        int l = 2 * i + 1;
        int r = 2 * i + 2;
        int t;
        int largest = i;
        while (l < n) {
            if (arr[l] > arr[i]) {
                largest = l;
            }
            if ((r < n) && (arr[r] > arr[i]) && (arr[r] > arr[l])) {
                largest = r;
            }
            if (largest != i) {
                t = arr[i];
                arr[i] = arr[largest];
                arr[largest] = t;
                i = largest;
                l = 2 * i + 1;
                r = 2 * i + 2;
            }
            else {
                break;
            }
        }
    }

    static void heap_sort(int[] arr) {
        int n = arr.length;
        int t;
        for (int i = n / 2; i >= 0; i--) {
            sift(arr, i, n);
        }
        for (int i = n - 1; i > 0; i--) {
            t = arr[0];
            arr[0] = arr[i];
            arr[i] = t;
            sift(arr, 0, i);
        }
    }
}
