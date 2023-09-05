//package com.library.changelog;
//
//import com.library.model.entity.BookStored;
//import io.mongock.api.annotations.ChangeUnit;
//import io.mongock.api.annotations.Execution;
//import io.mongock.api.annotations.RollbackExecution;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.data.mongodb.core.MongoTemplate;
//import org.springframework.data.mongodb.core.query.Criteria;
//import org.springframework.data.mongodb.core.query.Query;
//import org.springframework.data.mongodb.core.query.Update;
//
//@ChangeUnit(order="003", id="dropColumn", author = "Lesha Korobko")
//@Slf4j
//public class DropColumnExample {
//
//    @Execution
//    public void deleteRowInvNumber(MongoTemplate mongoTemplate){
//        Update dropColumnInventoryNumber = new Update().unset("inventoryNumber");
//        Query query = new Query(Criteria.where("inventoryNumber").ne(null));
//        mongoTemplate.updateMulti(query, dropColumnInventoryNumber, BookStored.class);
//    }
//
//    @RollbackExecution
//    public void rollback(){
//        log.info("Rollback for mongock method deleteRowInvNumber");
//    }
//}
