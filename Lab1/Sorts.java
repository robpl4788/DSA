
/** 
** Software Technology 152
** Class to hold various static sort methods.
*/
class Sorts
{
    // bubble sort
    public static void bubbleSort(int[] A)
    {
      boolean sorted = false;

      int iteration = 1;
      int length = A.length;

      // for (int i = 0; i < length; i ++) {
      //   System.out.print(A[i]);
      //   System.out.print(" ");
      // }
      // System.out.println( " Now sorting: ");

      while (sorted == false) {
        sorted = true;
        for (int i = 0; i < length - iteration; i ++)
        {
          if (A[i] > A[i + 1]) {
            swap(A, i, i + 1);
            sorted = false;
          }
        }
      }

      // for (int i = 0; i < length; i ++) {
      //   System.out.print(A[i]);
      //   System.out.print(" ");
      // }
      // System.out.println();

    }//bubbleSort()

    // selection sort
    public static void selectionSort(int[] A)
    {
      int length = A.length;

      for (int iteration = 0; iteration < length - 1; iteration ++){
        int smallestIndex = iteration;
        for (int i = iteration + 1; i < length; i ++)
        {
          if (A[smallestIndex] > A[i]) {
            smallestIndex = i;
          }
        }
        swap(A, iteration, smallestIndex);
      }
      //       for (int i = 0; i < length; i ++) {
      //   System.out.print(A[i]);
      //   System.out.print(" ");
      // }
      // System.out.println();

      // System.out.println(Arrays.toString(A));

    }// selectionSort()

    // insertion sort
    public static void insertionSort(int[] A)
    {
      int length = A.length;
      
      for (int markerIndex = 1; markerIndex < length; markerIndex ++)
      {
        int testIndex = markerIndex - 1;
        int toInsert = A[markerIndex];
        while (testIndex >= 0 && toInsert < A[testIndex])
        {
          A[testIndex + 1] = A[testIndex];
          testIndex --;
        }
        testIndex ++;
        A[testIndex] = toInsert;
      }
      // for (int i = 0; i < length; i ++) {
      //   System.out.print(A[i]);
      //   System.out.print(" ");
      // }
      // System.out.println();
    }// insertionSort()

    // mergeSort - front-end for kick-starting the recursive algorithm
    public static void mergeSort(int[] A)
    {
    }//mergeSort()
    @SuppressWarnings("unused")
    private static void mergeSortRecurse(int[] A, int leftIdx, int rightIdx)
    {
    }//mergeSortRecurse()
    @SuppressWarnings("unused")
    private static void merge(int[] A, int leftIdx, int midIdx, int rightIdx)
    {
    }//merge()


    // quickSort - front-end for kick-starting the recursive algorithm
    public static void quickSort(int[] A)
    {
    }//quickSort()
    @SuppressWarnings("unused")
    private static void quickSortRecurse(int[] A, int leftIdx, int rightIdx)
    {
    }//quickSortRecurse()
    @SuppressWarnings("unused")
    private static int doPartitioning(int[] A, int leftIdx, int rightIdx, int pivotIdx)
    {
		return 0;	// TEMP - Replace this when you implement QuickSort
    }//doPartitioning


    private static void swap (int[] A, int index1, int index2)
    {
      int temp = A[index1];
      A[index1] = A[index2];
      A[index2] = temp;
    }

}//end Sorts calss
