package com.challenge.crud.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.challenge.crud.entities.UserEntity;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

	@Query(value = "SELECT * FROM tb_usuario tu "
			+ "WHERE LOWER(TRIM(tu.email)) = LOWER(TRIM(ifnull(:userEmail, tu.email))) "
			+ "AND LOWER(TRIM(tu.nome)) LIKE LOWER(TRIM(ifnull(:userName, tu.nome))) "
			+ "ORDER BY tu.nome", nativeQuery = true)
	List<UserEntity> findUserByEmailAndNameOrderByName(
			@Param("userEmail") String userEmail,
			@Param("userName") String userName);

	List<UserEntity> findByNameContainingIgnoreCaseOrderByName(String name);
	
	UserEntity findByEmail(String email);

}
