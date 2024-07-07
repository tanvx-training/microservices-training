package com.tanvx.measurements.batch.job;

import com.tanvx.measurements.batch.model.MeasurementCsv;
import com.tanvx.measurements.entity.City;
import com.tanvx.measurements.entity.Measurement;
import com.tanvx.measurements.repository.CityRepository;
import com.tanvx.measurements.util.DateTimeUtil;
import java.io.File;
import java.time.LocalDateTime;
import java.util.Optional;
import javax.sql.DataSource;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@RequiredArgsConstructor
public class MigrateJob {

  private static final String CREATE_BY = "system_admin";

  private static final String FILE_NAME = "src/main/resources/measurements.csv";

  private static final LocalDateTime startTime = LocalDateTime.of(2022, 1, 1, 0, 0, 0, 0);

  private static final LocalDateTime endTime = LocalDateTime.of(2024, 1, 1, 0, 0, 0, 0);

  private final JobRepository jobRepository;

  private final PlatformTransactionManager transactionManager;

  private final CityRepository cityRepository;

  private final DateTimeUtil dateTimeUtil;

  private final DataSource dataSource;

  @Bean
  public Job migrateCsvJob() {

    return new JobBuilder("migrate-csv-job", jobRepository)
        .start(migrateCsvStep())
        .build();
  }

  @Bean
  public Step migrateCsvStep() {

    return new StepBuilder("migrate-csv-step", jobRepository)
        .<MeasurementCsv, City>chunk(10000, transactionManager)
        .reader(flatFileItemReader())
        .processor(processorCity())
        .writer(jdbcBatchItemWriter())
        .build();
  }

  @Bean
  public FlatFileItemReader<MeasurementCsv> flatFileItemReader() {
    FlatFileItemReader<MeasurementCsv> flatFileItemReader = new FlatFileItemReader<>();

    flatFileItemReader.setResource(new FileSystemResource(new File(FILE_NAME)));
    flatFileItemReader.setLineMapper(new DefaultLineMapper<>() {
      {
        setLineTokenizer(new DelimitedLineTokenizer() {
          {
            setDelimiter(";");
            setNames("city", "temperature");
          }
        });
        setFieldSetMapper(new BeanWrapperFieldSetMapper<>() {
          {
            setTargetType(MeasurementCsv.class);
          }
        });
      }
    });
    return flatFileItemReader;
  }

  @Bean
  public ItemProcessor<MeasurementCsv, Measurement> processorMeasurement() {

    return measurementCsv -> {
      Optional<City> optionalCity = cityRepository.findByName(measurementCsv.city());
      Measurement measurement = new Measurement();
      measurement.setTemperature(measurementCsv.temperature());
      measurement.setMeasurementTime(dateTimeUtil.getRandomBetween(startTime, endTime));
      measurement.setDeleteFlg(Boolean.FALSE);
      measurement.setCreatedAt(LocalDateTime.now());
      measurement.setCreatedBy(CREATE_BY);
      measurement.setCity(optionalCity.orElse(null));
      return measurement;
    };
  }

  @Bean
  public ItemProcessor<MeasurementCsv, City> processorCity() {

    return measurementCsv -> City.builder()
        .name(measurementCsv.city())
        .createdAt(LocalDateTime.now())
        .createdBy(CREATE_BY)
        .build();
  }

  @Bean
  public JdbcBatchItemWriter<City> jdbcBatchItemWriter() {
    JdbcBatchItemWriter<City> jdbcBatchItemWriter = new JdbcBatchItemWriter<>();
    jdbcBatchItemWriter.setDataSource(dataSource);
    jdbcBatchItemWriter.setSql(
        "INSERT INTO city (name, created_at, created_by) VALUES (:name, :createdAt, :createdBy)");
    jdbcBatchItemWriter.setItemSqlParameterSourceProvider(
        new BeanPropertyItemSqlParameterSourceProvider<>());
    return jdbcBatchItemWriter;
  }
}
