package calebzhou.rdicloudrest.module.qqbind;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface QBindRepo extends JpaRepository<QBind,String> {
    @Transactional
    @Modifying
    @Query("delete from QBind q where q.pid = ?1")
    void deleteByPid(String pid);
}
