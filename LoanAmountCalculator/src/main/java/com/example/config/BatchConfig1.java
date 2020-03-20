package com.example.config;


	 
	import javax.sql.DataSource;
	import org.springframework.batch.core.Job;
	import org.springframework.batch.core.Step;
	import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
	import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
	import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
	import org.springframework.batch.core.launch.support.RunIdIncrementer;
	import org.springframework.batch.item.ItemProcessor;
	import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
	import org.springframework.batch.item.database.JdbcBatchItemWriter;
	import org.springframework.batch.item.file.FlatFileItemReader;
	import org.springframework.batch.item.file.LineMapper;
	import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
	import org.springframework.batch.item.file.mapping.DefaultLineMapper;
	import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
	import org.springframework.beans.factory.annotation.Autowired;
	import org.springframework.beans.factory.annotation.Value;
	import org.springframework.context.annotation.Bean;
	import org.springframework.context.annotation.Configuration;
	import org.springframework.core.io.Resource;
	import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
	import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import com.example.bean.Customer;
import com.example.dao.DBLogProcessor;
	
	 
	@Configuration
	@EnableBatchProcessing
	public class BatchConfig1 {
	     
	    @Autowired
	    private JobBuilderFactory jobBuilderFactory;
	 
	    @Autowired
	    private StepBuilderFactory stepBuilderFactory;
	 
	    @Value("classPath:/input/inputData.csv")
	    private Resource inputResource;
	 
	    @Bean
	    public Job readCSVFileJob() {
	        return jobBuilderFactory
	                .get("readCSVFileJob")
	                .incrementer(new RunIdIncrementer())
	                .start(step())
	                .build();
	    }
	 
	    @Bean
	    public Step step() {
	        return stepBuilderFactory
	                .get("step")
	                .<Customer, Customer>chunk(5)
	                .reader(reader())
	                .processor(processor())
	                .writer(writer())
	                .build();
	    }
	     
	    @Bean
	    public ItemProcessor<Customer, Customer> processor() {
	        return new DBLogProcessor();
	    }
	     
	    @Bean
	    public FlatFileItemReader<Customer> reader() {
	        FlatFileItemReader<Customer> itemReader = new FlatFileItemReader<Customer>();
	        itemReader.setLineMapper(lineMapper());
	        itemReader.setLinesToSkip(1);
	        itemReader.setResource(inputResource);
	        return itemReader;
	    }
	 
	    @Bean
	    public LineMapper<Customer> lineMapper() {
	        DefaultLineMapper<Customer> lineMapper = new DefaultLineMapper<Customer>();
	        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
	        lineTokenizer.setNames(new String[] { "id", "name", "gender","age","adhaar","panNO" });
	        lineTokenizer.setIncludedFields(new int[] { 0, 1, 2 });
	        BeanWrapperFieldSetMapper<Customer> fieldSetMapper = new BeanWrapperFieldSetMapper<Customer>();
	        fieldSetMapper.setTargetType(Customer.class);
	        lineMapper.setLineTokenizer(lineTokenizer);
	        lineMapper.setFieldSetMapper(fieldSetMapper);
	        return lineMapper;
	    }
	 
	    @Bean
	    public JdbcBatchItemWriter<Customer> writer() {
	        JdbcBatchItemWriter<Customer> itemWriter = new JdbcBatchItemWriter<Customer>();
	        itemWriter.setDataSource(dataSource());
	        itemWriter.setSql("INSERT INTO EMPLOYEE (ID, name, age,gender,adhaar,panNo) VALUES (:id, :name, :gender,:age,:adhaar,:panNO)");
	        itemWriter.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<Customer>());
	        return itemWriter;
	    }
	     
	    @Bean
	    public DataSource dataSource(){
	        EmbeddedDatabaseBuilder embeddedDatabaseBuilder = new EmbeddedDatabaseBuilder();
	        return embeddedDatabaseBuilder.addScript("classpath:org/springframework/batch/core/schema-drop-h2.sql")
	                .addScript("classpath:org/springframework/batch/core/schema-h2.sql")
	                .addScript("classpath:employee.sql")
	                .setType(EmbeddedDatabaseType.H2)
	                .build();
	    }
	}


