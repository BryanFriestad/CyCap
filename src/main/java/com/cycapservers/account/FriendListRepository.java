package com.cycapservers.account;

import com.cycapservers.account.FriendList;

import java.sql.SQLException;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface FriendListRepository extends CrudRepository<FriendList, String> {
	
	@Query(value = "call FindAcceptedFriends(:username);", nativeQuery = true)
	List<Object[]> FindAcceptedFriends(@Param("username") String username);
	
	@Query(value = "call FindPendingReceived(:username);", nativeQuery = true)
	List<String> FindPendingReceived(@Param("username") String username);
	
	@Query(value = "call FindPendingSent(:username);", nativeQuery = true)
	List<Object[]> FindPendingSent(@Param("username") String username);
	
	@Query(value = "call FindBlockedByUser(:username);", nativeQuery = true)
	List<String> FindBlockedByUser(@Param("username") String username);
    
	@Query(value = "call SendFriendRequest(:sender, :recipient);", nativeQuery = true)
	Integer SendFriendRequest(@Param("sender") String sender_un, @Param("recipient") String rec_un);
	
	@Query(value = "call AcceptFriendRequest(:sender, :recipient);", nativeQuery = true)
	Integer AcceptFriendRequest(@Param("sender") String sender_un, @Param("recipient") String rec_un);
	
	@Query(value = "call DenyFriendRequest(:sender, :recipient);", nativeQuery = true)
	Integer DenyFriendRequest(@Param("sender") String sender_un, @Param("recipient") String rec_un);
	
	@Query(value = "call BlockUser(:sender, :recipient);", nativeQuery = true)
	Integer BlockUser(@Param("sender") String sender_un, @Param("recipient") String rec_un);
	
	@Query(value = "call UnblockUser(:sender, :recipient);", nativeQuery = true)
	Integer UnblockUser(@Param("sender") String sender_un, @Param("recipient") String rec_un);
}
