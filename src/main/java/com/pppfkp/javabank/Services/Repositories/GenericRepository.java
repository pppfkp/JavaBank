package com.pppfkp.javabank.Services.Repositories;

import com.pppfkp.javabank.Data.DTOs.IMapableTo;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.ArrayList;
import java.util.List;

public class GenericRepository<DataType, DataDTOType extends IMapableTo<DataType>, IdType> {
    private SessionFactory sessionFactory;
    private Class<DataType> dataTypeClass;
    public GenericRepository(SessionFactory sessionFactory, Class<DataType> dataTypeClass) {
        this.sessionFactory = sessionFactory;
        this.dataTypeClass = dataTypeClass;
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }



     IdType CreateRecord(DataDTOType dto) {
        List<String> validationErrors = dto.Validate();
        Session session = sessionFactory.openSession();
        if (!validationErrors.isEmpty()) {
            session.close();
            return null;
        }
        DataType recordToCreate = dto.MapToEntityTypeNewRecord();
        IdType result = (IdType)
        session.save(recordToCreate);
        session.close();
        return result;
     }

     DataType GetSingleRecordById(IdType id) {
        Session session = sessionFactory.openSession();
        DataType record = session.get(dataTypeClass, id);
        session.close();
        return record;
     }

     List<DataType> GetAllRecords() {
        Session session = sessionFactory.openSession();
        List<DataType> results = new ArrayList<DataType>();
        results = (List<DataType>) session.createQuery("from " + dataTypeClass.getSimpleName()).list();
        session.close();
        return results;
     }

     boolean UpdateRecord(DataDTOType dto, IdType id) {
         List<String> validationErrors = dto.Validate();
         Session session = sessionFactory.openSession();
         if (!validationErrors.isEmpty()) {
             session.close();
             return false;
         }
         DataType oldRecord = GetSingleRecordById(id);
         DataType recordToCreate = dto.MapToEntityTypeUpdateRecord(oldRecord);
         session.update(recordToCreate);
         //add Comparable check by value;
         session.close();
         return true;
     }

}
