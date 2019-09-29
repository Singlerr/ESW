package io.github.alpen.utils;

import java.util.List;
import java.util.Vector;

public class ElementTransformulator {
    public static <E1,E2> Vector<E2> transform(Vector<E1> target,Transformulator<E1,E2> f){
        Vector<E2> rt = new Vector<>();
        target.stream().forEach(x-> rt.add(f.get(x)));
        return rt;
    }
    @FunctionalInterface
    public interface Transformulator<T1,T2>{
        T2 get(T1 x);

    }
}
