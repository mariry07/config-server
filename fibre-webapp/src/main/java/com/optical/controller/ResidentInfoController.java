package com.optical.controller;

import com.optical.Service.ResidentService;
import com.optical.bean.OpWebResult;
import com.optical.bean.ResidentInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by mary on 2021/4/14.
 */
@Controller
@RequestMapping(value = "/xitang/resident")
public class ResidentInfoController {
    @Autowired
    private ResidentService residentService;

    @RequestMapping(value = "/list", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public OpWebResult resident(String roomName, Integer floor, Integer page, Integer limit) {

        return residentService.getResidentList(floor, roomName, page, limit);
    }

    @RequestMapping(value = "/newResident", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public OpWebResult newResident(ResidentInfo residentInfo) {

        return residentService.addNewResident(residentInfo);
    }

    @RequestMapping(value = "/updateResident", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public OpWebResult updateResident(Long id, String roomName, Integer floor, Integer age, Integer gender,
                                      String description) {

//        return residentService.updateResident(id, roomName, floor, age, gender, description);
        return null;
    }

    @RequestMapping(value = "/removeResident", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public OpWebResult removeResident(Long id) {

        return residentService.removeResident(id);
    }


    @RequestMapping(value = "/info", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public OpWebResult resident(Long id) {

        return residentService.getResidentInfoById(id);
    }



    @RequestMapping(value = "/floor", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public OpWebResult floor() {
        return residentService.getFloor();
    }


    @RequestMapping(value = "/room", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public OpWebResult room(Integer floor, String deviceCode) {
        return residentService.getFloorRoom(floor, deviceCode);
    }


}
