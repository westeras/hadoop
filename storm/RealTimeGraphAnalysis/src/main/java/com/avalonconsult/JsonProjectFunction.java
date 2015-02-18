package com.avalonconsult;

import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import storm.trident.operation.BaseFunction;
import storm.trident.operation.TridentCollector;
import storm.trident.tuple.TridentTuple;

/**
 * Created by adam on 2/17/15.
 */
public class JsonProjectFunction extends BaseFunction {

    private static final Logger LOG = LoggerFactory.getLogger(JsonProjectFunction.class);

    private Fields fields;

    public JsonProjectFunction(Fields fields) {
        this.fields = fields;
    }

    @Override
    public void execute(TridentTuple tridentTuple, TridentCollector tridentCollector) {
        String json = tridentTuple.getString(0);
        JSONObject array = (JSONObject) JSONValue.parse(json);
        LOG.info(json);
        Values values = new Values();

        for (int i = 0; i < this.fields.size(); i++) {
            values.add(array.get(this.fields.get(i)));
        }
        tridentCollector.emit(values);
    }
}
