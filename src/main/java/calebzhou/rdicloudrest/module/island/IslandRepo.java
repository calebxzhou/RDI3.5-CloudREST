package calebzhou.rdicloudrest.module.island;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface IslandRepo extends JpaRepository<Island,String> {

    @Modifying
    @Query("delete from Island where ownerUuid=:pid")
    void deleteByPid(@Param("pid") String pid);

    @Query("select islandId from Island where ownerUuid=:pid")
    String findIslandIdByPid(@Param("pid")String pid);
}
