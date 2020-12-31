package test;

import org.junit.Ignore;
import org.junit.Test;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.Comparator.comparing;

/**
 * @author pjli
 * @create 2020-06-18 11:06
 */
@Ignore
public class StreamTest {


    @Test
    public void foreachTest() {
        Random random = new Random();
        random.ints().limit(10).forEach(System.out::println);
    }

    @Test
    public void limitTest() {
        Random random = new Random();
        List<Integer> list = new ArrayList<>();
        random.ints().limit(10).forEach(list::add);
        System.out.println(list);
    }

    @Test
    public void sortedTest() {
        Random random = new Random();
        random.ints().limit(10).sorted().forEach(System.out::println);
    }

    @Test
    public void collectorsTest() {
        List<String> strings = Arrays.asList("abc", "", "bc", "efg", "abcd", "", "jkl");
        List<String> filtered = strings.stream().filter(string -> !string.isEmpty()).collect(Collectors.toList());

        System.out.println("筛选列表: " + filtered);
        String mergedString = strings.stream().filter(string -> !string.isEmpty()).collect(Collectors.joining(", "));
        System.out.println("合并字符串: " + mergedString);
    }

    @Test
    public void statisticsTest() {
        List<Integer> numbers = Arrays.asList(3, 2, 2, 3, 7, 3, 5);

        IntSummaryStatistics stats = numbers.stream().mapToInt((x) -> x).summaryStatistics();

        System.out.println("列表中最大的数 : " + stats.getMax());
        System.out.println("列表中最小的数 : " + stats.getMin());
        System.out.println("所有数之和 : " + stats.getSum());
        System.out.println("平均数 : " + stats.getAverage());
    }

    private List<User> listDataInit() {
        List<User> userList = new ArrayList() {
            {
                add(new User(1L, "唐三", 16, true));
                add(new User(2L, "小舞", 16, false));
                add(new User(1L, "唐银", 16, true));
                add(new User(3L, "戴沐白", 15, true));
                add(new User(4L, "朱竹清", 15, false));
                add(new User(8L, "小舞", 16, false));
                add(new User(5L, "奥斯卡", 15, true));
                add(new User(6L, "宁荣荣", 14, false));
                add(new User(7L, "马洪俊", 13, true));
            }
        };
        return userList;
    }

    private Map<Long, User> mapDataInit() {
        Map<Long, User> userMap = new HashMap() {
            {
                put(1L, new User(1L, "唐三", 16, true));
                put(1L, new User(1L, "唐银", 16, true));
                put(2L, new User(2L, "小舞", 16, false));
                put(8L, new User(8L, "小舞", 16, false));
                put(3L, new User(3L, "戴沐白", 15, true));
                put(4L, new User(4L, "朱竹清", 15, false));
                put(5L, new User(5L, "奥斯卡", 15, true));
                put(6L, new User(6L, "宁荣荣", 14, false));
                put(7L, new User(7L, "马洪俊", 13, true));
            }
        };
        return userMap;
    }

    @Test
    // 构造stream
    public void streamTest() {
        // 1. 多个独立元素
        Stream stream = Stream.of("a", "b", "c");
        // 2. 数组，数值类型推荐优先选用IntStream、LongStream、DoubleStream
        int[] intArray = new int[]{8, 9, 10};
        IntStream istream = IntStream.of(intArray);
        istream = Arrays.stream(intArray);
        IntStream.rangeClosed(8, 10).forEach(System.out::println);
        // 3. 集合
        List<String> strings = Arrays.asList("abc", "", "bc", "efg", "abcd", "", "jkl");
        List<String> ret = strings.stream().filter(string -> string.startsWith("a")).collect(Collectors.toList());
        System.out.println(ret);
    }

    @Test
    // 去重
    public void distinctTest() {
        List<User> userList = listDataInit();
        // 根据equals()方法去重
        List<User> newUserList = userList.stream().distinct().collect(Collectors.toList());
        newUserList.forEach(System.out::println);
        // 根据某一属性去重
        userList.stream().filter(distinctByKey(user -> user.getName())).forEach(System.out::println);
    }

