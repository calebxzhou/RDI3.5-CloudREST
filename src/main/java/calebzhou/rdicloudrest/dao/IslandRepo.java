package calebzhou.rdicloudrest.dao;

import calebzhou.rdicloudrest.model.Island;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IslandRepo extends JpaRepository<Island,Integer> {


    @Query(value = "select iid from Island where pid=:pid",nativeQuery = true)
    Integer findIslandIdOwnByPid(@Param("pid")String pid);

    @Query(value = "select iid from Island where crew like %:pid%",nativeQuery = true)
    Integer findIslandIdJoinByPid(@Param("pid")String pid);

    @Override
    <S extends Island> S save(S entity);

    void deleteByIid(Integer s);

    Optional<Island> findByIid(Integer s);

    /*@Query("select case when count(i)> 0 then true else false end from Island i where lower(i.ownerUuid) like lower(:pid)")
    boolean isPlayerOwnIsland(@Param("pid")String pid);
    @Query("select case when count(i)> 0 then true else false end from IslandCrew i where lower(i.mpid) like lower(:pid)")
    boolean isPlayerJoinIsland(@Param("pid")String pid);*/
}
