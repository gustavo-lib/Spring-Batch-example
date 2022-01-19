package com.gortiz.batch.springbatch.step;

import com.gortiz.batch.springbatch.model.StockInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

public class StockInfoProcessor  implements ItemProcessor<StockInfo, String> {

    private static final Logger LOGGER = LoggerFactory.getLogger(StockInfoProcessor.class);

    @Override
    public String process(StockInfo stockInfo) throws Exception {
        System.out.println("Hello");
        String message = stockInfo.getStockName() + " is trading at  "
                + stockInfo.getStockPrice() + " on " + stockInfo.getMarket()+" at "+ new Date().toString()+ "!";
        LOGGER.info("printing '{}' to output file", message);
        return message;
    }
}
