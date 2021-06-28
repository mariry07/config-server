package com.optical.Service.impl;

import com.optical.Service.ResidentService;
import com.optical.bean.Floor;
import com.optical.bean.OpWebResult;
import com.optical.bean.ResidentInfo;
import com.optical.bean.UserInfo;
import com.optical.mapper.ResidentInfoMapper;
import com.optical.mapper.UserInfoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by mary on 2021/4/14.
 */
@Service
public class ResidentServiceImpl implements ResidentService{

    private static final Logger log = LoggerFactory.getLogger(ResidentServiceImpl.class);

    @Autowired
    private ResidentInfoMapper residentInfoMapper;
    @Autowired
    private UserInfoMapper userInfoMapper;


    @Override
    public OpWebResult getResidentList(Integer floor, String roomName, Integer page, Integer limit) {
        OpWebResult op = new OpWebResult(OpWebResult.OP_SUCCESS, OpWebResult.OpMsg.OP_SUCCESS);
        Integer start = 0;
        Map rtn = new HashMap();
        List<ResidentInfo> list = null;

        try{
            if(limit == null || limit <= 0) {
                limit = 10;
            }
            if(page != null && page >= 0) {
                start = (page - 1) * limit;
            }

            Integer total = residentInfoMapper.getDataCountByCondition(floor, roomName, null);
            if(total > 0) {
                list = residentInfoMapper.getDataByCondition(roomName, floor, null, start, limit);
            }
            rtn.put("total",total);
            rtn.put("list", list);
            op.setData(rtn);

        }catch (Exception e) {
            log.error("getResidentList Error! e = {}", e);
            op.setResult(OpWebResult.OP_FAILED);
            op.setMsg(OpWebResult.OpMsg.OP_FAIL);
            return op;
        }

        return op;
    }

    @Override
    public OpWebResult getFloor() {

        OpWebResult op = new OpWebResult(OpWebResult.OP_SUCCESS, OpWebResult.OpMsg.OP_SUCCESS);
        Integer start = 0;
        Map rtn = new HashMap();
        List<Floor> list = new ArrayList<>();
        Floor f1 = new Floor(1L, "1", "一楼", 1);
        Floor f2 = new Floor(2L, "2", "二楼", 2);
        Floor f3 = new Floor(3L, "3", "三楼", 3);
        list.add(f1);
        list.add(f2);
        list.add(f3);
        rtn.put("list", list);
        op.setData(rtn);
        return op;
    }

    @Override
    public OpWebResult getFloorRoom(Integer floor, String deviceCode) {

        OpWebResult op = new OpWebResult(OpWebResult.OP_SUCCESS, OpWebResult.OpMsg.OP_SUCCESS);
        Integer start = 0;
        Map rtn = new HashMap();
        List<ResidentInfo> list = null;

        try{

            Integer total = residentInfoMapper.getDataCountByCondition(floor, null,deviceCode);
            if(total > 0) {
                list = residentInfoMapper.getDataByCondition(null, floor, deviceCode, null,null);
            }
            rtn.put("total",total);
            rtn.put("list", list);
            op.setData(rtn);

        }catch (Exception e) {
            log.error("getFloorRoom Error! e = {}", e);
            op.setResult(OpWebResult.OP_FAILED);
            op.setMsg(OpWebResult.OpMsg.OP_FAIL);
            return op;
        }

        return op;
    }

    @Override
    public OpWebResult getResidentInfoById(Long id) {
        OpWebResult op = new OpWebResult(OpWebResult.OP_SUCCESS, OpWebResult.OpMsg.OP_SUCCESS);

        try{
            ResidentInfo resident = residentInfoMapper.getById(id);
            op.setData(resident);

        }catch (Exception e) {
            log.error("getResidentInfoById Error! e = {}", e);
            op.setResult(OpWebResult.OP_FAILED);
            op.setMsg(OpWebResult.OpMsg.OP_FAIL);
            return op;
        }

        return op;
    }

