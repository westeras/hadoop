package com.avalonconsult;

import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.TransactionalGraph;
import storm.trident.operation.TridentCollector;
import storm.trident.state.State;
import storm.trident.tuple.TridentTuple;

import java.util.List;

/**
 * Created by adam on 2/17/15.
 */
public class GraphState implements State {

    private Graph graph;

    public GraphState(Graph graph) {
        this.graph = graph;
    }

    @Override
    public void beginCommit(Long txid) {}

    @Override
    public void commit(Long txid) {
        if (this.graph instanceof TransactionalGraph) {
            ((TransactionalGraph)this.graph).commit();
        }
    }

    public void update(List<TridentTuple> tuples, TridentCollector collector, GraphTupleProcessor processor) {
        for (TridentTuple tuple: tuples) {
            processor.process(this.graph, tuple, collector);
        }
    }
}
