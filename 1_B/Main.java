
import java.util.*;

public class Main {
    
/*    
General idea:

OPT(1) = 1
OPT(j) = max{ OPT(p(j)) + 1, OPT(j-1) }

where p(j) returns the index of the closest smaller number than the current number (to the left)

To retrieve the sub-sequence length AND indices, we can store indices in opt instead of 
the local maximum lengths. When we backtrack, we can get the indices again.

alternative: store the indices


*/
    private record Tuple(int prevIndex, boolean indexIsInSubSequence, int maximumLength) {}
    

    public static void main(String[] args) {
	longestSubSequence(new int[] {5, 19, 5, 81, 50, 28, 29, 1, 83, 23});
    }

    public static void kattis() {
        Kattio io = new Kattio(System.in, System.out);
        
        while (io.hasMoreTokens()) {
            final int[] sequence = new int[io.getInt()];
            final Tuple[] opt    = new Tuple[sequence.length];
            
            
            //OPT(0) = 0
            opt[0] = new Tuple(0, true, 1);
            sequence[0] = io.getInt();

            for(int i = 1; i < sequence.length; i++) {
                sequence[i] = io.getInt();

		int j = findClosestSmallerNumber(sequence, i);

                int optChoice1 = opt[i-1].maximumLength;
                int optChoice2 = 1;
                                
                if (j != -1) {
		    optChoice2 += opt[j].maximumLength;
		}
                
                //OPT(j) = max{ OPT(j-1), OPT(p(j)) + 1 }
                if (optChoice1 > optChoice2) {
                    opt[i] = new Tuple(i-1, false, optChoice1);
                } else {
                    opt[i] = new Tuple(j, true, optChoice2);
                }
                
                
            }
            
            //retrieve indices
            List<Integer> indices = new ArrayList<>();
            
            
            int i = sequence.length-1;
            while (i > 0) {
                if (opt[i].indexIsInSubSequence) {
                    indices.add(i);
                }

                i = opt[i].prevIndex;
            }
            
            
            System.out.println(opt[sequence.length-1].maximumLength);
            for(int j = indices.size()-1; j >= 0; j--) {
                System.out.print(indices.get(j) + " ");
            }
        }
        
        io.close();
    }
    
    public static void longestSubSequence(final int[] sequence) {
	final Tuple[] opt    = new Tuple[sequence.length];
            
            
            //OPT(0) = 0
            opt[0] = new Tuple(0, true, 1);

            for(int i = 1; i < sequence.length; i++) {

		int j = findClosestSmallerNumber(sequence, i);

                int optChoice1 = opt[i-1].maximumLength;
                int optChoice2 = 1;
                                
                if (j != -1) {
		    optChoice2 += opt[j].maximumLength;
		}
                
                //OPT(j) = max{ OPT(j-1), OPT(p(j)) + 1 }
                if (optChoice1 > optChoice2) {
                    opt[i] = new Tuple(i-1, false, optChoice1);
                } else {
                    opt[i] = new Tuple(j, true, optChoice2);
                }
                
                
            }
            
	System.out.println(Arrays.toString(sequence));
	System.out.println(Arrays.toString(opt));

            //retrieve indices
            List<Integer> indices = new ArrayList<>();
            
            
            int i = sequence.length-1;
            while (i >= 0) {
                if (opt[i].indexIsInSubSequence) {
                    indices.add(i);
                }


		if (i == opt[i].prevIndex) break;
                i = opt[i].prevIndex;

            }
            
            
            System.out.println(opt[sequence.length-1].maximumLength);
            for(int j = indices.size()-1; j >= 0; j--) {
                System.out.print(indices.get(j) + " ");
            }
    }

    public static int findClosestSmallerNumber(int[] arr, int j) {
        final int value = arr[j];
        j--;

        while (j >= 0) {
            if (arr[j] < value) return j;
            j--;
        }
        
        //could not find a smaller element
        return -1;
    }
}