    @Override
    public OpWebResult addNewResident(ResidentInfo residentInfo) {
        OpWebResult op = new OpWebResult(OpWebResult.OP_SUCCESS, OpWebResult.OpMsg.OP_SUCCESS);
        if(residentInfo.getName() == null || residentInfo.getAge() == null
                || residentInfo.getRoomName() == null ) {
            op.setResult(OpWebResult.OP_FAILED);
            op.setMsg("id 不可为null!");
            return op;
        }
        try{
            residentInfoMapper.insert(residentInfo);

        }catch (Exception e) {
            log.error("addNewResident Error! e = {}", e);
            op.setResult(OpWebResult.OP_FAILED);
            op.setMsg(OpWebResult.OpMsg.OP_FAIL);
            return op;
        }
        return op;
    }

    @Override
    public OpWebResult updateResident(Long id, String roomName, Integer floor, Integer age,
                                      Integer gender, String description, String name, String urgentPhone) {
        OpWebResult op = new OpWebResult(OpWebResult.OP_SUCCESS, OpWebResult.OpMsg.OP_SUCCESS);
        if(id == null) {
            op.setResult(OpWebResult.OP_FAILED);
            op.setMsg("id 不可为null!");
            return op;
        }
        try{
            Map map = new HashMap();
            map.put("id", id);
            map.put("roomName", roomName);
            map.put("name", name);
            map.put("floor", floor);
            map.put("age", age);
            map.put("gender", gender);
            map.put("description", description);
            map.put("urgentPhone", urgentPhone);

            int b = residentInfoMapper.updateResident(map);
            if(b <= 0) {
                op.setResult(OpWebResult.OP_FAILED);
                op.setMsg(OpWebResult.OpMsg.OP_FAIL);
            }

        }catch (Exception e) {
            log.error("updateResident Error! e = {}", e);
            op.setResult(OpWebResult.OP_FAILED);
            op.setMsg(OpWebResult.OpMsg.OP_FAIL);
            return op;
        }
        return op;
    }

    /**
     * 删除用户
     * @param id
     * @return
     */
    @Override
    public OpWebResult removeResident(Long id) {
        OpWebResult op = new OpWebResult(OpWebResult.OP_SUCCESS, OpWebResult.OpMsg.OP_SUCCESS);

        try{
            int b = residentInfoMapper.deleteById(id);
            if(b <= 0) {
                op.setResult(OpWebResult.OP_FAILED);
                op.setMsg(OpWebResult.OpMsg.OP_FAIL);
            }

        }catch (Exception e) {
            log.error("removeResident Error! e = {}", e);
            op.setResult(OpWebResult.OP_FAILED);
            op.setMsg(OpWebResult.OpMsg.OP_FAIL);
            return op;
        }

        return op;
    }

    @Override
    public OpWebResult getTabs(Long vendorId) {
        OpWebResult op = new OpWebResult(OpWebResult.OP_SUCCESS, OpWebResult.OpMsg.OP_SUCCESS);
        List rtnList = new ArrayList();
        Map rtnMap = new HashMap();
        if(vendorId == null ){
            return op;
        }
        try{

            UserInfo userInfo = userInfoMapper.getTags(vendorId);
            if(userInfo == null) {
                op.setResult(OpWebResult.OP_FAILED);
                op.setMsg(OpWebResult.OpMsg.OP_FAIL);
            }else{
                rtnMap.put("tagId", userInfo.getTagId());
                rtnMap.put("tagName", userInfo.getTagName());
                rtnList.add(rtnMap);
                op.setData(rtnList);
            }

        }catch (Exception e) {
            log.error("removeResident Error! e = {}", e);
            op.setResult(OpWebResult.OP_FAILED);
            op.setMsg(OpWebResult.OpMsg.OP_FAIL);
            return op;
        }

        return op;
    }
}
