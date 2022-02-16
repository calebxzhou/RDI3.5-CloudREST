package calebzhou.rdicloudrest.module.island;

import calebzhou.rdicloudrest.model.CoordLocation;
import calebzhou.rdicloudrest.utils.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static calebzhou.rdicloudrest.constants.LogicCondition.AND;
import static calebzhou.rdicloudrest.constants.LogicCondition.OR;

@Service
public class IslandService {
    @Autowired IslandRepo repo;

    public String getIslandIdByPlayerId(String pid){
        return repo.findIslandIdByPid(pid);
    }

    @CheckIslandStatus(ownIsland = false,joinIsland = false,condition = AND)
    public Island create(String pid){
        Island is = new Island(RandomUtils.getRandomId(),pid,RandomUtils.getRandomCoordinate());
        return repo.save(is);
    }
    @CheckIslandStatus(ownIsland = true,joinIsland = true,condition = OR)
    public Optional<Island> getById(String pid){
        return repo.findById(getIslandIdByPlayerId(pid));
    }
    @CheckIslandStatus(ownIsland = true,joinIsland = false,condition = AND)
    public void delete(String pid){
        repo.deleteById(getIslandIdByPlayerId(pid));
    }
    @CheckIslandStatus(ownIsland = true,joinIsland = false,condition = AND)
    public Island updateLocation(String pid,CoordLocation location){
        Island is   = repo.findById(getIslandIdByPlayerId(pid)).get();
        is.setLocation(location.toString());
        return repo.save(is);
    }

    public List<Island> getAll(){
        return repo.findAll();
    }
}
