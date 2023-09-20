/**
 * Task_1_1_1 HeapSort algorithm
 */

public class Heapsort {
    /**
     * Sorting array elements in ascending order. First, the unordered sequence becomes a heap.
     * Next, sorting is performed. The first (maximum) element is exchanged with the last element, so the finished
     * sequence is formed at the tail of the array.
     * @param arr The array of integers to sort
     */
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
            sift(arr, 0, i);
        }
    }

    /**
     * The largest element of parent(arr[i]) or children(arr[2*i+1], arr[2*i+2]) appears at the root of the current
     * subtree.
     * @param arr The array of integers
     * @param i The index of the element to be sifted
     * @param n The size of the heap
     */
    private static void sift(int[] arr, int i, int n) {
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
}
