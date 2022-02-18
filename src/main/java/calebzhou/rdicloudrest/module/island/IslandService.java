package calebzhou.rdicloudrest.module.island;

import calebzhou.rdicloudrest.model.CoordLocation;
import calebzhou.rdicloudrest.utils.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static calebzhou.rdicloudrest.constants.LogicCondition.AND;
import static calebzhou.rdicloudrest.constants.LogicCondition.OR;

@Service
public class IslandService {
    @Autowired IslandRepo repo;

    public String getIidOwn(String pid){
        return repo.findIslandIdOwnByPid(pid);
    }

    public String getIidJoin(String pid){
        return repo.findIslandIdJoinByPid(pid);
    }

    @CheckIslandStatus(needOwnIsland = false, needJoinIsland = false,condition = AND)
    public Island create(String pid){
        Island is = new Island(RandomUtils.getRandomId(),pid,RandomUtils.getRandomCoordinate());
        return repo.save(is);
    }

    @CheckIslandStatus(needOwnIsland = true, needJoinIsland = true,condition = OR)
    public Optional<Island> getById(String pid){
        String iid = getIidOwn(pid);
        if(StringUtils.isEmpty(iid))
            iid = getIidJoin(pid);
        return repo.findById(iid);
    }

    @CheckIslandStatus(needOwnIsland = true, needJoinIsland = false,condition = AND)
    public void delete(String pid){
        repo.deleteById(getIidOwn(pid));
    }

    @CheckIslandStatus(needOwnIsland = true, needJoinIsland = false,condition = AND)
    public Island updateLocation(String pid,CoordLocation location){
        Island is   = repo.findById(getIidOwn(pid)).get();
        is.setLocation(location.toString());
        return repo.save(is);
    }

    @CheckIslandStatus(needOwnIsland = true, needJoinIsland = false,condition = AND)
    public void addMember(String pid,String mpid){
        Island island = repo.findById(getIidOwn(pid)).get();
        if(island.isMemberExists(mpid))
            throw new IslandException("此玩家已经是您的空岛成员了，不可以重复添加。");
        island.addMember(new IslandCrew(island,mpid));
        repo.save(island);
    }
    @CheckIslandStatus(needOwnIsland = true, needJoinIsland = false,condition = AND)
    public void removeMember(String pid,String mpid){
        Island island = repo.findById(getIidOwn(pid)).get();
        if(!island.isMemberExists(mpid))
            throw new IslandException("此玩家不是您的空岛成员，不可以删除他。");
        island.removeMember(new IslandCrew(island,mpid));
        repo.save(island);
    }

    public boolean isPlayerOwnIsland(String pid){
        String iid = getIidOwn(pid);
        return !StringUtils.isEmpty(iid);
    }
    public boolean isPlayerJoinIsland(String pid){
        String iid = getIidJoin(pid);
        return !StringUtils.isEmpty(iid);
    }

    public List<Island> getAll(){
        return repo.findAll();
    }
}
