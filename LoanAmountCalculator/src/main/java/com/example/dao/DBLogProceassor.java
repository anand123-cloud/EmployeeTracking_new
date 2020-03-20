package com.example.dao;
 
import org.springframework.batch.item.ItemProcessor;
import com.example.dao.bean.Customer;
 
public class DBLogProcessor implements ItemProcessor<Customer, customer>
{
    public Customer process(Customer customer) throws Exception
    {
        System.out.println("Inserting employee : " + customer);
        return customer;
    }
}