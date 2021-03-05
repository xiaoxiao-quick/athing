package com.github.ompc.athing.aliyun.thing.util;

import java.util.*;

/**
 * 依赖关系
 * 测试代码见{@link com.github.ompc.athing.aliyun.qatest.util.DependentSetTestCase}
 * @param <E> 元素
 */
public class DependentSet<E> extends HashSet<E> {

    // 递归的table
    // key   value
    // A   Set {A1, A2, A3}
    // A1   Set{A11, A12, A13}
    private final Map<E, Set<E>> table = new HashMap<>();

    @Override
    public Iterator<E> iterator() {
        final List<E> list = new ArrayList<>();
        super.iterator().forEachRemaining(list::add);
        list.sort((e1, e2) ->
                !e1.equals(e2)
                        ? getDepends(e1).contains(e2) ? 1 : -1
                        : 0);
        return list.iterator();
    }

    /**
     * 获取元素依赖集合
     *
     * @param element 元素
     * @return 依赖家族集合
     */
    public Set<E> getDepends(E element) {
        return recGetDepends(element, new HashSet<>());
    }

    /**
     * 递归获取元素依赖集合
     *
     * @param element 元素
     * @param depends 依赖家族集合
     * @example
     * @return 依赖家族集合
     */
    private Set<E> recGetDepends(E element, Set<E> depends) {
        depends.add(element);
        if (table.containsKey(element)) {
            table.get(element).forEach(depend -> recGetDepends(depend, depends));
        }
        return depends;
    }

    /**
     * 声明依赖关系
     *
     * @param element 元素
     * @param depends 依赖元素集合
     */
    public void depends(E element, E[] depends) {

        // 循环依赖检测，检查depends中的元素是否已经依赖了element
        for (E depend : depends) {
            if (getDepends(depend).contains(element)) {
                throw new DependentCircularException(String.format("%s is circular dependence: %s",
                        element, depend
                ));
            }
        }

        // 添加到当前集合
        add(element);
        addAll(Arrays.asList(depends));

        // 添加到依赖表中
        if (table.containsKey(element)) {
            table.get(element).addAll(Arrays.asList(depends));
        } else {
            table.put(element, new HashSet<>(Arrays.asList(depends)));
        }

    }

    /**
     * 循环依赖异常
     */
    public static class DependentCircularException extends RuntimeException {

        DependentCircularException(String message) {
            super(message);
        }

    }


}
