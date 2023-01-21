package com.pppfkp.javabank.Repositories;

import com.pppfkp.javabank.Data.DTOs.IMapableTo;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

public class GenericRepository<DataType, DataDTOType extends IMapableTo<DataType>, IdType> {
    private SessionFactory sessionFactory;
    private Class<DataType> dataTypeClass;

    private List<String> validationErrors;

    public GenericRepository(SessionFactory sessionFactory, Class<DataType> dataTypeClass) {
        this.sessionFactory = sessionFactory;
        this.dataTypeClass = dataTypeClass;
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    //CREATE
    IdType CreateRecord(DataDTOType dto) {
        List<String> validationErrors = dto.ValidateAll();

        if (!validationErrors.isEmpty()) {
            return null;
        }
        DataType recordToCreate = dto.MapToEntityTypeNewRecord();
        IdType result = (IdType) CreateRecord(recordToCreate);
        return result;
     }
    IdType CreateRecord(DataType data) {
        IdType result;
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            result = (IdType) session.save(data);
            session.getTransaction().commit();
        } finally {
            session.close();
        }
        return result;
    }
    //READ
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
    DataType GetSingleRecordByFieldValue(String fieldName, String fieldValue) {
        Session session = getSessionFactory().openSession();
        Query<DataType> query = session.createQuery("from " +  dataTypeClass.getSimpleName() + " t where t." + fieldName + "=:par", dataTypeClass);
        query.setParameter("par", fieldValue);
        DataType record = query.setMaxResults(1).getSingleResultOrNull();
        session.close();
        return record;
    }
    List<DataType> GetAllRecordsByFieldValue(String fieldName, String fieldValue) {
        Session session = getSessionFactory().openSession();
        Query<DataType> query = session.createQuery("from " +  dataTypeClass.getSimpleName() + " t where t." + fieldName + "=:par", dataTypeClass);
        query.setParameter("par", fieldValue);
        List<DataType> records = query.list();
        session.close();
        return records;
    }
    //UPDATE
    //TODO check if success
    boolean UpdateRecord(DataDTOType dto, IdType id) {
         List<String> validationErrors = dto.ValidateUpdatable();
         Session session = sessionFactory.openSession();

         if (!validationErrors.isEmpty()) {
             session.close();
             return false;
         }

         try {
             session.beginTransaction();
             DataType data = session.get(dataTypeClass, id);
             if (data == null) {
                 session.getTransaction().rollback();
                 session.close();
                 return false;
             }
             dto.MapToEntityTypeUpdateRecord(data);
             session.getTransaction().commit();
         } finally {
             session.close();
         }
         //check if the records are the same and return the result
         return true;
     }
     boolean UpdateRecord(DataType data) {
         Session session = sessionFactory.openSession();

         if (!validationErrors.isEmpty()) {
             session.close();
             return false;
         }

         try {
             session.beginTransaction();
             if (data == null) {
                 session.getTransaction().rollback();
                 session.close();
                 return false;
             }
             session.update(data);
             session.getTransaction().commit();
         } finally {
             session.close();
         }
         //check if the records are the same and return the result
         return true;
     }
    //DELETE
    boolean DeleteRecord(IdType id) {
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            DataType data = session.get(dataTypeClass, id);
            session.delete(data);
            session.getTransaction().commit();
        } finally {
            session.close();
        }
        return (GetSingleRecordById(id) == null);
     }
}
