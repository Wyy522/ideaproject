package interfaces.service;


import java.util.Spliterator;

public class PipelineStage<I, O> extends AbstractRiverPipeline<I, O> {

    public PipelineStage(Spliterator<I> spliterator) {
        this.setSourceSpliterator(spliterator);
        this.isParallel = false;
    }

    /**
     * @param river 上一个stage
     */
    public PipelineStage(AbstractRiverPipeline<?, I> river) {
        previous = river;
        isParallel = river.isParallel;
        sourceSpliterator = river.sourceSpliterator;
    }

    public Object[] getArray() {
        throw new UnsupportedOperationException("to override");
    }

    public Object getState() {
        throw new UnsupportedOperationException("to override");
    }

    public void setState(O s) {
    }

    @Override
    public PipelineStage<I, O> clone() {
        throw new UnsupportedOperationException("to override");
    }
}