package calebzhou.rdicloudrest.dao;

import calebzhou.rdicloudrest.model.Island;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IslandRepo extends JpaRepository<Island,String> {


    @Query(value = "select islandId from Island where ownerUuid=:pid",nativeQuery = true)
    String findIslandIdOwnByPid(@Param("pid")String pid);

    @Query(value = "select island from IslandCrew where mpid=:pid",nativeQuery = true)
    String findIslandIdJoinByPid(@Param("pid")String pid);

    @Override
    <S extends Island> S save(S entity);

    @Override
    void deleteById(String s);

    @Override
    Optional<Island> findById(String s);

    /*@Query("select case when count(i)> 0 then true else false end from Island i where lower(i.ownerUuid) like lower(:pid)")
    boolean isPlayerOwnIsland(@Param("pid")String pid);
    @Query("select case when count(i)> 0 then true else false end from IslandCrew i where lower(i.mpid) like lower(:pid)")
    boolean isPlayerJoinIsland(@Param("pid")String pid);*/
}
