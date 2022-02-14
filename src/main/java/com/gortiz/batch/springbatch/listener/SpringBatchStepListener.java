package com.gortiz.batch.springbatch.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;

public class SpringBatchStepListener implements StepExecutionListener {

    Logger logger = LoggerFactory.getLogger(SpringBatchStepListener.class);

    @Override
    public void beforeStep(StepExecution stepExecution) {
        logger.info(stepExecution.getStepName());

        logger.info("SPStepListener - CALLED BEFORE STEP.ANTES DE STEP " +
                stepExecution.getStatus().toString());
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        logger.info("SPStepListener - CALLED AFTER STEP. CUANDO TERMINA " +
                stepExecution.getStatus().toString());
        String cad = stepExecution.getExitStatus().getExitCode();
        Boolean a = stepExecution.getExitStatus().toString().equals("FAILED");
        if (a){
            logger.info("fallo");
        }


        return ExitStatus.COMPLETED;
    }
}