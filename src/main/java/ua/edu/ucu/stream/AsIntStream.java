package ua.edu.ucu.stream;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ua.edu.ucu.function.*;


public class AsIntStream implements IntStream {

    private int[] vals;
    private AsIntStream(int[] vals) {
        this.vals = vals;
    }
    public static IntStream of(int... values) {
        AsIntStream stream = new AsIntStream(values);
        return stream;
    }

    @Override
    public Double average() {
        if (vals.length == 0) {
            throw new IllegalArgumentException("Stream is empty");
        }
        int sum = 0;
        for (int i = 0; i < vals.length; i++) {
            sum += vals[i];
        }
        return (double) sum / vals.length;
    }

    @Override
    public Integer max() {
        if (vals.length == 0) {
            throw new IllegalArgumentException("Stream is empty");
        }
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < vals.length;i++) {
            if (vals[i] > max) {
                max = vals[i];
            }
        }
        return max;
    }

    @Override
    public Integer min() {
        if (vals.length == 0) {
            throw new IllegalArgumentException("Stream is empty");
        }
        int min = Integer.MAX_VALUE;
        for (int i = 0; i<vals.length; i++) {
            if (vals[i] < min) {
                min = vals[i];
            }
        }
        return min;
    }

    @Override
    public long count() {
        return vals.length;
    }

    @Override
    public Integer sum() {
        int s = 0;
        for (int i = 0; i < vals.length; i++){
            s+=vals[i];
        }
        return s;
    }

    @Override
    public IntStream filter(IntPredicate predicate) {
        List<Integer> filteredList = new ArrayList<>();
        for (int i = 0; i < vals.length; i++) {
            if (predicate.test(vals[i])) {
                filteredList.add(vals[i]);
            }
        }
        int[] filteredArray = new int[filteredList.size()];
        for (int i = 0; i < filteredList.size(); i++) {
            filteredArray[i] = filteredList.get(i);
        }
    
        return new AsIntStream(filteredArray);
    }
    

    @Override
    public void forEach(IntConsumer action) {
        for (int i = 0; i < vals.length;i++) {
            action.accept(vals[i]);
        }
    }

    @Override
    public IntStream map(IntUnaryOperator mapper) {
        int[] mappedArray = new int[vals.length];
        for (int i = 0; i < vals.length; i++) {
            mappedArray[i] = mapper.apply(vals[i]);
        }
        return new AsIntStream(mappedArray);
    }

    @Override
    public IntStream flatMap(IntToIntStreamFunction func) {
        List<Integer> fmList = new ArrayList<>();
        for (int i = 0; i < vals.length; i++) {
            IntStream mappedStream = func.applyAsIntStream(vals[i]);
            mappedStream.forEach(fmList::add);
        }
        int[] fmArray = new int[fmList.size()];
        for (int i = 0; i < fmList.size(); i++) {
            fmArray[i] = fmList.get(i);
        }
        return new AsIntStream(fmArray);
    }

    @Override
    public int reduce(int identity, IntBinaryOperator operator) {
        int result = identity;
        for (int i = 0; i < vals.length;i++) {
            result = operator.apply(result, vals[i]);
        }
        return result;
    }

    @Override
    public int[] toArray() {
        return Arrays.copyOf(vals, vals.length);
    }

}