    static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        Map<Object, Boolean> seen = new ConcurrentHashMap<>();
        return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }

    @Test
    // 过滤
    public void filterTest() {
        List<User> userList = listDataInit();
        // 根据equals()方法去重
        List<User> newUserList = userList.stream().filter(user -> user.isGender() == true).filter(user -> user.getAge().equals(15)).collect(Collectors.toList());
        newUserList.forEach(System.out::println);
    }

    @Test
    // 排序
    public void sortTest() {
        List<User> userList = listDataInit();
        // 根据年龄排序
        List<User> newUserList = userList.stream().filter(user -> user.getAge() != null).sorted(comparing(User::getAge).reversed()).collect(Collectors.toList());
        newUserList.forEach(System.out::println);
    }

    @Test
    // 遍历
    public void forEachTest() {
        // 遍历集合
        List<User> userList = listDataInit();
        userList.forEach(user -> {
            if (user.getId().equals("1")) {
                user.setName("唐三");
            }
        });
        System.out.println(userList);
        // 遍历Map
        Map<Long, User> userMap = mapDataInit();
        userMap.forEach((k, v) -> System.out.println(k + ":" + v.getName()));
    }

    @Test
    // 元素处理
    public void mapTest() {
        // 字符串转大写
        List<String> strings = Arrays.asList("aaa", "BBB", "ccc");
        List<String> ret1 = strings.stream().map(String::toUpperCase).collect(Collectors.toList());
        ret1.forEach(System.out::println);

        // 获取数字对应的平方数,并过滤重复值
        List<Integer> numbers = Arrays.asList(3, 2, 2, 3, 7, 3, 5);
        List<Integer> ret2 = numbers.stream().map(i -> i * i).distinct().collect(Collectors.toList());
        ret2.forEach(System.out::println);

        // 将Object对象列表中的某一属性转成List
        List<User> userList = listDataInit();
        List<Long> userIdList = userList.stream().map(user -> user.getId()).distinct().collect(Collectors.toList());
        userIdList.forEach(System.out::println);

        // 利用Object对象列表中的元素重新生成新对象列表
        List<User> newUserList = userList.stream().map(user -> {
            User newUser = new User();
            newUser.setName(user.getName());
            newUser.setAge(user.getAge());
            return newUser;
        }).collect(Collectors.toList());
        newUserList.forEach(System.out::println);
    }

    @Test
    // flatMap
    public void flatMapTest() {
        Stream<List<Integer>> inputStream = Stream.of(
                Arrays.asList(1),
                Arrays.asList(2, 3),
                Arrays.asList(4, 5, 6)
        );
        Stream<Integer> outputStream = inputStream.
                flatMap((childList) -> childList.stream());
        outputStream.forEach(System.out::println);
    }

    @Test
    // 并行测试
    public void parallelTest() {
        List<String> strings = Arrays.asList("abc", "", "bc", "efg", "abcd", "", "jkl", "abc", "", "bc", "efg", "abcd", "", "jkl", "abc", "", "bc", "efg", "abcd", "", "jkl", "abc", "", "bc", "efg", "abcd", "", "jkl", "abc", "", "bc", "efg", "abcd", "", "jkl", "abc", "", "bc", "efg", "abcd", "", "jkl","abc", "", "bc", "efg", "abcd", "", "jkl", "abc", "", "bc", "efg", "abcd", "", "jkl", "abc", "", "bc", "efg", "abcd", "", "jkl", "abc", "", "bc", "efg", "abcd", "", "jkl", "abc", "", "bc", "efg", "abcd", "", "jkl", "abc", "", "bc", "efg", "abcd", "", "jkl","abc", "", "bc", "efg", "abcd", "", "jkl", "abc", "", "bc", "efg", "abcd", "", "jkl", "abc", "", "bc", "efg", "abcd", "", "jkl", "abc", "", "bc", "efg", "abcd", "", "jkl", "abc", "", "bc", "efg", "abcd", "", "jkl", "abc", "", "bc", "efg", "abcd", "", "jkl","abc", "", "bc", "efg", "abcd", "", "jkl", "abc", "", "bc", "efg", "abcd", "", "jkl", "abc", "", "bc", "efg", "abcd", "", "jkl", "abc", "", "bc", "efg", "abcd", "", "jkl", "abc", "", "bc", "efg", "abcd", "", "jkl", "abc", "", "bc", "efg", "abcd", "", "jkl","abc", "", "bc", "efg", "abcd", "", "jkl", "abc", "", "bc", "efg", "abcd", "", "jkl", "abc", "", "bc", "efg", "abcd", "", "jkl", "abc", "", "bc", "efg", "abcd", "", "jkl", "abc", "", "bc", "efg", "abcd", "", "jkl", "abc", "", "bc", "efg", "abcd", "", "jkl","abc", "", "bc", "efg", "abcd", "", "jkl", "abc", "", "bc", "efg", "abcd", "", "jkl", "abc", "", "bc", "efg", "abcd", "", "jkl", "abc", "", "bc", "efg", "abcd", "", "jkl", "abc", "", "bc", "efg", "abcd", "", "jkl", "abc", "", "bc", "efg", "abcd", "", "jkl");
        // 获取空字符串的数量
        long start = System.currentTimeMillis();
        long count = strings.stream().filter(string -> string.isEmpty()).count();
        System.out.println(System.currentTimeMillis() - start);
    }

    @Test
    // 统计计算
    public void statisticTest() {
        List<User> userList = listDataInit();
        // 根据年龄筛选，求出年龄总和
        int sum1 = userList.stream().filter(user -> user.getAge() > 14).mapToInt(User::getAge).sum();
        System.out.println(sum1);
        Integer sum2 = userList.stream().filter(user -> user.getAge() > 14).map(User::getAge).reduce(0, (a, b) -> a + b);
        System.out.println(sum2);
        Integer sum3 = userList.stream().filter(user -> user.getAge() > 14).map(User::getAge).reduce(0, Integer::sum);
        System.out.println(sum3);

        // 字符串连接，concat = "ABCD"
        String concat = Stream.of("A", "B", "C", "D").reduce("", String::concat);
        // 求最小值，minValue = -3.0
        double minValue = Stream.of(-1.5, 1.0, -3.0, -2.0).reduce(Double.MAX_VALUE, Double::min);
        // 求和，sumValue = 10, 有起始值
        int sumValue = Stream.of(1, 2, 3, 4).reduce(0, Integer::sum);
        // 求和，sumValue = 10, 无起始值
        sumValue = Stream.of(1, 2, 3, 4).reduce(Integer::sum).get();
        // 过滤，字符串连接，concat = "ace"
        concat = Stream.of("a", "B", "c", "D", "e", "F").
                filter(x -> x.compareTo("Z") > 0).
                reduce("", String::concat);
    }

    @Test
    // 转换为其他类型
    public void transformTest() {
        List<String> strings = Arrays.asList("aaa", "BBB", "ccc");
        // 1. Array
        String[] strArray1 = strings.stream().toArray(String[]::new);
        // 2. Collection
        List<String> list1 = strings.stream().collect(Collectors.toList());
        List<String> list2 = strings.stream().collect(Collectors.toCollection(ArrayList::new));
        Set set1 = strings.stream().collect(Collectors.toSet());
        Stack stack1 = strings.stream().collect(Collectors.toCollection(Stack::new));
        // 3. String
        String str = strings.stream().collect(Collectors.joining()).toString();
    }

    @Test
    public void sense1() {
        List<User> userList = new ArrayList() {
            {
                add(new User(1L, "唐三", 16, true));
                add(new User(2L, "小舞", 16, false));
                add(new User(3L, "戴沐白", 15, true));
                add(new User(4L, "朱竹清", 15, false));
                add(new User(5L, "奥斯卡", 15, true));
                add(new User(6L, "宁荣荣", 14, false));
                add(new User(7L, "马洪俊", 13, true));
            }
        };

        // 场景1. 将Collection转换为Map
        HashMap<Long, User> userMap1 = userList.stream().collect(HashMap::new, (m, v) -> m.put(v.getId(), v), HashMap::putAll);
        System.out.println(userMap1);

        // 场景2. 根据Collection中元素的某一字段进行分组
        Map<Boolean, List<User>> userMap2 = userList.stream()
                .collect(
                        Collectors.groupingBy(User::isGender)
                );
        System.out.println(userMap2);

        // 场景3. 根据Collection中元素的某一字段进行分组，并统计个数
        Map<Boolean, Long> userMap3 = userList.stream()
                .collect(
                        Collectors.groupingBy(User::isGender, Collectors.counting())
                );
        System.out.println(userMap3);

        // 场景4. 根据Collection中元素的某一字段进行分组，并对某一字段进行求和运算
        Map<Boolean, Integer> userMap4 = userList.stream()
                .collect(
                        Collectors.groupingBy(User::isGender, Collectors.summingInt(User::getAge))
                );
        System.out.println(userMap4);

        // 场景5. 根据Collection中元素的某一字段进行分组，并对每一组中的数据进行判断
        Map<Boolean, Boolean> userMap5 = null;
        Set<Boolean> booleans = userList.stream()
                .collect(
                        Collectors.groupingBy(User::isGender, Collectors.collectingAndThen(Collectors.toSet(), list -> list.stream().anyMatch(ol -> ol.getAge().equals(15))))
                ).entrySet()
                .stream()
                .filter(m -> m.getKey().equals(false)).collect(Collectors.toMap(m -> m.getKey(), m -> m.getValue()))
                .keySet();
        System.out.println(booleans);
    }

    @Test
    public void sense2() {
        List<String> strArr = Arrays.asList("21", "22", "3", "4");

        strArr.stream().filter(str ->
                str.startsWith("2")
        ).filter(str -> {
            return str.equals("22");
        }).forEach(str -> {
            System.out.println(str);
        });

        List<String> result1 = strArr.stream()  // convert list to stream
                .filter(line -> !"mkyong".equals(line)) // filter the line which equals to "mkyong"
                .collect(Collectors.toList());  // collect the output and convert streams to a list

        result1.forEach(System.out::println); // o

        List<String> list = Arrays.asList("AA", "BB", "CC", "BB", "CC", "AA", "AA");
        long l = list.stream().distinct().count();
        System.out.println("No. of distinct elements:" + l);
        String output = list.stream().distinct().collect(Collectors.joining(","));
        System.out.println(output);
    }
}