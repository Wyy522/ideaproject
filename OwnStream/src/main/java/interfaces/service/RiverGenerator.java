package interfaces.service;

import interfaces.River;

import java.util.Arrays;
import java.util.Spliterator;

public class RiverGenerator {

    public static <E> River<E> create(E... e) {
        Spliterator<E> spliterator = Arrays.spliterator(e);
        PipelineStage<E, E> head = new PipelineStage<E, E>(spliterator) {
            @Override
            public SinkChain<E, E> wrapSink(SinkChain<E, ?> sink) {
                SinkChain<E, E> sinkChain = new SinkChain<E, E>() {
                    @Override
                    public void accept(E t) {
                        next.accept(t);
                    }
                };
                sinkChain.setNext(sink);
                return sinkChain;
            }
        };
        head.setSourceSpliterator(spliterator);
        return head;
    }


    @Override
    public void forEach(Consumer<O> consumer) {
        PipelineStage<O, O> stage = new PipelineStage<O, O>(this) {
            @Override
            public SinkChain<O, O> wrapSink(SinkChain<O, ?> sink) {
                return new SinkChain<O, O>() {
                    @Override
                    public void accept(O t) {
                        consumer.accept(t);
                    }
                };
            }
        };
        // 启动流的开关
        evaluate(stage);
    }


    private void evaluate(PipelineStage<?, O> stage) {
        //构建处理链
        SinkChain<O, O> sinkHead = warpPipeline(stage);

        //开始处理
        sinkHead.begin(-1);
        //遍历元素
        sinkHead.getSourceSpliterator().forEachRemaining(sinkHead);
        sinkHead.end();
    }


    private SinkChain<O, O> warpPipeline(AbstractRiverPipeline river) {
        SinkChain<O, O> sink = null;
        for (; river != null; river = river.previous) {
            sink = river.wrapSink(sink);
        }
        return sink;
    }

    @Override
    public River<O> filter(Predicate<O> predicate) {
        return new PipelineStage<O, O>(this) {
            @Override
            public SinkChain<O, O> wrapSink(SinkChain<O, ?> sink) {
                SinkChain<O, O> sinkChain = new SinkChain<O, O>() {
                    @Override
                    public void accept(O t) {
                        // 如果不符合要求，不再向后传递该元素，该元素的处理到此为止
                        if (!predicate.test(t)) {
                            return;
                        }
                        //如何要求则调用下一个sink的accept()方法
                        getNext().accept(t);
                    }
                };
                sinkChain.setNext(sink);
                return sinkChain;
            }
        };
    }

    @Override
    public River<O> distinct() {
        return new PipelineStage<O, O>(this) {
            @Override
            public SinkChain<O, O> wrapSink(SinkChain<O, ?> sink) {
                SinkChain<O, O> sinkChain = new SinkChain<O, O>() {
                    private HashSet<O> set;

                    @Override
                    public void begin(int n) {
                        //执行初始化工作
                        this.set = new HashSet<>(n > 0 ? n : 16);
                        //调用下一个sink的begin()方法
                        super.begin(n);
                    }

                    @Override
                    public void end() {
                        this.set = null;
                        super.end();
                    }

                    @Override
                    public void accept(O t) {
                        if (!set.add(t)) {
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
