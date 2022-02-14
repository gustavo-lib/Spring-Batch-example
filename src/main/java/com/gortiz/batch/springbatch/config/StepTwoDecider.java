package com.gortiz.batch.springbatch.config;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.job.flow.FlowExecutionStatus;
import org.springframework.batch.core.job.flow.JobExecutionDecider;
import org.springframework.stereotype.Component;

@Component
public class StepTwoDecider implements JobExecutionDecider {
    @Override
    public FlowExecutionStatus decide(JobExecution jobExecution, StepExecution stepExecution) {
        double valor = Math.floor(Math.random() * (1 - 0 + 1)) + 0;
        if (valor<0.5){
            //return new FlowExecutionStatus(stepExecution.getStatus().toString()); line 57 error
            return  FlowExecutionStatus.FAILED;
        }
        else {
            return  FlowExecutionStatus.COMPLETED;
        }


    }
}