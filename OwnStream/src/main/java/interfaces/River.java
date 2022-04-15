package interfaces;

import interfaces.service.RiverGenerator;

import java.util.Collection;
import java.util.Comparator;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collector;

public interface River<E> {

    //===============================创建操作=====================================

    @SafeVarargs
    static <T> River<T> of(T... t) {
        return RiverGenerator.create(t);
    }

    static <T> River<T> of(Collection<T> collection) {
        return RiverGenerator.create(collection);
    }

    //===============================中间操作=====================================

    /**
     * 将流转换为并行流
     *
     * @return River
     */
    River<E> parallel();

    /**
     * 将流转换为串行流
     *
     * @return River
     */
    River<E> sequential();

    /**
     * 过滤操作
     *
     * @param predicate 过滤的表达式
     * @return 过滤后的River
     */
    River<E> filter(Predicate<E> predicate);

    /**
     * 元素去重操作
     *
     * @return 去重后的River
     */
    River<E> distinct();

    /**
     * 限制River的元素数量
     *
     * @param size 元素的最大数量
     * @return River
     */
    River<E> limit(int size);

    /**
     * 排序
     *
     * @param comparable 比较器
     * @return 添加排序后的River
     */
    River<E> sort(Comparator<E> comparable);

    /**
     * 对元素进行预操作
     *
     * @param consumer 执行的操作
     * @return 新River
     */
    River<E> peek(Consumer<E> consumer);

    /**
     * 跳过指定数量的元素
     *
     * @param size 要跳过的元素数
     * @return new River
     */
    River<E> skip(int size);

    /**
     * 元素转换映射
     *
     * @param function 映射执行逻辑
     * @return new River
     */
    <E_OUT> River<E_OUT> map(Function<? super E, ? extends E_OUT> function);

    //===============================终结操作=====================================

    /**
     * 遍历River所有元素
     *
     * @param consumer 表达式
     */
    void forEach(Consumer<E> consumer);

    /**
     * 输出一个数组
     *
     * @return E类型的数组
     */
    Object[] toArray();

    /**
     * 输出元素到参数数组中
     *
     * @param e 承载元素的数组
     */
    void toArray(E[] e);

    /**
     * 计算元素的数量
     *
     * @return River中元素的数量
     */
    long count();

    /**
     * 操作
     *
     * @param identity    初始值
     * @param accumulator 操作函数
     * @return reduce result
     */
    E reduce(E identity, BinaryOperator<E> accumulator);

    /**
     * 执行操作
     *
     * @param collector 执行操作的收集器
     * @param <R>       结果类型
     * @param <A>       中间累加类型
     * @return 执行结果
     */
    <R, A> R collect(Collector<? super E, A, R> collector);

    /**
     * 获取比较后，最小的元素
     *
     * @param comparator 比较器
     * @return 最小的元素
     */
    Optional<E> min(Comparator<? super E> comparator);

    /**
     * 获取比较后，最大的元素
     *
     * @param comparator 比较器
     * @return 最大的元素
     */
    Optional<E> max(Comparator<? super E> comparator);

    /**
     * 判断匹配，任意即可
     *
     * @param predicate
     * @return true:match success
     */
    boolean anyMatch(Predicate<? super E> predicate);

    /**
     * 判断匹配，所有都要匹配
     *
     * @param predicate
     * @return
     */
    boolean allMatch(Predicate<? super E> predicate);

    /**
     * 判断匹配，都不要匹配上
     *
     * @param predicate
     * @return
     */
    boolean noneMatch(Predicate<? super E> predicate);

    /**
     * 获取第一个元素
     *
     * @return
     */
    Optional<E> findFirst();

}
