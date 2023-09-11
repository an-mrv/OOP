/**
 * Task_1_1_1 HeapSort algorithm
 */

public class Heapsort {
    static void sift(int[] arr, int i, int n) {
        int l = 2 * i + 1; //index of the left child
        int r = 2 * i + 2; //index of the right child
        int t;
        int largest = i; //index of the largest element from a node of a parent and two children
        while (l < n) {
            if (arr[l] > arr[i]) {
                largest = l;
            }
            if ((r < n) && (arr[r] > arr[i]) && (arr[r] > arr[l])) {
                largest = r;
            }
            if (largest != i) { //the largest element appears at the root of the current subtree
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
        for (int i = n / 2; i >= 0; i--) { //making a heap
            sift(arr, i, n);
        }
        for (int i = n - 1; i > 0; i--) { //sort the heap
            t = arr[0];
            arr[0] = arr[i];
            arr[i] = t;
            sift(arr, 0, i); //the sorted sequence is formed at the tail of the array
        }
    }
}
