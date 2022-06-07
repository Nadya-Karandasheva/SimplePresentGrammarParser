import java.util.*;

public class main {
    static Map<Character, List<String>> grammar;
    static Map<Character, Integer> map;
    static Integer[][] matrix;

    public main() {
        Scanner in = new Scanner(System.in);
        String line = in.nextLine();
        System.out.println(check(line));
    }

    public static boolean check(String str) {
        start();
        boolean status = false;
        Stack<Character> stack = new Stack<>();
        stack.add('#');

        int pointer = 0;
        str += "#";
        char[] sentence = str.toCharArray();


        if (sentence.length == 1) {
            return false;
        }
        while (pointer <= sentence.length) {
            char c = sentence[pointer];
            if (c == '#' && stack.size() == 2 && stack.peek() == 'S') {
                break;
            }
            if (!map.containsKey(c)) {
                status = true;
                break;
            }

            Integer temp = findRelation(stack.peek(), sentence[pointer]);
            if (temp == null) {
                status = true;
                break;
            }
            if (temp > 0) {
                StringBuilder sb = new StringBuilder();
                char last_l;
                do {
                    last_l = stack.pop();
                    sb.insert(0, last_l);
                } while (findRelation(stack.peek(), last_l) >= 0);
                Iterator<Map.Entry<Character, List<String>>> iterator = grammar.entrySet().iterator();
                boolean found = false;
                char left = 0;
                while (iterator.hasNext() && !found) {
                    Map.Entry<Character, List<String>> entry = iterator.next();
                    if (entry.getValue().contains(sb.toString())) {
                        found = true;
                        left = entry.getKey();
                    }
                }
                if (!found) {
                    status = true;
                    break;
                }
                stack.add(left);
            } else {
                stack.push(c);
                pointer++;
            }
        }
        StringBuilder builder = new StringBuilder();
        try {
            builder.insert(0, stack.pop());
            builder.insert(0, stack.pop());
        } catch (EmptyStackException e) {
            return false;
        }
        return !status && builder.toString().equals("#S");
    }


    static void start() {
        grammar = new HashMap<>();
        List<String> S = new ArrayList<>();
        S.add("CS");
        S.add("CB");
        S.add("C");
        grammar.put('S', S);
        List<String> C = new ArrayList<>();
        C.add("A");
        grammar.put('C', C);
        List<String> A = new ArrayList<>();
        A.add("aA");
        A.add("bA");
        A.add("c");
        grammar.put('A', A);
        List<String> B = new ArrayList<>();
        B.add("dB");
        B.add("a");
        B.add("d");
        grammar.put('B', B);

        map = new HashMap<>();
        map.put('S', 0);
        map.put('C', 1);
        map.put('B', 2);
        map.put('A', 3);
        map.put('a', 4);
        map.put('b', 5);
        map.put('c', 6);
        map.put('d', 7);
        map.put('#', 8);

        matrix = new Integer[][]{
                {null, null, null, null, null, null, null, null, 1},
                {0, -1, 0, -1, -1, -1, 1, -1, 1},
                {null, null, null, null, null, null, null, null, 1},
                {1, 1, 1, 1, 1, 1, 1, 1, 1},
                {null, null, null, 0, -1, -1, -1, null, 1},
                {null, null, null, 0, -1, -1, -1, null, 1},
                {1, 1, 1, 1, 1, 1, 1, 1, 1},
                {null, null, 0, null, -1, null, null, -1, 1},
                {-1, -1, -1, -1, -1, -1, -1, -1, null},
        };
    }

    private static Integer findRelation(char left, char right) {
        return matrix[map.get(left)][map.get(right)];
    }
}