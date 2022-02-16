package calebzhou.rdicloudrest.module.island;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
@Cacheable("islands")
public interface IslandRepo extends JpaRepository<Island,String> {

    @Modifying
    @Query("delete from Island where ownerUuid=:pid")
    void deleteByPid(@Param("pid") String pid);

    @Query("select islandId from Island where ownerUuid=:pid")
    String findIslandIdByPid(@Param("pid")String pid);

    @Query("select case when count(i)> 0 then true else false end from Island i where lower(i.ownerUuid) like lower(:pid)")
    boolean isPlayerOwnIsland(@Param("pid")String pid);
    @Query("select case when count(i)> 0 then true else false end from IslandCrew i where lower(i.mpid) like lower(:pid)")
    boolean isPlayerJoinIsland(@Param("pid")String pid);
}
