package com.gortiz.batch.springbatch.config;


import ch.qos.logback.core.net.SyslogOutputStream;
import com.gortiz.batch.springbatch.listener.SpringBatchJobCompletionListener;
import com.gortiz.batch.springbatch.listener.SpringBatchJobExecutionListener;
import com.gortiz.batch.springbatch.listener.SpringBatchStepListener;
import com.gortiz.batch.springbatch.model.StockInfo;
import com.gortiz.batch.springbatch.step.StockInfoProcessor;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.flow.JobExecutionDecider;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder;
import org.springframework.batch.item.file.transform.PassThroughLineAggregator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;

import org.springframework.batch.repeat.RepeatStatus;

@Configuration
@EnableBatchProcessing
public class SpringBatchConfig {

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Autowired
    private StepBuilderFactory steps;

    @Autowired
    private StepTwoDecider stepTwoDecider;


    @Bean
    public Job processJob() {
        return jobBuilderFactory.get("stockpricesinfojob")
                .incrementer(new RunIdIncrementer())
                .listener(new SpringBatchJobExecutionListener())
                .start(StockPricesInfoStep())
                .next(step2())
                .next(conditionalStep1()).on(ExitStatus.FAILED.getExitCode()).to(conditionalStep3())
                .from(conditionalStep1()).on("HOGE").to(conditionalStep4())
                .from(stepTwoDecider).on(ExitStatus.FAILED).to(step2())
                .end()
                .build();
    }

    @Bean
    public Step StockPricesInfoStep() {
        return stepBuilderFactory.get("step1")
                .listener(new SpringBatchStepListener())
                .<StockInfo, String>chunk(10)
                .reader(reader())
                .processor(stockInfoProcessor())
                .writer(writer())
                .faultTolerant()
                .retryLimit(3)
                .retry(Exception.class)
                .build();
    }

    @Bean
    public FlatFileItemReader<StockInfo> reader() {
        return new FlatFileItemReaderBuilder<StockInfo>()
                .name("stockInfoItemReader")
                .resource(new ClassPathResource("csv/stockinfo.csv"))
                .delimited()
                .names(new String[] {"stockId", "stockName","stockPrice","yearlyHigh","yearlyLow","address","sector","market"})
                .targetType(StockInfo.class)
                .build();
    }

    @Bean
    public StockInfoProcessor stockInfoProcessor(){
        return new StockInfoProcessor();
    }

    @Bean
    public FlatFileItemWriter<String> writer() {
        return new FlatFileItemWriterBuilder<String>()
                .name("stockInfoItemWriter")
                .resource(new FileSystemResource(
                        "target/output.txt"))
                .lineAggregator(new PassThroughLineAggregator<>())
                .build();
    }

    @Bean
    public JobExecutionListener listener() {
        return new SpringBatchJobCompletionListener();
    }
    //
    @Bean
    public Step step2() {
        return steps.get("step2")
                .tasklet((contribution, chunkContext) -> {
                    System.out.println("HOLA ESTAMOS EN EL PASO step2");
                    return RepeatStatus.FINISHED;
                })
                .build();
    }

    @Bean
    public Step conditionalStep1() {
        return steps.get("conditionalStep1")
                .tasklet((contribution, chunkContext) -> {
                    Object fail = chunkContext.getStepContext().getJobParameters().get("fail");
                    if (fail instanceof String) {
                        switch ((String) fail) {
                            case "1":
                                contribution.setExitStatus(ExitStatus.FAILED);
                                break;
                            case "2":
                                contribution.setExitStatus(new ExitStatus("HOGE"));
                                break;
                        }
                    }
                    double valor = Math.floor(Math.random() * (1 - 0 + 1)) + 0;
                    if(valor <= 0.5){
                        contribution.setExitStatus(ExitStatus.FAILED);
                    }
                    else{
                        contribution.setExitStatus(new ExitStatus("HOGE"));
                    }
                    return RepeatStatus.FINISHED;
                }).build();
    }

    @Bean
    public Step conditionalStep3() {
        return steps.get("conditionalStep3")
                .tasklet((contribution, chunkContext) -> {
                    System.out.println("vfmdjskvnfjd");
                    return RepeatStatus.FINISHED;
                }).build();
    }

    @Bean
    public Step conditionalStep4() {
        return steps.get("conditionalStep4")
                .tasklet((contribution, chunkContext) -> {
                    System.out.println("conditionalStep4");
                    return RepeatStatus.FINISHED;
                }).build();
    }


}

