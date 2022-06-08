import java.util.*;

public class main {
    static Map<Character, Integer> integerMap = new HashMap<>();
    static Map<Character, List<String>> characterListMap = new HashMap<>();
    static Integer[][] precedenceMatrix;
    static List<String> S = new ArrayList<>();
    static Stack<Character> stack = new Stack<>();

    public main() {
        Scanner in = new Scanner(System.in);
        System.out.println(check(in.nextLine()));
    }

    private static Integer lookingForRelation(char l, char r) {
        return precedenceMatrix[integerMap.get(l)][integerMap.get(r)];
    }

    public static boolean check(String str) {
        //Создаем грамматику
        S.add("CS");
        S.add("CB");
        S.add("C");
        characterListMap.put('S', S);
        List<String> C = new ArrayList<>();
        C.add("A");
        characterListMap.put('C', C);
        List<String> A = new ArrayList<>();
        A.add("aA");
        A.add("bA");
        A.add("c");
        characterListMap.put('A', A);
        List<String> B = new ArrayList<>();
        B.add("dB");
        B.add("a");
        B.add("d");
        characterListMap.put('B', B);

        integerMap.put('S', 0);
        integerMap.put('C', 1);
        integerMap.put('B', 2);
        integerMap.put('A', 3);
        integerMap.put('a', 4);
        integerMap.put('b', 5);
        integerMap.put('c', 6);
        integerMap.put('d', 7);
        integerMap.put('#', 8);
        // Создаем матрицу предшествования
        precedenceMatrix = new Integer[][]{
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
        boolean status = false;
        stack.add('#');
        int pointer = 0;
        str += "#";
        char[] sentence = str.toCharArray();
        if (sentence.length == 1) {
            return false;
        }
        while (pointer <= sentence.length) {
            char symbol = sentence[pointer];
            if (integerMap.containsKey(symbol)) {
                status = false;
            } else {
                status = true;
                break;
            }
            Integer temp = lookingForRelation(stack.peek(), sentence[pointer]);
            if (temp == null) {
                status = true;
                break;
            }
            if (temp > 0) {
                StringBuilder stringBuilder = new StringBuilder();
                char character = stack.pop();
                while (lookingForRelation(stack.peek(), character) >= 0) {
                    character = stack.pop();
                    stringBuilder.insert(0, character);
                }
                Iterator<Map.Entry<Character, List<String>>> iterator = characterListMap.entrySet().iterator();
                int containce = 0;
                char left = 0;
                while (iterator.hasNext() && containce == 0) {
                    Map.Entry<Character, List<String>> listEntry = iterator.next();
                    if (listEntry.getValue().contains(stringBuilder.toString())) {
                        containce = 1;
                        left = listEntry.getKey();
                    }
                }
                if (containce == 1) {
                    status = true;
                    break;
                }
                stack.add(left);
            } else {
                pointer++;
                stack.push(symbol);
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

}
