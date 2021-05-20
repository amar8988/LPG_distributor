package com.satyam.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.satyam.model.Cylinder;

public interface CylinderRepository extends CrudRepository<Cylinder, Integer> {
	@Query(value="select * from cylinder c where c.type=:type and c.capacity=:capacity and c.status=:status order by id desc limit 1" , nativeQuery=true)
	public Optional<Cylinder> findCylinderByStatusTypeCapacity(@Param("status") String status , @Param("type") String type , @Param("capacity") String capacity);
	
	@Query(value="select max(id) from cylinder c where c.type=:type and c.capacity=:capacity and c.status='empty'" , nativeQuery=true)
	public int getMaxIdByTypeCapacity(@Param("type") String type ,  @Param("capacity") String capacity);
	
	@Query(value="select date from cylinder c where c.id=:id" , nativeQuery=true)
	public String getLastDateById(@Param("id") int id);
	
	@Query(value="select * from cylinder c where c.type=:type and c.capacity=:capacity and c.date=:date and c.sold_or_purchase='sold' order by id desc" , nativeQuery=true)
	public List<Cylinder> findSoldCylinderListByTypeCapacityDate(@Param("type") String type , @Param("capacity") String capacity , @Param("date") String date);
}
