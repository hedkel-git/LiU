import java.util.*;

public class Main {
    /*
    General idea:

    S = {s_1, s_2, ..., s_n}

    OPT(1) = 1
    OPT(j) = max{
                OPT(j-1),
                max{ OPT(i) | i <- [(j-1)..1], s_i < s_j AND s_i is in subsequence} + 1
             }

    To retrieve the indices of the sub-sequence, we can store indices in opt as well.
    */
    private record Tuple(int prevIndex, boolean indexIsInSubSequence, int maximumLength) {}

    public static void main(String[] args) {
        kattis();
    }

    public static void kattis() {
        Kattio io = new Kattio(System.in, System.out);

        while (io.hasMoreTokens()) {
            final int[] sequence = new int[io.getInt()];
            final Tuple[] opt    = new Tuple[sequence.length];
            
            //OPT(1) = 1
            opt[0] = new Tuple(-1, true, 1);
            sequence[0] = io.getInt();

            for(int i = 1; i < sequence.length; i++) {
                sequence[i] = io.getInt();

                int optChoice1 = opt[i-1].maximumLength;
                int optChoice2 = 1;

                int j = i-1;
                int currMaxIndex = -1;

                //search for the closest number that is part of 'a' subsequence
                while(j >= 0) {
                    if (opt[j].indexIsInSubSequence && sequence[j] < sequence[i]) {
                        currMaxIndex = j;
                        j--;
                        break;
                    }
                    j--;
                }
                
                //continue search to find maximal subsequence
                while (j >= 0) {
                    if (opt[j].indexIsInSubSequence && sequence[j] < sequence[i]) {
                        if (opt[currMaxIndex].maximumLength < opt[j].maximumLength) {
                            currMaxIndex = j;
                        }
                    }
                    j--;
                }
                
                if (currMaxIndex != -1) {
                    optChoice2 += opt[currMaxIndex].maximumLength;
                }
                
                //OPT(j) = max{ OPT(j-1), max{...} + 1 }
                if (optChoice1 > optChoice2) {
                    opt[i] = new Tuple(i-1, false, optChoice1);
                } else {
                    opt[i] = new Tuple(currMaxIndex, true, optChoice2);
                }
            }

            /*
            System.out.println(Arrays.toString(sequence));
            for (int x = 0; x < sequence.length; x++) {
                System.out.println(opt[x] + "\t" + sequence[x] + "\t\t" + x);
            }
            System.out.println();
             */

            //retrieve indices
            List<Integer> indices = new ArrayList<>();

            int i = sequence.length-1;
            while (i >= 0) {
                if (opt[i].indexIsInSubSequence) {
                    indices.add(i);
                }
                i = opt[i].prevIndex;
            }

            System.out.println(opt[sequence.length-1].maximumLength);
            for(int j = indices.size()-1; j >= 0; j--) {
                System.out.print(indices.get(j) + " ");
            }
            System.out.print('\n');
        }

        io.close();
    }
}
