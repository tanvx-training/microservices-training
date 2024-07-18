package com.tanvx.measurements.app.batch.job;

import com.tanvx.measurements.app.batch.listener.JobExecutionTimeListener;
import com.tanvx.measurements.app.batch.model.MeasurementCsv;
import com.tanvx.measurements.app.batch.processor.ProcessorMeasurement;
import com.tanvx.measurements.app.batch.writer.MigrateMeasurementWriter;
import com.tanvx.measurements.domain.measurement.entity.Measurement;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.orm.jpa.JpaTransactionManager;

@Configuration
@RequiredArgsConstructor
public class MigrateJob {

  private static final String FILE_NAME = "E:\\Java\\spring-microservices\\measurements\\src\\main\\resources\\measurements.csv";

  private static final String READER_NAME = "migrate-csv-reader";

  private final JobRepository jobRepository;

  private final JpaTransactionManager jpaTransactionManager;

  private final ProcessorMeasurement processorMeasurement;

  private final MigrateMeasurementWriter migrateMeasurementWriter;

  private final JobExecutionTimeListener jobExecutionTimeListener;

  @Bean
  public Job migrateCsvJob() {
    return new JobBuilder("migrate-csv-job", jobRepository)
        .start(migrateCsvStep())
        .listener(jobExecutionTimeListener)
        .build();
  }

  @Bean
  public Step migrateCsvStep() {
    return new StepBuilder("migrate-csv-step", jobRepository)
        .<MeasurementCsv, Measurement>chunk(100000, jpaTransactionManager)
        .reader(flatFileItemReader())
        .processor(processorMeasurement)
        .writer(migrateMeasurementWriter)
        .build();
  }

  @Bean
  public FlatFileItemReader<MeasurementCsv> flatFileItemReader() {
    FlatFileItemReader<MeasurementCsv> flatFileItemReader = new FlatFileItemReader<>();
    flatFileItemReader.setResource(new FileSystemResource(FILE_NAME));
    flatFileItemReader.setName(READER_NAME);
    flatFileItemReader.setLinesToSkip(0);
    flatFileItemReader.setLineMapper(lineMapperMeasurementCsv());
    return flatFileItemReader;
  }

  @Bean
  public LineMapper<MeasurementCsv> lineMapperMeasurementCsv() {

    DelimitedLineTokenizer delimitedLineTokenizer = new DelimitedLineTokenizer();
    delimitedLineTokenizer.setDelimiter(";");
    delimitedLineTokenizer.setNames("city", "temperature");

    BeanWrapperFieldSetMapper<MeasurementCsv> beanWrapperFieldSetMapper = new BeanWrapperFieldSetMapper<>();
    beanWrapperFieldSetMapper.setTargetType(MeasurementCsv.class);

    DefaultLineMapper<MeasurementCsv> defaultLineMapper = new DefaultLineMapper<>();
    defaultLineMapper.setLineTokenizer(delimitedLineTokenizer);
    defaultLineMapper.setFieldSetMapper(beanWrapperFieldSetMapper);
    return defaultLineMapper;
  }
}
