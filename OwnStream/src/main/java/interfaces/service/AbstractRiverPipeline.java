package interfaces.service;

import interfaces.River;

import java.util.Spliterator;
import java.util.function.Predicate;

public class AbstractRiverPipeline<I, O> extends Pipeline<I, O> implements River<O> {
    //存储元素的spliterator对象引用
    protected Spliterator sourceSpliterator;
    /**
     * 追加filter操作
     * 创建一个filter的{@link PipelineStage},然后将该stage追到到Pipeline的尾部
     *
     * @param predicate 过滤的表达式
     * @return 新增的filter对象
     */
    @Override
    public River<O> filter(Predicate<O> predicate) {
        return new PipelineStage<O, O>(this) {
            @Override
            public SinkChain<O, O> wrapSink(SinkChain<O, ?> sink) {
                SinkChain<O, O> sinkChain = new SinkChain<O, O>() {
                    @Override
                    public void accept(O t) {
                        if (!predicate.test(t)) {
                            return;
                        }
                        getNext().accept(t);
                    }
                };
                sinkChain.setNext(sink);
                return sinkChain;
            }
        };
    }
}