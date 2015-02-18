package com.avalonconsult;

import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.StormSubmitter;
import backtype.storm.generated.AlreadyAliveException;
import backtype.storm.generated.AuthorizationException;
import backtype.storm.generated.InvalidTopologyException;
import backtype.storm.generated.StormTopology;
import backtype.storm.spout.SchemeAsMultiScheme;
import backtype.storm.tuple.Fields;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import storm.kafka.BrokerHosts;
import storm.kafka.StringScheme;
import storm.kafka.ZkHosts;
import storm.kafka.trident.OpaqueTridentKafkaSpout;
import storm.kafka.trident.TridentKafkaConfig;
import storm.trident.Stream;
import storm.trident.TridentTopology;
import storm.trident.state.StateFactory;

/**
 * Created by adam on 2/17/15.
 */
public class TwitterGraphTopology {

    private static final Logger LOG = LoggerFactory.getLogger(TwitterGraphTopology.class);

    public static StormTopology buildTopology(String[] args) {
        TridentTopology topology = new TridentTopology();

        BrokerHosts zkHosts = new ZkHosts(args[0]);
        TridentKafkaConfig config = new TridentKafkaConfig(zkHosts, "tweets");
        config.scheme = new SchemeAsMultiScheme(new StringScheme());

        OpaqueTridentKafkaSpout spout = new OpaqueTridentKafkaSpout(config);

        Stream spoutStream = topology.newStream("kafka-stream", spout);

        Fields jsonFields = new Fields("timestamp", "message");
        Stream parsedStream = spoutStream.each(spoutStream.getOutputFields(), new JsonProjectFunction(jsonFields), jsonFields);
        parsedStream = parsedStream.project(jsonFields);

        StateFactory stateFactory = new GraphStateFactory(new TitanGraphFactory());
        GraphUpdater graphUpdater = new GraphUpdater(new TweetGraphTupleProcessor());

        parsedStream.partitionPersist(stateFactory, parsedStream.getOutputFields(), graphUpdater, new Fields());

        return topology.build();
    }

    public static void main(String[] args) throws InvalidTopologyException, AuthorizationException, AlreadyAliveException {

        LOG.debug("This is a debug message");

        Config config = new Config();
        config.put(TitanGraphFactory.STORAGE_BACKEND, "cassandrathrift");
        config.put(TitanGraphFactory.STORAGE_HOSTNAME, "192.168.47.104");

        config.setMaxSpoutPending(1000);

        config.setNumWorkers(4);

        if (args.length == 0) {
            LOG.info("Launching topology in local mode.  If you are running with 'storm jar', add zk hosts as arg");

            LocalCluster cluster = new LocalCluster();
            cluster.submitTopology("real-time-graph-analysis", config, buildTopology(args));
            try {
                Thread.sleep(30000);
            } catch (InterruptedException e) {
                //
            }
            cluster.killTopology("real-time-graph-analysis");
            cluster.shutdown();
        } else {
            LOG.info("Launching topology in cluster mode.");
            StormSubmitter.submitTopology("real-time-graph-analysis", config, buildTopology(args));
        }

    }
}
